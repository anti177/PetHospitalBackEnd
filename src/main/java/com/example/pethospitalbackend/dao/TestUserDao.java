package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.TestUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface TestUserDao extends Mapper<TestUser>, InsertListMapper<TestUser> {
  @Update(
      "UPDATE test_user SET has_Submit = #{hasSubmit} WHERE user_id = #{userId} and test_id = #{testId}")
  int updateTestUserSates(
      @Param("userId") long userId,
      @Param("testId") long testId,
      @Param("hasSubmit") Boolean hasSubmit);

  @Delete("DELETE from test_user where test_id = #{testId}")
  int deleteTestUsers(@Param("testId") Long id);
}
