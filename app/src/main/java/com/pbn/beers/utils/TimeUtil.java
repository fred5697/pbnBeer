package com.pbn.beers.utils;

import android.annotation.SuppressLint;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class TimeUtil
{
	public static String unixTimestamp2Date(int time) {
		long t = time * 1000L;
		@SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyy/dd/MM HH:mm:ss").format(new Date(t));
		return date;
	}
	
	public static int getUnixTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}
}
