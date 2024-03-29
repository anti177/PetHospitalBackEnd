package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.constant.UserRoleConstants;
import com.example.pethospitalbackend.dto.JwtUserDTO;
import com.example.pethospitalbackend.dto.UserDTO;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.UserMailNotRegisterOrPasswordWrongException;
import com.example.pethospitalbackend.request.UserLoginRequest;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.util.JwtUtils;
import com.example.pethospitalbackend.util.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户认证服务
 *
 * @author yyx
 */
@Service
public class AuthService {
  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

  @Autowired private UserService userService;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * 用户登录认证
   *
   * @param userLogin 用户登录信息
   */
  public JwtUserDTO authLogin(UserLoginRequest userLogin) {
    String email = userLogin.getEmail();
    String password = userLogin.getPassword();

    // 根据登录名获取用户信息
    UserDTO userReal = userService.getUserByEmail(email);
    if (userReal == null) {
      logger.warn("[User not sign up], email {}", SerialUtil.toJsonStr(email));
      throw new UserMailNotRegisterOrPasswordWrongException(ResponseEnum.USER_NOT_FOUND.getMsg());
    }
    String rightPassword = userService.getUserPassword(email);
    // 验证登录密码是否正确。如果正确，则赋予用户相应权限并生成用户认证信息
    if (this.bCryptPasswordEncoder.matches(password, rightPassword)) {
      String role = userReal.getRole();
      // 如果用户角色为空，则默认赋予 ROLE_USER 角色
      if (role == null) {
        role = UserRoleConstants.ROLE_USER;
      }
      // 生成 token
      String token =
          JwtUtils.generateToken(
              String.valueOf(userReal.getUserId()), role, userLogin.getRememberMe());

      // 认证成功后，设置认证信息到 Spring Security 上下文中
      Authentication authentication = JwtUtils.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // 用户信息
      return new JwtUserDTO(token, userReal);
    }
    logger.warn("[User auth fail], UserLoginRequest {}", SerialUtil.toJsonStr(userLogin));
    throw new UserMailNotRegisterOrPasswordWrongException(ResponseEnum.TEL_OR_PWD_ERROR.getMsg());
  }

  /**
   * 用户退出登录
   *
   * <p>清除 Spring Security 上下文中的认证信息
   */
  public Response<UserDTO> logout() {
    SecurityContextHolder.clearContext();
    Response<UserDTO> response = new Response<>();
    response.setSuc(null);
    return response;
  }
}
