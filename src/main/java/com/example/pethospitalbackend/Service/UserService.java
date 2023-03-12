package com.example.pethospitalbackend.Service;

import com.example.pethospitalbackend.Constant.UserRoleConstants;
import com.example.pethospitalbackend.DTO.*;
import com.example.pethospitalbackend.Dao.UserDao;
import com.example.pethospitalbackend.Entity.User;
import com.example.pethospitalbackend.Exception.DatabaseException;
import com.example.pethospitalbackend.Exception.UserMailNotRegisterOrPasswordWrongException;
import com.example.pethospitalbackend.Exception.UserRelatedException;
import com.example.pethospitalbackend.Response.Response;
import com.example.pethospitalbackend.Util.EmailUtil;
import com.example.pethospitalbackend.Util.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * UserService
 *
 * @author yyx
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private  EmailUtil emailUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Cache<String, String> mailVerifyCodeCache = Caffeine.newBuilder()
            .expireAfterWrite(600, TimeUnit.SECONDS)
            .initialCapacity(5)
            .maximumSize(25)
            .build();

    @Transactional(rollbackFor = Exception.class)
    public JwtUserDTO register(UserRegisterDTO dto) {
        // 预检查用户名是否存在
        UserDTO userOptional = this.userDao.getUserByEmail(dto.getEmail());
        if (userOptional != null) {
            throw new UserRelatedException("邮箱已经注册");
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);

        // 将登录密码进行加密
        String cryptPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        user.setPassword(cryptPassword);
        //TODO：没有检验用户是否能成为管理员
        try {
            userDao.insertUser(user);
        } catch (DataIntegrityViolationException e) {
            // 如果预检查没有检查到重复，就利用数据库的完整性检查
            throw new UserRelatedException("用户已经存在");

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

    public Response sendCode(String email){
        UserDTO userOptional = this.userDao.getUserByEmail(email);
        if (userOptional == null) {
            throw new UserRelatedException("用户没有注册");
        }
        //发邮件
        String code = null;
        Response response = new Response<>();

        code = emailUtil.sendMail(email);

        //存缓存
        mailVerifyCodeCache.put(email,code);
        response.setSuc(true);
        return response;
    }

    public Response forgetPassword(ForgetPasswordDTO changePasswordDTO) {
        String email = changePasswordDTO.getEmail();
        String password = changePasswordDTO.getPassword();
        String code = changePasswordDTO.getCode();
        String rightCode = mailVerifyCodeCache.getIfPresent(email);
        if(rightCode == null){
            throw new UserRelatedException("验证码过期或者邮箱错误");
        }
        if(rightCode.equals(code)){
            try{
                String cryptPassword = bCryptPasswordEncoder.encode(password);
                userDao.updatePassword(email,cryptPassword);
            }catch (Exception e){
                throw new UserRelatedException(e.getMessage());
            }
        }else{
            throw new UserRelatedException("验证码错误");
        }
        Response response = new Response();
        response.setSuccess(true);
        response.setMsg("修改密码成功，请重新登陆");
        return response;
    }

    public Response changePassword(ChangePasswordDTO changePasswordDTO){
        // 获取用户认证信息。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 认证信息可能为空，因此需要进行判断。
        if (Objects.nonNull(authentication)) {
            //从验证信息中拿userId
            String userId = (String)authentication.getPrincipal();
            try{
                String cryptPassword = bCryptPasswordEncoder.encode(changePasswordDTO.getPassword());
                userDao.updatePasswordByUserId(userId,cryptPassword);
            }catch (Exception e){
                throw new DatabaseException("修改密码失败"+e.getMessage());
            }
        }else{
            throw new UserMailNotRegisterOrPasswordWrongException("验证过期");
        }
        Response response = new Response();
        response.setSuccess(true);
        response.setMsg("修改密码成功");
        return response;

    }
}
