package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.TreatmentVideoDao;
import com.example.pethospitalbackend.entity.TreatmentVideo;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.util.JwtUtils;
import com.example.pethospitalbackend.util.OSSUtil;
import com.example.pethospitalbackend.util.SerialUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class VideoService {

  private static final Logger logger = LoggerFactory.getLogger(VideoService.class);

  @Resource OSSUtil ossUtil;

  @Resource TreatmentVideoDao treatmentVideoDao;

  private final String bucketName = "pet-hospital-back-end";

  // 上传视频的例子
  public void addVideo(MultipartFile video_mp4) {

    String code = RandomStringUtils.randomNumeric(5);
    String filename =
        code + "video_publisher" + JwtUtils.getUserId() + "/" + video_mp4.getOriginalFilename();
    String url = ossUtil.uploadFile(bucketName, video_mp4, filename);
    if (StringUtils.isBlank(url)) {
      logger.error(
          "[addVideo Fail], video_mp4: {}", SerialUtil.toJsonStr(video_mp4.getOriginalFilename()));
      throw new RuntimeException(ResponseEnum.UPLOAD_OSS_FAILURE.getMsg());
    }
    // 修改数据库
    TreatmentVideo video = new TreatmentVideo();
    video.setCaseId(1L);
    video.setUrl(url);
    video.setSortNum(4L);

    boolean result = treatmentVideoDao.insertVideo(video) > 0;

    if (!result) {
      logger.error("[addVideo Fail], video_mp4: {}", SerialUtil.toJsonStr(video_mp4));
      throw new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
    }
  }

  public void addVideos(MultipartFile[] videos) {
    for (MultipartFile video : videos) {
      String code = RandomStringUtils.randomNumeric(5);
      String filename =
          code + "video_publisher" + JwtUtils.getUserId() + "/" + video.getOriginalFilename();
      String url = ossUtil.uploadFile(bucketName, video, filename);
      if (StringUtils.isBlank(url)) {
        logger.error(
            "[addVideo Fail], video_mp4: {}", SerialUtil.toJsonStr(video.getOriginalFilename()));
        throw new RuntimeException(ResponseEnum.UPLOAD_OSS_FAILURE.getMsg());
      }
      // 在数据添加url
    }
  }
}
