package com.example.pethospitalbackend.Service;

import com.example.pethospitalbackend.Constant.UserRoleConstants;
import com.example.pethospitalbackend.DTO.JwtUserDTO;
import com.example.pethospitalbackend.DTO.UserDTO;
import com.example.pethospitalbackend.DTO.UserRegisterDTO;
import com.example.pethospitalbackend.Dao.UserDao;
import com.example.pethospitalbackend.Entity.User;
import com.example.pethospitalbackend.Exception.AlreadyExistsException;
import com.example.pethospitalbackend.Util.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public JwtUserDTO register(UserRegisterDTO dto) {
        // 预检查用户名是否存在
        UserDTO userOptional = this.userDao.getUserByEmail(dto.getEmail());
        if (userOptional != null) {
            throw new AlreadyExistsException("邮箱已经注册");
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
            throw new AlreadyExistsException("用户已经存在");

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


}
