package com.example.pethospitalbackend.dto;

import com.example.pethospitalbackend.entity.Operation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProcessFormBackDTO {
  @ApiModelProperty(value = "流程id")
  private Long processId;

  @ApiModelProperty(value = "流程名称")
  private String processName;

  @ApiModelProperty(value = "流程介绍")
  private String intro;

  @ApiModelProperty(value = "操作清单")
  List<Operation> operationList;
}
