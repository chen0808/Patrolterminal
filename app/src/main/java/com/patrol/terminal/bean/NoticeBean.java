package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class NoticeBean implements Serializable {

    private String id;
    private String title;
    private String announce_user;
    private String content;
    private String end_time;
    private List<FileBean> tempImgList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnounce_user() {
        return announce_user;
    }

    public void setAnnounce_user(String announce_user) {
        this.announce_user = announce_user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public List<FileBean> getTempImgList() {
        return tempImgList;
    }

    public void setTempImgList(List<FileBean> tempImgList) {
        this.tempImgList = tempImgList;
    }
}
