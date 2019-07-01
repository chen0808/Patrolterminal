package com.patrol.terminal.bean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * sz  2019年7月1日16:24:11
 * 巡视记录接收
 */
public class TaskPatrolDTO implements Serializable {

    // 巡视记录6张图
    private File[] patrolFile;

    //任务id
    private String task_id;

    // 常规巡视
    private List<TaskDefectPatrolRecode> taskDefectPatrolRecodeList;

    public File[] getPatrolFile() {
        return patrolFile;
    }

    public void setPatrolFile(File[] patrolFile) {
        this.patrolFile = patrolFile;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public List<TaskDefectPatrolRecode> getTaskDefectPatrolRecodeList() {
        return taskDefectPatrolRecodeList;
    }

    public void setTaskDefectPatrolRecodeList(List<TaskDefectPatrolRecode> taskDefectPatrolRecodeList) {
        this.taskDefectPatrolRecodeList = taskDefectPatrolRecodeList;
    }
}
