package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.bean.EqTower;

import java.util.List;

public class MonthTypeSelectAdapter extends BaseAdapter {
    private Context context;
    private  List<DefectBean> lineTypeBeans;
    private  List<EqTower> eqTowers;
    private  List<String> towerList;
    private int start,end;

    public MonthTypeSelectAdapter(Context context, List<DefectBean> traceList) {
        this.context = context;
        this.lineTypeBeans = traceList;
    }

    public MonthTypeSelectAdapter(Context context, List<DefectBean> selectType, List<EqTower> eqTowers, List<String>  names) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_type_new, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.item_plan_device_name);
            holder.time = (TextView) convertView.findViewById(R.id.item_plan_time);
            convertView.setTag(holder);
        }
        DefectBean dangerBean = lineTypeBeans.get(position);
        switch (dangerBean.getType()){
            case 0:
                holder.name.setText(dangerBean.getContent());
                break;
            case 1:
//                holder.name.setText(dangerBean.getTask_id()+"隐患处理");
                holder.name.setText(dangerBean.getLine_name()+dangerBean.getStart_id()+"杆塔"+dangerBean.getContent()+"隐患处理");
                holder.time.setText(dangerBean.getFind_time());
                break;

            case 2:
//                holder.name.setText(dangerBean.getTask_id()+"缺陷消除");
                holder.name.setText(dangerBean.getLine_name()+dangerBean.getStart_id()+"杆塔"+dangerBean.getContent()+"缺陷消除");
                holder.time.setText(dangerBean.getFind_time());
                break;
        }

        holder.time.setText(dangerBean.getFind_time());
        return convertView;
    }

    public void setData(List<DefectBean> typeBeanList) {
        lineTypeBeans = typeBeanList;
        notifyDataSetChanged();

    }
    static class ViewHolder {
        public TextView name;
        public TextView time;

    }


}