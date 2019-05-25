package com.patrol.terminal.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.TicketFirstGround;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;

import java.util.List;

public class GroundLineAdapter extends BaseAdapter {
    private Context context;
    private List<TicketFirstGround> mGroundLineList;

    public GroundLineAdapter(Context context, List<TicketFirstGround> groundLineList) {
        this.context = context;
        this.mGroundLineList = groundLineList;
    }

    @Override
    public int getCount() {
        if (mGroundLineList != null) {
            return mGroundLineList.size();
        }
        return 0;
    }

    @Override
    public TicketFirstGround getItem(int position) {
        return mGroundLineList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ground_line, parent, false);
            holder.hangLotionTv = (EditText) convertView.findViewById(R.id.hang_location_tv);
            holder.groundLineNumberTv = (EditText) convertView.findViewById(R.id.ground_line_number_tv);
            holder.hungTimeTv = (TextView) convertView.findViewById(R.id.hung_time_tv);
            holder.demolitionTimeTv = (TextView) convertView.findViewById(R.id.demolition_time_tv);
            holder.hungTimeCb = (CheckBox) convertView.findViewById(R.id.hung_time_checkbox);
            holder.demolitionTimeCb = (CheckBox) convertView.findViewById(R.id.demolition_time_checkbox);
            convertView.setTag(holder);
        }

        TicketFirstGround item = getItem(position);
        holder.hangLotionTv.setText(item.getInstall_site());
        holder.groundLineNumberTv.setText(item.getGround_id());
        holder.hungTimeTv.setText(item.getInstall_time());
        holder.demolitionTimeTv.setText(item.getRemove_time());

        holder.hangLotionTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setInstall_site(holder.hangLotionTv.getText().toString());
            }
        });

        holder.groundLineNumberTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setGround_id(holder.groundLineNumberTv.getText().toString());
            }
        });

        holder.hungTimeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    holder.hungTimeTv.setText(DateUatil.getCurrTime());
                } else {
                    holder.hungTimeTv.setText(Constant.WORK_TICKET_TIME);
                }
                item.setInstall_time(holder.hungTimeTv.getText().toString());
            }
        });

        holder.demolitionTimeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    holder.demolitionTimeTv.setText(DateUatil.getCurrTime());
                } else {
                    holder.demolitionTimeTv.setText(Constant.WORK_TICKET_TIME);
                }
                item.setRemove_time(holder.demolitionTimeTv.getText().toString());
            }
        });
        return convertView;
    }

    public void setData(List<TicketFirstGround> groundLineList) {
        mGroundLineList = groundLineList;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private EditText hangLotionTv;
        private EditText groundLineNumberTv;
        private TextView hungTimeTv;
        private TextView demolitionTimeTv;
        private CheckBox hungTimeCb;
        private CheckBox demolitionTimeCb;
    }

}
