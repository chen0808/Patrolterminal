package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.ProjectPlanBean;

import java.util.List;

/**
 * 作者：陈飞
 * 时间：2019/08/12 16:51
 */
public class ProjectPlanAdapter extends BaseQuickAdapter<ProjectPlanBean, BaseViewHolder> {

    public ProjectPlanAdapter(int layoutResId, @Nullable List<ProjectPlanBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectPlanBean item) {

        helper.setText(R.id.tv_project_name, item.getTemp_project_name())
                .setText(R.id.tv_start_time, "开始时间:" + item.getStart_date())
                .setText(R.id.tv_end_time, "结束时间:" + item.getFinish_date())
                .setText(R.id.tv_date, "工期:")
                .setText(R.id.tv_duty_user_name, "负责人:" + item.getDuty_user_name());
    }
}
