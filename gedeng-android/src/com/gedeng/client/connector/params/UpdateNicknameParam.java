package com.gedeng.client.connector.params;

public class UpdateNicknameParam extends Param {

	public UpdateNicknameParam() {
		super(PARAM_UPDATE_NICKNAME);
		
	}
	
	public void setNickname(String nickname) {
		params[0] = nickname;
	}
	
	public String getNickname() {
		return (String)(params[0]);
	}

}
