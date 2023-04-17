package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.ActorDao;
import com.example.pethospitalbackend.dao.OperationDao;
import com.example.pethospitalbackend.dao.ProcessDao;
import com.example.pethospitalbackend.dao.RelActorProcessDao;
import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Actor;
import com.example.pethospitalbackend.entity.Operation;
import com.example.pethospitalbackend.entity.Process;
import com.example.pethospitalbackend.entity.RelActorProcess;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.exception.ParameterException;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.util.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RolePlayService {
  private static final Logger logger = LoggerFactory.getLogger(RolePlayService.class);

  @Resource private ActorDao actorDao;
  @Resource private RelActorProcessDao relActorProcessDao;
  @Resource private ProcessDao processDao;
  @Resource private OperationDao operationDao;
  @Resource private FileService fileService;

  public Response<RoleDTO> getRoleContentAndResponsibility(String roleId) {
    long id = Long.parseLong(roleId);
    if (!(id == 1 || id == 2 || id == 3)) {
      logger.warn("[Parameter wrong], roleId:{}", SerialUtil.toJsonStr(roleId));
      throw new ParameterException(ResponseEnum.ILLEGAL_PARAM.getMsg());
    }
    RoleDTO roleDTO;
    try {
      roleDTO = actorDao.getActorByActorId(id);
    } catch (Exception e) {
      logger.error(
          "[GetRoleContentAndResponsibility Fail], roleId:{}, error msg:{}",
          SerialUtil.toJsonStr(roleId),
          e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
    Response<RoleDTO> response = new Response<>();
    response.setSuc(roleDTO);
    return response;
  }

  public Response<List<RoleProcessDTO>> getRoleProcess(String roleId) {
    long id = Long.parseLong(roleId);
    if (!(id == 1 || id == 2 || id == 3)) {
      logger.warn("[Parameter wrong], roleId:{}", SerialUtil.toJsonStr(roleId));
      throw new ParameterException(ResponseEnum.ILLEGAL_PARAM.getMsg());
    }
    List<RolePlayOperationDTO> rolePlayOperationDTOList;
    try {
      rolePlayOperationDTOList = actorDao.getActorProcessById(id);
    } catch (Exception e) {
      logger.error(
          "[GetRoleProcess Fail], roleId:{}, error msg:{}",
          SerialUtil.toJsonStr(roleId),
          e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }

    List<RoleProcessDTO> roleProcessDTOList = new ArrayList<>();
    if (rolePlayOperationDTOList != null) {
      Map<Long, RoleProcessDTO> roleProcessDTOMap = new HashMap<>();
      for (RolePlayOperationDTO x : rolePlayOperationDTOList) {
        RoleProcessDTO dto = roleProcessDTOMap.get(x.getId());
        if (dto == null) {
          dto = new RoleProcessDTO();
          dto.setId(x.getId());
          dto.setName(x.getName());
          dto.setIntro(x.getIntro());
          dto.setOperationDTOList(new ArrayList<>());
          roleProcessDTOMap.put(x.getId(), dto);
        }
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setSortNum(x.getSortNum());
        operationDTO.setUrl(x.getUrl());
        operationDTO.setOperationName(x.getOperationName());
        operationDTO.setIntro(x.getOperationIntro());
        dto.getOperationDTOList().add(operationDTO);
      }
      roleProcessDTOList.addAll(roleProcessDTOMap.values());
    }
    Response<List<RoleProcessDTO>> response = new Response<>();
    response.setSuc(roleProcessDTOList);
    return response;
  }

  public List<Actor> getAllActors() {
    try {
      return actorDao.selectAll();
    } catch (Exception e) {
      logger.error("[get all roles Fail], error msg:{}", e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public Actor addActor(ActorFormBackDTO actorFormBackDTO) {
    Actor actor = new Actor();
    BeanUtils.copyProperties(actorFormBackDTO, actor);
    try {
      actorDao.insert(actor);
      Long actorId = actor.getActorId();
      List<Long> processIdList = actorFormBackDTO.getProcessList();
      if (processIdList != null) {
        List<RelActorProcess> relActorProcessList = getRelActorProcessList(processIdList, actorId);
        relActorProcessDao.insertList(relActorProcessList);
      }
      return actor;
    } catch (Exception e) {
      logger.error("[add role Fail], error msg:{}", e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int updateActor(ActorFormBackDTO actorFormBackDTO) {
    Actor actor = new Actor();
    BeanUtils.copyProperties(actorFormBackDTO, actor);
    Long actorId = actor.getActorId();
    try {
      int res = actorDao.updateByPrimaryKey(actor);
      relActorProcessDao.deleteByActorId(actorId);
      List<Long> processIdList = actorFormBackDTO.getProcessList();
      if (processIdList != null) {
        List<RelActorProcess> relActorProcessList = getRelActorProcessList(processIdList, actorId);
        relActorProcessDao.insertList(relActorProcessList);
      }
      return res;
    } catch (Exception e) {
      logger.error(
          "[update role Fail], roleId:{}, error msg:{}",
          SerialUtil.toJsonStr(actorId),
          e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int deleteActor(Long actorId) {
    try {
      relActorProcessDao.deleteByActorId(actorId);
      return actorDao.deleteByPrimaryKey(actorId);
    } catch (Exception e) {
      logger.error(
          "[delete role Fail], roleId:{}, error msg:{}",
          SerialUtil.toJsonStr(actorId),
          e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public ActorDetailBackDTO getActor(Long id) {
    ActorDetailBackDTO actorDetailBackDTO = new ActorDetailBackDTO();
    try {
      Actor actor = actorDao.selectByPrimaryKey(id);
      BeanUtils.copyProperties(actor, actorDetailBackDTO);
      List<Process> list = actorDao.selectRelatedProcessDTOByRoleId(id);
      actorDetailBackDTO.setProcessList(list);
      return actorDetailBackDTO;
    } catch (Exception e) {
      logger.error(
          "[get role Fail], roleId:{}, error msg:{}", SerialUtil.toJsonStr(id), e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int deleteProcess(Long id) {
    try {
      relActorProcessDao.deleteByProcessId(id);
      List<String> fileUrls = operationDao.selectFileUrlByProcessId(id);
      operationDao.deleteByProcessId(id);
      int res = processDao.deleteByPrimaryKey(id);
      for (String url : fileUrls) {
        if (url != null && !url.equals("")) {
          fileService.deleteGraph(url);
        }
      }
      return res;
    } catch (Exception e) {
      logger.error(
          "[delete process Fail], processId:{}, error msg:{}",
          SerialUtil.toJsonStr(id),
          e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public int updateProcess(ProcessFormBackDTO processFormBackDTO) {
    Process process = new Process();
    BeanUtils.copyProperties(processFormBackDTO, process);
    Long processId = process.getProcessId();
    List<Operation> operationList = processFormBackDTO.getOperationList();
    for (Operation operation : operationList) {
      operation.setProcessId(processId);
    }
    try {
      operationDao.deleteByProcessId(processId);
      operationDao.insertList(operationList);
      return processDao.updateByPrimaryKey(process);
    } catch (Exception e) {
      logger.error(
          "[update process Fail], processId:{}, error msg:{}",
          SerialUtil.toJsonStr(processId),
          e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public Process addProcess(ProcessFormBackDTO processFormBackDTO) {
    Process process = new Process();
    BeanUtils.copyProperties(processFormBackDTO, process);
    try {
      processDao.insert(process);
      List<Operation> operationList = processFormBackDTO.getOperationList();
      if (operationList != null) {
        Long processId = process.getProcessId();
        for (Operation operation : operationList) {
          operation.setProcessId(processId);
        }
        operationDao.insertList(processFormBackDTO.getOperationList());
      }
      return process;
    } catch (Exception e) {
      logger.error("[add process Fail], error msg:{}", e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public List<Process> getAllProcesses() {
    try {
      return processDao.selectAll();
    } catch (Exception e) {
      logger.error("[get all processes Fail], error msg:{}", e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public ProcessFormBackDTO getProcess(Long id) {
    try {
      ProcessFormBackDTO processFormBackDTO = new ProcessFormBackDTO();
      Process process = processDao.selectByPrimaryKey(id);
      BeanUtils.copyProperties(process, processFormBackDTO);
      List<Operation> operationList = operationDao.selectByProcessId(id);
      processFormBackDTO.setOperationList(operationList);
      return processFormBackDTO;
    } catch (Exception e) {
      logger.error(
          "[get process Fail], processId:{}, error msg:{}",
          SerialUtil.toJsonStr(id),
          e.getMessage());
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  private List<RelActorProcess> getRelActorProcessList(List<Long> list, Long actorId) {
    List<RelActorProcess> relActorProcessList = new ArrayList<>();
    for (Long processId : list) {
      RelActorProcess relActorProcess = new RelActorProcess();
      relActorProcess.setActorId(actorId);
      relActorProcess.setProcessId(processId);
      relActorProcessList.add(relActorProcess);
    }
    return relActorProcessList;
  }
}
