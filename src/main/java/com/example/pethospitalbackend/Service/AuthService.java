package com.example.pethospitalbackend.Service;

import com.example.pethospitalbackend.Constant.UserRoleConstants;
import com.example.pethospitalbackend.DTO.JwtUserDTO;
import com.example.pethospitalbackend.DTO.UserDTO;
import com.example.pethospitalbackend.DTO.UserLoginDTO;
import com.example.pethospitalbackend.Exception.AlreadyExistsException;
import com.example.pethospitalbackend.Util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 用户认证服务
 *
 * @author star
 */
@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 用户登录认证
     *
     * @param userLogin 用户登录信息
     */
    public JwtUserDTO authLogin(UserLoginDTO userLogin) {
        String email = userLogin.getEmail();
        String password = userLogin.getPassword();

        // 根据登录名获取用户信息
        UserDTO userReal = userService.getUserByEmail(email);
        if (userReal == null) {
            throw new UsernameNotFoundException("用户没有注册");
        }
        String rightPassword = userLogin.getPassword();
        // 验证登录密码是否正确。如果正确，则赋予用户相应权限并生成用户认证信息
        if (this.bCryptPasswordEncoder.matches(password, rightPassword)) {
            String role = userReal.getRole();
            // 如果用户角色为空，则默认赋予 ROLE_USER 角色
            if (role == null) {
                role = UserRoleConstants.ROLE_USER;
            }
            // 生成 token
            String token = JwtUtils.generateToken(String.valueOf(userReal.getUserId()), role, userLogin.getRememberMe());

            // 认证成功后，设置认证信息到 Spring Security 上下文中
            Authentication authentication = JwtUtils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 用户信息
            return new JwtUserDTO(token, userReal);
        }
        throw new BadCredentialsException("邮箱或密码错误.");
    }

    /**
     * 用户退出登录
     *
     * <p>
     * 清除 Spring Security 上下文中的认证信息
     */
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
