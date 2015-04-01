package com.gedeng.client.connector.params;

public class GetImageByUrlParam extends Param {

	public GetImageByUrlParam() {
		super(PARAM_GET_IMAGE_BY_URL);

	}
	
	public void setUrl(String url) {
		params[0] = url;
	}
	
	public String getUrl() {
		return (String)(params[0]);
	}

}
