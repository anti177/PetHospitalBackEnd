package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dto.ActorDetailBackDTO;
import com.example.pethospitalbackend.dto.ActorFormBackDTO;
import com.example.pethospitalbackend.dto.ModifiedRecordCountDTO;
import com.example.pethospitalbackend.dto.ProcessFormBackDTO;
import com.example.pethospitalbackend.entity.Actor;
import com.example.pethospitalbackend.entity.Operation;
import com.example.pethospitalbackend.entity.Process;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.RolePlayService;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
public class RolePlayControllerTest extends BaseTest {
  @Autowired private MockMvc mockMvc;

  @InjectMocks private RolePlayController rolePlayController;

  @Mock private RolePlayService rolePlayService;

  private JacksonTester<Response> responseJacksonTester;
  private JacksonTester<ActorFormBackDTO> actorFormBackDTOJacksonTester;
  private JacksonTester<ProcessFormBackDTO> processFormBackDTOJacksonTester;

  @Before
  public void before() {
    JacksonTester.initFields(this, new ObjectMapper());
    mockMvc = MockMvcBuilders.standaloneSetup(rolePlayController).build();
  }

  @Test
  public void testGetRole() throws Exception {
    // Setup
    // Configure RolePlayService.getActor(...).
    final ActorDetailBackDTO actorDetailBackDTO = new ActorDetailBackDTO();
    actorDetailBackDTO.setActorId(0L);
    actorDetailBackDTO.setName("name");
    actorDetailBackDTO.setContent("content");
    actorDetailBackDTO.setResponsibility("responsibility");
    actorDetailBackDTO.setProcessList(Collections.singletonList("value"));
    when(rolePlayService.getActor(0L)).thenReturn(actorDetailBackDTO);
    Response<ActorDetailBackDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(actorDetailBackDTO);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/actors/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testAddRole() throws Exception {
    // Setup
    // Configure RolePlayService.addActor(...).
    final Actor actor = new Actor();
    actor.setActorId(0L);
    actor.setName("name");
    actor.setContent("content");
    actor.setResponsibility("responsibility");
    ActorFormBackDTO actorFormBackDTO = new ActorFormBackDTO();
    BeanUtils.copyProperties(actor, actorFormBackDTO);
    actorFormBackDTO.setProcessList(Arrays.asList(1L, 2L));

    when(rolePlayService.addActor(any())).thenReturn(actor);
    Response<Actor> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(actor);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/actors")
                    .content(actorFormBackDTOJacksonTester.write(actorFormBackDTO).getJson())
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
  public void testUpdateRole() throws Exception {
    // Setup
    when(rolePlayService.updateActor(any())).thenReturn(1);
    final Actor actor = new Actor();
    actor.setActorId(0L);
    actor.setName("name");
    actor.setContent("content");
    actor.setResponsibility("responsibility");
    ActorFormBackDTO actorFormBackDTO = new ActorFormBackDTO();
    BeanUtils.copyProperties(actor, actorFormBackDTO);
    actorFormBackDTO.setProcessList(Arrays.asList(1L, 2L));
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/actors/{id}", 0)
                    .content(actorFormBackDTOJacksonTester.write(actorFormBackDTO).getJson())
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
  public void testDeleteRole() throws Exception {
    // Setup
    when(rolePlayService.deleteActor(0L)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(delete("/actors/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetAllRoles() throws Exception {
    // Setup
    // Configure RolePlayService.getAllActors(...).
    final Actor actor = new Actor();
    actor.setActorId(0L);
    actor.setName("name");
    actor.setContent("content");
    actor.setResponsibility("responsibility");
    final List<Actor> actors = Collections.singletonList(actor);
    when(rolePlayService.getAllActors()).thenReturn(actors);
    Response<List<Actor>> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(actors);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/actors").accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetProcess() throws Exception {
    // Setup
    // Configure RolePlayService.getProcess(...).
    final ProcessFormBackDTO processFormBackDTO = new ProcessFormBackDTO();
    processFormBackDTO.setProcessId(0L);
    processFormBackDTO.setProcessName("processName");
    processFormBackDTO.setIntro("intro");
    final Operation operation = new Operation();
    operation.setOperationId(0L);
    operation.setProcessId(0L);
    operation.setOperationName("operationName");
    operation.setOperationIntro("operationIntro");
    operation.setUrl("url");
    operation.setSortNum(0L);
    processFormBackDTO.setOperationList(Collections.singletonList(operation));
    when(rolePlayService.getProcess(0L)).thenReturn(processFormBackDTO);
    Response<ProcessFormBackDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(processFormBackDTO);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/processes/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetAllProcesses() throws Exception {
    // Setup
    // Configure RolePlayService.getAllProcesses(...).
    final Process process = new Process();
    process.setProcessId(0L);
    process.setProcessName("processName");
    process.setIntro("intro");
    final List<Process> processes = Collections.singletonList(process);
    when(rolePlayService.getAllProcesses()).thenReturn(processes);
    Response<List<Process>> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(processes);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/processes").accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testAddProcess() throws Exception {
    // Setup
    // Configure RolePlayService.addProcess(...).
    final Process process = new Process();
    process.setProcessId(0L);
    process.setProcessName("processName");
    process.setIntro("intro");
    ProcessFormBackDTO processFormBackDTO = new ProcessFormBackDTO();
    BeanUtils.copyProperties(process, processFormBackDTO);
    when(rolePlayService.addProcess(any())).thenReturn(process);
    Response<Process> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(process);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/processes")
                    .content(processFormBackDTOJacksonTester.write(processFormBackDTO).getJson())
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
  public void testDeleteProcess() throws Exception {
    // Setup
    when(rolePlayService.deleteProcess(0L)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(delete("/processes/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testUpdateProcess() throws Exception {
    // Setup
    final Process process = new Process();
    process.setProcessId(0L);
    process.setProcessName("processName");
    process.setIntro("intro");
    ProcessFormBackDTO processFormBackDTO = new ProcessFormBackDTO();
    BeanUtils.copyProperties(process, processFormBackDTO);
    when(rolePlayService.updateProcess(any())).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/processes/{id}", 0)
                    .content(processFormBackDTOJacksonTester.write(processFormBackDTO).getJson())
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
