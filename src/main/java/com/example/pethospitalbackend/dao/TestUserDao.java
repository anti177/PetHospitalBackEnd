package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.TestUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

public interface TestUserDao extends Mapper<TestUser>, InsertListMapper<TestUser> {
  @Update(
      "UPDATE test_user SET has_Submit = #{hasSubmit} WHERE user_id = #{userId} and test_id = #{testId}")
  int updateTestUserSates(
      @Param("userId") long userId,
      @Param("testId") long testId,
      @Param("hasSubmit") Boolean hasSubmit);

  @Delete("DELETE from test_user where test_id = #{testId}")
  int deleteTestUsersByTestId(@Param("testId") long id);

  @Delete("DELETE from test_user where user_id = #{userId}")
  int deleteTestUserByUserId(@Param("userId") long id);

  @Delete(
      "<script>"
          + "delete from test_user where user_id in "
          + "<foreach collection='ids' open='(' item='id_' separator=',' close=')'> #{id_}"
          + "</foreach>"
          + "</script>")
  int deleteTestUsersByUserIds(@Param("ids") List<Long> id);
}
