package com.patrol.terminal.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUatil {


    //获取当前是第几周
    public static int getWeek() {
        Calendar calendar = Calendar.getInstance();
        //获取当前时间为本月的第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
            week = week - 1;
        } else {
            day = day - 1;
        }
        return week;
    }

    //获取当前是一周第几天
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        //获取当前时间为本月的第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
            week = week - 1;
        } else {
            day = day - 1;
        }
        return day;
    }

    public static  int getWeekNum(){
        Calendar calendar= Calendar.getInstance();
        //获取当前时间为本月的第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day==1) {
            day=7;
            week=week-1;
        } else {
            day=day-1;
        }

        return week;
    }

    //获取一个月有多少天
    public static int getWeekNumOfMonth(String year,String month){
        Calendar c=Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY); //指定从周一开始
        c.set(Calendar.YEAR,Integer.parseInt(year));//年
        c.set(Calendar.MONTH,Integer.parseInt(month)-1);//月
//        System.out.println("------------"+c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月的天数和周数-------------");
//        System.out.println("天数："+c.getActualMaximum(Calendar.DAY_OF_MONTH));
//        System.out.println("周数："+c.getActualMaximum(Calendar.WEEK_OF_MONTH));
        return c.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }
    //获取当前月份
    public static String getCurMonth() {
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        String format1 = format.format(new Date(l));
        return format1;
    }
    public static String getYear(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format  = new SimpleDateFormat("yyyy年");
        return format.format(date);
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format  = new SimpleDateFormat("yyyy年MM月");
        return format.format(date);
    }
    public static String getDay(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format  = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }
    public static String getDate(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    public static String getSelectTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format  = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分");
        return format.format(date);
    }

    public static String getCurrTime() {//可根据需要自行截取数据显示
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat format  = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分");
        return format.format(date);
    }

    public static String getTime(long time) {
        Date date=new Date(time);
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String getTime() {
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     *
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        //long seconds = (mss % (1000 * 60)) / 1000;
        return days + "天 " + hours + "小时 " + minutes + "分钟 "
               /* + seconds + " seconds "*/;
    }

    public static double formatString(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        //long seconds = (mss % (1000 * 60)) / 1000;
        double time = days + hours / 12 + minutes /1440;
        return time;
    }

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
                    System.out.println("begin:" + beginNum);

                    int endNum = midNum + 7;
                    System.out.println("end:" + endNum);

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
     * 获取日期属于周几
     * @param time
     * @return
     */
    public static String dateToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String times = "";
        try {
            Date date = sdf.parse(time);
            times = sdf1.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
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

}
