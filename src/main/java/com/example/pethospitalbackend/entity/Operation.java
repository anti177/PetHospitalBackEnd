package com.example.pethospitalbackend.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "operation",
		indexes = {@Index(columnList = "operation_id")})
public class Operation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "operation_id")
	@ApiModelProperty(value = "操作id")
	private long operationId;

	@Column(name = "process_id")
	@ApiModelProperty(value = "对应流程Id")
	private long processId;

	@Column(name = "operation_name")
	@ApiModelProperty(value = "操作名称")
	private String operationName;

	@Column(name = "url")
	@ApiModelProperty(value = "操作图片")
	private String url;

	//一个操作在一个流程中是第几个
	@Column(name = "sort_num")
	@ApiModelProperty(value = "操作顺序序号")
	private long sortNum;


}
