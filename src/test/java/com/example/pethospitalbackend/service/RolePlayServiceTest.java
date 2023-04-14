package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dao.ActorDao;
import com.example.pethospitalbackend.dao.OperationDao;
import com.example.pethospitalbackend.dao.ProcessDao;
import com.example.pethospitalbackend.dao.RelActorProcessDao;
import com.example.pethospitalbackend.dto.ActorDetailBackDTO;
import com.example.pethospitalbackend.dto.ActorFormBackDTO;
import com.example.pethospitalbackend.dto.ProcessBriefBackDTO;
import com.example.pethospitalbackend.dto.ProcessFormBackDTO;
import com.example.pethospitalbackend.entity.Actor;
import com.example.pethospitalbackend.entity.Operation;
import com.example.pethospitalbackend.entity.Process;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RolePlayServiceTest extends BaseTest {
  @InjectMocks @Resource RolePlayService rolePlayService;

  @MockBean(name = "actorDao")
  ActorDao actorDao;

  @MockBean(name = "relActorProcessDao")
  RelActorProcessDao relActorProcessDao;

  @MockBean(name = "processDao")
  ProcessDao processDao;

  @MockBean(name = "operationDao")
  OperationDao operationDao;

  @MockBean(name = "fileService")
  FileService fileService;

  @Before
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testAddActor() {
    // Setup
    final ActorFormBackDTO actorFormBackDTO = new ActorFormBackDTO();
    actorFormBackDTO.setActorId(0L);
    actorFormBackDTO.setName("name");
    actorFormBackDTO.setContent("content");
    actorFormBackDTO.setResponsibility("responsibility");
    actorFormBackDTO.setProcessList(Collections.singletonList(0L));

    final Actor expectedResult = new Actor();
    expectedResult.setActorId(0L);
    expectedResult.setName("name");
    expectedResult.setContent("content");
    expectedResult.setResponsibility("responsibility");

    when(actorDao.insert(any())).thenReturn(1);
    when(relActorProcessDao.insertList(anyList())).thenReturn(1);

    // Run the test
    final Actor result = rolePlayService.addActor(actorFormBackDTO);

    // Verify the results
    assertEquals(expectedResult, result);
    verify(actorDao).insert(new Actor());
  }

  @Test
  public void testDeleteActor() {
    // Setup
    when(relActorProcessDao.deleteByActorId(0L)).thenReturn(1);
    when(actorDao.deleteByPrimaryKey(0L)).thenReturn(1);

    // Run the test
    final int result = rolePlayService.deleteActor(0L);

    // Verify the results
    assertEquals(1, result);
    verify(relActorProcessDao).deleteByActorId(0L);
  }

  @Test
  public void testGetActor() {
    // Setup
    final ActorDetailBackDTO expectedResult = new ActorDetailBackDTO();
    expectedResult.setActorId(0L);
    expectedResult.setName("name");
    expectedResult.setContent("content");
    expectedResult.setResponsibility("responsibility");
    final ProcessBriefBackDTO processBriefBackDTO = new ProcessBriefBackDTO();
    processBriefBackDTO.setProcessId(0L);
    processBriefBackDTO.setProcessName("processName");
    expectedResult.setProcessList(Collections.singletonList(processBriefBackDTO));

    // Configure ActorDao.selectByPrimaryKey(...).
    final Actor actor = new Actor();
    actor.setActorId(0L);
    actor.setName("name");
    actor.setContent("content");
    actor.setResponsibility("responsibility");
    when(actorDao.selectByPrimaryKey(0L)).thenReturn(actor);

    // Configure ActorDao.selectRelatedProcessNameByRoleId(...).
    final ProcessBriefBackDTO processBriefBackDTO1 = new ProcessBriefBackDTO();
    processBriefBackDTO1.setProcessId(0L);
    processBriefBackDTO1.setProcessName("processName");
    final List<ProcessBriefBackDTO> processBriefBackDTOS =
        Collections.singletonList(processBriefBackDTO1);
    when(actorDao.selectRelatedProcessNameByRoleId(0L)).thenReturn(processBriefBackDTOS);

    // Run the test
    final ActorDetailBackDTO result = rolePlayService.getActor(0L);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testGetAllActors() {
    // Setup
    final Actor actor = new Actor();
    actor.setActorId(0L);
    actor.setName("name");
    actor.setContent("content");
    actor.setResponsibility("responsibility");
    final List<Actor> expectedResult = Collections.singletonList(actor);

    // Configure ActorDao.selectAll(...).
    final Actor actor1 = new Actor();
    actor1.setActorId(0L);
    actor1.setName("name");
    actor1.setContent("content");
    actor1.setResponsibility("responsibility");
    final List<Actor> actors = Collections.singletonList(actor1);
    when(actorDao.selectAll()).thenReturn(actors);

    // Run the test
    final List<Actor> result = rolePlayService.getAllActors();

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testUpdateActor() {
    // Setup
    final ActorFormBackDTO actorFormBackDTO = new ActorFormBackDTO();
    actorFormBackDTO.setActorId(0L);
    actorFormBackDTO.setName("name");
    actorFormBackDTO.setContent("content");
    actorFormBackDTO.setResponsibility("responsibility");
    actorFormBackDTO.setProcessList(Collections.singletonList(0L));

    when(relActorProcessDao.deleteByActorId(0L)).thenReturn(1);
    when(relActorProcessDao.insertList(anyList())).thenReturn(1);
    when(actorDao.updateByPrimaryKey(any())).thenReturn(1);

    // Run the test
    final int result = rolePlayService.updateActor(actorFormBackDTO);

    // Verify the results
    assertEquals(1, result);
    verify(relActorProcessDao).deleteByActorId(0L);
    verify(relActorProcessDao).insertList(anyList());
  }

  @Test
  public void testGetProcess() {
    // Setup
    final ProcessFormBackDTO expectedResult = new ProcessFormBackDTO();
    expectedResult.setProcessId(0L);
    expectedResult.setProcessName("processName");
    expectedResult.setIntro("intro");
    final Operation operation = new Operation();
    operation.setOperationId(0L);
    operation.setProcessId(0L);
    operation.setOperationName("operationName");
    operation.setIntro("operationIntro");
    operation.setUrl("url");
    operation.setSortNum(0L);
    expectedResult.setOperationList(Collections.singletonList(operation));

    // Configure ProcessDao.selectByPrimaryKey(...).
    final Process process = new Process();
    process.setProcessId(0L);
    process.setProcessName("processName");
    process.setIntro("intro");
    when(processDao.selectByPrimaryKey(0L)).thenReturn(process);

    // Configure OperationDao.selectByProcessId(...).
    final Operation operation1 = new Operation();
    operation1.setOperationId(0L);
    operation1.setProcessId(0L);
    operation1.setOperationName("operationName");
    operation1.setIntro("operationIntro");
    operation1.setUrl("url");
    operation1.setSortNum(0L);
    final List<Operation> operationList = Collections.singletonList(operation1);
    when(operationDao.selectByProcessId(0L)).thenReturn(operationList);

    // Run the test
    final ProcessFormBackDTO result = rolePlayService.getProcess(0L);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testAddProcess() {
    // Setup
    final ProcessFormBackDTO processFormBackDTO = new ProcessFormBackDTO();
    processFormBackDTO.setProcessId(0L);
    processFormBackDTO.setProcessName("processName");
    processFormBackDTO.setIntro("intro");
    final Operation operation = new Operation();
    operation.setOperationId(0L);
    operation.setProcessId(0L);
    operation.setOperationName("operationName");
    operation.setIntro("operationIntro");
    operation.setUrl("url");
    operation.setSortNum(0L);
    processFormBackDTO.setOperationList(Collections.singletonList(operation));

    final Process expectedResult = new Process();
    expectedResult.setProcessId(0L);
    expectedResult.setProcessName("processName");
    expectedResult.setIntro("intro");

    when(processDao.insert(any())).thenReturn(1);
    when(operationDao.insertList(anyList())).thenReturn(1);

    // Run the test
    final Process result = rolePlayService.addProcess(processFormBackDTO);

    // Verify the results
    assertEquals(expectedResult, result);
    verify(processDao).insert(any());
    verify(operationDao).insertList(anyList());
  }

  @Test
  public void testUpdateProcess() {
    // Setup
    final ProcessFormBackDTO processFormBackDTO = new ProcessFormBackDTO();
    processFormBackDTO.setProcessId(0L);
    processFormBackDTO.setProcessName("processName");
    processFormBackDTO.setIntro("intro");
    final Operation operation = new Operation();
    operation.setOperationId(0L);
    operation.setProcessId(0L);
    operation.setOperationName("operationName");
    operation.setIntro("operationIntro");
    operation.setUrl("url");
    operation.setSortNum(0L);
    processFormBackDTO.setOperationList(Collections.singletonList(operation));

    when(operationDao.deleteByProcessId(0L)).thenReturn(1);
    when(operationDao.insertList(anyList())).thenReturn(1);
    when(processDao.updateByPrimaryKey(any())).thenReturn(1);

    // Run the test
    final int result = rolePlayService.updateProcess(processFormBackDTO);

    // Verify the results
    assertEquals(1, result);
    verify(operationDao).deleteByProcessId(0L);
    verify(operationDao).insertList(anyList());
  }

  @Test
  public void testGetAllProcesses() {
    // Setup
    final Process process = new Process();
    process.setProcessId(0L);
    process.setProcessName("processName");
    process.setIntro("intro");
    final List<Process> expectedResult = Collections.singletonList(process);

    // Configure ProcessDao.selectAll(...).
    final Process process1 = new Process();
    process1.setProcessId(0L);
    process1.setProcessName("processName");
    process1.setIntro("intro");
    final List<Process> processes = Collections.singletonList(process1);
    when(processDao.selectAll()).thenReturn(processes);

    // Run the test
    final List<Process> result = rolePlayService.getAllProcesses();

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testDeleteProcess() {
    // Setup
    when(relActorProcessDao.deleteByProcessId(0L)).thenReturn(1);
    when(operationDao.selectFileUrlByProcessId(0L)).thenReturn(Collections.singletonList("value"));
    when(operationDao.deleteByProcessId(0L)).thenReturn(1);
    when(processDao.deleteByPrimaryKey(0L)).thenReturn(1);
    when(fileService.deleteGraphs(Collections.singletonList("value"))).thenReturn(true);

    // Run the test
    final int result = rolePlayService.deleteProcess(0L);

    // Verify the results
    assertEquals(1, result);
    verify(relActorProcessDao).deleteByProcessId(0L);
    verify(operationDao).deleteByProcessId(0L);
    verify(fileService).deleteGraphs(Collections.singletonList("value"));
  }
}
