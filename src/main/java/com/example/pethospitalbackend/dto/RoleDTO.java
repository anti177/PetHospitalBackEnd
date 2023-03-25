package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel("角色模型")
@Data
public class RoleDTO implements Serializable {

  @NotBlank
  @ApiModelProperty(value = "角色id")
  private long roleId;

  @NotBlank
  @ApiModelProperty(value = "角色名称")
  private String name;

  @ApiModelProperty(value = "角色内容")
  private String content;

  @NotBlank
  @ApiModelProperty(value = "角色职责")
  private String responsibility;
}
