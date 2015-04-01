package mymock.mockView;

import android.widget.ListView;
import android.content.Context;

public class MockListView extends ListView
{
    public String id;
    
    public MockListView(String id, Context context)
    {
        super(context);
        this.id = id;
    }

    public MockListView(Context context)
    {
        super(context);
    }

    @Override
    public String toString()
    {
        return this.getClass().getName() + ": " + id;
    }
}
