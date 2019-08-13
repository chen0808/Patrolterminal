package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.ProjectBoardBean;

import java.util.List;

/**
 * 作者：陈飞
 * 时间：2019/08/12 15:12
 */
public class BudgetAdapter extends BaseQuickAdapter<ProjectBoardBean, BaseViewHolder> {
    public BudgetAdapter(int layoutResId, @Nullable List<ProjectBoardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectBoardBean item) {
        helper.setText(R.id.tv_project_name, item.getProject_name())
                .setText(R.id.tv_money, "¥" + item.getMoney());
    }
}
