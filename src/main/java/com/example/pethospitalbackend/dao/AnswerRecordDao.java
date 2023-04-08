package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.FrontTestAnswerDTO;
import com.example.pethospitalbackend.entity.AnswerRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AnswerRecordDao extends Mapper<AnswerRecord> {
  @ResultType(TestRecordDao.class)
  @Select(
      "SELECT answer_record.question_id as questionId, answer_record.score as getScore, "
          + "rel_question_paper.score as score,choice, user_answer as userAns, "
          + "ans,description,question_type as questionType "
          + "from answer_record NATURAL JOIN question "
          + "JOIN rel_question_paper on question.question_id = rel_question_paper.question_id "
          + "where answer_record.user_id = #{user_id} and answer_record.test_id = #{test_id} "
          + "ORDER BY rel_question_paper.index_num ")
  List<FrontTestAnswerDTO> getTestAnswer(
      @Param("test_id") long testId, @Param("user_id") long userId);
}
