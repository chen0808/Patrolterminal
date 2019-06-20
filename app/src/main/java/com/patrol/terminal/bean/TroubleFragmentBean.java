package com.patrol.terminal.bean;

import java.io.Serializable;

public class TroubleFragmentBean implements Serializable {

    /**
     * id : F6E2E224D06A425EA99E7933F528E2FA
     * task_id : null
     * line_id : 8D241E0F548A483B9F4330588CA087F3
     * towers : #001-#020
     * start_id : 5DCE3C08BD6E43C592AA7F202930D46B
     * end_id : D8E8289986694E12A6D9FF72B1FFAB5E
     * start_name : #001
     * end_name : #020
     * tower_number : 20
     * type_id : CE0954EF596447CA9458CC230234E01A
     * find_time : 2019-01-01
     * towerList : null
     * tower_id : null
     * tower_name : null
     * line_name : 1111兰西空一线
     * type_name : 防鸟害
     */

    private String id;
    private Object task_id;
    private String line_id;
    private String towers;
    private String start_id;
    private String end_id;
    private String start_name;
    private String end_name;
    private int tower_number;
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

    public Object getTask_id() {
        return task_id;
    }

    public void setTask_id(Object task_id) {
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

    public int getTower_number() {
        return tower_number;
    }

    public void setTower_number(int tower_number) {
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
