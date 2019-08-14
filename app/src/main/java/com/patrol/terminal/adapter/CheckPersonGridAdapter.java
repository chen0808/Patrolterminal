package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;

import java.util.List;

public class CheckPersonGridAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<String> mList;

    public CheckPersonGridAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.check_person_gird_item, parent, false);
        RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radio_button);
        radioButton.setText(mList.get(position));

        return convertView;
    }
}
