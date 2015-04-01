package com.gedeng.client.connector.params;

public class UpdateDescriptionParam extends Param {

	public UpdateDescriptionParam() {
		super(PARAM_UPDATE_DESCRIPTION);
		
	}
	
	public void setDescription(String description) {
		params[0] = description;
	}
	
	public String getDescription() {
		return (String)(params[0]);
	}

}
