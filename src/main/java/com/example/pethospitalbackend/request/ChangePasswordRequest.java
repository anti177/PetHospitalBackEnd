package com.example.pethospitalbackend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel("用户修改密码模型")
@Data
@ToString
public class ChangePasswordRequest implements Serializable {

//	@NotBlank
//	@ApiModelProperty(value = "用户id")
//	private String userId;

	@NotBlank
	@ApiModelProperty(value = "密码")
	private String password;

}