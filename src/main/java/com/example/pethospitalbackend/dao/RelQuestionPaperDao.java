package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.RelQuestionPaper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface RelQuestionPaperDao extends Mapper<RelQuestionPaper> {

  @Select("select exists(select 1 from rel_question_paper where question_id=#{questionId})")
  boolean existsWithQuestionId(@Param("questionId") Long questionId);
}
