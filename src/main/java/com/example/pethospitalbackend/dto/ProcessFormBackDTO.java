package com.example.pethospitalbackend.dto;

import com.example.pethospitalbackend.entity.Operation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ApiModel("后台新建和修改流程模型")
public class ProcessFormBackDTO {

  // operationId不用填写
  @ApiModelProperty(value = "操作清单", required = true)
  @NotBlank
  List<Operation> operationList;

  @ApiModelProperty(value = "流程id")
  private Long processId;

  @ApiModelProperty(value = "流程名称", required = true)
  @NotBlank
  private String processName;

  @ApiModelProperty(value = "流程介绍")
  private String intro; // todo: 如何阻止不符合要求的请求
}
