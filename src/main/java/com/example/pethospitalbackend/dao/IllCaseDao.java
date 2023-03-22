package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.IllCaseDTO;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.IllCase;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface IllCaseDao extends Mapper<IllCase> {
    
    @Select("SELECT * from ill_case")
    @Results(id = "ill_case_map",
            value = {@Result(id = true, column = "ill_case_id", property = "illCaseId"), @Result(column = "case_name",
                    property = "illCaseName"), @Result(property = "disease", column = "disease_id",
                    javaType = Disease.class,
                    one = @One(select = "com.example.pethospitalbackend.dao.DiseaseDao.selectByPrimaryKey",
                            fetchType = FetchType.EAGER))})
    List<IllCaseDTO> findAll();
}
