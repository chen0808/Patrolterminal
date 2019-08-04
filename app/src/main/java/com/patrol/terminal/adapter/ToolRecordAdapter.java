package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.EqToolsOut;

import java.util.ArrayList;
import java.util.List;

/**
 * 工器具
 */
public class ToolRecordAdapter extends BaseAdapter {
    private Context context;
    private List<EqToolsOut> list = new ArrayList<>();

    public ToolRecordAdapter(Context context) {
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_control_tool_division, parent, false);

            holder.mDivisonNo = convertView.findViewById(R.id.divison_no);
            holder.mDivisonName = convertView.findViewById(R.id.divison_name);
            holder.mDivisonModel = convertView.findViewById(R.id.divison_model);
            holder.mDivisonUnit = convertView.findViewById(R.id.divison_unit);
            holder.mDivisonNum = convertView.findViewById(R.id.divison_num);
            holder.mDivisonRemarks = convertView.findViewById(R.id.divison_remarks);
            convertView.setTag(holder);
        }

        if (list != null && list.get(position) != null) {
            holder.mDivisonNo.setText("" + (position + 1));
            holder.mDivisonName.setText(list.get(position).getEq_tools_name());
            holder.mDivisonModel.setText(list.get(position).getType());
            holder.mDivisonUnit.setText(list.get(position).getUnit());
            holder.mDivisonNum.setText(list.get(position).getTotal() + "");
            holder.mDivisonRemarks.setText(list.get(position).getRemarks());
            //借出时间
        }

        return convertView;
    }

    public List<EqToolsOut> getData() {
        return list;
    }

    public void setData(List<EqToolsOut> typeBeanList) {
        list = typeBeanList;
        notifyDataSetChanged();

    }

    static class ViewHolder {
        private TextView mDivisonNo;
        private TextView mDivisonName;
        private TextView mDivisonModel;
        private TextView mDivisonUnit;
        private TextView mDivisonNum;
        private TextView mDivisonRemarks;
    }
}