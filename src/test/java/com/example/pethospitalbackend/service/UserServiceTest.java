package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dao.UserDao;
import com.example.pethospitalbackend.dto.UserDTO;
import com.example.pethospitalbackend.entity.User;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceTest extends BaseTest {
  @Resource UserService userService;

  @Resource UserDao userDao;

  User user1 = new User(0L, "password", "role", "email", "userClass");
  User user2 = new User(1L, "password", "role", "email", "userClass");

  @Test
  public void testGetAllUsers() {
    userDao.insert(user1);
    userDao.insert(user2);
    List<UserDTO> list = userService.getAllUserDTOs();
    Assert.assertEquals(2, list.size());
  }

  @Test
  public void testUpdateUser() {
    // Setup
    userDao.insert(user1);
    User user = new User();
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
    userDao.insert(user1);
    // Run the test
    int result = userService.deleteUser(1L);

    // Verify the results
    Assert.assertEquals(result, 1);
  }

  @Test
  public void testDeleteUsers() {
    // Setup
    userDao.insert(user1);
    userDao.insert(user2);
    // Run the test
    int result = userService.deleteUsers(Arrays.asList(1L, 2L));

    // Verify the results
    Assert.assertEquals(2, result);
  }

  @Test
  public void testGetUserDTOById() {
    // Setup
    userDao.insert(user1);

    final UserDTO expectedResult = new UserDTO();
    expectedResult.setUserId(1L);
    expectedResult.setRole("role");
    expectedResult.setEmail("email");
    expectedResult.setUserClass("userClass");

    // Run the test
    final UserDTO result = userService.getUserDTOById(1L);

    // Verify the results
    assertEquals(expectedResult, result);
  }
}
