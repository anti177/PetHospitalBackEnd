package com.example.pethospitalbackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(
    name = "test",
    indexes = {@Index(columnList = "test_id")})
public class Test {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "test_id")
  @KeySql(useGeneratedKeys = true)
  @ApiModelProperty(value = "考试id")
  private Long testId;

  @Column(name = "test_name")
  @ApiModelProperty(value = "考试名称")
  private String testName;

  @Column(name = "intro")
  @ApiModelProperty(value = "考试介绍")
  private String intro;

  @Column(name = "tag")
  @ApiModelProperty(value = "考试tag")
  private String tag;

  @Column(name = "paper_id")
  @ApiModelProperty(value = "试卷ID")
  private Long paperID;

  @Column(name = "begin_date")
  @ApiModelProperty(value = "考试开始时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date beginDate;

  @Column(name = "end_date")
  @ApiModelProperty(value = "考试结束时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date endDate;
}
