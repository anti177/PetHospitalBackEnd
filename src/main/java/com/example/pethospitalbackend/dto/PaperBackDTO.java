package com.example.pethospitalbackend.dto;

import com.example.pethospitalbackend.entity.Paper;
import lombok.Data;

import java.util.List;

@Data
public class PaperBackDTO {
  Paper paper;
  List<QuestionWithScoreDTO> list;
}
