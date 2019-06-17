package com.patrol.terminal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class BackTodoYXAdapter extends BaseQuickAdapter<PersonalTaskListBean, BaseViewHolder> {


    public BackTodoYXAdapter(int layoutResId, @Nullable List<PersonalTaskListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalTaskListBean item) {
        int position = helper.getPosition();
        if ("12".equals(item.getType_sign())||"13".equals(item.getType_sign())){
            helper.setText(R.id.tv_title, position+1+".关于" +item.getLine_name()+"的"+ StringUtil.getTypeSign(item.getType_sign())+"待办");
        }else {
            helper.setText(R.id.tv_title, position+1+"." +item.getLine_name()+item.getTower_name()+"的待办");
        }
    }
}
