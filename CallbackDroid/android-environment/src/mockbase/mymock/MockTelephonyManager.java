package mymock;

import android.telephony.TelephonyManager;
import android.content.Context;

public class MockTelephonyManager extends TelephonyManager {
	public MockTelephonyManager(Context context) {
		//super(context);
	}

	public String getDeviceId() {
		//return "the_device_id";
		return "123456789012345";
	}

	public String getSimSerialNumber() {
		return "the_sim_serial_num";
	}

	public String getLine1Number() {
		return "the_line_1_number";
	}

	public String getSubscriberId(){
		return "the_subscribeID";
	}
	
	public String getDeviceSoftwareVersion(){
		return "device_software_version";
	}
	
	public int getNetworkType(){
		return NETWORK_TYPE_CDMA;
	}
	
	public int getPhoneType(){
		return PHONE_TYPE_CDMA;
	}
	
	public String getVoiceMailNumber(){
		return "voice_mail_NO";
	}
}
