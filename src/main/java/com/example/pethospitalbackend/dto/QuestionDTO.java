package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("后台题目模型")
@Data
public class QuestionDTO {

  @ApiModelProperty(value = "题目id")
  private long questionId;

  @ApiModelProperty(value = "题目选项")
  private String choice;

  @ApiModelProperty(value = "题目分值")
  private long score;

  @ApiModelProperty(value = "题目描述")
  private String description;

  @ApiModelProperty(value = "题目种类")
  private String questionType;
}
