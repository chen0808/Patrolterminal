package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.FirstWTicketActivity;
import com.patrol.terminal.activity.SecondWTicketActivity;
import com.patrol.terminal.activity.ThirdWTicketActivity;

import java.util.List;

public class TicketAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TicketAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_ticket, item);
        helper.getView(R.id.tv_ticket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (helper.getAdapterPosition()) {
                    case 0:
                        Intent intent1 = new Intent(mContext, FirstWTicketActivity.class);
                        mContext.startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(mContext, SecondWTicketActivity.class);
                        mContext.startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(mContext, ThirdWTicketActivity.class);
                        mContext.startActivity(intent3);
                        break;
                }
            }
        });
    }
}
