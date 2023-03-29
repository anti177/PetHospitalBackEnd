package com.example.pethospitalbackend.service;
import com.example.pethospitalbackend.dao.DrugDao;
import com.example.pethospitalbackend.dao.VaccineDao;import com.example.pethospitalbackend.entity.Drug;
import com.example.pethospitalbackend.entity.Vaccine;
import com.example.pethospitalbackend.enums.ResponseEnum;
import com.example.pethospitalbackend.exception.DatabaseException;
import com.example.pethospitalbackend.response.Response;import com.example.pethospitalbackend.util.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;import java.util.List;

@Service
public class VaccineService {
	private static final Logger logger = LoggerFactory.getLogger(VideoService.class);

	@Resource VaccineDao vaccineDao;

  public Response<List<Vaccine>> getAllVaccines() {
		Response<List<Vaccine>> response = new Response<>();
		List<Vaccine> vaccineList;
		try{
			vaccineList = vaccineDao.selectAll();
		}catch (Exception e){
			logger.error(
					"[getAllVaccines Fail], error message{}", SerialUtil.toJsonStr(e.getMessage()));
			throw new DatabaseException(ResponseEnum.SERVER_ERROR.getMsg());
		}
		response.setSuc(vaccineList);
		return response;
	}
}
