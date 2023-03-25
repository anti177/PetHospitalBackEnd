package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(
    name = "ill_case",
    indexes = {@Index(columnList = "ill_case_id")})
public class IllCase implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @KeySql(useGeneratedKeys = true)
  @Column(name = "ill_case_id")
  @ApiModelProperty(value = "病例id")
  private Long caseId;

  @Column(name = "case_name")
  @ApiModelProperty(value = "病例名称")
  private String caseName;

  @Column(name = "disease_id")
  @ApiModelProperty(value = "对应疾病id")
  private Long diseaseId;

  @Column(name = "admission_text")
  @ApiModelProperty(value = "接诊文字")
  private String admissionText;

  @Column(name = "diagnostic_info")
  @ApiModelProperty(value = "诊断结果")
  private String diagnosticInfo;

  @Column(name = "treatment_text")
  @ApiModelProperty(value = "治疗方案介绍")
  private String treatmentInfo;
}
