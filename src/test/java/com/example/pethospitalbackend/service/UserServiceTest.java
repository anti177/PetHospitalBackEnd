package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dao.UserDao;
import com.example.pethospitalbackend.dto.UserDTO;
import com.example.pethospitalbackend.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest extends BaseTest {
  @InjectMocks @Resource UserService userService;

  @MockBean(name = "userDao")
  UserDao userDao;

  @Before
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testDeleteUser() {
    // Setup
    when(userDao.deleteByPrimaryKey(0L)).thenReturn(1);

    // Run the test
    final int result = userService.deleteUser(0L);

    // Verify the results
    assertEquals(1, result);
  }

  @Test
  public void testUpdateUser() {
    // Setup
    final User user = new User(0L, "password", "role", "email", "userClass");

    // Configure UserDao.selectByPrimaryKey(...).
    final User user1 = new User(0L, "password", "role", "email", "userClass");
    when(userDao.selectByPrimaryKey(0L)).thenReturn(user1);

    when(userDao.updateByPrimaryKeySelective(any())).thenReturn(1);

    // Run the test
    final int result = userService.updateUser(user);

    // Verify the results
    assertEquals(1, result);
  }

  @Test
  public void testDeleteUsers() {
    // Setup
    when(userService.userDao.deleteByIdList(Arrays.asList(0L, 1L))).thenReturn(2);

    // Run the test
    final int result = userService.deleteUsers(Arrays.asList(0L, 1L));

    // Verify the results
    assertEquals(2, result);
  }

  @Test
  public void testGetUserDTOById() {
    // Setup
    final UserDTO expectedResult = new UserDTO(0L, "role", "email", "userClass");

    // Configure UserDao.getUserByUserId(...).
    final UserDTO userDTO = new UserDTO(0L, "role", "email", "userClass");
    when(userDao.getUserByUserId(0L)).thenReturn(userDTO);

    // Run the test
    final UserDTO result = userService.getUserDTOById(0L);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testGetAllUserDTOs() {
    // Setup
    UserDTO userDTO = new UserDTO();
    userDTO.setUserId(0L);
    userDTO.setEmail("xxx");
    userDTO.setRole("xxx");
    userDTO.setUserClass("xxx");

    // Configure UserDao.selectAllUserDTOs(...).
    final List<UserDTO> userDTOS = Collections.singletonList(userDTO);
    when(userDao.selectAllUserDTOs()).thenReturn(userDTOS);

    // Run the test
    final List<UserDTO> result = userService.getAllUserDTOs();

    // Verify the results
    assertEquals(userDTOS, result);
  }
}
