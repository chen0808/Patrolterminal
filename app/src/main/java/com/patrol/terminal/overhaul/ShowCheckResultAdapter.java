package com.patrol.terminal.overhaul;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.imagepicker.loader.ImageLoader;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.CheckProjectServiceBean;
import com.patrol.terminal.bean.CheckResultBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

public class ShowCheckResultAdapter extends BaseQuickAdapter<CheckProjectServiceBean.TempCheckResultListBean, BaseViewHolder> {
    private boolean isRadioGroupShow = false;
    private List<CheckProjectServiceBean.TempCheckResultListBean> mData;
    private Activity mActivity;

    public ShowCheckResultAdapter(Activity activity, int layoutResId, @Nullable List<CheckProjectServiceBean.TempCheckResultListBean> data) {
        super(layoutResId, data);
        this.mData = data;
        this.mActivity = activity;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, CheckProjectServiceBean.TempCheckResultListBean item) {
        Log.w("linmeng", "item:" + item.getResult());
        switch (item.getResult()) {
            case "0":
                viewHolder.setText(R.id.check_result_tv, "通过");
                break;

            case "1":
                viewHolder.setText(R.id.check_result_tv, "口头警告");
                break;

            case "2":
                viewHolder.setText(R.id.check_result_tv, "书面整改");
                break;
        }

        TextView checkResultContent = viewHolder.getView(R.id.check_result_content);
        checkResultContent.setText(item.getContent());

        LinearLayout pictureLl = viewHolder.getView(R.id.pic_ll);

        List<CheckProjectServiceBean.TempCheckResultListBean.TempImgListBean> imgList = item.getTempImgList();
        for (int i = 0; i < imgList.size(); i++) {
            String filePath = BaseUrl.BASE_URL + imgList.get(i).getFile_path() + imgList.get(i).getFilename();
            ImageView imageView = new ImageView(mContext);
            imageView.setPadding(0, 0, 0, 0);
            Glide.with(mContext).load(filePath).into(imageView);
            pictureLl.addView(imageView, new LinearLayout.LayoutParams(180, 180));
        }

    }

}