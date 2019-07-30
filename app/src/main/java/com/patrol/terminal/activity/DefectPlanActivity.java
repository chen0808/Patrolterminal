package com.patrol.terminal.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.patrol.terminal.bean.DefectFragmentDetailBean;
import com.patrol.terminal.bean.DepPersonalBean;
import com.patrol.terminal.bean.DepUserBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.DangerSubmitView;
import com.patrol.terminal.widget.ProgressDialog;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DefectPlanActivity extends BaseActivity {

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
    @BindView(R.id.danger_patrol_content)
    DangerSubmitView dangerPatrolContent;
    @BindView(R.id.danger_patrol_line)
    DangerSubmitView dangerPatrolLine;
    @BindView(R.id.danger_patrol_tower)
    DangerSubmitView dangerPatrolTower;
    @BindView(R.id.danger_patrol_team)
    TextView dangerPatrolTeam;
    @BindView(R.id.danger_patrol_personal)
    TextView dangerPatrolPersonal;
    @BindView(R.id.danger_patrol_start_time)
    TextView dangerPatrolStartTime;
    @BindView(R.id.danger_patrol_end_time)
    TextView dangerPatrolEndTime;
    @BindView(R.id.danger_patrol_save)
    TextView dangerPatrolSave;
    @BindView(R.id.danger_patrol_idea)
    EditText dangerPatrolIdea;
    @BindView(R.id.control_card)
    TextView controlCard;
    @BindView(R.id.ns_control_card)
    NiceSpinner nsControlCard;
    @BindView(R.id.ll_control_card)
    LinearLayout llControlCard;
    private List<DepPersonalBean.UserListBean> personalList;
    private String[] personals;
    private AlertDialog personalDialog;
    private AlertDialog personalGroupDialog;
    private int dutyPositon = 0;
    private String teamName;
    private ArrayList<String> personalSelect = new ArrayList<>();
    private long start = 0;
    private long end = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_plan);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("消缺计划");
        String id = getIntent().getStringExtra("id");
        getDefectDeatail(id);
        getPersonal();
    }

    @OnClick({R.id.title_back, R.id.danger_patrol_team, R.id.danger_patrol_personal, R.id.danger_patrol_start_time, R.id.danger_patrol_end_time, R.id.danger_patrol_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.danger_patrol_team:
                showPersonalGroup();
                break;
            case R.id.danger_patrol_personal:
                showPersonal();
                break;
            case R.id.danger_patrol_start_time:
                showDay(0);
                break;
            case R.id.danger_patrol_end_time:
                showDay(1);

                break;
            case R.id.danger_patrol_save:
                break;
        }
    }

    public void showDay(int type) {
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
                if (type == 0) {
                    String startTime = DateUatil.getDate(date);
                    start = date.getTime();
                    if (end < start && end != 0) {
                        if (date.getTime() < System.currentTimeMillis()) {
                            Toast.makeText(DefectPlanActivity.this, "起始时间不能低于起始时间", Toast.LENGTH_SHORT);
                            return;
                        }
                    }
                    if (!startTime.equals(DateUatil.getTime())) {
                        if (date.getTime() < System.currentTimeMillis()) {
                            Toast.makeText(DefectPlanActivity.this, "起始时间不能低于当天", Toast.LENGTH_SHORT);
                            return;
                        }
                    }
                    dangerPatrolEndTime.setText(startTime);
                } else {
                    end = date.getTime();
                    if (end < start && start != 0) {
                        if (date.getTime() < System.currentTimeMillis()) {
                            Toast.makeText(DefectPlanActivity.this, "结束时间不能低于起始时间", Toast.LENGTH_SHORT);
                            return;
                        }
                    }
                    String endTime = DateUatil.getDate(date);
                    if (!endTime.equals(DateUatil.getTime())) {
                        if (date.getTime() < System.currentTimeMillis()) {
                            Toast.makeText(DefectPlanActivity.this, "结束时间不能低于当天", Toast.LENGTH_SHORT);
                            return;
                        }
                    }
                    dangerPatrolEndTime.setText(endTime);
                }

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

    //获取每个班组组员列表
    public void getPersonal() {

        BaseRequest.getInstance().getService()
                .getDepPersonal(SPUtil.getDepId(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DepPersonalBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<DepPersonalBean> t) throws Exception {
                        personalList = t.getResults().getUserList();
                        personals = new String[personalList.size()];
                        for (int i = 0; i < personalList.size(); i++) {
                            personals[i] = personalList.get(i).getName();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    public void showPersonalGroup() {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择负责人");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setSingleChoiceItems(personals, dutyPositon, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int options1) {

                dutyPositon = options1;
                teamName = personals[options1];
                dangerPatrolTeam.setText(teamName);
                personalSelect.clear();
                String names = dangerPatrolPersonal.getText().toString();
                String[] split = names.split(",");
                for (int i = 0; i < split.length; i++) {
                    String name = split[i];
                    personalSelect.add(name);
                }
                int i = personalSelect.indexOf(teamName);
                if (i != -1) {
                    personalSelect.remove(i);
                    String personal = "";
                    for (int j = 0; j < personalSelect.size(); j++) {
                        String name = personalSelect.get(j);
                        if (j == 0) {
                            personal = name;
                        } else {
                            personal = personal + "," + name;
                        }
                    }
                    dangerPatrolPersonal.setText(personal);
                }
                personalGroupDialog.dismiss();
            }
        });

        personalGroupDialog = alertBuilder.create();
        personalGroupDialog.show();
    }

    public void showPersonal() {
        personalSelect.clear();
        boolean[] booleans = new boolean[personals.length];
        String names = dangerPatrolPersonal.getText().toString();
        String[] split = names.split(",");
        for (int i = 0; i < split.length; i++) {
            String name = split[i];
            personalSelect.add(name);
        }
        for (int i = 0; i < personals.length; i++) {
            String personal = personals[i];
            int i1 = personalSelect.indexOf(personal);
            if (i1 == -1) {
                booleans[i] = false;
            } else {
                booleans[i] = true;
            }
        }
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择组员");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(personals, booleans, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {

                if (isChecked) {
                    personalSelect.add(personals[i]);
                }else {
                    for (int j = 0; j < personalSelect.size(); j++) {
                        String s = personalSelect.get(j);
                        if (s.equals(personals[i])){
                            personalSelect.remove(j);
                            break;
                        }
                    }
                }
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                String personal = "";
                for (int j = 0; j < personalSelect.size(); j++) {
                    String name = personalSelect.get(j);
                    if (j == 0) {
                        personal = name;
                    } else {
                        personal = personal + "," + name;
                    }
                }
                dangerPatrolPersonal.setText(personal);
                if (personalSelect.indexOf(teamName) != -1) {
                    teamName = "";
                    dangerPatrolTeam.setText(teamName);
                }
                personalDialog.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                personalSelect.clear();
                personalDialog.dismiss();
            }
        });


        personalDialog = alertBuilder.create();
        personalDialog.show();
    }

    public void getDefectDeatail(String id) {
        ProgressDialog.show(this,true,"正在加载中。。。");
        BaseRequest.getInstance().getService().getDefectDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DefectFragmentDetailBean>() {
                    @Override
                    protected void onSuccees(BaseResult<DefectFragmentDetailBean> t) throws Exception {
                        DefectFragmentDetailBean bean = t.getResults();
                        dangerPatrolType.setContent(bean.getCategory_name());
                        dangerPatrolDep.setContent(bean.getFind_dep_name());
                        dangerPatrolLine.setContent(bean.getLine_name());
                        dangerPatrolTower.setContent(bean.getTower_name());
                        dangerPatrolLevel.setContent(bean.getGrade_name());
                        dangerPatrolContent.setContent(bean.getContent());
                        dangerPatrolIdea.setText(bean.getDeal_notes());
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }


}
