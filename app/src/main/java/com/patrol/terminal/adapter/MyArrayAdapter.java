package com.patrol.terminal.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.patrol.terminal.R;

public class MyArrayAdapter extends ArrayAdapter {
    private Activity mContext = null; // 上下文环境
    private int mResourceId; // 列表项布局资源ID
    private String[] mItems; // 列表内容数组

    public MyArrayAdapter(Activity context, int resId, String[] items){
        super(context, resId, items);
        // 保存参数
        mContext = context;
        mResourceId = resId;
        mItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 获取LayoutInflater对象
        LayoutInflater inflater = mContext.getLayoutInflater();
        // 装载列表项视图
        View itemView = inflater.inflate(mResourceId, null);

        // 获取列表项之组件
        TextView contentTv = (TextView) itemView.findViewById(R.id.content_tv);

        // 取出要显示的数据
        String content = mItems[position].trim();

        // 给TextView设置显示值
        contentTv.setText(content);
        // 返回列表项视图
        return itemView;
    }

}
