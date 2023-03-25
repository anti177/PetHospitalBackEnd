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
    
    @Resource
    CaseService caseService;
    
    @GetMapping("/categories")
    @ApiOperation(value = "获得角色内容和职责")
    public ResponseEntity<Response<List<CategoryDTO>>> getTotalCategory() {
        Response<List<CategoryDTO>> response = caseService.getTotalCategory();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/diseases/{diseaseId}/cases")
    @ApiOperation(value = "获得角色内容和职责")
    public ResponseEntity<Response<List<CaseCategoryDTO>>> getCaseCategory(@PathVariable Long diseaseId) {
        Response<List<CaseCategoryDTO>> response = caseService.getCaseCategoryByDiseaseId(diseaseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/case/{caseId}")
    @ApiOperation(value = "获得具体病例")
    public ResponseEntity<Response<CaseDTO>> getCaseByCaseId(@PathVariable Long caseId) {
        Response<CaseDTO> response = caseService.getCaseByCaseId(caseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/cases")
    @ApiOperation("分页获取病例基本信息")
    Response getAllCases(
            @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize) {
        if (pageNum == null) {
            List<CaseBackEndDTO> caseBackEndDTOS = caseService.getAllCaseDTOs();
            Response<List<CaseBackEndDTO>> response = new Response<>();
            response.setSuc(caseBackEndDTOS);
            return response;
        } else {
            PageInfo<CaseBackEndDTO> illCaseDTOPageInfo = caseService.getCasePageInfo(pageNum, pageSize);
            Response<PageInfo<CaseBackEndDTO>> response = new Response<>();
            response.setSuc(illCaseDTOPageInfo);
            return response;
        }
    }

//    @GetMapping("/cases")
//    @ApiOperation("按字段模糊搜索病例")
//    ResponseEntity<Response<IllCase>> searchCase(@RequestParam Integer search_field, @RequestParam String content) {
//        //todo: 搜索->前端分页。暂时不做了
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }
    
    //todo: 等待前台商量接口
    @PostMapping("/cases")
    @ApiOperation("上传病例")
    ResponseEntity<Response<IllCase>> postCase(@RequestBody IllCaseFormDTO form) {
        caseService.addService(form);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @PutMapping("/cases/{id}")
    @ApiOperation("修改病例")
    Response<ModifiedRecordCountDTO> putCase(@PathVariable Long id) {
        Integer res = caseService.updateCase(id);
        Response<ModifiedRecordCountDTO> response = new Response<>();
        response.setSuc(new ModifiedRecordCountDTO(res));
        return response;
    }
    
    @DeleteMapping("/cases/{id}")
    @ApiOperation("删除病例")
    Response<ModifiedRecordCountDTO> deleteCase(@PathVariable Long id) {
        Integer res = caseService.deleteCase(id);
        Response<ModifiedRecordCountDTO> response = new Response<>();
        response.setSuc(new ModifiedRecordCountDTO(res));
        return response;
    }
    
    @PostMapping("/diseases")
    @ApiOperation("添加疾病")
    Response<Disease> postDisease(Disease disease) {
        Disease diseaseRecord = caseService.addDisease(disease);
        Response<Disease> response = new Response<>();
        response.setSuc(diseaseRecord);
        return response;
    }
    
    @DeleteMapping("/diseases/{id}")
    @ApiOperation("删除疾病")
    Response<ModifiedRecordCountDTO> deleteDisease(@PathVariable Long id) {
        Integer res = caseService.deleteDisease(id);
        Response<ModifiedRecordCountDTO> response = new Response<>();
        response.setSuc(new ModifiedRecordCountDTO(res));
        return response;
    }
    
    @PutMapping("/diseases/{id}")
    @ApiOperation("修改疾病")
    Response<ModifiedRecordCountDTO> putDisease(@PathVariable Long id, Disease disease) {
        Integer res = caseService.updateDisease(disease);
        Response<ModifiedRecordCountDTO> response = new Response<>();
        response.setSuc(new ModifiedRecordCountDTO(res));
        return response;
    }
    
    @GetMapping("/diseases")
    @ApiOperation("分页获取疾病")
    Response<PageInfo<Disease>> getAllDiseases(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<Disease> diseasePageInfo = caseService.getDiseasePageInfo(pageNum, pageSize);
        Response<PageInfo<Disease>> response = new Response<>();
        response.setSuc(diseasePageInfo);
        return response;
    }
    
    @GetMapping("/diseases/{id}")
    @ApiOperation("获取疾病详细信息")
    Response<Disease> getDisease(@PathVariable Long id) {
        Disease diseaseRecord = caseService.getDisease(id);
        Response<Disease> response = new Response<>();
        response.setSuc(diseaseRecord);
        return response;
    }
}

  