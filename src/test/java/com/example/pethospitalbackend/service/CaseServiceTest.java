package com.example.pethospitalbackend.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CaseServiceTest {
    
    @Resource
    CaseService caseService;
    
    @Test
    public void testCaseGeneralDetailDTO() {
        // todo: 测试
        caseService.getBackCaseDTOByCaseId((long) 22);
        Assert.assertEquals(0, 0);
    }
}
