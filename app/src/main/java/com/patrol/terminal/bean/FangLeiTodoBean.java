package com.patrol.terminal.bean;

import java.util.List;

public class FangLeiTodoBean {

    /**
     * id : 433E982F954742728F65041F4D817BED
     * task_trouble_id : AB33F5BF7239426CB676A976FAA413F0
     * total : null
     * deal_notes : null
     * company : null
     * reserve_time : null
     * task_id : 7C7B72E520EF473FB7562AD215F540CB
     * type_id : 3
     * type_name : 防雷
     * grade_sign : 1
     * content : null
     * close_time : null
     * line_id : 0D7F62036CB5458692811737D90FB097
     * line_name : 1121和焦T红线
     * tower_id : BD029BBB62204D7A86802B88FEB84E92
     * tower_name : #003
     * find_time : 2019-07-28
     * find_user_id : 9C9164DB3CFA4AACB4DE8D54BE198541
     * find_user_name : 马宝龙
     * find_dep_id : 024CD2E91A8447A799335E21B0FDADB6
     * find_dep_name : 榆中运维班
     * deal_user_id : null
     * deal_user_name : null
     * deal_dep_id : null
     * deal_dep_name : null
     * deal_time : null
     * done_status : 0
     * in_status : 1
     * remark : null
     * wares_id : E1C4D215842044CE82BE1F05704238B0
     * wares_name : 高速铁路
     * advice_deal_notes : 更换接地网
     * troubleFileList : [{"id":"17B7F27128D24770B0976B58B67FE353","task_id":"7C7B72E520EF473FB7562AD215F540CB","task_trouble_id":"AB33F5BF7239426CB676A976FAA413F0","upload_time":"2019-07-28 12:19:01","filename":"3b0219e1-1819-4fc4-8571-f180766ef835.jpg","file_path":"/upload.folder/"}]
     */

    private String id;
    private String task_trouble_id;
    private String total;
    private String deal_notes;
    private String company;
    private String reserve_time;
    private String task_id;
    private String type_id;
    private String type_name;
    private String grade_sign;
    private String content;
    private String close_time;
    private String line_id;
    private String line_name;
    private String tower_id;
    private String tower_name;
    private String find_time;
    private String find_user_id;
    private String find_user_name;
    private String find_dep_id;
    private String find_dep_name;
    private String deal_user_id;
    private String deal_user_name;
    private String deal_dep_id;
    private String deal_dep_name;
    private String deal_time;
    private String done_status;
    private String in_status;
    private String remark;
    private String wares_id;
    private String wares_name;
    private String advice_deal_notes;
    private String line_level;
    private List<TroubleFileListBean> troubleFileList;//隐患
    private List<TroubleFileListBean> eqTowerWaresImgList;//特殊属性

    public List<TroubleFileListBean> getEqTowerWaresImgList() {
        return eqTowerWaresImgList;
    }

    public void setEqTowerWaresImgList(List<TroubleFileListBean> eqTowerWaresImgList) {
        this.eqTowerWaresImgList = eqTowerWaresImgList;
    }

    public String getLine_level() {
        return line_level;
    }

    public void setLine_level(String line_level) {
        this.line_level = line_level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_trouble_id() {
        return task_trouble_id;
    }

    public void setTask_trouble_id(String task_trouble_id) {
        this.task_trouble_id = task_trouble_id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDeal_notes() {
        return deal_notes;
    }

    public void setDeal_notes(String deal_notes) {
        this.deal_notes = deal_notes;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(String reserve_time) {
        this.reserve_time = reserve_time;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getGrade_sign() {
        return grade_sign;
    }

    public void setGrade_sign(String grade_sign) {
        this.grade_sign = grade_sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public String getTower_name() {
        return tower_name;
    }

    public void setTower_name(String tower_name) {
        this.tower_name = tower_name;
    }

    public String getFind_time() {
        return find_time;
    }

    public void setFind_time(String find_time) {
        this.find_time = find_time;
    }

    public String getFind_user_id() {
        return find_user_id;
    }

    public void setFind_user_id(String find_user_id) {
        this.find_user_id = find_user_id;
    }

    public String getFind_user_name() {
        return find_user_name;
    }

    public void setFind_user_name(String find_user_name) {
        this.find_user_name = find_user_name;
    }

    public String getFind_dep_id() {
        return find_dep_id;
    }

    public void setFind_dep_id(String find_dep_id) {
        this.find_dep_id = find_dep_id;
    }

    public String getFind_dep_name() {
        return find_dep_name;
    }

    public void setFind_dep_name(String find_dep_name) {
        this.find_dep_name = find_dep_name;
    }

    public String getDeal_user_id() {
        return deal_user_id;
    }

    public void setDeal_user_id(String deal_user_id) {
        this.deal_user_id = deal_user_id;
    }

    public String getDeal_user_name() {
        return deal_user_name;
    }

    public void setDeal_user_name(String deal_user_name) {
        this.deal_user_name = deal_user_name;
    }

    public String getDeal_dep_id() {
        return deal_dep_id;
    }

    public void setDeal_dep_id(String deal_dep_id) {
        this.deal_dep_id = deal_dep_id;
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

    public String getDone_status() {
        return done_status;
    }

    public void setDone_status(String done_status) {
        this.done_status = done_status;
    }

    public String getIn_status() {
        return in_status;
    }

    public void setIn_status(String in_status) {
        this.in_status = in_status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWares_id() {
        return wares_id;
    }

    public void setWares_id(String wares_id) {
        this.wares_id = wares_id;
    }

    public String getWares_name() {
        return wares_name;
    }

    public void setWares_name(String wares_name) {
        this.wares_name = wares_name;
    }

    public String getAdvice_deal_notes() {
        return advice_deal_notes;
    }

    public void setAdvice_deal_notes(String advice_deal_notes) {
        this.advice_deal_notes = advice_deal_notes;
    }

    public List<TroubleFileListBean> getTroubleFileList() {
        return troubleFileList;
    }

    public void setTroubleFileList(List<TroubleFileListBean> troubleFileList) {
        this.troubleFileList = troubleFileList;
    }

    public static class TroubleFileListBean {
        /**
         * id : 17B7F27128D24770B0976B58B67FE353
         * task_id : 7C7B72E520EF473FB7562AD215F540CB
         * task_trouble_id : AB33F5BF7239426CB676A976FAA413F0
         * upload_time : 2019-07-28 12:19:01
         * filename : 3b0219e1-1819-4fc4-8571-f180766ef835.jpg
         * file_path : /upload.folder/
         */

        private String id;
        private String task_id;
        private String task_trouble_id;
        private String upload_time;
        private String filename;
        private String file_path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getTask_trouble_id() {
            return task_trouble_id;
        }

        public void setTask_trouble_id(String task_trouble_id) {
            this.task_trouble_id = task_trouble_id;
        }

        public String getUpload_time() {
            return upload_time;
        }

        public void setUpload_time(String upload_time) {
            this.upload_time = upload_time;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }
}
