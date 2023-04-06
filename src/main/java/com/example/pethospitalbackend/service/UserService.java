package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.constant.UserRoleConstants;
import com.example.pethospitalbackend.dao.UserDao;
import com.example.pethospitalbackend.dto.JwtUserDTO;
import com.example.pethospitalbackend.dto.UserDTO;
import com.example.pethospitalbackend.entity.User;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.exception.UserMailNotRegisterOrPasswordWrongException;
import com.example.pethospitalbackend.exception.UserRelatedException;
import com.example.pethospitalbackend.request.ChangePasswordRequest;
import com.example.pethospitalbackend.request.ForgetPasswordRequest;
import com.example.pethospitalbackend.request.UserRegisterRequest;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.util.EmailUtil;
import com.example.pethospitalbackend.util.JwtUtils;
import com.example.pethospitalbackend.util.SerialUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * UserService
 *
 * @author yyx
 * @author zjy19
 */
@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Resource private UserDao userDao;

  @Resource private EmailUtil emailUtil;

  @Resource private BCryptPasswordEncoder bCryptPasswordEncoder;

  private final Cache<String, String> mailVerifyCodeCache =
      Caffeine.newBuilder()
          .expireAfterWrite(600, TimeUnit.SECONDS)
          .initialCapacity(5)
          .maximumSize(25)
          .build();

  @Transactional(rollbackFor = Exception.class)
  public JwtUserDTO register(UserRegisterRequest dto) {
    // 预检查用户名是否存在
    UserDTO userOptional = this.userDao.getUserByEmail(dto.getEmail());
    if (userOptional != null) {
      logger.warn(
          "[Mail has already been registered],UserRegisterRequest :{}", SerialUtil.toJsonStr(dto));
      throw new UserRelatedException(ResponseEnum.MAIL_HAS_REGISTERED.getMsg());
    }

    User user = new User();
    BeanUtils.copyProperties(dto, user);

    // 将登录密码进行加密
    String cryptPassword = bCryptPasswordEncoder.encode(dto.getPassword());
    user.setPassword(cryptPassword);
    // TODO：没有检验用户是否能成为管理员
    try {
      userDao.insert(user);
    } catch (DataIntegrityViolationException e) {
      // 如果预检查没有检查到重复，就利用数据库的完整性检查
      logger.error(
          "[Insert User Fail],UserRegisterRequest:{}, error msg:{}",
          SerialUtil.toJsonStr(dto),
          e.getMessage());
      throw new UserRelatedException(ResponseEnum.MAIL_HAS_REGISTERED.getMsg());
    }

    UserDTO userdto = getUserByEmail(dto.getEmail());
    String role = userdto.getRole();
    // 如果用户角色为空，则默认赋予 ROLE_USER 角色
    if (role == null) {
      role = UserRoleConstants.ROLE_USER;
    }
    // 生成 token
    String token = JwtUtils.generateToken(String.valueOf(user.getUserId()), role, false);

    // 认证成功后，设置认证信息到 Spring Security 上下文中
    Authentication authentication = JwtUtils.getAuthentication(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 用户信息
    return new JwtUserDTO(token, userdto);
  }

  public UserDTO getUserByEmail(String email) {
    return userDao.getUserByEmail(email);
  }

  public String getUserPassword(String email) {
    return userDao.getUserPassword(email);
  }

  public Response sendCode(String email) {
    UserDTO userOptional = this.userDao.getUserByEmail(email);
    if (userOptional == null) {
      logger.warn("[SendCode Fail], email : {}", SerialUtil.toJsonStr(email));
      throw new UserRelatedException(ResponseEnum.USER_NOT_FOUND.getMsg());
    }
    // 发邮件
    String code;
    Response response = new Response<>();

    code = emailUtil.sendMail(email);

    // 存缓存
    mailVerifyCodeCache.put(email, code);
    response.setSuc(true);
    return response;
  }

  public Response forgetPassword(ForgetPasswordRequest changePasswordRequest) {
    String email = changePasswordRequest.getEmail();
    String password = changePasswordRequest.getPassword();
    String code = changePasswordRequest.getCode();
    String rightCode = mailVerifyCodeCache.getIfPresent(email);
    if (rightCode == null) {
      logger.warn("[ForgetPassword Fail], email: {}", SerialUtil.toJsonStr(email));
      throw new UserRelatedException(ResponseEnum.VERIFY_MSG_CODE_OR_MAIL_INVALID.getMsg());
    }
    if (rightCode.equals(code)) {
      try {
        String cryptPassword = bCryptPasswordEncoder.encode(password);
        userDao.updatePassword(email, cryptPassword);
      } catch (Exception e) {
        logger.error(
            "[ForgetPassword Fail], email : {}, error msg : {}",
            SerialUtil.toJsonStr(email),
            e.getMessage());
        throw new UserRelatedException(ResponseEnum.SERVER_ERROR.getMsg());
      }
    } else {
      logger.warn("[Verify Code Wrong], email : {}", SerialUtil.toJsonStr(email));
      throw new UserRelatedException(ResponseEnum.VERIFY_MSG_CODE_OR_MAIL_INVALID.getMsg());
    }
    Response response = new Response();
    response.setSuccess(true);
    response.setMessage("修改密码成功，请重新登陆");
    return response;
  }

  public Response changePassword(ChangePasswordRequest changePasswordDTO) {
    String userId = JwtUtils.getUserId();
    if (userId != null) {
      try {
        String cryptPassword = bCryptPasswordEncoder.encode(changePasswordDTO.getPassword());
        userDao.updatePasswordByUserId(userId, cryptPassword);
      } catch (Exception e) {
        logger.error("[ChangePassword Fail],  error msg : {}", e.getMessage());
        throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
      }
    } else {
      // 验证过期
      throw new UserMailNotRegisterOrPasswordWrongException(ResponseEnum.VERIFY_INVALID.getMsg());
    }
    Response response = new Response();
    response.setSuccess(true);
    response.setMessage("修改密码成功");
    return response;
  }

  public UserDTO getUserDTOByToken() {
    long id = Long.parseLong(JwtUtils.getUserId());
    try {
      return userDao.getUserByUserId(id);
    } catch (Exception e) {
      logger.error(
          "[get user fail], userId: {}, error msg: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  // ---------------------------------------后台-------------------------------------------

  public int updateUser(User user) {
    Example example = new Example(User.class);
    User originalUser = userDao.selectByPrimaryKey(user.getUserId());
    Example.Criteria criteria = example.createCriteria().andEqualTo("email", user.getEmail());
    try {
      return userDao.updateByPrimaryKeySelective(user);
    } catch (Exception e) {
      logger.error(
          "[update user fail], userId: {}, error msg: {}",
          SerialUtil.toJsonStr(user.getUserId()),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public int deleteUser(Long id) {
    try {
      return userDao.deleteByPrimaryKey(id);
    } catch (Exception e) {
      logger.error(
          "[delete user fail], userId: {}, error msg: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public int deleteUsers(List<Long> ids) {
    Example example = new Example(User.class);
    Example.Criteria criteria = example.createCriteria().andIn("userId", ids);
    try {
      return userDao.deleteByExample(example);
    } catch (Exception e) {
      logger.error(
          "[delete users fail], userIds: {}, error msg: {}",
          SerialUtil.toJsonStr(ids),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public UserDTO getUserDTOById(Long id) {
    try {
      return userDao.getUserByUserId(id);
    } catch (Exception e) {
      logger.error(
          "[get user fail], userId: {}, error msg: {}",
          SerialUtil.toJsonStr(id),
          SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }

  public List<UserDTO> getAllUserDTOs() {
    try {
      return userDao.selectAllUserDTOs();
    } catch (Exception e) {
      logger.error("[get all users fail], error msg: {}", SerialUtil.toJsonStr(e.getMessage()));
      throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
    }
  }
}
