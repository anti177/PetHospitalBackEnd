package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.constant.SecurityConstants;
import com.example.pethospitalbackend.dto.JwtUserDTO;
import com.example.pethospitalbackend.dto.ModifiedRecordCountDTO;
import com.example.pethospitalbackend.dto.UserDTO;
import com.example.pethospitalbackend.dto.UserFrontDTO;
import com.example.pethospitalbackend.entity.User;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.UserMailNotRegisterOrPasswordWrongException;
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
import java.net.http.HttpResponse;import java.util.List;

/**
 * UserResource
 *
 * @author yyx
 * @author zjy19
 */
@RestController
public class ExampleController {

	@GetMapping("/example")
	public Response<String> example() {
		Response<String> response = new Response<>();

		return response;
	}

	@GetMapping("/example_throw")
	public Response<String> exampleThrow() {
		throw new UserMailNotRegisterOrPasswordWrongException("example throw");
	}

	@GetMapping("/example_return")
	public ResponseEntity<Response<String>> exampleReturn() {
		Response<String> response = new Response<>();
        response.setFail(ResponseEnum.FAIL);
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
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
