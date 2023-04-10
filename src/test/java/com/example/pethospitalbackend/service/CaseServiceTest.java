package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dao.CaseDao;
import com.example.pethospitalbackend.dao.DiseaseDao;
import com.example.pethospitalbackend.dao.InspectionCaseDao;
import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.IllCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CaseServiceTest extends BaseTest {

  @InjectMocks @Resource CaseService caseService;

  @MockBean(name = "caseDao")
  CaseDao caseDao;

  @MockBean(name = "diseaseDao")
  DiseaseDao diseaseDao;

  @MockBean(name = "inspectionCaseDao")
  InspectionCaseDao inspectionCaseDao;

  @Before
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testDeleteDisease() {
    // Setup
    when(caseService.diseaseDao.deleteByPrimaryKey(0L)).thenReturn(1);

    // Run the test
    final int result = caseService.deleteDisease(0L);

    // Verify the results
    assertEquals(1, result);
  }

  @Test
  public void testGetAllInspectionItems() {
    // Setup
    final InspectionItemBackDTO inspectionItemBackDTO = new InspectionItemBackDTO();
    inspectionItemBackDTO.setItemId(0L);
    inspectionItemBackDTO.setItemName("itemName");
    final List<InspectionItemBackDTO> expectedResult =
        Collections.singletonList(inspectionItemBackDTO);

    // Configure InspectionCaseDao.selectAllInspectionItems(...).
    final InspectionItemBackDTO inspectionItemBackDTO1 = new InspectionItemBackDTO();
    inspectionItemBackDTO1.setItemId(0L);
    inspectionItemBackDTO1.setItemName("itemName");
    final List<InspectionItemBackDTO> inspectionItemBackDTOS =
        Collections.singletonList(inspectionItemBackDTO1);
    when(inspectionCaseDao.selectAllInspectionItems()).thenReturn(inspectionItemBackDTOS);

    // Run the test
    final List<InspectionItemBackDTO> result = caseService.getAllInspectionItems();

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testGetDisease() {
    // Setup
    final Disease expectedResult = new Disease();
    expectedResult.setDiseaseId(0L);
    expectedResult.setDiseaseName("diseaseName");
    expectedResult.setTypeName("typeName");

    // Configure DiseaseDao.selectByPrimaryKey(...).
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    when(caseService.diseaseDao.selectByPrimaryKey(0L)).thenReturn(disease);

    // Run the test
    final Disease result = caseService.getDisease(0L);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testGetAllDiseases() {
    // Setup
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    final List<Disease> expectedResult = Arrays.asList(disease);

    // Configure DiseaseDao.selectAll(...).
    final Disease disease1 = new Disease();
    disease1.setDiseaseId(0L);
    disease1.setDiseaseName("diseaseName");
    disease1.setTypeName("typeName");
    final List<Disease> diseases = Arrays.asList(disease1);
    when(caseService.diseaseDao.selectAll()).thenReturn(diseases);

    // Run the test
    final List<Disease> result = caseService.getAllDiseases();

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testAddDisease() {
    // Setup
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");

    final Disease expectedResult = new Disease();
    expectedResult.setDiseaseId(0L);
    expectedResult.setDiseaseName("diseaseName");
    expectedResult.setTypeName("typeName");

    when(caseService.diseaseDao.insert(any())).thenReturn(1);

    // Run the test
    final Disease result = caseService.addDisease(disease);

    // Verify the results
    assertEquals(expectedResult, result);
    verify(caseService.diseaseDao).insert(disease);
  }

  @Test
  public void testUpdateDisease() {
    // Setup
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");

    when(caseService.diseaseDao.updateByPrimaryKey(any())).thenReturn(1);

    // Run the test
    final int result = caseService.updateDisease(disease);

    // Verify the results
    assertEquals(1, result);
  }

  @Test
  public void testAddCase() {
    // Setup
    final IllCaseFormDTO form = new IllCaseFormDTO();
    form.setCase_id(0L);
    form.setFront_graph("frontGraph");
    form.setCase_title("caseName");
    form.setDisease_id(0L);
    form.setAdmission_text("admissionText");
    form.setAdmission_graphs(Collections.singletonList("value"));
    final InspectionCaseFrontDTO inspectionCaseFrontDTO = new InspectionCaseFrontDTO();
    inspectionCaseFrontDTO.setInspection_item_id(0L);
    inspectionCaseFrontDTO.setInspection_result_text("inspection_result_text");
    inspectionCaseFrontDTO.setInspection_graphs(Collections.singletonList("value"));
    form.setInspection_cases(Collections.singletonList(inspectionCaseFrontDTO));
    form.setDiagnostic_result("diagnosticInfo");
    form.setTreatment_info("treatmentInfo");
    form.setTherapy_graphs(Collections.singletonList("value"));

    final IllCase expectedResult = new IllCase();
    expectedResult.setCaseId(0L);
    expectedResult.setCaseName("caseName");
    expectedResult.setDiseaseId(0L);
    expectedResult.setAdmissionText("admissionText");
    expectedResult.setDiagnosticInfo("diagnosticInfo");
    expectedResult.setTreatmentInfo("treatmentInfo");
    expectedResult.setFrontGraph("frontGraph");

    when(caseService.caseDao.insert(any())).thenReturn(1);
    when(caseService.caseDao.insertFiles(Collections.singletonList(any()), any())).thenReturn(1);
    when(caseService.inspectionCaseDao.insert(any())).thenReturn(1);
    when(caseService.caseDao.insertInspectionGraphs(Collections.singletonList(any())))
        .thenReturn(1);

    // Run the test
    final IllCase result = caseService.addCase(form);

    // Verify the results
    assertEquals(expectedResult, result);
    verify(caseService.caseDao).insert(expectedResult);
  }

  @Test
  public void testDeleteCase() {
    // Setup
    when(caseService.inspectionCaseDao.selectAllInspectionCaseIdByIllCaseId(0L))
        .thenReturn(Collections.singletonList(0L));
    when(caseService.inspectionCaseDao.deleteByExample(any(Object.class))).thenReturn(0);
    when(caseService.inspectionCaseDao.deleteInspectionGraphsByInspectionCaseId(Arrays.asList(0L)))
        .thenReturn(0);
    when(caseService.caseDao.deleteFilesByIllCaseId(anyString(), anyLong())).thenReturn(0);
    when(caseService.caseDao.deleteByPrimaryKey(any())).thenReturn(0);

    // Run the test
    final int result = caseService.deleteCase(0L);

    // Verify the results
    assertEquals(0, result);
  }

  @Test
  public void testUpdateCase() {
    // Setup
    final IllCaseFormDTO formDTO = new IllCaseFormDTO();
    formDTO.setCase_id(0L);
    formDTO.setFront_graph("front_graph");
    formDTO.setCase_title("case_title");
    formDTO.setDisease_id(0L);
    formDTO.setAdmission_text("admission_text");
    formDTO.setAdmission_graphs(Collections.singletonList("value"));
    final InspectionCaseFrontDTO inspectionCaseFrontDTO = new InspectionCaseFrontDTO();
    inspectionCaseFrontDTO.setInspection_item_id(0L);
    inspectionCaseFrontDTO.setInspection_result_text("inspection_result_text");
    inspectionCaseFrontDTO.setInspection_graphs(Collections.singletonList("value"));
    formDTO.setInspection_cases(Collections.singletonList(inspectionCaseFrontDTO));
    formDTO.setDiagnostic_result("diagnostic_result");
    formDTO.setTreatment_info("therapy_text");
    formDTO.setTherapy_graphs(Collections.singletonList("value"));

    when(caseService.inspectionCaseDao.selectAllInspectionCaseIdByIllCaseId(0L))
        .thenReturn(Collections.singletonList(0L));
    when(caseService.inspectionCaseDao.deleteByExample(any(Object.class))).thenReturn(1);
    when(caseService.inspectionCaseDao.deleteInspectionGraphsByInspectionCaseId(Arrays.asList(0L)))
        .thenReturn(1);
    when(caseService.caseDao.deleteFilesByIllCaseId(anyString(), anyLong())).thenReturn(1);
    when(caseService.caseDao.deleteByPrimaryKey(any())).thenReturn(1);
    when(caseService.caseDao.insert(any())).thenReturn(1);
    when(caseService.caseDao.insertFiles(anyList(), anyString())).thenReturn(1);
    when(caseService.inspectionCaseDao.insert(any())).thenReturn(1);
    when(caseService.caseDao.insertInspectionGraphs(anyList())).thenReturn(1);

    // Run the test
    final int result = caseService.updateCase(formDTO);

    // Verify the results
    assertEquals(1, result);
  }

  @Test
  public void testGetAllCaseBackBriefDTOs() {
    // Setup
    final CaseBackBriefDTO caseBackBriefDTO = new CaseBackBriefDTO();
    caseBackBriefDTO.setIllCaseId(0L);
    caseBackBriefDTO.setIllCaseName("illCaseName");
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    caseBackBriefDTO.setDisease(disease);
    final List<CaseBackBriefDTO> expectedResult = Collections.singletonList(caseBackBriefDTO);

    // Configure CaseDao.getAllCaseBackBriefDTOs(...).
    final CaseBackBriefDTO caseBackBriefDTO1 = new CaseBackBriefDTO();
    caseBackBriefDTO1.setIllCaseId(0L);
    caseBackBriefDTO1.setIllCaseName("illCaseName");
    final Disease disease1 = new Disease();
    disease1.setDiseaseId(0L);
    disease1.setDiseaseName("diseaseName");
    disease1.setTypeName("typeName");
    caseBackBriefDTO1.setDisease(disease1);
    final List<CaseBackBriefDTO> caseBackBriefDTOS = Collections.singletonList(caseBackBriefDTO1);
    when(caseService.caseDao.getAllCaseBackBriefDTOs()).thenReturn(caseBackBriefDTOS);

    // Run the test
    final List<CaseBackBriefDTO> result = caseService.getAllCaseBackBriefDTOs();

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testGetBackCaseDetailDTOByCaseId() {
    // Setup
    final CaseBackDetailDTO expectedResult = new CaseBackDetailDTO();
    expectedResult.setCaseId(0L);
    expectedResult.setCaseName("caseName");
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    expectedResult.setDisease(disease);
    expectedResult.setAdmissionText("admissionText");
    expectedResult.setFrontGraph("frontGraph");
    final FileDTO fileDTO = new FileDTO();
    fileDTO.setFileId(0L);
    fileDTO.setCaseId(0L);
    fileDTO.setSortNum(0L);
    fileDTO.setUrl("url");
    expectedResult.setAdmissionGraphList(Collections.singletonList(fileDTO));
    final InspectionCaseBackDTO inspectionCaseBackDTO = new InspectionCaseBackDTO();
    inspectionCaseBackDTO.setInspectionCaseId(0L);
    final InspectionItemBackDTO inspectionItem = new InspectionItemBackDTO();
    inspectionItem.setItemId(0L);
    inspectionItem.setItemName("itemName");
    inspectionCaseBackDTO.setInspectionItem(inspectionItem);
    inspectionCaseBackDTO.setResult("result");
    final FileDTO fileDTO1 = new FileDTO();
    fileDTO1.setFileId(0L);
    fileDTO1.setCaseId(0L);
    fileDTO1.setSortNum(0L);
    fileDTO1.setUrl("url");
    inspectionCaseBackDTO.setInspectionGraphs(Collections.singletonList(fileDTO1));
    expectedResult.setInspectionCaseList(Collections.singletonList(inspectionCaseBackDTO));
    expectedResult.setDiagnosticInfo("diagnosticInfo");
    expectedResult.setTreatmentInfo("treatmentInfo");
    final FileDTO fileDTO2 = new FileDTO();
    fileDTO2.setFileId(0L);
    fileDTO2.setCaseId(0L);
    fileDTO2.setSortNum(0L);
    fileDTO2.setUrl("url");
    expectedResult.setTreatmentGraphList(Collections.singletonList(fileDTO2));
    expectedResult.setTreatmentVideoList(Collections.singletonList(fileDTO));
    // Configure CaseDao.getBackDetailDTO(...).
    final CaseBackDetailDTO caseBackDetailDTO = new CaseBackDetailDTO();
    caseBackDetailDTO.setCaseId(0L);
    caseBackDetailDTO.setCaseName("caseName");
    final Disease disease1 = new Disease();
    disease1.setDiseaseId(0L);
    disease1.setDiseaseName("diseaseName");
    disease1.setTypeName("typeName");
    caseBackDetailDTO.setDisease(disease1);
    caseBackDetailDTO.setAdmissionText("admissionText");
    caseBackDetailDTO.setFrontGraph("frontGraph");
    final FileDTO fileDTO3 = new FileDTO();
    fileDTO3.setFileId(0L);
    fileDTO3.setCaseId(0L);
    fileDTO3.setSortNum(0L);
    fileDTO3.setUrl("url");
    caseBackDetailDTO.setAdmissionGraphList(Collections.singletonList(fileDTO3));
    caseBackDetailDTO.setTreatmentVideoList(Collections.singletonList(fileDTO3));
    final InspectionCaseBackDTO inspectionCaseBackDTO1 = new InspectionCaseBackDTO();
    inspectionCaseBackDTO1.setInspectionCaseId(0L);
    final InspectionItemBackDTO inspectionItem1 = new InspectionItemBackDTO();
    inspectionItem1.setItemId(0L);
    inspectionItem1.setItemName("itemName");
    inspectionCaseBackDTO1.setInspectionItem(inspectionItem1);
    inspectionCaseBackDTO1.setResult("result");
    final FileDTO fileDTO4 = new FileDTO();
    fileDTO4.setFileId(0L);
    fileDTO4.setCaseId(0L);
    fileDTO4.setSortNum(0L);
    fileDTO4.setUrl("url");
    inspectionCaseBackDTO1.setInspectionGraphs(Collections.singletonList(fileDTO4));
    caseBackDetailDTO.setInspectionCaseList(Collections.singletonList(inspectionCaseBackDTO1));
    caseBackDetailDTO.setDiagnosticInfo("diagnosticInfo");
    caseBackDetailDTO.setTreatmentInfo("treatmentInfo");
    final FileDTO fileDTO5 = new FileDTO();
    fileDTO5.setFileId(0L);
    fileDTO5.setCaseId(0L);
    fileDTO5.setSortNum(0L);
    fileDTO5.setUrl("url");
    caseBackDetailDTO.setTreatmentGraphList(Collections.singletonList(fileDTO5));
    when(caseService.caseDao.getFilesByIllCaseId(eq("treatment_graph"), anyLong()))
        .thenReturn(Collections.singletonList(fileDTO2));
    when(caseService.caseDao.getFilesByIllCaseId(eq("admission_graph"), anyLong()))
        .thenReturn(Collections.singletonList(fileDTO));
    when(caseService.caseDao.getFilesByIllCaseId(eq("treatment_video"), anyLong()))
        .thenReturn(Collections.singletonList(fileDTO3));

    when(caseService.caseDao.getBackDetailDTO(0L)).thenReturn(caseBackDetailDTO);

    // Configure CaseDao.getFilesByIllCaseId(...).
    final FileDTO fileDTO6 = new FileDTO();
    fileDTO6.setFileId(0L);
    fileDTO6.setCaseId(0L);
    fileDTO6.setSortNum(0L);
    fileDTO6.setUrl("url");
    final List<FileDTO> fileDTOS = Collections.singletonList(fileDTO6);

    // Configure InspectionCaseDao.getInspectionCaseBackDTOByIllCaseId(...).
    final InspectionCaseBackDTO inspectionCaseBackDTO2 = new InspectionCaseBackDTO();
    inspectionCaseBackDTO2.setInspectionCaseId(0L);
    final InspectionItemBackDTO inspectionItem2 = new InspectionItemBackDTO();
    inspectionItem2.setItemId(0L);
    inspectionItem2.setItemName("itemName");
    inspectionCaseBackDTO2.setInspectionItem(inspectionItem2);
    inspectionCaseBackDTO2.setResult("result");
    final FileDTO fileDTO7 = new FileDTO();
    fileDTO7.setFileId(0L);
    fileDTO7.setCaseId(0L);
    fileDTO7.setSortNum(0L);
    fileDTO7.setUrl("url");
    inspectionCaseBackDTO2.setInspectionGraphs(Collections.singletonList(fileDTO7));
    final List<InspectionCaseBackDTO> inspectionCaseBackDTOS =
        Collections.singletonList(inspectionCaseBackDTO2);
    when(caseService.inspectionCaseDao.getInspectionCaseBackDTOByIllCaseId(0L))
        .thenReturn(inspectionCaseBackDTOS);

    // Run the test
    final CaseBackDetailDTO result = caseService.getBackCaseDetailDTOByCaseId(0L);

    // Verify the results
    assertEquals(expectedResult, result);
  }
}
