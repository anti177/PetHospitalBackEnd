package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.DepartmentDao;
import com.example.pethospitalbackend.entity.Department;
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
public class DepartmentService {
  private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

  @Resource DepartmentDao departmentDao;

  public Response<List<Department>> getAllDepartmentInfo() {
    Response<List<Department>> response = new Response<>();
    List<Department> departments;
    try {
      departments = departmentDao.selectAll();
    } catch (Exception e) {
      logger.error(
          "[getAllDepartmentInfo Fail], error message{}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
    response.setSuc(departments);
    return response;
  }

  public int updateDepartment(Department department) {
    try {
      return departmentDao.updateByPrimaryKey(department);
    } catch (Exception e) {
      logger.error(
          "[update Department Fail], departmentId: {}, error message: {}",
          SerialUtil.toJsonStr(department.getDepartmentId()),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }
}
