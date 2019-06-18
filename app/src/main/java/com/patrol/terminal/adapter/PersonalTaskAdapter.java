package com.patrol.terminal.adapter;

import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

public class PersonalTaskAdapter extends BaseQuickAdapter<GroupTaskBean, BaseViewHolder> {
    private int type = 1;

    public PersonalTaskAdapter(int layoutResId, @Nullable List<GroupTaskBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, GroupTaskBean item) {
        //图标
        TextView icon = viewHolder.getView(R.id.item_task_date_tv);
        AdapterUtils.setIconText(icon, item.getDep_name());

        TextView type = viewHolder.getView(R.id.item_task_type);
        AdapterUtils.setText(type, StringUtil.getTypeSign(item.getType_sign()));
        String finish_status = item.getDone_status();

            if ("0".equals(finish_status)){
                viewHolder.setText(R.id.item_line_state, "完成状态：未完成");
//                viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_red_bg);
            }else {
                viewHolder.setText(R.id.item_line_state, "完成状态：已完成");
//                viewHolder.setBackgroundRes(R.id.item_line_state, R.drawable.state_green_bg);
            }
        viewHolder.setGone(R.id.plan_progressbar_ll,true);
        viewHolder.setText(R.id.plan_progressbar_tv,"任务进度("+item.getDone_num()+"/"+item.getAll_num()+") :")
                .setText(R.id.plan_progressbar_num,item.getDone_rate()+"%");
        ProgressBar progressBar = viewHolder.getView(R.id.plan_progressbar_probar);
        progressBar.setMax(item.getAll_num());
        progressBar.setProgress(item.getDone_num());
        viewHolder.setText(R.id.item_task_personal, "执行人 :"+item.getWork_user_name())
                .setText(R.id.item_task_name, item.getLine_name()+"任务")  ;


    }
}