package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.constant.SecurityConstants;
import com.example.pethospitalbackend.dto.JwtUserDTO;
import com.example.pethospitalbackend.dto.UserDTO;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * UserResource
 *
 * @author yyx
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"用户登陆"})
public class UserController{

	@Resource
	private UserService userService;
	@Resource
	private AuthService authService;

	@PostMapping("/register")
	@ApiOperation(value = "用户注册")
	public ResponseEntity<Response<UserDTO>> register(@RequestBody UserRegisterRequest userRegister) {
		JwtUserDTO jwtUser = userService.register(userRegister);

		// 将 token 存入响应头中返回
		HttpHeaders httpHeaders = new HttpHeaders();
		// 添加 token 前缀 "Bearer "
		httpHeaders.set(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + jwtUser.getToken());
		Response<UserDTO> response = new Response<>();
		response.setSuc(jwtUser.getUser());
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	@PatchMapping("/login")
	@ApiOperation(value = "用户登陆")
	public ResponseEntity<Response<UserDTO>> login(@RequestBody UserLoginRequest userLogin) {
		JwtUserDTO jwtUser = authService.authLogin(userLogin);
		//将 token 存入响应头中返回
		HttpHeaders httpHeaders = new HttpHeaders();
		// 添加 token 前缀 "Bearer "
		httpHeaders.set(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + jwtUser.getToken());
		Response<UserDTO> response = new Response<>();
		response.setSuc(jwtUser.getUser());
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}
	@PatchMapping("/logout")
	@ApiOperation(value = "用户退出登录")
	public ResponseEntity<Response<UserDTO>> logout() {
		Response<UserDTO> response = authService.logout();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/code")
	@ApiOperation(value = "发验证码")
	public ResponseEntity sendCode(@RequestParam("email") String email) {
		Response response =  userService.sendCode(email);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@PatchMapping("/password/forget")
	@ApiOperation(value = "忘记密码")
	public ResponseEntity sendCode(@RequestBody ForgetPasswordRequest changePasswordDTO) {
		Response response =  userService.forgetPassword(changePasswordDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@PatchMapping("/password/change")
	@ApiOperation(value = "修改密码")
	public ResponseEntity changePassword(@RequestBody ChangePasswordRequest changePasswordDTO) {
		Response response =  userService.changePassword(changePasswordDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
    
    @GetMapping("/users")
    public Response<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        Response<List<User>> response = new Response<>();
        response.setSuc(userList);
        return response;
    }
}
