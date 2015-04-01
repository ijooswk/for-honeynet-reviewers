package mymock;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.ILocationManager;

public class MockLocationManager extends LocationManager{
	
	public static LocationListener locDrv; 

	public MockLocationManager(ILocationManager ilm){
		super(ilm);
	}

	public void requestLocationUpdates(String provider, long minTime, float minDistance, LocationListener listener){
	/*	listener.onStatusChanged(provider, 0, null);
		listener.onProviderEnabled(provider);
		listener.onProviderDisabled(provider);
		listener.onLocationChanged(new MockLocation(""));
	*/
		locDrv = listener;
	}
}
