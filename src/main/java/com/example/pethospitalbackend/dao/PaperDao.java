package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.Paper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface PaperDao extends Mapper<Paper> {

  @Select("select paper_name from paper where paper_id = #{id}")
  String selectNameByPrimaryKey(@Param("id") Long id);
}
