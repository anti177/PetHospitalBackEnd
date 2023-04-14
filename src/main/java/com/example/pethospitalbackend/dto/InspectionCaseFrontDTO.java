package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("后台添加检查情况模型")
public class InspectionCaseFrontDTO {

  @ApiModelProperty(value = "检查项目id", required = true)
  Long inspection_item_id;

  @ApiModelProperty(value = "检查详情", required = true)
  String inspection_result_text;

  @ApiModelProperty(value = "检查图片")
  List<String> inspection_graphs;
}
