package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.TestUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface TestUserDao extends Mapper<TestUser> {
  @Update(
      "UPDATE test_user SET has_tested = #{hasTested} WHERE user_id = #{userId} and test_id = #{testId}")
  int updateTestUserSates(
      @Param("userId") long userId,
      @Param("testId") long testId,
      @Param("hasTested") long hasTested);
}
