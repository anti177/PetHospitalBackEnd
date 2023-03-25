package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(
    name = "actor",
    indexes = {@Index(columnList = "actor_id")})
public class Actor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "actor_id")
  @ApiModelProperty(value = "角色id")
  private Long actorId;

  @Column(name = "name")
  @ApiModelProperty(value = "角色名称")
  private String name;

  @Column(name = "content")
  @ApiModelProperty(value = "工作内容")
  private String content;

  @Column(name = "responsibility")
  @ApiModelProperty(value = "职责")
  private String responsibility;
}
