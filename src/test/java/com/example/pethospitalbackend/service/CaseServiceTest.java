package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dto.CaseBackDetailDTO;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class CaseServiceTest extends BaseTest {

  @Resource CaseService caseService;

  @Test
  public void testCaseGeneralDetailDTO() {
    // todo: 测试
    CaseBackDetailDTO caseBackDetailDTO = caseService.getBackCaseDetailDTOByCaseId(1L);
    Assert.assertEquals(0, 0);
  }
}
