package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.TroubleDetailActivity;
import com.patrol.terminal.bean.LocalAddTrouble;

import java.util.List;

public class TroubleFragmentAdapter extends BaseQuickAdapter<LocalAddTrouble, BaseViewHolder> {
    public TroubleFragmentAdapter(int layoutResId, @Nullable List<LocalAddTrouble> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalAddTrouble item) {
        helper.setText(R.id.tv_name, "线路名称：" + item.getLine_name() + " " + item.getTower_name())
                .setText(R.id.tv_time, "隐患类型：" + item.getType_name())
                .setText(R.id.tv_detail, "发现时间：" + item.getFind_time())
                .setText(R.id.iv_icon, "隐患");
        helper.getView(R.id.item_defect_status).setVisibility(View.GONE);

        RelativeLayout rlDefectitem = helper.getView(R.id.rl_defect_item);
        rlDefectitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String lineName = ((PatrolRecordActivity) mContext).getLineName();
//                Intent intent = new Intent(mContext, TestActivity.class);
//                intent.putExtra("line_name", lineName);
//                mContext.startActivity(intent);


                Intent intent = new Intent(mContext, TroubleDetailActivity.class);
                intent.putExtra("line_id", item.getLine_id());
                intent.putExtra("type", item.getType_id());
                intent.putExtras(intent);
                mContext.startActivity(intent);
            }
        });
    }
}
