package com.nhncloud.edu.dto;

import java.io.Serializable;

public class ObsValueVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5210717195051935546L;
	private String token;
	private String obs;
	private String tid;
	private String st_acc;
	private String api_id;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private String st_pwd;

	public String getApi_id() {
		return api_id;
	}

	public void setApi_id(String api_id) {
		this.api_id = api_id;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSt_acc() {
		return st_acc;
	}

	public void setSt_acc(String st_acc) {
		this.st_acc = st_acc;
	}

	public String getSt_pwd() {
		return st_pwd;
	}

	public void setSt_pwd(String st_pwd) {
		this.st_pwd = st_pwd;
	}

}
