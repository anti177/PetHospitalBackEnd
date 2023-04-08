package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuestionBackBriefDTO {
  @ApiModelProperty(value = "题目id")
  private Long questionId;

  @ApiModelProperty(value = "题目描述")
  private String description;

  @ApiModelProperty(value = "题目种类")
  private String questionType;

  @ApiModelProperty(value = "关键词")
  private String keyword;

  @ApiModelProperty(value = "疾病名")
  private String diseaseName;
}
