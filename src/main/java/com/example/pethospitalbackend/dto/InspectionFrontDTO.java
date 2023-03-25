package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("检查情况模型")
@Data
public class InspectionFrontDTO {

  @ApiModelProperty(value = "检查项目id")
  private long inspectionCaseId;

  @ApiModelProperty(value = "对应科室名称")
  private String departmentName;

  @ApiModelProperty(value = "检查项目名称")
  private String itemName;

  @ApiModelProperty(value = "检查情况结果")
  private String result;

  @ApiModelProperty(value = "介绍")
  private String intro;

  @ApiModelProperty(value = "费用")
  private double fee;

  @ApiModelProperty(value = "检查图片列表")
  private List<String> inspectionGraphList;
}
