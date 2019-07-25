package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DefectFragmentDetailBean;
import com.patrol.terminal.bean.InAuditPostBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialogNew;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 复核任务制定
 */
public class ReviewTaskActivity extends BaseActivity {

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
    @BindView(R.id.day_plan_time)
    TextView dayPlanTime;
    @BindView(R.id.defect_find_dep_name)
    TextView defectFindDepName;
    @BindView(R.id.defect_line_name)
    TextView defectLineName;
    @BindView(R.id.tower_name)
    TextView towerName;

    private String year;
    private String month;
    private String day;
    private Disposable subscribe;
    private DefectFragmentDetailBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_task);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        Intent intent = getIntent();
        if(intent != null){
            bean = (DefectFragmentDetailBean)intent.getSerializableExtra("DefectFragmentDetailBean");
        }

        if (bean.getFind_dep_name() != null) {
            defectFindDepName.setText(bean.getFind_dep_name());
        }

        if(bean.getLine_name() != null){
            defectLineName.setText(bean.getLine_name());
        }

        if(bean.getTower_name() != null){
            towerName.setText(bean.getTower_name());
        }

        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        dayPlanTime.setText(time);
        initdate(time);

        titleName.setText("复核任务制定");
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {
            @Override
            public void accept(String type) throws Exception {
                if (type.equals("hide")) {
                    hideBottomUIMenu();
                } else if (type.equals("show")) {
                    showBottomUIMenu();
                }
            }
        });
    }

    @OnClick({R.id.title_back, R.id.give_mine, R.id.give_group, R.id.day_plan_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.day_plan_time:
                showDay();
                break;
            case R.id.give_mine:
                submit(0);
                break;
            case R.id.give_group:
                submit(1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //缺陷再复核-转班组
    public void defectCheckPOSTGroup() {
        ProgressDialog.show(this, false, "正在加载。。。。");
        InAuditPostBean inAuditPostBean = new InAuditPostBean();
        inAuditPostBean.setId(bean.getId());
        inAuditPostBean.setIn_status("5");
        inAuditPostBean.setFrom_user_id(SPUtil.getUserId(this));
        inAuditPostBean.setFrom_user_name(SPUtil.getUserName(this));
        inAuditPostBean.setLine_id(bean.getLine_id());
        inAuditPostBean.setLine_name(bean.getLine_name());
        inAuditPostBean.setTower_id(bean.getTower_id());
        inAuditPostBean.setTower_name(bean.getTower_name());
        inAuditPostBean.setFind_dep_id(bean.getFind_dep_id() + "");
        inAuditPostBean.setFind_dep_name(bean.getFind_dep_name() + "");
        inAuditPostBean.setYear(year);
        inAuditPostBean.setMonth(month);
        inAuditPostBean.setDay(day);
        BaseRequest.getInstance().getService()
                .defectCheckPOSTGroup(inAuditPostBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {

                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if(t.getCode() == 1){
                            Toast.makeText(ReviewTaskActivity.this,"处理完成",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(ReviewTaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }

    //缺陷再复核-转班组
    public void defectCheckPOSTMine() {
        ProgressDialog.show(this, false, "正在加载。。。。");
        InAuditPostBean inAuditPostBean = new InAuditPostBean();
        inAuditPostBean.setId(bean.getId());
        inAuditPostBean.setIn_status("5");
        inAuditPostBean.setFrom_user_id(SPUtil.getUserId(this));
        inAuditPostBean.setFrom_user_name(SPUtil.getUserName(this));
        inAuditPostBean.setLine_id(bean.getLine_id());
        inAuditPostBean.setLine_name(bean.getLine_name());
        inAuditPostBean.setTower_id(bean.getTower_id());
        inAuditPostBean.setTower_name(bean.getTower_name());
        inAuditPostBean.setFind_dep_id(bean.getFind_dep_id() + "");
        inAuditPostBean.setFind_dep_name(bean.getFind_dep_name() + "");
        inAuditPostBean.setYear(year);
        inAuditPostBean.setMonth(month);
        inAuditPostBean.setDay(day);
        BaseRequest.getInstance().getService()
                .defectCheckPOSTMine(inAuditPostBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {

                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if(t.getCode() == 1){
                            Toast.makeText(ReviewTaskActivity.this,"处理完成",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(ReviewTaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }

    //提交缺陷审核
    public void submit(int type) {
        if(type == 0){
            CancelOrOkDialogNew dialog = new CancelOrOkDialogNew(ReviewTaskActivity.this, "转自己", "取消", "确定") {
                @Override
                public void ok() {
                    super.ok();
                    defectCheckPOSTMine();
                }

                @Override
                public void cancle() {
                    super.cancle();
                }
            };
            dialog.show();
        } else if(type == 1){
            CancelOrOkDialogNew dialog = new CancelOrOkDialogNew(ReviewTaskActivity.this, "转自己", "取消", "确定") {
                @Override
                public void ok() {
                    super.ok();
                    defectCheckPOSTGroup();
                }

                @Override
                public void cancle() {
                    super.cancle();
                }
            };
            dialog.show();
        }
    }

    //初始化日期
    public void initdate(String time) {
        String[] times = time.split("年");
        String[] months = times[1].split("月");
        year = times[0];
        month = months[0];
        day = months[1].split("日")[0];
    }

    public void showDay() {
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        int curMonth = Integer.parseInt(months[0]);
        int curYear = Integer.parseInt(years[0]);
        int curDay = Integer.parseInt(days[0]);
        if(curMonth == 1){
            curMonth = 12;
            curYear = curYear - 1;
        } else {
            curMonth = curMonth - 1;
        }

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(curYear, curMonth, curDay);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = DateUatil.getDay(date);
                dayPlanTime.setText(time);
                initdate(time);
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

    protected void hideBottomUIMenu() {

        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                //布局位于状态栏下方
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                //全屏
//                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                //隐藏导航栏
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= 19) {
            uiOptions |= 0x00001000;
        } else {
            uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

    }

    protected void showBottomUIMenu() {
        //显示虚拟按键
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            //低版本sdk
            View v = getWindow().getDecorView();
            v.setSystemUiVisibility(View.VISIBLE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE ;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
