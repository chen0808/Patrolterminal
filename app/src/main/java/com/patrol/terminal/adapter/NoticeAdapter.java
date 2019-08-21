package com.patrol.terminal.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.NoticeBean;
import com.patrol.terminal.overhaul.ElectronicNoticeAddActivity;

import java.util.List;

public class NoticeAdapter extends BaseQuickAdapter<NoticeBean, BaseViewHolder> {
    private int logType=-1;

    public NoticeAdapter(int layoutResId, int logType) {
        super(layoutResId);
        this.logType = logType;
    }

    public NoticeAdapter(int item_initiate_project, List<NoticeBean> initiateProjectList) {
        super(item_initiate_project, initiateProjectList);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, NoticeBean item) {
        if (logType!=-1){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ElectronicNoticeAddActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("NoticeBean", item);
                    bundle.putInt("type", 1);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }

        viewHolder.setText(R.id.tv_project_name, item.getTitle())
                .setText(R.id.tv_code, item.getContent())
                .setText(R.id.tv_create_name, item.getAnnounce_user())
                .setText(R.id.tv_time, item.getEnd_time());
    }
}