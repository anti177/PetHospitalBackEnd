package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class TestDetailBackDTO {
  @NotBlank
  @ApiModelProperty(value = "考试id")
  Long testId;

  @NotBlank
  @ApiModelProperty(value = "考试名")
  String testName;

  @NotBlank
  @ApiModelProperty(value = "试卷名")
  Long paperName;

  @NotBlank
  @ApiModelProperty(value = "考试开始时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date beginDate;

  @NotBlank
  @ApiModelProperty(value = "考试开始时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date endDate;

  @ApiModelProperty(value = "简介")
  private String intro;

  @ApiModelProperty(value = "标签")
  private String tag;
}
