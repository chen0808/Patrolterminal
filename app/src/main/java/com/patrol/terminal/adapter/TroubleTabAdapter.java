package com.patrol.terminal.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DefectIngDetailActivity;
import com.patrol.terminal.bean.TroubleFragmentBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.StringUtil;

public class TroubleTabAdapter extends BaseQuickAdapter<TroubleFragmentBean, BaseViewHolder> {
    private int mType;

    public TroubleTabAdapter(int layoutResId, int type) {
        super(layoutResId);
        this.mType = type;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, TroubleFragmentBean item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DefectIngDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("id", item.getId());
                bundle.putInt("type", mType);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        if(item.getContent() == null){
            viewHolder.setText(R.id.tv_name, "内容：暂无");
        } else {
            viewHolder.setText(R.id.tv_name, "内容：" + item.getContent());
        }

        viewHolder.setText(R.id.tv_time, "线路杆塔：" + item.getLine_name()+item.getTower_name())
                .setText(R.id.tv_detail, "发现时间：" + item.getFind_time());

        if(mType == 1){
            viewHolder.setText(R.id.item_defect_status, StringUtil.getDefectState(item.getIn_status()));
            viewHolder.setTextColor(R.id.item_defect_status, mContext.getResources().getColor(StringUtil.getDefectColor(item.getIn_status())));
        } else {
            viewHolder.setText(R.id.item_defect_status, StringUtil.getDefectState(item.getDone_status()));
            viewHolder.setTextColor(R.id.item_defect_status, mContext.getResources().getColor(StringUtil.getDefectColor(item.getDone_status())));
        }

        TextView ivIcon = viewHolder.getView(R.id.iv_icon);
        AdapterUtils.setIconTextNew(ivIcon, item.getType_name());
    }
}