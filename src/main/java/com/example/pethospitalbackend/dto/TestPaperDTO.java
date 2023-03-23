package com.example.pethospitalbackend.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel("考试试卷模型")
@Data
public class TestPaperDTO {
	@ApiModelProperty(value = "试卷id")
	private long paperId;

	@ApiModelProperty(value = "试卷名称")
	private String paperName;

	@ApiModelProperty(value = "试卷总分")
	private long score;

	@ApiModelProperty(value = "题目列表")
	private List<PaperQuestionDTO> questionList;

}
