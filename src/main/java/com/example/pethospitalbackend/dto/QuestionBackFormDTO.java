package com.example.pethospitalbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionBackFormDTO {
  private Long questionId;
  private String questionType;
  private String description;
  private List<String> choice;
  private List<String> ans;
  private String keyword;
  private Long diseaseId;
}
