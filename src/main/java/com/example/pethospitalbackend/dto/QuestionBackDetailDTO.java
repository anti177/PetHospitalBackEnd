package com.example.pethospitalbackend.dto;

import com.example.pethospitalbackend.entity.Disease;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("后台问题详情")
public class QuestionBackDetailDTO {

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

  @ApiModelProperty("相关疾病")
  private DiseaseDTO disease;
}
