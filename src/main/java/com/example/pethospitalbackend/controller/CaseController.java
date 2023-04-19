package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.IllCase;
import com.example.pethospitalbackend.entity.InspectionItem;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.CaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = {"病例学习与管理"})
public class CaseController {

  @Resource CaseService caseService;

  @GetMapping("/categories")
  @ApiOperation(value = "获得病种目录")
  public ResponseEntity<Response<List<CategoryDTO>>> getTotalCategory() {
    Response<List<CategoryDTO>> response = caseService.getTotalCategory();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/diseases/{diseaseId}/cases")
  @ApiOperation(value = "获得疾病病例目录")
  public ResponseEntity<Response<List<CaseCategoryDTO>>> getCaseCategory(
      @PathVariable Long diseaseId) {
    Response<List<CaseCategoryDTO>> response = caseService.getCaseCategoryByDiseaseId(diseaseId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/categories/cases/{word}")
  @ApiOperation(value = "获得疾病病例目录通过关键词")
  public ResponseEntity<Response<List<CaseCategoryDTO>>> getCaseCategoryByWord(
      @PathVariable String word) {
    Response<List<CaseCategoryDTO>> response = caseService.getFrontCaseByWord(word);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/cases/{caseId}")
  @ApiOperation(value = "获得具体病例")
  public ResponseEntity<Response<CaseFrontDetailDTO>> getCaseByCaseId(@PathVariable Long caseId) {
    Response<CaseFrontDetailDTO> response = caseService.getFrontCaseByCaseId(caseId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/cases/{caseId}/detail")
  @ApiOperation(value = "管理员获得具体病例")
  public Response<CaseBackDetailDTO> getCase(@PathVariable Long caseId) {
    Response<CaseBackDetailDTO> response = new Response<>();
    response.setSuc(caseService.getBackCaseDetailDTOByCaseId(caseId));
    return response;
  }

  @GetMapping("/cases")
  @ApiOperation(value = "管理员获取全部病例")
  Response<List<CaseBackBriefDTO>> getAllCases() {
    List<CaseBackBriefDTO> caseBackBriefDTOS = caseService.getAllCaseBackBriefDTOs();
    Response<List<CaseBackBriefDTO>> response = new Response<>();
    response.setSuc(caseBackBriefDTOS);
    return response;
  }

  @PostMapping("/cases")
  @ApiOperation(value = "管理员上传病例")
  Response<IllCase> postCase(@RequestBody CaseBackFormDTO form) {
    Response<IllCase> response = new Response<>();
    response.setSuc(caseService.addCase(form));
    return response;
  }

  @PutMapping("/cases/{id}")
  @ApiOperation(value = "管理员修改病例")
  Response<ModifiedRecordCountDTO> putCase(
      @PathVariable Long id, @RequestBody CaseBackFormDTO form) {
    form.setCase_id(id);
    int res = caseService.updateCase(form);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(res));
    return response;
  }

  @DeleteMapping("/cases/{id}")
  @ApiOperation(value = "管理员删除病例")
  Response<ModifiedRecordCountDTO> deleteCase(@PathVariable Long id) {
    int res = caseService.deleteCase(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(res));
    return response;
  }

  @PostMapping("/diseases")
  @ApiOperation(value = "管理员添加疾病")
  Response<Disease> postDisease(@RequestBody Disease disease) {
    Disease diseaseRecord = caseService.addDisease(disease);
    Response<Disease> response = new Response<>();
    response.setSuc(diseaseRecord);
    return response;
  }

  @DeleteMapping("/diseases/{id}")
  @ApiOperation(value = "管理员删除疾病")
  Response<ModifiedRecordCountDTO> deleteDisease(@PathVariable Long id) {
    int result = caseService.deleteDisease(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    ModifiedRecordCountDTO modifiedRecordCountDTO = new ModifiedRecordCountDTO(result);
    if (result == -1) {
      response.setMessage("当前存在与该疾病相关的病例，删除失败。请修改或删除相关病例后重试。");
      response.setStatus(ResponseEnum.CONFLICT.getCode());
      response.setResult(modifiedRecordCountDTO);
    } else {
      response.setSuc(modifiedRecordCountDTO);
    }
    return response;
  }

  @PutMapping("/diseases/{id}")
  @ApiOperation(value = "管理员修改疾病")
  Response<ModifiedRecordCountDTO> putDisease(@PathVariable Long id, @RequestBody Disease disease) {
    int res = caseService.updateDisease(disease);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(res));
    return response;
  }

  @GetMapping("/diseases")
  @ApiOperation(value = "管理员获取全部疾病")
  Response<List<Disease>> getAllDiseases() {
    Response<List<Disease>> response = new Response<>();
    response.setSuc(caseService.getAllDiseases());
    return response;
  }

  @GetMapping("/diseases/{id}")
  @ApiOperation(value = "管理员获取疾病详细信息")
  Response<Disease> getDisease(@PathVariable Long id) {
    Disease diseaseRecord = caseService.getDisease(id);
    Response<Disease> response = new Response<>();
    response.setSuc(diseaseRecord);
    return response;
  }

  @GetMapping(value = "/inspections/items")
  @ApiOperation("管理员获取所有检查项目（新建病例用）")
  Response<List<InspectionItemBackDTO>> getAllInspectionItems() {
    Response<List<InspectionItemBackDTO>> response = new Response<>();
    response.setSuc(caseService.getAllInspectionItems());
    return response;
  }

  @GetMapping(value = "/inspections")
  @ApiOperation("管理员获取所有检查项目")
  Response<List<InspectionItemDetailDTO>> getAllInspectionItemDetails() {
    Response<List<InspectionItemDetailDTO>> response = new Response<>();
    response.setSuc(caseService.getAllInspectionItemDetails());
    return response;
  }

  @PostMapping(value = "/inspections")
  @ApiOperation("管理员添加检查项目")
  Response<InspectionItem> addInspectionItem(@RequestBody InspectionItem inspectionItem) {
    Response<InspectionItem> response = new Response<>();
    response.setSuc(caseService.addInspectionItem(inspectionItem));
    return response;
  }

  @PutMapping(value = "/inspections/{id}")
  @ApiOperation("管理员修改检查项目")
  Response<ModifiedRecordCountDTO> updateInspectionItem(
      @PathVariable("id") Long id, @RequestBody InspectionItem inspectionItem) {
    inspectionItem.setInspectionItemId(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(caseService.updateInspectionItem(inspectionItem)));
    return response;
  }

  @DeleteMapping(value = "/inspections/{id}")
  @ApiOperation("管理员删除检查项目")
  Response<ModifiedRecordCountDTO> deleteInspectionItem(@PathVariable("id") Long id) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(caseService.deleteInspectionItem(id)));
    return response;
  }
}
