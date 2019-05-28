package com.patrol.terminal.adapter;

import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.PlanFinishRateBean;

import java.util.List;

import androidx.annotation.Nullable;

public class PlanFinishRateAdapter extends BaseQuickAdapter<PlanFinishRateBean, BaseViewHolder> {
    public PlanFinishRateAdapter(int layoutResId, @Nullable List<PlanFinishRateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlanFinishRateBean item) {
        helper.setText(R.id.tv_name, item.getUsername())
                .setText(R.id.tv_progress1, item.getPercent1() + "%")
                .setText(R.id.tv_progress2, item.getPercent2() + "%");
        ProgressBar pb1 = helper.getView(R.id.pb1);
        ProgressBar pb2 = helper.getView(R.id.pb2);
        pb1.setProgress((int) item.getPercent1());
        pb2.setProgress((int) item.getPercent2());
    }
}
