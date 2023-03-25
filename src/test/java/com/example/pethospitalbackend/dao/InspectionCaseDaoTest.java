package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.InspectionCaseBackDTO;
import com.example.pethospitalbackend.dto.InspectionItemBackDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InspectionCaseDaoTest {
    
    @Resource
    InspectionCaseDao inspectionCaseDao;
    
    @Test
    public void getInspectionItemByIdTest() {
        InspectionItemBackDTO inspectionItemBackDTO = inspectionCaseDao.getInspectionItemById(1);
        Assert.assertEquals(0, 0);
    }
    
    @Test
    public void getInspectionCaseByIllCaseIdTest() {
        List<InspectionCaseBackDTO> inspectionCaseBackDTOList = inspectionCaseDao.getInspectionCaseBackDTOByIllCaseId(1);
        Assert.assertEquals(0, 0);
    }
}
