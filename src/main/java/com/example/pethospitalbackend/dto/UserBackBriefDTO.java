package com.example.pethospitalbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("后台用户简单模型（用于选择参加考试的用户）")
public class UserBackBriefDTO {

  @ApiModelProperty("用户id")
  Long userId;

  @ApiModelProperty("用户邮箱")
  String email;
}
