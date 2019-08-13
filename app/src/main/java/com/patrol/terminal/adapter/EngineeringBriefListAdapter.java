package com.patrol.terminal.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.EngineeringBriefAddActivity;
import com.patrol.terminal.activity.TroubleDetailActivity;
import com.patrol.terminal.bean.LocalGcjbBean;
import com.patrol.terminal.bean.TroubleFragmentBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.Constant;

import java.util.List;

/**
 * 简报 list
 */
public class EngineeringBriefListAdapter extends BaseQuickAdapter<LocalGcjbBean, BaseViewHolder> {
    public EngineeringBriefListAdapter(int layoutResId, @Nullable List<LocalGcjbBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, LocalGcjbBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EngineeringBriefAddActivity.class);
                Constant.isEditStatus = true;
                intent.putExtra(Constant.GCJB_TYPE_STR,item.getType());
                Bundle bundle = new Bundle();
                bundle.putSerializable("gclbBean",item);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        helper.setText(R.id.project_name,   item.getProject_name())
                .setText(R.id.project_num,  item.getProject_bgnum())
                .setText(R.id.project_type,  item.getProject_type())
                .setText(R.id.project_tbr,  item.getProject_tbr())
                .setText(R.id.project_date,  item.getProject_date());
    }
}
