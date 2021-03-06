package com.patrol.terminal.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.WorkingLogDetailActivity;
import com.patrol.terminal.bean.WorkingLogBean;

public class WorkingLogAdapter extends BaseQuickAdapter<WorkingLogBean, BaseViewHolder> {
    private int logType;

    public WorkingLogAdapter(int layoutResId, int logType) {
        super(layoutResId);
        this.logType = logType;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, WorkingLogBean item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkingLogDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("WorkingLogBean", item);
                bundle.putInt("logType", logType);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        viewHolder.setText(R.id.tv_project_name, item.getTemp_project_name())
                .setText(R.id.tv_working_name, item.getUser_name())
                .setText(R.id.tv_content, item.getConstruction_content())
                .setText(R.id.tv_report_name, item.getCreated_user_name())
                .setText(R.id.tv_time, item.getHappen_date());

//        if (item.getLog_sign().equals("1")){
//            viewHolder.setImageResource(R.id.image_status,R.mipmap.check_project_accepted);
//        } else if (item.getLog_sign().equals("2")){
//            viewHolder.setImageResource(R.id.image_status,R.mipmap.check_project_ok);
//        } else if (item.getLog_sign().equals("3")){
//            viewHolder.setImageResource(R.id.image_status,R.mipmap.check_project_unaccepted);
//        }
    }
}