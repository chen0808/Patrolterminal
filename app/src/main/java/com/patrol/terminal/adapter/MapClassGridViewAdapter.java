package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DepBean;
import com.patrol.terminal.bean.MapUserInfo;

import java.util.List;

public class MapClassGridViewAdapter extends android.widget.BaseAdapter {
    private Context mContext;
    private List<DepBean> mList;
    private LayoutInflater inflater;

    private int mClickPos = 0;

    public MapClassGridViewAdapter(Context mContext, List<DepBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    public  void setClickPos(int clickPos) {
        mClickPos = clickPos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mList.size() > 0) {
            return mList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.map_class_gv_item_layout, parent,false);
        TextView classNameTv = (TextView) convertView.findViewById(R.id.map_class_item_tv);
        classNameTv.setText(mList.get(position).getName());

        if (position == 0) {
            classNameTv.setBackgroundResource(R.drawable.btn_base_bg);
            classNameTv.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            classNameTv.setBackgroundResource(R.drawable.btn_grey_bg);
            classNameTv.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        if (position == mClickPos) {
            classNameTv.setBackgroundResource(R.drawable.btn_base_bg);
            classNameTv.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            classNameTv.setBackgroundResource(R.drawable.btn_grey_bg);
            classNameTv.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        return convertView;
    }
}