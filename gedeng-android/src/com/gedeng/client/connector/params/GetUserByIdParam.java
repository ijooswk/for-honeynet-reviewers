package com.gedeng.client.connector.params;

public class GetUserByIdParam extends Param {

	public GetUserByIdParam() {
		super(PARAM_GET_USER_BY_ID);
	}
	
	public void setId(int id) {
		params[0] = id;
	}
	
	public int getId() {
		return (Integer)(params[0]);
	}

}
