package com.patrol.terminal.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DefectIngDetailActivity;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.patrol.terminal.utils.StringUtil;

public class DefectIngTabAdapter extends BaseQuickAdapter<DefectFragmentBean, BaseViewHolder> {
    private int mType;

    public DefectIngTabAdapter(int layoutResId, int type) {
        super(layoutResId);
        this.mType = type;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, DefectFragmentBean item) {
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

        viewHolder.setText(R.id.tv_name, "内容：" + item.getContent())
                .setText(R.id.tv_time, "线路杆塔：" + item.getLine_name()+item.getTower_name())
                .setText(R.id.tv_detail, "发现时间：" + item.getFind_time());

        if(mType == 1){
            viewHolder.setText(R.id.item_defect_status, StringUtil.getDefectState(item.getIn_status()));
            viewHolder.setTextColor(R.id.item_defect_status, mContext.getResources().getColor(StringUtil.getDefectColor(item.getIn_status())));
        } else {
            viewHolder.setText(R.id.item_defect_status, StringUtil.getDefectState(item.getDone_status()));
            viewHolder.setTextColor(R.id.item_defect_status, mContext.getResources().getColor(StringUtil.getDefectColor(item.getDone_status())));
        }

        viewHolder.setGone(R.id.iv_icon,false);
        viewHolder.setGone(R.id.iv_icon_iv,true);
        if ("一般".equals(item.getGrade_name())){
            viewHolder.setImageResource(R.id.iv_icon_iv,R.mipmap.yiban);
        }else if ("严重".equals(item.getGrade_name())){
            viewHolder.setImageResource(R.id.iv_icon_iv,R.mipmap.yanzhong);
        }else if ("危急".equals(item.getGrade_name())){
            viewHolder.setImageResource(R.id.iv_icon_iv,R.mipmap.weiji);
        }
    }
}