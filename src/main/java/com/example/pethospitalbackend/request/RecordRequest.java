package com.example.pethospitalbackend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("用户答案提交模型")
@Data
@ToString
public class RecordRequest {

  @NotBlank
  @ApiModelProperty(value = "题目Id")
  private long questionId;

  @NotBlank
  @ApiModelProperty(value = "用户答案")
  private String ans;

  @NotBlank
  @ApiModelProperty(value = "题目分数")
  private long score;
}
