package com.gedeng.client.connector.params;

public class GetPostsParam extends Param {

	public GetPostsParam() {
		super(PARAM_GET_POSTS);
		setCount(20);
		setMinId(-1);
		setMaxId(-1);
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
