package com.example.pethospitalbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class TestFormBackDTO {

  private Long testId;

  @ApiModelProperty(value = "考试名称")
  private String testName;

  @ApiModelProperty(value = "考试介绍")
  private String intro;

  @ApiModelProperty(value = "考试tag")
  private String tag;

  @ApiModelProperty(value = "试卷ID")
  private Long paperID;

  @ApiModelProperty(value = "考试开始时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date beginDate;

  @ApiModelProperty(value = "考试结束时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date endDate;

  @ApiModelProperty(value = "参考人员id列表")
  private List<Long> userList;
}
