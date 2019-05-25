package com.patrol.terminal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.WeekTaskToPersonBean;

import java.util.ArrayList;
import java.util.List;

public class WeekTaskZzPublishAdapter extends BaseAdapter {
    private Context context;
    private List<WeekTaskToPersonBean> list=new ArrayList<>();

    public WeekTaskZzPublishAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_zz_publish_listview, parent, false);

            holder.mNumTv = convertView.findViewById(R.id.num_tv);
            holder.mTaskContentTv = convertView.findViewById(R.id.task_content_tv);
            holder.mNameTv = convertView.findViewById(R.id.send_person_tv);
            convertView.setTag(holder);
        }
        if (list != null && list.get(position) != null) {
           holder.mNumTv.setText(list.get(position).getNum() + "");
            holder.mTaskContentTv.setText(list.get(position).getContent());
            holder.mNameTv.setText(list.get(position).getUserName());
        }

        return convertView;
    }

    public void setData(List<WeekTaskToPersonBean> typeBeanList) {
        Log.w("linmeng", "typeBeanList:" + typeBeanList.size());
        list = typeBeanList;
        notifyDataSetChanged();

    }

    public List<WeekTaskToPersonBean>  getData() {
        return list;
    }


    static class ViewHolder {
        private TextView mNumTv;;
        private TextView mTaskContentTv;
        private TextView mNameTv;
    }
}