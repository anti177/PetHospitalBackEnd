package com.example.pethospitalbackend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * UserDTO
 *
 * @author yyx
 */
@ApiModel("用户注册模型")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegisterRequest {
  //    @NotBlank
  //    @Size(min = 4, max = 30, message="用户名格式错误")
  //    @ApiModelProperty(value = "用户名称")
  //    private String name;

  @NotBlank
  @Size(min = 6, max = 15, message = "密码格式错误")
  @ApiModelProperty(value = "用户密码")
  private String password;

  @NotBlank
  @Email(message = "邮箱格式不对")
  @ApiModelProperty(value = "用户邮箱")
  @Size(max = 40)
  private String email;

  @NotBlank
  @ApiModelProperty(value = "角色")
  private String role;

  @ApiModelProperty(value = "班级")
  private String userClass;
}
