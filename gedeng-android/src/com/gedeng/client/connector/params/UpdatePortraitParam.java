package com.gedeng.client.connector.params;

import android.graphics.Bitmap;

public class UpdatePortraitParam extends Param {

	public UpdatePortraitParam() {
		super(PARAM_UPDATE_PORTRAIT);
		
	}
	
	public void setPortrait(Bitmap portrait) {
		params[0] = portrait;
	}
	
	public Bitmap getPortrait() {
		return (Bitmap)(params[0]);
	}

}
