package com.patrol.terminal.bean;

import java.io.Serializable;

public class AddressBookLevel2 implements Serializable {
    private String content;
    private String contentBz;
    private String contentJob;
    private boolean tag;
    private int position;
    private String userId;

    public AddressBookLevel2(String content, String contentBz, String contentJob, boolean tag, String userId) {
        this.content = content;
        this.contentBz = contentBz;
        this.contentJob = contentJob;
        this.tag = tag;
        this.userId = userId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public String getContentBz() {
        return contentBz;
    }

    public void setContentBz(String contentBz) {
        this.contentBz = contentBz;
    }

    public String getContentJob() {
        return contentJob;
    }

    public void setContentJob(String contentJob) {
        this.contentJob = contentJob;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
