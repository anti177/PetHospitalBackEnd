package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("题目模型")
@Data
public class FrontTestAnswerDTO {
  @ApiModelProperty(value = "题目id")
  private long questionId;

  @ApiModelProperty(value = "题目选项")
  private String choice;

  // 不清楚前端要哪一种，再建一个dto感觉太重复了
  @ApiModelProperty(value = "题目选项list")
  private List<String> choiceList;

  @ApiModelProperty(value = "题目分值")
  private long score;

  @ApiModelProperty(value = "题目描述")
  private String description;

  @ApiModelProperty(value = "题目种类")
  private String questionType;

  @ApiModelProperty(value = "用户答案")
  private String userAns;

  @ApiModelProperty(value = "正确答案")
  private String ans;

  @ApiModelProperty(value = "小题得分")
  private Long getScore;
}
