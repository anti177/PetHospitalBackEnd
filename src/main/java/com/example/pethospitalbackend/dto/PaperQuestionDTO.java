package com.example.pethospitalbackend.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;


@ApiModel("考试题目模型")
@Data
public class PaperQuestionDTO {
	@ApiModelProperty(value = "题目id")
	private long questionId;

	@ApiModelProperty(value = "题目选项")
	private List<String> choice;

	@ApiModelProperty(value = "题目分值")
	private long score;

	@ApiModelProperty(value = "题目描述")
	private String description;

	@ApiModelProperty(value = "题目种类")
	private String questionType;

}
