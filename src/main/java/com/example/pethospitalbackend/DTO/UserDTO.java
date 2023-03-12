package com.example.pethospitalbackend.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel("用户登录模型")
@Data
public class UserDTO implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "用户id")
    private long userId;

    @ApiModelProperty(value = "身份")
    private String role;

    @NotBlank
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "班级")
    private String user_class;

}
