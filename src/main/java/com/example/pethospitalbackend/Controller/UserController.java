package com.example.pethospitalbackend.Controller;

import com.example.pethospitalbackend.Constant.SecurityConstants;
import com.example.pethospitalbackend.DTO.JwtUserDTO;
import com.example.pethospitalbackend.DTO.UserDTO;
import com.example.pethospitalbackend.DTO.UserLoginDTO;
import com.example.pethospitalbackend.DTO.UserRegisterDTO;
import com.example.pethospitalbackend.Entity.User;
import com.example.pethospitalbackend.Response.Response;
import com.example.pethospitalbackend.Service.AuthService;
import com.example.pethospitalbackend.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
	public ResponseEntity<Response> register(@RequestBody UserRegisterDTO userRegister) {
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
	public ResponseEntity<Response> login(@RequestBody UserLoginDTO userLogin) {
		JwtUserDTO jwtUser = authService.authLogin(userLogin);

		//将 token 存入响应头中返回
		HttpHeaders httpHeaders = new HttpHeaders();
		// 添加 token 前缀 "Bearer "
		httpHeaders.set(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + jwtUser.getToken());
		Response<UserDTO> response = new Response<>();
		response.setSuc(jwtUser.getUser());
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

}
