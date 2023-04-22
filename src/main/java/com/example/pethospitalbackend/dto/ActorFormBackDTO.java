package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel("后台新建角色模型")
public class ActorFormBackDTO {

  @ApiModelProperty(value = "角色关联的流程id列表")
  List<Long> processList;

  @NotEmpty
  @ApiModelProperty(value = "角色id", required = true)
  private Long actorId;

  @NotEmpty
  @ApiModelProperty(value = "角色名称", required = true)
  private String name;

  @ApiModelProperty(value = "角色内容")
  private String content;

  @ApiModelProperty(value = "角色职责")
  private String responsibility;
}
