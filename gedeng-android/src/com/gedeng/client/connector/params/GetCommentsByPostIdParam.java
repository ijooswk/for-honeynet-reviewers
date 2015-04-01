package com.gedeng.client.connector.params;

public class GetCommentsByPostIdParam extends Param {

	public GetCommentsByPostIdParam() {
		super(PARAM_GET_COMMENTS_BY_POSTID);
		setCount(20);
		setMinId(-1);
		setMaxId(-1);
	}
	
	public void setId(int id) {
		params[0] = id;
	}
	public void setCount(int count) {
		params[1] = count;
	}
	public void setMinId(int id) {
		params[2] = id;
	}
	public void setMaxId(int id) {
		params[3] = id;
	}
	
	public int getId() {
		return (Integer)(params[0]);
	}
	public int getCount() {
		return (Integer)(params[1]);
	}
	public int getMinId() {
		return (Integer)(params[2]);
	}
	public int getMaxId() {
		return (Integer)(params[3]);
	}
	
}
