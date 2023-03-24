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
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CaseService {
    
    private static final Logger logger = LoggerFactory.getLogger(CaseService.class);
    
    @Resource
    DiseaseDao diseaseDao;
    
    @Resource
    CaseDao caseDao;
    
    @Resource
    InspectionCaseDao inspectionCaseDao;
    
    public Response<List<CategoryDTO>> getTotalCategory() {
        Response<List<CategoryDTO>> response = new Response<>();
        List<String> typeList;
        List<Disease> diseaseList;
        try {
            typeList = diseaseDao.getAllType();
            diseaseList = diseaseDao.getAllDisease();
        } catch (Exception e) {
            logger.error("[getTotalCategory Fail], error message{}", SerialUtil.toJsonStr(e.getMessage()));
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
            logger.error("[getCaseCategory Fail], diseaseId : {},error message{}", SerialUtil.toJsonStr(diseaseId), SerialUtil.toJsonStr(e.getMessage()));
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
                if (admissionGraphs != null) caseFrontDetailDTO.setAdmissionGraphList(admissionGraphs);
                List<String> treatmentGraphs = caseDao.getTreatmentGraphByCaseId(caseId);
                if (treatmentGraphs != null) caseFrontDetailDTO.setTreatmentGraphList(treatmentGraphs);
                List<String> treatmentVideos = caseDao.getTreatmentVideoByCaseId(caseId);
                if (treatmentVideos != null) caseFrontDetailDTO.setTreatmentVideoList(treatmentVideos);
                
                List<InspectionFrontDTO> inspectionFrontDTOList = inspectionCaseDao.getInspectionCaseByCaseId(caseId);
                if (inspectionFrontDTOList != null) {
                    for (int i = 0; i < inspectionFrontDTOList.size(); i++) {
                        InspectionFrontDTO a = inspectionFrontDTOList.get(i);
                        List<String> inspectionGraphs = inspectionCaseDao.getInspectionGraphByInspectionCaseId(a.getInspectionCaseId());
                        if (inspectionGraphs != null) {
                            a.setInspectionGraphList(inspectionGraphs);
                            inspectionFrontDTOList.set(i, a);
                        }
                        
                    }
                    caseFrontDetailDTO.setInspectionFrontDTOList(inspectionFrontDTOList);
                }
            }
        } catch (Exception e) {
            logger.error("[getCaseByCaseId Fail],caseId:{}, error message{}", SerialUtil.toJsonStr(caseId), SerialUtil.toJsonStr(e.getMessage()));
            throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
        }
        response.setSuc(caseFrontDetailDTO);
        return response;
        
    }
    
    public PageInfo<CaseBackBriefDTO> getCasePageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CaseBackBriefDTO> CaseDTOList = caseDao.getAllBackBriefDTOs();
        return new PageInfo<>(CaseDTOList);
    }
    
    // todo: 考虑级联删除？
    @Transactional(rollbackFor = Exception.class)
    public int deleteCase(long id) {
        try {
            int res = caseDao.deleteByPrimaryKey(id);
            Example.Criteria inspectionCaseExample = new Example(InspectionCase.class).createCriteria().andEqualTo("case_id", id);
            inspectionCaseDao.deleteByExample(inspectionCaseExample);
            
            return res;
        } catch (Exception e) {
            logger.error("[delete ill case fail], caseId : {},error message{}", SerialUtil.toJsonStr(id), SerialUtil.toJsonStr(e.getMessage()));
            throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public int deleteCases(List<Long> ids) {
        return 0; // todo: 多选删除
    }
    
    //todo: 增改病例
    
    
    @Transactional
    public int deleteDisease(Long id) {
        try {
            int res = diseaseDao.deleteByPrimaryKey(id); //todo: 关联删除
            deleteCase(id);
            return res;
        } catch (Exception e) {
            logger.error("[delete disease fail], diseaseId : {},error message{}", SerialUtil.toJsonStr(id), SerialUtil.toJsonStr(e.getMessage()));
            throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
        }
    }
    
    public int updateDisease(Disease disease) {
        try {
            int res = diseaseDao.updateByPrimaryKey(disease);
            return res;
        } catch (Exception e) {
            logger.error("[update disease fail], diseaseId : {},error message{}", SerialUtil.toJsonStr(disease.getDiseaseId()), SerialUtil.toJsonStr(e.getMessage()));
            throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
        }
    }
    
    public Disease addDisease(Disease disease) {
        diseaseDao.insert(disease);
        return disease;
    }
    
    public Disease getDisease(Long id) {
        return diseaseDao.selectByPrimaryKey(id);
    }
    
    public PageInfo<Disease> getDiseasePageInfo(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Disease> diseaseList = diseaseDao.selectAll();
        return new PageInfo<>(diseaseList);
    }
    
    public int updateCase(Long id) {
        return 0;
    }
    
    public List<CaseBackBriefDTO> getAllCaseDTOs() {
        return caseDao.getAllBackBriefDTOs();
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void addCase(IllCaseFormDTO form) {
        //todo: 编写逻辑
    }
    
    //todo 测试
    public CaseBackDetailDTO getBackCaseDTOByCaseId(Long caseId) {
        CaseBackDetailDTO caseBackDetailDTO = caseDao.getBackDetailDTO(caseId);
        
        caseBackDetailDTO.setAdmissionGraphList(caseDao.getFileByIllCaseId("admission_graph", caseId));
        caseBackDetailDTO.setTreatmentGraphList(caseDao.getFileByIllCaseId("treatment_graph", caseId));
        caseBackDetailDTO.setInspectionCaseList(inspectionCaseDao.getInspectionCaseBackDTOByCaseId(caseId));
        caseBackDetailDTO.setTreatmentVideoList(caseDao.getFileByIllCaseId("treatment_video", caseId));
        
        return null;
    }
}
