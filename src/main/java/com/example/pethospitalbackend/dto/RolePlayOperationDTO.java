package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("角色操作模型")
@Data
public class RolePlayOperationDTO {

  @NotBlank
  @ApiModelProperty(value = "流程id")
  private long id;

  @ApiModelProperty(value = "流程名称")
  private String name;

  @ApiModelProperty(value = "流程介绍")
  private String intro;

  @ApiModelProperty(value = "操作名称")
  private String operationName;

  @ApiModelProperty(value = "操作介绍")
  private String operationIntro;

  @ApiModelProperty(value = "操作图片")
  private String url;

  // 一个操作在一个流程中是第几个
  @ApiModelProperty(value = "操作顺序序号")
  private long sortNum;
}
