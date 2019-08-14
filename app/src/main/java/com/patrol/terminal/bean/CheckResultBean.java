package com.patrol.terminal.bean;

import android.graphics.Bitmap;

import java.util.List;

public class CheckResultBean {
    private long checkResultId;
    private int checkResult;   //0:正常   1:通过   2:待整改
    private String checkContent;
    private List<Bitmap> checkPics;

    public long getCheckResultId() {
        return checkResultId;
    }

    public void setCheckResultId(long checkResultId) {
        this.checkResultId = checkResultId;
    }

    public int getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(int checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public List<Bitmap> getCheckPics() {
        return checkPics;
    }

    public void setCheckPics(List<Bitmap> checkPics) {
        this.checkPics = checkPics;
    }

    @Override
    public String toString() {
        return "CheckResultBean{" +
                "checkResult=" + checkResult +
                ", checkContent='" + checkContent + '\'' +
                ", checkPics=" + checkPics +
                '}';
    }
}
