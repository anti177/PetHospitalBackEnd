package com.example.pethospitalbackend.Dao;

import com.example.pethospitalbackend.DTO.UserDTO;
import com.example.pethospitalbackend.Entity.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserDao {
	@Insert("INSERT INTO user(password,email,role) VALUES (#{password},#{email},#{role});")
	int insertUser(User user);

	@ResultType(UserDTO.class)
	@Select("SELECT user_id as userId,email,role FROM user WHERE user_id = #{userId}")
	UserDTO getUserByUserId(@Param("userId") long userId);

	@ResultType(UserDTO.class)
	@Select("SELECT user_id as userId,email,role FROM user WHERE email = #{email} and password = #{password}")
	UserDTO getUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	@ResultType(UserDTO.class)
	@Select("SELECT user_id as userId,email,role FROM user WHERE email = #{email}")
	UserDTO getUserByEmail(@Param("email") String email);

	@Update("UPDATE user SET password = #{newPassword} WHERE email = #{email}")
	int updatePassword(@Param("email") String email, @Param("newPassword") String newPassword);

	@Delete("Delete From user WHERE user_id=#{userid}")
	int deleteByUserId(@Param("userid") long userid);
}
