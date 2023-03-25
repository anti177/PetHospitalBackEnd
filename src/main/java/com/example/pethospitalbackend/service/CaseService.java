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
    
    public Response<CaseDTO> getCaseByCaseId(Long caseId) {
        Response<CaseDTO> response = new Response<>();
        CaseDTO caseDTO = new CaseDTO();
        try {
            IllCase illCase = caseDao.getCaseByCaseId(caseId);
            if (illCase != null) {
                caseDTO.setCaseId(illCase.getCaseId());
                caseDTO.setCaseName(illCase.getCaseName());
                caseDTO.setAdmissionText(illCase.getAdmissionText());
                caseDTO.setDiagnosticInfo(illCase.getDiagnosticInfo());
                caseDTO.setTreatmentInfo(illCase.getTreatmentInfo());
                
                List<String> admissionGraphs = caseDao.getAdmissionGraphByCaseId(caseId);
                if (admissionGraphs != null) caseDTO.setAdmissionGraphList(admissionGraphs);
                List<String> treatmentGraphs = caseDao.getTreatmentGraphByCaseId(caseId);
                if (treatmentGraphs != null) caseDTO.setTreatmentGraphList(treatmentGraphs);
                List<String> treatmentVideos = caseDao.getTreatmentVideoByCaseId(caseId);
                if (treatmentVideos != null) caseDTO.setTreatmentVideoList(treatmentVideos);
                
                
                List<InspectionDTO> inspectionDTOList = inspectionCaseDao.getInspectionCaseByCaseId(caseId);
                if (inspectionDTOList != null) {
                    for (int i = 0; i < inspectionDTOList.size(); i++) {
                        InspectionDTO a = inspectionDTOList.get(i);
                        List<String> inspectionGraphs = inspectionCaseDao.getInspectionGraphByInspectionCaseId(a.getInspectionCaseId());
                        if (inspectionGraphs != null) {
                            a.setInspectionGraphList(inspectionGraphs);
                            inspectionDTOList.set(i, a);
                        }
                        
                    }
                    caseDTO.setInspectionDTOList(inspectionDTOList);
                }
            }
        } catch (Exception e) {
            logger.error("[getCaseByCaseId Fail],caseId:{}, error message{}", SerialUtil.toJsonStr(caseId), SerialUtil.toJsonStr(e.getMessage()));
            throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
        }
        response.setSuc(caseDTO);
        return response;
        
    }
    
    public PageInfo<CaseBackEndDTO> getCasePageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CaseBackEndDTO> CaseDTOList = caseDao.getAllBackEndDtos();
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
        PageInfo<Disease> diseasePageInfo = new PageInfo<>(diseaseList);
        return diseasePageInfo;
    }
    
    public int updateCase(Long id) {
        return 0;
    }
    
    public List<CaseBackEndDTO> getAllCaseDTOs() {
        return caseDao.getAllBackEndDtos();
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void addService(IllCaseFormDTO form) {
    
    }
}
