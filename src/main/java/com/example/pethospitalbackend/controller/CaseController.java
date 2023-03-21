package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.dto.IllCaseDTO;
import com.example.pethospitalbackend.dto.IllCaseFormDTO;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.entity.IllCase;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.service.DiseaseService;
import com.example.pethospitalbackend.service.IllCaseService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
@Api(tags = {"病例管理"})
public class CaseController {
    
    @Resource
    IllCaseService illCaseService;
    
    @Resource
    DiseaseService diseaseService;
    
    // todo: 增加逻辑，异常处理
    
    /**
     * 分页获取病例
     *
     * @param pageNum  第几页
     * @param pageSize 每页数量
     * @return
     */
    @GetMapping("/cases")
    ResponseEntity<Response<PageInfo<IllCaseDTO>>> getAllCases(
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    /**
     * 获取特定病例
     *
     * @param id 病例id
     * @return
     */
    @GetMapping("/cases/{id}")
    ResponseEntity<Response<IllCase>> getCase(@PathVariable Integer id) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @GetMapping("/cases")
    ResponseEntity<Response<IllCase>> searchCase(@RequestParam Integer search_field, @RequestParam String content) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @PostMapping("/cases")
    ResponseEntity<Response<IllCase>> postCase(
            @RequestPart("form") IllCaseFormDTO form,
            @RequestPart("admission_pictures") MultipartFile[] admission_pictures,
            @RequestPart("inspection_pictures") MultipartFile[] inspection_pictures,
            @RequestPart("therapy_video") MultipartFile therapy_video) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @PutMapping("/cases/{id}")
    ResponseEntity<Response<IllCase>> putCase(@PathVariable Long id) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @DeleteMapping("/cases/{id}")
    ResponseEntity<Response<IllCase>> deleteCase(@PathVariable Long id) { // 返回新建对象的详细信息或id
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    /**
     * 分页返回疾病信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/diseases")
    ResponseEntity<Response<PageInfo<Disease>>> getAllDiseases(
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        diseaseService.getDiseasePageInfo(pageNum, pageSize);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    /**
     * 添加疾病信息
     *
     * @param disease
     * @return
     */
    @PostMapping("/diseases")
    ResponseEntity<Response<Disease>> postDisease(Disease disease) {
        diseaseService.addDisease(disease);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    /**
     * 修改疾病信息
     *
     * @param id
     * @param disease
     * @return
     */
    @PutMapping("/diseases/{id}")
    ResponseEntity<Response<Disease>> putDisease(@PathVariable Long id, Disease disease) {
        diseaseService.updateDisease(disease);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    /**
     * 删除疾病信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/diseases/{id}")
    ResponseEntity<Response<Disease>> deleteDisease(@PathVariable Long id) {
        diseaseService.deleteDisease(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
