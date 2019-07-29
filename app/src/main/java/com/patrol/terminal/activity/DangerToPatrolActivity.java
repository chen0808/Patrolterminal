package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DangerPatrolReqBean;
import com.patrol.terminal.bean.InAuditTroubleReqBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.DangerSubmitView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DangerToPatrolActivity extends BaseActivity {


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
    @BindView(R.id.danger_patrol_type)
    DangerSubmitView dangerPatrolType;
    @BindView(R.id.danger_patrol_dep)
    DangerSubmitView dangerPatrolDep;
    @BindView(R.id.danger_patrol_level)
    DangerSubmitView dangerPatrolLevel;
    @BindView(R.id.danger_patrol_line)
    DangerSubmitView dangerPatrolLine;
    @BindView(R.id.danger_patrol_tower)
    DangerSubmitView dangerPatrolTower;
    @BindView(R.id.danger_patrol_end_time)
    TextView dangerPatrolEndTime;
    @BindView(R.id.danger_patrol_date)
    EditText dangerPatrolDate;
    @BindView(R.id.danger_patrol_save)
    TextView dangerPatrolSave;
    private String type_id;
    private String id;
    private String f_id;
    private String time="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_to_patrol);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("隐患巡视计划");
        String danger_type = getIntent().getStringExtra("danger_type");
        String danger_level = getIntent().getStringExtra("danger_level");
        String line_name = getIntent().getStringExtra("line_name");
        String dep_name = getIntent().getStringExtra("dep_name");
        String tower_name = getIntent().getStringExtra("tower_name");
        type_id = getIntent().getStringExtra("type_id");
        id = getIntent().getStringExtra("id");
        f_id = getIntent().getStringExtra("f_id");
        dangerPatrolType.setContent(danger_type);
        dangerPatrolLevel.setContent(danger_level);
        dangerPatrolDep.setContent(dep_name);
        dangerPatrolLine.setContent(line_name);
        dangerPatrolTower.setContent(tower_name);

        switch (danger_level) {
            case "一般":
                dangerPatrolDate.setText("30");
                break;
            case "重大":
                dangerPatrolDate.setText("7");
                break;
            case "紧急":
                dangerPatrolDate.setText("1");
                break;
            default:
                dangerPatrolDate.setText("30");
                break;
        }
    }


    @OnClick({R.id.title_back, R.id.danger_patrol_end_time, R.id.danger_patrol_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.danger_patrol_end_time:
                showDay();
                break;
            case R.id.danger_patrol_save:
                createDangerPatrol();
                break;
        }
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
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time = DateUatil.getDate(date);
                dangerPatrolEndTime.setText(time);
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

    public void createDangerPatrol() {
        String day = dangerPatrolDate.getText().toString().trim();
        if ("".equals(time)){
            Toast.makeText(this,"请先选择结束日期",Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(day)){
            Toast.makeText(this,"请先选择周期",Toast.LENGTH_SHORT).show();
            return;
        }
        DangerPatrolReqBean bean = new DangerPatrolReqBean();

        bean.setF_id(f_id);
        bean.setId(id);
        bean.setClose_time(time);
        bean.setType_id(type_id);
        bean.setPatrol_cycle(Integer.parseInt(day));
        BaseRequest.getInstance().getService()
                .createDangerPatrol(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        Toast.makeText(DangerToPatrolActivity.this, "处理成功", Toast.LENGTH_SHORT).show();

                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
}
