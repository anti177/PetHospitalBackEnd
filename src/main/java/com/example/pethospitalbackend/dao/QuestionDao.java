package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.QuestionBackBriefDTO;
import com.example.pethospitalbackend.dto.QuestionDTO;
import com.example.pethospitalbackend.dto.TestPaperDTO;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.Question;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QuestionDao extends Mapper<Question> {

  @ResultType(TestPaperDTO.class)
  @Select(
      "select paper_id as paperId, paper_name as paperName, score from paper "
          + "where paper_id = (SELECT paper_id from test where test_id = #{id})")
  TestPaperDTO getTestPaperByUserIdAndTestId(@Param("id") long testId);

  @ResultType(QuestionDTO.class)
  @Select(
      "SELECT question_id as questionId, score, choice, description, question_type as questionType "
          + "from rel_question_paper NATURAL JOIN question "
          + "where paper_id = #{id} ORDER BY index_num")
  List<QuestionDTO> getQuestionByPaperId(@Param("id") long paperId);

  @ResultType(Question.class)
  @Select(
      "SELECT question_id as questionId, ans, score, choice, description, question_type as questionType "
          + "from rel_question_paper NATURAL JOIN question "
          + "where paper_id = (SELECT paper_id from test where test_id = #{id}) ORDER BY index_num")
  List<Question> getQuestionAnsByTestId(@Param("id") long testId);

  @Select("SELECT question_id, description, question_type, keyword, disease_id from question")
  @Results(
      id = "question_list",
      value = {
        @Result(id = true, column = "question_id", property = "questionId"),
        @Result(column = "description", property = "description"),
        @Result(column = "keyword", property = "keyword"),
        @Result(column = "question_type", property = "questionType"),
        @Result(
            property = "diseaseName",
            column = "disease_id",
            javaType = Disease.class,
            one =
                @One(
                    select = "com.example.pethospitalbackend.dao.DiseaseDao.selectByPrimaryKey",
                    fetchType = FetchType.EAGER))
      })
  List<QuestionBackBriefDTO> getAllQuestionBackBriefDTOs();
}
