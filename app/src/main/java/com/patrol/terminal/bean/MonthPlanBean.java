package com.patrol.terminal.bean;

import java.io.Serializable;

public class MonthPlanBean implements Serializable {






    /**
     * full_plan : 定期巡视,接地电阻
     * plan_type : 1
     * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
     * month : 5
     * year : 2019
     * month_id : 440D30A263F7434DA714523D9782245A
     * line_name : 1111安刘线
     * audit_status : 3
     * dep_name : 西固运维班
     */

    private String type_name;
    private String plan_type;
    private String dep_id;
    private int month;
    private int year;
    private String  type_sign;
    private String month_id;
    private String line_name;
    private String audit_status;
    private String dep_name;
    private int done_num;
    private int all_num;

    public int getDone_num() {
        return done_num;
    }

    public void setDone_num(int done_num) {
        this.done_num = done_num;
    }

    public int getAll_num() {
        return all_num;
    }

    public void setAll_num(int all_num) {
        this.all_num = all_num;
    }

    // 线路总长度(km) - pda用于计算线路总长
    private Double line_length;

    public Double getLine_length() {
        return line_length;
    }

    public void setLine_length(Double line_length) {
        this.line_length = line_length;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_sign() {
        return type_sign;
    }

    public void setType_sign(String type_sign) {
        this.type_sign = type_sign;
    }

    /**
     * id : 161E390898624F04A88E53F832712FEF
     * apply_dep_id : 34B9F165BF9B4527B01ABF328055FBD3
     * apply_dep_name : 兰州供电局
     * voltage_level : 110kv
     * line_id : C0848BA9870747869C86817785BB4091
     * repair_content : 恢复兴前线1#至门架的导地线
     * is_blackout : 1
     * blackout_range : 安宁区
     * task_source : 停电配合
     * start_time : 2019-05-03
     * end_time : 2019-05-05
     * blackout_days : 1
     * last_repair_time : 2019-01-01
     * risk_level : 6
     * type_id : null
     * type_val : null
     * substation_id : 110kV源泰变电站
     * remark : 22
     * week : 1
     * day : 3
     * month_audit_status : 2
     * week_audit_status : 2
     * done_status : 0
     * done_time : null
     * is_ele : 1
     * ele_user_id : 94AE9C0BD55A43E49E47D12BD1F48391
     * ele_user_name : 王小龙
     * safe_user_id : FF9F62C044724A319A9CCE7848AEB19D
     * safe_user_name : 丁伟
     * check_user_id : D7E03E77D625448CAAAE0B4A96D0D3D3
     * check_user_name : 龚靖东
     * taskList : null
     * userId1 : null
     * userName1 : null
     * userId2 : null
     * userName2 : null
     */

    private String id;
    private String apply_dep_id;
    private String apply_dep_name;
    private String voltage_level;
    private String line_id;
    private String repair_content;
    private String is_blackout;
    private String blackout_range;
    private String task_source;
    private String start_time;
    private String end_time;
    private int blackout_days;
    private String last_repair_time;
    private String risk_level;
    private String type_id;
    private String type_val;
    private String substation_id;
    private String remark;
    private int week;
    private int day;
    private String month_audit_status;
    private String week_audit_status;
    private String done_status;
    private String done_time;
    private String is_ele;
    private String ele_user_id;
    private String ele_user_name;
    private String safe_user_id;
    private String safe_user_name;
    private String check_user_id;
    private String check_user_name;
    private String taskList;
    private String userId1;
    private String userName1;
    private String userId2;
    private String userName2;
    private String allot_status;

    public String getAllot_status() {
        return allot_status;
    }

    public void setAllot_status(String allot_status) {
        this.allot_status = allot_status;
    }
    

    public String getFull_plan() {
        return type_name;
    }

    public void setFull_plan(String full_plan) {
        this.type_name = full_plan;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth_id() {
        return month_id;
    }

    public void setMonth_id(String month_id) {
        this.month_id = month_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApply_dep_id() {
        return apply_dep_id;
    }

    public void setApply_dep_id(String apply_dep_id) {
        this.apply_dep_id = apply_dep_id;
    }

    public String getApply_dep_name() {
        return apply_dep_name;
    }

    public void setApply_dep_name(String apply_dep_name) {
        this.apply_dep_name = apply_dep_name;
    }

    public String getVoltage_level() {
        return voltage_level;
    }

    public void setVoltage_level(String voltage_level) {
        this.voltage_level = voltage_level;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getRepair_content() {
        return repair_content;
    }

    public void setRepair_content(String repair_content) {
        this.repair_content = repair_content;
    }

    public String getIs_blackout() {
        return is_blackout;
    }

    public void setIs_blackout(String is_blackout) {
        this.is_blackout = is_blackout;
    }

    public String getBlackout_range() {
        return blackout_range;
    }

    public void setBlackout_range(String blackout_range) {
        this.blackout_range = blackout_range;
    }

    public String getTask_source() {
        return task_source;
    }

    public void setTask_source(String task_source) {
        this.task_source = task_source;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getBlackout_days() {
        return blackout_days;
    }

    public void setBlackout_days(int blackout_days) {
        this.blackout_days = blackout_days;
    }

    public String getLast_repair_time() {
        return last_repair_time;
    }

    public void setLast_repair_time(String last_repair_time) {
        this.last_repair_time = last_repair_time;
    }

    public String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_val() {
        return type_val;
    }

    public void setType_val(String type_val) {
        this.type_val = type_val;
    }

    public String getSubstation_id() {
        return substation_id;
    }

    public void setSubstation_id(String substation_id) {
        this.substation_id = substation_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth_audit_status() {
        return month_audit_status;
    }

    public void setMonth_audit_status(String month_audit_status) {
        this.month_audit_status = month_audit_status;
    }

    public String getWeek_audit_status() {
        return week_audit_status;
    }

    public void setWeek_audit_status(String week_audit_status) {
        this.week_audit_status = week_audit_status;
    }

    public String getDone_status() {
        return done_status;
    }

    public void setDone_status(String done_status) {
        this.done_status = done_status;
    }

    public String getDone_time() {
        return done_time;
    }

    public void setDone_time(String done_time) {
        this.done_time = done_time;
    }

    public String getIs_ele() {
        return is_ele;
    }

    public void setIs_ele(String is_ele) {
        this.is_ele = is_ele;
    }

    public String getEle_user_id() {
        return ele_user_id;
    }

    public void setEle_user_id(String ele_user_id) {
        this.ele_user_id = ele_user_id;
    }

    public String getEle_user_name() {
        return ele_user_name;
    }

    public void setEle_user_name(String ele_user_name) {
        this.ele_user_name = ele_user_name;
    }

    public String getSafe_user_id() {
        return safe_user_id;
    }

    public void setSafe_user_id(String safe_user_id) {
        this.safe_user_id = safe_user_id;
    }

    public String getSafe_user_name() {
        return safe_user_name;
    }

    public void setSafe_user_name(String safe_user_name) {
        this.safe_user_name = safe_user_name;
    }

    public String getCheck_user_id() {
        return check_user_id;
    }

    public void setCheck_user_id(String check_user_id) {
        this.check_user_id = check_user_id;
    }

    public String getCheck_user_name() {
        return check_user_name;
    }

    public void setCheck_user_name(String check_user_name) {
        this.check_user_name = check_user_name;
    }

    public String getTaskList() {
        return taskList;
    }

    public void setTaskList(String taskList) {
        this.taskList = taskList;
    }

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getUserName1() {
        return userName1;
    }

    public void setUserName1(String userName1) {
        this.userName1 = userName1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    public String getUserName2() {
        return userName2;
    }

    public void setUserName2(String userName2) {
        this.userName2 = userName2;
    }

}
