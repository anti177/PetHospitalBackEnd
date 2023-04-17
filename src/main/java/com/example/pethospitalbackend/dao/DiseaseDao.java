package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.DiseaseDTO;
import com.example.pethospitalbackend.entity.Disease;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DiseaseDao extends Mapper<Disease> {

  @ResultType(String.class)
  @Select("SELECT DISTINCT type_name FROM disease ORDER BY type_name")
  List<String> getAllType();

  @ResultType(Disease.class)
  @Select(
      "SELECT disease_id as diseaseId, disease_name as diseaseName,type_name as typeName FROM disease ORDER BY type_name")
  List<Disease> getAllDisease();

  @ResultType(DiseaseDTO.class)
  @Select(
      "SELECT disease_id as diseaseId, disease_name as diseaseName FROM disease where disease_id=#{id}")
  DiseaseDTO selectDTOByPrimaryKey(@Param("id") Long id);
}
