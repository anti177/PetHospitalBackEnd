package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "department", indexes = {@Index(columnList = "department_id")})
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    @ApiModelProperty(value = "科室id")
    private Long departmentId;
    
    @Column(name = "department_name")
    @ApiModelProperty(value = "科室名称")
    private String departmentName;
    
    @Column(name = "intro")
    @ApiModelProperty(value = "科室介绍")
    private String intro;
    
    //一个科室的所有负责人字符串，分割符号还每确定
    @Column(name = "people_List")
    @ApiModelProperty(value = "科室负责人")
    private String peopleList;
}