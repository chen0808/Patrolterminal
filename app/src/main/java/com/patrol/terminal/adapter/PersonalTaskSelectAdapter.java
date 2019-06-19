package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.EqTower;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

public class PersonalTaskSelectAdapter extends BaseAdapter {
    private Context context;
    private  List<GroupTaskBean> list;
    private  List<EqTower> eqTowers;
    private  List<String> towerList;
    private int start,end;

    public PersonalTaskSelectAdapter(Context context, List<GroupTaskBean> traceList) {
        this.context = context;
        this.list = traceList;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_type, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.item_plan_device_name);
            holder.time = (TextView) convertView.findViewById(R.id.item_plan_time);
            convertView.setTag(holder);
        }
        GroupTaskBean bean = list.get(position);
        holder.name.setText(bean.getLine_name()+bean.getName()+"任务");
        AdapterUtils.setText(holder.time,StringUtil.getTypeSign(bean.getType_sign()));
        return convertView;
    }

    public void setData(List<GroupTaskBean> typeBeanList) {
        list = typeBeanList;
        notifyDataSetChanged();

    }
    static class ViewHolder {
        public TextView name;
        public TextView time;

    }


}