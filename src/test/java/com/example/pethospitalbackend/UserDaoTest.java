package com.example.pethospitalbackend;

import com.example.pethospitalbackend.dto.UserDTO;
import com.example.pethospitalbackend.dao.UserDao;
import com.example.pethospitalbackend.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
	@Autowired
	private UserDao userDao;

	@Test
	public void addUserTest(){
		User user = new User();
		user.setPassword("01012");
		user.setRole("普通用户");
		user.setEmail("1803697047@qq.com");
		int result = userDao.insertUser(user);
		UserDTO a = userDao.getUserByEmail("1803697047@qq.com");
		userDao.deleteByUserId(a.getUserId());
		Assert.assertEquals("普通用户", a.getRole());
	}
}
