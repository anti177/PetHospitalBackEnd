package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.CaseCategoryDTO;
import com.example.pethospitalbackend.dto.CategoryDTO;
import com.example.pethospitalbackend.dto.RoleDTO;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.CaseService;
import com.example.pethospitalbackend.service.RolePlayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Cases")
@Api(tags = {"病例学习"})
public class CaseController {
	@Autowired
	CaseService caseService;

	@GetMapping("/TotalCategory")
	@ApiOperation(value = "获得角色内容和职责")
	public ResponseEntity<Response<List<CategoryDTO>>> getTotalCategory() {
		Response<List<CategoryDTO>> response =  caseService.getTotalCategory();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/TotalCases/{diseaseId}")
	@ApiOperation(value = "获得角色内容和职责")
	public ResponseEntity<Response<List<CaseCategoryDTO>>> getCaseCategory(@PathVariable Long diseaseId) {
		Response<List<CaseCategoryDTO>> response =  caseService.getCaseCategoryByDiseaseId(diseaseId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
