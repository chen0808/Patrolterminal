package com.patrol.terminal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.patrol.terminal.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalLineView extends LinearLayout {
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
    @BindView(R.id.tv3)
    TextView tv3;

    public HorizontalLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View inflate = LayoutInflater.from(context).inflate(R.layout.horizontal_line_view, this, true);
        ButterKnife.bind(inflate);
    }

    public void setStatus(String type) {
        switch (type) {
            case "0":
            case "4":
                view1.setBackgroundResource(R.drawable.line_point_0);
                line1.setBackgroundColor(getResources().getColor(R.color.color_69));
                view2.setBackgroundResource(R.drawable.gray_oval);
                line2.setBackgroundColor(getResources().getColor(R.color.color_69));
                view3.setBackgroundResource(R.drawable.gray_oval);
                line3.setBackgroundColor(getResources().getColor(R.color.color_69));
                view4.setBackgroundResource(R.drawable.gray_oval);
                break;
            case "1":
                view1.setBackgroundResource(R.drawable.line_point_1);
                line1.setBackgroundColor(getResources().getColor(R.color.line_point_1));
                view2.setBackgroundResource(R.drawable.line_point_1);
                line2.setBackgroundColor(getResources().getColor(R.color.color_69));
                view3.setBackgroundResource(R.drawable.gray_oval);
                line3.setBackgroundColor(getResources().getColor(R.color.color_69));
                view4.setBackgroundResource(R.drawable.gray_oval);
                break;
            case "2":
                view1.setBackgroundResource(R.drawable.line_point_2);
                line1.setBackgroundColor(getResources().getColor(R.color.line_point_2));
                view2.setBackgroundResource(R.drawable.line_point_2);
                line2.setBackgroundColor(getResources().getColor(R.color.line_point_2));
                view3.setBackgroundResource(R.drawable.line_point_2);
                line3.setBackgroundColor(getResources().getColor(R.color.color_69));
                view4.setBackgroundResource(R.drawable.gray_oval);
                break;
            case "3":
                view1.setBackgroundResource(R.drawable.line_point_3);
                view1.setBackgroundResource(R.drawable.line_point_3);
                line1.setBackgroundColor(getResources().getColor(R.color.line_point_3));
                view2.setBackgroundResource(R.drawable.line_point_3);
                line2.setBackgroundColor(getResources().getColor(R.color.line_point_3));
                view3.setBackgroundResource(R.drawable.line_point_3);
                line3.setBackgroundColor(getResources().getColor(R.color.line_point_3));
                view4.setBackgroundResource(R.drawable.line_point_3);
                break;

        }
    }

    public void setWeekState(String type) {
        tv3.setVisibility(GONE);
        view3.setVisibility(GONE);
        line2.setVisibility(GONE);
        switch (type) {
            case "0":
                break;
            case "1":
                view2.setBackgroundResource(R.drawable.green_oval);
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                break;
            case "2":
            case "3":
                view2.setBackgroundResource(R.drawable.green_oval);
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                view4.setBackgroundResource(R.drawable.green_oval);
                line3.setBackgroundColor(getResources().getColor(R.color.line_green));
                break;
            case "4":
                view2.setBackgroundResource(R.drawable.green_oval);
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                view3.setBackgroundResource(R.drawable.green_oval);
                line2.setBackgroundColor(getResources().getColor(R.color.line_green));
                view4.setBackgroundResource(R.drawable.green_oval);
                line3.setBackgroundColor(getResources().getColor(R.color.line_green));
                break;
            case "5":
                view2.setBackgroundResource(R.drawable.green_oval);
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                view3.setBackgroundResource(R.drawable.green_oval);
                line2.setBackgroundColor(getResources().getColor(R.color.line_green));
                view4.setBackgroundResource(R.drawable.green_oval);
                line3.setBackgroundColor(getResources().getColor(R.color.line_green));
//                view5.setBackgroundResource(R.drawable.green_oval);
//                line4.setBackgroundColor(getResources().getColor(R.color.line_green));
                break;

        }
    }

    public void setOverStatus(String type) {

        switch (type) {
            case "0":
                break;
            case "1":
                view2.setBackgroundResource(R.drawable.green_oval);
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                break;
            case "2":
                /*case "5":*/
                view2.setBackgroundResource(R.drawable.green_oval);
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                view3.setBackgroundResource(R.drawable.green_oval);
                line2.setBackgroundColor(getResources().getColor(R.color.line_green));
                break;
            case "3":
                view2.setBackgroundResource(R.drawable.green_oval);
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                view3.setBackgroundResource(R.drawable.green_oval);
                line2.setBackgroundColor(getResources().getColor(R.color.line_green));
                view4.setBackgroundResource(R.drawable.green_oval);
                line3.setBackgroundColor(getResources().getColor(R.color.line_green));
                break;
         /*   case "4":
                view2.setBackgroundResource(R.drawable.green_oval);
                line1.setBackgroundColor(getResources().getColor(R.color.line_green));
                view3.setBackgroundResource(R.drawable.green_oval);
                line2.setBackgroundColor(getResources().getColor(R.color.line_green));
                view4.setBackgroundResource(R.drawable.green_oval);
                line3.setBackgroundColor(getResources().getColor(R.color.line_green));
                view5.setBackgroundResource(R.drawable.green_oval);
                line4.setBackgroundColor(getResources().getColor(R.color.line_green));
                break;*/

        }
    }

}
