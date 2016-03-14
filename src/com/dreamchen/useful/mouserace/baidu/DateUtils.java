package com.dreamchen.useful.mouserace.baidu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import topevery.framework.system.DateTime;
import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class DateUtils
{
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String toString(DateTime dateTime)
	{
		Date date = dateTime.toJavaDate();
		String str = sdf.format(date);
		return str;
	}

	public static String toString(Calendar calendar)
	{
		Date date = calendar.getTime();
		String str = sdf.format(date);
		return str;
	}

	public static String toString(Date date)
	{
		String str = sdf.format(date);
		return str;
	}

	public static Calendar toCalendar(String str)
	{
		Date date = toDate(str);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Date toDate(String str)
	{
		Date date = null;
		try
		{
			date = sdf.parse(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (date == null)
			{
				date = new Date();
			}
		}
		return date;
	}
}
