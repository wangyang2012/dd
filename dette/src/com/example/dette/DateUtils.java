package com.example.dette;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat dureeFormat = new SimpleDateFormat("HH");
	
	public static String dateToString(Date date) {
		return dateFormat.format(date);
	}
	
	public static Date stringToDate(String date) {
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	public static String heureToString(Date heure) {
		return heureFormat.format(heure);
	}
	
	public static Date stringToHeure(String heure) {
		try {
			return heureFormat.parse(heure);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	public static Integer minuteToInteger(String time) {
		Date datetime = DateUtils.stringToHeure(time);
		Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
		calendar.setTime(datetime); 
		return calendar.get(Calendar.MINUTE);
	}
	

	public static String formatHeure(String str) {
		if (str == null || str.isEmpty()) {
			return "O";
		}
		
		Date min = null;
		if (str.contains(":")) {
			try {
				min = heureFormat.parse(str);
			} catch (ParseException e) {
				return "0";
			}
		} else {
			try {
				min = dureeFormat.parse(str);
			} catch (ParseException e) {
				return "0";
			}
		}
		return dureeFormat.format(min);
	}
}
