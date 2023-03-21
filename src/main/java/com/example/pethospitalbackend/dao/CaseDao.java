package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.CaseCategoryDTO;
import com.example.pethospitalbackend.entity.IllCase;
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

	@ResultType(IllCase.class)
	@Select("SELECT ill_case_id as caseId, case_name as caseName, admission_text as admissionText, " +
			"diagnostic_info as diagnosticInfo, treatment_text as treatmentInfo " +
			" FROM ill_case WHERE ill_case_id = #{id}")
	IllCase getCaseByCaseId(@Param("id") long caseId);

	@Select("SELECT url  FROM admission_graph WHERE case_id = #{id} ORDER BY sort_num ")
	List<String> getAdmissionGraphByCaseId(@Param("id") long caseId);

	@Select("SELECT url  FROM treatment_graph WHERE case_id = #{id} ORDER BY sort_num ")
	List<String> getTreatmentGraphByCaseId(@Param("id") long caseId);

	@Select("SELECT url  FROM treatment_video WHERE case_id = #{id} ORDER BY sort_num ")
	List<String> getTreatmentVideoByCaseId(@Param("id") long caseId);

}