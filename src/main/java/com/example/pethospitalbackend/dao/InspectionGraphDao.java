package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.InspectionGraph;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface InspectionGraphDao
    extends Mapper<InspectionGraph>, InsertListMapper<InspectionGraph> {}
