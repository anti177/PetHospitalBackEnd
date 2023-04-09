package com.example.pethospitalbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ActorDetailBackDTO {
  private Long actorId;

  private String name;

  private String content;

  private String responsibility;

  List<String> processList;
}
