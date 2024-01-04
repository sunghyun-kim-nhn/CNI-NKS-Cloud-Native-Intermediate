package com.nhncloud.edu.cont;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.nhncloud.edu.dto.AuthDto;

public class ObjectFileListController {
	private String tokenId;
	private String storageUrl;

	private List<String> getObjectList(String containerName) {
		// 헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", tokenId);
		//HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<String> requestHttpEntity = new HttpEntity<String>(null, headers);
		System.out.println("st_URL : " + storageUrl);
		System.out.println("c_name : " + containerName);
		System.out.println("token : " + tokenId);
		System.out.println("header :" + headers);

		// API 호출
		ResponseEntity<String> response = new RestTemplate().exchange(storageUrl + "/" + containerName, HttpMethod.GET,
				requestHttpEntity, String.class);
		ResponseEntity<String> response1 = new RestTemplate().exchange(storageUrl + "/" + containerName + "/", HttpMethod.POST,
				requestHttpEntity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			// String으로 받은 목록을 배열로 변환
			System.out.println("Test:" + response.getBody());
			System.out.println("Test11:" + response1.getBody());
			return Arrays.asList(response.getBody().split("\\r?\\n"));
		}

		return Collections.emptyList();

	}

	public List<String> getObjectFileList(AuthDto ad, String containerName) {
		this.tokenId = ad.getTokenId();
		this.storageUrl = ad.getStorageUrl();		
		return getObjectList(containerName);
	}
	
	public List<String> getObjectStorageFileList(String containerName, String token, String obs) {
		this.tokenId = token;
		this.storageUrl = obs;		
		return getObjectList(containerName);
	}
}
