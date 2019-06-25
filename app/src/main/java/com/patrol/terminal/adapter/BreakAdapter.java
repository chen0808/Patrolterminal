package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TroubleDetailBean;

import java.util.List;

public class BreakAdapter extends BaseQuickAdapter<TroubleDetailBean, BaseViewHolder> {
    public BreakAdapter(int layoutResId, @Nullable List<TroubleDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TroubleDetailBean item) {
        helper.setText(R.id.tv_type_name, "隐患名字：" + item.getType_name())
                .setText(R.id.tv_dep_name, "班组名字：" + item.getDep_name())
                .setText(R.id.tv_line_level, "线路等级：" + item.getLine_level())
                .setText(R.id.tv_line_name, "线路名称：" + item.getLine_name())
                .setText(R.id.tv_find_time, "发现时间：" + item.getFind_time())
                .setText(R.id.tv_tower_number, "基数：" + item.getTower_number())
                .setText(R.id.tv_towers, "杆段号：" + item.getTowers())
                .setText(R.id.tv_is_video, "是否是视频监控：" + (item.getIs_video().equals("0") ? "否" : "是"))
                .setText(R.id.tv_deal_notes_first, "措施1：" + (item.getDeal_notes_first() == null ? "" : item.getDeal_notes_first()))
                .setText(R.id.tv_deal_notes_second, "措施2：" + (item.getDeal_notes_second() == null ? "" : item.getDeal_notes_second()))
                .setText(R.id.tv_deal_notes_third, "措施3：" + (item.getDeal_notes_third() == null ? "" : item.getDeal_notes_third()))
                .setText(R.id.tv_deal_notes_fourth, "措施4：" + (item.getDeal_notes_fourth() == null ? "" : item.getDeal_notes_fourth()))
                .setText(R.id.tv_deal_notes_fifth, "措施5：" + (item.getDeal_notes_fifth() == null ? "" : item.getDeal_notes_fifth()))
                .setText(R.id.tv_duty_unit, "责任单位：" + item.getDuty_unit())
                .setText(R.id.tv_trouble_wares, "隐患级别：" + item.getTrouble_wares())
                .setText(R.id.tv_content, "隐患描述内容：" + item.getContent());
    }
}
