package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.CaseBackBriefDTO;
import com.example.pethospitalbackend.dto.CaseBackDetailDTO;
import com.example.pethospitalbackend.dto.CaseCategoryDTO;
import com.example.pethospitalbackend.dto.FileDTO;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.IllCase;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface CaseDao extends Mapper<IllCase> {

  @ResultType(CaseCategoryDTO.class)
  @Select(
      "SELECT ill_case_id as caseId, case_name as caseName, admission_text as admissionText,front_graph as frontGraph "
          + "FROM ill_case WHERE disease_id = #{id}")
  List<CaseCategoryDTO> getCaseByDiseaseId(@Param("id") long diseaseId);

  @ResultType(IllCase.class)
  @Select(
      "SELECT ill_case_id as caseId, case_name as caseName, admission_text as admissionText, "
          + "diagnostic_info as diagnosticInfo, treatment_text as treatmentInfo "
          + " FROM ill_case WHERE ill_case_id = #{id}")
  IllCase getCaseByCaseId(@Param("id") long caseId);

  @Select("SELECT url FROM admission_graph WHERE case_id = #{id} ORDER BY sort_num ")
  List<String> getAdmissionGraphByCaseId(@Param("id") long caseId);

  @Select("SELECT url FROM treatment_graph WHERE case_id = #{id} ORDER BY sort_num ")
  List<String> getTreatmentGraphByCaseId(@Param("id") long caseId);

  @Select("SELECT url FROM treatment_video WHERE case_id = #{id} ORDER BY sort_num ")
  List<String> getTreatmentVideoByCaseId(@Param("id") long caseId);

  @Select("SELECT * FROM ${table} WHERE case_id = #{id} ORDER BY sort_num ")
  @Results(
      id = "file_list",
      value = {
        @Result(id = true, column = "id", property = "fileId"),
        @Result(column = "case_id", property = "caseId"),
        @Result(column = "url", property = "url"),
        @Result(column = "sort_num", property = "sortNum")
      })
  List<FileDTO> getFilesByIllCaseId(@Param("table") String table, @Param("id") long caseId);

  @Delete("DELETE FROM ${table} WHERE case_id = #{id}")
  Integer deleteFilesByIllCaseId(@Param("table") String table, @Param("id") long caseId);

  @Select("SELECT * FROM ${table} WHERE case_id = #{id} ORDER BY sort_num ")
  @Results(
      id = "file_list_map",
      value = {
        @Result(id = true, column = "id", property = "fileId"),
        @Result(column = "case_id", property = "caseId"),
        @Result(column = "url", property = "url"),
        @Result(column = "sort_num", property = "sortNum")
      })
  List<FileDTO> getFileByIllCaseId(Map<String, Object> param);

  @Select("SELECT * from ill_case")
  @Results(
      id = "ill_case_list",
      value = {
        @Result(id = true, column = "ill_case_id", property = "illCaseId"),
        @Result(column = "case_name", property = "illCaseName"),
        @Result(
            property = "disease",
            column = "disease_id",
            javaType = Disease.class,
            one =
                @One(
                    select = "com.example.pethospitalbackend.dao.DiseaseDao.selectByPrimaryKey",
                    fetchType = FetchType.EAGER))
      })
  List<CaseBackBriefDTO> getAllCaseBackBriefDTOs();

  @Select("SELECT * from ill_case where ill_case_id = #{id}")
  @Results(
      id = "ill_case_map",
      value = {
        @Result(id = true, column = "ill_case_id", property = "caseId"),
        @Result(column = "case_name", property = "caseName"),
        @Result(column = "front_graph", property = "frontGraph"),
        @Result(
            property = "disease",
            column = "disease_id",
            javaType = Disease.class,
            one =
                @One(
                    select = "com.example.pethospitalbackend.dao.DiseaseDao.selectByPrimaryKey",
                    fetchType = FetchType.EAGER)),
        @Result(property = "admissionText", column = "admission_text"),
        @Result(property = "diagnosticInfo", column = "diagnostic_info"),
        @Result(property = "treatmentInfo", column = "treatment_text")
      })
  CaseBackDetailDTO getBackDetailDTO(@Param("id") Long caseId);

  @Insert({
    "<script>",
    "insert into ${tableName}(id, case_id, sort_num, url) values ",
    "<foreach collection='files' item='item' index='index' separator=','>",
    "(#{item.fileId}, #{item.caseId}, #{item.sortNum},#{item.url})",
    "</foreach>",
    "</script>"
  })
  int insertFiles(
      @Param(value = "files") List<FileDTO> files, @Param(value = "tableName") String table);

  @Insert({
    "<script>",
    "insert into inspection_graph(id, inspection_case_id, sort_num, url) values ",
    "<foreach collection='files' item='item' index='index' separator=','>",
    "(#{item.fileId}, #{item.caseId}, #{item.sortNum},#{item.url})",
    "</foreach>",
    "</script>"
  })
  int insertInspectionGraphs(@Param("files") List<FileDTO> inspectionGraphList);

  @Delete("DELETE * FROM ${table} WHERE url = #{url}")
  void deleteFilesByGraphUrl(@Param("table") String table, @Param("url") String url);

  @Select("SELECT max(sort_num) FROM ${table} WHERE case_id = #{id} ORDER BY sort_num ")
  Long getMaxFileSortNum(@Param("table") String table, @Param("id") Long caseId);
}
