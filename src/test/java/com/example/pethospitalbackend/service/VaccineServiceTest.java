package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.VaccineDao;
import com.example.pethospitalbackend.entity.Vaccine;
import com.example.pethospitalbackend.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
    expectedResult.setResult(Collections.singletonList(vaccine));

    // Configure VaccineDao.selectAll(...).
    final Vaccine vaccine1 = new Vaccine();
    vaccine1.setId(0L);
    vaccine1.setName("name");
    vaccine1.setIntro("intro");
    final List<Vaccine> vaccineList = Collections.singletonList(vaccine1);
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

  @Test
  void testAddVaccine() {
    // Setup
    final Vaccine vaccine = new Vaccine();
    vaccine.setId(0L);
    vaccine.setName("name");
    vaccine.setIntro("intro");

    final Vaccine expectedResult = new Vaccine();
    expectedResult.setId(0L);
    expectedResult.setName("name");
    expectedResult.setIntro("intro");

    when(vaccineServiceUnderTest.vaccineDao.insert(any())).thenReturn(1);

    // Run the test
    final Vaccine result = vaccineServiceUnderTest.addVaccine(vaccine);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testDeleteVaccine() {
    // Setup
    when(vaccineServiceUnderTest.vaccineDao.deleteByPrimaryKey(0L)).thenReturn(1);

    // Run the test
    final int result = vaccineServiceUnderTest.deleteVaccine(0L);

    // Verify the results
    assertThat(result).isEqualTo(1);
  }

  @Test
  void testUpdateVaccine() {
    // Setup
    final Vaccine vaccine = new Vaccine();
    vaccine.setId(0L);
    vaccine.setName("name");
    vaccine.setIntro("intro");

    when(vaccineServiceUnderTest.vaccineDao.updateByPrimaryKeySelective(any())).thenReturn(1);

    // Run the test
    final int result = vaccineServiceUnderTest.updateVaccine(vaccine);

    // Verify the results
    assertThat(result).isEqualTo(1);
  }
}
