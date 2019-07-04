package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.utils.Constant;

import java.util.List;

public class GridViewAdapter4 extends android.widget.BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<String> mList;

    public GridViewAdapter4(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        //return mList.size() + 1;//因为最后多了一个添加图片的ImageView
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > Constant.MAX_SELECT_PIC_NUM) {
            return mList.size();
        } else {
            return count;
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
        convertView = inflater.inflate(R.layout.grid_item2, parent, false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv_pic);
        ImageView ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
        ivDelete.setVisibility(View.GONE);
        if (position < mList.size()) {
            //代表+号之前的需要正常显示图片
            Glide.with(mContext).load(mList.get(position)).into(iv);
        } else {
            iv.setImageResource(R.mipmap.zj);//最后一个显示加号图片
        }
        return convertView;
    }
}