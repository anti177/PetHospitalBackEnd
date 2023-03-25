package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(
    name = "user",
    indexes = {@Index(columnList = "email")})
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  @ApiModelProperty(value = "用户id")
  private Long userId;

  @Column(name = "password", nullable = false)
  @ApiModelProperty(value = "密码")
  private String password;

  @Column(name = "role", nullable = false)
  @ApiModelProperty(value = "角色")
  private String role;

  @Column(name = "email")
  @ApiModelProperty(value = "邮箱")
  private String email;

  @Column(name = "user_class")
  @ApiModelProperty(value = "班级")
  private String user_class;
}
