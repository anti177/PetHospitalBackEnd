package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProcessBriefBackDTO {
  @ApiModelProperty(value = "流程id")
  private Long processId;

  @ApiModelProperty(value = "流程名称")
  private String processName;
}
