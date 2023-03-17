package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.RoleDTO;
import com.example.pethospitalbackend.dto.RoleProcessDTO;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.RolePlayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Role")
@Api(tags = {"角色扮演"})
public class RolePlayController {
	@Autowired
	RolePlayService rolePlayService;

	//1是医生，2是护士，3是前台
	@GetMapping("/RoleContentAndResponsibility")
	@ApiOperation(value = "获得角色内容和职责")
	public ResponseEntity<Response<RoleDTO>> getRoleContentAndResponsibility(@RequestParam("roleId") String roleId) {
		Response<RoleDTO> response =  rolePlayService.getRoleContentAndResponsibility(roleId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@GetMapping("/RoleProcess")
	@ApiOperation(value = "获得角色流程")
	public ResponseEntity<Response<List<RoleProcessDTO>>> getRoleProcess(@RequestParam("roleId") String roleId) {
		Response<List<RoleProcessDTO>> response =  rolePlayService.getRoleProcess(roleId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
