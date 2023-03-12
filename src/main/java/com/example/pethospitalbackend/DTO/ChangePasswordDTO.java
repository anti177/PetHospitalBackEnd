package com.example.pethospitalbackend.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel("用户修改密码模型")
@Data
public class ChangePasswordDTO implements Serializable {

//	@NotBlank
//	@ApiModelProperty(value = "用户id")
//	private String userId;

	@NotBlank
	@ApiModelProperty(value = "密码")
	private String password;

}