package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("后台查看检查情况模型")
public class InspectionCaseBackDTO {

  @ApiModelProperty("检查情况id")
  Long inspectionCaseId;

  @ApiModelProperty("检查项目")
  InspectionItemBackDTO inspectionItem;

  @ApiModelProperty("检查结果描述")
  String result;

  @ApiModelProperty("检查图片")
  List<FileDTO> inspectionGraphs;
}
