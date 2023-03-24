package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InspectionItemBackDTO {
    
    @ApiModelProperty("检查项目id")
    private long itemId;
    @ApiModelProperty("检查项目名称")
    private String itemName;
}
