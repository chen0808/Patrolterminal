package com.patrol.terminal.overhaul;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.CheckProjectBean;
import com.patrol.terminal.bean.CheckProjectServiceBean;
import com.patrol.terminal.bean.CheckResultBean;

import java.util.List;

import androidx.annotation.Nullable;

public class CheckProjectAdapter extends BaseQuickAdapter<CheckProjectServiceBean, BaseViewHolder> {
    //private List<CheckProjectBean> mData;
    private int mType;

    public CheckProjectAdapter(int layoutResId, @Nullable List<CheckProjectServiceBean> data, int type) {
        super(layoutResId, data);
        //this.mData = data;
        this.mType = type;

    }

    @Nullable
    @Override
    public CheckProjectServiceBean getItem(int position) {
        if (mData != null) {
            return mData.get(position);
        }
        return super.getItem(position);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CheckProjectServiceBean item) {
        if (mType == 0) {
            viewHolder.setText(R.id.project_tv, item.getTemp_project_name());
            viewHolder.setText(R.id.project_content_tv, item.getCheck_project());
            viewHolder.setText(R.id.project_create_person_tv, item.getCheck_user_name());
            viewHolder.setText(R.id.time_tv, item.getTime());



            List<CheckProjectServiceBean.TempCheckResultListBean> checkResultList = item.getTempCheckResultList();
            if (checkResultList==null){
               checkResultList = item.getTempQualityResultList();
            }
            boolean isSuccess = true;

                for (int i = 0; i < checkResultList.size(); i++) {
                    String result = checkResultList.get(i).getResult();
                    if ("1".equals(result)) {  //有需要整改的
                    }else {
                        isSuccess = false;
                    }
                }



            if (isSuccess) {
                viewHolder.setImageResource(R.id.check_project_result_iv, R.mipmap.check_project_ok);   //通过
                item.setState_sign("0");
            }else {
                viewHolder.setImageResource(R.id.check_project_result_iv, R.mipmap.check_project_accepted);   //正常
                item.setState_sign("1");
            }


            for (int i = 0; i < checkResultList.size(); i++) {
                String result = checkResultList.get(i).getResult();

                if ("3".equals(result)) {  //有需要整改的
                    viewHolder.setImageResource(R.id.check_project_result_iv, R.mipmap.check_project_unaccepted);   //待整改
                    item.setState_sign("2");
                }
            }





//            switch (item.getType_sign()) {
//                case "0":
//                    viewHolder.setImageResource(R.id.check_project_result_iv, R.mipmap.check_project_ok);   //通过
//                    break;
//
//                case "1":
//                    viewHolder.setImageResource(R.id.check_project_result_iv, R.mipmap.check_project_accepted);   //正常
//                    break;
//
//                case "2":
//                    viewHolder.setImageResource(R.id.check_project_result_iv, R.mipmap.check_project_unaccepted);   //待整改
//                    break;
//            }
        }else if(mType == 1) {
            viewHolder.setText(R.id.project_tv, item.getTemp_project_name());
        }



    }

}