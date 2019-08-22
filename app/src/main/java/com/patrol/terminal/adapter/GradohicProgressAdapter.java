package com.patrol.terminal.adapter;

import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.GraphicProgressBean;
import com.patrol.terminal.bean.OvaTodoBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.widget.PinchImageView;

import java.util.ArrayList;
import java.util.List;

public class GradohicProgressAdapter extends BaseQuickAdapter<GraphicProgressBean, BaseViewHolder> {


    public GradohicProgressAdapter(int layoutResId, @Nullable List<GraphicProgressBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GraphicProgressBean item) {
        helper.setText(R.id.item_progress_content, item.getPlan_desc())
                .setText(R.id.item_progress_plan_name, item.getTemp_project_name())
                .setText(R.id.item_progress_time, item.getUpload_time());
        GridView gridView = helper.getView(R.id.gridView);
        List<GraphicProgressBean.TempImgListBean> tempImgList = item.getTempImgList();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < tempImgList.size(); i++) {
            GraphicProgressBean.TempImgListBean tempImgListBean = tempImgList.get(i);
            if (i < 3) {
                list.add(BaseUrl.BASE_URL + tempImgListBean.getFile_path() + tempImgListBean.getFilename());
            }
        }

        GroProPicAdapter mGridViewAddImgAdapter = new GroProPicAdapter(mContext, list);
        gridView.setAdapter(mGridViewAddImgAdapter);

    }


}
