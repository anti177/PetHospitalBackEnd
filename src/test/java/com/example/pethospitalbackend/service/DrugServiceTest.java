package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.DrugDao;
import com.example.pethospitalbackend.entity.Drug;
import com.example.pethospitalbackend.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DrugServiceTest {

  private DrugService drugServiceUnderTest;

  @BeforeEach
  void setUp() {
    drugServiceUnderTest = new DrugService();
    drugServiceUnderTest.drugDao = mock(DrugDao.class);
    drugServiceUnderTest.fileService = mock(FileService.class);
  }

  @Test
  void testGetAllDrugs() {
    // Setup
    final Response<List<Drug>> expectedResult = new Response<>();
    expectedResult.setSuccess(true);
    expectedResult.setStatus(200);
    expectedResult.setMessage("成功");
    final Drug drug = new Drug();
    drug.setId(0L);
    drug.setName("name");
    drug.setType("type");
    drug.setIntro("intro");
    drug.setPrice(0.0);
    drug.setUrl("url");
    expectedResult.setResult(Collections.singletonList(drug));

    // Configure DrugDao.selectAll(...).
    final Drug drug1 = new Drug();
    drug1.setId(0L);
    drug1.setName("name");
    drug1.setType("type");
    drug1.setIntro("intro");
    drug1.setPrice(0.0);
    drug1.setUrl("url");
    final List<Drug> drugs = Collections.singletonList(drug1);
    when(drugServiceUnderTest.drugDao.selectAll()).thenReturn(drugs);

    // Run the test
    final Response<List<Drug>> result = drugServiceUnderTest.getAllDrugs();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetAllDrugs_DrugDaoReturnsNoItems() {
    // Setup
    final Response<List<Drug>> expectedResult = new Response<>();
    expectedResult.setSuccess(true);
    expectedResult.setStatus(200);
    expectedResult.setMessage("成功");
    expectedResult.setResult(Collections.emptyList());

    when(drugServiceUnderTest.drugDao.selectAll()).thenReturn(Collections.emptyList());

    // Run the test
    final Response<List<Drug>> result = drugServiceUnderTest.getAllDrugs();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testAddDrug() {
    // Setup
    final Drug drug = new Drug();
    drug.setId(0L);
    drug.setName("name");
    drug.setType("type");
    drug.setIntro("intro");
    drug.setPrice(0.0);
    drug.setUrl("url");

    final Drug expectedResult = new Drug();
    expectedResult.setId(0L);
    expectedResult.setName("name");
    expectedResult.setType("type");
    expectedResult.setIntro("intro");
    expectedResult.setPrice(0.0);
    expectedResult.setUrl("url");

    when(drugServiceUnderTest.fileService.updateFileState(anyString(), anyBoolean())).thenReturn(1);
    when(drugServiceUnderTest.drugDao.insert(drug)).thenReturn(1);

    // Run the test
    final Drug result = drugServiceUnderTest.addDrug(drug);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
    verify(drugServiceUnderTest.fileService).updateFileState("url", true);
    verify(drugServiceUnderTest.drugDao).insert(any());
  }

  @Test
  void testDeleteDrug() {
    // Setup
    // Configure DrugDao.selectByPrimaryKey(...).
    final Drug drug = new Drug();
    drug.setId(0L);
    drug.setName("name");
    drug.setType("type");
    drug.setIntro("intro");
    drug.setPrice(0.0);
    drug.setUrl("url");
    when(drugServiceUnderTest.drugDao.selectByPrimaryKey(0L)).thenReturn(drug);

    when(drugServiceUnderTest.drugDao.deleteByPrimaryKey(0L)).thenReturn(1);
    when(drugServiceUnderTest.fileService.updateFileState(anyString(), anyBoolean())).thenReturn(1);

    // Run the test
    final int result = drugServiceUnderTest.deleteDrug(0L);

    // Verify the results
    assertThat(result).isEqualTo(1);
    verify(drugServiceUnderTest.fileService).updateFileState("url", false);
  }

  @Test
  void testUpdateDrug() {
    // Setup
    final Drug drug = new Drug();
    drug.setId(0L);
    drug.setName("name");
    drug.setType("type");
    drug.setIntro("intro");
    drug.setPrice(0.0);
    drug.setUrl("url");

    // Configure DrugDao.selectByPrimaryKey(...).
    final Drug drug1 = new Drug();
    drug1.setId(0L);
    drug1.setName("name");
    drug1.setType("type");
    drug1.setIntro("intro");
    drug1.setPrice(0.0);
    drug1.setUrl("url");
    when(drugServiceUnderTest.drugDao.selectByPrimaryKey(0L)).thenReturn(drug1);

    when(drugServiceUnderTest.fileService.updateFileState(anyString(), anyBoolean())).thenReturn(1);
    when(drugServiceUnderTest.drugDao.updateByPrimaryKey(any())).thenReturn(1);

    // Run the test
    final int result = drugServiceUnderTest.updateDrug(drug);

    // Verify the results
    assertThat(result).isEqualTo(1);
    verify(drugServiceUnderTest.fileService).updateFileState("url", false);
  }
}
