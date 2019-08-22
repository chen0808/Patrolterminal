package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.LocalLandMarkBean;
import com.patrol.terminal.utils.Constant;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 里程碑 详情
 */
public class LandMarkDetailActivity extends AppCompatActivity {

    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_tv)
    TextView title_setting_tv;
    @BindView(R.id.title_setting)
    RelativeLayout title_setting;
    @BindView(R.id.lcb_list)
    LinearLayout lcbLL;
    @BindView(R.id.scroView)
    ScrollView scroView;

    HashMap<String,View> map = new HashMap<>();
    private List<LocalLandMarkBean> landMarkList;// = new ArrayList<>()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_detail);
        ButterKnife.bind(this);

        titleName.setText("里程碑");


        String marks = getIntent().getStringExtra("marks");
        landMarkList = (List<LocalLandMarkBean>) getIntent().getSerializableExtra("list");

        if(landMarkList != null){
            initView();
            if(!TextUtils.isEmpty(marks))
                scroTo(marks);
        }

    }

    public void scroTo(String mark){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                View view = map.get(mark);
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                scroView.scrollTo(0,location[1]);
            }
        }, 500);
    }

    public void initData2(String sbjd, View view) {
        map.put(sbjd,view);
        TextView lcb_year = view.findViewById(R.id.lcb_year);
        TextView lcb_sbjd = view.findViewById(R.id.lcb_sbjd);
        lcb_year.setText("2019年");
        lcb_sbjd.setText(sbjd);

        lcbLL.addView(view);
    }

    public void initData3(LocalLandMarkBean bean, View view) {
        if (bean != null && !TextUtils.isEmpty(bean.getLandmark_qkms())) {
            view.findViewById(R.id.lcb_qx).setVisibility(View.VISIBLE);
            TextView lichengbei_date = view.findViewById(R.id.lichengbei_date);
            TextView lcb_jd = view.findViewById(R.id.lcb_jd);
            TextView lcb_qk = view.findViewById(R.id.lcb_qk);
            TextView lcb_bz = view.findViewById(R.id.lcb_bz);
            lichengbei_date.setText(bean.getDate());
            lcb_jd.setText("进度：" + bean.getLandmark_jd() + "%");
            lcb_qk.setText("情况：" + bean.getLandmark_qkms());
            lcb_bz.setText("备注：" + bean.getLandmark_bz());
        }
    }


    public void initView() {

        for (int i = 0; i < Constant.lcbList.length; i++) {
            String sbjd = Constant.lcbList[i];
            View view = View.inflate(this, R.layout.activity_landmark_detail_item, null);
            initData2(sbjd, view);
            for (int j = 0; j < landMarkList.size(); j++) {
                LocalLandMarkBean bean = landMarkList.get(j);
                if (i == bean.getLandmark_sbjd()) {
                    initData3(bean, view);
                }
            }
        }

    }

    @OnClick({R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

        }
    }

}
