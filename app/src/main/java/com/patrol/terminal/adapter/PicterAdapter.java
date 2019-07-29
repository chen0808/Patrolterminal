package com.patrol.terminal.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.AddressBookLevel2;
import com.patrol.terminal.bean.FangLeiTodoBean;
import com.patrol.terminal.utils.Constant;

import java.util.List;

import androidx.annotation.Nullable;

public class PicterAdapter extends BaseQuickAdapter<FangLeiTodoBean.TroubleFileListBean, BaseViewHolder> {

    public PicterAdapter(int layoutResId, @Nullable List<FangLeiTodoBean.TroubleFileListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FangLeiTodoBean.TroubleFileListBean item) {

        if (item.getFile_path()!=null){
            ImageView imageView = helper.getView(R.id.item_pic_iv);
            Glide.with(mContext).load(BaseUrl.BASE_URL+item.getFile_path()+item.getFilename()).into(imageView);
        }
    }
}
