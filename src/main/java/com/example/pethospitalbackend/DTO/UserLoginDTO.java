package com.example.pethospitalbackend.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * UserLoginDTO
 *
 * @author star
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("用户登陆模型")
@Data
public class UserLoginDTO {

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
