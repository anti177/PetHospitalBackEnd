package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("角色流程模型")
@Data
public class OperationDTO {

  @ApiModelProperty(value = "操作名称")
  private String operationName;

  @ApiModelProperty(value = "操作介绍")
  private String intro;

  @ApiModelProperty(value = "操作图片")
  private String url;

  // 一个操作在一个流程中是第几个
  @ApiModelProperty(value = "操作顺序序号")
  private long sortNum;
}
