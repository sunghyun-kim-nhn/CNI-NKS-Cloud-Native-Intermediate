package com.nhncloud.edu.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.NonNull;

@Data
public class ObjectService {

	private String tokenId;
	private String storageUrl;
	private RestTemplate restTemplate;

	public ObjectService(String storageUrl, String tokenId) {
		this.setStorageUrl(storageUrl);
		this.setTokenId(tokenId);

		this.restTemplate = new RestTemplate();
	}

	private String getUrl(@NonNull String containerName, @NonNull String objectName) {
		return this.getStorageUrl() + "/" + containerName + "/" + objectName;
	}

	public void uploadObject(String containerName, String objectName, final InputStream inputStream) {
		String url = this.getUrl(containerName, objectName);

		// InputStream을 요청 본문에 추가할 수 있도록 RequestCallback 오버라이드
		final RequestCallback requestCallback = new RequestCallback() {
			public void doWithRequest(final ClientHttpRequest request) throws IOException {
				request.getHeaders().add("X-Auth-Token", tokenId);
				IOUtils.copy(inputStream, request.getBody());
			}
		};

		// 오버라이드한 RequestCallback을 사용할 수 있도록 설정
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setBufferRequestBody(false);
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpMessageConverterExtractor<String> responseExtractor = new HttpMessageConverterExtractor<String>(
				String.class, restTemplate.getMessageConverters());

		// API 호출
		restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
	}

	public void uploadManifestObject(String containerName, String objectName) {
		String url = this.getUrl(containerName, objectName);
		String manifestName = containerName + "/" + objectName + "/"; // 매니페스트 이름 생성

		// 헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", tokenId);
		headers.add("X-Object-Manifest", manifestName); // 헤더에 매니페스트 표기

		HttpEntity<String> requestHttpEntity = new HttpEntity<String>(null, headers);

		// API 호출
		this.restTemplate.exchange(url, HttpMethod.PUT, requestHttpEntity, String.class);
	}
}