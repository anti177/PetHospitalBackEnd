package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.Department;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface DepartmentDao extends Mapper<Department> {

  @Select("select department_name from department where department_id = #{id}")
  String selectNameByPrimaryKey(@Param("id") Long id);
}
