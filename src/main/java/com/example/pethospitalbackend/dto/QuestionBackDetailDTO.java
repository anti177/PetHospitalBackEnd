package com.example.pethospitalbackend.dto;

import com.example.pethospitalbackend.entity.Disease;
import lombok.Data;

import java.util.List;

@Data
public class QuestionBackDetailDTO {
  private Long questionId;
  private String questionType;
  private String description;
  private List<String> choice;
  private List<String> ans;
  private String keyword;
  private Disease disease;
}
