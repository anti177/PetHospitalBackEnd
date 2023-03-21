package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admission_graph",
		indexes = {@Index(columnList = "admission_graph_id")})
public class AdmissionGraph {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admission_graph_id")
	@ApiModelProperty(value = "接诊图片id")
	private long admissionGraphId;

	@Column(name = "case_id")
	@ApiModelProperty(value = "对应病例id")
	private long caseId;

	@Column(name = "url")
	@ApiModelProperty(value = "图片链接")
	private String url;

	//一个病例中的接诊中可能有多张图片，这些图片的顺序用sortNum表示
	@Column(name = "sort_num")
	@ApiModelProperty(value = "图片顺序")
	private long sortNum;
}