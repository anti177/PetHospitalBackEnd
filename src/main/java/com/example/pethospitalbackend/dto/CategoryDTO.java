package com.example.pethospitalbackend.dto;

import com.example.pethospitalbackend.entity.Disease;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@ApiModel("病例目录模型")
@Data
public class CategoryDTO {
	@ApiModelProperty(value = "病种ID")
	private int typeId;

	@ApiModelProperty(value = "病种名称")
	private String typeName;

	@ApiModelProperty(value = "操作顺序序号")
	private List<Disease> diseaseDTOList;
}
