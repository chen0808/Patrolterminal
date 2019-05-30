package com.patrol.terminal.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TicketFirstGround;
import com.patrol.terminal.utils.DateUatil;

import java.util.List;

import androidx.annotation.Nullable;

public class GroundLineAdapter extends BaseQuickAdapter<TicketFirstGround, BaseViewHolder> {

    public GroundLineAdapter(int layoutResId, @Nullable List<TicketFirstGround> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TicketFirstGround item) {
        TextView tvHangLocation = helper.getView(R.id.hang_location_tv);
        TextView tvGroundLineNumber = helper.getView(R.id.ground_line_number_tv);
        TextView tvHungTime = helper.getView(R.id.hung_time_tv);
        TextView tvDemolitionTime = helper.getView(R.id.demolition_time_tv);
        CheckBox cbHungTime = helper.getView(R.id.hung_time_checkbox);
        CheckBox cbDemolitionTime = helper.getView(R.id.demolition_time_checkbox);

        tvHangLocation.setText(item.getInstall_site());
        tvHangLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setInstall_site(tvHangLocation.getText().toString());
            }
        });

        tvGroundLineNumber.setText(item.getGround_id());
        tvGroundLineNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setGround_id(tvGroundLineNumber.getText().toString());
            }
        });

        tvHungTime.setText(item.getInstall_time());
        if (!item.getInstall_time().equals(mContext.getResources().getString(R.string.work_ticket_time))) {
            cbHungTime.setVisibility(View.GONE);
        }
        cbHungTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvHungTime.setText(DateUatil.getCurrTime());
                } else {
                    tvHungTime.setText(mContext.getResources().getString(R.string.work_ticket_time));
                }
                item.setInstall_time(tvHungTime.getText().toString());
            }
        });

        if (!item.getRemove_time().equals(mContext.getResources().getString(R.string.work_ticket_time))) {
            cbDemolitionTime.setVisibility(View.GONE);
        }
        tvDemolitionTime.setText(item.getRemove_time());
        cbDemolitionTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tvDemolitionTime.setText(DateUatil.getCurrTime());
                } else {
                    tvDemolitionTime.setText(mContext.getResources().getString(R.string.work_ticket_time));
                }
                item.setRemove_time(tvDemolitionTime.getText().toString());
            }
        });
    }
}
