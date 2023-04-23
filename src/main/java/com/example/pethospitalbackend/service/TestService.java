package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.*;
import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.*;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.exception.UserMailNotRegisterOrPasswordWrongException;
import com.example.pethospitalbackend.request.RecordRequest;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.util.JwtUtils;
import com.example.pethospitalbackend.util.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class TestService {
  private static final Logger logger = LoggerFactory.getLogger(TestService.class);

  @Resource private TestDao testDao;

  @Resource private QuestionDao questionDao;

  @Resource private TestRecordDao testRecordDao;

  @Resource private TestUserDao testUserDao;

  @Resource private AnswerRecordDao answerRecordDao;

  @Resource private RelQuestionPaperDao relQuestionPaperDao;

  @Resource private DiseaseDao diseaseDao;

  @Resource private PaperDao paperDao;

  public Response<List<TestCategoryDTO>> getTestCategoryList() {
    String userId = JwtUtils.getUserId();
    Response<List<TestCategoryDTO>> response = new Response<>();
    if (userId != null) {
      try {
        List<TestCategoryDTO> testList = testDao.getTestCategoryByUserId(Long.parseLong(userId));
        response.setSuc(testList);
      } catch (Exception e) {
        logger.error(
            "[getTestCategoryDTO Fail], userId : {},error message{}",
            userId,
            SerialUtil.toJsonStr(e.getMessage()));
        throw new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
      }
    } else {
      // 验证过期
      throw new UserMailNotRegisterOrPasswordWrongException(ResponseEnum.VERIFY_INVALID.getMsg());
    }
    return response;
  }

  public Response<List<EndTestCategoryDTO>> getEndTestCategoryList() {
    String userId = JwtUtils.getUserId();
    Response<List<EndTestCategoryDTO>> response = new Response<>();
    if (userId != null) {
      try {
        List<EndTestCategoryDTO> testList =
            testDao.getEndTestCategoryByUserId(Long.parseLong(userId));
        response.setSuc(testList);
      } catch (Exception e) {
        logger.error(
            "[getEndTestCategoryDTO Fail], userId : {},error message{}",
            userId,
            SerialUtil.toJsonStr(e.getMessage()));
        throw new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
      }
    } else {
      // 验证过期
      throw new UserMailNotRegisterOrPasswordWrongException(ResponseEnum.VERIFY_INVALID.getMsg());
    }
    return response;
  }

  public Response<TestPaperDTO> getTestContent(Long testId) {
    Response<TestPaperDTO> response = new Response<>();
    try {
      // 1.获得paper数据
      TestPaperDTO testPaperDTO = questionDao.getTestPaperByUserIdAndTestId(testId);
      // 2.获得paper question数据
      if (testPaperDTO != null && testPaperDTO.getPaperId() != 0) {
        List<QuestionDTO> questionList =
            questionDao.getQuestionByPaperId(testPaperDTO.getPaperId());
        List<PaperQuestionDTO> paperQuestionDTOList = new ArrayList<>();
        for (QuestionDTO q : questionList) {
          PaperQuestionDTO dto = new PaperQuestionDTO();
          dto.setQuestionId(q.getQuestionId());
          dto.setDescription(q.getDescription());
          dto.setQuestionType(q.getQuestionType());
          dto.setScore(q.getScore());
          dto.setChoice(transferChoice(q.getChoice()));
          paperQuestionDTOList.add(dto);
        }
        testPaperDTO.setQuestionList(paperQuestionDTOList);
        response.setSuc(testPaperDTO);
      }

    } catch (Exception e) {
      logger.error(
          "[ getTestContent Fail], testId : {},error message{}",
          SerialUtil.toJsonStr(testId),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
    }

    return response;
  }

  public Response recordAnswer(List<RecordRequest> recordRequests, long testId) {
    Response<Boolean> response = new Response<>();
    String userId = JwtUtils.getUserId();

    // 1.查询question答案 计算得分
    List<RecordRequest> questionList = recordRequests;
    List<Question> questionAnsList;
    try {
      questionAnsList = questionDao.getQuestionAnsByTestId(testId);
    } catch (Exception e) {
      logger.error(
          "[get answer Fail], testId : {},error message{}",
          SerialUtil.toJsonStr(testId),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
    }
    long score = getAnsScore(questionList, questionAnsList);

    if (questionAnsList.size() - questionList.size() != 0 && score == 0) {
      logger.error(
              "[valid post], testId : {}",
              SerialUtil.toJsonStr(testId));
      throw new DatabaseException(ResponseEnum.ILLEGAL_PARAM.getMsg());
    }
    // 2.向test_record插入记录，拿到test_record_id
    TestRecord testRecord = new TestRecord();
    testRecord.setTestId(testId);
    testRecord.setScore(score);
    testRecord.setUserId(Long.parseLong(userId));
    // 3.向record_answer, testRecord插入记录
    try {
      testRecordDao.insert(testRecord);
      for (RecordRequest r : questionList) {
        AnswerRecord a = new AnswerRecord();
        a.setScore(r.getScore());
        a.setQuestionId(r.getQuestionId());
        a.setUserId(Long.parseLong(userId));
        a.setUserAnswer(r.getAns());
        a.setTestId(testId);

        answerRecordDao.insert(a);
      }

    } catch (Exception e) {
      logger.error(
          "[add record Fail], testRecord : {},error message{}",
          SerialUtil.toJsonStr(testRecord),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
    }
    // 4.修改test_user表 user提交状态
    try {
      testUserDao.updateTestUserSates(Long.parseLong(userId), testId, true);
      response.setSuc(true);
    } catch (Exception e) {
      logger.error(
          "[change test user Fail], testRecord : {},error message{}",
          SerialUtil.toJsonStr(testRecord),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
    }
    return response;
  }

  private long getAnsScore(List<RecordRequest> userAnsList, List<Question> rightAnsList) {
    long score = 0;
    if (userAnsList.size() != rightAnsList.size()) {
      return 0;
    }
    for (int i = 0; i < userAnsList.size(); i++) {
      RecordRequest userAns = userAnsList.get(i);
      Question question = rightAnsList.get(i);
      if (userAns.getAns().equals(question.getAns())) {
        score += userAns.getScore();
      } else {
        userAns.setScore(0);
      }
    }
    return score;
  }

  public Response<List<FrontTestAnswerDTO>> getRecord(long testId) {
    String userId = JwtUtils.getUserId();
    List<FrontTestAnswerDTO> answerDTOS = new ArrayList<>();
    Response<List<FrontTestAnswerDTO>> response = new Response<>();
    try {
      answerDTOS = answerRecordDao.getTestAnswer(testId, Long.parseLong(userId));
      for (FrontTestAnswerDTO dto : answerDTOS) {
        dto.setChoiceList(transferChoice(dto.getChoice()));
      }
    } catch (Exception e) {
      logger.error(
          "[get Record Fail], testId : {}, userId:{},error message{}",
          SerialUtil.toJsonStr(testId),
          userId,
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
    }
    if (answerDTOS.size() == 0) {
      response.setSuc(answerDTOS);
      response.setMessage("用户错过了考试,无法查看考试答案");
      testUserDao.updateTestUserSates(Long.parseLong(userId), testId, false);
    } else {
      response.setSuc(answerDTOS);
    }
    return response;
  }

  private List<String> transferChoice(String choice) {
    // 将choice转为数组
    String[] temp;
    String delimiter = ";"; // 指定分割字符
    temp = choice.split(delimiter); // 分割字符串
    List<String> choiceList = new ArrayList<>();
    Collections.addAll(choiceList, temp);
    return choiceList;
  }

  public List<QuestionBackBriefDTO> getAllQuestions() {
    try {
      return questionDao.getAllQuestionBackBriefDTOs();
    } catch (Exception e) {
      logger.error("[get all questions fail], error msg: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public Question addQuestion(QuestionBackFormDTO questionBackFormDTO) {
    try {
      Question question = changeFormToQuestion(questionBackFormDTO);
      questionDao.insert(question);
      return question;
    } catch (Exception e) {
      logger.error("[insert question fail], error msg: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public int deleteQuestion(Long id) {
    if (!relQuestionPaperDao.existsWithQuestionId(id)) {
      try {
        return questionDao.deleteByPrimaryKey(id);
      } catch (Exception e) {
        logger.error(
            "[delete question fail], questionId: {}, error msg: {}",
            SerialUtil.toJsonStr(id),
            SerialUtil.toJsonStr(e.getMessage()));
        throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
      }
    } else {
      return -1;
    }
  }

  public int updateQuestion(QuestionBackFormDTO questionForm) {
    Question question = changeFormToQuestion(questionForm);
    try {
      return questionDao.updateByPrimaryKey(question);
    } catch (Exception e) {
      logger.error(
          "[update question fail], questionId: {}, error msg: {}",
          SerialUtil.toJsonStr(question.getQuestionId()),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public QuestionBackDetailDTO getQuestion(Long id) {
    try {
      Question question = questionDao.selectByPrimaryKey(id);
      QuestionBackDetailDTO questionFormDTO = new QuestionBackDetailDTO();
      BeanUtils.copyProperties(question, questionFormDTO);
      questionFormDTO.setAns(Arrays.asList(question.getAns().split(";")));
      questionFormDTO.setChoice(Arrays.asList(question.getChoice().split(";")));
      DiseaseDTO disease = diseaseDao.selectDTOByPrimaryKey(question.getDiseaseId());
      questionFormDTO.setDisease(disease);
      return questionFormDTO;
    } catch (Exception e) {
      logger.error(
          "[get question fail], questionId: {}, error msg: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public List<Paper> getAllPapers() {
    try {
      return paperDao.selectAll();
    } catch (Exception e) {
      logger.error("[get all papers fail], error msg: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public PaperDetailBackDTO getPaperById(Long id) {
    try {
      Paper paper = paperDao.selectByPrimaryKey(id);
      PaperDetailBackDTO paperDetailBackDTO = new PaperDetailBackDTO();
      BeanUtils.copyProperties(paper, paperDetailBackDTO);
      paperDetailBackDTO.setQuestionList(questionDao.getQuestionByPaperId(id));
      return paperDetailBackDTO;
    } catch (Exception e) {
      logger.error(
          "[get paper fail], questionId: {}, error msg: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public Paper addPaper(PaperBackDTO paperBackDTO) {
    try {
      Paper paper = new Paper();
      BeanUtils.copyProperties(paperBackDTO, paper);
      paperDao.insert(paper);
      List<RelQuestionPaper> relQuestionPaperList =
          getRelQuestionPaperList(paperBackDTO.getQuestionList(), paper.getPaperId());
      relQuestionPaperDao.insertList(relQuestionPaperList);
      return paper;
    } catch (Exception e) {
      logger.error("[insert paper fail], error msg: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int updatePaper(PaperBackDTO paperBackDTO) {
    Paper paper = new Paper();
    BeanUtils.copyProperties(paperBackDTO, paper);
    try {
      paperDao.updateByPrimaryKey(paper);

      // 删除该试卷之前的所有题目
      relQuestionPaperDao.deleteByPaperId(paper.getPaperId());

      // 重新添加
      List<RelQuestionPaper> relQuestionPaperList =
          getRelQuestionPaperList(paperBackDTO.getQuestionList(), paper.getPaperId());
      relQuestionPaperDao.insertList(relQuestionPaperList);
      return 1;
    } catch (Exception e) {
      logger.error(
          "[update paper fail], paperId: {}, error msg: {}",
          SerialUtil.toJsonStr(paper.getPaperId()),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int deletePaper(Long id) {
    // 如果存在相关考试场次则无法删除
    if (!testDao.existsWithPaperId(id)) {
      try {
        relQuestionPaperDao.deleteByPaperId(id);
        return paperDao.deleteByPrimaryKey(id);
      } catch (Exception e) {
        logger.error(
            "[delete paper fail], paperId: {}, error msg: {}",
            SerialUtil.toJsonStr(id),
            SerialUtil.toJsonStr(e.getMessage()));
        throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
      }
    } else {
      return -1;
    }
  }

  public List<Test> getAllTests() {
    try {
      return testDao.selectAll();
    } catch (Exception e) {
      logger.error("[get all tests fail], error msg: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public TestDetailBackDTO getTest(Long id) {
    try {
      TestDetailBackDTO testDetailBackDTO = new TestDetailBackDTO();
      Test test = testDao.selectByPrimaryKey(id);
      BeanUtils.copyProperties(test, testDetailBackDTO);
      Long paperId = test.getPaperId();
      testDetailBackDTO.setPaperName(paperDao.selectNameByPrimaryKey(paperId));
      testDetailBackDTO.setUserList(testDao.selectRelatedUserNameByTestId(id));
      return testDetailBackDTO;
    } catch (Exception e) {
      logger.error(
          "[get test fail], testId: {}, error msg: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int deleteTest(Long id) {
    try {
      testUserDao.deleteTestUsersByTestId(id);
      return testDao.deleteByPrimaryKey(id);
    } catch (Exception e) {
      logger.error(
          "[delete test fail], testId: {}, error msg: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public Test addTest(TestFormBackDTO testFormBackDTO) {
    try {
      Test test = new Test();
      BeanUtils.copyProperties(testFormBackDTO, test);
      testDao.insert(test);
      List<Long> userIdList = testFormBackDTO.getUserList();
      if (userIdList != null) {
        List<TestUser> testUserList = getTestUserList(userIdList, test.getTestId());
        testUserDao.insertList(testUserList);
      }
      return test;
    } catch (Exception e) {
      logger.error("[insert test fail], error msg: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int updateTest(TestFormBackDTO testFormBackDTO) {
    Test test = new Test();
    BeanUtils.copyProperties(testFormBackDTO, test);
    try {
      testUserDao.deleteTestUsersByTestId(test.getTestId());
      List<Long> userIdList = testFormBackDTO.getUserList();
      if (userIdList != null) {
        List<TestUser> testUserList = getTestUserList(userIdList, test.getTestId());
        testUserDao.insertList(testUserList);
      }
      return testDao.updateByPrimaryKey(test);
    } catch (Exception e) {
      logger.error(
          "[update paper fail], paperId: {}, error msg: {}",
          SerialUtil.toJsonStr(test.getTestId()),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  private Question changeFormToQuestion(QuestionBackFormDTO questionForm) {
    Question question = new Question();
    BeanUtils.copyProperties(questionForm, question);
    String ans = String.join(";", questionForm.getAns());
    String choice = String.join(";", questionForm.getChoice());
    question.setAns(ans);
    question.setChoice(choice);
    return question;
  }

  private List<RelQuestionPaper> getRelQuestionPaperList(
      List<QuestionWithScoreDTO> list, Long paperId) {
    List<RelQuestionPaper> relQuestionPaperList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      QuestionWithScoreDTO questionWIthScoreDTO = list.get(i);
      RelQuestionPaper relQuestionPaper = new RelQuestionPaper();
      relQuestionPaper.setIndex_num((long) i);
      relQuestionPaper.setPaperId(paperId);
      relQuestionPaper.setQuestionId(questionWIthScoreDTO.getQuestionId());
      relQuestionPaper.setScore(questionWIthScoreDTO.getScore());
      relQuestionPaperList.add(relQuestionPaper);
    }
    return relQuestionPaperList;
  }

  private List<TestUser> getTestUserList(List<Long> list, Long testId) {
    List<TestUser> testUserList = new ArrayList<>();
    for (Long userId : list) {
      TestUser testUser = new TestUser();
      testUser.setUserId(userId);
      testUser.setTestId(testId);
      testUser.setHasSubmit(false);
      testUserList.add(testUser);
    }
    return testUserList;
  }
}
