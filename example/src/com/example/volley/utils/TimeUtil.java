package com.example.volley.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.text.format.DateFormat;

public class TimeUtil {
	public static final String ACTIVITY_START_TIME = "T23:59:59.999Z";
	public static final String ACTIVITY_DEADLINE = "T23:59:59.998Z";

	/*
	 * GMT time <---> Local time
	 */
	public static Date localToGMT(String from, boolean isGMT) {
		from = from.replace("T", " ");
		from = from.replace("Z", "");
		String to = "";
		SimpleDateFormat simpleLocal = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		Calendar nowCal = Calendar.getInstance();
		TimeZone localZone = nowCal.getTimeZone();
		simpleLocal.setTimeZone(localZone);

		SimpleDateFormat simpleGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simpleGMT.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date fromDate = new Date();
		try {
			if (isGMT) {
				fromDate = simpleLocal.parse(from);
				to = simpleGMT.format(fromDate);
			} else {
				fromDate = simpleGMT.parse(from);
				to = simpleLocal.format(fromDate);
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Date toDate = new Date();
		try {
			if (isGMT) {
				toDate = simpleGMT.parse(to);
			} else {
				toDate = simpleLocal.parse(to);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return toDate;
	}

	public static String GetUTCTime() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return (String) DateFormat.format("yyyy'-'MM'-'dd'T'kk':'mm':'ss'Z'",
				cal);
	}

	public static String getDateFromTime(String time) {
		String[] strs = time.split("T");
		if (strs != null && strs.length > 0)
			return strs[0];

		return "";
	}

	public static String getShowTime(Date date) {
		String shortstring = null;
		Calendar nowCalendar = Calendar.getInstance();
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		long now = nowCalendar.getTimeInMillis();

		int dataDay = dateCalendar.get(Calendar.DATE);
		int nowDay = nowCalendar.get(Calendar.DATE);
		if (date == null)
			return shortstring;
		long deltime = (now - date.getTime()) / 1000;
		if (deltime > 365 * 24 * 60 * 60 || dataDay == nowDay) {
			return (String) DateFormat.format("kk':'mm", date);
		} else if (nowDay - dataDay == 1) {
			return "昨天" + (String) DateFormat.format(" kk':'mm", date);
		} else {
			return (String) DateFormat.format("yyyy'-'MM'-'dd kk':'mm", date);
		}
	}

	public static String getShortTime2(Long date) {
		String shortstring = null;
		long now = new Date().getTime();
		String ntime = Long.toString(date);
		ntime = ntime + "000";
		date = Long.valueOf(ntime);
		if (date == null)
			return shortstring;
		long deltime = (now - date) / 1000;
		if (deltime > 365 * 24 * 60 * 60) {
			shortstring = (int) (deltime / (365 * 24 * 60 * 60)) + "年前";
		} else if (deltime > 24 * 60 * 60) {
			shortstring = (int) (deltime / (24 * 60 * 60)) + "天前";
		} else if (deltime > 60 * 60) {
			shortstring = (int) (deltime / (60 * 60)) + "小时前";
		} else if (deltime > 60) {
			shortstring = (int) (deltime / (60)) + "分前";
		} else if (deltime > 1) {
			shortstring = deltime + "秒前";
		} else {
			shortstring = "1秒前";
		}
		return shortstring;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static String longDate2String(long longDate) {
		String datetime = DateFormat.format("yyyy:MM:dd kk:mm:ss",
				longDate * 1000).toString();
		return datetime;
	}

	/**
	 * 秒杀倒计时状态 0 未开始 1 已开始 -1 已结束
	 * 
	 * @param time
	 * @return
	 */
	public static int checkSecKillTime(long start, long end) {
		long now = new Date().getTime();

		long start_time = start * 1000;
		long end_time = end * 1000;

		long del_start = now - start_time;
		long del_end = now - end_time;

		if (del_start <= 0) {
			return 0;
		} else if (del_end <= 0) {
			return 1;
		} else {
			return -1;
		}
	}

	public static long[] getSecKillTime(long time) {
		long[] times = new long[4];
		long now = new Date().getTime();
		long ntime = time * 1000;
		long deltime = (now - ntime) / 1000;
		if (deltime < 0) {
			deltime = (ntime - now) / 1000;
		}
		if (deltime > 0) {
			times[0] = deltime / (24 * 60 * 60);
			times[1] = (deltime % (24 * 60 * 60)) / (60 * 60);
			times[2] = (deltime % (60 * 60)) / 60;
			times[3] = deltime % 60;
		} else {
			times[0] = 0;
			times[1] = 0;
			times[2] = 0;
			times[3] = 0;
		}
		return times;
	}
}
