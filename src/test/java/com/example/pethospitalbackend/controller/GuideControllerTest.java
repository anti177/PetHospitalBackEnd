package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dto.ModifiedRecordCountDTO;
import com.example.pethospitalbackend.entity.Department;
import com.example.pethospitalbackend.entity.Drug;
import com.example.pethospitalbackend.entity.Vaccine;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.DepartmentService;
import com.example.pethospitalbackend.service.DrugService;
import com.example.pethospitalbackend.service.VaccineService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
public class GuideControllerTest extends BaseTest {
  @Autowired private MockMvc mockMvc;

  @InjectMocks private GuideController guideController;

  @Mock private DepartmentService departmentService;

  @Mock private DrugService drugService;

  @Mock private VaccineService vaccineService;

  private JacksonTester<Response> responseJacksonTester;
  private JacksonTester<Drug> drugJacksonTester;
  private JacksonTester<Vaccine> vaccineJacksonTester;
  private JacksonTester<Department> departmentJacksonTester;

  @Before
  public void before() {
    JacksonTester.initFields(this, new ObjectMapper());
    mockMvc = MockMvcBuilders.standaloneSetup(guideController).build();
  }

  @Test
  public void testAddDrug() throws Exception {
    // Setup
    // Configure DrugService.addDrug(...).
    final Drug drug = new Drug();
    drug.setId(0L);
    drug.setName("name");
    drug.setType("type");
    drug.setIntro("intro");
    drug.setPrice(0.0);
    drug.setUrl("url");
    when(drugService.addDrug(drug)).thenReturn(drug);
    Response<Drug> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(drug);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/drugs")
                    .content(drugJacksonTester.write(drug).getJson())
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
  public void testUpdateDrug() throws Exception {
    // Setup
    final Drug drug = new Drug();
    drug.setId(0L);
    drug.setName("name");
    drug.setType("type");
    drug.setIntro("intro");
    drug.setPrice(0.0);
    drug.setUrl("url");
    when(drugService.updateDrug(any())).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/drugs/{id}", 0)
                    .content(drugJacksonTester.write(drug).getJson())
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
  public void testDeleteDrug() throws Exception {
    /// Setup
    when(drugService.deleteDrug(0L)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(delete("/drugs/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testAddVaccine() throws Exception {
    // Setup
    // Configure VaccineService.addVaccine(...).
    final Vaccine vaccine = new Vaccine();
    vaccine.setId(0L);
    vaccine.setName("name");
    vaccine.setIntro("intro");
    when(vaccineService.addVaccine(any())).thenReturn(vaccine);
    Response<Vaccine> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(vaccine);
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/vaccines")
                    .content(vaccineJacksonTester.write(vaccine).getJson())
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
  public void testUpdateVaccine() throws Exception {
    // Setup
    final Vaccine vaccine = new Vaccine();
    vaccine.setId(0L);
    vaccine.setName("name");
    vaccine.setIntro("intro");
    when(vaccineService.updateVaccine(any())).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/vaccines/{id}", 0)
                    .content(vaccineJacksonTester.write(vaccine).getJson())
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
  public void testDeleteVaccine() throws Exception {
    // Setup
    when(vaccineService.deleteVaccine(0L)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(delete("/vaccines/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testUpdateDepartment() throws Exception {
    // Setup
    Department department = new Department();
    department.setDepartmentId(0L);
    department.setDepartmentName("xx");
    department.setIntro("123");
    department.setPeopleList("执业医师");
    when(departmentService.updateDepartment(department)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/departments/{id}", 0)
                    .content(departmentJacksonTester.write(department).getJson())
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
  public void testGetAllDepartment() throws Exception {
    // Setup
    // Configure DepartmentService.getAllDepartmentInfo(...).
    final Response<List<Department>> listResponse = new Response<>();
    listResponse.setSuccess(false);
    listResponse.setStatus(0);
    listResponse.setMessage("message");
    final Department department = new Department();
    department.setDepartmentId(0L);
    department.setDepartmentName("departmentName");
    department.setIntro("intro");
    department.setPeopleList("peopleList");
    listResponse.setResult(Collections.singletonList(department));
    when(departmentService.getAllDepartmentInfo()).thenReturn(listResponse);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/departments").accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(listResponse).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetAllDrugs() throws Exception {
    // Setup
    // Configure DrugService.getAllDrugs(...).
    final Response<List<Drug>> listResponse = new Response<>();
    listResponse.setSuccess(false);
    listResponse.setStatus(0);
    listResponse.setMessage("message");
    final Drug drug = new Drug();
    drug.setId(0L);
    drug.setName("name");
    drug.setType("type");
    drug.setIntro("intro");
    drug.setPrice(0.0);
    drug.setUrl("url");
    listResponse.setResult(Collections.singletonList(drug));
    when(drugService.getAllDrugs()).thenReturn(listResponse);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc.perform(get("/drugs").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(listResponse).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetAllVaccine() throws Exception {
    // Setup
    // Configure VaccineService.getAllVaccines(...).
    final Response<List<Vaccine>> listResponse = new Response<>();
    listResponse.setSuccess(false);
    listResponse.setStatus(0);
    listResponse.setMessage("message");
    final Vaccine vaccine = new Vaccine();
    vaccine.setId(0L);
    vaccine.setName("name");
    vaccine.setIntro("intro");
    listResponse.setResult(Collections.singletonList(vaccine));
    when(vaccineService.getAllVaccines()).thenReturn(listResponse);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/vaccines").accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(listResponse).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }
}
