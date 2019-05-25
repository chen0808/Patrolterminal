package com.patrol.terminal.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PickerUtils {

    public static String initDate() {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(currentTimeMillis);
    }

    public static void showDate(Context context, TextView tv) {
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                tv.setText(sdf.format(date));
            }
        }).build();
        pvTime.show(tv);
    }

    public static void showTime(Context context, TextView tv) {
        boolean[] bool = {false, false, false, true, true, true};
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                tv.setText(sdf.format(date));
            }
        }).setType(bool).build();
        pvTime.show(tv);
    }
}
