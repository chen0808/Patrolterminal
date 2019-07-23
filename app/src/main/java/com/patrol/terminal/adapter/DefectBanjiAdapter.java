package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.BanjiBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 缺陷 班级
 */
public class DefectBanjiAdapter extends BaseAdapter {
    ViewHolder holder = null;
    private Context context;
    private List<BanjiBean> bajiList = new ArrayList<>();
    private int indexPosition = -1;

    public DefectBanjiAdapter(Context context, List<BanjiBean> list) {
        this.context = context;
        this.bajiList = list;

    }

    @Override
    public int getCount() {
        return bajiList.size();
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

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_defect_banji, parent, false);
            holder.tvName = convertView.findViewById(R.id.tv_banji);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(bajiList.get(position).getName());

        if (indexPosition == position) {
            holder.tvName.setBackgroundColor(context.getResources().getColor(R.color.base_status_bar));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.tvName.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.black_gray));
        }

        return convertView;
    }

    public void setIndexPosition(int index) {
        indexPosition = index;
        notifyDataSetChanged();
    }


    class ViewHolder {
        private TextView tvName;
    }
}