package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Actor;
import com.example.pethospitalbackend.entity.Process;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.RolePlayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = {"角色扮演"})
public class RolePlayController {

  @Resource RolePlayService rolePlayService;

  // 1是医生，2是护士，3是前台
  @GetMapping("/roles/{roleId}")
  @ApiOperation(value = "获得角色内容和职责")
  public ResponseEntity<Response<RoleDTO>> getRoleContentAndResponsibility(
      @PathVariable("roleId") String roleId) {
    Response<RoleDTO> response = rolePlayService.getRoleContentAndResponsibility(roleId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/roles/{roleId}/processes")
  @ApiOperation(value = "获得角色流程")
  public ResponseEntity<Response<List<RoleProcessDTO>>> getRoleProcess(
      @PathVariable("roleId") String roleId) {
    Response<List<RoleProcessDTO>> response = rolePlayService.getRoleProcess(roleId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/actors")
  @ApiOperation(value = "管理员获得全部角色内容和职责")
  public Response<List<Actor>> getAllRoles() {
    Response<List<Actor>> response = new Response<>();
    response.setSuc(rolePlayService.getAllActors());
    return response;
  }

  @GetMapping("/actors/{id}")
  @ApiOperation(value = "管理员获得角色具体信息（包含相关流程信息）")
  public Response<ActorDetailBackDTO> getRole(@PathVariable Long id) {
    Response<ActorDetailBackDTO> response = new Response<>();
    response.setSuc(rolePlayService.getActor(id));
    return response;
  }

  @PostMapping("/actors")
  @ApiOperation(value = "管理员添加角色信息")
  public Response<Actor> addRole(@RequestBody ActorFormBackDTO actorFormBackDTO) {
    Response<Actor> response = new Response<>();
    response.setSuc(rolePlayService.addActor(actorFormBackDTO));
    return response;
  }

  @DeleteMapping("/actors/{id}")
  @ApiOperation(value = "管理员删除角色信息")
  public Response<ModifiedRecordCountDTO> deleteRole(@PathVariable Long id) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(rolePlayService.deleteActor(id)));
    return response;
  }

  @PutMapping("/actors/{id}")
  @ApiOperation(value = "管理员修改角色信息")
  public Response<ModifiedRecordCountDTO> updateRole(
      @PathVariable Long id, @RequestBody ActorFormBackDTO actorFormBackDTO) {
    actorFormBackDTO.setActorId(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(rolePlayService.updateActor(actorFormBackDTO)));
    return response;
  }

  @PostMapping("/processes")
  @ApiOperation(value = "管理员添加流程信息")
  public Response<Process> addProcess(@RequestBody ProcessFormBackDTO processFormBackDTO) {
    Response<Process> response = new Response<>();
    response.setSuc(rolePlayService.addProcess(processFormBackDTO));
    return response;
  }

  @PutMapping("/processes/{id}")
  @ApiOperation(value = "管理员修改流程信息")
  public Response<ModifiedRecordCountDTO> updateProcess(
      @PathVariable Long id, @RequestBody ProcessFormBackDTO processFormBackDTO) {
    processFormBackDTO.setProcessId(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(rolePlayService.updateProcess(processFormBackDTO)));
    return response;
  }

  @DeleteMapping("/processes/{id}")
  @ApiOperation(value = "管理员删除流程信息")
  public Response<ModifiedRecordCountDTO> deleteProcess(@PathVariable Long id) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(rolePlayService.deleteProcess(id)));
    return response;
  }

  @GetMapping("/processes")
  @ApiOperation(value = "管理员获得全部流程列表")
  public Response<List<Process>> getAllProcesses() {
    Response<List<Process>> response = new Response<>();
    response.setSuc(rolePlayService.getAllProcesses());
    return response;
  }

  @GetMapping("/processes/{id}")
  @ApiOperation(value = "管理员获取流程具体信息")
  public Response<ProcessFormBackDTO> getProcess(@PathVariable Long id) {
    Response<ProcessFormBackDTO> response = new Response<>();
    response.setSuc(rolePlayService.getProcess(id));
    return response;
  }
}
