package com.patrol.terminal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bin.david.form.core.SmartTable;

/**
 * 处理表格的滑动冲突
 */
public class CustomTable extends SmartTable {
    public CustomTable(Context context) {
        super(context);
    }

    public CustomTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(false);
        return false;
    }
}
