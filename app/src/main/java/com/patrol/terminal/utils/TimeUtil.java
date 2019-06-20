package com.patrol.terminal.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dxr on 2017/6/14. 根据日期获取当前月的周区间
 */
public class TimeUtil {


    /**
	 * @param year 年
	 * @param month 月
	 * @param weeks 第几周
	 * @return 返回第几周的开始与结束日期 11112313
	 */
	public static Map<String, Object> getScopeForWeeks(int year, int month, int weeks) {
		Map<String, Object> map = new HashMap<String, Object>();
		String time = year + "-" + getMonthToString(month);
		Map<String, Object> result = getDateScope(time);
		// 获取天数和周数
		int resultDays = Integer.parseInt(result.get("days").toString());
		int resultWeeks = Integer.parseInt(result.get("weeks").toString());
		// 如果参数周数大于实际周数 则返回一个不存在的日期 默认设置为当前 天数+1
		if (weeks > resultWeeks) {
			int days = resultDays + 1;
			String beginDate = year + "-" + getMonthToString(month) + "-" + days;
			map.put("beginDate", beginDate);
			map.put("endDate", beginDate);
		} else {
			// 获取当月第一天属于周几
			Map<Integer, Object> scopes = getScope(time, resultDays, resultWeeks);
			map = (Map<String, Object>) scopes.get(weeks);
		}
		return map;
	}

	/**
	 * 获取某年某月的天数以及周数
     * @param time
     * @return
     */
	private static Map<String, Object> getDateScope(String time) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 保证日期位数
			if (time.length() <= 7) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(time + "-01");
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				// 获取天数
				int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				// 获取周数
				int weeks = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
				map.put("days", days);
				map.put("weeks", weeks);
			} else {
				throw new RuntimeException("日期格式不正确");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取本月周区间
	 * @param date
     * @param days 天数
	 * @param weeks 周数
	 * @return
	 */
	private static Map<Integer, Object> getScope(String date, int days, int weeks) {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		// 遍历周数
		int midNum = 0;
		for (int i = 1; i <= weeks; i++) {
			// 第一周
			if (i == 1) {
				String time = date + "-01";
				String week = getWeekOfDate(time);
				// 获取第一周还有多少天
				int firstDays = getSurplusDays(week);
				// 获取第一周结束日期
				int endDays = 1 + firstDays;
				String time2 = date + "-0" + endDays;
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("beginDate", time);
				data.put("endDate", time2);
				map.put(i, data);
				midNum = endDays;
			} else {
				// 当前日期数+7 比较 当月天数
				if ((midNum + 7) <= days) {
					int beginNum = midNum + 1;
					int endNum = midNum + 7;
					String time1 = date + "-" + getNum(beginNum);
					String time2 = date + "-" + getNum(endNum);
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("beginDate", time1);
					data.put("endDate", time2);
					map.put(i, data);
					midNum = endNum;
				} else {
					int beginNum = midNum + 1;
					int endNum = days;
					String time1 = date + "-" + getNum(beginNum);
					String time2 = date + "-" + getNum(endNum);
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("beginDate", time1);
					data.put("endDate", time2);
					map.put(i, data);
					midNum = endNum;
				}
			}
		}
		return map;
	}

	/**
	 * 获取日期属于周几
     * @param time
     * @return
     */
	private static String getWeekOfDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String week = "";
		try {
			Date date = sdf.parse(time);
			String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0)
				w = 0;
			week = weekDays[w];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return week;
	}

	/**
	 * 获取数字
	 * @return
	 */
	private static String getNum(int num) {
		int result = num / 10;
		if (result == 0) {
			return "0" + num;
		} else {
			return num + "";
		}
	}

	/**
	 * 返回月份的字符串
	 * 
	 * @param month
	 * @return
	 */
	private static String getMonthToString(int month) {
		String str = "";
		switch (month) {
		case 1:
			str = "01";
			break;
		case 2:
			str = "02";
			break;
		case 3:
			str = "03";
			break;
		case 4:
			str = "04";
			break;
		case 5:
			str = "05";
			break;
		case 6:
			str = "06";
			break;
		case 7:
			str = "07";
			break;
		case 8:
			str = "08";
			break;
		case 9:
			str = "09";
			break;
		case 10:
			str = "10";
			break;
		case 11:
			str = "11";
			break;
		case 12:
			str = "12";
			break;
		}
		return str;
	}

	/**
	 * 根据当前周几判断当前周还有几天
	 * 
	 * @param week
	 * @return
	 */
	private static int getSurplusDays(String week) {
		int num = 0;
		if ("星期日".equals(week)) {
			num = 0;
		} else if ("星期一".equals(week)) {
			num = 6;
		} else if ("星期二".equals(week)) {
			num = 5;
		} else if ("星期三".equals(week)) {
			num = 4;
		} else if ("星期四".equals(week)) {
			num = 3;
		} else if ("星期五".equals(week)) {
			num = 2;
		} else if ("星期六".equals(week)) {
			num = 1;
		}
		return num;
	}

	/**
	 * 根据当前日期判断在当月的第几周
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static int getWeek(String str) throws Exception {
		Locale.setDefault(Locale.CHINA);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 第几周
		int week = calendar.get(Calendar.WEEK_OF_MONTH);

		// 判断今天如果是星期天的话，那么周就需要减1
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			week = week - 1;
		} else {
		}
		// 第几天，从周日开始
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return week;
	}

	/**
	 * 去掉日期前面填充的0
	 * 
	 * @param str
	 * @return
	 */
	public static String removeZero(String str) {
		// 传进来日期
		String[] array = str.split("-");
		String getYear = array[0];
		String getMonth = array[1];
		String getDay = array[2];

		Integer year = Integer.parseInt(getYear);
		Integer month = Integer.parseInt(getMonth);
		Integer day = Integer.parseInt(getDay);

		// 拼一个年月日出来
		String getTime = year + "-" + month + "-" + day;
		return getTime;
	}

	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * @param nowDate
     * @param startDate
     * @param endDate
     * @return
	 */
	public static boolean isEffectiveDate(String nowDate, String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		Date nowTime = null;
		Date startTime = null;
		Date endTime = null;
		try {
			nowTime = sdf.parse(nowDate);
			startTime = sdf.parse(startDate);
			endTime = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
			return true;
		}
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}
	public static String dateToDate(String str) {
		// 传进来日期
		String[] array = str.split("-");
		String getYear = array[0];
		String getMonth = array[1];
		String getDay = array[2];

		Integer year = Integer.parseInt(getYear);
		Integer month = Integer.parseInt(getMonth);
		Integer day = Integer.parseInt(getDay);

		// 拼一个年月日出来
		String getTime = month + "月" + day+"日";
		return getTime;
	}
	public static String dateToDay(String str) {
		// 传进来日期
		String[] array = str.split("-");
		String getYear = array[0];
		String getMonth = array[1];
		String getDay = array[2];

		Integer year = Integer.parseInt(getYear);
		Integer month = Integer.parseInt(getMonth);
		Integer day = Integer.parseInt(getDay);

		// 拼一个年月日出来
		String getTime = day+"日";
		return getTime;
	}

	// 获取当前时间所在年的周数
	public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	// 获取当前时间所在年的最大周数
	public static int getMaxWeekNumOfYear(int year) {
        Calendar c = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	// 获取某年的第几周的开始日期
	public static String getFirstDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, (week-1) * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	// 获取第几周的起始日期
	public static String getDateOfWeek(int year, int week) {
		Calendar c = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, (week-1) * 7);

		return getDayOfWeek(cal.getTime());
	}

	// 获取某年的第几周的结束日期
	public static String getLastDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, (week-1) * 7);

		return getLastDayOfWeek(cal.getTime());
	}
	// 获取某年的第几周的结束日期
	public static String getLastDateOfWeek(int year, int week) {
		Calendar c = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, (week-1) * 7);

		return getLastDayOfWeeks(cal.getTime());
	}
	// 获取某年的第几周的所在月份
	public static String getMonthOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(cal.getTime());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		Date time = c.getTime();
		String month = DateUatil.getMonth(time);
		return month;
	}

	// 获取当前时间所在周的开始日期
	public static String getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		Date time = c.getTime();
		String date1 = DateUatil.getMonthAndDays(time);
		return date1;
	}
	// 获取当前时间所在周的开始日期
	public static String getDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		Date time = c.getTime();
		String date1 = DateUatil.getMonthAndDays(time);
		return date1;
	}
	// 获取当前时间所在周的结束日期
	public static String getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		Date time = c.getTime();
		String monthAndDay = DateUatil.getMonthAndDays(time);
		return monthAndDay;
	}
	// 获取当前时间所在周的结束日期
	public static String getLastDayOfWeeks(Date date) {
		Calendar c = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		Date time = c.getTime();
		String monthAndDay = DateUatil.getMonthAndDays(time);
		return monthAndDay;
	}

	public static int getCurrWeek(){
		Calendar cal = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
		cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
		cal.setTime(new Date());
		int weeks=cal.get(Calendar.WEEK_OF_YEAR);
		return  weeks+1;
	}

	public static int getWeekOfDate(long time){
		Calendar cal = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
		cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
		cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
		cal.setTime(new Date(time));
		int weeks=cal.get(Calendar.WEEK_OF_YEAR);
		return  weeks+1;
	}
	public static int getMonthOfDay(int year,int month){
		int day = 0;
		if(year%4==0&&year%100!=0||year%400==0){
			day = 29;
		}else{
			day = 28;
		}
		switch (month){
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2:
				return day;

		}
		return 0;
	}
}
