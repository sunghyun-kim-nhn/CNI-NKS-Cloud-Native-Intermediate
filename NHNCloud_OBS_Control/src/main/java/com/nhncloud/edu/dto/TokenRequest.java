package com.nhncloud.edu.dto;


import lombok.Data;

@Data
public class TokenRequest {

    private Auth auth = new Auth();

    public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	@Data
    static public class Auth {
        private String tenantId;
        private PasswordCredentials passwordCredentials = new PasswordCredentials();
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

    @Data
    static public class PasswordCredentials {
        private String username;
        private String password;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
        
        
    }
}
