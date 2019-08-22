package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.BudgetActivity;
import com.patrol.terminal.adapter.GridViewAdapter5;
import com.patrol.terminal.adapter.GridePagerAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MapUserInfo;
import com.patrol.terminal.bean.ProjectBoardBean;
import com.patrol.terminal.utils.DateUatil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//检修新首页
public class NewJXHomeFragment extends BaseFragment implements WeatherSearch.OnWeatherSearchListener, AMapLocationListener {

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
    @BindView(R.id.jx_home_date)
    TextView jxHomeDate;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.ll_ed)
    LinearLayout llEd;
    @BindView(R.id.ll_ing)
    LinearLayout llIng;
    @BindView(R.id.jx_home_weather)
    TextView jxHomeWeather;
    @BindView(R.id.tv_project_total)
    TextView tvProjectTotal;
    @BindView(R.id.tv_budget_total)
    TextView tvBudgetTotal;
    @BindView(R.id.tv_project_ed)
    TextView tvProjectEd;
    @BindView(R.id.tv_persent_ed)
    TextView tvPersentEd;
    @BindView(R.id.tv_project_ing)
    TextView tvProjectIng;
    @BindView(R.id.tv_persent_ing)
    TextView tvPersentIng;
    private GridViewAdapter5 weekAdapter;

    private String[] names1 = new String[]{"项目看板", "形象进度", "里程碑", "质量检查", "施工日志", "工程简报", "工程周报", "安全检查"};
    private int[] img1 = new int[]{R.mipmap.jx_home_icon1, R.mipmap.jx_home_icon2, R.mipmap.jx_home_icon3, R.mipmap.jx_home_icon4, R.mipmap.jx_home_icon5, R.mipmap.jx_home_icon6, R.mipmap.jx_home_icon7, R.mipmap.jx_home_icon8};
    private String[] names2 = new String[]{"项目立项", "设计计划", "电子公告", "内部新闻", "技术规范",};
    private int[] img2 = new int[]{R.mipmap.jx_home_icon12, R.mipmap.jx_home_icon13, R.mipmap.jx_home_icon9, R.mipmap.jx_home_icon10, R.mipmap.jx_home_icon11};
    private List<List<MapUserInfo>> results = new ArrayList<>();
    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String city = "武汉";
    private double budgetTotal;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jx_home, container, false);
        return view;
    }

    @Override
    protected void initData() {
        titleBack.setVisibility(View.GONE);
        titleName.setText("电力检修工程管理");
        paomadeng.setSelected(true);
        init();
        getCity();
        jxHomeDate.setText(DateUatil.getTime() + "   " + DateUatil.getWeeks());
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
                        jxPoint1.setBackgroundResource(R.drawable.week_bg);
                        jxPoint2.setBackgroundResource(R.drawable.point);
                        break;
                    case 1:
                        jxPoint1.setBackgroundResource(R.drawable.point);
                        jxPoint2.setBackgroundResource(R.drawable.week_bg);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initProjectBoard();
    }

    private void initProjectBoard() {
        BaseRequest.getInstance().getService()
                .getProjectBoardList("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ProjectBoardBean>>(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult<List<ProjectBoardBean>> t) throws Exception {
                        List<ProjectBoardBean> results = t.getResults();
                        if (results != null && results.size() > 0) {
                            setData(results);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void setData(List<ProjectBoardBean> results) {
        tvProjectTotal.setText("" + results.size());
        for (int i = 0; i < results.size(); i++) {
            budgetTotal += results.get(i).getTotal_money();
        }
        tvBudgetTotal.setText("" + budgetTotal / 10000.0);
        tvProjectEd.setText("2");
        int projectIng = results.size() - 2;
        tvProjectIng.setText(String.valueOf(projectIng));
        tvPersentEd.setText(2 * 100 / results.size() + "%");
        tvPersentIng.setText(projectIng * 100 / results.size() + "%");
    }

    //獲取定位城市
    private void getCity() {
        mlocationClient = new AMapLocationClient(getContext());
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(600000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mlocationClient.startLocation();

    }

    //获取天气
    private void getWeather() {
        mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mweathersearch = new WeatherSearch(getContext());
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
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

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                placeAndTemp.setText(city + "   " + weatherlive.getTemperature() + "°C");
                jxHomeWeather.setText(weatherlive.getWeather() + "    " + weatherlive.getWindDirection() + "风     " + weatherlive.getWindPower() + "级");
                if (weatherlive.getWeather().contains("雨")) {
                    weatherIcon.setBackgroundResource(R.mipmap.jx_xiayu);
                } else if (weatherlive.getWeather().contains("阳")) {
                    weatherIcon.setBackgroundResource(R.mipmap.jx_taiyang);
                } else if (weatherlive.getWeather().contains("云")) {
                    weatherIcon.setBackgroundResource(R.mipmap.jx_duoyun);
                } else if (weatherlive.getWeather().contains("阴")) {
                    weatherIcon.setBackgroundResource(R.mipmap.jx_yin);
                }
            } else {
            }
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult weatherLiveResult, int rCode) {

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                city = amapLocation.getCity();
                getWeather();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @OnClick({R.id.ll_total, R.id.ll_ed, R.id.ll_ing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_total:
                Intent intent = new Intent(getActivity(), BudgetActivity.class);
                intent.putExtra("status", "");
                startActivity(intent);
                break;
            case R.id.ll_ing:
                Intent intent2 = new Intent(getActivity(), BudgetActivity.class);
                intent2.putExtra("status", "0");
                startActivity(intent2);
                break;
            case R.id.ll_ed:
                Intent intent1 = new Intent(getActivity(), BudgetActivity.class);
                intent1.putExtra("status", "16");
                startActivity(intent1);
                break;
        }
    }
}
