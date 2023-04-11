package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ApiModel("后台新建角色模型")
public class ActorFormBackDTO {
  @NotBlank
  @ApiModelProperty(value = "角色id")
  private Long actorId;

  @NotBlank
  @ApiModelProperty(value = "角色名称")
  private String name;

  @ApiModelProperty(value = "角色内容")
  private String content;

  @NotBlank
  @ApiModelProperty(value = "角色职责")
  private String responsibility;

  @ApiModelProperty(value = "角色关联的流程名称列表")
  List<Long> processList;
}
