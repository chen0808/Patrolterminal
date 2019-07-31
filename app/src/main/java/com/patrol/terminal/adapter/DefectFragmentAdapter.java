package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DefectIngDetailActivity;
import com.patrol.terminal.bean.DefectFragmentBean2;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

public class DefectFragmentAdapter extends BaseQuickAdapter<DefectFragmentBean2, BaseViewHolder> {

    public DefectFragmentAdapter(int layoutResId, @Nullable List<DefectFragmentBean2> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DefectFragmentBean2 item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DefectIngDetailActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("type", "2");
                mContext.startActivity(intent);
            }
        });

        viewHolder.setText(R.id.tv_name, "内容：" + item.getContent())
                .setText(R.id.tv_time, "线路杆塔：" + item.getLine_name()+item.getTower_name())
                .setText(R.id.tv_detail, "发现时间：" + item.getFind_time());

        viewHolder.setText(R.id.item_defect_status, StringUtil.getDefectState(item.getDone_status()));
        viewHolder.setTextColor(R.id.item_defect_status, mContext.getResources().getColor(StringUtil.getDefectColor(item.getDone_status())));

        viewHolder.getView(R.id.iv_icon_iv).setVisibility(View.VISIBLE);
        if ("一般".equals(item.getGrade_name())){
            viewHolder.setImageResource(R.id.iv_icon_iv,R.mipmap.yiban);
        } else if ("严重".equals(item.getGrade_name())){
            viewHolder.setImageResource(R.id.iv_icon_iv,R.mipmap.yanzhong);
        } else if ("危急".equals(item.getGrade_name())){
            viewHolder.setImageResource(R.id.iv_icon_iv,R.mipmap.weiji);
        }
    }
}