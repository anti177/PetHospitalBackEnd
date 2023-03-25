package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("病例目录模型")
@Data
public class CaseCategoryDTO {
	@ApiModelProperty(value = "病例ID")
	private int caseId;

	@ApiModelProperty(value = "病例名称")
	private String caseName;

	@ApiModelProperty(value = "接诊文字")
	private String admissionText;


}
