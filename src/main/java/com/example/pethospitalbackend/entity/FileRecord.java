package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Data
@Entity
@Table(
    name = "file",
    indexes = {@Index(columnList = "url")})
public class FileRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "file_id")
  @KeySql(useGeneratedKeys = true)
  @ApiModelProperty(value = "文件id")
  private Long id;

  @Column(name = "url")
  private String url;

  @Column(name = "in_use")
  private Boolean inUse;

  @Column(name = "type")
  private String type;
}
