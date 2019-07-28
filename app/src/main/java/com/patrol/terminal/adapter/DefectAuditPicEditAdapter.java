package com.patrol.terminal.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class DefectAuditPicEditAdapter extends android.widget.BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;
    private String in_status;

    public DefectAuditPicEditAdapter(Context mContext, List<String> mList, String in_status) {
        this.mContext = mContext;
        this.mList = mList;
        this.in_status = in_status;
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
        convertView = inflater.inflate(R.layout.grid_item2, parent,false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv_pic);
        ImageView ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);

        //代表+号之前的需要正常显示图片
        if (mList.get(position) != null && !mList.get(position).equals("")) {
            if(mList.get(position).startsWith("http") || mList.get(position).startsWith("https")){
                Glide.with(mContext).load(mList.get(position)).into(iv);
            } else {
                Bitmap bitmap = getLoacalBitmap(mList.get(position));
                iv .setImageBitmap(bitmap);
            }
            ivDelete.setVisibility(View.VISIBLE);
        } else {
            iv.setImageResource(R.mipmap.zj);//最后一个显示加号图片
            ivDelete.setVisibility(View.GONE);
        }

        if(in_status.equals("3") || in_status.equals("1")){
            ivDelete.setVisibility(View.GONE);
        }

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String pics = bean.getPics();
//                String newPics = "";
//                String[] split = pics.split(";");
//                if (mList.size() > 0) {
//                    for (int i = 0; i < split.length; i++) {
//                        if (!split[i].equals(mList.get(position).getAbsolutePath()) && !mList.get(position).equals("")) {
//                            newPics += split[i] + ";";
//                        }
//                    }
//                }
//                bean.setPics(newPics);
//                bean.update();
                mList.remove(position);
                if(mList.size() == 0){
                    mList.add("");
                }
                notifyDataSetChanged();
            }
        });

//        Glide.with(mContext).clear(iv);
//        if(mList.get(position).equals("")){
//            Glide.with(mContext).load(R.mipmap.zj).into(iv);
//        } else {
//            if(mList.get(position).startsWith("http") || mList.get(position).startsWith("https")){
//                Glide.with(mContext).load(mList.get(position)).into(iv);
//            } else {
//                Bitmap bitmap = getLoacalBitmap(mList.get(position));
//                iv .setImageBitmap(bitmap);
//            }
//        }
        return convertView;
    }

    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}