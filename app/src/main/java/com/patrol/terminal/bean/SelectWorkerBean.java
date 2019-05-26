package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SelectWorkerBean implements Parcelable {

    private List<SelectUserInfo> userInfos;

    public List<SelectUserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<SelectUserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public static class SelectUserInfo implements Parcelable {

        private String userId;
        private String userName;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public SelectUserInfo() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.userId);
            dest.writeString(this.userName);
        }

        protected SelectUserInfo(Parcel in) {
            this.userId = in.readString();
            this.userName = in.readString();
        }

        public static final Creator<SelectUserInfo> CREATOR = new Creator<SelectUserInfo>() {
            @Override
            public SelectUserInfo createFromParcel(Parcel source) {
                return new SelectUserInfo(source);
            }

            @Override
            public SelectUserInfo[] newArray(int size) {
                return new SelectUserInfo[size];
            }
        };
    }

    public SelectWorkerBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.userInfos);
    }

    protected SelectWorkerBean(Parcel in) {
        this.userInfos = in.createTypedArrayList(SelectUserInfo.CREATOR);
    }

    public static final Creator<SelectWorkerBean> CREATOR = new Creator<SelectWorkerBean>() {
        @Override
        public SelectWorkerBean createFromParcel(Parcel source) {
            return new SelectWorkerBean(source);
        }

        @Override
        public SelectWorkerBean[] newArray(int size) {
            return new SelectWorkerBean[size];
        }
    };
}
