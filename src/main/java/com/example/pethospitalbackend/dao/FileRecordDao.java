package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.entity.FileRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.additional.update.batch.BatchUpdateMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FileRecordDao extends Mapper<FileRecord>, BatchUpdateMapper<FileRecord> {

  @Update("update file set in_use = #{inUse} where url = #{url}")
  int updateStatusByUrl(@Param("url") String url, @Param("inUse") Boolean inUse);

  @Select("select url from file where in_use = false and type = 'graph'")
  List<String> selectUnusedGraphs();

  @Select("select url from file where in_use = false and type = 'video'")
  List<String> selectUnusedVideos();

  @Delete("delete from file where in_use = false")
  int deleteUnusedRecords();
}
