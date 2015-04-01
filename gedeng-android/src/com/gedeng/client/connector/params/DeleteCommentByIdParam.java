package com.gedeng.client.connector.params;

public class DeleteCommentByIdParam extends Param {

	public DeleteCommentByIdParam() {
		super(PARAM_DELETE_COMMENT_BY_ID);

	}
	
	public void setId(int id) {
		params[0] = id;
	}
	public int getId() {
		return (Integer)(params[0]);
	}

}
