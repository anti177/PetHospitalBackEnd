package com.example.pethospitalbackend.config;

import com.example.pethospitalbackend.constant.SecurityConstants;
import com.example.pethospitalbackend.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

/**
 * Web 安全配置
 *
 * @author star
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private SecurityProblemSupport securityProblemSupport;

    /**
     * 使用 Spring Security 推荐的加密方式进行登录密码的加密
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 此方法配置的资源路径不会进入 Spring Security 机制进行验证
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/test/**");
    }

    /**
     * 定义安全策略，设置 HTTP 访问规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                // 当用户无权访问资源时发送 401 响应
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                // 当用户访问资源因权限不足时发送 403 响应
                .accessDeniedHandler(securityProblemSupport)
             .and()
                // 禁用 CSRF
                .csrf().disable()
                .headers().frameOptions().disable()
             .and()
                .logout().permitAll()
             .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                // 配置登录地址
                .antMatchers(HttpMethod.PATCH, SecurityConstants.AUTH_LOGIN_URL).permitAll()
                // 配置注册地址
                .antMatchers(HttpMethod.POST,SecurityConstants.AUTH_REGISTER_URL).permitAll()
                .antMatchers(HttpMethod.POST,SecurityConstants.SEND_CODE).permitAll()
                .antMatchers(HttpMethod.PATCH,SecurityConstants.FORGET_PASSWORD).permitAll()
                .antMatchers(HttpMethod.PATCH,SecurityConstants.AUTH_LOGOUT_URL).permitAll()
                // 其他请求需验证
                .anyRequest().authenticated()
             .and()
                // 不需要 session（不创建会话）
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and()
                //设置认证用的过滤器
               .apply(securityConfigurationAdapter());
//        super.configure(http);
    }

    private JwtConfigurer securityConfigurationAdapter() throws Exception{
        return new JwtConfigurer(new JwtAuthorizationFilter(authenticationManager()));
    }
}
