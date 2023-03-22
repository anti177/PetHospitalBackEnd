package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.CategoryDTO;
import com.example.pethospitalbackend.dto.EndTestCategoryDTO;
import com.example.pethospitalbackend.dto.TestCategoryDTO;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Test")
@Api(tags = {"考试"})
public class TestController {

	@Resource
	TestService testService;

	@GetMapping("/TotalCategory")
	@ApiOperation(value = "获得角色内容和职责")
	public ResponseEntity<Response<List<TestCategoryDTO>>> getTotalCategory() {
		Response<List<TestCategoryDTO>> response =  testService.getTestCategoryList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@GetMapping("/EndTestCategory")
	@ApiOperation(value = "获得答题记录")
	public ResponseEntity<Response<List<EndTestCategoryDTO>>> getEndTestCategory() {
		Response<List<EndTestCategoryDTO>> response =  testService.getEndTestCategoryList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
