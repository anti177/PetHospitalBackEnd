package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 使用oos上传文件
 *
 * @author yyx
 */
@RestController
@Api(tags = {"文件管理"})
public class FileController {

  @Resource FileService fileService;

  @PostMapping("/videos")
  @ApiOperation("添加视频")
  public Response<String> addVideo(@RequestParam("videos") MultipartFile video) {
    Response<String> response = new Response<>();
    String url = fileService.addVideo(video);
    response.setSuc(url);
    return response;
  }

  @PostMapping("/graphs")
  @ApiOperation("添加图片")
  public Response<String> addGraphs(@RequestParam("graphs") MultipartFile graph) {
    Response<String> response = new Response<>();
    String url = fileService.addGraph(graph);
    response.setSuc(url);
    return response;
  }

  @DeleteMapping("/graphs")
  @ApiOperation("删除图片")
  public Response<Boolean> deleteGraph(@RequestBody String url) {
    Response<Boolean> response = new Response<>();
    response.setSuc(fileService.deleteGraph(url));
    return response;
  }

  @DeleteMapping("/videos")
  @ApiOperation("删除视频")
  public Response<Boolean> deleteVideo(@RequestBody String url) {
    Response<Boolean> response = new Response<>();
    response.setSuc(fileService.deleteVideo(url));
    return response;
  }
}
