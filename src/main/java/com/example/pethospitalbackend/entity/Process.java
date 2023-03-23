package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "process", indexes = {@Index(columnList = "process_id")})
public class Process {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id")
    @ApiModelProperty(value = "流程id")
    private Long processId;
    
    @Column(name = "process_name")
    @ApiModelProperty(value = "流程名称")
    private String processName;
    
    @Column(name = "intro")
    @ApiModelProperty(value = "流程介绍")
    private String intro;
    
    
}