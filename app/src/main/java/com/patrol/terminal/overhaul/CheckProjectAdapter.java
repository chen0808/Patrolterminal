package com.patrol.terminal.overhaul;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.CheckProjectBean;
import com.patrol.terminal.bean.CheckResultBean;

import java.util.List;

import androidx.annotation.Nullable;

public class CheckProjectAdapter extends BaseQuickAdapter<CheckProjectBean, BaseViewHolder> {
    //private List<CheckProjectBean> mData;
    private int mType;

    public CheckProjectAdapter(int layoutResId, @Nullable List<CheckProjectBean> data, int type) {
        super(layoutResId, data);
        //this.mData = data;
        this.mType = type;

    }

    @Nullable
    @Override
    public CheckProjectBean getItem(int position) {
        if (mData != null) {
            return mData.get(position);
        }
        return super.getItem(position);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CheckProjectBean item) {
        if (mType == 0) {
            viewHolder.setText(R.id.project_tv, item.getName());
            viewHolder.setText(R.id.project_content_tv, item.getContent());
            viewHolder.setText(R.id.project_create_person_tv, item.getCreate_person_name());
            viewHolder.setText(R.id.time_tv, item.getTime());

            switch (item.getProject_result_status()) {
                case 0:
                    viewHolder.setImageResource(R.id.check_project_result_iv, R.mipmap.check_project_ok);   //通过
                    break;

                case 1:
                    viewHolder.setImageResource(R.id.check_project_result_iv, R.mipmap.check_project_accepted);   //正常
                    break;

                case 2:
                    viewHolder.setImageResource(R.id.check_project_result_iv, R.mipmap.check_project_unaccepted);   //待整改
                    break;
            }
        }else if(mType == 1) {
            viewHolder.setText(R.id.project_tv, item.getName());
        }



    }

}