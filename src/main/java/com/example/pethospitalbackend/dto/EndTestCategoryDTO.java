package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@ApiModel("用户已经结束考试列表模型")
@Data
@ToString
public class EndTestCategoryDTO implements Serializable {

	@NotBlank
	@ApiModelProperty(value = "考试id")
	private long testId;

	@ApiModelProperty(value = "考试名称")
	private String testName;

	@NotBlank
	@ApiModelProperty(value = "考试分数")
	private long score;

	@NotBlank
	@ApiModelProperty(value = "考试开始时间")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date beginDate;
	
	@NotBlank
	@ApiModelProperty(value = "考试结束时间")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endDate;

	//2.已经参加考试，3.未参加考试错过考试
	@NotBlank
	@ApiModelProperty(value = "是否参加考试")
	private long hasTested;

}
