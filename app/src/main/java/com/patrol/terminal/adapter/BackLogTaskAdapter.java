package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.PersonalTaskListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class BackLogTaskAdapter extends BaseQuickAdapter<PersonalTaskListBean, BaseViewHolder> {




    public BackLogTaskAdapter(int layoutResId, @Nullable List<PersonalTaskListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalTaskListBean item) {
        int position = helper.getPosition();
        helper.setText(R.id.tv_title, position+1+"." +item.getLine_name()+item.getTower_name()+item.getType_name());
    }
}
