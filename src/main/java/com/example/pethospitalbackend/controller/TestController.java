package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Question;
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
@Api(tags = {"考试"})
public class TestController {

  @Resource TestService testService;

  @GetMapping("/category")
  @ApiOperation(value = "获得考试目录")
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
  @ApiOperation(value = "提交考试答案")
  public ResponseEntity<Response<Boolean>> postTestAnswer(
      @RequestBody List<RecordRequest> recordRequests, @PathVariable long testId) {
    Response<Boolean> response = testService.recordAnswer(recordRequests, testId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/record/{testId}")
  @ApiOperation(value = "查看考试答案")
  public ResponseEntity<Response<List<FrontTestAnswerDTO>>> getTestAnswer(
      @PathVariable long testId) {
    Response<List<FrontTestAnswerDTO>> response = testService.getRecord(testId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/questions")
  @ApiOperation(value = "管理员获取全部问题列表")
  public Response<List<QuestionBackBriefDTO>> getAllQuestions() {
    Response<List<QuestionBackBriefDTO>> response = new Response<>();
    response.setSuc(testService.getAllQuestions());
    return response;
  }

  @GetMapping("/questions/{id}")
  @ApiOperation(value = "管理员获取某问题具体信息")
  public Response<QuestionBackDetailDTO> getQuestion(@PathVariable Long id) {
    Response<QuestionBackDetailDTO> response = new Response<>();
    response.setSuc(testService.getQuestion(id));
    return response;
  }

  @PostMapping("/questions")
  @ApiOperation("管理员上传问题")
  public Response<Question> addQuestion(@RequestBody QuestionFormDTO questionForm) {
    Response<Question> response = new Response<>();
    response.setSuc(testService.addQuestion(questionForm));
    return response;
  }

  @DeleteMapping("/questions/{id}")
  @ApiOperation("管理员删除问题")
  public Response<ModifiedRecordCountDTO> deleteQuestion(@PathVariable Long id) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(testService.deleteQuestion(id)));
    return response;
  }

  @PutMapping("/questions/{id}")
  @ApiOperation("管理员更新问题")
  public Response<ModifiedRecordCountDTO> updateQuestions(
      @PathVariable Long id, @RequestBody QuestionFormDTO questionForm) {
    questionForm.setQuestionId(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(testService.updateQuestion(questionForm)));
    return response;
  }
}
