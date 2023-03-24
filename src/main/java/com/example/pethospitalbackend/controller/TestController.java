package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.EndTestCategoryDTO;
import com.example.pethospitalbackend.dto.TestCategoryDTO;
import com.example.pethospitalbackend.dto.TestPaperDTO;
import com.example.pethospitalbackend.request.RecordRequest;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/mytest")
@Api(tags = {"考试"})
public class TestController {

  @Resource TestService testService;

  @GetMapping("/category")
  @ApiOperation(value = "获得角色内容和职责")
  public ResponseEntity<Response<List<TestCategoryDTO>>> getTotalCategory() {
    Response<List<TestCategoryDTO>> response = testService.getTestCategoryList();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/records/category")
  @ApiOperation(value = "获得答题记录")
  public ResponseEntity<Response<List<EndTestCategoryDTO>>> getEndTestCategory() {
    Response<List<EndTestCategoryDTO>> response = testService.getEndTestCategoryList();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{testId}")
  @ApiOperation(value = "获得考试题目")
  public ResponseEntity<Response<TestPaperDTO>> getTestContent(@PathVariable Long testId) {
    Response<TestPaperDTO> response = testService.getTestContent(testId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/answer/{testId}")
  @ApiOperation(value = "获得考试题目")
  public ResponseEntity<Response<Boolean>> getTestContent(
      @RequestBody List<RecordRequest> recordRequests, @PathVariable long testId) {
    Response<Boolean> response = testService.recordAnswer(recordRequests, testId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
