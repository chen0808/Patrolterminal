package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.utils.RxRefreshEvent;

import java.util.List;

public class AddDayAdapter extends BaseAdapter {
    private Context context;
    private List<DayOfWeekBean> lineTypeBeans;
    private int type=0;
    public AddDayAdapter(Context context, List<DayOfWeekBean> traceList) {
        this.context = context;
        this.lineTypeBeans = traceList;
    }

    @Override
    public int getCount() {

        if (type==0){
            if (lineTypeBeans.size()<6){
                return lineTypeBeans.size();
            }else {
                return 6;
            }
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_tower, parent, false);
            holder.towerNmae = (TextView) convertView.findViewById(R.id.item_add_tower_name);
            holder.towerCheck = (CheckBox) convertView.findViewById(R.id.item_add_tower_check);
            convertView.setTag(holder);
        }
        DayOfWeekBean listBean = lineTypeBeans.get(position);
        String planName=listBean.getLine_name()+listBean.getName();
        holder.towerNmae.setText(planName);
        holder.towerCheck.setChecked(false);
        holder.towerCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.towerCheck.isChecked()){
                    RxRefreshEvent.publish("add@"+listBean.getTowers_id()+"@"+listBean.getName()+"@"+listBean.getWeek_line_id());
                }else {
                    RxRefreshEvent.publish("delete@"+listBean.getTowers_id());
                }

            }
        });
        return convertView;
    }

    public void setData(List<DayOfWeekBean> typeBeanList) {
        lineTypeBeans = typeBeanList;
        notifyDataSetChanged();

    }


    static class ViewHolder {
      private   TextView towerNmae;
        private   CheckBox towerCheck;

    }

  public  void setType(int type){
        this.type=type;
        notifyDataSetChanged();

  }
}