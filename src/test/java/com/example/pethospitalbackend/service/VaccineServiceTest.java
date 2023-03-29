package com.example.pethospitalbackend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.pethospitalbackend.dao.VaccineDao;
import com.example.pethospitalbackend.entity.Vaccine;
import com.example.pethospitalbackend.response.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VaccineServiceTest {

  private VaccineService vaccineServiceUnderTest;

  @BeforeEach
  void setUp() {
    vaccineServiceUnderTest = new VaccineService();
    vaccineServiceUnderTest.vaccineDao = mock(VaccineDao.class);
  }

  @Test
  void testGetAllVaccines() {
    // Setup
    final Response<List<Vaccine>> expectedResult = new Response<>();
    expectedResult.setSuccess(true);
    expectedResult.setStatus(200);
    expectedResult.setMessage("成功");
    final Vaccine vaccine = new Vaccine();
    vaccine.setId(0L);
    vaccine.setName("name");
    vaccine.setIntro("intro");
    expectedResult.setResult(Arrays.asList(vaccine));

    // Configure VaccineDao.selectAll(...).
    final Vaccine vaccine1 = new Vaccine();
    vaccine1.setId(0L);
    vaccine1.setName("name");
    vaccine1.setIntro("intro");
    final List<Vaccine> vaccineList = Arrays.asList(vaccine1);
    when(vaccineServiceUnderTest.vaccineDao.selectAll()).thenReturn(vaccineList);

    // Run the test
    final Response<List<Vaccine>> result = vaccineServiceUnderTest.getAllVaccines();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetAllVaccines_VaccineDaoReturnsNoItems() {
    // Setup
    final Response<List<Vaccine>> expectedResult = new Response<>();
    expectedResult.setSuccess(true);
    expectedResult.setStatus(200);
    expectedResult.setMessage("成功");

    expectedResult.setResult(Collections.emptyList());

    when(vaccineServiceUnderTest.vaccineDao.selectAll()).thenReturn(Collections.emptyList());

    // Run the test
    final Response<List<Vaccine>> result = vaccineServiceUnderTest.getAllVaccines();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }
}
