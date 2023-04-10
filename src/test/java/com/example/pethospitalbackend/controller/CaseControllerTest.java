package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.IllCase;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.CaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
public class CaseControllerTest extends BaseTest {
  @Autowired private MockMvc mockMvc;

  @InjectMocks private CaseController caseController;

  @Mock private CaseService caseService;

  private JacksonTester<Response> responseJacksonTester;
  private JacksonTester<Disease> diseaseJacksonTester;
  private JacksonTester<CaseBackFormDTO> illCaseFormDTOJacksonTest;

  @Before
  public void before() {
    JacksonTester.initFields(this, new ObjectMapper());
    mockMvc = MockMvcBuilders.standaloneSetup(caseController).build();
  }

  @Test
  public void testGetAllDiseases() throws Exception {
    // Setup
    Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    when(caseService.getAllDiseases()).thenReturn(Collections.singletonList(disease));
    Response<List<Disease>> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(Collections.singletonList(disease));
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/diseases").accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetDisease() throws Exception {
    // Setup
    // Configure CaseService.getDisease(...).
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    when(caseService.getDisease(0L)).thenReturn(disease);
    Response<Disease> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(disease);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/diseases/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testDeleteDisease() throws Exception {
    // Setup
    when(caseService.deleteDisease(0L)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(delete("/diseases/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testPutDisease() throws Exception {
    // Setup
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    when(caseService.updateDisease(disease)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/diseases/{id}", 0)
                    .content(diseaseJacksonTester.write(disease).getJson())
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
  public void testPostDisease() throws Exception {
    // Setup
    // Configure CaseService.addDisease(...).
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    when(caseService.addDisease(disease)).thenReturn(disease);
    Response<Disease> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(disease);
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/diseases")
                    .content(diseaseJacksonTester.write(disease).getJson())
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
  public void testGetAllInspectionItems() throws Exception {
    // Setup
    // Configure CaseService.getAllInspectionItems(...).
    final InspectionItemBackDTO inspectionItemBackDTO = new InspectionItemBackDTO();
    inspectionItemBackDTO.setItemId(0L);
    inspectionItemBackDTO.setItemName("itemName");
    final List<InspectionItemBackDTO> inspectionItemBackDTOS =
        Collections.singletonList(inspectionItemBackDTO);
    when(caseService.getAllInspectionItems()).thenReturn(inspectionItemBackDTOS);
    Response<List<InspectionItemBackDTO>> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(inspectionItemBackDTOS);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/inspections/items").accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testDeleteCase() throws Exception {
    // Setup
    when(caseService.deleteCase(0L)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(delete("/cases/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetAllCases() throws Exception {
    // Setup
    // Configure CaseService.getAllCaseBackBriefDTOs(...).
    final CaseBackBriefDTO caseBackBriefDTO = new CaseBackBriefDTO();
    caseBackBriefDTO.setIllCaseId(0L);
    caseBackBriefDTO.setIllCaseName("illCaseName");
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    caseBackBriefDTO.setDisease(disease);
    final List<CaseBackBriefDTO> caseBackBriefDTOS = Collections.singletonList(caseBackBriefDTO);
    when(caseService.getAllCaseBackBriefDTOs()).thenReturn(caseBackBriefDTOS);
    Response<List<CaseBackBriefDTO>> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(caseBackBriefDTOS);

    // Configure CaseService.getCasePageInfo(...).
    final CaseBackBriefDTO caseBackBriefDTO1 = new CaseBackBriefDTO();
    caseBackBriefDTO1.setIllCaseId(0L);
    caseBackBriefDTO1.setIllCaseName("illCaseName");
    final Disease disease1 = new Disease();
    disease1.setDiseaseId(0L);
    disease1.setDiseaseName("diseaseName");
    disease1.setTypeName("typeName");
    caseBackBriefDTO1.setDisease(disease1);
    final PageInfo<CaseBackBriefDTO> caseBackBriefDTOPageInfo =
        new PageInfo<>(Collections.singletonList(caseBackBriefDTO1), 0);
    when(caseService.getCasePageInfo(0, 0)).thenReturn(caseBackBriefDTOPageInfo);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc.perform(get("/cases").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetCaseByCaseId() throws Exception {
    // Setup
    // Configure CaseService.getFrontCaseByCaseId(...).
    final Response<CaseBackDetailDTO> caseFrontDetailDTOResponse = new Response<>();
    final CaseFrontDetailDTO caseFrontDetailDTO = new CaseFrontDetailDTO();
    caseFrontDetailDTO.setCaseId(0L);
    caseFrontDetailDTO.setCaseName("caseName");
    caseFrontDetailDTO.setAdmissionText("admissionText");
    caseFrontDetailDTO.setAdmissionGraphList(Collections.singletonList("value"));
    final InspectionFrontDTO inspectionFrontDTO = new InspectionFrontDTO();
    inspectionFrontDTO.setInspectionCaseId(0L);
    inspectionFrontDTO.setDepartmentName("departmentName");
    inspectionFrontDTO.setItemName("itemName");
    inspectionFrontDTO.setResult("result");
    inspectionFrontDTO.setIntro("intro");
    inspectionFrontDTO.setFee(0.0);
    inspectionFrontDTO.setInspectionGraphList(Collections.singletonList("value"));
    caseFrontDetailDTO.setInspectionFrontDTOList(Collections.singletonList(inspectionFrontDTO));
    caseFrontDetailDTO.setDiagnosticInfo("diagnosticInfo");
    caseFrontDetailDTO.setTreatmentInfo("treatmentInfo");
    caseFrontDetailDTO.setTreatmentGraphList(Collections.singletonList("value"));
    caseFrontDetailDTO.setTreatmentVideoList(Collections.singletonList("value"));

    // Configure CaseService.getBackCaseDetailDTOByCaseId(...).
    final CaseBackDetailDTO caseBackDetailDTO = new CaseBackDetailDTO();
    caseBackDetailDTO.setCaseId(0L);
    caseBackDetailDTO.setCaseName("caseName");
    final Disease disease = new Disease();
    disease.setDiseaseId(0L);
    disease.setDiseaseName("diseaseName");
    disease.setTypeName("typeName");
    caseBackDetailDTO.setDisease(disease);
    caseBackDetailDTO.setAdmissionText("admissionText");
    final FileDTO fileDTO = new FileDTO();
    fileDTO.setFileId(0L);
    fileDTO.setCaseId(0L);
    fileDTO.setSortNum(0L);
    fileDTO.setUrl("url");
    caseBackDetailDTO.setAdmissionGraphList(Collections.singletonList(fileDTO));
    final InspectionCaseBackDTO inspectionCaseBackDTO = new InspectionCaseBackDTO();
    inspectionCaseBackDTO.setInspectionCaseId(0L);
    final InspectionItemBackDTO inspectionItem = new InspectionItemBackDTO();
    inspectionItem.setItemId(0L);
    inspectionItem.setItemName("itemName");
    inspectionCaseBackDTO.setInspectionItem(inspectionItem);
    inspectionCaseBackDTO.setResult("result");
    final FileDTO fileDTO1 = new FileDTO();
    fileDTO1.setFileId(0L);
    fileDTO1.setCaseId(0L);
    fileDTO1.setSortNum(0L);
    fileDTO1.setUrl("url");
    inspectionCaseBackDTO.setInspectionGraphs(Collections.singletonList(fileDTO1));
    caseBackDetailDTO.setInspectionCaseList(Collections.singletonList(inspectionCaseBackDTO));
    caseBackDetailDTO.setDiagnosticInfo("diagnosticInfo");
    caseBackDetailDTO.setTreatmentInfo("treatmentInfo");
    final FileDTO fileDTO2 = new FileDTO();
    fileDTO2.setFileId(0L);
    fileDTO2.setCaseId(0L);
    fileDTO2.setSortNum(0L);
    fileDTO2.setUrl("url");
    caseBackDetailDTO.setTreatmentGraphList(Collections.singletonList(fileDTO2));
    final FileDTO fileDTO3 = new FileDTO();
    fileDTO3.setFileId(0L);
    fileDTO3.setCaseId(0L);
    fileDTO3.setSortNum(0L);
    fileDTO3.setUrl("url");
    caseBackDetailDTO.setTreatmentVideoList(Collections.singletonList(fileDTO3));
    when(caseService.getBackCaseDetailDTOByCaseId(0L)).thenReturn(caseBackDetailDTO);
    caseFrontDetailDTOResponse.setSuc(caseBackDetailDTO);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get("/cases/{caseId}", 0)
                    .param("front", "false")
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(caseFrontDetailDTOResponse).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testPostCase() throws Exception {
    // Setup
    // Configure CaseService.addCase(...).
    final IllCase illCase = new IllCase();
    illCase.setCaseId(0L);
    illCase.setCaseName("caseName");
    illCase.setDiseaseId(0L);
    illCase.setAdmissionText("admissionText");
    illCase.setDiagnosticInfo("diagnosticInfo");
    illCase.setTreatmentInfo("treatmentInfo");
    illCase.setFrontGraph("frontGraph");
    when(caseService.addCase(any())).thenReturn(illCase);
    CaseBackFormDTO caseBackFormDTO = new CaseBackFormDTO();
    caseBackFormDTO.setCase_id(0L);
    caseBackFormDTO.setCase_title("caseName");
    caseBackFormDTO.setDisease_id(0L);
    caseBackFormDTO.setAdmission_text("admissionText");
    caseBackFormDTO.setDiagnostic_result("diagnosticInfo");
    caseBackFormDTO.setTreatment_info("treatmentInfo");
    caseBackFormDTO.setFront_graph("frontGraph");

    Response<IllCase> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(illCase);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/cases")
                    .content(illCaseFormDTOJacksonTest.write(caseBackFormDTO).getJson())
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
  public void testPutCase() throws Exception {
    // Setup
    CaseBackFormDTO caseBackFormDTO = new CaseBackFormDTO();
    caseBackFormDTO.setCase_id(0L);
    caseBackFormDTO.setCase_title("caseName");
    caseBackFormDTO.setDisease_id(0L);
    caseBackFormDTO.setAdmission_text("admissionText");
    caseBackFormDTO.setDiagnostic_result("diagnosticInfo");
    caseBackFormDTO.setTreatment_info("treatmentInfo");
    caseBackFormDTO.setFront_graph("frontGraph");
    when(caseService.updateCase(any())).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/cases/{id}", 0)
                    .content(illCaseFormDTOJacksonTest.write(caseBackFormDTO).getJson())
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
