package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.RelActorProcess;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface RelActorProcessDao
    extends Mapper<RelActorProcess>, InsertListMapper<RelActorProcess> {

  @Delete("DELETE from rel_actor_process where actor_id = #{actorId}")
  int deleteByActorId(@Param("actorId") Long actorId);
}
