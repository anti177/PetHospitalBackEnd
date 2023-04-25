package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dao.TestUserDao;
import com.example.pethospitalbackend.dao.UserDao;
import com.example.pethospitalbackend.dto.JwtUserDTO;import com.example.pethospitalbackend.dto.UserDTO;
import com.example.pethospitalbackend.entity.User;
import com.example.pethospitalbackend.request.ForgetPasswordRequest;import com.example.pethospitalbackend.request.UserLoginRequest;import com.example.pethospitalbackend.request.UserRegisterRequest;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.util.EmailUtil;import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.security.GeneralSecurityException;import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest extends BaseTest {
  @InjectMocks @Resource UserService userService;

  @MockBean(name = "userDao")
  UserDao userDao;

  @MockBean(name = "testUserDao")
  TestUserDao testUserDao;

  @MockBean(name = "emailUtil")
  EmailUtil emailUtil;

  @Before
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testUpdateUser() {
    // Setup
    final User user = new User(0L, "password", "role", "email", "userClass");

    // Configure UserDao.selectByPrimaryKey(...).
    final User user1 = new User(0L, "password", "role", "email", "userClass");
    when(userDao.selectByPrimaryKey(0L)).thenReturn(user1);

    when(userDao.updateByPrimaryKey(any())).thenReturn(1);

    // Run the test
    final int result = userService.updateUser(user);

    // Verify the results
    assertEquals(1, result);
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

  @Test
  public void testDeleteUser() {
    // Setup
    when(userService.testUserDao.deleteTestUserByUserId(0L)).thenReturn(1);
    when(userService.userDao.deleteByPrimaryKey(0L)).thenReturn(1);

    // Run the test
    final int result = userService.deleteUser(0L);

    // Verify the results
    assertEquals(1, result);
    verify(userService.testUserDao).deleteTestUserByUserId(0L);
  }

  @Test
  public void testDeleteUsers() {
    // Setup
    when(userService.testUserDao.deleteTestUsersByUserIds(Collections.singletonList(0L)))
        .thenReturn(1);
    when(userService.userDao.deleteByIdList(Collections.singletonList(0L))).thenReturn(1);

    // Run the test
    final int result = userService.deleteUsers(Collections.singletonList(0L));

    // Verify the results
    assertEquals(1, result);
    verify(userService.testUserDao).deleteTestUsersByUserIds(Collections.singletonList(0L));
  }

  @Test
  public void testRegisterUser() {
    // Setup
    UserDTO userDTO = new UserDTO(1,"manger","1803@qq.com","1");
    when(userDao.getUserPassword(any())).thenReturn("01022");
    when(userDao.insertUser(any())).thenReturn(1);
    when(userDao.getUserByEmail(any())).thenReturn(null).thenReturn(userDTO);
    UserRegisterRequest dto = new UserRegisterRequest();
    dto.setEmail(userDTO.getEmail());
    dto.setRole("manager");
    dto.setPassword("01022");
    // Run the test
    final JwtUserDTO result = userService.register(dto);

    // Verify the results
    assertEquals("1803@qq.com", result.getUser().getEmail());
  }

  @Test
  public void testSendCodeAndChangePasswordUser()throws GeneralSecurityException {
    // Setup
    UserDTO userDTO = new UserDTO(1,"manger","1803@qq.com","1");
    when(userDao.getUserPassword(any())).thenReturn("01022");
    when(userDao.insertUser(any())).thenReturn(1);
    when(userDao.getUserByEmail(any())).thenReturn(userDTO);
    when(emailUtil.sendMail(any())).thenReturn("true");
    UserRegisterRequest dto = new UserRegisterRequest();
    dto.setEmail(userDTO.getEmail());
    dto.setRole("manager");
    dto.setPassword("01022");

    ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
    forgetPasswordRequest.setPassword("123456");
    forgetPasswordRequest.setEmail(userDTO.getEmail());
    forgetPasswordRequest.setCode("123456");
    // Run the test
    final Response result = userService.sendCode(userDTO.getEmail());
     try{
       userService.forgetPassword(forgetPasswordRequest);
       fail();
     }catch (Exception e){
       // Verify the results
       assertNotNull(result);
       assertTrue(true);
     }

  }

}
