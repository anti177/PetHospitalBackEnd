package com.example.pethospitalbackend.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InspectionCaseDaoTest {

  @Resource InspectionCaseDao inspectionCaseDao;

  @Test
  void getInspectionGraphDTOByInspectionCaseId() {}

  @Test
  void getInspectionItemById() {}

  @Test
  void getInspectionCaseBackDTOByIllCaseId() {}

  @Test
  void deleteInspectionGraphsByInspectionCaseId() {}

  @Test
  void selectAllInspectionCaseIdByIllCaseId() {}

  @Test
  void selectAllInspectionItems() {}
}
