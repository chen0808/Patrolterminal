package com.patrol.terminal.bean;

/**
 * 表名: TASK_DEFECT_PATROL_RECODE - 巡视内容记录表
 * <p>
 * Date：2019-05-31 21:28:52
 */
public class TaskDefectPatrolRecode {

    // 主键id
    private String id;

    // 个人任务id
    private String task_id;

    // 缺陷巡视内容表id
    private String patrol_id;

    // 状态（0：未巡视，1：巡视有缺陷 2：巡视无缺陷）
    private String status;

    /*** 自定义字段 ***/

    // 返回主表信息 sz
    private String name;

    // 返回主表信息 sz
    private String category;

    // 返回主表信息 sz
    private String remarks;

    private TaskDefect taskDefect;

}