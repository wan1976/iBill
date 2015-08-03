package net.toeach.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类<br/>
 * net.toeach.common.utils.DateUtil
 * @author 万云  <br/>
 * @version 1.0
 * @date 2015-3-5 下午3:03:08
 */
public class DateUtil {
	// 日期格式
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	// 时间格式
	private static final String TIME_PATTERN = "HH:mm:ss";
	// 日期+时间格式
	private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 格式化Date   格式：yyyy-MM-dd
     * @param date 指定格式日期
     * @return  指定格式日期
     */
    public static String formatDate(Date date) throws Exception{
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.CHINA);
    	return sdf.format(date);
    }
    
    /**
     * 格式化Date   格式：HH:mm:ss
     * @param date 指定格式时间
     * @return  指定格式时间
     */
    public static String formatTime(Date date) throws Exception {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN, Locale.CHINA);
    	return sdf.format(date);
    }
    
    /**
     * 格式化Date   格式：yyyy-MM-dd HH:mm:ss
     * @param date 指定格式格式日期
     * @return  指定格式格式日期
     */
    public static String formatDateTime(Date date) throws Exception {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_PATTERN, Locale.CHINA);
    	return sdf.format(date);
    }
    
    /**
	 * 以指定格式，格式化指定时间
	 * @param date 指定时间
	 * @param pattern  指定格式
	 * @return 格式化后的日期
	 */
	public static String formatPatternDate(Date date, String pattern) throws Exception {
		if (date == null || pattern == null) {
    		return null;
    	}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		return sdf.format(date);
	}
	
	/**
	 * 比较两个日期大小，返回1则表示date1在date2前，返回-1则表示date1在date2后面，返回0表示相等。<br/>
	 * @param date1 日期1
	 * @param date2 日期2
	 * @return 比较结果
	 */
	public static int compareDate(Date date1, Date date2) {
		if (date1.getTime() > date2.getTime()) {// daet1 在date2前
			return 1;
		} else if (date1.getTime() < date2.getTime()) {// date1在date2后
			return -1;
		} else {
			return 0;
		}
	}
	
	/**
	 * 把字符对象转为日期对象 <br/>
	 * @param s 日期字符串
	 * @return 日期对象
	 * @throws Exception 异常
	 */
	public static Date parseDate(String s) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.CHINA);
		return sdf.parse(s);
	}
	
	/**
	 * 按不同的格式把字符对象转为日期对象
	 * @param s 日期字符串
	 * @param pattern 格式
	 * @return 日期对象
	 * @throws Exception 异常
	 */
	public static Date parseDate(String s, String pattern) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		return sdf.parse(s);
	}
}
