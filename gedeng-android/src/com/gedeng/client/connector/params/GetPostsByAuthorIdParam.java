package com.gedeng.client.connector.params;

public class GetPostsByAuthorIdParam extends Param {

	public GetPostsByAuthorIdParam() {
		super(PARAM_GET_POSTS_BY_AUTHORID);
		setMinId(-1);
		setMaxId(-1);
	}
	
	public void setId(int id) {
		params[0] = id;
	}
	public void setCount(int count) {
		params[1] = count;
	}
	public void setMinId(int minId) {
		params[2] = minId;
	}
	public void setMaxId(int maxId) {
		params[3] = maxId;
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
