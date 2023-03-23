package com.example.pethospitalbackend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@ApiModel("试卷提交模型")
@Data
@ToString
public class TestRequest {

	@NotBlank
	@Email(message = "邮箱格式不对")
	@ApiModelProperty(value = "用户邮箱")
	private String email;
	@NotBlank
	@ApiModelProperty(value = "用户密码")
	private String password;

	/**
	 * 是否记住我，默认 false
	 */
	@ApiModelProperty(value = "是否记住我")
	private Boolean rememberMe = false;


}
