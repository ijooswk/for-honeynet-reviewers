package com.gedeng.client.connector.params;

public class UpdateGenderParam extends Param {

	public UpdateGenderParam() {
		super(PARAM_UPDATE_GENDER);
		
	}
	
	public void setGender(int gender) {
		params[0] = gender;
	}
	
	public int getGender() {
		return (Integer)(params[0]);
	}

}
