package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.QuestionDao;
import com.example.pethospitalbackend.dao.TestDao;
import com.example.pethospitalbackend.dto.*;
import com.example.pethospitalbackend.entity.Question;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.exception.UserMailNotRegisterOrPasswordWrongException;
import com.example.pethospitalbackend.response.Response;
import com.example.pethospitalbackend.util.JwtUtils;
import com.example.pethospitalbackend.util.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TestService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Resource
	private TestDao testDao;

	@Resource
	private QuestionDao questionDao;

	public Response<List<TestCategoryDTO>> getTestCategoryList() {
		String userId = JwtUtils.getUserId();
		Response<List<TestCategoryDTO>> response = new Response<>();
		if(userId != null){
			try{
				List<TestCategoryDTO> testList = testDao.getTestCategoryByUserId(Long.parseLong(userId));
				response.setSuc(testList);
			}catch (Exception e){
				logger.error("[getTestCategoryDTO Fail], userId : {},error message{}",
						userId,SerialUtil.toJsonStr(e.getMessage()));
				throw  new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
			}
		}else{
			//验证过期
			throw new UserMailNotRegisterOrPasswordWrongException(ResponseEnum.VERIFY_INVALID.getMsg());
		}
		return response;
	}

	public Response<List<EndTestCategoryDTO>> getEndTestCategoryList() {
		String userId = JwtUtils.getUserId();
		Response<List<EndTestCategoryDTO>> response = new Response<>();
		if(userId != null){
			try{
				List<EndTestCategoryDTO> testList = testDao.getEndTestCategoryByUserId(Long.parseLong(userId));
				response.setSuc(testList);
			}catch (Exception e){
				logger.error("[getEndTestCategoryDTO Fail], userId : {},error message{}",
						userId,SerialUtil.toJsonStr(e.getMessage()));
				throw  new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
			}
		}else{
			//验证过期
			throw new UserMailNotRegisterOrPasswordWrongException(ResponseEnum.VERIFY_INVALID.getMsg());
		}
		return response;
	}

	public Response<TestPaperDTO> getTestContent(Long testId) {
		Response<TestPaperDTO> response = new Response<>();
		try{
			//1.获得paper数据
			TestPaperDTO testPaperDTO = questionDao.getTestPaperByUserIdAndTestId(testId);
			//2.获得paper question数据
			if(testPaperDTO!=null && testPaperDTO.getPaperId()!=0){
				List<QuestionDTO> questionList = questionDao.getQuestionByPaperId(testPaperDTO.getPaperId());
				List<PaperQuestionDTO>paperQuestionDTOList = new ArrayList<>();
				for(QuestionDTO q: questionList){
					PaperQuestionDTO dto = new PaperQuestionDTO();
					dto.setQuestionId(q.getQuestionId());
					dto.setDescription(q.getDescription());
					dto.setQuestionType(q.getQuestionType());
					dto.setScore(q.getScore());
					//将choice转为数组
					String[] temp;
					String delimiter = ";";  // 指定分割字符
					temp = q.getChoice().split(delimiter); // 分割字符串
					List<String> choiceList = new ArrayList<>();
					Collections.addAll(choiceList,temp);
					dto.setChoice(choiceList);
					paperQuestionDTOList.add(dto);
				}
				testPaperDTO.setQuestionList(paperQuestionDTOList);
				response.setSuc(testPaperDTO);
			}

		}catch (Exception e){
			logger.error("[ getTestContent Fail], testId : {},error message{}",
					SerialUtil.toJsonStr(testId), SerialUtil.toJsonStr(e.getMessage()));
			throw  new DatabaseException(ResponseEnum.DATABASE_FAIL.getMsg());
		}

		return response;
	}
}
