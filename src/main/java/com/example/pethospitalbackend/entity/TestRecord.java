package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Data
@Entity
@Table(
    name = "test_record",
    indexes = {@Index(columnList = "test_record_id")})
public class TestRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "test_record_id")
  @KeySql(useGeneratedKeys = true)
  @ApiModelProperty(value = "考试记录id")
  private Long testRecordId;

  @Column(name = "test_id")
  @ApiModelProperty(value = "考试id")
  private Long testId;

  @Column(name = "user_id")
  @ApiModelProperty(value = "用户id")
  private Long userId;

  @Column(name = "score")
  @ApiModelProperty(value = "总分")
  private Long score;
}
