package com.patrol.terminal.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TicketSafeContent;

import java.util.List;

import androidx.annotation.Nullable;

public class SafeAdapter extends BaseQuickAdapter<TicketSafeContent, BaseViewHolder> {

    public SafeAdapter(int layoutResId, @Nullable List<TicketSafeContent> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TicketSafeContent item) {
        helper.setText(R.id.et_content, item.getTicket_safe_content());
        EditText etContent = helper.getView(R.id.et_content);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setTicket_safe_content(etContent.getText().toString());
            }
        });
    }
}
