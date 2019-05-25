package com.patrol.terminal.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PatrolRecordBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapActivity extends BaseActivity implements AMap.OnMyLocationChangeListener, RouteSearch.OnRouteSearchListener, View.OnClickListener {

    @BindView(R.id.btn_location_test)
    Button btnLocationTest;
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

    private MapView map;
    private AMap aMap;
    private RelativeLayout back;
    private ArrayList<LatLonPoint> locationList = new ArrayList<>();
    private LatLonPoint testLocation;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        checkPremission();
        back = findViewById(R.id.title_back);
        back.setOnClickListener(this);
        titleName.setText("地图");
        map = findViewById(R.id.map);
        map.onCreate(savedInstanceState);

        aMap = map.getMap();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
//        myLocationStyle.interval(20000);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的style
        aMap.setMyLocationEnabled(true);//显示定位蓝点

        aMap.setOnMyLocationChangeListener(this);

        //手势交互
        UiSettings aMapUiSettings = aMap.getUiSettings();
        aMapUiSettings.setRotateGesturesEnabled(false);//禁用旋转

        initData();

        //初始化定位
        initLocation();
        startLocation();
    }

    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 1;
        private void checkPremission() {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE},
                        REQUEST_TAKE_PHOTO_PERMISSION); }
    }

    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        if (null == locationOption) {
            locationOption = new AMapLocationClientOption();
        }
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);

        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(false);
        // 设置是否单次定位
        locationOption.setOnceLocation(false);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        //locationOption.setOnceLocationLatest(cbOnceLastest.isChecked());
        //设置是否使用传感器
        //locationOption.setSensorEnable(cbSensorAble.isChecked());
        //设置是否开启wifi扫描，如果设置为false时同时会停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        //String strInterval = etInterval.getText().toString();
//        if (!TextUtils.isEmpty(strInterval)) {
//            try{
//                // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
//                locationOption.setInterval(Long.valueOf(strInterval));
//            }catch(Throwable e){
//                e.printStackTrace();
//            }
//        }

//        String strTimeout = etHttpTimeout.getText().toString();
//        if(!TextUtils.isEmpty(strTimeout)){
            try{
                // 设置网络请求超时时间
                locationOption.setHttpTimeOut(30000);
            }catch(Throwable e){
                e.printStackTrace();
            }
//        }
    }


    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                if(location.getErrorCode() == 0) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    String locationTime = DateUatil.getTime(location.getTime());
                }

//                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
 //               if(location.getErrorCode() == 0){
 //                   sb.append("定位成功" + "\n");
//                    sb.append("定位类型: " + location.getLocationType() + "\n");
 //                   sb.append("经    度    : " + location.getLongitude() + "\n");
 //                   sb.append("纬    度    : " + location.getLatitude() + "\n");
//                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
//                    sb.append("提供者    : " + location.getProvider() + "\n");
//
//                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
//                    sb.append("角    度    : " + location.getBearing() + "\n");
//                    // 获取当前提供定位服务的卫星个数
//                    sb.append("星    数    : " + location.getSatellites() + "\n");
//                    sb.append("国    家    : " + location.getCountry() + "\n");
//                    sb.append("省            : " + location.getProvince() + "\n");
//                    sb.append("市            : " + location.getCity() + "\n");
//                    sb.append("城市编码 : " + location.getCityCode() + "\n");
//                    sb.append("区            : " + location.getDistrict() + "\n");
//                    sb.append("区域 码   : " + location.getAdCode() + "\n");
//                    sb.append("地    址    : " + location.getAddress() + "\n");
//                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
  //                  sb.append("定位时间: " + DateUatil.getTime(location.getTime()) + "\n");
//                } else {
//                    //定位失败
//                    sb.append("定位失败" + "\n");
//                    sb.append("错误码:" + location.getErrorCode() + "\n");
//                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
//                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
//                }
//                sb.append("***定位质量报告***").append("\n");
//                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启":"关闭").append("\n");
//                //sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
//                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
//                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
//                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
//                sb.append("****************").append("\n");
//                //定位之后的回调时间
//                //sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
//
//                //解析定位结果，
//                //String result = sb.toString();
//                //tvResult.setText(result);
//            } else {
//                //tvResult.setText("定位失败，loc is null");
           }
        }
    };

    private void initData() {
        BaseRequest.getInstance().getService().sendPatrolRecord("zhangsan", "2019-04-09").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolRecordBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<PatrolRecordBean>> t) throws Exception {
                        Log.e("", t.toString());
                        Toast.makeText(MapActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < t.getResults().size(); i++) {
                            locationList.add(new LatLonPoint(t.getResults().get(i).getLat(), t.getResults().get(i).getLon()));
                        }
                        drawLocation();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.e("", e.getMessage());
                    }
                });
    }

    private void addMarkers() {
        for (int i = 1; i < locationList.size(); i++) {
            MarkerOptions position = new MarkerOptions().position(new LatLng(locationList.get(i).getLatitude(), locationList.get(i).getLongitude()));
            aMap.addMarker(position);
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        locationList.add(new LatLonPoint(location.getLatitude(), location.getLongitude()));
        Bundle bundle = location.getExtras();
        String address = bundle.getString("Address");
        Intent intent = new Intent();
        intent.putExtra("address", address);
        setResult(Constant.MAP_REQUEST_CODE, intent);
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        DrivePath drivePath = driveRouteResult.getPaths().get(0);
        List<DriveStep> steps = drivePath.getSteps();
        PolylineOptions option = new PolylineOptions();
        option.color(Color.RED);
        for (DriveStep step : steps) {
            List<LatLonPoint> polyline = step.getPolyline();
            for (LatLonPoint latLonPoint : polyline) {
                option.add(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()));
            }
        }
        aMap.addPolyline(option);
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @OnClick(R.id.btn_location_test)
    public void onViewClicked() {
        testLocation = new LatLonPoint(locationList.get(0).getLatitude() + (Math.random() - 0.5) * 0.03, locationList.get(0).getLongitude() + (Math.random() - 0.5) * 0.03);
        locationList.add(testLocation);

        drawLocation();
    }

    private void drawLocation() {
        if (locationList != null && locationList.size() > 1) {
            addMarkers();

            //路线规划
            RouteSearch routeSearch = new RouteSearch(this);
            routeSearch.setRouteSearchListener(this);

            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(locationList.get(0), locationList.get(locationList.size() - 1));
            List<LatLonPoint> pointList = new ArrayList<>();
            for (int i = 1; i < locationList.size() - 1; i++) {
                pointList.add(locationList.get(i));
            }
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, 0, pointList, null, "");
            routeSearch.calculateDriveRouteAsyn(query);
        }
    }
}
