package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class WeekTaskInfo implements Parcelable {

    private int num;
    private String weekContent;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getWeekContent() {
        return weekContent;
    }

    public void setWeekContent(String weekContent) {
        this.weekContent = weekContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.num);
        dest.writeString(this.weekContent);
    }

    public WeekTaskInfo() {
    }

    protected WeekTaskInfo(Parcel in) {
        this.num = in.readInt();
        this.weekContent = in.readString();
    }

    public static final Parcelable.Creator<WeekTaskInfo> CREATOR = new Parcelable.Creator<WeekTaskInfo>() {
        @Override
        public WeekTaskInfo createFromParcel(Parcel source) {
            return new WeekTaskInfo(source);
        }

        @Override
        public WeekTaskInfo[] newArray(int size) {
            return new WeekTaskInfo[size];
        }
    };
}
