package com.patrol.terminal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.TroubleDetailBean;

import java.util.List;

public class AcrossAdapter extends BaseQuickAdapter<TroubleDetailBean, BaseViewHolder> {
    public AcrossAdapter(int layoutResId, @Nullable List<TroubleDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TroubleDetailBean item) {
        helper.setText(R.id.tv_type_name, "隐患名字：" + item.getType_name())
                .setText(R.id.tv_towers, "杆段名字：" + item.getTowers())
                .setText(R.id.tv_across_name, "跨越物名称：" + item.getAcross_name())
                .setText(R.id.tv_small_tower_name, "跨越档小号侧杆塔名字：" + item.getSmall_tower_name())
                .setText(R.id.tv_big_tower_name, "跨越档大号侧杆塔名字：" + item.getBig_tower_name())
                .setText(R.id.tv_tension, "跨越耐张段：" + item.getTension())
                .setText(R.id.tv_is_independent, "是否独立耐张段：" + (item.getIs_independent().equals("0") ? "否" : "是"))
                .setText(R.id.tv_is_double, "绝缘子是否双串：" + (item.getIs_double().equals("0") ? "否" : "是"))
                .setText(R.id.tv_is_drainage, "是否安装附引流：" + (item.getIs_drainage().equals("0") ? "否" : "是"))
                .setText(R.id.tv_is_metal, "是否金属探伤：" + (item.getIs_metal().equals("0") ? "否" : "是"))
                .setText(R.id.tv_is_video, "是否安装视频监控：" + (item.getIs_video().equals("0") ? "否" : "是"))
                .setText(R.id.tv_is_adss, "是否挂接ADSS：" + (item.getIs_adss().equals("0") ? "否" : "是"))
                .setText(R.id.tv_remarks, "备注：" + (item.getRemarks() == null ? "" : item.getRemarks()))
                .setText(R.id.tv_status, "完成状态：" + (item.getStatus().equals("0") ? "未完成" : "已完成"));
    }
}
