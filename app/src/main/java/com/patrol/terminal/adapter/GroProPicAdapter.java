package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;

import java.util.List;

public class GroProPicAdapter extends android.widget.BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<String> mList;

    public GroProPicAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {

        return mList.size();
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
        convertView = inflater.inflate(R.layout.grid_item3, parent, false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv_pic);
        ImageView ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
        ivDelete.setVisibility(View.GONE);

            //代表+号之前的需要正常显示图片
            Glide.with(mContext).load(mList.get(position)).into(iv);

        return convertView;
    }
}