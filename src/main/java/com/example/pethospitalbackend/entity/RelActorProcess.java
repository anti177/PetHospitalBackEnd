package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rel_actor_process",
		indexes = {@Index(columnList = "id")})
public class RelActorProcess {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@ApiModelProperty(value = "id")
	private long id;

	@Column(name = "process_id")
	@ApiModelProperty(value = "对应流程Id")
	private long processId;

	@Column(name = "actor_id")
	@ApiModelProperty(value = "角色Id")
	private long acotrId;

}