package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.ModifiedRecordCountDTO;
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
import org.springframework.web.bind.annotation.*;

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

  @PutMapping("/departments/{id}")
  @ApiOperation(value = "后台修改科室信息")
  public Response<ModifiedRecordCountDTO> updateDepartment(
      @PathVariable Long id, @RequestBody Department department) {
    department.setDepartmentId(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(departmentService.updateDepartment(department)));
    return response;
  }

  @PostMapping("/drugs")
  @ApiOperation(value = "后台添加药品信息")
  public Response<Drug> addDrug(@RequestBody Drug drug) {
    Response<Drug> response = new Response<>();
    response.setSuc(drugService.addDrug(drug));
    return response;
  }

  @DeleteMapping("/drugs/{id}")
  @ApiOperation(value = "后台删除药品信息")
  public Response<ModifiedRecordCountDTO> deleteDrug(@PathVariable Long id) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(drugService.deleteDrug(id)));
    return response;
  }

  @PutMapping("/drugs/{id}")
  @ApiOperation(value = "后台修改药品信息")
  public Response<ModifiedRecordCountDTO> updateDrug(
      @PathVariable Long id, @RequestBody Drug drug) {
    drug.setId(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(drugService.updateDrug(drug)));
    return response;
  }

  @PostMapping("/vaccines")
  @ApiOperation(value = "后台添加疫苗信息")
  public Response<Vaccine> addVaccine(@RequestBody Vaccine vaccine) {
    Response<Vaccine> response = new Response<>();
    response.setSuc(vaccineService.addVaccine(vaccine));
    return response;
  }

  @DeleteMapping("/vaccines/{id}")
  @ApiOperation(value = "后台删除疫苗信息")
  public Response<ModifiedRecordCountDTO> deleteVaccine(@PathVariable Long id) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(vaccineService.deleteVaccine(id)));
    return response;
  }

  @PutMapping("/vaccines/{id}")
  @ApiOperation(value = "后台修改疫苗信息")
  public Response<ModifiedRecordCountDTO> updateVaccine(
      @PathVariable Long id, @RequestBody Vaccine vaccine) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    vaccine.setId(id);
    response.setSuc(new ModifiedRecordCountDTO(vaccineService.updateVaccine(vaccine)));
    return response;
  }
}
