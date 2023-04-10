package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("文件统一模型")
public class FileDTO {
  Long fileId;
  Long caseId;
  Long sortNum;
  String url;
}
