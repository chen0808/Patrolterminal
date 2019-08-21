package com.patrol.terminal.bean;

import android.graphics.Bitmap;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

public class CheckResultBean extends BaseModel{
    private long checkResultId;
    private int checkResult;   //0:正常   1:通过   2:待整改
    private String checkContent;
    private List<PictureInfo> checkPics;

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

    public List<PictureInfo> getCheckPics() {
        return checkPics;
    }

    public void setCheckPics(List<PictureInfo> checkPics) {
        this.checkPics = checkPics;
    }

    @Override
    public String toString() {
        return "CheckResultBean{" +
                "checkResultId=" + checkResultId +
                ", checkResult=" + checkResult +
                ", checkContent='" + checkContent + '\'' +
                ", checkPics=" + checkPics +
                '}';
    }


    public static class PictureInfo {
        private Bitmap bitmap;
        private int bitmapId;

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getBitmapId() {
            return bitmapId;
        }

        public void setBitmapId(int bitmapId) {
            this.bitmapId = bitmapId;
        }
    }
}
