package com.patrol.terminal.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

}
