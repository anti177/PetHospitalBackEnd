package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("后台新建问题模型")
public class QuestionBackFormDTO {
  @ApiModelProperty("问题id")
  private Long questionId;

  @ApiModelProperty("问题类型")
  private String questionType;

  @ApiModelProperty("问题描述")
  private String description;

  @ApiModelProperty("选项列表")
  private List<String> choice;

  @ApiModelProperty("正确回答列表")
  private List<String> ans;

  @ApiModelProperty("关键词")
  private String keyword;

  @ApiModelProperty("疾病id")
  private Long diseaseId;
}
