package com.patrol.terminal.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.liulishuo.filedownloader.FileDownloader;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GroupBean;
import com.patrol.terminal.fragment.AnylyzeFrgment;
import com.patrol.terminal.fragment.HomeFragment;
import com.patrol.terminal.fragment.JXHomeFragment;
import com.patrol.terminal.fragment.MeFragement;
import com.patrol.terminal.fragment.YXTodosManageFragment;
import com.patrol.terminal.sqlite.DefactContentDBHelper;
import com.patrol.terminal.training.TrainingHomeFragment;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class NewMainActivity extends BaseActivity /*implements IRfid.CallbackListener*/ {

    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 1;
    @BindView(R.id.fragment_vp)
    NoScrollViewPager fragmentVp;
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
    @BindView(R.id.todo_num)
    TextView todoNum;
    @BindView(R.id.home_bottom)
    RelativeLayout homeBottom;
    @BindView(R.id.btn)
    Button btn;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;

    private String userId;
    private String jobType;
    private Disposable refreshTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        ButterKnife.bind(this);
        jobType = SPUtil.getString(NewMainActivity.this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        FileDownloader.init(this);
        checkPremission();

        initView();
        //RFIDManager.getRFIDInstance().init(this, "001583EA5423", "", this);

        // timer.schedule(timerTask,1000,1000 * 1000);
        refreshTodo = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("todoRefreshNum")) {
                    String[] split = type.split("@");
                    String num = split[1];
                    if (!"0".equals(num)) {
                        todoNum.setVisibility(View.VISIBLE);
                        todoNum.setText(num);
                    } else {
                        todoNum.setVisibility(View.GONE);
                    }
                }
            }
        });
//        getDefectList();
    }


    public void setFragment(int index) {
        fragmentVp.setCurrentItem(index);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) && !jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
            getGroupName();
        }
    }

    private void initView() {
        mFragments = new ArrayList<>(4);
        userId = SPUtil.getString(this, Constant.USER, Constant.USERID, "");
        // init fragment


        //分配每个职位进来的首页
        if (jobType.contains(Constant.TRAINING_SPECIALIZED)) {     //培训专责
            mFragments.add(new TrainingHomeFragment());
        } else if (jobType.contains("yx")) {//运行专责
            mFragments.add(new HomeFragment());
        } else {
            mFragments.add(new JXHomeFragment());
        }

        //分配每个职位进来的待办
//        if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.REFURBISHMENT_MEMBER)
//                || jobType.contains(Constant.REFURBISHMENT_TEMA_LEADER)
//                || jobType.contains(Constant.TRAINING_SPECIALIZED)) {  //无待办的角色
//            mainExameRb.setVisibility(View.GONE);
//
//            mFragments.add(new TodosManageFragment());  //只是不显示，以免RideoButton点击错乱
//
//        } else {                                                    //有待办的角色
        mainExameRb.setVisibility(View.VISIBLE);

        mFragments.add(new YXTodosManageFragment());

//        }

        mFragments.add(new AnylyzeFrgment());
        mFragments.add(new MeFragement());
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        fragmentVp.setAdapter(mAdapter);
        // register listener
        fragmentVp.addOnPageChangeListener(mPageChangeListener);
        llBottomTab.setOnCheckedChangeListener(mOnCheckedChangeListener);
        fragmentVp.setNoScroll(false);


        DefactContentDBHelper defactContentDBHelper = new DefactContentDBHelper(this);
        Cursor cursor = defactContentDBHelper.queryAll();
        if (cursor == null || cursor.getCount() == 0) {
            defactContentDBHelper.insertAll();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (refreshTodo != null) {
            refreshTodo.dispose();
        }
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
        String month = Integer.parseInt(months[0]) + "";
        String year = years[0];
        String day = Integer.parseInt(days[0]) + "";
        BaseRequest.getInstance().getService()
                .getGroupName(year, month, day, SPUtil.getDepId(this), SPUtil.getUserId(this), "2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GroupBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<GroupBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            GroupBean results = t.getResults();
                            if (results != null) {
                                if ("2".contains(results.getSign())) {
                                    SPUtil.putString(NewMainActivity.this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_TEMA_LEADER + "," + jobType);
                                    jobType = jobType + "," + Constant.RUNNING_SQUAD_TEMA_LEADER;
                                }
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    public RequestBody toRequestBody(String value) {
        if (value != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
            return requestBody;
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            return requestBody;
        }
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        startActivity(new Intent(this, PhotoTestActivity.class));
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

   /* public void getDefectList() {
        BaseRequest.getInstance().getService().getPatrolContent().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolContentBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<PatrolContentBean>> t) throws Exception {
                        List<PatrolContentBean> results = t.getResults();
                        SPUtil.put(NewMainActivity.this,"list","defectList",results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }*/
}
