package com.nhncloud.edu.restapi.cont;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhncloud.edu.cont.DbInfoContoller;
import com.nhncloud.edu.cont.FileUploadController;
import com.nhncloud.edu.cont.LocalFileListController;
import com.nhncloud.edu.cont.ObjectContainerListContoller;
import com.nhncloud.edu.cont.SecureKeyManagerController;
import com.nhncloud.edu.cont.TokenController;

import com.nhncloud.edu.dto.ContainerListVo;
import com.nhncloud.edu.dto.ObsFileVo;
import com.nhncloud.edu.dto.ObsValueVo;
import com.nhncloud.edu.dto.TokenRequest;

@RestController
@RequestMapping("api")
public class RestApiContoller {
	// obs_file_upload.do
	// 1. Token 호출 API
	@PostMapping("/get_token.do")
	public String returnToken(@RequestBody TokenRequest tr) {
		TokenController tc = new TokenController(tr.getAuth().getTenantId(),
				tr.getAuth().getPasswordCredentials().getUsername(),
				tr.getAuth().getPasswordCredentials().getPassword());
		System.out.println("getToken Start : " + tc.requestToken());

		JSONObject jsonString = new JSONObject(tc.requestToken());
		String tokenKeyID = jsonString.getJSONObject("access").getJSONObject("token").getString("id");
		// String user_token = tc.requestToken();

		System.out.println(tokenKeyID);

		// clad.setStorageUrl(ov.getObs());
		// clad.setTokenId(user_token);

		return tc.requestToken();
	}

	// 2. Local File JSON 생성 API
	@PostMapping("/get_local_file.do")
	public String returnLocalFile() {
		LocalFileListController lflc = new LocalFileListController();
		// upload 폴더 입력
		String result = lflc.getObjectList("upload");
		System.out.println("local file info : " + result);

		return result;
	}

	// 2-1. Local File JSON 불러오기 API
	@PostMapping("/get_json_info.do")
	public String returnLocalJsonFile() {
		LocalFileListController lflc = new LocalFileListController();
		// upload 폴더 입력
		String result = lflc.getJsonFile();
		System.out.println("local JSON file info : " + result);

		return result;
	}
//[{"hash":"19116919414d17c19a1f518917417b19710d13c12018b1fe","last_modified":"2023-04-24T06:48:50.772Z","bytes":"99244","name":"image2.png","content_type":"image/png"},{"hash":"1961a21c31f01361f017f19612f1821d41d91e41b712a1e0","last_modified":"2023-04-22T22:08:05.683Z","bytes":"37285","name":"undraw_posting_photo.svg","content_type":"image/svg+xml"}]
//

	// 3. Local File Upload API
	@PostMapping("/local_file_upload.do")
	public String uploadLocalFile(@RequestPart(value = "file", required = false) MultipartFile file) {
		System.out.println("upload Start");
		FileUploadController fulc = new FileUploadController();

		String test = fulc.upload(file);

		return test;
	}

	// 4. OBS Container List JSON 생성 API
	@PostMapping("/get_container.do")
	public String returnObsContainerList(@RequestBody ObsValueVo vo) {
		ObjectContainerListContoller olc = new ObjectContainerListContoller();
		System.out.println(vo.getToken() + " --------- " + vo.getObs());
		List<String> result = olc.getContainerListWas(vo.getToken(), vo.getObs());
		List<ContainerListVo> list = new ArrayList<ContainerListVo>();
		if (result.size() != 0) {
			for (int i = 0; i < result.size(); i++) {
				ContainerListVo clv = new ContainerListVo();
				clv.setC_name(result.get(i));
				list.add(clv);
			}
		}

		// JSON 만들기
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		JSONArray jarr = new JSONArray();
		try {
			jsonString = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jarr.put(jsonString);
		System.out.println("JSON List Test : " + jsonString);

		return jsonString;
	}

	// 5. OBS File JSON 생성 API
	@GetMapping("/get_obs_file.do")
	public String returnObsFile(@RequestParam("c_name") String c_name, @RequestParam("obs") String obs) {
		// Json 파일 받아오기
		ObjectContainerListContoller olc = new ObjectContainerListContoller();
		String result = olc.getContainerFileListWas(obs + "/" + c_name);
		System.out.println("obs Get file URL : " + obs + "/" + c_name);
		// JSON 만들기

		return result;
	}
	

	// 6. OBS File Upload 실행 API
	// obs_file_upload.do
	@PostMapping("/obs_file_upload.do")
	public String uploadObsFile(ObsFileVo ofv) {
		System.out.println("OBS upload Start");
		System.out.println(ofv.getToken());
		System.out.println(ofv.getObs());
		System.out.println(ofv.getC_name());
		FileUploadController fulc = new FileUploadController();
		String result = fulc.obsupload(ofv);

		return result;
	}

	// 7. Movie URL Info by DB - get_db_info.do
	@GetMapping("/get_db_info.do")
	public String returnDbInfo(@RequestParam("c_name") String c_name, @RequestParam("user_appkey") String user_appkey,
			@RequestParam("skm_id") String skm_id) {
		SecureKeyManagerController skmc = new SecureKeyManagerController(user_appkey, skm_id);
		System.out.println("User Appkey : " + user_appkey);
		System.out.println("SKM DB ID : " + skm_id);
		DbInfoContoller dic = new DbInfoContoller(skmc.getDbUrl());
		System.out.println("teatsdrfsa :" + c_name);
		String test = dic.getMovieInfo(c_name);
		File directory = new File("/mnt/");
		if (directory.isDirectory()) {
			try {
				FileWriter file = new FileWriter("/mnt/local_video.json");
				file.write(test);
				file.flush();
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("Test DB Count : " + test);

		return test;
	}

	// 8. Movie URL Add in DB - add_db_info.do
	@GetMapping("/add_db_info.do")
	public int addDbInfo(@RequestParam("c_title") String c_title, @RequestParam("y_code") String y_code,
			@RequestParam("c_name") String c_name, @RequestParam("token") String token, @RequestParam("obs") String obs,
			@RequestParam("user_appkey") String user_appkey, @RequestParam("skm_id") String skm_id) {
		SecureKeyManagerController skmc = new SecureKeyManagerController(user_appkey, skm_id);
		DbInfoContoller dic = new DbInfoContoller(skmc.getDbUrl());
		System.out.println("teatsdrfsa :" + c_name);
		// String c_name, String c_title, String y_code
		int cnt = dic.addMovieInfo(c_name, c_title, y_code);
		System.out.println("Test DB Cound : " + cnt);

		return cnt;
	}	


}
