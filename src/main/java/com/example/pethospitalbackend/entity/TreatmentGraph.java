package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "treatment_graph", indexes = {@Index(columnList = "id")})
public class TreatmentGraph {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "治疗图片id")
    private Long treatmentGraphId;
    
    @Column(name = "case_id")
    @ApiModelProperty(value = "对应病例id")
    private Long caseId;
    
    @Column(name = "url")
    @ApiModelProperty(value = "图片链接")
    private String url;
    
    //一个病例中的治疗中可能有多张图片，这些图片的顺序用sortNum表示
    @Column(name = "sort_num")
    @ApiModelProperty(value = "图片顺序")
    private Long sortNum;
}
