package com.ctbt.beidou.base.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class DateUtil {
	/** 默认日期格式 */
	public static final String FORMAT_DATE = "yyyy-MM-dd";

	/** 默认时间格式 */
	public static final String FORMAT_TIME = "HH:mm:ss";

	/** 默认日期时间格式 */
	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_DATE_CN = "yyyy年MM月dd日";

	public static final String FORMAT_DATE_SHORT = "yyyyMMdd";

	public DateUtil() {
	}

	public String getNowTime() {
		String timeStr = "";
		try{
			timeStr = DateFormatUtils.format(new Date(), FORMAT_DATETIME);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return timeStr;
	}

	/**
	 * 将日期转换成字符格式, 默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		String result = null;
		if(date == null){
			return result;
		}

		try{
			result = DateFormatUtils.format(date, FORMAT_DATETIME);
		}catch (Exception ex){
			ex.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 将日期转换成字符格式
	 * 
	 * @param date
	 *                java.util.Date类型
	 * @param format
	 *                如果为null或""，默认为DATE格式
	 * @return 无法成功转换则返回null
	 */
	public static String date2String(Date date, String format) {
		String result = null;
		if(date == null){
			return result;
		}

		if(StringUtils.isEmpty(format)){
			format = FORMAT_DATE;
		}else if("date".equalsIgnoreCase(format)){
			format = FORMAT_DATE;
		}else if("datetime".equalsIgnoreCase(format)){
			format = FORMAT_DATETIME;
		}

		try{
			result = DateFormatUtils.format(date, format);
		}catch (Exception ex){
			ex.printStackTrace();
		}

		return result;
	}

	/**
	 * 将字符串转换成日期格式
	 * 
	 * @param str
	 *                需要转换的字符串
	 * @param format
	 *                相应的转换格式，如果参数为Blank则表示按常规的三种格式转换
	 * @return 如果不能正常转换则返回null
	 */
	public static Date string2Date(String str, String format) {
		if(StringUtils.isEmpty(str)){
			return null;
		}
		Date result = null;
		String[] formats = null;
		if(StringUtils.isEmpty(format)){
			formats = new String[3];
			formats[0] = FORMAT_DATETIME;
			formats[1] = FORMAT_DATE;
			formats[2] = FORMAT_TIME;
		}else{
			formats = new String[4];
			formats[0] = format;
			formats[1] = FORMAT_DATETIME;
			formats[2] = FORMAT_DATE;
			formats[3] = FORMAT_TIME;
		}
		try{
			str = DateUtil.stringFormat(str);
			result = DateUtils.parseDate(str, formats);
		}catch (Exception ex){
			ex.printStackTrace();

		}

		return result;
	}
	
	/**
	 * 将一个表示时间的字符串 格式化为 标准的格式
	 * @param dateString
	 * @return
	 */
	public static String stringFormat(String dateString){
		if(dateString != null){
			Pattern p = Pattern.compile("([\\d]{4})[\\.|\\-|/|年]{0,1}([\\d]{0,2})[\\.|\\-|/|月]{0,1}([\\d]{0,2})[ |日]{0,1}([\\d]{0,2})[:]{0,1}([\\d]{0,2})[:]{0,1}([\\d]{0,2})");
			Matcher m = p.matcher(dateString);
			String year = null;
			String month = null;
			String day = null;
			String hour = null;
			String minute = null;
			String second = null;
			while (m.find()){
//				String tmp = m.group();
				year = m.group(1);
				if(m.groupCount() > 1)month = m.group(2);
				if(m.groupCount() > 2)day = m.group(3);
				if(m.groupCount() > 3)hour = m.group(4);
				if(m.groupCount() > 4)minute = m.group(5);
				if(m.groupCount() > 5)second = m.group(6);
//				System.out.println(tmp);
			}
			
			month = (month != null && month.length() > 0) ? month : "01";
			day = (day != null && day.length() > 0) ? day : "01";
			hour = (hour != null && hour.length() > 0) ? hour : "00";
			minute = (minute != null && minute.length() > 0) ? minute : "00";
			second = (second != null && second.length() > 0) ? second : "00";
			
			return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
		}
		
		return dateString;
	}

	/**
	 * 将时间戳转换成字符串格式
	 * 
	 * @param ts
	 *                时间戳
	 * @param format
	 *                日期时间格式
	 * @return 转换后的字符串
	 */
	public static String timestamp2String(Timestamp ts, String format) {
		return ts == null ? null : date2String(new Date(ts.getTime()), format);
	}

	/**
	 * 将字符串转换成时间戳格式
	 * 
	 * @param str
	 *                需要转换的字符串
	 * @param format
	 *                相应的转换格式，如果参数为Blank则表示按常规的三种格式转换
	 * @return 如果不能正常转换则返回null
	 * @throws Exception
	 */
	public static Timestamp string2Timestamp(String str, String format) {
		return string2Date(str, format) == null ? null : new Timestamp(string2Date(str, format).getTime());
	}

	/**
	 * 添加年。
	 * 
	 * @param date
	 *                日期
	 * @param num
	 *                添加的年数
	 * @return 添加后的日期
	 */
	public static Date addYears(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, num);
		return cal.getTime();
	}

	/**
	 * 添加月份。
	 * 
	 * @param date
	 *                日期
	 * @param num
	 *                添加对月数
	 * @return 添加后的日期
	 */
	public static Date addMonths(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, num);
		return cal.getTime();
	}

	/**
	 * 添加天数。
	 * 
	 * @param date
	 *                日期
	 * @param num
	 *                添加的天数
	 * @return 添加后的日期
	 */
	public static Date addDays(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, num);
		return cal.getTime();
	}

	public static Date addMinutes(Date date, int mins) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, mins);
		return cal.getTime();
	}

	/**
	 * 得到当年第一天的开始时间。
	 * 
	 * @param date
	 *                日期
	 * @return 当年第一天的开始时间
	 */
	public static Date getFirstDateOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.DAY_OF_YEAR));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 得到当月第一天的开始时间。
	 * 
	 * @param date
	 *                日期
	 * @return 当月第一天的开始时间
	 */
	public static Date getFirstDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 得到当年的最后一天最后一秒。
	 * 
	 * @param date
	 *                日期
	 * @return 当年最后一天最后一秒
	 */
	public static Date getLastDateOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 得到当月的最后一天最后一秒。
	 * 
	 * @param date
	 *                日期
	 * @return 当月最后一天最后一秒
	 */
	public static Date getLastDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static Date getDate(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

		return cal.getTime();
	}

	/**
	 * 获得指定日期的之前第几天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getPreDate(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)-days);
		return cal.getTime();
	}

	/**
	 * 获得指定日期的未来第几天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextDate(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)+days);
		return cal.getTime();
	}

	/**
	 * 获得该日期在一个星期中是第几天,星期天为1，星期一为2，以此类推
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 获得该日期在一个星期中是第几天,星期天为1，星期一为2，以此类推
	 * 返回星期的中文表示方式
	 * @param date
	 * @return
	 */
	public static String getDayOfWeekCn(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		
		return "星期"+ (week == 1 ? "天" : NumberUtil.CnNums[week-1]);
	}
	
	/**
	 * 获得该日期在这个月中是第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得该日期 是该年的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获得该日期 是该年的第几周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}

	/**
	 * 指定时间的月 有多少天
	 * @param date
	 * @return
	 */
	public static int getDayCountOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 指定时间的年 有多少天
	 * @param date
	 * @return
	 */
	public static int getDayCountOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getMaximum(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 指定时间的年 有多少周
	 * @param date
	 * @return
	 */
	public static int getWeekCountOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getMaximum(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获得该日期的号
	 * 
	 * @param date
	 * @return
	 */
	public static int getDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}

	/**
	 * 根据毫秒值，换算成日期Date
	 * @param timeMillis
	 * @return Date
	 */
	public static Date getDateFromTimeMillis(long timeMillis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeMillis);
		return cal.getTime();
	}

	/**
	 * 获得该天的最早时刻
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayFirstTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得该天最晚的时刻
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayLastTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	public static int getDiffAbsDays(Date d1, Date d2) {
		return (int) Math.abs((d1.getTime() - d2.getTime()) / 1000 / 60 / 60 / 24);
	}

	public static int getDiffDays(Date d1, Date d2) {
		return (int) (d1.getTime() - d2.getTime()) / 1000 / 60 / 60 / 24;
	}

	public static int getDiffSeconds(String startTime, String endTime) {
		Date d1 = string2Date(startTime, "HH:mm:ss");
		Date d2 = string2Date(endTime, "HH:mm:ss");
		return (int) Math.abs((d1.getTime() - d2.getTime()) / 1000);
	}

	public static int getDiffHours(Date d1, Date d2) {
		return (int) Math.abs((d1.getTime() - d2.getTime()) / 1000 / 60 / 60);
	}

	/**
	 * 将日期转换成大写的中文日期
	 * 
	 * @param date
	 *                java.util.Date类型
	 * @return 无法成功转换则返回null
	 */
	public static String date2Cn(Date date) {
		String result = null;
		String sDate = "";
		if(date == null){
			return result;
		}
		try{
			sDate = DateFormatUtils.format(date, FORMAT_DATE_CN);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		String s = sDate.substring(0, 5);
		String s2 = sDate.substring(5, sDate.length());
		String[] a = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] b = { "○", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String[] c = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
						"30", "31" };
		String[] d = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九",
						"三十", "三十一" };

		for(int i = 0; i < a.length; i++){
			for(int j = 0; j < s.length() - 1; j++){
				if(s.substring(j, j + 1).equals(a[i])) s = s.substring(0, j) + b[i] + s.substring(j + 1, s.length());
			}
		}
		for(int i = 0; i < c.length; i++){
			for(int j = 0; j < s2.length() - 2; j++){
				if(s2.substring(j, j + 2).equals(c[i])) s2 = s2.substring(0, j) + d[i] + s2.substring(j + 2, s2.length());
			}
		}
		result = s + s2;
		return result;
	}

	public static int getDiffMonths(Date sd, Date ed) {

		if(sd == null || ed == null) return 0;

		if(sd.compareTo(ed) > 0){
			Date t = sd;
			sd = ed;
			ed = t;
		}

		int sy = Integer.parseInt(date2String(sd, "yyyy"));
		int sm = Integer.parseInt(date2String(sd, "M"));
		int ey = Integer.parseInt(date2String(ed, "yyyy"));
		int em = Integer.parseInt(date2String(ed, "M"));

		if(ey == sy){
			return em - sm + 1;
		}else if((ey - sy) == 1){
			return 12 - sm + em + 1;
		}else if((ey - sy) > 1){
			return 12 - sm + (ey - sy) * 12 + em + 1;
		}

		return 0;
	}

	// public static void main(String[] args) {
	// String d = "2005-01-01";
	// java.util.Date date = DateUtil.string2Date(d, DateUtil.FORMAT_DATE);
	// //System.out.println(date);
	// }
}
