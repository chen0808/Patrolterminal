package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.utils.RxRefreshEvent;

import java.util.List;

public class LineTypeAdapter extends BaseAdapter {
    private Context context;
    private List<DefectBean> lineTypeBeans;
    private int type=0;
    public LineTypeAdapter(Context context, List<DefectBean> traceList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trouble, parent, false);
            holder.itemTroubleName = (TextView) convertView.findViewById(R.id.item_trouble_name);
            holder.itemTroubleLevel = (TextView) convertView.findViewById(R.id.item_trouble_level);
            holder.itemTroublePrior = (TextView) convertView.findViewById(R.id.item_trouble_prior);
            holder.itemTroubleTime = (TextView) convertView.findViewById(R.id.item_trouble_time);
            holder.itemTroubleCheck = (CheckBox) convertView.findViewById(R.id.item_trouble_check);
            convertView.setTag(holder);
        }
        DefectBean listBean = lineTypeBeans.get(position);
        holder.itemTroubleName.setText(listBean.getStart_name()+"杆塔"+listBean.getContent());
        holder.itemTroubleTime.setText(listBean.getFind_time());
        holder.itemTroubleLevel.setText(listBean.getGrade_name());
        boolean ischeck = listBean.isIscheck();
        if (ischeck==true){
            holder.itemTroubleCheck.setChecked(true);
        }else {
            holder.itemTroubleCheck.setChecked(false);
        }
        if (listBean.getGrade_name()!=null){
        switch (listBean.getGrade_name()){
            case "一般":
                holder.itemTroublePrior.setText("低");
                break;
            case "严重":
                holder.itemTroublePrior.setText("中");
                break;
            case "危急":
                holder.itemTroublePrior.setText("高");
                break;

        }}
        holder.itemTroubleCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.itemTroubleCheck.isChecked()){
                    listBean.setIscheck(true);
                    RxRefreshEvent.publish("add@"+listBean.getId()+"@"+listBean.getFind_time()+"@"+listBean.getStart_name()+"@"+listBean.getContent());
                }else {
                    listBean.setIscheck(false);
                    RxRefreshEvent.publish("delete@"+listBean.getId());
                }

            }
        });
        return convertView;
    }

    public void setData(List<DefectBean> typeBeanList) {
        lineTypeBeans = typeBeanList;
        notifyDataSetChanged();

    }


    static class ViewHolder {
      private   TextView itemTroubleName;
        private   TextView itemTroubleLevel;
        private  TextView itemTroublePrior;
        private  TextView itemTroubleTime;
        private  CheckBox itemTroubleCheck;

    }

  public  void setType(int type){
        this.type=type;
        notifyDataSetChanged();

  }
}