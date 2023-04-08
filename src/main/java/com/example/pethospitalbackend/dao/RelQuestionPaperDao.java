package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.RelQuestionPaper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface RelQuestionPaperDao
    extends Mapper<RelQuestionPaper>, InsertListMapper<RelQuestionPaper> {

  @Select("select exists(select 1 from rel_question_paper where question_id=#{questionId})")
  boolean existsWithQuestionId(@Param("questionId") Long questionId);

  @Delete("delete from rel_question_paper where paper_id=#{paperId}")
  int deleteByPaperId(@Param("paperId") Long id);
}
