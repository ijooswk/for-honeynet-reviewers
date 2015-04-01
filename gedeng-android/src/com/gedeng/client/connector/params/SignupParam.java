package com.gedeng.client.connector.params;

import com.gedeng.client.util.Encryptor;

public class SignupParam extends Param {
	
	public SignupParam() {
		super(PARAM_SIGNUP);
	}
	public void setTelephone(String telephone) {
		params[0] = telephone;
	}
	public void setPassword(String password) {
		params[1] = Encryptor.encryptToSHA(password);
	}
	public String getTelephone() {
		return (String)(params[0]);
	}
	public String getPassword() {
		return (String)(params[1]);
	}
	
}
