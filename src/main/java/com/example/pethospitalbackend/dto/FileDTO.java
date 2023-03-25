package com.example.pethospitalbackend.dto;

import lombok.Data;

@Data
public class FileDTO {

  Long fileId;
  Long caseId;
  Long sortNum;
  String url;
}
