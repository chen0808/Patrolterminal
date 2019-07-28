package com.patrol.terminal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.DepUserBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DangerSubmitView extends RelativeLayout {


    @BindView(R.id.view_danger_name)
    TextView viewDangerName;
    @BindView(R.id.view_danger_content)
    TextView viewDangerContent;
    @BindView(R.id.view_danger_submit)
    RelativeLayout item;

    public DangerSubmitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_danger_submit, this, true);
        ButterKnife.bind(inflate);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DangerSubmitView);
        String string = array.getString(R.styleable.DangerSubmitView_danger_name);
        if (string!=null){
            setName(string);
        }
        array.recycle();
    }

    public DangerSubmitView(Context context, String name, String content) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_danger_submit, this, true);
        ButterKnife.bind(inflate);
        setName(name);
        setContent(content);
    }

    public void setName(String name){
        viewDangerName.setText(name);
    }
    public void setContent(String content){
        viewDangerContent.setText(content);
    }
}
