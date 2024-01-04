package com.nhncloud.edu.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ObsFileVo implements Serializable {

	private static final long serialVersionUID = 2529304179233410663L;
	@JsonProperty("token")
	private String token;
	@JsonProperty("obs")
	private String obs;
	@JsonProperty("c_name")
	private String c_name;
	@JsonProperty("file")
	private MultipartFile file;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
