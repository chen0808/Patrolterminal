package com.patrol.terminal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.WeekTaskInfo;

import java.util.ArrayList;
import java.util.List;

public class WeekTaskAdapter extends BaseAdapter {
    private Context context;
    private List<WeekTaskInfo> list=new ArrayList<>();

    public WeekTaskAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_week_task, parent, false);

            holder.mTaskNo = convertView.findViewById(R.id.task_no);
            holder.mTaskContent = convertView.findViewById(R.id.task_content_tv);
            convertView.setTag(holder);
        }
        if (list != null && list.get(position) != null) {
            holder.mTaskNo.setText("" + (list.get(position).getNum() + 1));
            holder.mTaskContent.setText(list.get(position).getWeekContent());
        }

        return convertView;
    }

    public void setData(List<WeekTaskInfo> typeBeanList) {
        Log.w("linmeng", "typeBeanList:" + typeBeanList.size());
        list = typeBeanList;
        notifyDataSetChanged();

    }

    public List<WeekTaskInfo>  getData() {
        return list;
    }


    static class ViewHolder {
        private TextView mTaskNo;
        private TextView mTaskContent;
    }
}