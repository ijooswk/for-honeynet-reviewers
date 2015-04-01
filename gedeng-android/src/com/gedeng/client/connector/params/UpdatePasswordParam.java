package com.gedeng.client.connector.params;

import com.gedeng.client.util.Encryptor;

public class UpdatePasswordParam extends Param {

	public UpdatePasswordParam() {
		super(PARAM_UPDATE_PASSWORD);
		
	}
	
	public void setOldPassword(String password) {
		params[0] = Encryptor.encryptToSHA(password);
	}
	public void setNewPassword(String password) {
		params[1] = Encryptor.encryptToSHA(password);
	}
	
	public String getOldPassword() {
		return (String)(params[0]);
	}
	public String getNewPassword() {
		return (String)(params[1]);
	}
}
