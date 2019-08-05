package com.patrol.terminal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DepPersonalBean;
import com.patrol.terminal.bean.MapUserInfo;

import java.util.List;

public class GridViewAdapter5 extends android.widget.BaseAdapter {

    private Context mContext;
    private List<MapUserInfo> mList;
    private LayoutInflater inflater;

    public GridViewAdapter5(Context mContext, List<MapUserInfo> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (mList.size() > 0) {
            return mList.size();
        } else {
            return 0;
        }
    }

    public void setData(List<MapUserInfo> mList) {
        this.mList = mList;
        notifyDataSetChanged();
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
        convertView = inflater.inflate(R.layout.map_name_gridview_item, parent,false);
        ImageView nameIv = (ImageView) convertView.findViewById(R.id.name_item_iv);
        TextView nameTv = (TextView) convertView.findViewById(R.id.name_item_tv);

        String name = mList.get(position).getUserName();
        nameTv.setText(name);

        int userImgId = mList.get(position).getUserImgId();
        Log.w("linmeng", "userImgId:" + userImgId);

        Glide.with(mContext).load(mContext.getResources().getDrawable(mList.get(position).getUserImgId())).into(nameIv);


        return convertView;
    }
}