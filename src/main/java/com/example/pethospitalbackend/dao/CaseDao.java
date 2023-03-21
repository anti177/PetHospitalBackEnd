package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.CaseCategoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface CaseDao {
	@ResultType(CaseCategoryDTO.class)
	@Select("SELECT ill_case_id as caseId, case_name as caseName, admission_text as admissionText" +
			" FROM ill_case WHERE disease_id = #{id}")
	List<CaseCategoryDTO> getCaseByDiseaseId(@Param("id") long diseaseId);


}