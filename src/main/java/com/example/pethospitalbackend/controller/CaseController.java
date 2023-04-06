package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.IllCase;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.CaseService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = {"病例学习"})
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

  @GetMapping("/cases/{caseId}")
  @ApiOperation(value = "获得具体病例")
  public ResponseEntity<Response<CaseFrontDetailDTO>> getCaseByCaseId(
      @PathVariable Long caseId, @RequestParam("front") Boolean front) {
    Response response = new Response<>();
    if (front) {
      response = caseService.getFrontCaseByCaseId(caseId);
    } else {
      response.setSuc(caseService.getBackCaseDetailDTOByCaseId(caseId));
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/cases")
  @ApiOperation("分页获取病例基本信息")
  Response getAllCases(
      @RequestParam(required = false) Integer pageNum,
      @RequestParam(required = false) Integer pageSize) {
    if (pageNum == null) {
      List<CaseBackBriefDTO> caseBackBriefDTOS = caseService.getAllCaseBackBriefDTOs();
      Response<List<CaseBackBriefDTO>> response = new Response<>();
      response.setSuc(caseBackBriefDTOS);
      return response;
    } else {
      PageInfo<CaseBackBriefDTO> illCaseDTOPageInfo =
          caseService.getCasePageInfo(pageNum, pageSize);
      Response<PageInfo<CaseBackBriefDTO>> response = new Response<>();
      response.setSuc(illCaseDTOPageInfo);
      return response;
    }
  }

  @PostMapping("/cases")
  @ApiOperation("管理员后台上传病例")
  ResponseEntity<Response<IllCase>> postCase(@RequestBody IllCaseFormDTO form) {
    caseService.addCase(form);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @PutMapping("/cases/{id}")
  @ApiOperation("管理员后台修改病例")
  Response<ModifiedRecordCountDTO> putCase(
      @PathVariable Long id, @RequestBody IllCaseFormDTO form) {
    Integer res = caseService.updateCase(id, form);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(res));
    return response;
  }

  @DeleteMapping("/cases/{id}")
  @ApiOperation("管理员删除病例")
  Response<ModifiedRecordCountDTO> deleteCase(@PathVariable Long id) {
    Integer res = caseService.deleteCase(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(res));
    return response;
  }

  @PostMapping("/diseases")
  @ApiOperation("管理员添加疾病")
  Response<Disease> postDisease(@RequestBody Disease disease) {
    Disease diseaseRecord = caseService.addDisease(disease);
    Response<Disease> response = new Response<>();
    response.setSuc(diseaseRecord);
    return response;
  }

  @DeleteMapping("/diseases/{id}")
  @ApiOperation("管理员删除疾病")
  Response<ModifiedRecordCountDTO> deleteDisease(@PathVariable Long id) {
    Integer res = caseService.deleteDisease(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(res));
    return response;
  }

  @PutMapping("/diseases/{id}")
  @ApiOperation("管理员修改疾病")
  Response<ModifiedRecordCountDTO> putDisease(@PathVariable Long id, @RequestBody Disease disease) {
    Integer res = caseService.updateDisease(disease);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(res));
    return response;
  }

  @GetMapping("/diseases")
  @ApiOperation("管理员获取全部疾病")
  Response<List<Disease>> getAllDiseases() {
    Response<List<Disease>> response = new Response<>();
    response.setSuc(caseService.getAllDiseases());
    return response;
  }

  @GetMapping("/diseases/{id}")
  @ApiOperation("后台获取疾病详细信息")
  Response<Disease> getDisease(@PathVariable Long id) {
    Disease diseaseRecord = caseService.getDisease(id);
    Response<Disease> response = new Response<>();
    response.setSuc(diseaseRecord);
    return response;
  }
}
