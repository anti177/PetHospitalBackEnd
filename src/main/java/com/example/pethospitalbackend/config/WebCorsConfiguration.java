package com.example.pethospitalbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

/**
 * WebCorsConfiguration 跨域配置
 *
 * @author yyx
 */
@Configuration
public class WebCorsConfiguration implements WebMvcConfigurer {

  /** 设置xxx主页 */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("home.html");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    WebMvcConfigurer.super.addViewControllers(registry);
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    // config.setAllowedOrigins(Collections.singletonList("*"));似乎是spring版本的原因这两个函数的含义变了
    config.setAllowedOriginPatterns(Collections.singletonList("*"));
    config.setAllowedMethods(Collections.singletonList("*"));
    config.setAllowedHeaders(Collections.singletonList("*"));
    // 暴露 header 中的其他属性给客户端应用程序
    config.setExposedHeaders(
        Arrays.asList(
            "Authorization",
            "X-Total-Count",
            "Link",
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials"));
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
