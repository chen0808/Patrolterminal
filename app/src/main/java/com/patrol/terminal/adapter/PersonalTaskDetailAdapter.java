package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.StringUtil;

import java.sql.Struct;
import java.util.List;

public class PersonalTaskDetailAdapter extends BaseQuickAdapter<PersonalTaskListBean, BaseViewHolder> {

    private Context mContext;

    public PersonalTaskDetailAdapter(int layoutResId, @Nullable List<PersonalTaskListBean> data) {
        super(layoutResId, data);
    }

    public PersonalTaskDetailAdapter(int layoutResId, @Nullable List<PersonalTaskListBean> data, Context mContext) {
        this(layoutResId, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, PersonalTaskListBean item) {
        viewHolder.setText(R.id.item_plan_time, StringUtil.getPersonalState(item.getAudit_status()));
        viewHolder.setTextColor(R.id.item_plan_time,mContext.getResources().getColor(StringUtil.getPersonalColor(item.getAudit_status())));
        TextView tvContent = viewHolder.getView(R.id.item_plan_name);
        if (item.getTower_name() != null) {
            AdapterUtils.setText(tvContent, item.getTower_name() + StringUtil.getTypeSign(item.getType_sign()) + "任务");
        } else {
            AdapterUtils.setText(tvContent, StringUtil.getTypeSign(item.getType_sign()) + "任务");
        }

        if(item.isCheck()){
            viewHolder.setImageResource(R.id.item_plan_offline,R.mipmap.check_yes);
        }else{
            viewHolder.setImageResource(R.id.item_plan_offline,R.mipmap.check_no);
        }

        ImageView imgView = viewHolder.getView(R.id.item_plan_offline);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!item.isCheck()&& "10".equals(item.getAudit_status())){
                        viewHolder.setImageResource(R.id.item_plan_offline,R.mipmap.check_yes);
                        item.setCheck(true);
                }else{
                    item.setCheck(false);
                    viewHolder.setImageResource(R.id.item_plan_offline,R.mipmap.check_no);
                }

                if(!item.getAudit_status().equals("待上传")){
                    Toast.makeText(mContext,"'待上传'"+"才可选择",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}