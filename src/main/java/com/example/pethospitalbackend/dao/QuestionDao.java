package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.EndTestCategoryDTO;
import com.example.pethospitalbackend.dto.QuestionDTO;
import com.example.pethospitalbackend.dto.TestCategoryDTO;
import com.example.pethospitalbackend.dto.TestPaperDTO;
import com.example.pethospitalbackend.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QuestionDao extends Mapper<Question> {

	@ResultType(TestPaperDTO.class)
	@Select("select paper_id as paperId, paper_name as paperName, score from paper " +
			"where paper_id = (SELECT paper_id from test where test_id = #{id})")
	TestPaperDTO getTestPaperByUserIdAndTestId(@Param("id") long testId);


	@ResultType(QuestionDTO.class)
	@Select("SELECT question_id as questionId, score, choice, description, question_type as questionType " +
			"from rel_question_paper NATURAL JOIN question " +
			"where paper_id = #{id} ORDER BY index_num")
	List<QuestionDTO> getQuestionByPaperId(@Param("id") long paperId);
}
