package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("后台新建病例模型")
public class CaseBackFormDTO {

  @ApiModelProperty(value = "病例id")
  Long case_id;

  @ApiModelProperty("封面链接")
  String front_graph;

  @ApiModelProperty(value = "病例标题", required = true)
  String case_title;

  @ApiModelProperty(value = "疾病id", required = true)
  Long disease_id;

  @ApiModelProperty("接诊文字")
  String admission_text;

  @ApiModelProperty("接诊图片列表")
  List<String> admission_graphs;

  @ApiModelProperty(value = "检查情况列表")
  List<InspectionCaseFrontDTO> inspection_cases;

  @ApiModelProperty(value = "诊断信息")
  String diagnostic_result;

  @ApiModelProperty(value = "治疗方案介绍")
  String treatment_info;

  @ApiModelProperty(value = "治疗方案图片列表")
  List<String> therapy_graphs;

  @ApiModelProperty(value = "治疗方案视频列表")
  List<String> therapy_videos;
}
