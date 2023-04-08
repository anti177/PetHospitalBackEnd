package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Paper;
import com.example.pethospitalbackend.entity.Question;
import com.example.pethospitalbackend.entity.Test;
import com.example.pethospitalbackend.enums.ResponseEnum;
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

  @GetMapping("/mytest/category")
  @ApiOperation(value = "获得考试目录")
  public ResponseEntity<Response<List<TestCategoryDTO>>> getTotalCategory() {
    Response<List<TestCategoryDTO>> response = testService.getTestCategoryList();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/mytest/records/category")
  @ApiOperation(value = "获得答题记录")
  public ResponseEntity<Response<List<EndTestCategoryDTO>>> getEndTestCategory() {
    Response<List<EndTestCategoryDTO>> response = testService.getEndTestCategoryList();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/mytest/{testId}")
  @ApiOperation(value = "获得考试题目")
  public ResponseEntity<Response<TestPaperDTO>> getTestContent(@PathVariable Long testId) {
    Response<TestPaperDTO> response = testService.getTestContent(testId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/mytest/answer/{testId}")
  @ApiOperation(value = "提交考试答案")
  public ResponseEntity<Response<Boolean>> postTestAnswer(
      @RequestBody List<RecordRequest> recordRequests, @PathVariable long testId) {
    Response<Boolean> response = testService.recordAnswer(recordRequests, testId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/mytest/record/{testId}")
  @ApiOperation(value = "查看考试答案")
  public ResponseEntity<Response<List<FrontTestAnswerDTO>>> getTestAnswer(
      @PathVariable long testId) {
    Response<List<FrontTestAnswerDTO>> response = testService.getRecord(testId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  // -----------------------------------------------------------------后台---------------------------------------------------------------

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
  public Response<Question> addQuestion(@RequestBody QuestionBackFormDTO questionForm) {
    Response<Question> response = new Response<>();
    response.setSuc(testService.addQuestion(questionForm));
    return response;
  }

  @DeleteMapping("/questions/{id}")
  @ApiOperation("管理员删除问题")
  public Response<ModifiedRecordCountDTO> deleteQuestion(@PathVariable Long id) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    int result = testService.deleteQuestion(id);
    ModifiedRecordCountDTO modifiedRecordCountDTO = new ModifiedRecordCountDTO(result);
    if (result == -1) {
      response.setMessage("当前存在包含该试题的试卷，删除失败。请修改或删除相关试卷后重试。");
      response.setStatus(ResponseEnum.CONFLICT.getCode());
      response.setResult(modifiedRecordCountDTO);
    } else {
      response.setSuc(modifiedRecordCountDTO);
    }
    return response;
  }

  @PutMapping("/questions/{id}")
  @ApiOperation("管理员更新问题")
  public Response<ModifiedRecordCountDTO> updateQuestions(
      @PathVariable Long id, @RequestBody QuestionBackFormDTO questionForm) {
    questionForm.setQuestionId(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(testService.updateQuestion(questionForm)));
    return response;
  }

  @GetMapping("/papers")
  @ApiOperation(value = "管理员获取全部试卷列表")
  public Response<List<Paper>> getAllPapers() {
    Response<List<Paper>> response = new Response<>();
    response.setSuc(testService.getAllPapers());
    return response;
  }

  @GetMapping("/papers/{id}")
  @ApiOperation(value = "管理员获取试卷信息")
  public Response<Paper> getPaper(@PathVariable Long id) {
    Response<Paper> response = new Response<>();
    response.setSuc(testService.getPaperById(id));
    return response;
  }

  @PostMapping("/papers")
  @ApiOperation(value = "管理员生成试卷")
  public Response<Paper> addPaper(@RequestBody PaperBackDTO paperBackDTO) {
    Response<Paper> response = new Response<>();
    response.setSuc(testService.addPaper(paperBackDTO));
    return response;
  }

  @PutMapping("/papers/{id}")
  @ApiOperation(value = "管理员修改试卷")
  public Response<ModifiedRecordCountDTO> updatePaper(
      @PathVariable Long id, @RequestBody PaperBackDTO paperBackDTO) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    paperBackDTO.getPaper().setPaperId(id);
    response.setSuc(new ModifiedRecordCountDTO(testService.updatePaper(paperBackDTO)));
    return response;
  }

  @DeleteMapping("/papers/{id}")
  @ApiOperation(value = "管理员删除试卷")
  public Response<ModifiedRecordCountDTO> deletePaper(@PathVariable Long id) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    int result = testService.deletePaper(id);
    ModifiedRecordCountDTO modifiedRecordCountDTO = new ModifiedRecordCountDTO(result);
    if (result == -1) {
      response.setMessage("当前存在包含该试卷的考试，删除失败。请修改或删除相关考试后重试。");
      response.setStatus(ResponseEnum.CONFLICT.getCode());
      response.setResult(modifiedRecordCountDTO);
    } else {
      response.setSuc(modifiedRecordCountDTO);
    }
    return response;
  }

  // todo: 添加一个接口用于在新建考试时获取全部试卷的id和名字

  @GetMapping("/tests")
  @ApiOperation(value = "管理员获取全部考试场次列表")
  public Response<List<Test>> getAllTests() {
    Response<List<Test>> response = new Response<>();
    response.setSuc(testService.getAllTests());
    return response;
  }

  @DeleteMapping("/tests/{id}")
  @ApiOperation(value = "管理员删除考试")
  public Response<ModifiedRecordCountDTO> deleteTest(@PathVariable Long id) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(testService.deleteTest(id)));
    return response;
  }

  @GetMapping("/tests/{id}")
  @ApiOperation(value = "管理员获取考试具体信息")
  public Response<TestDetailBackDTO> getTest(@PathVariable Long id) {
    Response<TestDetailBackDTO> response = new Response<>();
    response.setSuc(testService.getTest(id));
    return response;
  }

  @PostMapping("/tests")
  @ApiOperation(value = "管理员添加考试")
  public Response<Test> addTest(@RequestBody Test test) {
    Response<Test> response = new Response<>();
    response.setSuc(testService.addTest(test));
    return response;
  }

  @PutMapping("/tests/{id}")
  @ApiOperation(value = "管理员更新考试")
  public Response<ModifiedRecordCountDTO> updateTest(
      @PathVariable Long id, @RequestBody Test test) {
    test.setPaperID(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(testService.updateTest(test)));
    return response;
  }
}
