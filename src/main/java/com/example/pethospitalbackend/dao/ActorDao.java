package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.RoleDTO;
import com.example.pethospitalbackend.dto.RolePlayOperationDTO;
import com.example.pethospitalbackend.entity.Actor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

public interface ActorDao extends tk.mybatis.mapper.common.Mapper<Actor> {
  @ResultType(RoleDTO.class)
  @Select(
      "SELECT actor_id as roleId,name,content,responsibility FROM actor WHERE actor_id = #{actorId}")
  RoleDTO getActorByActorId(@Param("actorId") long actorId);

  @ResultType(RolePlayOperationDTO.class)
  @Select(
      "SELECT process.process_id as id,process.intro as intro,process_name as name, operation.sort_num as sortNum, operation.intro as operationIntro, operation.operation_name as operationName, operation.url as url "
          + "FROM process join operation on process.process_id = operation.process_id "
          + "where process.process_id "
          + "IN  (SELECT process_id FROM rel_actor_process WHERE #{actorId}) ORDER BY id, operation_id")
  ArrayList<RolePlayOperationDTO> getActorProcessById(@Param("actorId") long actorId);

  @ResultType(List.class)
  @Select(
      "SELECT process.process_name from rel_actor_process JOIN process on rel_actor_process.process_id = process.process_id "
          + "WHERE rel_actor_process.actor_id = #{actorId}")
  List<String> selectRelatedProcessNameByRoleId(@Param("actorId") long id);
}
