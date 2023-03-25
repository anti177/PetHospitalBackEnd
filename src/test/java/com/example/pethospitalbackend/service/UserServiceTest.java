package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dao.UserDao;
import com.example.pethospitalbackend.dto.UserDTO;
import com.example.pethospitalbackend.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class UserServiceTest extends BaseTest {
  @Resource UserService userService;

  @Resource UserDao userDao;

  User user;

  @Before
  public void setup() {
    user = new User();
    user.setUserId(1L);
    user.setPassword("password");
    user.setRole("role");
    user.setEmail("email");
    user.setUserClass("userClass");
  }

  @Test
  public void testGetAllUsers() {
    userDao.insert(user);
    List<UserDTO> list = userService.getAllUsers();
    Assert.assertEquals(1, list.size());
  }

  @Test
  public void testUpdateUser() {
    // Setup
    userDao.insert(user);
    final User user = new User();
    user.setUserId(1L);
    user.setPassword("password1");
    user.setRole("role1");
    user.setEmail("email1");
    user.setUserClass("userClass1");

    // Run the test
    int result = userService.updateUser(user);

    // Verify the results
    Assert.assertEquals(1, result);
  }

  @Test
  public void testDeleteUser() {
    // Setup
    userDao.insert(user);
    // Run the test
    int result = userService.deleteUser(1L);

    // Verify the results
    Assert.assertEquals(result, 1);
  }

  // todo: test deleteUsers & getUserDTOById
}
