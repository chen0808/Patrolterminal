package com.patrol.terminal.bean;

import android.graphics.Bitmap;

public class SignBean {
    private static Bitmap bitmap;
    private static int index;

    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        SignBean.index = index;
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static void setBitmap(Bitmap bitmap) {
        SignBean.bitmap = bitmap;
    }
}
