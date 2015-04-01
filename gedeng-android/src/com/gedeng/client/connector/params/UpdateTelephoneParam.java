package com.gedeng.client.connector.params;

public class UpdateTelephoneParam extends Param {

	public UpdateTelephoneParam() {
		super(PARAM_UPDATE_TELEPHONE);
		
	}
	
	public void setNewTelephone(String telephone) {
		params[0] = telephone;
	}

	public String getNewTelephone() {
		return (String)(params[0]);
	}
}
