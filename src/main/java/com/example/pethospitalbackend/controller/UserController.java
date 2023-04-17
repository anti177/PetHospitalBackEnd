package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.constant.SecurityConstants;
import com.example.pethospitalbackend.dto.JwtUserDTO;
import com.example.pethospitalbackend.dto.ModifiedRecordCountDTO;
import com.example.pethospitalbackend.dto.UserDTO;
import com.example.pethospitalbackend.dto.UserFrontDTO;
import com.example.pethospitalbackend.entity.User;
import com.example.pethospitalbackend.request.ChangePasswordRequest;
import com.example.pethospitalbackend.request.ForgetPasswordRequest;
import com.example.pethospitalbackend.request.UserLoginRequest;
import com.example.pethospitalbackend.request.UserRegisterRequest;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.AuthService;
import com.example.pethospitalbackend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserResource
 *
 * @author yyx
 * @author zjy19
 */
@RestController
@Api(tags = {"用户登录与管理"})
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Resource private UserService userService;
  @Resource private AuthService authService;

  @PostMapping("/user/register")
  @ApiOperation(value = "用户注册")
  public Response<UserFrontDTO> register(@RequestBody UserRegisterRequest userRegister) {
    JwtUserDTO jwtUser = userService.register(userRegister);

    Response<UserFrontDTO> response = new Response<>();
    UserFrontDTO userFrontDTO = transferUserDTOToUserFrontDTO(jwtUser.getUser());
    userFrontDTO.setToken(SecurityConstants.TOKEN_PREFIX + jwtUser.getToken());
    response.setSuc(userFrontDTO);

    return response;
  }

  @PatchMapping("/user/login")
  @ApiOperation(value = "用户登陆")
  public Response<UserFrontDTO> login(@RequestBody UserLoginRequest userLogin) {
    JwtUserDTO jwtUser = authService.authLogin(userLogin);

    Response<UserFrontDTO> response = new Response<>();
    UserFrontDTO userFrontDTO = transferUserDTOToUserFrontDTO(jwtUser.getUser());
    userFrontDTO.setToken(SecurityConstants.TOKEN_PREFIX + jwtUser.getToken());
    response.setSuc(userFrontDTO);
    return response;
  }

  @PatchMapping("/user/logout")
  @ApiOperation(value = "用户退出登录")
  public ResponseEntity<Response<UserDTO>> logout() {
    Response<UserDTO> response = authService.logout();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/user/code")
  @ApiOperation(value = "发验证码")
  public ResponseEntity sendCode(@RequestParam("email") String email) {
    Response response = userService.sendCode(email);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/user/password/forget")
  @ApiOperation(value = "忘记密码")
  public ResponseEntity sendCode(@RequestBody ForgetPasswordRequest changePasswordDTO) {
    Response response = userService.forgetPassword(changePasswordDTO);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/user/password/change")
  @ApiOperation(value = "修改密码")
  public ResponseEntity changePassword(@RequestBody ChangePasswordRequest changePasswordDTO) {
    Response response = userService.changePassword(changePasswordDTO);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/user")
  @ApiOperation("管理员获取单个用户")
  public Response<UserDTO> getUser() {
    UserDTO user = userService.getUserDTOByToken();
    Response<UserDTO> response = new Response<>();
    response.setSuc(user);
    return response;
  }

  @GetMapping("/users")
  @ApiOperation("管理员获取全部用户")
  public Response<List<UserDTO>> getAllUsers() {
    List<UserDTO> userList = userService.getAllUserDTOs();
    Response<List<UserDTO>> response = new Response<>();
    response.setSuc(userList);
    return response;
  }

  @GetMapping("/users/{id}")
  @ApiOperation("获取单个用户")
  public Response<UserDTO> getUser(@PathVariable Long id) {
    UserDTO user = userService.getUserDTOById(id);
    Response<UserDTO> response = new Response<>();
    response.setSuc(user);
    return response;
  }

  @DeleteMapping("/users/{id}")
  @ApiOperation("删除单个用户")
  public Response<ModifiedRecordCountDTO> deleteUser(@PathVariable Long id) {
    Integer res = userService.deleteUser(id);
    Response<ModifiedRecordCountDTO> response = new Response<>();
    response.setSuc(new ModifiedRecordCountDTO(res));
    return response;
  }

  @PostMapping("/users/batch")
  @ApiOperation("批量删除用户")
  public Response<ModifiedRecordCountDTO> userBatchOperation(@RequestBody List<Long> ids) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    Integer res = userService.deleteUsers(ids);
    response.setSuc(new ModifiedRecordCountDTO(res));
    return response;
  }

  @PatchMapping("/users/{id}")
  @ApiOperation("管理员修改用户信息")
  public Response<ModifiedRecordCountDTO> updateUser(
      @PathVariable Long id, @RequestBody User user) {
    Response<ModifiedRecordCountDTO> response = new Response<>();
    Integer res = userService.updateUser(user);
    response.setSuc(new ModifiedRecordCountDTO(res));
    return response;
  }

  @PostMapping("/users")
  @ApiOperation("管理员添加用户")
  public Response<UserDTO> addUser(@RequestBody UserRegisterRequest registerRequest) {
    JwtUserDTO jwtUserDTO = userService.register(registerRequest);
    Response<UserDTO> response = new Response<>();
    response.setSuc(jwtUserDTO.getUser());
    return response;
  }

  private UserFrontDTO transferUserDTOToUserFrontDTO(UserDTO userDTO) {
    UserFrontDTO userFrontDTO = new UserFrontDTO();
    userFrontDTO.setUserId(userDTO.getUserId());
    userFrontDTO.setEmail(userDTO.getEmail());
    userFrontDTO.setRole(userDTO.getRole());
    userFrontDTO.setUserClass(userDTO.getUserClass());
    return userFrontDTO;
  }
}
