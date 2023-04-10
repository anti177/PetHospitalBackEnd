package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.ActorDao;
import com.example.pethospitalbackend.dto.OperationDTO;
import com.example.pethospitalbackend.dto.RoleDTO;
import com.example.pethospitalbackend.dto.RolePlayOperationDTO;
import com.example.pethospitalbackend.dto.RoleProcessDTO;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.exception.ParameterException;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.util.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RolePlayService {
  private static final Logger logger = LoggerFactory.getLogger(RolePlayService.class);

  @Resource private ActorDao actorDao;

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
}
