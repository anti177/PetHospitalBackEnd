package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.EndTestCategoryDTO;
import com.example.pethospitalbackend.dto.TestCategoryDTO;
import com.example.pethospitalbackend.dto.TestDetailBackDTO;
import com.example.pethospitalbackend.entity.Test;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TestDao extends Mapper<Test> {

  // 返回还未开始的考试列表(正在考试期间，已经提交了答案的考试不在其中)
  @ResultType(TestCategoryDTO.class)
  @Select(
      "SELECT test.test_id as test_id, begin_date as beginDate, end_date as endDate, test_name as testName, intro, tag  "
          + "from test JOIN test_user on test.test_id = test_user.test_id "
          + "WHERE test_user.user_id = #{id} and NOW() < begin_date and test_user.has_submit = 0 "
          + "ORDER BY begin_date")
  List<TestCategoryDTO> getTestCategoryByUserId(@Param("id") long userId);

  // 返回已经结束的考试列表
  @ResultType(EndTestCategoryDTO.class)
  @Select(
      "SELECT test.test_id as testId, begin_date as beginDate, end_date as endDate, intro, tag, score, test_name as testName, has_submit as hasSubmit  "
          + "from test NATURAL JOIN test_user NATURAL JOIN  test_record WHERE test_user.user_id = #{id} and (end_date < NOW() or has_submit > 0); ")
  List<EndTestCategoryDTO> getEndTestCategoryByUserId(@Param("id") long userId);

  @Select("select exists(select 1 from test where paper_id=#{paperId})")
  boolean existsWithPaperId(@Param("paperId") Long id);

  @Select(
      "select test_id, begin_date, end_date, test_name, paper_id, intro, tag from test where test_id = #{testId}")
  @Results(
      id = "test",
      value = {
        @Result(id = true, column = "test_id", property = "testId"),
        @Result(column = "test_name", property = "testName"),
        @Result(column = "begin_date", property = "beginDate"),
        @Result(column = "end_date", property = "endDate"),
        @Result(
            property = "paperName",
            column = "paper_id",
            javaType = String.class,
            one =
                @One(
                    select = "com.example.pethospitalbackend.dao.paperDao.selectNameByPrimaryKey",
                    fetchType = FetchType.EAGER)),
        @Result(column = "intro", property = "intro"),
        @Result(column = "tag", property = "tag"),
      })
  TestDetailBackDTO selectDetailBackDTOById(@Param("testId") Long id);
}
