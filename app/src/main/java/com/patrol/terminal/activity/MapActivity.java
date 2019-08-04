package com.patrol.terminal.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DepBean;
import com.patrol.terminal.bean.DepPersonalBean;
import com.patrol.terminal.bean.LocationBean;
import com.patrol.terminal.bean.PositionListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.MapNameSelectDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapActivity extends BaseActivity implements /*AMap.OnMyLocationChangeListener,*/ /*RouteSearch.OnRouteSearchListener,*/
        View.OnClickListener, AMap.OnMarkerClickListener, AMap.OnMapClickListener, AMap.InfoWindowAdapter/*, MapNameSelectDialog.PopWindowItemClick*/ {

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
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.name_tv)
    TextView nameTv;

    private AMap aMap;
    private RelativeLayout back;
    private List<LocationBean> locationList = new ArrayList<>();

//    private AMapLocationClient locationClient = null;
//    private AMapLocationClientOption locationOption = null;
    private boolean isPopWindowShow = false;
    //private MapNameSelectDialog.PopWindowItemClick itemClick;
    private TimePickerView pvTime;
    private String year, month, day;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        // checkPremission();
        back = findViewById(R.id.title_back);
        back.setOnClickListener(this);
        titleName.setText("轨迹");
        map = findViewById(R.id.map);
        map.onCreate(savedInstanceState);

        aMap = map.getMap();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
//        myLocationStyle.interval(20000);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的style
        aMap.setMyLocationEnabled(true);//显示定位蓝点

        //aMap.setOnMyLocationChangeListener(this);

        //手势交互
        UiSettings aMapUiSettings = aMap.getUiSettings();
        aMapUiSettings.setRotateGesturesEnabled(false);//禁用旋转

        aMap.setOnMarkerClickListener(this);
        aMap.setOnMapClickListener(this);
        aMap.setInfoWindowAdapter(this);

        initData();

        //初始化定位
        //initLocation();

    }

//    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 1;
//        private void checkPremission() {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.CAMERA,
//                                Manifest.permission.ACCESS_COARSE_LOCATION,
//                                Manifest.permission.ACCESS_FINE_LOCATION,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.READ_PHONE_STATE},
//                        REQUEST_TAKE_PHOTO_PERMISSION); }
//    }

//    /**
//     * 初始化定位
//     *
//     * @since 2.8.0
//     * @author hongming.wang
//     *
//     */
//    private void initLocation(){
//        //初始化client
//        locationClient = new AMapLocationClient(this.getApplicationContext());
//        locationOption = getDefaultOption();
//        //设置定位参数
//        locationClient.setLocationOption(locationOption);
//        // 设置定位监听
//        locationClient.setLocationListener(locationListener);
//        startLocation();
//    }

//    /**
//     * 默认的定位参数
//     * @since 2.8.0
//     * @author hongming.wang
//     *
//     */
//    private AMapLocationClientOption getDefaultOption(){
//        AMapLocationClientOption mOption = new AMapLocationClientOption();
//        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
//        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
//        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
//        mOption.setInterval(120 *1000);//可选，设置定位间隔。默认为2秒
//        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
//        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
//        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
//        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
//        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
//        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
//        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
//        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
//        return mOption;
//    }
//
//    /**
//     * 开始定位
//     *
//     * @since 2.8.0
//     * @author hongming.wang
//     *
//     */
//    private void startLocation(){
//        if (null == locationOption) {
//            locationOption = new AMapLocationClientOption();
//        }
//        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        locationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);
//
//        //根据控件的选择，重新设置定位参数
//        resetOption();
//        // 设置定位参数
//        locationClient.setLocationOption(locationOption);
//        // 启动定位
//        locationClient.startLocation();
//    }
//
//    // 根据控件的选择，重新设置定位参数
//    private void resetOption() {
//        // 设置是否需要显示地址信息
//        locationOption.setNeedAddress(true);
//        /**
//         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
//         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
//         */
//        locationOption.setGpsFirst(true);
//        // 设置是否开启缓存
//        locationOption.setLocationCacheEnable(false);
//        // 设置是否单次定位
//        locationOption.setOnceLocation(false);
//
//            try{
//                // 设置网络请求超时时间
//                locationOption.setHttpTimeOut(30000);
//            }catch(Throwable e){
//                e.printStackTrace();
//            }
//    }


//    /**
//     * 定位监听
//     */
//    AMapLocationListener locationListener = new AMapLocationListener() {
//        @Override
//        public void onLocationChanged(AMapLocation location) {
//            if (null != location) {
//                if(location.getErrorCode() == 0) {
//                    double longitude = location.getLongitude();
//                    double latitude = location.getLatitude();
//                    String locationTime = DateUatil.getTime(location.getTime());
//                }
//
//           }
//        }
//    };

    private void initData() {
        String userId = SPUtil.getString(this, Constant.USER, Constant.USERID, "");
        String date = DateUatil.getTime() + "%";
        BaseRequest.getInstance().getService().getPositonList(userId, date).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PositionListBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<PositionListBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<PositionListBean> positionListBeans = t.getResults();
                            for (int i = 0; i < positionListBeans.size(); i++) {
                                LocationBean locationBean = new LocationBean();
                                LatLng latLng = new LatLng(positionListBeans.get(i).getLat(), positionListBeans.get(i).getLon());
                                locationBean.setLatLng(latLng);
                                locationBean.setPosition(positionListBeans.get(i).getAddress());
                                locationBean.setTime(positionListBeans.get(i).getLoc_time());
                                locationList.add(locationBean);
                            }
                            aMap.moveCamera(CameraUpdateFactory.changeLatLng(locationList.get(0).getLatLng()));
                            aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                            drawLocation();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.e("", e.getMessage());
                    }
                });

        getDepInfo();
    }

    private void addMarkers() {
        for (int i = 0; i < locationList.size(); i++) {
            MarkerOptions position = new MarkerOptions().position(new LatLng(locationList.get(i).getLatLng().latitude, locationList.get(i).getLatLng().longitude))
                    .title(locationList.get(i).getPosition()).snippet(locationList.get(i).getTime());
            aMap.addMarker(position);
        }
    }

//    @Override
//    public void onMyLocationChange(Location location) {
//        locationList.add(new LatLng(location.getLatitude(), location.getLongitude()));
//        Bundle bundle = location.getExtras();
//        String address = bundle.getString("Address");
//        Intent intent = new Intent();
//        intent.putExtra("address", address);
//        setResult(Constant.MAP_REQUEST_CODE, intent);
//    }
//
//    @Override
//    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
//
//    }
//
//    @Override
//    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
////        DrivePath drivePath = driveRouteResult.getPaths().get(0);
////        List<DriveStep> steps = drivePath.getSteps();
////        PolylineOptions option = new PolylineOptions();
////        option.color(Color.RED);
////        for (DriveStep step : steps) {
////            List<LatLonPoint> polyline = step.getPolyline();
////            for (LatLonPoint latLonPoint : polyline) {
////                option.add(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()));
////            }
////        }
////        aMap.addPolyline(option);
//    }
//
//    @Override
//    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
//
//    }
//
//    @Override
//    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
//
//    }

    @Override
    public void onClick(View v) {
        finish();
    }


    private void drawLocation() {
        if (locationList != null && locationList.size() > 1) {
            addMarkers();

            List<LatLng> latlngList = new ArrayList<>();
            for (int i = 0; i < locationList.size(); i++) {
                LatLng latLng = locationList.get(i).getLatLng();
                latlngList.add(latLng);
            }

            Polyline polyline = aMap.addPolyline(new PolylineOptions().
                    addAll(latlngList).width(10).color(Color.argb(255, 1, 1, 1)));
        }
    }

    private Marker mMarker;

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMarker = marker;
        mMarker.showInfoWindow();
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMarker.hideInfoWindow();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View infoContent = LayoutInflater.from(MapActivity.this).inflate(
                R.layout.item_map_window, null);
        render(marker, infoContent);
        return infoContent;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    public void render(Marker marker, View view) {
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.window_linearlayout);
        //设置透明度
        layout.getBackground().setAlpha(240);
        TextView name = (TextView) view.findViewById(R.id.address_tv);
        TextView info = (TextView) view.findViewById(R.id.time_tv);
        name.setText(mMarker.getTitle());
        info.setText(mMarker.getSnippet());
    }

    @OnClick({R.id.date_tv, R.id.name_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.date_tv:
                //显示时间选择器
                showDay();
                break;
            case R.id.name_tv:
                if (!isPopWindowShow) {
                    showNamePopWindow();
                    isPopWindowShow = true;
                }else {
                    mapNameSelectDialog.dismiss();
                    isPopWindowShow = false;
                }

                break;
        }
    }


    private MapNameSelectDialog mapNameSelectDialog;
    private void showNamePopWindow() {
        if (depList != null && depList.size() > 0) {
            mapNameSelectDialog = new MapNameSelectDialog(MapActivity.this, depList, classMemberList);
            mapNameSelectDialog.setOutsideTouchable(true);
            mapNameSelectDialog.setCallback(new MapNameSelectDialog.PopWindowItemClick() {
                @Override
                public void setNameAndId(String name, String id) {
                    nameTv.setText(name);
                    if (isPopWindowShow) {
                        mapNameSelectDialog.dismiss();
                        isPopWindowShow = false;
                    }

                }
            });
            mapNameSelectDialog.showAsDropDown(dateTv);
        }else {
            Toast.makeText(MapActivity.this,"未获取到班组数据!", Toast.LENGTH_SHORT).show();
        }

    }

    private List<DepBean> depList = new ArrayList<>();
    private void getDepInfo() {
        //获取所有班组
        BaseRequest.getInstance().getService()
                .getDepList("SYS_DEP", "ID,name", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DepBean>>(MapActivity.this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DepBean>> t) throws Exception {
                        depList = t.getResults();
                        if (depList != null && depList.size() > 0) {
                            getClassMember(depList.get(0).getId());  //默认获取第一个班级
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    private List<DepPersonalBean.UserListBean> classMemberList = new ArrayList<>();
    private void getClassMember(String depId) {
        BaseRequest.getInstance().getService()
                .getDepPersonal(depId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DepPersonalBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<DepPersonalBean> t) throws Exception {
                        classMemberList = t.getResults().getUserList();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    public void showDay() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time = DateUatil.getDay(date);
                dateTv.setText(time);
                initDate();
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }


    public void initDate() {
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
    }

}
