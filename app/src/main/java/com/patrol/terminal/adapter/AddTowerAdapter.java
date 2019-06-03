package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.WeekOfMonthBean;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

public class AddTowerAdapter extends BaseAdapter {
    private Context context;
    private List<WeekOfMonthBean> lineTypeBeans;
    private int type=0;
    public AddTowerAdapter(Context context, List<WeekOfMonthBean> traceList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_tower, parent, false);
            holder.towerNmae = (TextView) convertView.findViewById(R.id.item_add_tower_name);
            holder.towerCheck = (CheckBox) convertView.findViewById(R.id.item_add_tower_check);
            holder.towerType = (TextView) convertView.findViewById(R.id.item_add_tower_type);
            convertView.setTag(holder);
        }


        holder.towerNmae.setText(lineTypeBeans.get(position).getLine_name() + lineTypeBeans.get(position).getTowers_name()/*lineTypeBeans.get(position).getTowers()*/);
        holder.towerType.setText(StringUtil.getTypeSign(lineTypeBeans.get(position).getType_sign()));  //TODO
        WeekOfMonthBean listBean = lineTypeBeans.get(position);
     holder.towerCheck.setChecked(false);
        holder.towerCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.towerCheck.isChecked()){

                        RxRefreshEvent.publish("add@"+listBean.getTowers_id()+"@"+listBean.getLine_id()+"@"+listBean.getLine_name()+"@"+listBean.getTowers_name()+"@"+listBean.getType_id()+"@"+listBean.getType_name()+"@"+listBean.getType_sign()+"@"+listBean.getMonth_line_id()+"@"+listBean.getDefect_id());

                }else {
                        RxRefreshEvent.publish("delete@"+listBean.getTowers_id());

                }

            }
        });
        return convertView;
    }

    public void setData(List<WeekOfMonthBean> typeBeanList) {
        lineTypeBeans = typeBeanList;
        notifyDataSetChanged();

    }


    static class ViewHolder {
      private   TextView towerNmae;
        private   CheckBox towerCheck;
        private TextView towerType;

    }

  public  void setType(int type){
        this.type=type;
        notifyDataSetChanged();

  }
}