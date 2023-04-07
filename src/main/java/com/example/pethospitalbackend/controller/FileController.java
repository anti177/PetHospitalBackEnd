package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 使用oos上传文件的例子
 *
 * @author yyx
 */
@RestController
@Api(tags = {"上传文件的例子"})
public class FileController {

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
  @ApiOperation("添加视频")
  public Response<String> addVideos(@RequestParam("videos") MultipartFile video) {
    Response<String> response = new Response<>();
    String url = fileService.addVideos(video);
    response.setSuc(url);
    return response;
  }

  @PostMapping("/graphs")
  @ApiOperation("添加图片")
  public Response<String> addGraphs(@RequestParam("graphs") MultipartFile graph) {
    Response<String> response = new Response<>();
    String url = fileService.addGraphs(graph);
    response.setSuc(url);
    return response;
  }

  @DeleteMapping("/graphs")
  @ApiOperation("删除图片")
  public Response<Boolean> deleteGraphs(@RequestBody String url) {
    Response<Boolean> response = new Response<>();
    response.setSuc(fileService.deleteGraph(url));
    return response;
  }

  @DeleteMapping("/videos")
  @ApiOperation("删除视频")
  public Response<Boolean> deleteVideos(@RequestBody String url) {
    Response<Boolean> response = new Response<>();
    response.setSuc(fileService.deleteVideo(url));
    return response;
  }
}
