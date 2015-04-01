package com.gedeng.client.util;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;


public class Time {
	protected static DateTime Iso2DateTime(String isoStr) {
		DateTime datetime = new DateTime(isoStr);
		return datetime;
	}
	public static String getTimeSpanString(DateTime time) {
		DateTime now = DateTime.now();
		String result = "未知";
		if (time.isAfter(now)) {
			result = "刚刚";
		}
		else
		{
			Interval interval = new Interval(time,now);
			Period period = new Period(interval);
			int month = period.getMonths();
			int day = period.getDays();
			int hour = period.getHours();
			int minute = period.getMinutes();
			
			if (month > 0) {
				result = time.toString("MM-dd HH:mm");
			} else if (now.dayOfYear().get() == time.dayOfYear().get() + 2) {
				result = "前天 " + time.toString("HH:mm");
			} else if (now.dayOfYear().get() == time.dayOfYear().get() + 1) {
				result = "昨天 " + time.toString("HH:mm");
			} else if (day > 0) {
				result = time.toString("MM-dd HH:mm");
			} else if (hour > 2) {
				result = "今天 " + time.toString("HH:mm");
			} else if (hour > 0) {
				result = hour + "小时前";
			} else if (minute > 1) {
				result = minute + "分钟前";
			} else {
				result = "刚刚";
			}
		}
		return result;
	}
}
