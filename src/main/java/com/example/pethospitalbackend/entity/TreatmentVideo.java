package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "treatment_video",
		indexes = {@Index(columnList = "treatment_video_id")})
public class TreatmentVideo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@ApiModelProperty(value = "治疗图片id")
	private Long treatmentGraphId;

	@Column(name = "case_id")
	@ApiModelProperty(value = "对应病例id")
	private Long caseId;

	@Column(name = "url")
	@ApiModelProperty(value = "视频链接")
	private String url;

	//一个病例中的治疗视频中可能有多个，这些视频的顺序用sortNum表示
	@Column(name = "sort_num")
	@ApiModelProperty(value = "图片顺序")
	private Long sortNum;
}
