package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaperBackDTO {
  @ApiModelProperty("试卷id")
  private Long paperId;

  @ApiModelProperty(value = "试卷名称", required = true)
  private String paperName;

  @ApiModelProperty(value = "试卷总分", required = true)
  private Long score;

  @ApiModelProperty(value = "问题列表", required = true)
  List<QuestionWithScoreDTO> questionList;
}
