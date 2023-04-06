package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dao.DiseaseDao;
import com.example.pethospitalbackend.dao.QuestionDao;
import com.example.pethospitalbackend.dto.QuestionBackBriefDTO;
import com.example.pethospitalbackend.dto.QuestionBackDetailDTO;
import com.example.pethospitalbackend.dto.QuestionFormDTO;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.Question;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestServiceTest extends BaseTest {

  @InjectMocks @Resource TestService testService;

  @Mock QuestionDao questionDao;
  @Mock DiseaseDao diseaseDao;

  @Before
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testAddQuestion() {
    // Setup
    final QuestionFormDTO questionFormDTO = new QuestionFormDTO();
    questionFormDTO.setQuestionId(1L);
    questionFormDTO.setQuestionType("questionType");
    questionFormDTO.setDescription("description");
    questionFormDTO.setChoice(Arrays.asList("choice1", "choice2"));
    questionFormDTO.setAns(Arrays.asList("ans1", "ans2"));
    questionFormDTO.setKeyword("keyword");
    questionFormDTO.setDiseaseId(1L);

    final Question expectedResult = new Question();
    expectedResult.setQuestionId(1L);
    expectedResult.setQuestionType("questionType");
    expectedResult.setDescription("description");
    expectedResult.setChoice("choice1;choice2");
    expectedResult.setAns("ans1;ans2");
    expectedResult.setKeyword("keyword");
    expectedResult.setDiseaseId(1L);

    when(questionDao.insert(expectedResult)).thenReturn(1);

    // Run the test
    final Question result = testService.addQuestion(questionFormDTO);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testGetAllQuestions() {
    // Setup
    final QuestionBackBriefDTO questionBackBriefDTO = new QuestionBackBriefDTO();
    questionBackBriefDTO.setQuestionId(0L);
    questionBackBriefDTO.setChoice("choice");
    questionBackBriefDTO.setDescription("description");
    questionBackBriefDTO.setQuestionType("questionType");
    final List<QuestionBackBriefDTO> expectedResult =
        Collections.singletonList(questionBackBriefDTO);

    // Configure QuestionDao.getAllQuestions(...).
    final QuestionBackBriefDTO questionBackBriefDTO1 = new QuestionBackBriefDTO();
    questionBackBriefDTO1.setQuestionId(0L);
    questionBackBriefDTO1.setChoice("choice");
    questionBackBriefDTO1.setDescription("description");
    questionBackBriefDTO1.setQuestionType("questionType");
    final List<QuestionBackBriefDTO> questionBackBriefDTOS =
        Collections.singletonList(questionBackBriefDTO1);
    when(questionDao.getAllQuestions()).thenReturn(questionBackBriefDTOS);

    // Run the test
    final List<QuestionBackBriefDTO> result = testService.getAllQuestions();

    // Verify the results
    assertEquals(expectedResult, result);
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
    final QuestionFormDTO questionForm = new QuestionFormDTO();
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
}
