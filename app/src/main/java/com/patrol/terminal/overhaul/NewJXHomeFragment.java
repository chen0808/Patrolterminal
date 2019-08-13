package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GridViewAdapter5;
import com.patrol.terminal.adapter.GridePagerAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.bean.MapUserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewJXHomeFragment extends BaseFragment {

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
    @BindView(R.id.weather_icon)
    ImageView weatherIcon;
    @BindView(R.id.place_and_temp)
    TextView placeAndTemp;
    @BindView(R.id.jx_vpr)
    ViewPager jxVpr;
    @BindView(R.id.jx_point1)
    TextView jxPoint1;
    @BindView(R.id.jx_point2)
    TextView jxPoint2;
    @BindView(R.id.paomadeng)
    TextView paomadeng;
    private GridViewAdapter5 weekAdapter;

    private String[] names1 = new String[]{"项目看板","形象进度","里程碑","质量检查","施工日志","工程简报","工程周报","安全检查"};
    private int[] img1 = new int[]{R.mipmap.jx_home_icon1,R.mipmap.jx_home_icon2,R.mipmap.jx_home_icon3,R.mipmap.jx_home_icon4,R.mipmap.jx_home_icon5,R.mipmap.jx_home_icon6,R.mipmap.jx_home_icon7,R.mipmap.jx_home_icon8};
    private String[] names2 = new String[]{"里程碑"};
    private int[] img2 = new int[]{R.mipmap.todo2};
    private List<List<MapUserInfo>> results = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jx_home, container, false);
        return view;
    }

    @Override
    protected void initData() {
        titleBack.setGravity(View.GONE);
        titleName.setText("检修");
        paomadeng.setSelected(true);
        init();
        GridePagerAdapter pagerAdapter = new GridePagerAdapter(getContext(), results);
        jxVpr.setAdapter(pagerAdapter);
        jxVpr.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        jxPoint1.setBackgroundResource(R.drawable.point);
                        jxPoint2.setBackgroundResource(R.drawable.week_bg);
                        break;
                    case 1:
                        jxPoint1.setBackgroundResource(R.drawable.week_bg);
                        jxPoint2.setBackgroundResource(R.drawable.point);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void init() {
        List<MapUserInfo> results1 = new ArrayList<>();
        List<MapUserInfo> results2 = new ArrayList<>();
        for (int i = 0; i < names1.length; i++) {
            MapUserInfo mapUserInfo3 = new MapUserInfo();
            mapUserInfo3.setUserImgId(img1[i]);
            mapUserInfo3.setUserName(names1[i]);
            results1.add(mapUserInfo3);
        }
        for (int i = 0; i < names2.length; i++) {
            MapUserInfo mapUserInfo3 = new MapUserInfo();
            mapUserInfo3.setUserImgId(img2[i]);
            mapUserInfo3.setUserName(names2[i]);
            results2.add(mapUserInfo3);
        }
        results.add(results1);
        results.add(results2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
