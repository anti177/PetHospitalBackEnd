package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 使用oos上传文件的例子
 *
 * @author yyx
 */
@RestController
@Api(tags = {"上传文件的例子"})
public class UploadFileExampleController {

  @Resource FileService fileService;

  @PostMapping("/video")
  @ApiOperation("添加视频测试")
  public ResponseEntity<Response<Boolean>> addVideo(
      @RequestParam("video_mp4") MultipartFile video_mp4) {
    Response<Boolean> response = new Response<>();
    fileService.addVideo(video_mp4);
    response.setSuc(null);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/videos")
  @ApiOperation("添加多个视频")
  public Response<List<String>> addVideos(@RequestParam("videos") MultipartFile[] videos) {
    Response<List<String>> response = new Response<>();
    List<String> urlList = fileService.addVideos(videos);
    response.setSuc(urlList);
    return response;
  }

  @PostMapping("/graphs")
  @ApiOperation("添加多个图片")
  public Response<List<String>> addGraphs(@RequestParam("graphs") MultipartFile[] graphs) {
    Response<List<String>> response = new Response<>();
    List<String> urlList = fileService.addGraphs(graphs);
    response.setSuc(urlList);
    return response;
  }
}
