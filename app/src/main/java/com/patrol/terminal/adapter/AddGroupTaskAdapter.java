package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.GroupOfDayBean;
import com.patrol.terminal.utils.RxRefreshEvent;

import java.util.List;

public class AddGroupTaskAdapter extends BaseAdapter {
    private Context context;
    private List<GroupOfDayBean> lineTypeBeans;
    private int type=0;
    public AddGroupTaskAdapter(Context context, List<GroupOfDayBean> traceList) {
        this.context = context;
        this.lineTypeBeans = traceList;
    }

    @Override
    public int getCount() {

        if (type==0){
            if (lineTypeBeans.size()<4){
                return lineTypeBeans.size();
            }
            return 4;
        }else {
            return lineTypeBeans.size();
        }

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_group_task, parent, false);
            holder.itemTroubleName = (TextView) convertView.findViewById(R.id.add_group_task_name);
            holder.taskType = (TextView) convertView.findViewById(R.id.add_group_task_type);
            holder.itemTroubleCheck = (CheckBox) convertView.findViewById(R.id.add_group_task_check);
            convertView.setTag(holder);
        }
        GroupOfDayBean listBean = lineTypeBeans.get(position);

            holder.itemTroubleName.setText(listBean.getLine_name()+listBean.getName());


        holder.taskType.setText(listBean.getType_name());
        holder.itemTroubleCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.itemTroubleCheck.isChecked()){
//                    if ("1".equals(type_val)||"2".equals(type_val)){
//                        RxRefreshEvent.publish("addgroup@"+listBean.getType_val()+"@"+listBean.getId()+"@"+listBean.getLine_name()+listBean.getName()+listBean.getType_name()+"@"+"无");
//                    }else {
//                        RxRefreshEvent.publish("addgroup@"+listBean.getType_val()+"@"+listBean.getId()+"@"+listBean.getName()+listBean.getType_name()+"@"+listBean.getDone_time());
//                    }
//
//                }else {
//
//                    if ("1".equals(type_val)||"2".equals(type_val)){
//                        RxRefreshEvent.publish("deletegroup@"+listBean.getType_val()+"@"+listBean.getId());
//                    }else {
//                        RxRefreshEvent.publish("deletegroup@"+listBean.getType_val()+"@"+listBean.getId());
//                    }
                }

            }
        });
        return convertView;
    }

    public void setData(List<GroupOfDayBean> typeBeanList) {
        lineTypeBeans = typeBeanList;
        notifyDataSetChanged();

    }


    static class ViewHolder {
      private   TextView itemTroubleName;
        private   TextView taskType;
        private  CheckBox itemTroubleCheck;

    }

  public  void setType(int type){
        this.type=type;
        notifyDataSetChanged();

  }
}