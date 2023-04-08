package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.VaccineDao;
import com.example.pethospitalbackend.entity.Vaccine;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.util.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VaccineService {
  private static final Logger logger = LoggerFactory.getLogger(VaccineService.class);

  @Resource VaccineDao vaccineDao;

  public Response<List<Vaccine>> getAllVaccines() {
    Response<List<Vaccine>> response = new Response<>();
    List<Vaccine> vaccineList;
    try {
      vaccineList = vaccineDao.selectAll();
    } catch (Exception e) {
      logger.error("[getAllVaccines Fail], error message{}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
    response.setSuc(vaccineList);
    return response;
  }

  public Vaccine addVaccine(Vaccine vaccine) {
    try {
      vaccineDao.insert(vaccine);
      return vaccine;
    } catch (Exception e) {
      logger.error(
          "[insert vaccine Fail], error message: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public int deleteVaccine(Long id) {
    try {
      return vaccineDao.deleteByPrimaryKey(id);
    } catch (Exception e) {
      logger.error(
          "[delete vaccine Fail], vaccineId: {}, error message: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public int updateVaccine(Vaccine vaccine) {
    try {
      return vaccineDao.updateByPrimaryKey(vaccine);
    } catch (Exception e) {
      logger.error(
          "[update vaccine Fail], vaccineId: {}, error message: {}",
          SerialUtil.toJsonStr(vaccine.getId()),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }
}
