package com.patrol.terminal.bean;

import java.io.Serializable;

public class TroubleDetailBean implements Serializable {

    //公共
    private String id;
    private String task_trouble_id;
    private String deal_notes;
    private String plan_time;
    private String towers;
    private String tower_number;
    private String find_time;
    private String line_name;
    private String line_level;
    private String dep_name;
    private String type_name;
    private String remarks;
    private String status;
    private String done_time;

    //防鸟
    private String total;
    private String year;
    private String category;
    private String batch;
    private String reporting_time;
    private String orders_company;
    private String orders_time;
    private String arrival_time;
    private String installed;

    //防地灾
    private String disaster_type;
    private String join_reason;
    private String main_reason;
    private String scale;
    private String final_deal;

    //防外破
    private String content;
    private String trouble_wares;
    private String duty_unit;
    private String contact;
    private String phone;
    private String deal_notes_first;
    private String deal_notes_second;
    private String deal_notes_third;
    private String deal_notes_fourth;
    private String deal_notes_fifth;
    private String is_video;
    private String company;

    //防雷
    private String reserve_time;

    //防火
    private String reason;

    //防风
    //三跨
    private String line_id;
    private String type_id;
    private String start_id;
    private String end_id;
    private String small_tower_id;
    private String big_tower_id;

    private String across_name;
    private String small_tower_name;
    private String big_tower_name;
    private String tension;
    private String is_independent;
    private String is_double;
    private String is_drainage;
    private String is_metal;
    private String is_adss;

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

    public String getDeal_notes() {
        return deal_notes;
    }

    public void setDeal_notes(String deal_notes) {
        this.deal_notes = deal_notes;
    }

    public String getPlan_time() {
        return plan_time;
    }

    public void setPlan_time(String plan_time) {
        this.plan_time = plan_time;
    }

    public String getTowers() {
        return towers;
    }

    public void setTowers(String towers) {
        this.towers = towers;
    }

    public String getTower_number() {
        return tower_number;
    }

    public void setTower_number(String tower_number) {
        this.tower_number = tower_number;
    }

    public String getFind_time() {
        return find_time;
    }

    public void setFind_time(String find_time) {
        this.find_time = find_time;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getLine_level() {
        return line_level;
    }

    public void setLine_level(String line_level) {
        this.line_level = line_level;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDone_time() {
        return done_time;
    }

    public void setDone_time(String done_time) {
        this.done_time = done_time;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getReporting_time() {
        return reporting_time;
    }

    public void setReporting_time(String reporting_time) {
        this.reporting_time = reporting_time;
    }

    public String getOrders_company() {
        return orders_company;
    }

    public void setOrders_company(String orders_company) {
        this.orders_company = orders_company;
    }

    public String getOrders_time() {
        return orders_time;
    }

    public void setOrders_time(String orders_time) {
        this.orders_time = orders_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getInstalled() {
        return installed;
    }

    public void setInstalled(String installed) {
        this.installed = installed;
    }

    public String getDisaster_type() {
        return disaster_type;
    }

    public void setDisaster_type(String disaster_type) {
        this.disaster_type = disaster_type;
    }

    public String getJoin_reason() {
        return join_reason;
    }

    public void setJoin_reason(String join_reason) {
        this.join_reason = join_reason;
    }

    public String getMain_reason() {
        return main_reason;
    }

    public void setMain_reason(String main_reason) {
        this.main_reason = main_reason;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getFinal_deal() {
        return final_deal;
    }

    public void setFinal_deal(String final_deal) {
        this.final_deal = final_deal;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTrouble_wares() {
        return trouble_wares;
    }

    public void setTrouble_wares(String trouble_wares) {
        this.trouble_wares = trouble_wares;
    }

    public String getDuty_unit() {
        return duty_unit;
    }

    public void setDuty_unit(String duty_unit) {
        this.duty_unit = duty_unit;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeal_notes_first() {
        return deal_notes_first;
    }

    public void setDeal_notes_first(String deal_notes_first) {
        this.deal_notes_first = deal_notes_first;
    }

    public String getDeal_notes_second() {
        return deal_notes_second;
    }

    public void setDeal_notes_second(String deal_notes_second) {
        this.deal_notes_second = deal_notes_second;
    }

    public String getDeal_notes_third() {
        return deal_notes_third;
    }

    public void setDeal_notes_third(String deal_notes_third) {
        this.deal_notes_third = deal_notes_third;
    }

    public String getDeal_notes_fourth() {
        return deal_notes_fourth;
    }

    public void setDeal_notes_fourth(String deal_notes_fourth) {
        this.deal_notes_fourth = deal_notes_fourth;
    }

    public String getDeal_notes_fifth() {
        return deal_notes_fifth;
    }

    public void setDeal_notes_fifth(String deal_notes_fifth) {
        this.deal_notes_fifth = deal_notes_fifth;
    }

    public String getIs_video() {
        return is_video;
    }

    public void setIs_video(String is_video) {
        this.is_video = is_video;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
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

    public String getAcross_name() {
        return across_name;
    }

    public void setAcross_name(String across_name) {
        this.across_name = across_name;
    }

    public String getSmall_tower_id() {
        return small_tower_id;
    }

    public void setSmall_tower_id(String small_tower_id) {
        this.small_tower_id = small_tower_id;
    }

    public String getSmall_tower_name() {
        return small_tower_name;
    }

    public void setSmall_tower_name(String small_tower_name) {
        this.small_tower_name = small_tower_name;
    }

    public String getBig_tower_id() {
        return big_tower_id;
    }

    public void setBig_tower_id(String big_tower_id) {
        this.big_tower_id = big_tower_id;
    }

    public String getBig_tower_name() {
        return big_tower_name;
    }

    public void setBig_tower_name(String big_tower_name) {
        this.big_tower_name = big_tower_name;
    }

    public String getTension() {
        return tension;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }

    public String getIs_independent() {
        return is_independent;
    }

    public void setIs_independent(String is_independent) {
        this.is_independent = is_independent;
    }

    public String getIs_double() {
        return is_double;
    }

    public void setIs_double(String is_double) {
        this.is_double = is_double;
    }

    public String getIs_drainage() {
        return is_drainage;
    }

    public void setIs_drainage(String is_drainage) {
        this.is_drainage = is_drainage;
    }

    public String getIs_metal() {
        return is_metal;
    }

    public void setIs_metal(String is_metal) {
        this.is_metal = is_metal;
    }

    public String getIs_adss() {
        return is_adss;
    }

    public void setIs_adss(String is_adss) {
        this.is_adss = is_adss;
    }
}
