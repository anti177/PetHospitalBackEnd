package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.Operation;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

public interface OperationDao extends Mapper<Operation>, InsertListMapper<Operation> {

  @Delete("DELETE from operation where process_id = #{processId}")
  int deleteByProcessId(@Param("processId") Long id);

  @Select("SELECT * from operation where process_id = #{processId} ORDER BY sort_num")
  @Results(
      id = "operation_list",
      value = {
        @Result(id = true, column = "operation_id", property = "operationId"),
        @Result(column = "operation_name", property = "operationName"),
        @Result(column = "operation_intro", property = "operationIntro"),
        @Result(column = "process_id", property = "processId"),
        @Result(column = "sort_num", property = "sortNum"),
        @Result(column = "url", property = "url"),
      })
  List<Operation> selectByProcessId(@Param("processId") Long id);
}
