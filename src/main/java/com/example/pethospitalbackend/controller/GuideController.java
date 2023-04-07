package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.entity.Department;
import com.example.pethospitalbackend.entity.Drug;
import com.example.pethospitalbackend.entity.Vaccine;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.DepartmentService;
import com.example.pethospitalbackend.service.DrugService;
import com.example.pethospitalbackend.service.VaccineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = {"科室管理与导览"})
public class GuideController {
  @Resource DepartmentService departmentService;

  @Resource DrugService drugService;

  @Resource VaccineService vaccineService;

  @GetMapping("/departments")
  @ApiOperation(value = "获得全部科室信息")
  public ResponseEntity<Response<List<Department>>> getAllDepartment() {
    Response<List<Department>> response = departmentService.getAllDepartmentInfo();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/drugs")
  @ApiOperation(value = "获得全部药品信息")
  public ResponseEntity<Response<List<Drug>>> getAllDrugs() {
    Response<List<Drug>> response = drugService.getAllDrugs();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/vaccines")
  @ApiOperation(value = "获得全部疫苗信息")
  public ResponseEntity<Response<List<Vaccine>>> getAllVaccine() {
    Response<List<Vaccine>> response = vaccineService.getAllVaccines();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  // todo: 科室信息修改 & 药品管理 & 疫苗管理
}
