package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Data
@Entity
@Table(
    name = "paper",
    indexes = {@Index(columnList = "paper_id")})
public class Paper {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "paper_id")
  @KeySql(useGeneratedKeys = true)
  @ApiModelProperty(value = "试卷id")
  private Long paperId;

  @Column(name = "paper_name")
  @ApiModelProperty(value = "试卷名称")
  private String paperName;

  @Column(name = "score")
  @ApiModelProperty(value = "试卷总分")
  private Long score;
}
