package com.example.pethospitalbackend.dto;

import com.example.pethospitalbackend.entity.Disease;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("病例模型")
@Data
public class CaseBackBriefDTO {

  @NotBlank
  @ApiModelProperty(value = "病例id")
  private long illCaseId;

  @NotBlank
  @ApiModelProperty(value = "病例名称")
  private String illCaseName;

  @ApiModelProperty(value = "疾病")
  private Disease disease;
}
