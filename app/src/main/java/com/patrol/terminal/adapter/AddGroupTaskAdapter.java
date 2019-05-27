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
                        RxRefreshEvent.publishGrooup(listBean);
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