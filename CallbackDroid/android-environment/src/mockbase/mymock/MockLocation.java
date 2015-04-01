package mymock;
import android.location.Location;

public class MockLocation extends Location {

	public MockLocation(String str){
		super(str);
	}

	public double getLatitude(){
		return 0.0;
	}

	public double getLongitude(){
		return 0.1;
	}
}
