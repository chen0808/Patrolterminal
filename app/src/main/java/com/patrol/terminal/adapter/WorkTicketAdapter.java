package com.patrol.terminal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrol.terminal.R;

import java.util.List;

public class WorkTicketAdapter extends BaseAdapter {

    private final List<String> data;

    public WorkTicketAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_choose, parent, false);
        TextView tvChoose = view.findViewById(R.id.tv_choose);
        tvChoose.setText(data.get(position));
        return view;
    }
}
