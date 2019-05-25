package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AuditorRatingActivity;
import com.patrol.terminal.bean.ScoreListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class ScoreAdapter extends BaseQuickAdapter<ScoreListBean, BaseViewHolder> implements View.OnClickListener {
    public ScoreAdapter(int layoutResId, @Nullable List<ScoreListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScoreListBean item) {
        helper.setText(R.id.tv_name, item.getName()).setText(R.id.tv_score, "得分：" + item.getScore());
        helper.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mContext.startActivity(new Intent(mContext, AuditorRatingActivity.class));
    }
}
