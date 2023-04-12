package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dao.CaseDao;
import com.example.pethospitalbackend.dao.DiseaseDao;
import com.example.pethospitalbackend.dao.InspectionCaseDao;
import com.example.pethospitalbackend.dto.CaseBackBriefDTO;
import com.example.pethospitalbackend.dto.CaseBackFormDTO;
import com.example.pethospitalbackend.dto.InspectionCaseFrontDTO;
import com.example.pethospitalbackend.dto.InspectionItemBackDTO;
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
    final CaseBackFormDTO form = new CaseBackFormDTO();
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
    final CaseBackFormDTO formDTO = new CaseBackFormDTO();
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
}
