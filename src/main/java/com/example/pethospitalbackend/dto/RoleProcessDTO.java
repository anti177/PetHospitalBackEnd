package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel("角色流程模型")
@Data
public class RoleProcessDTO {

  @NotBlank
  @ApiModelProperty(value = "流程id")
  private long id;

  @ApiModelProperty(value = "流程名称")
  private String name;

  @ApiModelProperty(value = "流程介绍")
  private String intro;

  @ApiModelProperty(value = "流程操作")
  private List<OperationDTO> operationDTOList;
}
