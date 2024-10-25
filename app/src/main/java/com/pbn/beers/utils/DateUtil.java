package com.pbn.beers.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{
	/**
	 * 获取当前日期
	 *
	 * @return
	 */
	public static String getCurrentDate() {
		@SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(new Date());
	}
	
	public static String formatDateTime(String time, boolean haveYear) {
		@SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(time == null) {
			return "";
		}
		Date date;
		try {
			date = format.parse(time);
		}
		catch(ParseException e) {
			e.printStackTrace();
			return "";
		}
		
		Calendar current = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		Calendar yesterday = Calendar.getInstance();
		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		yesterday.set(Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		
		current.setTime(date);
		if(haveYear) {
			int index = time.indexOf(" ");
			return time.substring(0, index);
		}
		else {
			int yearIndex = time.indexOf("-") + 1;
			int index = time.indexOf(" ");
			return time.substring(yearIndex, time.length()).substring(0, index);
		}
	}
	
}
