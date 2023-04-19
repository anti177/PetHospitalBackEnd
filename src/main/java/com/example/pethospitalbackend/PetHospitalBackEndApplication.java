package com.example.pethospitalbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "com.example.pethospitalbackend.dao")
// @EnableScheduling
public class PetHospitalBackEndApplication {

  public static void main(String[] args) {
    SpringApplication.run(PetHospitalBackEndApplication.class, args);
  }
}
