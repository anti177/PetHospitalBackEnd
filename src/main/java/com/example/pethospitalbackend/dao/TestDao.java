package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.EndTestCategoryDTO;
import com.example.pethospitalbackend.dto.TestCategoryDTO;
import com.example.pethospitalbackend.entity.Test;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
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
}
