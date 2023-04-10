package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dao.*;
import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.Paper;
import com.example.pethospitalbackend.entity.Question;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestServiceTest extends BaseTest {

  @InjectMocks @Resource TestService testService;

  @MockBean(name = "questionDao")
  QuestionDao questionDao;

  @MockBean(name = "diseaseDao")
  DiseaseDao diseaseDao;

  @MockBean(name = "relQuestionPaperDao")
  RelQuestionPaperDao relQuestionPaperDao;

  @MockBean(name = "paperDao")
  PaperDao paperDao;

  @MockBean(name = "testDao")
  TestDao testDao;

  @Before
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testAddQuestion() {
    // Setup
    final QuestionBackFormDTO questionBackFormDTO = new QuestionBackFormDTO();
    questionBackFormDTO.setQuestionId(1L);
    questionBackFormDTO.setQuestionType("questionType");
    questionBackFormDTO.setDescription("description");
    questionBackFormDTO.setChoice(Arrays.asList("choice1", "choice2"));
    questionBackFormDTO.setAns(Arrays.asList("ans1", "ans2"));
    questionBackFormDTO.setKeyword("keyword");
    questionBackFormDTO.setDiseaseId(1L);

    final Question question = new Question();
    question.setQuestionId(1L);
    question.setQuestionType("questionType");
    question.setDescription("description");
    question.setChoice("choice1;choice2");
    question.setAns("ans1;ans2");
    question.setKeyword("keyword");
    question.setDiseaseId(1L);

    when(questionDao.insert(question)).thenReturn(1);

    // Run the test
    final Question result = testService.addQuestion(questionBackFormDTO);

    // Verify the results
    assertEquals(question, result);
  }

  @Test
  public void testDeleteQuestion() {
    // Setup
    when(questionDao.deleteByExample(0L)).thenReturn(1);

    // Run the test
    final int result = testService.deleteQuestion(0L);

    // Verify the results
    assertEquals(0, result);
  }

  @Test
  public void testGetQuestion() {
    // Setup
    final QuestionBackDetailDTO expectedResult = new QuestionBackDetailDTO();
    expectedResult.setQuestionId(0L);
    expectedResult.setQuestionType("questionType");
    expectedResult.setDescription("description");
    expectedResult.setChoice(Arrays.asList("choice1", "choice2"));
    expectedResult.setAns(Arrays.asList("ans1", "ans2"));
    expectedResult.setKeyword("keyword");
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    expectedResult.setDisease(disease);

    // Configure QuestionDao.selectByPrimaryKey(...).
    final Question question = new Question();
    question.setQuestionId(0L);
    question.setQuestionType("questionType");
    question.setDescription("description");
    question.setChoice("choice1;choice2");
    question.setAns("ans1;ans2");
    question.setKeyword("keyword");
    question.setDiseaseId(0L);
    when(questionDao.selectByPrimaryKey(0L)).thenReturn(question);

    // Configure DiseaseDao.selectByPrimaryKey(...).
    final Disease disease1 = new Disease();
    disease1.setDiseaseId(0L);
    disease1.setDiseaseName("diseaseName");
    disease1.setTypeName("typeName");
    when(diseaseDao.selectByPrimaryKey(0L)).thenReturn(disease1);

    // Run the test
    final QuestionBackDetailDTO result = testService.getQuestion(0L);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testUpdateQuestion() {
    // Setup
    final QuestionBackFormDTO questionForm = new QuestionBackFormDTO();
    questionForm.setQuestionId(0L);
    questionForm.setQuestionType("questionType");
    questionForm.setDescription("description");
    questionForm.setChoice(Collections.singletonList("value"));
    questionForm.setAns(Collections.singletonList("value"));
    questionForm.setKeyword("keyword");
    questionForm.setDiseaseId(0L);

    when(questionDao.updateByPrimaryKey(any())).thenReturn(1);

    // Run the test
    final int result = testService.updateQuestion(questionForm);

    // Verify the results
    assertEquals(1, result);
  }

  @Test
  public void testGetAllQuestions() {
    // Setup
    final QuestionBackBriefDTO questionBackBriefDTO = new QuestionBackBriefDTO();
    questionBackBriefDTO.setQuestionId(0L);
    questionBackBriefDTO.setDescription("description");
    questionBackBriefDTO.setQuestionType("questionType");
    questionBackBriefDTO.setKeyword("keyword");
    questionBackBriefDTO.setDiseaseName("diseaseName");
    final List<QuestionBackBriefDTO> expectedResult =
        Collections.singletonList(questionBackBriefDTO);

    // Configure QuestionDao.getAllQuestionBackBriefDTOs(...).
    final QuestionBackBriefDTO questionBackBriefDTO1 = new QuestionBackBriefDTO();
    questionBackBriefDTO1.setQuestionId(0L);
    questionBackBriefDTO1.setDescription("description");
    questionBackBriefDTO1.setQuestionType("questionType");
    questionBackBriefDTO1.setKeyword("keyword");
    questionBackBriefDTO1.setDiseaseName("diseaseName");
    final List<QuestionBackBriefDTO> questionBackBriefDTOS =
        Collections.singletonList(questionBackBriefDTO1);
    when(questionDao.getAllQuestionBackBriefDTOs()).thenReturn(questionBackBriefDTOS);

    // Run the test
    final List<QuestionBackBriefDTO> result = testService.getAllQuestions();

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testAddPaper() {
    // Setup
    final PaperBackDTO paperBackDTO = new PaperBackDTO();
    final Paper paper = new Paper();
    paper.setPaperId(0L);
    paper.setPaperName("paperName");
    paper.setScore(0L);
    paperBackDTO.setPaper(paper);
    final QuestionWithScoreDTO questionWithScoreDTO = new QuestionWithScoreDTO();
    questionWithScoreDTO.setQuestion_id(0L);
    questionWithScoreDTO.setScore(0L);
    paperBackDTO.setList(Collections.singletonList(questionWithScoreDTO));

    final Paper expectedResult = new Paper();
    expectedResult.setPaperId(0L);
    expectedResult.setPaperName("paperName");
    expectedResult.setScore(0L);

    // Run the test
    final Paper result = testService.addPaper(paperBackDTO);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testDeletePaper() {
    // Setup
    when(relQuestionPaperDao.deleteByPaperId(0L)).thenReturn(1);
    when(paperDao.deleteByPrimaryKey(0L)).thenReturn(1);

    // Run the test
    final int result = testService.deletePaper(0L);

    // Verify the results
    assertEquals(1, result);
    verify(relQuestionPaperDao).deleteByPaperId(0L);
  }

  @Test
  public void testGetAllPapers() {
    // Setup
    final Paper paper = new Paper();
    paper.setPaperId(0L);
    paper.setPaperName("paperName");
    paper.setScore(0L);
    final List<Paper> expectedResult = Collections.singletonList(paper);

    // Configure PaperDao.selectAll(...).
    final Paper paper1 = new Paper();
    paper1.setPaperId(0L);
    paper1.setPaperName("paperName");
    paper1.setScore(0L);
    final List<Paper> papers = Collections.singletonList(paper1);
    when(paperDao.selectAll()).thenReturn(papers);

    // Run the test
    final List<Paper> result = testService.getAllPapers();

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testGetPaperById() {
    // Setup
    final Paper expectedResult = new Paper();
    expectedResult.setPaperId(0L);
    expectedResult.setPaperName("paperName");
    expectedResult.setScore(0L);

    // Configure PaperDao.selectByPrimaryKey(...).
    final Paper paper = new Paper();
    paper.setPaperId(0L);
    paper.setPaperName("paperName");
    paper.setScore(0L);
    when(paperDao.selectByPrimaryKey(0L)).thenReturn(paper);

    // Run the test
    final Paper result = testService.getPaperById(0L);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testUpdatePaper() {
    // Setup
    final PaperBackDTO paperBackDTO = new PaperBackDTO();
    final Paper paper = new Paper();
    paper.setPaperId(0L);
    paper.setPaperName("paperName");
    paper.setScore(0L);
    paperBackDTO.setPaper(paper);
    final QuestionWithScoreDTO questionWithScoreDTO = new QuestionWithScoreDTO();
    questionWithScoreDTO.setQuestion_id(0L);
    questionWithScoreDTO.setScore(0L);
    paperBackDTO.setList(Collections.singletonList(questionWithScoreDTO));

    when(paperDao.updateByPrimaryKey(any(Paper.class))).thenReturn(1);
    when(relQuestionPaperDao.deleteByPaperId(0L)).thenReturn(1);
    when(relQuestionPaperDao.insertList(anyList())).thenReturn(1);

    // Run the test
    final int result = testService.updatePaper(paperBackDTO);

    // Verify the results
    assertEquals(1, result);
  }

  @Test
  public void testAddTest() {
    // Setup
    final TestFormBackDTO testFormBackDTO = new TestFormBackDTO();
    testFormBackDTO.setTestId(0L);
    testFormBackDTO.setTestName("testName");
    testFormBackDTO.setIntro("intro");
    testFormBackDTO.setTag("tag");
    testFormBackDTO.setPaperID(0L);
    testFormBackDTO.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    testFormBackDTO.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    testFormBackDTO.setUserList(Collections.singletonList(0L));

    final com.example.pethospitalbackend.entity.Test expectedResult =
        new com.example.pethospitalbackend.entity.Test();
    expectedResult.setTestId(0L);
    expectedResult.setTestName("testName");
    expectedResult.setIntro("intro");
    expectedResult.setTag("tag");
    expectedResult.setPaperID(0L);
    expectedResult.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    expectedResult.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

    when(testDao.insert(any())).thenReturn(0);

    // Run the test
    final com.example.pethospitalbackend.entity.Test result = testService.addTest(testFormBackDTO);

    // Verify the results
    assertEquals(expectedResult, result);
    verify(testDao).insert(any());
  }

  @Test
  public void testDeleteTest() {
    // Setup
    // Configure TestDao.selectByPrimaryKey(...).
    final com.example.pethospitalbackend.entity.Test test =
        new com.example.pethospitalbackend.entity.Test();
    test.setTestId(0L);
    test.setTestName("testName");
    test.setIntro("intro");
    test.setTag("tag");
    test.setPaperID(0L);
    test.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    test.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    when(testDao.selectByPrimaryKey(0L)).thenReturn(test);

    when(paperDao.existsWithPrimaryKey(0L)).thenReturn(false);
    when(testDao.deleteByPrimaryKey(0L)).thenReturn(1);

    // Run the test
    final int result = testService.deleteTest(0L);

    // Verify the results
    assertEquals(1, result);
  }

  @Test
  public void testGetTest() {
    // Setup
    final TestDetailBackDTO expectedResult = new TestDetailBackDTO();
    expectedResult.setTestId(0L);
    expectedResult.setTestName("testName");
    expectedResult.setIntro("intro");
    expectedResult.setTag("tag");
    expectedResult.setPaperID(0L);
    expectedResult.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    expectedResult.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    expectedResult.setUserList(Collections.singletonList("value"));

    // Configure TestDao.selectByPrimaryKey(...).
    final com.example.pethospitalbackend.entity.Test test =
        new com.example.pethospitalbackend.entity.Test();
    test.setTestId(0L);
    test.setTestName("testName");
    test.setIntro("intro");
    test.setTag("tag");
    test.setPaperID(0L);
    test.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    test.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    when(testDao.selectByPrimaryKey(0L)).thenReturn(test);

    when(testDao.selectRelatedUserNameByTestId(0L)).thenReturn(Collections.singletonList("value"));

    // Run the test
    final TestDetailBackDTO result = testService.getTest(0L);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testGetAllTests() {
    // Setup
    final com.example.pethospitalbackend.entity.Test test =
        new com.example.pethospitalbackend.entity.Test();
    test.setTestId(0L);
    test.setTestName("testName");
    test.setIntro("intro");
    test.setTag("tag");
    test.setPaperID(0L);
    test.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    test.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    final List<com.example.pethospitalbackend.entity.Test> expectedResult =
        Collections.singletonList(test);

    // Configure TestDao.selectAll(...).
    final com.example.pethospitalbackend.entity.Test test1 =
        new com.example.pethospitalbackend.entity.Test();
    test1.setTestId(0L);
    test1.setTestName("testName");
    test1.setIntro("intro");
    test1.setTag("tag");
    test1.setPaperID(0L);
    test1.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    test1.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    final List<com.example.pethospitalbackend.entity.Test> tests = Collections.singletonList(test1);
    when(testDao.selectAll()).thenReturn(tests);

    // Run the test
    final List<com.example.pethospitalbackend.entity.Test> result = testService.getAllTests();

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testUpdateTest() {
    // Setup
    final TestFormBackDTO testFormBackDTO = new TestFormBackDTO();
    testFormBackDTO.setTestId(0L);
    testFormBackDTO.setTestName("testName");
    testFormBackDTO.setIntro("intro");
    testFormBackDTO.setTag("tag");
    testFormBackDTO.setPaperID(0L);
    testFormBackDTO.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    testFormBackDTO.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    testFormBackDTO.setUserList(Collections.singletonList(0L));

    when(testDao.updateByPrimaryKey(any())).thenReturn(1);

    // Run the test
    final int result = testService.updateTest(testFormBackDTO);

    // Verify the results
    assertEquals(1, result);
  }
}
