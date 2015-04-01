package com.gedeng.client.connector.params;

import com.gedeng.client.util.Encryptor;

public class ResetPasswordParam extends Param {

	public ResetPasswordParam() {
		super(PARAM_RESET_PASSWORD);
		
	}
	
	public void setPassword(String password) {
		params[0] = Encryptor.encryptToSHA(password);
	}
	
	public String getPassword() {
		return (String)(params[0]);
	}

}
