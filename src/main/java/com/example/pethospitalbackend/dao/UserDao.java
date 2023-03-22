package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.UserDTO;
import com.example.pethospitalbackend.entity.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserDao {
    
	@Insert("INSERT INTO user(password,email,role,user_class) VALUES (#{password},#{email},#{role},#{user_class});")
	int insertUser(User user);

	@ResultType(UserDTO.class)
	@Select("SELECT user_id as userId,email,role,user_class as userClass FROM user WHERE user_id = #{userId}")
	UserDTO getUserByUserId(@Param("userId") long userId);

	@ResultType(UserDTO.class)
	@Select("SELECT user_id as userId,email,role,user_class as userClass FROM user WHERE email = #{email} and password = #{password}")
	UserDTO getUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	@ResultType(UserDTO.class)
	@Select("SELECT user_id as userId,email,role,user_class as userClass FROM user WHERE email = #{email}")
	UserDTO getUserByEmail(@Param("email") String email);

	@Update("UPDATE user SET password = #{newPassword} WHERE email = #{email}")
	int updatePassword(@Param("email") String email, @Param("newPassword") String newPassword);

	@Update("UPDATE user SET password = #{newPassword} WHERE user_id = #{userId}")
	int updatePasswordByUserId(@Param("userId") String userId, @Param("newPassword") String newPassword);


	@Delete("Delete From user WHERE user_id=#{userid}")
	int deleteByUserId(@Param("userid") long userid);


	@Select("SELECT password FROM user WHERE email = #{email}")
	String getUserPassword(@Param("email") String email);

}
