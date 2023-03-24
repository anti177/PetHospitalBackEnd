package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 使用oos上传文件的例子
 *
 * @author yyx
 */
@RestController
@Api(tags = {"上传文件的例子"})
public class UploadFileExampleController {
    
    @Resource
    VideoService videoService;
    
    @PostMapping("/video")
    @ApiOperation("添加视频")
    public ResponseEntity<Response<Boolean>> addVideo(@RequestParam("video_mp4") MultipartFile video_mp4) {
        Response<Boolean> response = new Response<>();
        videoService.addVideo(video_mp4);
        response.setSuc(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/videos")
    @ApiOperation("添加多个视频")
    public ResponseEntity<Response<Boolean>> addVideos(@RequestParam("videos") MultipartFile[] videos) {
        Response<Boolean> response = new Response<>();
        videoService.addVideos(videos);
        response.setSuc(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
