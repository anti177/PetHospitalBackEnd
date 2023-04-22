package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Data
@Entity
@Table(
    name = "vaccine",
    indexes = {@Index(columnList = "id")})
public class Vaccine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @KeySql(useGeneratedKeys = true)
  @ApiModelProperty(value = "疫苗id")
  private Long id;

  @Column(name = "name")
  @ApiModelProperty(value = "疫苗名称")
  private String name;

  @Column(name = "intro")
  @ApiModelProperty(value = "疫苗介绍")
  private String intro;
}
