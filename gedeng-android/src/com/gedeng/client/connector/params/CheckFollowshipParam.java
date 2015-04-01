package com.gedeng.client.connector.params;

public class CheckFollowshipParam extends Param {

	public CheckFollowshipParam() {
		super(PARAM_CHECK_FOLLOWSHIP);

	}
	
	public void setId(int id) {
		params[0] = id;
	}
	
	public int getId() {
		return (Integer)(params[0]);
	}


}
