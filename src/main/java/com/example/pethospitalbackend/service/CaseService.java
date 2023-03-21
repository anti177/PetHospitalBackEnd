package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.DiseaseDao;
import com.example.pethospitalbackend.dto.CategoryDTO;
import com.example.pethospitalbackend.entity.Disease;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.util.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaseService {
	private static final Logger logger = LoggerFactory.getLogger(CaseService.class);

	@Autowired
	DiseaseDao diseaseDao;

	public Response<List<CategoryDTO>> getTotalCategory() {
		Response<List<CategoryDTO>> response = new Response<>();
		List<String> typeList;
		List<Disease> diseaseList;
		try{
			typeList = diseaseDao.getAllType();
			diseaseList = diseaseDao.getAllDisease();
		}catch (Exception e){
			logger.error("[getTotalCategory Fail], error message{}", SerialUtil.toJsonStr(e.getMessage()));
			throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
		}
		List<CategoryDTO> categoryDTOList = new ArrayList<>();
		for(int i = 0;i < typeList.size();i++){
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setTypeId(i);
			categoryDTO.setTypeName(typeList.get(i));
			List<Disease> subDiseaseList = new ArrayList<>();
			boolean endFlag = false;
			for(Disease d : diseaseList){
				if(d.getTypeName().equals(typeList.get(i))){
					subDiseaseList.add(d);
					if(!endFlag) endFlag = true;
				}else{
					if(endFlag)break;
				}
			}
			categoryDTO.setDiseaseDTOList(subDiseaseList);
			categoryDTOList.add(categoryDTO);
		}

		response.setSuc(categoryDTOList);
	   return response;

	}
}
