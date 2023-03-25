package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Data
@Entity
@Table(
    name = "disease",
    indexes = {@Index(columnList = "disease_id")})
public class Disease {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @KeySql(useGeneratedKeys = true) // 使用通用mapper提供的注解
  @Column(name = "disease_id")
  @ApiModelProperty(value = "疾病id")
  private Long diseaseId;

  @Column(name = "disease_name")
  @ApiModelProperty(value = "疾病名称")
  private String diseaseName;

  @Column(name = "type_name")
  @ApiModelProperty(value = "疾病类别")
  private String typeName;
}
