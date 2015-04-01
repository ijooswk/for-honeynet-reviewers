package com.gedeng.client.connector.params;

public class DeletePostByIdParam extends Param {

	public DeletePostByIdParam() {
		super(PARAM_DELETE_POST_BY_ID);
		
	}
	
	public void setId(int id) {
		params[0] = id;
	}
	public int getId() {
		return (Integer)(params[0]);
	}

}
