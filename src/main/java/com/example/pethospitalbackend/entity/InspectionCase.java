package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Data
@Entity
@Table(
    name = "inspection_case",
    indexes = {@Index(columnList = "inspection_case_id")})
public class InspectionCase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @KeySql(useGeneratedKeys = true)
  @Column(name = "inspection_case_id")
  @ApiModelProperty(value = "检查情况id")
  private Long inspectionCaseId;

  // 一个病例可能有多个检查情况
  @Column(name = "case_id")
  @ApiModelProperty(value = "对应病例id")
  private Long caseId;

  @Column(name = "item_id")
  @ApiModelProperty(value = "对应检查项目id")
  private Long itemId;

  @Column(name = "result")
  @ApiModelProperty(value = "检查情况结果")
  private String result;

  @Column(name = "sort_num")
  @ApiModelProperty(value = "检查情况的排序序号")
  private Long sortNum;
}
