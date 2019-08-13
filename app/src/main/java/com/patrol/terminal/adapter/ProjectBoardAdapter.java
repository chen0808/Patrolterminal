package com.patrol.terminal.adapter;

import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.ProjectBoardBean;

import java.util.List;

/**
 * 作者：陈飞
 * 时间：2019/08/12 10:28
 */
public class ProjectBoardAdapter extends BaseQuickAdapter<ProjectBoardBean, BaseViewHolder> {
    public ProjectBoardAdapter(int layoutResId, @Nullable List<ProjectBoardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectBoardBean item) {
        helper.setText(R.id.tv_project_name, item.getProject_name())
                .setText(R.id.tv_total, "总工期:" + item.getDate_total() + "天")
                .setText(R.id.tv_ing, "进行:" + item.getDate_now() * 100 / item.getDate_total() + "%")
                .setText(R.id.tv_remain, "滞后:" + (100 - item.getDate_now() * 100 / item.getDate_total()) + "%");
        ProgressBar pbProject = helper.getView(R.id.pb_project);
        pbProject.setProgress(item.getDate_now() * 100 / item.getDate_total());
    }
}
