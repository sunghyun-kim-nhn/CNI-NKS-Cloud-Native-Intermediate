package com.nhncloud.edu.cont;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhncloud.edu.dto.AuthDto;

public class ObjectContainerListContoller {
	public List<String> getContainerList(AuthDto clad) {
		// 헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", clad.getTokenId());

		HttpEntity<String> requestHttpEntity = new HttpEntity<String>(null, headers);
		RestTemplate restTemplate = new RestTemplate();

		// API 호출
		ResponseEntity<String> response = restTemplate.exchange(clad.getStorageUrl(), HttpMethod.GET, requestHttpEntity,
				String.class);

		List<String> containerList = null;
		if (response.getStatusCode() == HttpStatus.OK) {
			// String으로 받은 목록을 배열로 변환
			containerList = Arrays.asList(response.getBody().split("\\r?\\n"));
		}

		// 배열을 List로 변환하여 반환
		return new ArrayList<String>(containerList);
	}

	public List<String> getContainerListWas(String token, String url) {
		// 헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<String> requestHttpEntity = new HttpEntity<String>(null, headers);
		RestTemplate restTemplate = new RestTemplate();

		// API 호출
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestHttpEntity, String.class);

		List<String> containerList = null;
		if (response.getStatusCode() == HttpStatus.OK) {
			// String으로 받은 목록을 배열로 변환
			containerList = Arrays.asList(response.getBody().split("\\r?\\n"));
		}

		// 배열을 List로 변환하여 반환
		return new ArrayList<String>(containerList);
	}

	public String getContainerFileListWas(String url) {
		// 헤더 생성		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class);
		Object[] objects = responseEntity.getBody();
		System.out.println("requestBody :" + objects[0].toString());

		ObjectMapper mapper = new ObjectMapper();

		String jsonString = "[{test: 'test'}]";
		JSONArray jarr = new JSONArray();
		try {
			jsonString = mapper.writeValueAsString(objects);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		jarr.put(jsonString);
		System.out.println("JSON OBS ----- List Test : " + jsonString);

		return jsonString;
	}

//	public List<String> getContainerFileListWas(String url) {
//        // 헤더 생성
//        HttpHeaders headers = new HttpHeaders();
//        
//
//        HttpEntity<String> requestHttpEntity = new HttpEntity<String>(null, headers);
//        RestTemplate restTemplate = new RestTemplate();
//
//        // API 호출
//        ResponseEntity<String> response
//            = restTemplate.exchange(url, HttpMethod.GET, requestHttpEntity, String.class);
//        
//        System.out.println("response 값 : " + response);
//
//        List<String> fileList = null;
//        if (response.getStatusCode() == HttpStatus.OK) {
//            // String으로 받은 목록을 배열로 변환
//        	fileList = Arrays.asList(response.getBody().split("\\r?\\n"));
//        }
//        System.out.println("jsonFile List : " + fileList);
//
//        // 배열을 List로 변환하여 반환
//        return new ArrayList<String>(fileList);
//    }

}
