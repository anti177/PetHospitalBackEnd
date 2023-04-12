package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.CaseDao;
import com.example.pethospitalbackend.dao.DiseaseDao;
import com.example.pethospitalbackend.dao.InspectionCaseDao;
import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.IllCase;
import com.example.pethospitalbackend.entity.InspectionCase;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.util.SerialUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class CaseService {

  private static final Logger logger = LoggerFactory.getLogger(CaseService.class);

  @Resource DiseaseDao diseaseDao;

  @Resource CaseDao caseDao;

  @Resource InspectionCaseDao inspectionCaseDao;

  @Resource FileService fileService;

  // ----------------------------------前台方法----------------------------

  public Response<List<CategoryDTO>> getTotalCategory() {
    Response<List<CategoryDTO>> response = new Response<>();
    List<String> typeList;
    List<Disease> diseaseList;
    try {
      typeList = diseaseDao.getAllType();
      diseaseList = diseaseDao.getAllDisease();
    } catch (Exception e) {
      logger.error(
          "[getTotalCategory Fail], error message{}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
    List<CategoryDTO> categoryDTOList = new ArrayList<>();
    for (int i = 0; i < typeList.size(); i++) {
      CategoryDTO categoryDTO = new CategoryDTO();
      categoryDTO.setTypeId(i);
      categoryDTO.setTypeName(typeList.get(i));
      List<Disease> subDiseaseList = new ArrayList<>();
      boolean endFlag = false;
      for (Disease d : diseaseList) {
        if (d.getTypeName().equals(typeList.get(i))) {
          subDiseaseList.add(d);
          if (!endFlag) endFlag = true;
        } else {
          if (endFlag) break;
        }
      }
      categoryDTO.setDiseaseDTOList(subDiseaseList);
      categoryDTOList.add(categoryDTO);
    }

    response.setSuc(categoryDTOList);
    return response;
  }

  public Response<List<CaseCategoryDTO>> getCaseCategoryByDiseaseId(Long diseaseId) {
    Response<List<CaseCategoryDTO>> response = new Response<>();
    List<CaseCategoryDTO> categoryDTOList;
    try {
      categoryDTOList = caseDao.getCaseByDiseaseId(diseaseId);
    } catch (Exception e) {
      logger.error(
          "[getCaseCategory Fail], diseaseId : {},error message{}",
          SerialUtil.toJsonStr(diseaseId),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
    response.setSuc(categoryDTOList);
    return response;
  }

  public Response<CaseFrontDetailDTO> getFrontCaseByCaseId(Long caseId) {
    Response<CaseFrontDetailDTO> response = new Response<>();
    CaseFrontDetailDTO caseFrontDetailDTO = new CaseFrontDetailDTO();
    try {
      IllCase illCase = caseDao.getCaseByCaseId(caseId);
      if (illCase != null) {
        caseFrontDetailDTO.setCaseId(illCase.getCaseId());
        caseFrontDetailDTO.setCaseName(illCase.getCaseName());
        caseFrontDetailDTO.setAdmissionText(illCase.getAdmissionText());
        caseFrontDetailDTO.setDiagnosticInfo(illCase.getDiagnosticInfo());
        caseFrontDetailDTO.setTreatmentInfo(illCase.getTreatmentInfo());

        List<String> admissionGraphs = caseDao.getAdmissionGraphByCaseId(caseId);
        if (admissionGraphs != null) {
          caseFrontDetailDTO.setAdmissionGraphList(admissionGraphs); // 需要修改一下逻辑让插入的文件sort_num最大
        }
        List<String> treatmentGraphs = caseDao.getTreatmentGraphByCaseId(caseId);
        if (treatmentGraphs != null) {
          caseFrontDetailDTO.setTreatmentGraphList(treatmentGraphs);
        }
        List<String> treatmentVideos = caseDao.getTreatmentVideoByCaseId(caseId);
        if (treatmentVideos != null) {
          caseFrontDetailDTO.setTreatmentVideoList(treatmentVideos);
        }

        List<InspectionFrontDTO> inspectionFrontDTOList =
            inspectionCaseDao.getInspectionCaseByCaseId(caseId);
        if (inspectionFrontDTOList != null) {
          for (int i = 0; i < inspectionFrontDTOList.size(); i++) {
            InspectionFrontDTO a = inspectionFrontDTOList.get(i);
            List<String> inspectionGraphs =
                inspectionCaseDao.getInspectionGraphByInspectionCaseId(a.getInspectionCaseId());
            if (inspectionGraphs != null) {
              a.setInspectionGraphList(inspectionGraphs);
              inspectionFrontDTOList.set(i, a);
            }
          }
          caseFrontDetailDTO.setInspectionFrontDTOList(inspectionFrontDTOList);
        }
      }
    } catch (Exception e) {
      logger.error(
          "[getCaseByCaseId Fail],caseId:{}, error message{}",
          SerialUtil.toJsonStr(caseId),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
    response.setSuc(caseFrontDetailDTO);
    return response;
  }

  // ---------------------------------后台方法------------------------------

  @Transactional(rollbackFor = Exception.class)
  public IllCase addCase(CaseBackFormDTO form) {
    try {
      // 插入病例基本类
      IllCase illCase = transformIllCaseFormToIllCase(form);
      caseDao.insert(illCase);
      Long caseId = illCase.getCaseId();

      List<String> admissionGraphUrls = form.getAdmission_graphs();
      List<String> therapyGraphUrls = form.getTherapy_graphs();
      List<String> therapyVideoUrls = form.getTherapy_videos();
      // 如果存在，添加相关图片和视频信息
      if (admissionGraphUrls != null) {
        List<FileDTO> admissionGraphList = getFileDTOList(admissionGraphUrls, caseId);
        caseDao.insertFiles(admissionGraphList, "admission_graph");
      }
      if (therapyGraphUrls != null) {
        List<FileDTO> therapyGraphList = getFileDTOList(therapyGraphUrls, caseId);
        caseDao.insertFiles(therapyGraphList, "treatment_graph");
      }
      if (therapyVideoUrls != null) {
        List<FileDTO> therapyVideoList = getFileDTOList(therapyVideoUrls, caseId);
        caseDao.insertFiles(therapyVideoList, "treatment_video");
      }

      List<InspectionCaseFrontDTO> inspectionCaseList = form.getInspection_cases();
      if (inspectionCaseList != null) {
        for (int i = 0; i < form.getInspection_cases().size(); i++) {
          // 添加相关检查项目信息
          InspectionCaseFrontDTO inspectionCaseFrontDTO = form.getInspection_cases().get(i);
          InspectionCase inspectionCase = new InspectionCase();
          inspectionCase.setCaseId(caseId);
          inspectionCase.setItemId(inspectionCaseFrontDTO.getInspection_item_id());
          inspectionCase.setResult(inspectionCaseFrontDTO.getInspection_result_text());
          inspectionCase.setSortNum((long) i + 1);
          inspectionCaseDao.insert(inspectionCase);

          // 添加相关检查图片信息
          Long inspectionCaseId = inspectionCase.getInspectionCaseId();
          List<String> inspectionGraphUrls = inspectionCaseFrontDTO.getInspection_graphs();
          if (inspectionGraphUrls.size() > 0) {
            List<FileDTO> inspectionGraphList =
                getFileDTOList(inspectionGraphUrls, inspectionCaseId);
            caseDao.insertInspectionGraphs(inspectionGraphList);
          }
        }
      }
      return illCase;
    } catch (Exception e) {
      logger.error("[add case Fail], error message: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  // todo: 重构，修改一下方法参数顺序
  public List<FileDTO> getFileDTOList(List<String> urlList, Long caseId) {
    List<FileDTO> fileDTOList = new ArrayList<>();
    for (int i = 0; i < urlList.size(); i++) {
      FileDTO fileDTO = new FileDTO();
      fileDTO.setCaseId(caseId);
      fileDTO.setSortNum((long) i + 1);
      fileDTO.setUrl(urlList.get(i));
      fileDTOList.add(fileDTO);
    }
    return fileDTOList;
  }

  // 只删表，不删实际文件
  public int deleteCasesData(long caseId) {
    List<Long> inspectionCaseIdList =
        inspectionCaseDao.selectAllInspectionCaseIdByIllCaseId(caseId);
    if (inspectionCaseIdList.size() > 0) {
      inspectionCaseDao.deleteInspectionGraphsByInspectionCaseId(
          inspectionCaseIdList); // 删除检查情况中的照片
      inspectionCaseDao.deleteInspectionCasesByInspectionCaseId(inspectionCaseIdList); // 删除检查情况
    }

    caseDao.deleteFilesByIllCaseId("admission_graph", caseId);
    caseDao.deleteFilesByIllCaseId("treatment_graph", caseId);
    caseDao.deleteFilesByIllCaseId("treatment_video", caseId);

    return caseDao.deleteByPrimaryKey(caseId);
  }

  @Transactional(rollbackFor = Exception.class) // 包含实际文件删除
  public int deleteCase(long caseId) {
    try {
      List<String> graphUrlS = new LinkedList<>();
      List<Long> inspectionCaseIdList =
          inspectionCaseDao.selectAllInspectionCaseIdByIllCaseId(caseId);
      if (inspectionCaseIdList.size() > 0) {
        graphUrlS.addAll(
            inspectionCaseDao.getInspectionGraphUrlByInspectionCaseId(inspectionCaseIdList));
        inspectionCaseDao.deleteInspectionGraphsByInspectionCaseId(
            inspectionCaseIdList); // 删除检查情况中的照片
        inspectionCaseDao.deleteInspectionCasesByInspectionCaseId(inspectionCaseIdList); // 删除检查情况
      }
      graphUrlS.addAll(caseDao.getFilesByIllCaseId("admission_graph", caseId));
      graphUrlS.addAll(caseDao.getFilesByIllCaseId("treatment_graph", caseId));
      List<String> videoUrls = caseDao.getFilesByIllCaseId("treatment_video", caseId);

      caseDao.deleteFilesByIllCaseId("admission_graph", caseId);
      caseDao.deleteFilesByIllCaseId("treatment_graph", caseId);
      caseDao.deleteFilesByIllCaseId("treatment_video", caseId);

      caseDao.deleteByPrimaryKey(caseId); // 先删表数据

      fileService.deleteGraphs(graphUrlS); // 再删实际文件
      fileService.deleteVideos(videoUrls);
      return 1;
    } catch (Exception e) {
      logger.error(
          "[delete ill case fail], caseId: {}, error message: {}",
          SerialUtil.toJsonStr(caseId),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int updateCase(CaseBackFormDTO formDTO) {
    Long id = formDTO.getCase_id();
    try {
      deleteCasesData(id);
      formDTO.setCase_id(id);
      addCase(formDTO);
      return 1;
    } catch (Exception e) {
      logger.error(
          "[update ill case fail], caseId: {}, error message: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  // 工具方法，用于转换前端表单类到实体类
  private IllCase transformIllCaseFormToIllCase(CaseBackFormDTO form) {
    IllCase illCase = new IllCase();
    illCase.setCaseId(form.getCase_id());
    illCase.setFrontGraph(form.getFront_graph());
    illCase.setCaseName(form.getCase_title());
    illCase.setDiseaseId(form.getDisease_id());
    illCase.setDiagnosticInfo(form.getDiagnostic_result());
    illCase.setTreatmentInfo(form.getTreatment_info());
    illCase.setAdmissionText(form.getAdmission_text());
    return illCase;
  }

  public List<CaseBackBriefDTO> getAllCaseBackBriefDTOs() {
    try {
      return caseDao.getAllCaseBackBriefDTOs();
    } catch (Exception e) {
      logger.error(
          "[get Ill Case brief infos fail], error message: {}",
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public CaseBackDetailDTO getBackCaseDetailDTOByCaseId(Long caseId) {
    try {
      CaseBackDetailDTO caseBackDetailDTO = caseDao.getBackDetailDTO(caseId); // 先读基本类型属性和疾病属性
      caseBackDetailDTO.setAdmissionGraphList(
          caseDao.getFilesByIllCaseId("admission_graph", caseId));
      caseBackDetailDTO.setTreatmentGraphList(
          caseDao.getFilesByIllCaseId("treatment_graph", caseId));
      caseBackDetailDTO.setInspectionCaseList(
          inspectionCaseDao.getInspectionCaseBackDTOByIllCaseId(caseId));
      caseBackDetailDTO.setTreatmentVideoList(
          caseDao.getFilesByIllCaseId("treatment_video", caseId));
      return caseBackDetailDTO;
    } catch (Exception e) {
      logger.error(
          "[get Ill Case detailed info fail], caseId: {}, error message: {}",
          SerialUtil.toJsonStr(caseId),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  // 目前不需要这个方法
  public PageInfo<CaseBackBriefDTO> getCasePageInfo(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<CaseBackBriefDTO> CaseDTOList = caseDao.getAllCaseBackBriefDTOs();
    return new PageInfo<>(CaseDTOList);
  }

  public Disease addDisease(Disease disease) {
    try {
      diseaseDao.insert(disease);
      return disease;
    } catch (Exception e) {
      logger.error("[add disease fail], error message: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int deleteDisease(Long id) {
    try {
      // todo: 如果存在相关病例则不能删除
      return diseaseDao.deleteByPrimaryKey(id);
    } catch (Exception e) {
      logger.error(
          "[delete disease fail], diseaseId: {}, error message: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public int updateDisease(Disease disease) {
    try {
      return diseaseDao.updateByPrimaryKey(disease);
    } catch (Exception e) {
      logger.error(
          "[update disease fail], diseaseId: {}, error message: {}",
          SerialUtil.toJsonStr(disease.getDiseaseId()),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public Disease getDisease(Long id) {
    try {
      return diseaseDao.selectByPrimaryKey(id);
    } catch (Exception e) {
      logger.error(
          "[get disease fail], diseaseId: {}, error message: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public List<Disease> getAllDiseases() {
    try {
      return diseaseDao.selectAll();
    } catch (Exception e) {
      logger.error(
          "[get all diseases fail], error message: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public List<InspectionItemBackDTO> getAllInspectionItems() {
    return inspectionCaseDao.selectAllInspectionItems();
  }

  // 目前不需要这个方法，考虑删除
  public PageInfo<Disease> getDiseasePageInfo(Integer pageNum, Integer pageSize) {
    try {
      PageHelper.startPage(pageNum, pageSize);
      return new PageInfo<>(diseaseDao.selectAll());
    } catch (Exception e) {
      logger.error(
          "[get all diseases fail], error message: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }
}
