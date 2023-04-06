package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dto.ModifiedRecordCountDTO;
import com.example.pethospitalbackend.dto.QuestionBackBriefDTO;
import com.example.pethospitalbackend.dto.QuestionBackDetailDTO;
import com.example.pethospitalbackend.dto.QuestionFormDTO;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.Question;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
public class TestControllerTest extends BaseTest {
  @Autowired private MockMvc mockMvc;

  @InjectMocks private TestController testController;

  @Mock private TestService testService;

  private JacksonTester<Response> responseJacksonTester;
  private JacksonTester<QuestionFormDTO> questionFormDTOJacksonTester;

  @Before
  public void before() {
    JacksonTester.initFields(this, new ObjectMapper());
    mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
  }

  // todo: 测试

  @Test
  public void testGetQuestion() throws Exception {
    // Setup
    // Configure TestService.getQuestion(...).
    final QuestionBackDetailDTO questionBackDetailDTO = new QuestionBackDetailDTO();
    questionBackDetailDTO.setQuestionId(0L);
    questionBackDetailDTO.setQuestionType("questionType");
    questionBackDetailDTO.setDescription("description");
    questionBackDetailDTO.setChoice(Collections.singletonList("value"));
    questionBackDetailDTO.setAns(Collections.singletonList("value"));
    questionBackDetailDTO.setKeyword("keyword");
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    questionBackDetailDTO.setDisease(disease);
    when(testService.getQuestion(0L)).thenReturn(questionBackDetailDTO);
    Response<QuestionBackDetailDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(questionBackDetailDTO);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/questions/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetAllQuestions() throws Exception {
    // Setup
    // Configure TestService.getAllQuestions(...).
    final QuestionBackBriefDTO questionBackBriefDTO = new QuestionBackBriefDTO();
    questionBackBriefDTO.setQuestionId(0L);
    questionBackBriefDTO.setChoice("choice");
    questionBackBriefDTO.setDescription("description");
    questionBackBriefDTO.setQuestionType("questionType");
    final List<QuestionBackBriefDTO> questionBackBriefDTOS =
        Collections.singletonList(questionBackBriefDTO);
    when(testService.getAllQuestions()).thenReturn(questionBackBriefDTOS);
    Response<List<QuestionBackBriefDTO>> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(questionBackBriefDTOS);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/questions").accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testUpdateQuestions() throws Exception {
    // Setup
    when(testService.updateQuestion(any())).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/questions/{id}", 0)
                    .content(questionFormDTOJacksonTester.write(new QuestionFormDTO()).getJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testAddQuestions() throws Exception {
    // Setup
    // Configure TestService.addQuestion(...).
    final Question question = new Question();
    question.setQuestionId(0L);
    question.setQuestionType("questionType");
    question.setDescription("description");
    question.setChoice("choice");
    question.setAns("ans");
    question.setKeyword("keyword");
    question.setDiseaseId(0L);

    QuestionFormDTO questionFormDTO = new QuestionFormDTO();
    BeanUtils.copyProperties(question, questionFormDTO);
    questionFormDTO.setChoice(Collections.singletonList("choice"));
    questionFormDTO.setAns(Collections.singletonList("ans"));

    when(testService.addQuestion(questionFormDTO)).thenReturn(question);
    Response<Question> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(question);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/questions")
                    .content(questionFormDTOJacksonTester.write(questionFormDTO).getJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testDeleteQuestion() throws Exception {
    // Setup
    when(testService.deleteQuestion(0L)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(delete("/questions/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }
}
