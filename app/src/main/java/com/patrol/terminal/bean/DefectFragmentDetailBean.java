package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class DefectFragmentDetailBean implements Serializable {

    /**
     *      "task_id": "16286DA283B744988A806B22DAE5C779",
     *      "category_id": null,
     *      "category_name": "导线、地线",
     *      "grade_id": "10C639F13341484997EE8D955322BE02",
     *      "grade_name": "危急",
     *      "patrol_id": "7AC64E2FC84342B891F4E642BE9ED2A9",
     *      "patrol_name": "（2）导线、地线弧垂变化；导线、地线上扬、振动、舞动、脱冰跳跃",
     *      "content": "fhjjbvccghjj",
     *      "close_time": null,
     *      "line_id": "3B14083B22604672B96A907699F28FE2",
     *      "line_name": "3513华阿一线",
     *      "tower_id": "48F03C8EE7434CF880B0C36E6D047629",
     *      "tower_name": "#032",
     *      "find_time": "2019-07-25",
     *      "find_user_id": "9C9164DB3CFA4AACB4DE8D54BE198541",
     *      "find_user_name": "马宝龙",
     *      "find_dep_id": "024CD2E91A8447A799335E21B0FDADB6",
     *      "find_dep_name": "榆中运维班",
     *      "deal_notes": "补装",
     *      "deal_user_id": null,
     *      "deal_user_name": null,
     *      "deal_dep_id": null,
     *      "deal_dep_name": null,
     *      "deal_time": null,
     *      "done_status": "0",
     *      "in_status": "1",
     *      "remark": null,
     *      "defect_file": null,
     *      "fileList": [
     *        {
     *          "id": "F8DF7819B20440EE844935A8CCFE53C6",
     *          "task_id": "16286DA283B744988A806B22DAE5C779",
     *          "task_defect_id": "D3A95871BB514DCFA7906A3BD641D623",
     *          "upload_time": "2019-07-25 15:40:13",
     *          "filename": "70df2c70-0066-418e-b467-39a1a3868976.jpg",
     *          "file_path": "\/upload.folder\/"
     *        },
     *        {
     *          "id": "8B4BDE89688442AC94F69E5F2C4B6473",
     *           "task_id": "16286DA283B744988A806B22DAE5C779",
     *           "task_defect_id": "D3A95871BB514DCFA7906A3BD641D623",
     *           "upload_time": "2019-07-25 15:40:13",
     *           "filename": "0c880da6-ed43-4208-8875-b7467805a376.jpg",
     *           "file_path": "\/upload.folder\/"
     *         },
     *         {
     *           "id": "3715BFBFEB9448DDBCF0EB5AE84D8E13",
     *           "task_id": "16286DA283B744988A806B22DAE5C779",
     *           "task_defect_id": "D3A95871BB514DCFA7906A3BD641D623",
     *           "upload_time": "2019-07-25 15:40:13",
     *           "filename": "851169ae-7bef-4bcf-83f8-cc592c0f4b2d.jpg",
     *           "file_path": "\/upload.folder\/"
     *         }
     *       ]
     */

    private String id;
    private Object month_line_id;
    private Object week_id;
    private Object day_id;
    private Object group_id;
    private Object task_id;
    private String category_id;
    private String grade_id;
    private String grade_name;// 缺陷级别   危急，严重，一般
    private Object patrol_id;
    private String patrol_name;// 缺陷巡视内容   1导线，地线腐蚀
    private String content;// 缺陷内容   手输入内容
    private String line_id;// 线路id
    private String line_name;// 线路名称
    private String close_time;// 缺陷截止时间
    private String tower_id;// 杆塔id
    private String tower_name;// 杆塔名称
    private String start_name;
    private String end_name;
    private String find_time; // 发现时间
    private String deal_notes;// 处理措施
    private String status;
    private String deal_dep_name;// 消缺班组名称
    private String deal_time;// 消除时间
    private String auditor;
    private String audit_status;
    private Object week_line_id;
    private Object day_line_id;
    private Object month_id;
    private Object group_list_id;
    private Object deal_dep_id;
    private String start_id;
    private String end_id;
    // 缺陷完成状态   不管  （0：待处理，1：月计划分配，2：周计划分配，3：日计划分配，4：进行中，5：已完成，6：未完成）
    private String done_status;
    private Object find_user_id;
    private String find_user_name;// 发现人姓名
    private Object deal_user_id;
    private String deal_user_name;// 处理人姓名
    private String remark;// 工作备注
    private Object find_dep_id;// 发现人部门id  登录人部门id
    private String find_dep_name;// 发现人部门名称
    private String category_name;// 缺陷类别  导线，地线
    private Object user_name;
    private Object dep_name;
    // 缺陷入库状态     不管  （0：编制，1：待班长审核，2：待专责审核，3：复核中，4：审核通过，5：审核不通过）
    private String in_status;
    private List<FileBean> fileList;

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public String getDone_status() {
        return done_status;
    }

    public void setDone_status(String done_status) {
        this.done_status = done_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getMonth_line_id() {
        return month_line_id;
    }

    public void setMonth_line_id(Object month_line_id) {
        this.month_line_id = month_line_id;
    }

    public Object getWeek_id() {
        return week_id;
    }

    public void setWeek_id(Object week_id) {
        this.week_id = week_id;
    }

    public Object getDay_id() {
        return day_id;
    }

    public void setDay_id(Object day_id) {
        this.day_id = day_id;
    }

    public Object getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Object group_id) {
        this.group_id = group_id;
    }

    public Object getTask_id() {
        return task_id;
    }

    public void setTask_id(Object task_id) {
        this.task_id = task_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }

    public Object getPatrol_id() {
        return patrol_id;
    }

    public void setPatrol_id(Object patrol_id) {
        this.patrol_id = patrol_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getEnd_name() {
        return end_name;
    }

    public void setEnd_name(String end_name) {
        this.end_name = end_name;
    }

    public String getFind_time() {
        return find_time;
    }

    public void setFind_time(String find_time) {
        this.find_time = find_time;
    }

    public String getDeal_notes() {
        return deal_notes;
    }

    public void setDeal_notes(String deal_notes) {
        this.deal_notes = deal_notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeal_dep_name() {
        return deal_dep_name;
    }

    public void setDeal_dep_name(String deal_dep_name) {
        this.deal_dep_name = deal_dep_name;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public Object getWeek_line_id() {
        return week_line_id;
    }

    public void setWeek_line_id(Object week_line_id) {
        this.week_line_id = week_line_id;
    }

    public Object getDay_line_id() {
        return day_line_id;
    }

    public void setDay_line_id(Object day_line_id) {
        this.day_line_id = day_line_id;
    }

    public Object getMonth_id() {
        return month_id;
    }

    public void setMonth_id(Object month_id) {
        this.month_id = month_id;
    }

    public Object getGroup_list_id() {
        return group_list_id;
    }

    public void setGroup_list_id(Object group_list_id) {
        this.group_list_id = group_list_id;
    }

    public Object getDeal_dep_id() {
        return deal_dep_id;
    }

    public void setDeal_dep_id(Object deal_dep_id) {
        this.deal_dep_id = deal_dep_id;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public String getEnd_id() {
        return end_id;
    }

    public void setEnd_id(String end_id) {
        this.end_id = end_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public Object getFind_user_id() {
        return find_user_id;
    }

    public void setFind_user_id(Object find_user_id) {
        this.find_user_id = find_user_id;
    }

    public String getFind_user_name() {
        return find_user_name;
    }

    public void setFind_user_name(String find_user_name) {
        this.find_user_name = find_user_name;
    }

    public Object getDeal_user_id() {
        return deal_user_id;
    }

    public void setDeal_user_id(Object deal_user_id) {
        this.deal_user_id = deal_user_id;
    }

    public String getDeal_user_name() {
        return deal_user_name;
    }

    public void setDeal_user_name(String deal_user_name) {
        this.deal_user_name = deal_user_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getFind_dep_id() {
        return find_dep_id;
    }

    public void setFind_dep_id(Object find_dep_id) {
        this.find_dep_id = find_dep_id;
    }

    public String getFind_dep_name() {
        return find_dep_name;
    }

    public void setFind_dep_name(String find_dep_name) {
        this.find_dep_name = find_dep_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public Object getUser_name() {
        return user_name;
    }

    public void setUser_name(Object user_name) {
        this.user_name = user_name;
    }

    public Object getDep_name() {
        return dep_name;
    }

    public void setDep_name(Object dep_name) {
        this.dep_name = dep_name;
    }

    public List<FileBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileBean> fileList) {
        this.fileList = fileList;
    }

    public String getIn_status() {
        return in_status;
    }

    public void setIn_status(String in_status) {
        this.in_status = in_status;
    }

    public String getTower_name() {
        return tower_name;
    }

    public void setTower_name(String tower_name) {
        this.tower_name = tower_name;
    }

    public String getPatrol_name() {
        return patrol_name;
    }

    public void setPatrol_name(String patrol_name) {
        this.patrol_name = patrol_name;
    }
}
