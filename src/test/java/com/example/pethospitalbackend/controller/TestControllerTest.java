package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.Paper;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
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
  private JacksonTester<QuestionBackFormDTO> questionFormDTOJacksonTester;
  private JacksonTester<PaperBackDTO> paperBackDTOJacksonTester;
  private JacksonTester<TestFormBackDTO> testFormBackDTOJacksonTester;
  private JacksonTester<com.example.pethospitalbackend.entity.Test> testJacksonTester;

  @Before
  public void before() {
    JacksonTester.initFields(this, new ObjectMapper());
    mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
  }

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
    questionBackBriefDTO.setDescription("description");
    questionBackBriefDTO.setQuestionType("questionType");
    questionBackBriefDTO.setKeyword("keyword");
    questionBackBriefDTO.setDiseaseName("diseaseName");
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
                    .content(
                        questionFormDTOJacksonTester.write(new QuestionBackFormDTO()).getJson())
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
  public void testAddQuestion() throws Exception {
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
    QuestionBackFormDTO questionBackFormDTO = new QuestionBackFormDTO();
    BeanUtils.copyProperties(question, questionBackFormDTO);
    questionBackFormDTO.setChoice(Collections.singletonList("choice"));
    questionBackFormDTO.setAns(Collections.singletonList("ans"));

    when(testService.addQuestion(questionBackFormDTO)).thenReturn(question);
    Response<Question> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(question);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/questions")
                    .content(questionFormDTOJacksonTester.write(questionBackFormDTO).getJson())
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

  @Test
  public void testGetAllPapers() throws Exception {
    // Setup
    // Configure TestService.getAllPapers(...).
    final Paper paper = new Paper();
    paper.setPaperId(0L);
    paper.setPaperName("paperName");
    paper.setScore(0L);
    final List<Paper> papers = Collections.singletonList(paper);
    when(testService.getAllPapers()).thenReturn(papers);
    Response<List<Paper>> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(papers);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/papers").accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testUpdatePaper() throws Exception {
    // Setup
    when(testService.updatePaper(any(PaperBackDTO.class))).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));
    final Paper paper = new Paper();
    paper.setPaperId(0L);
    paper.setPaperName("paperName");
    paper.setScore(0L);
    PaperBackDTO paperBackDTO = new PaperBackDTO();
    BeanUtils.copyProperties(paper, paperBackDTO);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/papers/{id}", 0)
                    .content(paperBackDTOJacksonTester.write(paperBackDTO).getJson())
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
  public void testAddPaper() throws Exception {
    // Setup
    // Configure TestService.addPaper(...).
    final Paper paper = new Paper();
    paper.setPaperId(0L);
    paper.setPaperName("paperName");
    paper.setScore(0L);
    when(testService.addPaper(any(PaperBackDTO.class))).thenReturn(paper);
    Response<Paper> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(paper);
    PaperBackDTO paperBackDTO = new PaperBackDTO();
    //    paperBackDTO.setPaper(paper);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/papers")
                    .content(paperBackDTOJacksonTester.write(paperBackDTO).getJson())
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
  public void testDeletePaper() throws Exception {
    // Setup
    when(testService.deletePaper(0L)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(delete("/papers/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetPaper() throws Exception {
    // Setup
    // Configure TestService.getPaperById(...).
    final Paper paper = new Paper();
    paper.setPaperId(0L);
    paper.setPaperName("paperName");
    paper.setScore(0L);
    PaperDetailBackDTO paperDetailBackDTO = new PaperDetailBackDTO();
    BeanUtils.copyProperties(paper, paperDetailBackDTO);
    when(testService.getPaperById(0L)).thenReturn(paperDetailBackDTO);
    Response<PaperDetailBackDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(paperDetailBackDTO);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/papers/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetAllTests() throws Exception {
    // Setup
    // Configure TestService.getAllTests(...).
    final com.example.pethospitalbackend.entity.Test test =
        new com.example.pethospitalbackend.entity.Test();
    test.setTestId(0L);
    test.setTestName("testName");
    test.setIntro("intro");
    test.setTag("tag");
    test.setPaperId(0L);
    test.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    test.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    final List<com.example.pethospitalbackend.entity.Test> tests = Collections.singletonList(test);
    when(testService.getAllTests()).thenReturn(tests);
    Response<List<com.example.pethospitalbackend.entity.Test>> expectedResponseContent =
        new Response<>();
    expectedResponseContent.setSuc(tests);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc.perform(get("/tests").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetTest() throws Exception {
    // Setup
    // Configure TestService.getTest(...).
    final TestDetailBackDTO testDetailBackDTO = new TestDetailBackDTO();
    testDetailBackDTO.setTestId(0L);
    testDetailBackDTO.setTestName("testName");
    testDetailBackDTO.setIntro("intro");
    testDetailBackDTO.setTag("tag");
    testDetailBackDTO.setPaperId(0L);
    testDetailBackDTO.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    testDetailBackDTO.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    when(testService.getTest(0L)).thenReturn(testDetailBackDTO);
    Response<TestDetailBackDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(testDetailBackDTO);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/tests/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testUpdateTest() throws Exception {
    // Setup
    final com.example.pethospitalbackend.entity.Test test =
        new com.example.pethospitalbackend.entity.Test();
    test.setTestId(0L);
    test.setTestName("testName");
    test.setIntro("intro");
    test.setTag("tag");
    test.setPaperId(0L);
    test.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    test.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    TestFormBackDTO testFormBackDTO = new TestFormBackDTO();
    BeanUtils.copyProperties(test, testFormBackDTO);

    when(testService.updateTest(any())).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/tests/{id}", 0)
                    .content(testFormBackDTOJacksonTester.write(testFormBackDTO).getJson())
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
  public void testDeleteTest() throws Exception {
    // Setup
    when(testService.deleteTest(0L)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(delete("/tests/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testAddTest() throws Exception {
    // Setup
    // Configure TestService.addTest(...).
    final com.example.pethospitalbackend.entity.Test test =
        new com.example.pethospitalbackend.entity.Test();
    test.setTestId(0L);
    test.setTestName("testName");
    test.setIntro("intro");
    test.setTag("tag");
    test.setPaperId(0L);
    test.setBeginDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    test.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    TestFormBackDTO testFormBackDTO = new TestFormBackDTO();
    BeanUtils.copyProperties(test, testFormBackDTO);
    when(testService.addTest(any())).thenReturn(test);

    Response<com.example.pethospitalbackend.entity.Test> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(test);
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/tests")
                    .content(testFormBackDTOJacksonTester.write(testFormBackDTO).getJson())
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
}
