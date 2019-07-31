package com.patrol.terminal.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DangerDetailActivity;
import com.patrol.terminal.bean.LocalAddTrouble;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;

import java.util.List;

public class TroubleFragmentAdapter extends BaseQuickAdapter<LocalAddTrouble, BaseViewHolder> {
    public TroubleFragmentAdapter(int layoutResId, @Nullable List<LocalAddTrouble> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalAddTrouble item) {
        if(item.getContent() == null){
            helper.setText(R.id.tv_name, "内容：暂无");
        } else {
            helper.setText(R.id.tv_name, "内容：" + item.getContent());
        }

        helper.setText(R.id.tv_time, "线路杆塔：" + item.getLine_name()+item.getTower_name())
                .setText(R.id.tv_detail, "发现时间：" + item.getFind_time());

        helper.setText(R.id.item_defect_status, StringUtil.getDefectState(item.getDone_status()));
        helper.setTextColor(R.id.item_defect_status, mContext.getResources().getColor(StringUtil.getDefectColor(item.getDone_status())));

        helper.getView(R.id.iv_icon).setVisibility(View.VISIBLE);
        TextView ivIcon = helper.getView(R.id.iv_icon);
        AdapterUtils.setIconTextNew(ivIcon, item.getType_name());

        if (TextUtils.isEmpty(item.getFind_time())) {
            helper.getView(R.id.tv_detail).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_detail).setVisibility(View.VISIBLE);
        }

        if (item.getIsdownload().equals("1")) {//离线添加
            helper.getView(R.id.btn_deltrouble).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_defect_status, "待提交");
        } else {
            helper.getView(R.id.btn_deltrouble).setVisibility(View.GONE);
            helper.setText(R.id.item_defect_status, StringUtil.getDefectState(item.getDone_status()));
        }

        RelativeLayout rlDefectitem = helper.getView(R.id.rl_defect_item);
        rlDefectitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(item.getFind_time())) {
                    Toast.makeText(mContext, "待提交隐患不可查看", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(mContext, DangerDetailActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("type_id", item.getType_id());
                    intent.putExtra("type", "2");
                    mContext.startActivity(intent);
                }
            }
        });

        helper.getView(R.id.btn_deltrouble).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelOrOkDialog.setOnDialogclick(mContext, "是否删除？", "取消", "删除", new CancelOrOkDialog.onDialogClick() {
                    @Override
                    public void ok() {
                        item.delete();
                        mData.remove(item);
                        notifyDataSetChanged();
                    }
                    @Override
                    public void cancle() {

                    }
                });
            }
        });
    }
}
