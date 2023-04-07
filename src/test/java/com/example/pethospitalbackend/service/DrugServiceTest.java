package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.DrugDao;
import com.example.pethospitalbackend.entity.Drug;
import com.example.pethospitalbackend.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DrugServiceTest {

  private DrugService drugServiceUnderTest;

  @BeforeEach
  void setUp() {
    drugServiceUnderTest = new DrugService();
    drugServiceUnderTest.drugDao = mock(DrugDao.class);
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
    expectedResult.setResult(Arrays.asList(drug));

    // Configure DrugDao.selectAll(...).
    final Drug drug1 = new Drug();
    drug1.setId(0L);
    drug1.setName("name");
    drug1.setType("type");
    drug1.setIntro("intro");
    drug1.setPrice(0.0);
    drug1.setUrl("url");
    final List<Drug> drugs = Arrays.asList(drug1);
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
}
