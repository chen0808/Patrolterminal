package com.patrol.terminal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.GetToPostCheckBean;

import java.util.ArrayList;
import java.util.List;

public class GetToPostCheckAdapter extends BaseAdapter {
    private Context context;
    private List<GetToPostCheckBean> list=new ArrayList<>();

    public GetToPostCheckAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
            return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_get_to_post_listview, parent, false);

            holder.mContentTv = convertView.findViewById(R.id.get_to_post_content_tv);
            holder.mCb = convertView.findViewById(R.id.get_to_post_content_cb);
            holder.mLineIv = convertView.findViewById(R.id.item_line_iv);
            convertView.setTag(holder);
        }
        if (list != null && list.get(position) != null) {
            holder.mContentTv.setText(list.get(position).getCheck_content());
        }
        holder.mCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.mCb.isChecked()) {
                    list.get(position).setStatus(1);
                } else {
                    list.get(position).setStatus(0);
                }
            }
        });
        Log.w("linmeng", "positon:" + position);
        if (position == (list.size() - 1)) {
            holder.mLineIv.setVisibility(View.GONE);
        }else {
            holder.mLineIv.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public void setData(List<GetToPostCheckBean> typeBeanList) {
        Log.w("linmeng", "typeBeanList:" + typeBeanList.size());
        list = typeBeanList;
        notifyDataSetChanged();

    }

    public List<GetToPostCheckBean>  getData() {
        return list;
    }


    static class ViewHolder {
        private TextView mContentTv;;
        private CheckBox mCb;
        private ImageView mLineIv;
    }
}