package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaperDetailBackDTO {
  @ApiModelProperty("试卷id")
  private Long paperId;

  @ApiModelProperty(value = "试卷名称")
  private String paperName;

  @ApiModelProperty(value = "试卷总分")
  private Long score;

  @ApiModelProperty(value = "问题列表")
  List<QuestionDTO> questionList;
}
