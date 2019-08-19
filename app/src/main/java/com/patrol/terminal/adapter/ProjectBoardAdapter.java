package com.patrol.terminal.adapter;

import android.os.Build;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.ProjectBoardBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 作者：陈飞
 * 时间：2019/08/12 10:28
 */
public class ProjectBoardAdapter extends BaseQuickAdapter<ProjectBoardBean, BaseViewHolder> {

    private long daysTotal;
    private long daysIng;

    public ProjectBoardAdapter(int layoutResId, @Nullable List<ProjectBoardBean> data) {
        super(layoutResId, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void convert(BaseViewHolder helper, ProjectBoardBean item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long startTime = sdf.parse(item.getStart_time()).getTime();
            long endTime = sdf.parse(item.getEnd_time()).getTime();
            daysTotal = endTime / 24 / 60 / 60 / 1000 - startTime / 24 / 60 / 60 / 1000;
            daysIng = System.currentTimeMillis() / 24 / 60 / 60 / 1000 - startTime / 24 / 60 / 60 / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        helper.setText(R.id.tv_project_name, item.getName())
                .setText(R.id.tv_total, "总工期:" + daysTotal + "天")
                .setText(R.id.tv_ing, "进行:" + daysIng * 100 / daysTotal + "%")
                .setText(R.id.tv_remain, "滞后:" + (100 - daysIng * 100 / daysTotal) + "%");
        ProgressBar pbProject = helper.getView(R.id.pb_project);
        pbProject.setProgress((int) (daysIng * 100 / daysTotal));
    }
}
