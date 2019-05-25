package com.patrol.terminal.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

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




}
