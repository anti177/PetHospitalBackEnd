package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.IllCaseDao;
import com.example.pethospitalbackend.dto.IllCaseDTO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 病例服务
 *
 * @author zjy19
 */
@Service
public class IllCaseService {
    
    @Resource
    private IllCaseDao caseDao;
    
    
    public PageInfo<IllCaseDTO> getCasePageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<IllCaseDTO> IllCaseDTOList = caseDao.findAll();
        return new PageInfo<>(IllCaseDTOList);
    }
    
    // 考虑批量删除？
    public int deleteCase(long id) {
        // todo: 关联删除
        return caseDao.deleteByPrimaryKey(id);
    }
    
    //todo: 增改查
}
