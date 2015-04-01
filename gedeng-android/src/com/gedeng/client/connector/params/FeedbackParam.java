package com.gedeng.client.connector.params;

public class FeedbackParam extends Param {

	public FeedbackParam() {
		super(PARAM_FEEDBACK);
		setContact(null);
	}
	
	public void setText(String text) {
		params[0] = text;
	}
	public void setContact(String contact) {
		params[1] = contact;
	}
	
	public String getText() {
		return (String)(params[0]);
	}
	public String getContact() {
		return (String)(params[1]);
	}
	

}
