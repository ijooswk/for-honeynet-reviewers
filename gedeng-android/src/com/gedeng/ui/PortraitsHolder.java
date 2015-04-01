/**
 * @author William
 * @date 2014-07-27
 * 
 * 对用户的头像缓存
 * 
 */

package com.gedeng.ui;

import android.graphics.Bitmap;
import android.util.SparseArray;


public class PortraitsHolder {
	private SparseArray<Bitmap> entries;
	private static PortraitsHolder instance = new PortraitsHolder();
	
	private PortraitsHolder() {
		entries = new SparseArray<Bitmap>();
	}
	
	public static PortraitsHolder getInstance() {
		return instance;
	}
	
	public void addEntry(int uid, Bitmap portrait) {
		entries.put(uid, portrait);
	}
	
	public Bitmap getPortrait(int uid) {
		return entries.get(uid);
	}
}
