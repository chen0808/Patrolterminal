package com.patrol.terminal.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.external.rfid.IRfid;
import com.liulishuo.filedownloader.FileDownloader;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GroupBean;
import com.patrol.terminal.bean.PatrolRecordBean;
import com.patrol.terminal.bean.PositionInfo;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.fragment.AnylyzeFrgment;
import com.patrol.terminal.fragment.HomeFragment;
import com.patrol.terminal.fragment.JXHomeFragment;
import com.patrol.terminal.fragment.MeFragement;
import com.patrol.terminal.fragment.TodosManageFragment;
import com.patrol.terminal.fragment.YXTodosManageFragment;
import com.patrol.terminal.fragment.ZyHomeFragment;
import com.patrol.terminal.rfid.RFIDManager;
import com.patrol.terminal.training.TrainingHomeFragment;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class NewMainActivity extends BaseActivity /*implements IRfid.CallbackListener*/ {

    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 1;
    @BindView(R.id.fragment_vp)
    ViewPager fragmentVp;
    @BindView(R.id.main_home_rb)
    RadioButton mainHomeRb;
    @BindView(R.id.main_exame_rb)
    RadioButton mainExameRb;
    @BindView(R.id.main_history_rb)
    RadioButton mainHistoryRb;
    @BindView(R.id.main_my_rb)
    RadioButton mainMyRb;
    @BindView(R.id.ll_bottom_tab)
    RadioGroup llBottomTab;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        ButterKnife.bind(this);

        FileDownloader.init(this);
        checkPremission();
        initView();

        //RFIDManager.getRFIDInstance().init(this, "001583EA5423", "", this);
        //初始化定位
        initLocation();
       // timer.schedule(timerTask,1000,1000 * 1000);
    }

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
                    REQUEST_TAKE_PHOTO_PERMISSION);
        }
    }

    private void initView() {
        mFragments = new ArrayList<>(4);
        userId = SPUtil.getString(this, Constant.USER, Constant.USERID, "");
        // init fragment
        String jobType = SPUtil.getString(NewMainActivity.this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);

        if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)) {
            getGroupName();
        }

        if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.REFURBISHMENT_MEMBER)
                || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER) || jobType.contains(Constant.REFURBISHMENT_TEMA_LEADER)
                || jobType.contains(Constant.TRAINING_SPECIALIZED)) {  //无待办的角色
            mainExameRb.setVisibility(View.GONE);

            if (jobType.contains(Constant.TRAINING_SPECIALIZED)) {     //培训专责
                mFragments.add(new TrainingHomeFragment());
            }else  if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)||jobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)||jobType.contains(Constant.MAINTENANCE_SUPERVISOR)){
                mFragments.add(new HomeFragment());
            }else  if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)||jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)){
                mFragments.add(new ZyHomeFragment());
            }else {
                mFragments.add(new JXHomeFragment());
            }

            mFragments.add(new TodosManageFragment());  //只是不显示，以免RideoButton点击错乱

        }else {                                                    //有待办的角色
            mainExameRb.setVisibility(View.VISIBLE);

            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)){
                mFragments.add(new HomeFragment());
            }else {
                mFragments.add(new JXHomeFragment());
            }

            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)||jobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)){   //运行待办
                mFragments.add(new YXTodosManageFragment());
            } else {   //其他角色待办
                mFragments.add(new TodosManageFragment());
            }

        }

        mFragments.add(new AnylyzeFrgment());
        mFragments.add(new MeFragement());
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        fragmentVp.setAdapter(mAdapter);
        // register listener
        fragmentVp.addOnPageChangeListener(mPageChangeListener);
        llBottomTab.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentVp.removeOnPageChangeListener(mPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) llBottomTab.getChildAt(position);
            radioButton.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    fragmentVp.setCurrentItem(i);
                    return;
                }
            }
        }
    };

//    @Override
//    public void callback(boolean b, int i, String s) {
//        runOnUiThread(() -> {
//            Log.w("linmeng", "RFID 初始化成功!");
//        });
//    }

    //获取是否是运行班负责人
    public void getGroupName() {
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
       String month = Integer.parseInt(months[0])+"";
        String year =years[0];
        String day=Integer.parseInt(days[0])+"";
        BaseRequest.getInstance().getService()
                .getGroupName(year,month,day,SPUtil.getDepId(this),SPUtil.getUserId(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GroupBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<GroupBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            GroupBean results = t.getResults();
                            if (results!=null){
                                if ("1".contains(results.getIs_boss())){
                                    SPUtil.putString(NewMainActivity.this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_TEMA_LEADER);
                                }
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
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

        startLocation();
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
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2 * 60 * 1000);//可选，设置定位间隔。默认为60秒
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
        try{
            // 设置网络请求超时时间
            locationOption.setHttpTimeOut(30000);
        }catch(Throwable e){
            e.printStackTrace();
        }
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
                    String address = location.getAddress();
                    if (TextUtils.isEmpty(address)) {
                        address = "甘肃省兰州市安宁区";
                    }


                    PositionInfo positionInfo = new PositionInfo();
                    positionInfo.setLat(latitude);
                    positionInfo.setLon(longitude);
                    positionInfo.setAddress(address);
                    positionInfo.setLoc_time(locationTime);
//            positionInfo.setData_id(null);
//            positionInfo.setSign(null);
                    positionInfo.setUser_id(userId);
                    setPositon(positionInfo);

                    Log.w("linmeng", "longitude:" + longitude + ",latitude:" + latitude + ",locationTime:" + locationTime + ",address:" + address);
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

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 1){
//                setPositon();
//            }
//            super.handleMessage(msg);
//        }
//    };

    private void setPositon(PositionInfo poitionInfo)
    {
        Log.w("linmeng","setPosition poitionInfo:" + poitionInfo.getLat());
            BaseRequest.getInstance().getService().setPosition(poitionInfo).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<TypeBean>(this) {
                        @Override
                        protected void onSuccees(BaseResult<TypeBean> t) throws Exception {
                            if (t.getCode() == 1) {
                                t.getResults();
                            }

                        }

                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                            Log.e("", e.getMessage());
                        }
                    });

    }

//    Timer timer = new Timer();
//    TimerTask timerTask = new TimerTask() {
//        @Override
//        public void run() {
//            Message message = new Message();
//            message.what = 1;
//            handler.sendMessage(message);
//        }
//    };

}
