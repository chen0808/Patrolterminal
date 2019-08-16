package com.patrol.terminal.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.TssxPhotoAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.CheckProjectBean;
import com.patrol.terminal.bean.LocalWorkingLogBean;
import com.patrol.terminal.bean.UserBean;
import com.patrol.terminal.overhaul.CheckPersonSearchActivity;
import com.patrol.terminal.overhaul.ProjectSearchActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//施工日志
public class WorkingLogDetailActivity extends BaseActivity {

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
    @BindView(R.id.tv_log_num)
    TextView tvLogNum;
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.tv_working_name)
    TextView tvWorkingName;
    @BindView(R.id.edit_working_num)
    TextView editWorkingNum;
    @BindView(R.id.tv_report_name)
    TextView tvReportName;
    @BindView(R.id.edit_weather_status)
    TextView editWeatherStatus;
    @BindView(R.id.tv_compile_date)
    TextView tvCompileDate;
    @BindView(R.id.tv_occurrence_date)
    TextView tvOccurDate;
    @BindView(R.id.edit_morning_temperature)
    TextView editMorningTemperature;
    @BindView(R.id.edit_middle_temperature)
    TextView editMiddleTemperature;
    @BindView(R.id.edit_afternoon_temperature)
    TextView editAfternoonTemperature;
    @BindView(R.id.edit_working_remark)
    TextView editWorkingRemark;
    @BindView(R.id.edit_emergency_remark)
    TextView editEmergencyRemark;
    @BindView(R.id.edit_content_remark)
    TextView editContentRemark;
    @BindView(R.id.edit_check_remark)
    TextView editCheckRemark;
    @BindView(R.id.edit_materials_remark)
    TextView editMaterialsRemark;
    @BindView(R.id.edit_visa_remark)
    TextView editVisaRemark;
    @BindView(R.id.edit_file_remark)
    TextView editFileRemark;
    @BindView(R.id.edit_other_remark)
    TextView editOtherRemark;
    @BindView(R.id.defect_gridView)
    GridView defectGridView;

    private int logType = 0;
    private List<String> photoList = new ArrayList<>();
    private TssxPhotoAdapter photoAdapter;
    private int position;//点击图片项
    private String mSelectProjectId;
    private String mSelectPersonId;
    private LocalWorkingLogBean localWorkingLogBean;
    private String userId;
    private String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_detail_log);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        userId = SPUtil.getUserId(this);
        userName = SPUtil.getUserName(this);

        Intent intent = getIntent();
        localWorkingLogBean = (LocalWorkingLogBean)intent.getSerializableExtra("LocalWorkingLogBean");
        logType = intent.getIntExtra("logType", 0);
        switch (logType) {
            case 1:
                titleName.setText("施工方日志");
                break;
            case 2:
                titleName.setText("监理方日志");
                break;
            case 3:
                titleName.setText("建设方日志");
                break;
            default:
                break;
        }

        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("保存");

        photoAdapter = new TssxPhotoAdapter(this, photoList);
        defectGridView.setAdapter(photoAdapter);
        defectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                startCamera();
            }
        });

        if(localWorkingLogBean != null){
            tvLogNum.setText(localWorkingLogBean.getLog_num());
            tvProjectName.setText(localWorkingLogBean.getProject_name());
            tvWorkingName.setText(localWorkingLogBean.getWorking_name());
            editWorkingNum.setText(localWorkingLogBean.getWorking_num());
            tvReportName.setText(localWorkingLogBean.getReport_name());
            editWeatherStatus.setText(localWorkingLogBean.getWeather_status());
            tvCompileDate.setText(localWorkingLogBean.getCompile_date());
            tvOccurDate.setText(localWorkingLogBean.getOccurrence_date());
            editMorningTemperature.setText(localWorkingLogBean.getMorning_temperature());
            editMiddleTemperature.setText(localWorkingLogBean.getMiddle_temperature());
            editAfternoonTemperature.setText(localWorkingLogBean.getAfternoon_temperature());
            editWorkingRemark.setText(localWorkingLogBean.getWorking_remark());
            editEmergencyRemark.setText(localWorkingLogBean.getEmergency_remark());
            editContentRemark.setText(localWorkingLogBean.getContent_remark());
            editCheckRemark.setText(localWorkingLogBean.getCheck_remark());
            editMaterialsRemark.setText(localWorkingLogBean.getMaterials_remark());
            editVisaRemark.setText(localWorkingLogBean.getVisa_remark());
            editFileRemark.setText(localWorkingLogBean.getFile_remark());
            editOtherRemark.setText(localWorkingLogBean.getOther_remark());

            Constant.isEditStatus = true;
            photoList.addAll(Utils.strToList(localWorkingLogBean.getProject_photo()));
            photoAdapter.setAddStatus(false);
            photoAdapter.notifyDataSetChanged();

            tvProjectName.setEnabled(false);
            tvWorkingName.setEnabled(false);
            editWorkingNum.setEnabled(false);
            tvReportName.setEnabled(false);
            tvCompileDate.setEnabled(false);
            tvOccurDate.setEnabled(false);
            editWeatherStatus.setEnabled(false);
            editMorningTemperature.setEnabled(false);
            editMiddleTemperature.setEnabled(false);
            editAfternoonTemperature.setEnabled(false);
            editWorkingRemark.setEnabled(false);
            editEmergencyRemark.setEnabled(false);
            editContentRemark.setEnabled(false);
            editCheckRemark.setEnabled(false);
            editMaterialsRemark.setEnabled(false);
            editVisaRemark.setEnabled(false);
            editFileRemark.setEnabled(false);
            editOtherRemark.setEnabled(false);

            tvReportName.setHint("");
            editWeatherStatus.setHint("");
            editMorningTemperature.setHint("");
            editMiddleTemperature.setHint("");
            editAfternoonTemperature.setHint("");
            editWorkingRemark.setHint("");
            editEmergencyRemark.setHint("");
            editContentRemark.setHint("");
            editCheckRemark.setHint("");
            editMaterialsRemark.setHint("");
            editVisaRemark.setHint("");
            editFileRemark.setHint("");
            editOtherRemark.setHint("");

            tvProjectName.setCompoundDrawables(null, null, null, null);
            tvWorkingName.setCompoundDrawables(null, null, null, null);
            tvReportName.setCompoundDrawables(null, null, null, null);
            tvCompileDate.setCompoundDrawables(null, null, null, null);
            tvOccurDate.setCompoundDrawables(null, null, null, null);

            titleSetting.setVisibility(View.GONE);
        } else {
            Constant.isEditStatus = false;
        }
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.tv_compile_date, R.id.tv_occurrence_date, R.id.tv_project_name, R.id.tv_working_name, R.id.tv_report_name})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.tv_compile_date:
                showDay(1);
                break;
            case R.id.tv_occurrence_date:
                showDay(2);
                break;
            case R.id.tv_project_name:
                intent = new Intent();
                intent.setClass(this, ProjectSearchActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.tv_working_name:
                intent = new Intent();
                intent.setClass(this, CheckPersonSearchActivity.class);
                startActivityForResult(intent, 1002);
                break;
            case R.id.tv_report_name:
                intent = new Intent();
                intent.setClass(this, CheckPersonSearchActivity.class);
                startActivityForResult(intent, 1003);
                break;
            case R.id.title_setting:
                if(TextUtils.isEmpty(tvProjectName.getText().toString())){
                    Utils.showToast("请选择所属项目");
                    break;
                }

                if(TextUtils.isEmpty(tvWorkingName.getText().toString())){
                    Utils.showToast("请选择施工人员");
                    break;
                }

                if(TextUtils.isEmpty(editWorkingNum.getText().toString())){
                    Utils.showToast("请填写施工人数");
                    break;
                }

                if(TextUtils.isEmpty(tvCompileDate.getText().toString())){
                    Utils.showToast("请选择编制日期");
                    break;
                }

                if(TextUtils.isEmpty(tvOccurDate.getText().toString())){
                    Utils.showToast("请选择发生日期");
                    break;
                }

                LocalWorkingLogBean bean = new LocalWorkingLogBean();
                bean.setType(logType + "");
                bean.setUser_id(userId);
                bean.setUser_name(userName);
                bean.setLog_num(tvLogNum.getText().toString());
                bean.setProject_id(mSelectProjectId);
                bean.setProject_name(tvProjectName.getText().toString());
                bean.setWorking_name(tvWorkingName.getText().toString());
                bean.setWorking_num(editWorkingNum.getText().toString());
                bean.setReport_name(tvReportName.getText().toString());
                bean.setWeather_status(editWeatherStatus.getText().toString());
                bean.setCompile_date(tvCompileDate.getText().toString());
                bean.setOccurrence_date(tvOccurDate.getText().toString());
                bean.setMorning_temperature(editMorningTemperature.getText().toString());
                bean.setMiddle_temperature(editMiddleTemperature.getText().toString());
                bean.setAfternoon_temperature(editAfternoonTemperature.getText().toString());
                bean.setWorking_remark(editWorkingRemark.getText().toString());
                bean.setEmergency_remark(editEmergencyRemark.getText().toString());
                bean.setContent_remark(editContentRemark.getText().toString());
                bean.setCheck_remark(editCheckRemark.getText().toString());
                bean.setMaterials_remark(editMaterialsRemark.getText().toString());
                bean.setVisa_remark(editVisaRemark.getText().toString());
                bean.setFile_remark(editFileRemark.getText().toString());
                bean.setOther_remark(editOtherRemark.getText().toString());
                bean.setProject_photo(Utils.listToStr(photoList));
                bean.save();

                Utils.showToast("提交成功");
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    public void showDay(int type) {
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
                if(type == 1){
                    tvCompileDate.setText(time);
                } else if(type == 2){
                    tvOccurDate.setText(time);
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

    //打开相机
    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constant.DEFECT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.DEFECT_REQUEST_CODE:
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    String path = Environment.getExternalStorageDirectory().getPath()
                            + "/MyPhoto/" + DateUatil.getDateStr() + "_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                    try {
                        //保存本地成功 刷新刷新数据添加到页面
                        FileUtil.saveFile(bitmap, path);
                        if (position == photoList.size()) {
                            photoList.add(path);
                        } else {
                            photoList.set(position, path);
                        }

                        photoAdapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1001:
                    if (data != null) {
                        CheckProjectBean clickedCheckProjectBean = data.getParcelableExtra("search_project_item");
                        if (clickedCheckProjectBean != null) {
                            mSelectProjectId = clickedCheckProjectBean.getProject_id();
                            String name = clickedCheckProjectBean.getName();
                            tvProjectName.setText(name);
                        }
                    }
                    break;
                case 1002:
                    if (data != null) {
                        UserBean clickedUserBean = data.getParcelableExtra("search_user_item");
                        if (clickedUserBean != null) {
                            mSelectPersonId = clickedUserBean.getId();
                            String name = clickedUserBean.getName();
                            tvWorkingName.setText(name);
                        }
                    }
                    break;
                case 1003:
                    if (data != null) {
                        UserBean clickedUserBean = data.getParcelableExtra("search_user_item");
                        if (clickedUserBean != null) {
                            mSelectPersonId = clickedUserBean.getId();
                            String name = clickedUserBean.getName();
                            tvReportName.setText(name);
                        }
                    }
                    break;
            }
        }
    }
}
