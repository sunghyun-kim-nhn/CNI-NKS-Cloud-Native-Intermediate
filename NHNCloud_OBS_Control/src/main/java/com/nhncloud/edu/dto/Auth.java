package com.nhncloud.edu.dto;

public class Auth {
	private String tenantId;
	private PasswordCredentials passwordCredentials;
	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public PasswordCredentials getPasswordCredentials() {
		return passwordCredentials;
	}
	public void setPasswordCredentials(PasswordCredentials passwordCredentials) {
		this.passwordCredentials = passwordCredentials;
	}
	
	

}
