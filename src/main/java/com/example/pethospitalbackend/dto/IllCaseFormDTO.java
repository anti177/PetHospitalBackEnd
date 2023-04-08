package com.example.pethospitalbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class IllCaseFormDTO {
  Long case_id;
  String front_graph;
  String case_title;
  Long disease_id;
  String admission_text;
  List<String> admission_graphs;
  List<InspectionCaseFrontDTO> inspection_cases;
  String diagnostic_result;
  String therapy_text;
  List<String> therapy_graphs;
  List<String> therapy_videos;
}
