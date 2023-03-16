package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.ActorDao;
import com.example.pethospitalbackend.dto.OperationDTO;
import com.example.pethospitalbackend.dto.RoleDTO;
import com.example.pethospitalbackend.dto.RolePlayOperationDTO;
import com.example.pethospitalbackend.dto.RoleProcessDTO;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.exception.ParameterException;
import com.example.pethospitalbackend.response.Response;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RolePlayService {
	@Autowired
	private ActorDao actorDao;

	public Response<RoleDTO> getRoleContentAndResponsibility(String roleId) {
		long id = Long.parseLong(roleId);
		if(!(id == 1 || id == 2 || id ==3)){
			throw new ParameterException("参数错误");
		}
		RoleDTO roleDTO;
		try{
			roleDTO = actorDao.getActorByActorId(id);
		}catch (Exception e){
			throw new DatabaseException(e.getMessage());
		}
		Response<RoleDTO> response = new Response<>();
		response.setSuc(roleDTO);
		return response;
	}

	public Response<List<RoleProcessDTO>> getRoleProcess(String roleId) {
		long id = Long.parseLong(roleId);
		if(!(id == 1 || id == 2 || id ==3)){
			throw new ParameterException("参数错误");
		}
		List<RolePlayOperationDTO> rolePlayOperationDTOList;
		try{
			rolePlayOperationDTOList = actorDao.getActorProcessById(id);
		}catch (Exception e){
			throw new DatabaseException(e.getMessage());
		}

		List<RoleProcessDTO> roleProcessDTOList = new ArrayList<>();
		if(rolePlayOperationDTOList != null) {
			Map<Long, RoleProcessDTO> roleProcessDTOMap = new HashMap<>();
			for (RolePlayOperationDTO x: rolePlayOperationDTOList ){
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
				dto.getOperationDTOList().add(operationDTO);
			}
			roleProcessDTOList.addAll(roleProcessDTOMap.values());
		}
		Response<List<RoleProcessDTO>> response = new Response<>();
		response.setSuc(roleProcessDTOList);
		return response;
	}
}
