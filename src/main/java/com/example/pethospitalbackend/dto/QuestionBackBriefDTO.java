package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuestionBackBriefDTO {
  @ApiModelProperty(value = "题目id")
  private Long questionId;

  @ApiModelProperty(value = "题目选项")
  private String choice;

  @ApiModelProperty(value = "题目描述")
  private String description;

  @ApiModelProperty(value = "题目种类")
  private String questionType;
}
