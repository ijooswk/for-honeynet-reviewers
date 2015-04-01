package mymock;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MockConnectivityManager extends ConnectivityManager
{
    public MockNetworkInfo network_info;

    // This class will mockup the wifi connection status based on the constructor argument
    public MockConnectivityManager(int type, int subtype, String typeName, 
            String subtypeName, NetworkInfo.State state, boolean isAvailable,
            boolean isRoaming) 
    {
        super(null);
        network_info = new MockNetworkInfo(type, subtype, typeName, 
                subtypeName, state, isAvailable, isRoaming);
    }

    @Override
    public MockNetworkInfo getActiveNetworkInfo()
    {
        return network_info;
    }
}
