package com.gedeng.client.connector.params;

public class GetUserByTelephoneParam extends Param {

	public GetUserByTelephoneParam() {
		super(PARAM_GET_USER_BY_TELEPHONE);
		
	}
	
	public void setTelephone(String telephone) {
		params[0] = telephone;
	}
	
	public String getTelephone() {
		return (String)(params[0]);
	}

}
