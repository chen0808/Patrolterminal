package com.patrol.terminal.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;

import com.patrol.terminal.R;
import com.patrol.terminal.activity.CaptureActivity;
import com.patrol.terminal.activity.GroupTaskDetailActivity;
import com.patrol.terminal.activity.HongWaiCeWenActivity;
import com.patrol.terminal.activity.JiediDianZuCeLiangActicivity;
import com.patrol.terminal.activity.JueYuanZiLingZhiJianCeActivity;
import com.patrol.terminal.activity.NewTaskActivity;
import com.patrol.terminal.activity.NextMonthPlanActivity;
import com.patrol.terminal.activity.NextWeekPlanActivity;
import com.patrol.terminal.activity.PatrolRecordActivity;
import com.patrol.terminal.activity.PersonalTaskDetailActivity;
import com.patrol.terminal.activity.XieGanTaQingXieCeWenActivity;
import com.patrol.terminal.bean.DefactTvModel;
import com.patrol.terminal.bean.TodoBean;

import java.math.BigDecimal;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

public class Utils {

    public static String getJobName(String jobType) {
        String jobName = Constant.JOBS_NAME[0];
        switch (jobType) {
            case Constant.RUNNING_SQUAD_LEADER:          //1.运行班班长
                jobName = Constant.JOBS_NAME[0];
                break;

            case Constant.RUNNING_SQUAD_TEMA_LEADER:    //2.运行班组长（临时）
                jobName = Constant.JOBS_NAME[1];
                break;

            case Constant.RUNNING_SQUAD_MEMBER:          //3.运行班班员（可能是班级的审计员）
                jobName = Constant.JOBS_NAME[2];
                break;

            case Constant.REFURBISHMENT_LEADER:          //4.检修班班长
                jobName = Constant.JOBS_NAME[3];
                break;

            case Constant.REFURBISHMENT_TEMA_LEADER:    //5.检修班组长（临时）
                jobName = Constant.JOBS_NAME[4];
                break;

            case Constant.REFURBISHMENT_MEMBER:          // 6.检修班班员（可能是班级的审计员）
                jobName = Constant.JOBS_NAME[5];
                break;

            case Constant.RUNNING_SQUAD_SPECIALIZED:     //7.运行专责
                jobName = Constant.JOBS_NAME[6];
                break;

            case Constant.REFURBISHMENT_SPECIALIZED:     //8.检修专责
                jobName = Constant.JOBS_NAME[7];
                break;

            case Constant.DISTRICT_MANAGERD:                  //9.工区主管
                jobName = Constant.JOBS_NAME[8];
                break;

            case Constant.RUN_SUPERVISOR:
                jobName = Constant.JOBS_NAME[9];
                break;

            case Constant.MAINTENANCE_SUPERVISOR:
                jobName = Constant.JOBS_NAME[10];
                break;

            case Constant.ACHIEVEMENTS_SPECIALIZED:        //绩效专责
                jobName = Constant.JOBS_NAME[11];
                break;

            case Constant.TRAINING_SPECIALIZED:        //绩效专责
                jobName = Constant.JOBS_NAME[12];
                break;


            case Constant.CAR_SQUAD_LEADER:            //13.汽车班班长
                jobName = Constant.JOBS_NAME[13];
                break;

            case Constant.CAR_SQUAD_MEMBER:            //14.汽车班班员
                jobName = Constant.JOBS_NAME[14];
                break;

            case Constant.POWER_CONSERVATION_SPECIALIZED:
                jobName = Constant.JOBS_NAME[15];
                break;

            case Constant.SAFETY_SPECIALIZED:
                jobName = Constant.JOBS_NAME[16];
                break;

            case Constant.ACCEPTANCE_CHECK_SPECIALIZED:
                jobName = Constant.JOBS_NAME[17];
                break;
        }
        return jobName;
    }

    //1月运行计划审核 2，周运行计划审核，3 运行小组长查看小组任务 4运行组员查看个人任务 5红外测温测量审核，6接电电阻测审量核，7绝缘子测量审核 8杆塔倾斜测量审核 9巡视记录审核 10缺陷审核，11隐患审核
    // 12保电，安全，验收专责查看检修任务13月检修计划 14周检修计划审核 15检修班长查看周检修任务  16检修负责人查看周检修任务 17工作票审核 18 控制卡审核 19验收计划审核 20安全质量监督审核 21运行组员抢单
    //22 抢单退还
    public static Intent goTodo(Context context, TodoBean bean) {
        Intent intent = new Intent();
        switch (bean.getFlow_sign()) {
            case "1":
                intent.setClass(context, NextMonthPlanActivity.class);
                intent.putExtra("year", bean.getYear());
                intent.putExtra("month", bean.getMonth());
                intent.putExtra("from", "todoMonth");
                break;
            case "2":
                intent.setClass(context, NextWeekPlanActivity.class);
                intent.putExtra("year", bean.getYear());
                intent.putExtra("week", bean.getWeek());
                intent.putExtra("from", "todoWeek");
                break;
            case "21":
                intent.setClass(context, GroupTaskDetailActivity.class);
                intent.putExtra("from", "todoGroup");
                break;
            case "3":
                intent.setClass(context, NewTaskActivity.class);
                intent.putExtra("from", "todoGroup");
                intent.putExtra("index", 0);

                break;
            case "4":
                intent.setClass(context, NewTaskActivity.class);
                intent.putExtra("from", "todoPersonal");
                intent.putExtra("index", 1);
                break;
            case "5":
                intent.setClass(context, HongWaiCeWenActivity.class);
                break;
            case "6":
                intent.setClass(context, JiediDianZuCeLiangActicivity.class);
                break;
            case "7":
                intent.setClass(context, JueYuanZiLingZhiJianCeActivity.class);
                break;
            case "8":
                intent.setClass(context, XieGanTaQingXieCeWenActivity.class);
                break;
            case "9":
                intent.setClass(context, PatrolRecordActivity.class);
                break;
            case "10":
                break;
            case "11":
                break;
            case "12":
                break;
            case "13":
                break;
            case "22":
                intent.setClass(context, GroupTaskDetailActivity.class);
                intent.putExtra("from", "todoRob");
                break;
        }
        return intent;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getTodoIcon(String sign) {
        switch (sign) {
            case "1":
                return R.mipmap.todo1;
            case "2":
                return R.mipmap.todo2;
            case "3":
                return R.mipmap.todo3;
            case "4":
                return R.mipmap.todo4;
            case "5":
                return R.mipmap.todo5;
            case "6":
                return R.mipmap.todo6;
            case "7":
                return R.mipmap.todo7;
            case "8":
                return R.mipmap.todo8;
            case "9":
                return R.mipmap.todo9;
            case "10":
                return R.mipmap.todo10;
            case "11":
                return R.mipmap.todo11;
            case "12":
                return R.mipmap.todo12;
            case "13":
                return R.mipmap.todo13;
            case "14":
                return R.mipmap.todo14;
            case "15":
                return R.mipmap.todo15;
            case "16":
                return R.mipmap.todo16;
            case "17":
                return R.mipmap.todo17;
            case "18":
                return R.mipmap.todo18;
            case "19":
                return R.mipmap.todo19;
            case "20":
                return R.mipmap.todo20;
            case "21":
                return R.mipmap.todo21;
            case "22":
                return R.mipmap.todo22;
            default:
                return R.mipmap.todo1;
        }


    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     **/
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        //默认保留两位会有错误，这里设置保留小数点后4位
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取 excel 表格中的数据,不能在主线程中调用
     *
     * @param xlsName excel 表格的名称
     * @param index   第几张表格中的数据
     */
    public static ArrayList<DefactTvModel> getXlsData(Context context, String xlsName, int index) {
        ArrayList<DefactTvModel> countryList = new ArrayList<DefactTvModel>();
        AssetManager assetManager = context.getAssets();

        try {
            Workbook workbook = Workbook.getWorkbook(assetManager.open(xlsName));
            Sheet sheet = workbook.getSheet(index);

            //int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            //int sheetColumns = sheet.getColumns();

            for (int i = 0; i < sheetRows; i++) {
                DefactTvModel defactTvModel = new DefactTvModel();
                defactTvModel.setContent(sheet.getCell(5, i).getContents());
                defactTvModel.setLevel(sheet.getCell(6, i).getContents());
                countryList.add(defactTvModel);
            }

            workbook.close();

        } catch (Exception e) {
            Log.e("xxx", "read error=" + e, e);
        }

        return countryList;
    }
}
