package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.WeekReceiveBean;

import org.w3c.dom.Text;

import java.util.List;

public class WeekReceiveAdapter extends BaseAdapter {
    private Context context;
    private List<WeekReceiveBean> weekReceiveBeans;
    public WeekReceiveAdapter(Context context, List<WeekReceiveBean> traceList) {
        this.context = context;
        this.weekReceiveBeans = traceList;
    }

    @Override
    public int getCount() {
        if (weekReceiveBeans != null) {
            return weekReceiveBeans.size();
        }
        return 0;
    }

    @Override
    public WeekReceiveBean getItem(int position) {
        return weekReceiveBeans.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_week_receiver, parent, false);
            holder.workLines = (TextView) convertView.findViewById(R.id.work_lines_tv);
            holder.workContent = (TextView)  convertView.findViewById(R.id.work_content_tv);
            holder.receiver = (TextView)  convertView.findViewById(R.id.receiver_tv);
            convertView.setTag(holder);
        }

        WeekReceiveBean item = weekReceiveBeans.get(position);
        holder.workLines.setText(item.getWorkLines());
        holder.workContent.setText(item.getWorkContent());
        holder.receiver.setText(item.getReceiver());

//        PlanTypeBean listBean = lineTypeBeans.get(position);
//        holder.name.setText(listBean.getName());
//        boolean ischeck = listBean.isIscheck();
//        holder.ischeck=ischeck;
//        if ( holder.ischeck){
//            holder.name.setText(context.getResources().getColor(R.color.color_69));
//        }else {
//            holder.name.setText(context.getResources().getColor(R.color.tv_gray));
//        }
//        holder.name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.ischeck){
//                    RxRefreshEvent.publish("add@"+listBean.getId()+"@"+listBean.getVal()+"@"+listBean.getName());
//                }else {
//                    holder.name.setText(context.getResources().getColor(R.color.tv_gray));
//                    RxRefreshEvent.publish("delete@"+listBean.getId()+"@"+listBean.getVal()+"@"+listBean.getName());
//                }
//
//            }
//        });
        return convertView;
    }

    public void setData(List<WeekReceiveBean> weekReceiveList) {
        weekReceiveBeans = weekReceiveList;
        notifyDataSetChanged();
    }


    static class ViewHolder {
      private   TextView workLines;
      private   TextView workContent;
      private TextView receiver;
    }

  public  void setType(int type){
        notifyDataSetChanged();

  }
}