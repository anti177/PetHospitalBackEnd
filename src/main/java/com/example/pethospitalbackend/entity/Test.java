package com.example.pethospitalbackend.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "test",
		indexes = {@Index(columnList = "test_id")})
public class Test {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "test_id")
	@KeySql(useGeneratedKeys = true)
	@ApiModelProperty(value = "考试id")
	private long testId;

	@Column(name = "test_name")
	@ApiModelProperty(value = "考试名称")
	private String testName;

	@Column(name = "begin_date")
	@ApiModelProperty(value = "考试开始时间")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date beginDate;

	@Column(name = "end_date")
	@ApiModelProperty(value = "考试结束时间")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endDate;
}
