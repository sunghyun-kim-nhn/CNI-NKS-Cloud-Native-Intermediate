package com.nhncloud.edu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocalFileListVo {
	
	//	{"hash":"38e7f56505144e1393bda7a67e481e37","last_modified":"2023-04-21T12:55:58.033600","bytes":2717890,"name":"1670288701959.jpg","content_type":"image/jpeg"}

	@JsonProperty("hash")
	private String hash;
	
	@JsonProperty("last_modified")
	private String last_modified;
	
	@JsonProperty("bytes")
	private String bytes;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("content_type")
	private String content_type;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getLast_modified() {
		return last_modified;
	}

	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
	}

	public String getBytes() {
		return bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	
	
	
	

}
