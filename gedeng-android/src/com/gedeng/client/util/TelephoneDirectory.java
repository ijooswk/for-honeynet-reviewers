package com.gedeng.client.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.gedeng.client.entity.Contact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

public class TelephoneDirectory {
	public static HashMap<String,ArrayList<String>> getAll(
			ContentResolver contentResolver) {
		HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
		Cursor cursor = contentResolver.query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			Cursor phones = contentResolver.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			ArrayList<String> detail = new ArrayList<String>();

			while (phones.moveToNext()) {

				String phoneNumber = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				detail.add(phoneNumber);
			}
			phones.close();

			map.put(name, detail);
		}
		cursor.close();
		return map;
	}
	
	public static String convert2Mobile(String telephone) {
		String result = telephone.replaceAll("-", "");
		if (result.length() == 14 && result.startsWith("+86")) {
			result = result.substring(3);
		}
		if (!(result.length() == 11 && result.startsWith("1"))) {
			result = null;
		}
		return result;
	}
	
	public static HashMap<String,ArrayList<String>> getMobileAll(ContentResolver contentResolver) {
		HashMap<String,ArrayList<String>> map = getAll(contentResolver);
		Iterator<Entry<String, ArrayList<String>>> iter=map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, ArrayList<String>> me=iter.next();
			ArrayList<String> numbersArray = me.getValue();
			Iterator<String> numbersIter = numbersArray.iterator();
			ArrayList<String> mobilesArray = new ArrayList<String>();
			while(numbersIter.hasNext()) {
				String number = convert2Mobile(numbersIter.next());
				if (number != null) {
					mobilesArray.add(number);
				}
			}
			me.setValue(mobilesArray);
		}
		return map;
	}
	
	public static Contact[] convert2Contects(HashMap<String,ArrayList<String>> map) {
		if (map == null) {
			return null;
		}
		ArrayList<Contact> contactList = new ArrayList<Contact>();
		Iterator<Entry<String, ArrayList<String>>> iter=map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, ArrayList<String>> me=iter.next();
			ArrayList<String> numbersArray = me.getValue();
			Iterator<String> numbersIter = numbersArray.iterator();
			while(numbersIter.hasNext()) {
				Contact contact = new Contact();
				contact.setName(me.getKey());
				contact.setTelephone(numbersIter.next());
				contactList.add(contact);
			}
		}
		return (Contact[])(contactList.toArray(new Contact[contactList.size()]));
	}
}
