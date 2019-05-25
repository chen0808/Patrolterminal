package com.patrol.terminal.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.external.rfid.IRfid;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        ButterKnife.bind(this);

        FileDownloader.init(this);
        checkPremission();
        initView();

        //RFIDManager.getRFIDInstance().init(this, "001583EA5423", "", this);
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

        // init fragment
        mFragments = new ArrayList<>(4);
        String jobType = SPUtil.getString(NewMainActivity.this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (jobType.equals(Constant.RUNNING_SQUAD_LEADER)||jobType.equals(Constant.RUNNING_SQUAD_SPECIALIZED)||jobType.equals(Constant.REFURBISHMENT_SPECIALIZED)||jobType.equals(Constant.REFURBISHMENT_LEADER)){
            mainExameRb.setVisibility(View.VISIBLE);
        }
        if (jobType.equals(Constant.RUNNING_SQUAD_MEMBER)) {
            getGroupName();

        }
        if (jobType.equals(Constant.TRAINING_SPECIALIZED)) {
            mFragments.add(new TrainingHomeFragment());  //培训专责
        }  else  if (jobType.equals(Constant.RUNNING_SQUAD_MEMBER)||jobType.equals(Constant.RUNNING_SQUAD_TEMA_LEADER)){
            mFragments.add(new ZyHomeFragment());
        }else  if (jobType.equals(Constant.RUNNING_SQUAD_LEADER)){
            mFragments.add(new HomeFragment());;
        }else{
            mFragments.add(new JXHomeFragment());
        }
        if (jobType.equals(Constant.RUNNING_SQUAD_LEADER)||jobType.equals(Constant.RUNNING_SQUAD_SPECIALIZED)){
            mFragments.add(new YXTodosManageFragment());
        } else if (jobType.equals(Constant.REFURBISHMENT_SPECIALIZED)||jobType.equals(Constant.REFURBISHMENT_LEADER)){
            mFragments.add(new TodosManageFragment());

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
                                if ("1".equals(results.getIs_boss())){
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

}
