package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "my_class",
		indexes = {@Index(columnList = "class_id")})
public class MyClass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "class_id")
	@ApiModelProperty(value = "班级id")
	private Long classId;

	@Column(name = "class_name")
	@ApiModelProperty(value = "班级名称")
	private String className;
}
