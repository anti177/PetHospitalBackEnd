package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.TreatmentVideo;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

public interface TreatmentVideoDao extends Mapper<TreatmentVideo> {

  @Insert(
      "INSERT INTO treatment_video(case_id, sort_num, url) VALUES (#{caseId},#{sortNum},#{url});")
  int insertVideo(TreatmentVideo treatmentVideo);
}
