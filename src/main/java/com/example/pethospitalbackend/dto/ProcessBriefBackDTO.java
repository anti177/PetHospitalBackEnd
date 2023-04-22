package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("后台简单流程模型")
public class ProcessBriefBackDTO {

  @ApiModelProperty(value = "流程id")
  private Long processId;

  @ApiModelProperty(value = "流程名称")
  private String processName;

  @ApiModelProperty(value = "流程介绍")
  private String intro;
}
