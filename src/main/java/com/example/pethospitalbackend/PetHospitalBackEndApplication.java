package com.example.pethospitalbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PetHospitalBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetHospitalBackEndApplication.class, args);
	}

}
