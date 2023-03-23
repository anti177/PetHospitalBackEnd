package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.RoleDTO;
import com.example.pethospitalbackend.dto.RoleProcessDTO;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.RolePlayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = {"角色扮演"})
public class RolePlayController {
    
    @Resource
    RolePlayService rolePlayService;
    
    //1是医生，2是护士，3是前台
    @GetMapping("/roles/{roleId}")
    @ApiOperation(value = "获得角色内容和职责")
    public ResponseEntity<Response<RoleDTO>> getRoleContentAndResponsibility(@PathVariable("roleId") String roleId) {
        Response<RoleDTO> response = rolePlayService.getRoleContentAndResponsibility(roleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @GetMapping("/roles/{roleId}/processes")
    @ApiOperation(value = "获得角色流程")
    public ResponseEntity<Response<List<RoleProcessDTO>>> getRoleProcess(@PathVariable("roleId") String roleId) {
        Response<List<RoleProcessDTO>> response = rolePlayService.getRoleProcess(roleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
