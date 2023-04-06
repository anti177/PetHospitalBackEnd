package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.BaseTest;
import com.example.pethospitalbackend.dto.JwtUserDTO;
import com.example.pethospitalbackend.dto.ModifiedRecordCountDTO;
import com.example.pethospitalbackend.dto.UserDTO;
import com.example.pethospitalbackend.entity.User;
import com.example.pethospitalbackend.request.UserRegisterRequest;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
public class UserControllerTest extends BaseTest {
  @Autowired private MockMvc mockMvc;

  @Mock private UserService userService;

  @InjectMocks private UserController userController;

  private JacksonTester<User> userJacksonTester;
  private JacksonTester<UserRegisterRequest> userRegisterRequestJacksonTester;
  private JacksonTester<Response> responseJacksonTester;
  private JacksonTester<List<Long>> listJacksonTester;

  UserDTO userDTO1 = new UserDTO(1L, "role", "email1", "userClass");
  UserDTO userDTO2 = new UserDTO(2L, "role", "email2", "userClass");

  @Before
  public void before() {
    JacksonTester.initFields(this, new ObjectMapper());
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void testGetAllUsers() throws Exception {
    // Setup
    when(userService.getAllUserDTOs()).thenReturn(Arrays.asList(userDTO1, userDTO2));
    Response<List<UserDTO>> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(Arrays.asList(userDTO1, userDTO2));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8)); // 防止中文乱码
  }

  @Test
  public void testUpdateUser() throws Exception {
    // Setup
    User user = new User(0L, "password", "role", "email", "userClass");
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));
    when(userService.updateUser(user)).thenReturn(1);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                patch("/users/{id}", 0)
                    .content(userJacksonTester.write(user).getJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testGetUser() throws Exception {
    // Setup
    final UserDTO userDTO = new UserDTO(0L, "role", "email", "userClass");
    when(userService.getUserDTOById(0L)).thenReturn(userDTO);
    Response<UserDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(userDTO);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(get("/users/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testDeleteUser() throws Exception {
    // Setup
    when(userService.deleteUser(0L)).thenReturn(1);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(1));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(delete("/users/{id}", 0).accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testUserBatchOperation() throws Exception {
    // Setup
    List<Long> userIdList = Arrays.asList(1L, 2L);
    when(userService.deleteUsers(userIdList)).thenReturn(2);
    Response<ModifiedRecordCountDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(new ModifiedRecordCountDTO(2));

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/users/batch")
                    .param("action", "delete")
                    .content(listJacksonTester.write(userIdList).getJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }

  @Test
  public void testAddUser() throws Exception {
    // Setup
    // Configure UserService.register(...).
    final JwtUserDTO jwtUserDTO =
        new JwtUserDTO("bearer token", new UserDTO(0L, "role", "123456@qq.com", "userClass"));
    UserRegisterRequest userRegisterRequest =
        new UserRegisterRequest("123456", "123456@qq.com", "email", "userClass");
    when(userService.register(userRegisterRequest)).thenReturn(jwtUserDTO);
    Response<UserDTO> expectedResponseContent = new Response<>();
    expectedResponseContent.setSuc(jwtUserDTO.getUser());

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/users")
                    .content(userRegisterRequestJacksonTester.write(userRegisterRequest).getJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        responseJacksonTester.write(expectedResponseContent).getJson(),
        response.getContentAsString(StandardCharsets.UTF_8));
  }
}
