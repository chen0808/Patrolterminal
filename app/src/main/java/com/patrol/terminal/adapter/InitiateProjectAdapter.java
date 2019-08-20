package com.patrol.terminal.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.InitiateProjectBean;
import com.patrol.terminal.overhaul.InitiateProjectAddActivity;
import com.patrol.terminal.utils.SPUtil;

public class InitiateProjectAdapter extends BaseQuickAdapter<InitiateProjectBean, BaseViewHolder> {
    private int logType;

    public InitiateProjectAdapter(int layoutResId, int logType) {
        super(layoutResId);
        this.logType = logType;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, InitiateProjectBean item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InitiateProjectAddActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("InitiateProjectBean", item);
                bundle.putInt("type", 1);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        viewHolder.setText(R.id.tv_project_name, item.getName())
                .setText(R.id.tv_code, item.getProject_no())
                .setText(R.id.tv_create_name, item.getCreate_name())
                .setText(R.id.tv_time, item.getStart_time());

//        if (item.getStatus().equals("1")){
//            viewHolder.setText(R.id.tv_status, "施工阶段");
//        } else if (item.getStatus().equals("2")){
//            viewHolder.setText(R.id.tv_status, "招采与建设准备阶段");
//        } else if (item.getStatus().equals("3")){
//            viewHolder.setText(R.id.tv_status, "竣工验收阶段");
//        }
    }
}