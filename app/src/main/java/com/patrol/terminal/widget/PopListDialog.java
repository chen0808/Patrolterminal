package com.patrol.terminal.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.DepBean;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.Utils;

import java.util.List;

public class PopListDialog extends PopupWindow {
    private View mainView;
   private List<DepBean> data;
private Activity paramActivity;
    public PopListDialog(Activity paramActivity,  int paramInt1, int paramInt2, List<DepBean> data){
        super(paramActivity);
        this.paramActivity=paramActivity;
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.popmenu_list, null);
        //全部
        LinearLayout  listView = ((LinearLayout)mainView.findViewById(R.id.pop_ll));
        for (int i = 0; i <data.size(); i++) {
            DepBean depBean = data.get(i);
            TextView textView=new TextView(paramActivity);
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dip2px(paramActivity, 40));
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setText(depBean.getName());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RxRefreshEvent.publish("getDep@"+depBean.getId());
                    dismiss();
                }
            });
            listView.addView(textView);
            if (i!=data.size()-1){
                View view=new View(paramActivity);
                view.setBackgroundColor(paramActivity.getResources().getColor(R.color.image_overlay_false));
                ViewGroup.LayoutParams viewpar=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2);
                view.setLayoutParams(viewpar);
                listView.addView(view);
            }
        }

        setContentView(mainView);
        //设置宽度
        setWidth(paramInt1);
        //设置高度
        setHeight(paramInt2);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }

}
