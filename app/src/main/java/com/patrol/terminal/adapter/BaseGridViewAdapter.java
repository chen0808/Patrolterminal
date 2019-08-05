package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DepPersonalBean;

import java.util.List;

public class BaseGridViewAdapter extends android.widget.BaseAdapter {

    private Context mContext;
    private List<DepPersonalBean.UserListBean> mList;
    private LayoutInflater inflater;

    public BaseGridViewAdapter(Context mContext, List<DepPersonalBean.UserListBean> mList) {
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

    public void setData(List<DepPersonalBean.UserListBean> mList) {
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

        String name = mList.get(position).getName();
        nameTv.setText(name);

        Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.map_user_icon)).into(nameIv);


//        if (position < mList.size()) {
//            //代表+号之前的需要正常显示图片
//            String picUrl = mList.get(position); //图片路径
//            Glide.with(mContext).load(picUrl).into(iv);
//        } else {
//            iv.setImageResource(R.mipmap.zj);//最后一个显示加号图片
//        }
        return convertView;
    }
}