package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.Disease;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DiseaseDao {
	@ResultType(String.class)
	@Select("SELECT DISTINCT type_name FROM disease ORDER BY type_name")
	List<String> getAllType();

	@ResultType(Disease.class)
	@Select("SELECT disease_id as diseaseId, disease_name as diseaseName,type_name as typeName FROM disease ORDER BY type_name")
	List<Disease> getAllDisease();

}
