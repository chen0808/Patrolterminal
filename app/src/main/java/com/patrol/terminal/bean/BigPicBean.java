package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BigPicBean implements Parcelable {
    private int positon;
    private List<String> pic;

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.positon);
        dest.writeStringList(this.pic);
    }

    public BigPicBean() {
    }

    protected BigPicBean(Parcel in) {
        this.positon = in.readInt();
        this.pic = in.createStringArrayList();
    }

    public static final Parcelable.Creator<BigPicBean> CREATOR = new Parcelable.Creator<BigPicBean>() {
        @Override
        public BigPicBean createFromParcel(Parcel source) {
            return new BigPicBean(source);
        }

        @Override
        public BigPicBean[] newArray(int size) {
            return new BigPicBean[size];
        }
    };
}
