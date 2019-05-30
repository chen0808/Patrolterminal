package com.patrol.terminal.utils;

import android.content.Context;
import android.content.Intent;

import com.patrol.terminal.activity.HongWaiCeWenActivity;
import com.patrol.terminal.activity.JiediDianZuCeLiangActicivity;
import com.patrol.terminal.activity.JueYuanZiLingZhiJianCeActivity;
import com.patrol.terminal.activity.NewPlanActivity;
import com.patrol.terminal.activity.NewTaskActivity;
import com.patrol.terminal.activity.PatrolRecordActivity;
import com.patrol.terminal.activity.XieGanTaQingXieCeWenActivity;

public class Utils {

    public static String getJobName (String jobType) {
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
    // 12保电，安全，验收专责查看检修任务13月检修计划 14周检修计划审核 15检修班长查看周检修任务  16检修负责人查看周检修任务 17工作票审核 18 控制卡审核 19验收计划审核 20安全质量监督审核

   public static Intent goTodo(Context context,String sign){
      Intent intent=new Intent();
      switch (sign){
          case "1":
              intent.setClass(context,NewPlanActivity.class);
              intent.putExtra("from","todoMonth");
              break;
          case "2":
              intent.setClass(context,NewPlanActivity.class);
              intent.putExtra("from","todoWeek");
              break;
          case "3":
              intent.setClass(context, NewTaskActivity.class);
              intent.putExtra("from","todoGroup");
              break;
          case "4":
              intent.setClass(context, NewTaskActivity.class);
              intent.putExtra("from","todoPersonal");
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

      }
      return intent;
   }
}
