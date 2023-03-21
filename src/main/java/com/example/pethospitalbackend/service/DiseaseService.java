package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.DiseaseDao;
import com.example.pethospitalbackend.entity.Disease;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DiseaseService {
    
    @Resource
    DiseaseDao diseaseDao;
    
    public Disease addDisease(Disease disease) {
        diseaseDao.insert(disease);
        return disease;
    }
    
    public int updateDisease(Disease disease) {
        return diseaseDao.updateByPrimaryKey(disease);
    }
    
    public int deleteDisease(Long id) {
        return diseaseDao.deleteByPrimaryKey(id);
    }
    
    public PageInfo<Disease> getDiseasePageInfo(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Disease> diseaseList = diseaseDao.selectAll();
        PageInfo<Disease> diseasePageInfo = new PageInfo<>(diseaseList);
        return diseasePageInfo;
    }
}
