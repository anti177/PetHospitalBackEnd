package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "disease",
		indexes = {@Index(columnList = "disease_id")})
public class Disease {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "disease_id")
	@ApiModelProperty(value = "疾病id")
	private long diseaseId;

	@Column(name = "disease_name")
	@ApiModelProperty(value = "疾病名称")
	private String diseaseName;

	@Column(name = "type_name")
	@ApiModelProperty(value = "疾病类别")
	private String typeName;

}
