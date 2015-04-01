package com.gedeng.client.connector.params;

public class CreateFollowshipParam extends Param {

	public CreateFollowshipParam() {
		super(PARAM_CREATE_FOLLOWSHIP);
		
	}
	
	public void setId(int id) {
		params[0] = id;
	}
	
	public int getId() {
		return (Integer)(params[0]);
	}

}
