package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("后台返回修改情况模型")
public class ModifiedRecordCountDTO {

  Integer modifiedRecordCount;
}
