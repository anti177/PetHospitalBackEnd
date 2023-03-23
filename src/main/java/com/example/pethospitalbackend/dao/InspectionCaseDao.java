package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.InspectionDTO;
import com.example.pethospitalbackend.entity.InspectionCase;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface InspectionCaseDao extends Mapper<InspectionCase> {
    
    @ResultType(InspectionDTO.class)
    @Select("SELECT inspection_case_id as inspectionCaseId, result, fee, item_name as itemName, department_name as departmentName, inspection_item.intro as intro " + "FROM (inspection_case left JOIN inspection_item ON item_id = inspection_item_id) " + "left JOIN department on inspection_item.department_id = department.department_id " + "where inspection_case.case_id = #{id} " + "ORDER BY sort_num")
    List<InspectionDTO> getInspectionCaseByCaseId(@Param("id") long caseId);
    
    
    @Select("SELECT url FROM inspection_case JOIN inspection_graph " + "on inspection_case.inspection_case_id = inspection_graph.inspection_case_id " + "where inspection_case.inspection_case_id = #{id} ORDER BY inspection_graph.sort_num")
    List<String> getInspectionGraphByInspectionCaseId(@Param("id") long inspectionCaseId);
}
