package mymock;

import android.graphics.Bitmap;

public class MockDummyBitmap extends Bitmap 
{
    public MockDummyBitmap() {
        // just whatever argument suitable for super class's constructor
        super(0, true, null, 0);
    }
}
