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
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.CheckProjectBean;
import com.patrol.terminal.bean.UserBean;
import com.patrol.terminal.bean.WorkingLogBean;
import com.patrol.terminal.overhaul.CheckPersonSearchActivity;
import com.patrol.terminal.overhaul.ProjectSearchActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
    private ArrayList<String> photoList = new ArrayList<>();
    private TssxPhotoAdapter photoAdapter;
    private int position;//点击图片项
    private String mSelectProjectId;
    private String mSelectPersonId;
//    private LocalWorkingLogBean localWorkingLogBean;
    private String userId;
    private String userName;
    private Map<String, RequestBody> params = new HashMap<>();
    private WorkingLogBean workingLogBean;

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
//        localWorkingLogBean = (LocalWorkingLogBean)intent.getSerializableExtra("LocalWorkingLogBean");
        workingLogBean = (WorkingLogBean) intent.getSerializableExtra("WorkingLogBean");
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
                if(workingLogBean != null){
                    viewPluImg(position);
                } else {
                    startCamera();
                }

            }
        });

        if(workingLogBean != null){
            tvLogNum.setText(workingLogBean.getLog_no());
            tvProjectName.setText(workingLogBean.getTemp_project_name());
            tvWorkingName.setText(workingLogBean.getUser_name());
            editWorkingNum.setText(workingLogBean.getUser_number() + "");
            tvReportName.setText(workingLogBean.getCreated_user_name());
            editWeatherStatus.setText(workingLogBean.getWeather());
            tvCompileDate.setText(workingLogBean.getCreated_date());
            tvOccurDate.setText(workingLogBean.getHappen_date());
            editMorningTemperature.setText(workingLogBean.getTemperature_am() + "");
            editMiddleTemperature.setText(workingLogBean.getTemperature_noon() + "");
            editAfternoonTemperature.setText(workingLogBean.getTemperature_pm() + "");
            editWorkingRemark.setText(workingLogBean.getConstruction_content());
            editEmergencyRemark.setText(workingLogBean.getEmergency_content());
            editContentRemark.setText(workingLogBean.getWork_content());
            editCheckRemark.setText(workingLogBean.getCheck_content());
            editMaterialsRemark.setText(workingLogBean.getTool_content());
            editVisaRemark.setText(workingLogBean.getPermit_content());
            editFileRemark.setText(workingLogBean.getContact_content());
            editOtherRemark.setText(workingLogBean.getOther_content());

            Constant.isEditStatus = true;
            if (workingLogBean.getTempLogImgList() != null && workingLogBean.getTempLogImgList().size() > 0) {
                photoList.clear();
                for (int i = 0; i < workingLogBean.getTempLogImgList().size(); i++) {
                    String path = BaseUrl.BASE_URL + workingLogBean.getTempLogImgList().get(i).getFile_path() + workingLogBean.getTempLogImgList().get(i).getFilename();
                    photoList.add(path);
                }
                photoAdapter.setAddStatus(false);
                photoAdapter.notifyDataSetChanged();
            } else {
                defectGridView.setVisibility(View.GONE);
            }

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
            String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
            tvCompileDate.setText(time);
            tvOccurDate.setText(time);
        }

//        if(localWorkingLogBean != null){
//            tvLogNum.setText(localWorkingLogBean.getLog_num());
//            tvProjectName.setText(localWorkingLogBean.getProject_name());
//            tvWorkingName.setText(localWorkingLogBean.getWorking_name());
//            editWorkingNum.setText(localWorkingLogBean.getWorking_num());
//            tvReportName.setText(localWorkingLogBean.getReport_name());
//            editWeatherStatus.setText(localWorkingLogBean.getWeather_status());
//            tvCompileDate.setText(localWorkingLogBean.getCompile_date());
//            tvOccurDate.setText(localWorkingLogBean.getOccurrence_date());
//            editMorningTemperature.setText(localWorkingLogBean.getMorning_temperature());
//            editMiddleTemperature.setText(localWorkingLogBean.getMiddle_temperature());
//            editAfternoonTemperature.setText(localWorkingLogBean.getAfternoon_temperature());
//            editWorkingRemark.setText(localWorkingLogBean.getWorking_remark());
//            editEmergencyRemark.setText(localWorkingLogBean.getEmergency_remark());
//            editContentRemark.setText(localWorkingLogBean.getContent_remark());
//            editCheckRemark.setText(localWorkingLogBean.getCheck_remark());
//            editMaterialsRemark.setText(localWorkingLogBean.getMaterials_remark());
//            editVisaRemark.setText(localWorkingLogBean.getVisa_remark());
//            editFileRemark.setText(localWorkingLogBean.getFile_remark());
//            editOtherRemark.setText(localWorkingLogBean.getOther_remark());
//
//            Constant.isEditStatus = true;
//            photoList.addAll(Utils.strToList(localWorkingLogBean.getProject_photo()));
//            photoAdapter.setAddStatus(false);
//            photoAdapter.notifyDataSetChanged();
//
//            tvProjectName.setEnabled(false);
//            tvWorkingName.setEnabled(false);
//            editWorkingNum.setEnabled(false);
//            tvReportName.setEnabled(false);
//            tvCompileDate.setEnabled(false);
//            tvOccurDate.setEnabled(false);
//            editWeatherStatus.setEnabled(false);
//            editMorningTemperature.setEnabled(false);
//            editMiddleTemperature.setEnabled(false);
//            editAfternoonTemperature.setEnabled(false);
//            editWorkingRemark.setEnabled(false);
//            editEmergencyRemark.setEnabled(false);
//            editContentRemark.setEnabled(false);
//            editCheckRemark.setEnabled(false);
//            editMaterialsRemark.setEnabled(false);
//            editVisaRemark.setEnabled(false);
//            editFileRemark.setEnabled(false);
//            editOtherRemark.setEnabled(false);
//
//            tvReportName.setHint("");
//            editWeatherStatus.setHint("");
//            editMorningTemperature.setHint("");
//            editMiddleTemperature.setHint("");
//            editAfternoonTemperature.setHint("");
//            editWorkingRemark.setHint("");
//            editEmergencyRemark.setHint("");
//            editContentRemark.setHint("");
//            editCheckRemark.setHint("");
//            editMaterialsRemark.setHint("");
//            editVisaRemark.setHint("");
//            editFileRemark.setHint("");
//            editOtherRemark.setHint("");
//
//            tvProjectName.setCompoundDrawables(null, null, null, null);
//            tvWorkingName.setCompoundDrawables(null, null, null, null);
//            tvReportName.setCompoundDrawables(null, null, null, null);
//            tvCompileDate.setCompoundDrawables(null, null, null, null);
//            tvOccurDate.setCompoundDrawables(null, null, null, null);
//
//            titleSetting.setVisibility(View.GONE);
//        } else {
//            Constant.isEditStatus = false;
//        }
    }

    public void projectSavePOST() {
        ProgressDialog.show(this, false, "正在加载。。。。");
        params.clear();
        params.put("log_no", toRequestBody(tvLogNum.getText().toString()));
        params.put("temp_project_id", toRequestBody(mSelectProjectId));
        params.put("temp_project_name", toRequestBody(tvProjectName.getText().toString()));
        params.put("user_name", toRequestBody(tvWorkingName.getText().toString()));
        params.put("user_number", toRequestBody(editWorkingNum.getText().toString()));
        params.put("created_user_id", toRequestBody(""));
        params.put("created_user_name", toRequestBody(tvReportName.getText().toString()));
        params.put("weather", toRequestBody(editWeatherStatus.getText().toString()));
        params.put("created_date", toRequestBody(tvCompileDate.getText().toString()));
        params.put("happen_date", toRequestBody(tvOccurDate.getText().toString()));
        params.put("temperature_am", toRequestBody(editMorningTemperature.getText().toString()));
        params.put("temperature_noon", toRequestBody(editMiddleTemperature.getText().toString()));
        params.put("temperature_pm", toRequestBody(editAfternoonTemperature.getText().toString()));
        params.put("construction_content", toRequestBody(editWorkingRemark.getText().toString()));
        params.put("emergency_content", toRequestBody(editEmergencyRemark.getText().toString()));
        params.put("work_content", toRequestBody(editContentRemark.getText().toString()));
        params.put("check_content", toRequestBody(editCheckRemark.getText().toString()));
        params.put("tool_content", toRequestBody(editMaterialsRemark.getText().toString()));
        params.put("permit_content", toRequestBody(editVisaRemark.getText().toString()));
        params.put("contact_content", toRequestBody(editFileRemark.getText().toString()));
        params.put("other_content", toRequestBody(editOtherRemark.getText().toString()));
        params.put("log_sign", toRequestBody(logType + ""));
        for(int i=0;i<photoList.size();i++){
            if(!photoList.get(i).equals("")){
                File file = new File(photoList.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                params.put("files\"; filename=\"" + i + ".jpg", requestFile);
            }
        }

        BaseRequest.getInstance().getService()
                .logSavePOST(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {

                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if(t.getCode() == 1){
                            Utils.showToast("提交成功");
                            setResult(RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }

                });
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

                projectSavePOST();

//                LocalWorkingLogBean bean = new LocalWorkingLogBean();
//                bean.setType(logType + "");
//                bean.setUser_id(userId);
//                bean.setUser_name(userName);
//                bean.setLog_num(tvLogNum.getText().toString());
//                bean.setProject_id(mSelectProjectId);
//                bean.setProject_name(tvProjectName.getText().toString());
//                bean.setWorking_name(tvWorkingName.getText().toString());
//                bean.setWorking_num(editWorkingNum.getText().toString());
//                bean.setReport_name(tvReportName.getText().toString());
//                bean.setWeather_status(editWeatherStatus.getText().toString());
//                bean.setCompile_date(tvCompileDate.getText().toString());
//                bean.setOccurrence_date(tvOccurDate.getText().toString());
//                bean.setMorning_temperature(editMorningTemperature.getText().toString());
//                bean.setMiddle_temperature(editMiddleTemperature.getText().toString());
//                bean.setAfternoon_temperature(editAfternoonTemperature.getText().toString());
//                bean.setWorking_remark(editWorkingRemark.getText().toString());
//                bean.setEmergency_remark(editEmergencyRemark.getText().toString());
//                bean.setContent_remark(editContentRemark.getText().toString());
//                bean.setCheck_remark(editCheckRemark.getText().toString());
//                bean.setMaterials_remark(editMaterialsRemark.getText().toString());
//                bean.setVisa_remark(editVisaRemark.getText().toString());
//                bean.setFile_remark(editFileRemark.getText().toString());
//                bean.setOther_remark(editOtherRemark.getText().toString());
//                bean.setProject_photo(Utils.listToStr(photoList));
//                bean.save();
//
//                Utils.showToast("提交成功");
//                setResult(RESULT_OK);
//                finish();
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

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constant.IMG_LIST, photoList);
        intent.putExtra("isDelPic", "0");
        intent.putExtra(Constant.POSITION, position);
        startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
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
