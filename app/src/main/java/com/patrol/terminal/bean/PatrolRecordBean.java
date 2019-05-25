package com.patrol.terminal.bean;

import java.io.Serializable;

public class PatrolRecordBean implements Serializable {

    /**
     * id : C6C78BAAF4AC4049AEEE80DCC49046D4
     * card_user : zhangsan
     * lon : 12.33
     * lat : 133
     * card_time : 2019-04-04 17:00:00
     * task_id : null
     * image_url : F:\lzproject2012031220134655.jpg
     * comments : 测试
     */

    private String id;
    private String card_user;
    private double lon;
    private double lat;
    private String card_time;
    private Object task_id;
    private String image_url;
    private String comments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard_user() {
        return card_user;
    }

    public void setCard_user(String card_user) {
        this.card_user = card_user;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCard_time() {
        return card_time;
    }

    public void setCard_time(String card_time) {
        this.card_time = card_time;
    }

    public Object getTask_id() {
        return task_id;
    }

    public void setTask_id(Object task_id) {
        this.task_id = task_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
