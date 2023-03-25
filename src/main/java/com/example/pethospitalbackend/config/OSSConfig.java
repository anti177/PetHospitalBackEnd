package com.example.pethospitalbackend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OSSConfig {
  private String endpoint;

  private String accessKeyId;

  private String accessKeySecret;
}
