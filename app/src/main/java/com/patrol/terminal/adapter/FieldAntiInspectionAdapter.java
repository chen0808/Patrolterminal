package com.patrol.terminal.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.FieldAntiInspectionBean;

import java.util.ArrayList;
import java.util.List;

public class FieldAntiInspectionAdapter extends BaseAdapter {
    private Context context;
    private List<FieldAntiInspectionBean> list=new ArrayList<>();

    public FieldAntiInspectionAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_field_anti_inspection_listview, parent, false);

            holder.mContentTv = convertView.findViewById(R.id.item_content_tv);
            holder.mLevelTv = convertView.findViewById(R.id.level_tv);
            holder.mScoreTv = convertView.findViewById(R.id.score_tv);
            holder.mCheckPersonEt = convertView.findViewById(R.id.check_person_et);
            holder.mLineIv = convertView.findViewById(R.id.item_line_iv);
            convertView.setTag(holder);
        }
        if (list != null && list.get(position) != null) {
            holder.mContentTv.setText(list.get(position).getCheck_content());
            holder.mLevelTv.setText((list.get(position).getIllegal_type()));
            holder.mScoreTv.setText(list.get(position).getScore());
            holder.mCheckPersonEt.setText(list.get(position).getCheck_user());
            holder.mCheckPersonEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    list.get(position).setCheck_user(s.toString());
                }
            });
        }
        Log.w("linmeng", "positon:" + position);
        if (position == (list.size() - 1)) {
            holder.mLineIv.setVisibility(View.GONE);
        }else {
            holder.mLineIv.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public void setData(List<FieldAntiInspectionBean> typeBeanList) {
        Log.w("linmeng", "typeBeanList:" + typeBeanList.size());
        list = typeBeanList;
        notifyDataSetChanged();

    }

    public List<FieldAntiInspectionBean>  getData() {
        return list;
    }


    static class ViewHolder {
        private TextView mContentTv;;
        private TextView mLevelTv;
        private TextView mScoreTv;
        private EditText mCheckPersonEt;
        private ImageView mLineIv;
    }
}