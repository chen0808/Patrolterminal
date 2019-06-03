package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.EqTower;
import com.patrol.terminal.bean.GroupOfDayBean;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

public class GroupTaskSelectAdapter extends BaseAdapter {
    private Context context;
    private  List<GroupOfDayBean> lineTypeBeans;
    private  List<EqTower> eqTowers;
    private  List<String> towerList;
    private int start,end;

    public GroupTaskSelectAdapter(Context context, List<GroupOfDayBean> traceList) {
        this.context = context;
        this.lineTypeBeans = traceList;
    }

    public GroupTaskSelectAdapter(Context context, List<GroupOfDayBean> selectType, List<EqTower> eqTowers, List<String>  names) {
        this.context = context;
        this.lineTypeBeans = selectType;
        this.eqTowers=eqTowers;
        towerList=names;
    }

    @Override
    public int getCount() {
        return lineTypeBeans.size();
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
        GroupOfDayBean dangerBean = lineTypeBeans.get(position);

      holder.name.setText(dangerBean.getLine_name()+dangerBean.getName()+"任务");
        holder.time.setText(StringUtil.getTypeSign(dangerBean.getType_sign()));
        return convertView;
    }

    public void setData(List<GroupOfDayBean> typeBeanList) {
        lineTypeBeans = typeBeanList;
        notifyDataSetChanged();

    }
    static class ViewHolder {
        public TextView name;
        public TextView time;

    }


}