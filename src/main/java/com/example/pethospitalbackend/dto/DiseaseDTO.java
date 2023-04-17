package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;

@Data
public class DiseaseDTO {
  @Id
  @ApiModelProperty(value = "疾病id")
  private Long diseaseId;

  @ApiModelProperty(value = "疾病名称")
  private String diseaseName;
}
