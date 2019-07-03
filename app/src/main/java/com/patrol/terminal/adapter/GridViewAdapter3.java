package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.LocalPatrolDefectBean;
import com.patrol.terminal.bean.LocalPatrolDefectBean_Table;
import com.patrol.terminal.utils.Constant;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.File;
import java.util.List;

public class GridViewAdapter3 extends android.widget.BaseAdapter {

    private Context mContext;
    private final LocalPatrolDefectBean bean;
    private LayoutInflater inflater;
    private List<File> mList;

    public GridViewAdapter3(Context mContext, List<File> mList, String task_id, String patrol_id) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
        bean = SQLite.select().from(LocalPatrolDefectBean.class)
                .where(LocalPatrolDefectBean_Table.patrol_id.is(patrol_id))
                .and(LocalPatrolDefectBean_Table.task_id.is(task_id))
                .querySingle();
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
        if (position < mList.size()) {
            //代表+号之前的需要正常显示图片
            Glide.with(mContext).load(mList.get(position)).into(iv);
            ivDelete.setVisibility(View.VISIBLE);
        } else {
            iv.setImageResource(R.mipmap.zj);//最后一个显示加号图片
            ivDelete.setVisibility(View.GONE);
        }
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                String pics = bean.getPics();
                String newPics = "";
                String[] split = pics.split(";");
                for (int i = 0; i < split.length; i++) {
                    if (!split[i].equals(mList.get(position).getAbsolutePath())) {
                        newPics += split[i] + ";";
                    }
                }
                bean.setPics(newPics);
            }
        });
        return convertView;
    }
}