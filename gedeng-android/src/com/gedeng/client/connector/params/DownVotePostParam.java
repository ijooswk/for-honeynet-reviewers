package com.gedeng.client.connector.params;

public class DownVotePostParam extends Param {

	public DownVotePostParam() {
		super(PARAM_DOWN_VOTE_POST);
		
	}
	
	public void setId(int id) {
		params[0] = id;
	}
	
	public int getId() { 
		return (Integer)(params[0]);
	}

}
