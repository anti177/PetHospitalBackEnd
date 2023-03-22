package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
@Data
@Entity
@Table(name = "answer_record",
		indexes = {@Index(columnList = "answer_record_id")})
public class AnswerRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_record_id")
	@KeySql(useGeneratedKeys = true)
	@ApiModelProperty(value = "用户答案记录id")
	private long answerRecordId;

	@Column(name = "question_id")
	@ApiModelProperty(value = "题目id")
	private long questionId;

	@Column(name = "user_id")
	@ApiModelProperty(value = "用户id")
	private long userId;

	@Column(name = "user_answer")
	@ApiModelProperty(value = "用户答案")
	private String userAnswer;

	@Column(name = "score")
	@ApiModelProperty(value = "小题得分")
	private long score;
}
