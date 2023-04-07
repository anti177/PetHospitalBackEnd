package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class TestBackFormDTO {
  @NotBlank
  @ApiModelProperty(value = "考试id")
  Long testId;

  @NotBlank
  @ApiModelProperty(value = "考试名")
  String testName;

  @NotBlank
  @ApiModelProperty(value = "试卷id")
  Long paperId;

  @NotBlank
  @ApiModelProperty(value = "考试开始时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date beginDate;

  @NotBlank
  @ApiModelProperty(value = "考试开始时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date endDate;
}
