package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "inspection_item", indexes = {@Index(columnList = "inspection_item_id")})
public class InspectionItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inspection_item_id")
    @ApiModelProperty(value = "检查项目id")
    private Long inspectionItemId;
    
    @Column(name = "department_id")
    @ApiModelProperty(value = "对应科室id")
    private Long departmentId;
    
    @Column(name = "item_name")
    @ApiModelProperty(value = "检查项目名称")
    private String itemName;
    
    @Column(name = "intro")
    @ApiModelProperty(value = "介绍")
    private String intro;
    
    @Column(name = "fee")
    @ApiModelProperty(value = "费用")
    private Double fee;
    
}