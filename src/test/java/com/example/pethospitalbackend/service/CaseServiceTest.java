package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dao.CaseDao;
import com.example.pethospitalbackend.dao.DiseaseDao;
import com.example.pethospitalbackend.dao.InspectionCaseDao;
import com.example.pethospitalbackend.dto.InspectionItemBackDTO;
import com.example.pethospitalbackend.entity.Disease;
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
import static org.mockito.ArgumentMatchers.any;
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

  // todo: 补充测试
}
