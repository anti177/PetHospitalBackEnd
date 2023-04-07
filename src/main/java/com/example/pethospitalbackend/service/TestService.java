package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.*;
import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.*;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.exception.ParameterException;
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

    // 2.向test_record插入记录，拿到test_record_id
    TestRecord testRecord = new TestRecord();
    testRecord.setTestId(testId);
    testRecord.setScore(score);
    testRecord.setUserId(Long.parseLong(userId));
    // 3.向record_answer, testRecord插入记录
    try {
      testRecordDao.insert(testRecord);
      if (score != 0) {
        for (RecordRequest r : questionList) {
          AnswerRecord a = new AnswerRecord();
          a.setScore(r.getScore());
          a.setQuestionId(r.getQuestionId());
          a.setUserId(Long.parseLong(userId));
          a.setUserAnswer(r.getAns());
          a.setTestId(testId);

          answerRecordDao.insert(a);
        }
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

  // todo: 错误处理
  public List<QuestionBackBriefDTO> getAllQuestions() {
    return questionDao.getAllQuestionBackBriefDTOs();
  }

  public Question addQuestion(QuestionFormDTO questionFormDTO) {
    Question question = changeFormToQuestion(questionFormDTO);
    questionDao.insert(question);
    return question;
  }

  public int deleteQuestion(Long id) {
    if (!relQuestionPaperDao.existsWithQuestionId(id)) {
      return questionDao.deleteByPrimaryKey(id);
    } else {
      // todo: 打印报错信息
      throw new ParameterException("存在相关试卷，删除失败");
    }
  }

  public int updateQuestion(QuestionFormDTO questionForm) {
    Question question = changeFormToQuestion(questionForm);
    return questionDao.updateByPrimaryKey(question);
  }

  public Question changeFormToQuestion(QuestionFormDTO questionForm) {
    Question question = new Question();
    BeanUtils.copyProperties(questionForm, question);
    String ans = String.join(";", questionForm.getAns());
    String choice = String.join(";", questionForm.getChoice());
    question.setAns(ans);
    question.setChoice(choice);
    return question;
  }

  public QuestionBackDetailDTO getQuestion(Long id) {
    Question question = questionDao.selectByPrimaryKey(id);
    QuestionBackDetailDTO questionFormDTO = new QuestionBackDetailDTO();
    BeanUtils.copyProperties(question, questionFormDTO);
    questionFormDTO.setAns(Arrays.asList(question.getAns().split(";")));
    questionFormDTO.setChoice(Arrays.asList(question.getChoice().split(";")));
    questionFormDTO.setDisease(diseaseDao.selectByPrimaryKey(question.getDiseaseId()));
    return questionFormDTO;
  }

  // todo: 测试
  public List<Paper> getAllPapers() {
    return paperDao.selectAll();
  }

  public Paper getPaperById(Long id) {
    return paperDao.selectByPrimaryKey(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public Paper addPaper(PaperBackDTO paperBackDTO) {
    Paper paper = paperBackDTO.getPaper();
    paperDao.insert(paper);
    List<RelQuestionPaper> relQuestionPaperList =
        getRelQuestionPaperList(paperBackDTO.getList(), paper.getPaperId());
    relQuestionPaperDao.insertList(relQuestionPaperList);
    return paper;
  }

  private List<RelQuestionPaper> getRelQuestionPaperList(
      List<QuestionWithScoreDTO> list, Long paperId) {
    List<RelQuestionPaper> relQuestionPaperList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      QuestionWithScoreDTO questionWIthScoreDTO = list.get(i);
      RelQuestionPaper relQuestionPaper = new RelQuestionPaper();
      relQuestionPaper.setIndex_num((long) i);
      relQuestionPaper.setPaperId(paperId);
      relQuestionPaper.setQuestionId(questionWIthScoreDTO.getQuestion_id());
      relQuestionPaper.setScore(questionWIthScoreDTO.getScore());
      relQuestionPaperList.add(relQuestionPaper);
    }
    return relQuestionPaperList;
  }

  @Transactional(rollbackFor = Exception.class)
  public int updatePaper(PaperBackDTO paperBackDTO) {
    Paper paper = paperBackDTO.getPaper();
    paperDao.updateByPrimaryKey(paper);

    // 删除该试卷之前的所有题目
    relQuestionPaperDao.deleteByPaperId(paper.getPaperId());

    // 重新添加
    List<RelQuestionPaper> relQuestionPaperList =
        getRelQuestionPaperList(paperBackDTO.getList(), paper.getPaperId());
    relQuestionPaperDao.insertList(relQuestionPaperList);
    return 1;
  }

  @Transactional(rollbackFor = Exception.class)
  public int deletePaper(Long id) {
    // 如果存在相关考试场次则无法删除
    if (!testDao.existsWithPaperId(id)) {
      relQuestionPaperDao.deleteByPaperId(id);
      return paperDao.deleteByPrimaryKey(id);
    } else {
      logger.warn(""); // todo: 完善
      throw new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
    }
  }

  // todo: 补充考试场次管理
  public List<TestBackFormDTO> getAllTests() {
    return null;
  }
}
