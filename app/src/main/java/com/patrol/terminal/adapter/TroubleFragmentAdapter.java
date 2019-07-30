package com.patrol.terminal.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DangerDetailActivity;
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
        if (TextUtils.isEmpty(item.getFind_time())) {
            helper.getView(R.id.tv_detail).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_detail).setVisibility(View.VISIBLE);
        }

        RelativeLayout rlDefectitem = helper.getView(R.id.rl_defect_item);
        rlDefectitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(item.getFind_time())) {
                    Toast.makeText(mContext, "待提交隐患不可查看", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent = new Intent(mContext, DangerDetailActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("type_id", item.getType_id());
                    mContext.startActivity(intent);

                }

            }
        });

    }

}
