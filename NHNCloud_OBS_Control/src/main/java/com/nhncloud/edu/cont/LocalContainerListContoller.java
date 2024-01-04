package com.nhncloud.edu.cont;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.nhncloud.edu.dto.AuthDto;

public class LocalContainerListContoller {
	public List<String> getContainerList(AuthDto clad) {
        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", clad.getTokenId());

        HttpEntity<String> requestHttpEntity = new HttpEntity<String>(null, headers);
        RestTemplate restTemplate = new RestTemplate();

        // API 호출
        ResponseEntity<String> response
            = restTemplate.exchange(clad.getStorageUrl(), HttpMethod.GET, requestHttpEntity, String.class);

        List<String> containerList = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            // String으로 받은 목록을 배열로 변환
            containerList = Arrays.asList(response.getBody().split("\\r?\\n"));
        }

        // 배열을 List로 변환하여 반환
        return new ArrayList<String>(containerList);
    }

}
