package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.CardControlSafe;
import com.patrol.terminal.bean.ControlDepWorkInfo2;

import java.util.List;

public class ControlDepdapter2 extends BaseAdapter {
    private Context context;
    private List<CardControlSafe> list;
    private boolean isLook;
    public ControlDepdapter2(Context context, List<CardControlSafe> traceList) {
        this.context = context;
        this.list = traceList;
        this.isLook = isLook;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_control_card_guide, parent, false);

            holder.mDivisonNo = convertView.findViewById(R.id.divison_no);
            holder.mSafeContent = convertView.findViewById(R.id.divison_danger);
            holder.mDivisonContent = convertView.findViewById(R.id.divison_content);
            holder.mDivisonName = convertView.findViewById(R.id.divison_name);

            if (list != null && list.size() > 0) {
                if (list.get(position) != null) {
                    holder.mDivisonNo.setText("" + list.get(position).getDivisonNo());
                    holder.mDivisonContent.setText(list.get(position).getContent());
                    holder.mSafeContent.setText(list.get(position).getContent());
                    holder.mDivisonName.setText(list.get(position).getDuty_user_name());


                }
            }


            convertView.setTag(holder);
        }

        return convertView;
    }

    public void setData(List<CardControlSafe> typeBeanList) {
        list = typeBeanList;
        notifyDataSetChanged();

    }


    static class ViewHolder {
        private TextView mDivisonNo;
        private TextView mSafeContent;
        private TextView mDivisonContent;
        private TextView mDivisonName;

    }

}