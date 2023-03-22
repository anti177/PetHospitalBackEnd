package com.example.pethospitalbackend.entity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "rel_question_paper",
		indexes = {@Index(columnList = "id")})
public class RelQuestionPaper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@ApiModelProperty(value = "关系id")
	private long id;

	@Column(name = "paper_id")
	@ApiModelProperty(value = "卷子")
	private long paperId;

	@Column(name = "question_id")
	@ApiModelProperty(value = "题目id")
	private long questionId;

	@Column(name = "index_num")
	@ApiModelProperty(value = "题目在卷子中的序号")
	private long index_num;

	@Column(name = "score")
	@ApiModelProperty(value = "题目分值")
	private long score;

}

