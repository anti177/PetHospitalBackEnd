package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.EndTestCategoryDTO;
import com.example.pethospitalbackend.dto.TestCategoryDTO;
import com.example.pethospitalbackend.entity.Test;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TestDao extends Mapper<Test> {

	//返回还未开始的考试列表
	@ResultType(TestCategoryDTO.class)
	@Select("SELECT test.test_id as test_id, begin_date as beginDate, end_date as endDate, test_name as testName " +
			"from test JOIN test_user on test.test_id = test_user.test_id " +
			"WHERE test_user.user_id = #{id} and test_user.has_tested = 1 " +
			"ORDER BY begin_date")
	List<TestCategoryDTO> getTestCategoryByUserId(@Param("id") long userId);



	//返回已经结束的考试列表
	@ResultType(EndTestCategoryDTO.class)
	@Select("SELECT test.test_id as testId, begin_date as beginDate, end_date as endDate, score, test_name as testName, has_tested as hasTested\n" +
			"from test NATURAL JOIN test_user NATURAL JOIN  test_record WHERE test_user.user_id = #{id} and test_user.has_tested <> 1;")
	List<EndTestCategoryDTO> getEndTestCategoryByUserId(@Param("id") long userId);
}