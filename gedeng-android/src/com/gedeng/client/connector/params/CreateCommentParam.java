package com.gedeng.client.connector.params;

public class CreateCommentParam extends Param {

	public CreateCommentParam() {
		super(PARAM_CREATE_COMMENT);
		setTargetUserId(0);
	}
	
	public void setText(String text) {
		params[0] = text;
	}
	public void setPostId(int id) {
		params[1] = id;
	}
	public void setTargetUserId(int id) {
		params[2] = id;
	}
	
	public String getText() {
		return (String)(params[0]);
	}
	public int getPostId() {
		return (Integer)(params[1]);
	}
	public int getTargetUserId() {
		return (Integer)(params[2]);
	}

}
