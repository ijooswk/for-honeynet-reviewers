package com.gedeng.client.connector.params;

import com.gedeng.client.util.Encryptor;


public class LoginParam extends Param {

	public LoginParam() {
		super(PARAM_LOGIN);
	}
	
	public void setUsername(String username) {
		params[0] = username;
	}
	
	public void setPassword(String password) {
		if (password.length() < 20) {
			params[1] = Encryptor.encryptToSHA(password);
		}
		else
			params[1] = password;
	}
	
	public String getUsername() {
		return (String)(params[0]);
	}
	
	public String getPassword() {
		return (String)(params[1]);
	}
	
}
