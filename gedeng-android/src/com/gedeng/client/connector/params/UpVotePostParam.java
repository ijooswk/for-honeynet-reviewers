package com.gedeng.client.connector.params;

public class UpVotePostParam extends Param {

	public UpVotePostParam() {
		super(PARAM_UP_VOTE_POST);
	}

	public void setId(int id) {
		params[0] = id;
	}
	
	public int getId() { 
		return (Integer)(params[0]);
	}
	
}
