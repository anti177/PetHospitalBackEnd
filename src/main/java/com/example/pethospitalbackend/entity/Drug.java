package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Data
@Entity
@Table(
    name = "drug",
    indexes = {@Index(columnList = "id")})
public class Drug {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @KeySql(useGeneratedKeys = true)
  @ApiModelProperty(value = "药品id")
  private Long id;

  @Column(name = "name")
  @ApiModelProperty(value = "药品名称")
  private String name;

  @Column(name = "type")
  @ApiModelProperty(value = "药品种类")
  private String type;

  @Column(name = "intro")
  @ApiModelProperty(value = "药品介绍")
  private String intro;

  @Column(name = "price")
  @ApiModelProperty(value = "价格")
  private Double price;

  @Column(name = "url")
  @ApiModelProperty(value = "图片")
  private String url;
}
