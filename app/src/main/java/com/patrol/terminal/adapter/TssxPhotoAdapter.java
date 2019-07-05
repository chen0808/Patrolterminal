package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.utils.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TssxPhotoAdapter extends android.widget.BaseAdapter {

    private Context mContext;
//    private final LocalPatrolDefectBean bean;
    private LayoutInflater inflater;
    private List<String> mList = new ArrayList<>();
    private ViewHolder holder;
    private int tssxId;
    private onDelPhotoAdapter onDelPhoto;

    public TssxPhotoAdapter(Context context) {
        this.mContext = context;
    }

    public TssxPhotoAdapter(Context context, List<String> mList) {
        this.mContext = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
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
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item2, parent, false);
            holder.iv = convertView.findViewById(R.id.iv_pic);
            holder.ivDelete = convertView.findViewById(R.id.iv_delete);
            convertView.setTag(holder);
        }

        if (position != mList.size()) {//代表+号之前的需要正常显示图片
            Glide.with(mContext).load(new File(mList.get(position))).into(holder.iv);
            holder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            holder.iv.setImageResource(R.drawable.item_photo_add);//最后一个显示加号图片
            holder.ivDelete.setVisibility(View.GONE);
        }


        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDelPhoto.onDelPhotoAdapterStr(mList.get(position));
                mList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public interface onDelPhotoAdapter{
        void onDelPhotoAdapterStr(String delPhotoStr);
    }

    public void setDelItem(onDelPhotoAdapter delPhoto)
    {
        this.onDelPhoto = delPhoto;
    }


    public void setNotifyDataSetChanged(List<String> list)
    {
        this.mList = list;
        notifyDataSetChanged();
    }

    class ViewHolder {
        private ImageView iv;
        private ImageView ivDelete;
    }
}