package com.gedeng.client.connector.params;

public class VerifyCaptchaParam extends Param {

	public VerifyCaptchaParam() {
		super(PARAM_VERIFY_CAPTCHA);
	}
	public void setTelephone(String telephone) {
		params[0] = telephone;
	}
	public void setCaptcha(String captcha) {
		params[1] = captcha;
	}
	public String getTelephone() {
		return (String)(params[0]);
	}
	public String getCaptcha() {
		return (String)(params[1]);
	}
}
