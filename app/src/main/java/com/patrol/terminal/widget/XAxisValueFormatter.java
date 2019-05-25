package com.patrol.terminal.widget;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class XAxisValueFormatter implements IAxisValueFormatter {
    private String[] xStrs = new String[]{"35V", "110V", "500V"};

    @Override

    public String getFormattedValue(float value, AxisBase axis) {
        int position = (int) value;
        if (position >= 3) {
            position = 0;
        }
        return xStrs[position];
    }
}
