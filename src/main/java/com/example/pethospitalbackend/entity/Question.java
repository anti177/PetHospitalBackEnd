package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Data
@Entity
@Table(name = "question", indexes = {@Index(columnList = "question_id")})
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty(value = "题目id")
    private Long questionId;
    
    @Column(name = "question_type")
    @ApiModelProperty(value = "题目种类")
    private String questionType;
    
    @Column(name = "description")
    @ApiModelProperty(value = "题目描述")
    private String description;
    
    @Column(name = "choice")
    @ApiModelProperty(value = "题目选项")
    private String choice;
    
    @Column(name = "ans")
    @ApiModelProperty(value = "题目答案")
    private String ans;
    
    @Column(name = "keyword")
    @ApiModelProperty(value = "关键字")
    private String keyword;
    
    @Column(name = "disease_id")
    @ApiModelProperty(value = "对应疾病id")
    private String diseaseId;
    
}
