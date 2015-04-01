package com.gedeng.client.connector.params;

public class SendCaptchaParam extends Param {

	public SendCaptchaParam() {
		super(PARAM_SEND_CAPTCHA);
	}
	public void setTelephone(String telephone) {
		params[0] = telephone;
	}
	public String getTelephone() {
		return (String)(params[0]);
	}

}
