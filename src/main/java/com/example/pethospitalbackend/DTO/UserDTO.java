package com.example.pethospitalbackend.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("用户登录注册模型")
@Data
public class UserDTO implements Serializable {

    @ApiModelProperty(value = "用户id")
    private long userId;

    @ApiModelProperty(value = "身份")
    private String role;

    @ApiModelProperty(value = "邮箱")
    private String email;

}
