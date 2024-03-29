package com.example.pethospitalbackend.dto;

import com.example.pethospitalbackend.entity.Disease;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("后台病例详情模型")
public class CaseBackDetailDTO {

  @ApiModelProperty(value = "病例ID")
  private long caseId;

  @ApiModelProperty(value = "病例名称")
  private String caseName;

  @ApiModelProperty(value = "疾病")
  private Disease disease;

  @ApiModelProperty(value = "接诊文字")
  private String admissionText;

  @ApiModelProperty(value = "封面图")
  private String frontGraph;

  @ApiModelProperty(value = "接诊图片列表")
  private List<String> admissionGraphList;

  @ApiModelProperty(value = "检查情况列表")
  private List<InspectionCaseBackDTO> inspectionCaseList;

  @ApiModelProperty(value = "诊断信息")
  private String diagnosticInfo;

  @ApiModelProperty(value = "治疗方案介绍")
  private String treatmentInfo;

  @ApiModelProperty(value = "治疗图片列表")
  private List<String> treatmentGraphList;

  @ApiModelProperty(value = "治疗视频列表")
  private List<String> treatmentVideoList;
}
