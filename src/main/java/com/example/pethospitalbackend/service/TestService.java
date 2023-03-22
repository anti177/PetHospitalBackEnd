package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.dao.TestDao;
import com.example.pethospitalbackend.dto.EndTestCategoryDTO;
import com.example.pethospitalbackend.dto.TestCategoryDTO;
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
import java.util.List;

@Service
public class TestService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Resource
	private TestDao testDao;

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
}
