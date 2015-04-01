package mymock;

import android.net.NetworkInfo;
import android.net.ConnectivityManager;

public class MockNetworkInfo extends NetworkInfo
{
    public int mNetworkType;
    public int mSubtype;
    public String mTypeName;
    public String mSubtypeName;
    public State mState;
    public boolean mIsAvailable;
    public boolean mIsRoaming;

    public MockNetworkInfo(int type, int subtype, String typeName, 
            String subtypeName, State state, boolean isAvailable,
            boolean isRoaming)
    {
        super(0);
        mNetworkType = type;
        mSubtype = subtype;
        mTypeName = typeName;
        mSubtypeName = subtypeName;
        mState = State.CONNECTED;
        mIsAvailable = false;
        mIsRoaming = false;
    }

    public void setNetworkInfo(int type, int subtype, String typeName, 
            String subtypeName, State state, boolean isAvailable,
            boolean isRoaming)
    {
        mNetworkType = type;
        mSubtype = subtype;
        mTypeName = typeName;
        mSubtypeName = subtypeName;
        mState = State.CONNECTED;
        mIsAvailable = false;
        mIsRoaming = false;
    }

    @Override
    public String getTypeName()
    {
        return mTypeName;
    }

    @Override
    public boolean isConnectedOrConnecting()
    {
        return mState == State.CONNECTED || mState == State.CONNECTING;
    }

    @Override
    public int getType()
    {
        return mNetworkType;
    }
}
