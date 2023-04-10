package com.example.pethospitalbackend.dto;

import com.example.pethospitalbackend.entity.Test;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TestDetailBackDTO {
  @ApiModelProperty(value = "考试场次信息")
  private Test test;

  @ApiModelProperty(value = "参考人员名单")
  private List<String> userList;
}
