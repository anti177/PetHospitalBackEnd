package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Data
@Entity
@Table(name = "test_user", indexes = {@Index(columnList = "id")})
public class TestUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty(value = "考试用户表id")
    private Long id;
    
    @Column(name = "test_id")
    @ApiModelProperty(value = "考试id")
    private Long testId;
    
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;
    
    //1.还未考试，2.已经考试，3.错过考试）
    @Column(name = "has_tested")
    @ApiModelProperty(value = "是否参考")
    private Long hasTested;
    
    
}
