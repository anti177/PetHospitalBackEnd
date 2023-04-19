package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.DrugDao;
import com.example.pethospitalbackend.entity.Drug;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.util.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DrugService {
  private static final Logger logger = LoggerFactory.getLogger(DrugService.class);

  @Resource DrugDao drugDao;
  @Resource FileService fileService;

  public Response<List<Drug>> getAllDrugs() {
    Response<List<Drug>> response = new Response<>();
    List<Drug> drugList;
    try {
      drugList = drugDao.selectAll();
    } catch (Exception e) {
      logger.error("[getAllDrug Fail], error message{}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
    response.setSuc(drugList);
    return response;
  }

  public Drug addDrug(Drug drug) {
    try {
      fileService.updateFileState(drug.getUrl(), true);
      drugDao.insert(drug);
      return drug;
    } catch (Exception e) {
      logger.error("[insert drug Fail], error message: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int deleteDrug(Long id) {
    try {
      String fileUrl = drugDao.selectByPrimaryKey(id).getUrl();
      int res = drugDao.deleteByPrimaryKey(id);
      fileService.updateFileState(fileUrl, false);
      return res;
    } catch (Exception e) {
      logger.error(
          "[delete drug Fail], drugId: {}, error message: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int updateDrug(Drug drug) {
    try {
      String originalUrl = drugDao.selectByPrimaryKey(drug.getId()).getUrl();
      fileService.updateFileState(originalUrl, false);
      fileService.updateFileState(drug.getUrl(), true);
      return drugDao.updateByPrimaryKey(drug);
    } catch (Exception e) {
      logger.error(
          "[update drug Fail], drugId: {}, error message: {}",
          SerialUtil.toJsonStr(drug.getId()),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }
}
