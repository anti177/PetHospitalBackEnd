package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dao.CaseDao;
import com.example.pethospitalbackend.dao.DiseaseDao;
import com.example.pethospitalbackend.dao.InspectionCaseDao;
import com.example.pethospitalbackend.dto.InspectionItemBackDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
}
