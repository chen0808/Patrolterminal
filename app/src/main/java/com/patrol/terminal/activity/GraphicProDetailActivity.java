package com.patrol.terminal.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GroProPicAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.GraphicProgressBean;
import com.patrol.terminal.widget.PinchImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GraphicProDetailActivity extends BaseActivity {

    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @BindView(R.id.title_setting_tv)
    TextView titleSettingTv;
    @BindView(R.id.title_setting)
    RelativeLayout titleSetting;
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.detail_belong_project)
    TextView detailBelongProject;
    @BindView(R.id.detail_create_name)
    TextView detailCreateName;
    @BindView(R.id.detail_create_time)
    TextView detailCreateTime;
    @BindView(R.id.detail_content)
    TextView detailContent;
    @BindView(R.id.gridView)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_pro_detail);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("形象进度详情");
        GraphicProgressBean bean= (GraphicProgressBean) getIntent().getSerializableExtra("bean");
        List<GraphicProgressBean.TempImgListBean> tempImgList = bean.getTempImgList();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < tempImgList.size(); i++) {
            GraphicProgressBean.TempImgListBean tempImgListBean = tempImgList.get(i);
            list.add(BaseUrl.BASE_URL + tempImgListBean.getFile_path() + tempImgListBean.getFilename());
        }
        GroProPicAdapter mGridViewAddImgAdapter = new GroProPicAdapter(this, list);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                showBigImage(list.get(position));
            }
        });
        detailBelongProject.setText(bean.getTemp_project_name());
        detailContent.setText(bean.getPlan_desc());
        detailCreateName.setText(bean.getUser_name());
        detailCreateTime.setText(bean.getUpload_time());
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
    //查看大图
    private void showBigImage(String path) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_big_image);
        PinchImageView iv = dialog.findViewById(R.id.iv);
        Glide.with(this).load(path).into(iv);
        dialog.show();
    }
}
