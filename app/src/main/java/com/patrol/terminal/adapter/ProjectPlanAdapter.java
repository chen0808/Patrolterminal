package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.bean.ProjectBoardBean;

import java.util.List;

/**
 * 作者：陈飞
 * 时间：2019/08/12 16:51
 */
public class ProjectPlanAdapter extends BaseQuickAdapter<ProjectBoardBean, BaseViewHolder> {

    public ProjectPlanAdapter(int layoutResId, @Nullable List<ProjectBoardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectBoardBean item) {

    }
}
