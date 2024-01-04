package com.nhncloud.edu.cont;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.nhncloud.edu.dto.TokenRequest;

public class TokenController {
	private TokenRequest tokenRequest;
	private RestTemplate restTemplate;
	private final String AUTHURL = "https://api-identity-infrastructure.nhncloudservice.com/v2.0";

	public TokenController(String tenantId, String username, String password) {
		tokenRequest = new TokenRequest();
		tokenRequest.getAuth().setTenantId(tenantId);
		tokenRequest.getAuth().getPasswordCredentials().setUsername(username);
		tokenRequest.getAuth().getPasswordCredentials().setPassword(password);
	}

	// 해더 생성 및 토큰 요청 method
	public String requestToken() {

		restTemplate = new RestTemplate();
		String identityUrl = AUTHURL + "/tokens";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<TokenRequest> httpEntity = new HttpEntity<TokenRequest>(tokenRequest, headers);

		// 토큰 요청
		ResponseEntity<String> response = restTemplate.exchange(identityUrl, HttpMethod.POST, httpEntity, String.class);
		

		return response.getBody();
	}

}
