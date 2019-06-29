package com.patrol.terminal.bean;

import java.io.Serializable;

public class TowerPart implements Serializable {
    private String name;
    private String name_start;
    private String name_end;
    private String start_id;
    private String end_id;
    private String tower_type;
    private int start_sort;
    private int end_sort;
    private int start_index;
    private int end_index;

    public int getStart_index() {
        return start_index;
    }

    public void setStart_index(int start_index) {
        this.start_index = start_index;
    }

    public int getEnd_index() {
        return end_index;
    }

    public void setEnd_index(int end_index) {
        this.end_index = end_index;
    }

    public int getStart_sort() {
        return start_sort;
    }

    public void setStart_sort(int start_sort) {
        this.start_sort = start_sort;
    }

    public int getEnd_sort() {
        return end_sort;
    }

    public void setEnd_sort(int end_sort) {
        this.end_sort = end_sort;
    }

    public TowerPart(String name, String start_id, String end_id, String tower_type) {
        this.name = name;
        this.start_id = start_id;
        this.end_id = end_id;
        this.tower_type = tower_type;
    }

    public String getName_start() {
        return name_start;
    }

    public void setName_start(String name_start) {
        this.name_start = name_start;
    }

    public String getName_end() {
        return name_end;
    }

    public void setName_end(String name_end) {
        this.name_end = name_end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTower_type() {
        return tower_type;
    }

    public void setTower_type(String tower_type) {
        this.tower_type = tower_type;
    }
}
