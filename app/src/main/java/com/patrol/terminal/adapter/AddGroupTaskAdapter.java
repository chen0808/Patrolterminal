package com.patrol.terminal.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.GroupOfDayBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.StringUtil;

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

            holder.itemTroubleTower = (TextView) convertView.findViewById(R.id.add_group_task_tower);
            holder.itemTroubleName = (TextView) convertView.findViewById(R.id.add_group_task_name);
            holder.taskType = (TextView) convertView.findViewById(R.id.add_group_task_type);
            holder.itemTroubleCheck = (ImageView) convertView.findViewById(R.id.add_group_task_check);
            holder.item = (RelativeLayout) convertView.findViewById(R.id.personal_task_item);

            convertView.setTag(holder);
        }
        GroupOfDayBean listBean = lineTypeBeans.get(position);

        holder.itemTroubleName.setText(listBean.getLine_name());
        holder.itemTroubleTower.setText(listBean.getName());
        AdapterUtils.setText( holder.taskType,StringUtil.getTypeSign(listBean.getType_sign()));
        boolean check = listBean.isCheck();
        if (check==true){
            holder.itemTroubleCheck.setImageResource(R.mipmap.check_yes);
            }else {
            holder.itemTroubleCheck.setImageResource(R.mipmap.check_no);
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listBean.isCheck()){
                    listBean.setCheck(true);
                    holder.itemTroubleCheck.setImageResource(R.mipmap.check_yes);
                }else {
                    listBean.setCheck(false);
                    holder.itemTroubleCheck.setImageResource(R.mipmap.check_no);
                }
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
      private   TextView itemTroubleName,itemTroubleTower;
        private   TextView taskType;
        private  ImageView itemTroubleCheck;
        private  RelativeLayout item;

    }

  public  void setType(int type){
        this.type=type;
        notifyDataSetChanged();

  }
}