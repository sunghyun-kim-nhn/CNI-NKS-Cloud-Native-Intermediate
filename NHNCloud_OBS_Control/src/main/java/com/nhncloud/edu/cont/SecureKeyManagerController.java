package com.nhncloud.edu.cont;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONObject;

public class SecureKeyManagerController {
	private String appKey;
	private String keyId;

	public SecureKeyManagerController(String user_appkey, String skm_id) {
		this.appKey = user_appkey;
		this.keyId = skm_id;
	}

	public String getDbUrl() {
		JSONObject json_data = null;
		String url_str = String.format("https://api-keymanager.nhncloudservice.com/keymanager/v1.0/appkey/%s/secrets/%s", appKey, keyId);
		URL url;
		try {
			url = new URL(url_str);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");

			// HTTP 응답 읽기
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			json_data = new JSONObject(response.toString());
			System.out.println("test secure key : " + json_data);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String secret_value = json_data.getJSONObject("body").getString("secret");

		System.out.println("test secure key : " + secret_value); // 출력: 가져온 secret 값
		
		return secret_value;
	}

}
