package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.FileDTO;
import com.example.pethospitalbackend.dto.InspectionCaseBackDTO;
import com.example.pethospitalbackend.dto.InspectionFrontDTO;
import com.example.pethospitalbackend.dto.InspectionItemBackDTO;
import com.example.pethospitalbackend.entity.InspectionCase;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

public interface InspectionCaseDao
    extends Mapper<InspectionCase>, InsertListMapper<InspectionCase> {

  @ResultType(InspectionFrontDTO.class)
  @Select(
      "SELECT inspection_case_id as inspectionCaseId, result, fee, item_name as itemName, department_name as departmentName, inspection_item.intro as intro "
          + "FROM (inspection_case left JOIN inspection_item ON item_id = inspection_item_id) "
          + "left JOIN department on inspection_item.department_id = department.department_id "
          + "where inspection_case.case_id = #{id} "
          + "ORDER BY sort_num")
  List<InspectionFrontDTO> getInspectionCaseByCaseId(@Param("id") long caseId);

  @Select(
      "SELECT url FROM inspection_case JOIN inspection_graph "
          + "on inspection_case.inspection_case_id = inspection_graph.inspection_case_id "
          + "where inspection_case.inspection_case_id = #{id} ORDER BY inspection_graph.sort_num")
  List<String> getInspectionGraphByInspectionCaseId(@Param("id") long inspectionCaseId);

  @Select("SELECT * FROM inspection_graph WHERE inspection_case_id = #{id} ORDER BY sort_num ")
  @Results(
      id = "inspection_graph_list",
      value = {
        @Result(id = true, column = "id", property = "fileId"),
        @Result(column = "inspection_case_id", property = "caseId"),
        @Result(column = "url", property = "url"),
        @Result(column = "sort_num", property = "sortNum")
      })
  List<FileDTO> getInspectionGraphDTOByInspectionCaseId(@Param("id") long inspectionCaseId);

  @Select(
      "SELECT inspection_item_id, item_name FROM inspection_item WHERE inspection_item_id = #{id}")
  @Results(
      id = "inspectionItem",
      value = {
        @Result(id = true, column = "inspection_item_id", property = "itemId"),
        @Result(column = "item_name", property = "itemName")
      })
  InspectionItemBackDTO getInspectionItemById(@Param("id") long itemId);

  @Select(
      "SELECT inspection_case_id, item_id, result from inspection_case where case_id = #{id} ORDER BY sort_num")
  @Results(
      id = "inspection_case_list",
      value = {
        @Result(id = true, column = "inspection_case_id", property = "inspectionCaseId"),
        @Result(
            property = "inspectionItem",
            column = "item_id",
            javaType = InspectionItemBackDTO.class,
            one =
                @One(
                    select =
                        "com.example.pethospitalbackend.dao.InspectionCaseDao.getInspectionItemById",
                    fetchType = FetchType.EAGER)),
        @Result(property = "result", column = "result"),
        @Result(
            property = "inspectionGraphs",
            column = "inspection_case_id",
            javaType = List.class,
            many =
                @Many(
                    select =
                        "com.example.pethospitalbackend.dao.InspectionCaseDao.getInspectionGraphDTOByInspectionCaseId"))
      })
  List<InspectionCaseBackDTO> getInspectionCaseBackDTOByIllCaseId(@Param("id") long IllCaseId);

  @Delete(
      "<script>"
          + "delete from inspection_graph where inspection_case_id in "
          + "<foreach collection='id' open='(' item='id_' separator=',' close=')'> #{id_}"
          + "</foreach>"
          + "</script>")
  int deleteInspectionGraphsByInspectionCaseId(@Param("id") List<Long> id);

  @Delete(
      "<script>"
          + "delete from inspection_case where inspection_case_id in "
          + "<foreach collection='id' open='(' item='id_' separator=',' close=')'> #{id_}"
          + "</foreach>"
          + "</script>")
  int deleteInspectionCasesByInspectionCaseId(@Param("id") List<Long> id);

  @Select("SELECT inspection_case_id from inspection_case where case_id = #{id}")
  List<Long> selectAllInspectionCaseIdByIllCaseId(@Param("id") long illCaseId);

  @Select("SELECT inspection_item_id, item_name from inspection_item")
  @Results(
      id = "inspection_item_list",
      value = {
        @Result(id = true, column = "inspection_item_id", property = "itemId"),
        @Result(column = "item_name", property = "itemName")
      })
  List<InspectionItemBackDTO> selectAllInspectionItems();
}
