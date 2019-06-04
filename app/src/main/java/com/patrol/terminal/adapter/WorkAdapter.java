package com.patrol.terminal.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TicketWork;

import java.util.List;

import androidx.annotation.Nullable;

public class WorkAdapter extends BaseQuickAdapter<TicketWork, BaseViewHolder> {
    List<TicketWork> workList;

    public WorkAdapter(int layoutResId, @Nullable List<TicketWork> data) {
        super(layoutResId, data);
        this.workList = data;
    }



    public List<TicketWork> getData() {
        return workList;
    }

    @Override
    protected void convert(BaseViewHolder helper, TicketWork item) {
        int position = helper.getPosition();
        helper.setText(R.id.tv_content, item.getWork_content());
        helper.getView(R.id.tv_name).setVisibility(View.GONE);
        helper.getView(R.id.view_name).setVisibility(View.GONE);

        EditText plactEt = helper.getView(R.id.tv_place);
        plactEt.setText(item.getWork_range());
        plactEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                workList.get(position).setWork_range(plactEt.getText().toString());
            }
        });
    }


}
