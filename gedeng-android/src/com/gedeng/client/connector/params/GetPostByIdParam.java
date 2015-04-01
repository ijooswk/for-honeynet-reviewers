package com.gedeng.client.connector.params;

public class GetPostByIdParam extends Param {

	public GetPostByIdParam() {
		super(PARAM_GET_POST_BY_ID);
	}
	
	public void setId(int id) {
		params[0] = id;
	}
	public int getId() {
		return (Integer)(params[0]);
	}

}
