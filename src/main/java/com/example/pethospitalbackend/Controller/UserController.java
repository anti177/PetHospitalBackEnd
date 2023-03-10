package com.example.pethospitalbackend.Controller;

import com.example.pethospitalbackend.Constant.SecurityConstants;
import com.example.pethospitalbackend.DTO.*;
import com.example.pethospitalbackend.Response.Response;
import com.example.pethospitalbackend.Service.AuthService;
import com.example.pethospitalbackend.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * UserResource
 *
 * @author yyx
 */
@RestController
@RequestMapping("/User")
@Api(tags = {"用户登陆"})
public class UserController{

	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;

	@PostMapping("/Register")
	@ApiOperation(value = "用户注册")
	public ResponseEntity<Response<UserDTO>> register(@RequestBody UserRegisterDTO userRegister) {
		JwtUserDTO jwtUser = userService.register(userRegister);

		// 将 token 存入响应头中返回
		HttpHeaders httpHeaders = new HttpHeaders();
		// 添加 token 前缀 "Bearer "
		httpHeaders.set(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + jwtUser.getToken());
		Response<UserDTO> response = new Response<>();
		response.setSuc(jwtUser.getUser());
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	@PostMapping("/Login")
	@ApiOperation(value = "用户登陆")
	public ResponseEntity<Response<UserDTO>> login(@RequestBody UserLoginDTO userLogin) {
		JwtUserDTO jwtUser = authService.authLogin(userLogin);
		//将 token 存入响应头中返回
		HttpHeaders httpHeaders = new HttpHeaders();
		// 添加 token 前缀 "Bearer "
		httpHeaders.set(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + jwtUser.getToken());
		Response<UserDTO> response = new Response<>();
		response.setSuc(jwtUser.getUser());
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}
	@PostMapping("/Logout")
	@ApiOperation(value = "用户退出登录")
	public ResponseEntity<Response<UserDTO>> logout() {
		Response<UserDTO> response = authService.logout();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/sendCode")
	@ApiOperation(value = "发验证码")
	public ResponseEntity sendCode(@RequestParam("email") String email) {
		Response response =  userService.sendCode(email);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@PostMapping("/forgetPassWord")
	@ApiOperation(value = "忘记密码")
	public ResponseEntity sendCode(@RequestBody ForgetPasswordDTO changePasswordDTO) {
		Response response =  userService.forgetPassword(changePasswordDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@PostMapping("/changePassWord")
	@ApiOperation(value = "修改密码")
	public ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
		Response response =  userService.changePassword(changePasswordDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}


}
