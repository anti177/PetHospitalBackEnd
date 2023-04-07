package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dto.InspectionItemBackDTO;
import com.example.pethospitalbackend.dto.ModifiedRecordCountDTO;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.CaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
public class CaseControllerTest extends BaseTest {
  @Autowired private MockMvc mockMvc;

  @InjectMocks private CaseController caseController;

  @Mock private CaseService caseService;

  private JacksonTester<Response> responseJacksonTester;
  private JacksonTester<Disease> diseaseJacksonTester;

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

  // todo: 病例相关接口测试
}
