package com.example.pethospitalbackend.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@ApiModel("用户忘记密码模型")
@Data
public class ForgetPasswordDTO implements Serializable {

	@NotBlank
	@ApiModelProperty(value = "邮箱")
	private String email;

	@NotBlank
	@ApiModelProperty(value = "密码")
	private String password;

	@NotBlank
	@ApiModelProperty(value = "验证码")
	private String code;


}
