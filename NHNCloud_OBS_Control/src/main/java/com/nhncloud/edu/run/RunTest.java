package com.nhncloud.edu.run;

import java.util.List;

import org.json.JSONObject;

import com.nhncloud.edu.cont.ObjectContainerListContoller;
import com.nhncloud.edu.cont.ObjectFileListController;
import com.nhncloud.edu.cont.TokenController;
import com.nhncloud.edu.dto.AuthDto;

public class RunTest {

	public static void main(String[] args) {

		// 향후 Properties 항목이나 Web 상에서 Input 값으로 대체하여 사용
		// TokenID 값은 Cookie나 Session 활용 가능 여부 확인 필요
		//final String authUrl = "https://api-identity-infrastructure.nhncloudservice.com/v2.0";
		final String tenantId = "4a012981ac48456c94b54f992277d465";
		final String username = "nhn_cl_edu@nhn.com";
		final String password = "nHnCloudEdu!23";
		final String storageUrl = "https://kr1-api-object-storage.nhncloudservice.com/v1/AUTH_4a012981ac48456c94b54f992277d465";

		// Step 1. Get Token ID
		// com.nhncloud.edu.cont.TokenController, com.nhncloud.edu.dto.TokenRequest 사용
		String tokenBody = null;

		TokenController rnc = new TokenController(tenantId, username, password);
		tokenBody = rnc.requestToken();

		System.out.println("Test token Body 값 : " + tokenBody);

		JSONObject jsonString = new JSONObject(tokenBody);
		String tokenKeyID = jsonString.getJSONObject("access").getJSONObject("token").getString("id");
		//
		// System.out.println(token.getJSONArray(0).getString(0));
		// JSONArray id = token.getJSONArray("id");

		System.out.println("Token ID 값 :" + tokenKeyID);

		// Step 1-2. Token ID기반 Auth와 Object Storage Url 세팅
		AuthDto clad = new AuthDto();
		clad.setStorageUrl(storageUrl);
		clad.setTokenId(tokenKeyID);

		// Step 2. 컨테이너 목록 조회 -> 사전 TokenID 값 가져오는 기능 먼저 진행 필요
		// com.nhncloud.edu.cont.ObjectContainerListContoller,
		// com.nhncloud.edu.dto.AuthDto 사용

		ObjectContainerListContoller oclc = new ObjectContainerListContoller();
		List<String> containerList = oclc.getContainerList(clad);

		// OBS 컨테이너 이름 저장 변수
		String selectObjectContainerName = null;

		if (containerList != null) {
			for (int i = 0; i < containerList.size(); i++) {
				System.out.println("Container List " + (i + 1) + " Name : " + containerList.get(i));
				selectObjectContainerName = containerList.get(i);
			}
		}

		// Step 3. 컨테이너 내 파일 조회
		// com.nhncloud.edu.cont.ObjectFileListController, com.nhncloud.edu.dto.AuthDto
		// 사용
		// 사전 Step 2. selectObjectContainerName 저장된 Container 이름과 tokenId, Storage URL
		// 값인 Auth = clad 필요

		ObjectFileListController oflc = new ObjectFileListController();
		List<String> objectFileList = oflc.getObjectFileList(clad, selectObjectContainerName);

		if (objectFileList != null) {
			for (int i = 0; i < objectFileList.size(); i++) {
				System.out.println("Container [" + selectObjectContainerName + "] File List no." + (i + 1) + " Name : "
						+ storageUrl + "/" + selectObjectContainerName + "/" + objectFileList.get(i));

			}
		}

	}

}
