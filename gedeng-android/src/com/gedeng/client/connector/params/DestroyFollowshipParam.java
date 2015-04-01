package com.gedeng.client.connector.params;

public class DestroyFollowshipParam extends Param {

	public DestroyFollowshipParam() {
		super(PARAM_DESTROY_FOLLOWSHIP);
		
	}

	public void setId(int id) {
		params[0] = id;
	}
	
	public int getId() {
		return (Integer)(params[0]);
	}
	
}
