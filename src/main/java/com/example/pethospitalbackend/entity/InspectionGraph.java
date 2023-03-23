package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "inspection_graph",
		indexes = {@Index(columnList = "inspection_graph_id")})
public class InspectionGraph {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inspection_graph_id")
	@ApiModelProperty(value = "检查图片id")
	private Long inspectionGraphId;

	@Column(name = "inspection_case_id")
	@ApiModelProperty(value = "对应检查情况id")
	private Long inspectionCaseId;

	@Column(name = "url")
	@ApiModelProperty(value = "图片链接")
	private String url;

	//一个病例中的一个检查情况可能有多张图片，这些图片的顺序用sortNum表示
	@Column(name = "sort_num")
	@ApiModelProperty(value = "图片顺序")
	private Long sortNum;
}
