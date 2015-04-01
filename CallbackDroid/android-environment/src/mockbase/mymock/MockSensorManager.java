package mymock;

import android.os.Looper;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorListener;
import android.hardware.SensorEventListener;

import java.util.Arrays;
import java.util.List;

public class MockSensorManager extends SensorManager {

	public MockSensorManager(Looper mainLooper){
		super(0, 0);
	}
	
	public List<Sensor> getSensorList(int type) {
		Sensor sensor = new Sensor("Goldfish 3-axis Accelerometer", "The Android Open Source Project", 1, 1, (float)2.8, (float)0.48, (float)3.0, 0);
		List<Sensor> list = Arrays.asList(sensor);
		return list;
		//[{Sensor name = "Goldfish 3-axis Accelerometer", vendor ="The Android Open Source Project", version=1, type=1, maxRange = 2.8, resolution = 2.48E-4, power = 3.0, minDelay = 0}];
	}
	
	public boolean registerListener(SensorEventListener listener, Sensor sensor, int rate) {
		return true;
	}

}