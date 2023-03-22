package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.TreatmentVideo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TreatmentVideoDao {
	@Insert("INSERT INTO treatment_video(case_id, sort_num, url) VALUES (#{caseId},#{sortNum},#{url});")
	int insertVideo(TreatmentVideo treatmentVideo);

}
