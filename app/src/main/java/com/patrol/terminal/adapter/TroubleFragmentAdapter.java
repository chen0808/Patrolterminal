package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.PatrolRecordActivity;
import com.patrol.terminal.activity.TestActivity;
import com.patrol.terminal.bean.TroubleBean;

import java.util.List;

public class TroubleFragmentAdapter extends BaseQuickAdapter<TroubleBean, BaseViewHolder> {
    public TroubleFragmentAdapter(int layoutResId, @Nullable List<TroubleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TroubleBean item) {
        helper.setText(R.id.tv_name, "线路名称：" + item.getLine_name() + " " + item.getTowers())
                .setText(R.id.tv_time, "隐患类型：" + item.getType_name())
                .setText(R.id.tv_detail, "发现时间：" + item.getFind_time())
                .setText(R.id.iv_icon, "隐患");
        RelativeLayout rlDefectitem = helper.getView(R.id.rl_defect_item);
        rlDefectitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (item.getType_id().equals("3A7703BA2EFA4B9E86732DB1B0FA4E3F") || item.getType_id().equals("F0C697FDE56E42959F52B6620544240D") || item.getType_id().equals("2F9D2BEF6B08403FAD1FED246B79410B")) {
//                    String lineName = ((PatrolRecordActivity) mContext).getLineName();
//                    Intent intent = new Intent(mContext, TestActivity.class);
//                    intent.putExtra("line_name", lineName);
//                    mContext.startActivity(intent);
//                }else if (item.getType_id().equals("CE0954EF596447CA9458CC230234E01A")){
                String lineName = ((PatrolRecordActivity) mContext).getLineName();
                Intent intent = new Intent(mContext, TestActivity.class);
                intent.putExtra("line_name", lineName);
                mContext.startActivity(intent);
//                }
            }
        });
    }
}
