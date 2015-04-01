package mymock;


import android.database.sqlite.*;

import com.google.android.collect.Maps;

import android.app.ActivityThread;
import android.app.AppGlobals;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDebug.DbStats;
import android.os.Debug;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Config;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;

import dalvik.system.BlockGuard;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public class MockSQLiteDatabase extends SQLiteDatabase {
    private static final String TAG = "Database";
	private int mVersion = 0;
	private boolean mInTrans = false;
	private String [] columnNames;
	private ArrayList<Object []> mTable;

	@Override public void beginTransaction() {mInTrans = true;}
	@Override public void endTransaction() {mInTrans = false;}
	@Override public boolean inTransaction() {return mInTrans;}
	@Override public void setTransactionSuccessful () {}
	@Override public boolean isOpen() {return true;}
	@Override public boolean isReadOnly() {return false;}
	@Override public void close() {}
	@Override public int getVersion () {return mVersion;}
	@Override public void setVersion (int version) {mVersion = version;}

	public MockSQLiteDatabase ()
	{
		super(1);

        //@yqq
        //debug
        //System.out.println("inside MockSQLiteDatabase");
        //#yqq
        
		//TODO: I'm using fixed table.
		//should parse "create table ..." in execSQL().
		columnNames = new String [] {"theid", "thename"};
		mTable = new ArrayList<Object []>();
	}

	@Override
	public Cursor query(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy, String limit)
	{
		// we simply return the whole database
		MatrixCursor cursor = new MatrixCursor(columnNames);
		for (Object [] row : mTable) {
			cursor.addRow(row);
		}
		return cursor;
	}

    //@yqq
    //implement another Overrided query method
    @Override
    public Cursor query(String table, String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having, 
            String orderBy)
    {
        //System.out.println("inside another query function");
        //do the same thing as the other overrided query method
        //System.out.println("MockSQLiteDatabase::query()");
        String[] cName = null;
        if (mTable.size() != 0)
        {
            int mTable_size = mTable.size();
            cName = new String[mTable_size + 1];
            int i = 0;
            cName[0] = "_id"; // this is for testing app RSSReader. Need to written into generic logic
            for (Object[] tuple : mTable)
            {
                ++i;
                cName[i] = tuple[0].toString();
                //System.out.println("cName[" + i + "] = " + cName[i]);
            }
        }
		MatrixCursor cursor = new MatrixCursor(cName);
        Object[] row_to_insert = new Object[mTable.size() + 1];
        int i = 0;
        row_to_insert[0] = 0;
		for (Object[] row : mTable) 
        {
            ++i;
            row_to_insert[i] = row[1].toString();
            //System.out.println("row_to_insert[" + i + "] = " + row_to_insert[i]);
		}
        cursor.addRow(row_to_insert);
        //System.out.println(cursor == null ? "cursor null" : "cusor not null");
		return cursor;
    }
    //#yqq

	@Override
	public int delete(String table, String whereClause, String[] whereArgs)
	{
		//@wujl added
		return 1;
		//#wujl
		//@wujl commented
		//throw new UnsupportedOperationException("no delete");
		//#wujl
	}

	@Override
	public long insert(String table, String nullColumnHack, ContentValues values)
	{
        //@yqq
        //debug
        //System.out.println("MockSQLiteDatabase::insert()");
        //#yqq
        //@original
		//assert(values.size() == 2);
        //#original
        //@yqq
        //debug
        //System.out.println("assert succeeded");
        //System.out.println(values.size());
        //#yqq
        
        //@original
		//Object [] row = new Object [2];
		//row[0] = values.get("theid");
		//row[1] = values.get("thename");
		//mTable.add(row);
        //#original
        //@yqq
        //debug
        //System.out.println("values added");
        //Integer i = (Integer)values.get("theid");
        //System.out.println(i == null ? "i null" : "i not null");
        //System.out.println("getted theid");
        //Set<Map.Entry<String, Object>> v_set = values.valueSet();
        //Iterator<Map.Entry<String, Object>> it = v_set.iterator();
        //System.out.println(it.hasNext() == true ? "true" : "false");
        //while (it.hasNext())
        //{
        //    Map.Entry<String, Object> me = it.next();
        //    System.out.println(me.getKey());
        //}
        //#yqq
        //@yqq
        //fix insert error
        //@original
		//return ((Integer)values.get("theid")).longValue();
        //#original
        //Set<Map.Entry<String, Object>> v_set = values.valueSet();
        //Iterator<Map.Entry<String, Object>> it = v_set.iterator();
        //Map.Entry<String, Object> me = it.;
        //if (it.hasNext())
        //{
        //    me = it.next();
        //}
        //String key = me.getKey();
        //System.out.println(key);
        //System.out.println(values.getAsLong(key));
        //return values.getAsLong(key);
        //return (long)0;
        Set<Map.Entry<String, Object>> values_set = values.valueSet();
        Iterator<Map.Entry<String, Object>> it = values_set.iterator();
        Map.Entry<String, Object> map_entry = null;
        //System.out.println("values size " + values.size());
        Object[] row = null;
        int i = 0;
        while (it.hasNext())
        {
            row = new Object[2];
            map_entry = it.next();
            row[0] = map_entry.getKey();
            //System.out.println("row[0] = " + row[0].toString());
            row[1] = values.get(map_entry.getKey());
            mTable.add(row);
            i++;
        }
        //System.out.println("i size " + i);
        //System.out.println("mTable size " + mTable.size());
        return (long)mTable.size();
        //#yqq
	}

	@Override
	public int update(String table, ContentValues values, String whereClause, String[] whereArgs)
	{
		throw new UnsupportedOperationException("no delete");
	}

	@Override
	public void execSQL(String sql) throws SQLException
	{
		Log.w(TAG, "execSQL(\"" + sql + "\")");
	}
	
	public SQLiteStatement compileStatement(String sql) throws SQLException {
		return new MockSQLiteStatement(this);
	}
}
