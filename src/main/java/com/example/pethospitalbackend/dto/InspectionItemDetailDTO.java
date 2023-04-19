package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InspectionItemDetailDTO {

  @ApiModelProperty(value = "检查项目id")
  private Long inspectionItemId;

  @ApiModelProperty(value = "对应科室名")
  private String departmentName;

  @ApiModelProperty(value = "对应科室id")
  private Long departmentId;

  @ApiModelProperty(value = "检查项目名称")
  private String itemName;

  @ApiModelProperty(value = "介绍")
  private String intro;

  @ApiModelProperty(value = "费用")
  private Double fee;
}
