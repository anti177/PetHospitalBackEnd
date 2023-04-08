package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.DepartmentDao;
import com.example.pethospitalbackend.entity.Department;
import com.example.pethospitalbackend.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DepartmentServiceTest {

  private DepartmentService departmentServiceUnderTest;

  @BeforeEach
  void setUp() {
    departmentServiceUnderTest = new DepartmentService();
    departmentServiceUnderTest.departmentDao = mock(DepartmentDao.class);
  }

  @Test
  void testGetAllDepartmentInfo() {
    // Setup
    final Response<List<Department>> expectedResult = new Response<>();
    expectedResult.setSuccess(true);
    expectedResult.setStatus(200);
    expectedResult.setMessage("成功");
    final Department department = new Department();
    department.setDepartmentId(0L);
    department.setDepartmentName("departmentName");
    department.setIntro("intro");
    department.setPeopleList("peopleList");
    expectedResult.setResult(Arrays.asList(department));

    // Configure DepartmentDao.selectAll(...).
    final Department department1 = new Department();
    department1.setDepartmentId(0L);
    department1.setDepartmentName("departmentName");
    department1.setIntro("intro");
    department1.setPeopleList("peopleList");
    final List<Department> departments = Arrays.asList(department1);
    when(departmentServiceUnderTest.departmentDao.selectAll()).thenReturn(departments);

    // Run the test
    final Response<List<Department>> result = departmentServiceUnderTest.getAllDepartmentInfo();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetAllDepartmentInfo_DepartmentDaoReturnsNoItems() {
    // Setup
    final Response<List<Department>> expectedResult = new Response<>();
    expectedResult.setSuccess(true);
    expectedResult.setStatus(200);
    expectedResult.setMessage("成功");
    expectedResult.setResult(Collections.emptyList());

    when(departmentServiceUnderTest.departmentDao.selectAll()).thenReturn(Collections.emptyList());

    // Run the test
    final Response<List<Department>> result = departmentServiceUnderTest.getAllDepartmentInfo();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }
}
