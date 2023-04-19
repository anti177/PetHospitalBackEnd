package com.example.pethospitalbackend;
import com.example.pethospitalbackend.dao.DiseaseDao;import com.example.pethospitalbackend.dto.CaseBackFormDTO;import com.example.pethospitalbackend.dto.InspectionCaseFrontDTO;import com.example.pethospitalbackend.entity.Disease;import com.example.pethospitalbackend.entity.IllCase;import com.example.pethospitalbackend.service.CaseService;import com.example.pethospitalbackend.util.OSSUtil;import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.util.Proxys;

import java.net.Proxy;import java.util.ArrayList;import java.util.List;import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class gptTest {
  @Resource DiseaseDao diseaseDao;
  @Resource CaseService caseService;
  @Resource OSSUtil ossUtil;
  @Test
  public void test() {
	  ChatGPT chatGPT = ChatGPT.builder()
			  .apiKey("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")
			  .apiHost("https://api.openai.com/") //反向代理地址
			  .build()
			  .init();

	  List<Disease> diseaseList = diseaseDao.getAllDisease();
	  for(Disease disease : diseaseList){
		  if(disease.getDiseaseId() == 1){
			  String name = disease.getDiseaseName();
			  System.out.println(name);
			  String require1 ="写50字宠物狗得"+name+"的初次接诊信息，不要分点，写一段话";
			  String res1 = chatGPT.chat(require1);
			  String require2 ="写50字宠物狗得"+name+"的诊断信息，不要分点，写一段话";
			  String res2 = chatGPT.chat(require2);
			  String require3 ="写50字宠物狗得"+name+"的治疗方案，不要分点，写一段话";
			  String res3 = chatGPT.chat(require3);
			  for(int i = 0;i < 2;i++){
				  CaseBackFormDTO dto = new CaseBackFormDTO();
				  dto.setCase_title(name+"病例"+(i+1));
				  dto.setAdmission_text(res1);
				  dto.setDiagnostic_result(res2);
				  dto.setTreatment_info(res3);
				  dto.setDisease_id(disease.getDiseaseId());

				  Random random = new Random();
				  int randomNumber = random.nextInt(8) + 1;
				  String front_graph1 = "https://pet-hospital-back-end-picture.oss-cn-shanghai.aliyuncs.com/cat/cat"+randomNumber+".jpg";
				  String front_graph2 = "https://pet-hospital-back-end-picture.oss-cn-shanghai.aliyuncs.com/dog/dog"+randomNumber+".jpg";
				  //if(i % 2 == 0)dto.setFront_graph(front_graph1);
				  dto.setFront_graph(front_graph2);
				  List<String>  admission_graph = asList(front_graph1,"https://pet-hospital-back-end-picture.oss-cn-shanghai.aliyuncs.com/process/14.png",
						  "https://pet-hospital-back-end-picture.oss-cn-shanghai.aliyuncs.com/process/13.png");

				  List<String>  therapy_graph = asList("https://pet-hospital-back-end-picture.oss-cn-shanghai.aliyuncs.com/process/19.png",
						  "https://pet-hospital-back-end-picture.oss-cn-shanghai.aliyuncs.com/process/18.png");

				  InspectionCaseFrontDTO inspectionCaseFrontDTO = new InspectionCaseFrontDTO();
				  inspectionCaseFrontDTO.setInspection_item_id(1L);
				  inspectionCaseFrontDTO.setInspection_result_text("白细胞较高，有轻微感染");
				  List<String>  inspection_graphs = asList("https://pet-hospital-back-end-picture.oss-cn-shanghai.aliyuncs.com/process/29.png"
						);
				  inspectionCaseFrontDTO.setInspection_graphs(inspection_graphs);

				  InspectionCaseFrontDTO inspectionCaseFrontDTO2 = new InspectionCaseFrontDTO();
				  inspectionCaseFrontDTO2.setInspection_item_id(1L);
				  inspectionCaseFrontDTO2.setInspection_result_text("骨线清晰，无骨折");
				  List<String>  inspection_graphs2 = asList(
						  "https://pet-hospital-back-end-picture.oss-cn-shanghai.aliyuncs.com/process/15.png");
				  inspectionCaseFrontDTO2.setInspection_graphs(inspection_graphs2);

				  List<InspectionCaseFrontDTO> dtos = asList(inspectionCaseFrontDTO,inspectionCaseFrontDTO2);
				  dto.setInspection_cases(dtos);
				  dto.setAdmission_graphs(admission_graph);
				  dto.setTherapy_graphs(therapy_graph);

				  caseService.addCase(dto);
				  System.out.println("add ok");
			  }

		  }


	  }
		//国内需要代理
		//Proxy proxy = Proxys.http("127.0.0.1", 1081);
		//socks5 代理
		// Proxy proxy = Proxys.socks5("127.0.0.1", 1080);

	}
  @Test
  public void fileTest(){

	  ossUtil.deleteFile("pet-hospital-back-end",
			  "cat1.jpg");
  }
}
