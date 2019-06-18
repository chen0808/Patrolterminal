package com.patrol.terminal.bean;

import java.io.Serializable;

public class TodoBean implements Serializable {

    /**
     * id : BD5F6DCE16D14BE5BFA1DC11DF955A49
     * menu_id : null
     * module : 0
     * run_id : BF098B592DBF45E2A8B13B95D9F211A5
     * data_id : 8414D98AA23C4CB98F9704013706EB72
     * title : 巡视记录待负责人审核
     * url : null
     * flow_sign : 9
     * node_sign : 1
     * to_user_id : 0B9EAADA3DBD4317B4CF80BFA309587C
     * from_user_id : 0B9EAADA3DBD4317B4CF80BFA309587C
     * from_user_name : 蒋秀珍
     * remind_type : 1
     * done_status : 0
     * deal_user_id : null
     * deal_user_name : null
     * deal_time : null
     * create_time : 2019-06-12 03:33:27
     */

    private String id;
    private Object menu_id;
    private String module;
    private String run_id;
    private String data_id;
    private String title;
    private Object url;
    private String flow_sign;
    private String node_sign;
    private String to_user_id;
    private String from_user_id;
    private String from_user_name;
    private String remind_type;
    private String done_status;
    private Object deal_user_id;
    private Object deal_user_name;
    private Object deal_time;
    private String create_time;
    private int year;
    private int month;
    private int week;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Object menu_id) {
        this.menu_id = menu_id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getRun_id() {
        return run_id;
    }

    public void setRun_id(String run_id) {
        this.run_id = run_id;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public String getFlow_sign() {
        return flow_sign;
    }

    public void setFlow_sign(String flow_sign) {
        this.flow_sign = flow_sign;
    }

    public String getNode_sign() {
        return node_sign;
    }

    public void setNode_sign(String node_sign) {
        this.node_sign = node_sign;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getFrom_user_name() {
        return from_user_name;
    }

    public void setFrom_user_name(String from_user_name) {
        this.from_user_name = from_user_name;
    }

    public String getRemind_type() {
        return remind_type;
    }

    public void setRemind_type(String remind_type) {
        this.remind_type = remind_type;
    }

    public String getDone_status() {
        return done_status;
    }

    public void setDone_status(String done_status) {
        this.done_status = done_status;
    }

    public Object getDeal_user_id() {
        return deal_user_id;
    }

    public void setDeal_user_id(Object deal_user_id) {
        this.deal_user_id = deal_user_id;
    }

    public Object getDeal_user_name() {
        return deal_user_name;
    }

    public void setDeal_user_name(Object deal_user_name) {
        this.deal_user_name = deal_user_name;
    }

    public Object getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(Object deal_time) {
        this.deal_time = deal_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
