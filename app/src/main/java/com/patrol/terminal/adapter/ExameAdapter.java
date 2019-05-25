package com.patrol.terminal.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PatrolListBean;
import com.patrol.terminal.bean.PatrolRecordBean;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ExameAdapter extends BaseQuickAdapter<PatrolListBean, BaseViewHolder> {

    public ExameAdapter(int layoutResId, @Nullable List<PatrolListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, PatrolListBean item) {
        viewHolder.setText(R.id.item_exame_date_tv, "巡");
        viewHolder.setBackgroundRes(R.id.item_exame_date_tv, R.drawable.plan_mon_bg);
        viewHolder.setOnClickListener(R.id.item_exame_refuse, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPatrolList(viewHolder, item.getId(), "2");
            }
        });
        viewHolder.setOnClickListener(R.id.item_exame_agree, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPatrolList(viewHolder, item.getId(), "1");
            }
        });

        if ("2".equals(item.getIsConfirm())) {
            viewHolder.setVisible(R.id.item_line_state, true)
                    .setTextColor(R.id.item_line_state, mContext.getResources().getColor(R.color.write_red))
                    .setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg)
                    .setVisible(R.id.item_line_ll, false)
                    .setText(R.id.item_line_state, "审核未通过");

        } else if ("1".equals(item.getIsConfirm())) {
            viewHolder.setVisible(R.id.item_line_state, true)
                    .setTextColor(R.id.item_line_state, mContext.getResources().getColor(R.color.write_red))
                    .setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg)
                    .setVisible(R.id.item_line_ll, false)
                    .setText(R.id.item_line_state, "审核通过");

        } else {
            viewHolder.setVisible(R.id.item_line_state, true)
                    .setTextColor(R.id.item_line_state, mContext.getResources().getColor(R.color.green))
                    .setBackgroundRes(R.id.item_line_state, R.drawable.state_green_bg)
                    .setVisible(R.id.item_line_ll, true)
                    .setText(R.id.item_line_state, "审核中");
        }
        viewHolder.setText(R.id.item_exame_name, "计划名称: " + item.getName())
                .setText(R.id.item_exame_time, "执行日期: 无")
                .setText(R.id.item_exame_content, "备注: 无");
    }

    public void getPatrolList(BaseViewHolder viewHolder, String id, String flag) {
        BaseRequest.getInstance().getService()
                .patrolConfirm(id, "999", flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolRecordBean>>(mContext) {

                    @Override
                    protected void onSuccees(BaseResult<List<PatrolRecordBean>> t) throws Exception {
                        int code = t.getCode();
                        if (code == 0) {
                            if ("2".equals(flag)) {
                                viewHolder.setVisible(R.id.item_line_state, true)
                                        .setTextColor(R.id.item_line_state, mContext.getResources().getColor(R.color.write_red))
                                        .setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg)
                                        .setVisible(R.id.item_line_ll, false)
                                        .setText(R.id.item_line_state, "审核未通过");

                            } else if ("1".equals(flag)) {
                                viewHolder.setVisible(R.id.item_line_state, true)
                                        .setTextColor(R.id.item_line_state, mContext.getResources().getColor(R.color.write_red))
                                        .setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg)
                                        .setVisible(R.id.item_line_ll, false)
                                        .setText(R.id.item_line_state, "审核通过");

                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
}