package com.patrol.terminal.bean;

import android.graphics.Bitmap;

import java.util.List;

public class CheckResultBean {
    private int checkResult;   //0:通过   1:不通过
    private String checkContent;
    private List<Bitmap> checkPics;

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
