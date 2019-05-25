package com.patrol.terminal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.patrol.terminal.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalLineViewForTrain extends LinearLayout {
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.view4)
    View view4;

    public HorizontalLineViewForTrain(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View inflate = LayoutInflater.from(context).inflate(R.layout.horizontal_line_view_for_train, this, true);
        ButterKnife.bind(inflate);
    }

    public void setTrainTempStatus(String type) {
        switch (type) {
            case "0":  //待审核
                break;
            case "1":  //审核中
                view2.setBackgroundResource(R.drawable.green_oval);
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                break;
            case "2":  //已审核
                view2.setBackgroundResource(R.drawable.green_oval);
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                view3.setBackgroundResource(R.drawable.green_oval);
                line2.setBackgroundColor(getResources().getColor(R.color.line_green));
                break;
            case "3": //已分发
            case "4":
                view2.setBackgroundResource(R.drawable.green_oval);
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                view3.setBackgroundResource(R.drawable.green_oval);
                line2.setBackgroundColor(getResources().getColor(R.color.line_green));
                view4.setBackgroundResource(R.drawable.green_oval);
                line3.setBackgroundColor(getResources().getColor(R.color.line_green));
                break;

        }
    }
}
