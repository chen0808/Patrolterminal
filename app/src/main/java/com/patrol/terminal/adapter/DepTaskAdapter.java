package com.patrol.terminal.adapter;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DepInfoBean;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

public class DepTaskAdapter extends BaseQuickAdapter<DepInfoBean, BaseViewHolder> {

    private String time = "";

    public DepTaskAdapter(int layoutResId, @Nullable List<DepInfoBean> data, String time) {
        super(layoutResId, data);
        this.time = time;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DepInfoBean item) {
        String dutyName = "";
        for (int i = 0; i < item.getDuty().size(); i++) {
            DepInfoBean.DutyBean dutyBean = item.getDuty().get(i);
            if (i == 0) {
                dutyName = dutyBean.getDuty_user_name();
            } else {
                dutyName = dutyName + "," + dutyBean.getDuty_user_name();
            }
        }
        //图标
        TextView icon = viewHolder.getView(R.id.item_task_date_tv);
        AdapterUtils.setIconText(icon, item.getName());
        viewHolder.setText(R.id.item_task_name, item.getName() + time + "计划")
                .setText(R.id.item_110kv_tv, "110kv："+item.getBig_count() + "段/" + Math.round(item.getBig_length() / 10d) / 100d + "km")
                .setText(R.id.item_35kv_tv, "35kv："+item.getSmall_count() + "段/" + Math.round(item.getSmall_length() / 10d) / 100d + "km")
                .setText(R.id.plan_progressbar_tv, "计划进度(" + item.getDone_num() + "/" + item.getAll_num() + ")：");
        if ("".equals(dutyName)){
            viewHolder.setText(R.id.item_task_group_team,"负责人：无" );
        }else {
            viewHolder.setText(R.id.item_task_group_team,"负责人：" +dutyName);
        }

        if (item.getDone_num() == 0) {
            viewHolder.setText(R.id.plan_progressbar_num, "0%");
        } else {
            //默认保留两位会有错误，这里设置保留小数点后4位
            double range = 0;
            try {
                range = Utils.div(item.getDone_num(), item.getAll_num(), 4) * 100;
                viewHolder.setText(R.id.plan_progressbar_num, range + "%");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        ProgressBar progressBar = viewHolder.getView(R.id.plan_progressbar_probar);
        progressBar.setMax(item.getDone_num());
        progressBar.setProgress(item.getAll_num());
    }
}