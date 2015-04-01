package mymock;

import android.content.SharedPreferences;
import com.google.android.collect.Maps;


import java.util.Hashtable;
import java.util.Iterator;

import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;


public class MockSharedPreferencesImpl implements SharedPreferences {
	Hashtable<String, String> preference = null;
	private final WeakHashMap<OnSharedPreferenceChangeListener, Object> mListeners;
	private static final Object mContent = new Object();
	
	public MockSharedPreferencesImpl()
	{
	//	preferences = new Hashtable<String, Hashtable<String, String>>();
		preference = new Hashtable<String, String>();
		mListeners = new WeakHashMap<OnSharedPreferenceChangeListener, Object>();
	}

	public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
		synchronized(this) {
		mListeners.put(listener, mContent);
		}
	}

	public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
		synchronized(this) {
		mListeners.remove(listener);
		}
	}

	public Map<String, ?> getAll() {
		return new HashMap<String, Object>();
	}

	public String getString(String key, String defValue) {
		String str = (String)(preference.get(key));
		return str;
	}

	public void put(String key, String value)
	{
		preference.put(key, value);
	}    
	
	public int getInt(String key, int defValue) {
		return defValue;
	}    
	
	public long getLong(String key, long defValue) {
		return 1;
	}    
	
	public float getFloat(String key, float defValue) {
		return defValue;
	}    
	
	public boolean getBoolean(String key, boolean defValue) {
		return defValue;
	}    	

	public boolean contains(String key) {
		return false;
	}

	public Editor edit() {
		return new EditorImpl();
	}

	 public class EditorImpl implements Editor {
            private final Map<String, Object> mModified = Maps.newHashMap();
            private boolean mClear = false;

            public Editor putString(String key, String value) {
                synchronized (this) {
                    mModified.put(key, value);
                    return this;
                }    
            }    
            public Editor putInt(String key, int value) {
                synchronized (this) {
                    mModified.put(key, value);
                    return this;
                }
            }
            public Editor putLong(String key, long value) {
                synchronized (this) {
                    mModified.put(key, value);
                    return this;
                }
            }
            public Editor putFloat(String key, float value) {
                synchronized (this) {
                    mModified.put(key, value);
                    return this;
                }
            }
            public Editor putBoolean(String key, boolean value) {
                synchronized (this) {
                    mModified.put(key, value);
                    return this;
                }
            }
			public void apply() {
			}
			public boolean commit() {
				return false;
			}
		 public Editor remove(String key) {
                    return this;
            }

            public Editor clear() {
                    return this;
            }


	}
}

