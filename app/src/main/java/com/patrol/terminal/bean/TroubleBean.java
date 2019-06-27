package com.patrol.terminal.bean;

import java.io.Serializable;

public class TroubleBean implements Serializable {

    /**
     * id : 74CCC547A2C844B78429F5506094B7B7
     * task_id : E6FCC7820C8745A8BADB1AF1521B20CB
     * line_id : 7FED02A7DCE94D34B010A074A29802D8
     * towers : #001-#002
     * start_id : A70736A860194A13AAF11B10B680D41F
     * end_id : 81B4D03308C94063B10E6F7E6B9F4B90
     * start_name : #001
     * end_name : #002
     * tower_number : null
     * type_id : 2F9D2BEF6B08403FAD1FED246B79410B
     * find_time : 2019-06-26
     * towerList : null
     * tower_id : null
     * tower_name : null
     * line_name : 1113桃建西T石线
     * type_name : 高速公路
     */

    private String id;
    private String task_id;
    private String line_id;
    private String towers;
    private String start_id;
    private String end_id;
    private String start_name;
    private String end_name;
    private Object tower_number;
    private String type_id;
    private String find_time;
    private Object towerList;
    private Object tower_id;
    private Object tower_name;
    private String line_name;
    private String type_name;

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

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getTowers() {
        return towers;
    }

    public void setTowers(String towers) {
        this.towers = towers;
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

    public Object getTower_number() {
        return tower_number;
    }

    public void setTower_number(Object tower_number) {
        this.tower_number = tower_number;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getFind_time() {
        return find_time;
    }

    public void setFind_time(String find_time) {
        this.find_time = find_time;
    }

    public Object getTowerList() {
        return towerList;
    }

    public void setTowerList(Object towerList) {
        this.towerList = towerList;
    }

    public Object getTower_id() {
        return tower_id;
    }

    public void setTower_id(Object tower_id) {
        this.tower_id = tower_id;
    }

    public Object getTower_name() {
        return tower_name;
    }

    public void setTower_name(Object tower_name) {
        this.tower_name = tower_name;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
