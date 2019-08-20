package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.PlusImageActivity;
import com.patrol.terminal.adapter.TssxPhotoAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.CheckProjectBean;
import com.patrol.terminal.bean.CheckProjectStatusBean;
import com.patrol.terminal.bean.InitiateProjectBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.StringUtil;
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

//项目立项添加
public class InitiateProjectAddActivity extends BaseActivity {

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
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.tv_project_no)
    TextView tvProjectNo;
    @BindView(R.id.edit_total_money)
    EditText editTotalMoney;
    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.edit_detailed_address)
    EditText editDetailedAddress;
    @BindView(R.id.edit_dep_name)
    EditText editDepName;
    @BindView(R.id.edit_parent_project)
    EditText editParentProject;
    @BindView(R.id.edit_model)
    EditText editModel;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.edit_type_sign)
    EditText editTypeSign;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.defect_gridView)
    GridView defectGridView;

    private int type = 0;
    private ArrayList<String> photoList = new ArrayList<>();
    private TssxPhotoAdapter photoAdapter;
    private int position;//点击图片项
    private Map<String, RequestBody> params = new HashMap<>();
    private InitiateProjectBean initiateProjectBean;
    private String mSelectProjectStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_project_add);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        initiateProjectBean = (InitiateProjectBean)intent.getSerializableExtra("InitiateProjectBean");
        type = intent.getIntExtra("type", 0);

        titleName.setText("项目");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("提交");

        photoAdapter = new TssxPhotoAdapter(this, photoList);
        defectGridView.setAdapter(photoAdapter);
        defectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                if(initiateProjectBean != null){
                    viewPluImg(position);
                } else {
                    startCamera();
                }
            }
        });

        if(initiateProjectBean != null){
            editName.setText(initiateProjectBean.getName());
            tvProjectNo.setText(initiateProjectBean.getProject_no());

            if(initiateProjectBean.getTotal_money() != null){
                editTotalMoney.setText(initiateProjectBean.getTotal_money() + "");
            } else {
                editTotalMoney.setText("");
            }

            editAddress.setText(initiateProjectBean.getAddress());
            editDetailedAddress.setText(initiateProjectBean.getDetailed_address());
            editDepName.setText(initiateProjectBean.getDep_name());
            editParentProject.setText(initiateProjectBean.getParent_project());
            editModel.setText(initiateProjectBean.getModel());

            String status = initiateProjectBean.getStatus();
            if(status == null){
                status = "";
            }
            tvStatus.setText(StringUtil.getProjectStatus(status));

            editTypeSign.setText(initiateProjectBean.getType_sign());
            tvStartTime.setText(initiateProjectBean.getStart_time());
            tvEndTime.setText(initiateProjectBean.getEnd_time());
            editContent.setText(initiateProjectBean.getContent());

            Constant.isEditStatus = true;
            if (initiateProjectBean.getTempImgList() != null && initiateProjectBean.getTempImgList().size() > 0) {
                photoList.clear();
                for (int i = 0; i < initiateProjectBean.getTempImgList().size(); i++) {
                    String path = BaseUrl.BASE_URL + initiateProjectBean.getTempImgList().get(i).getFile_path() + initiateProjectBean.getTempImgList().get(i).getFilename();
                    photoList.add(path);
                }
                photoAdapter.setAddStatus(false);
                photoAdapter.notifyDataSetChanged();
            } else {
                defectGridView.setVisibility(View.GONE);
            }

            editName.setEnabled(false);
            editTotalMoney.setEnabled(false);
            editAddress.setEnabled(false);
            editDetailedAddress.setEnabled(false);
            editDepName.setEnabled(false);
            editParentProject.setEnabled(false);
            editModel.setEnabled(false);
            tvStatus.setEnabled(false);
            editTypeSign.setEnabled(false);
            tvStartTime.setEnabled(false);
            tvEndTime.setEnabled(false);
            editContent.setEnabled(false);

            editName.setHint("");
            tvProjectNo.setHint("");
            editTotalMoney.setHint("");
            editAddress.setHint("");
            editDetailedAddress.setHint("");
            editContent.setHint("");
            editDepName.setHint("");
            editParentProject.setHint("");
            tvStatus.setHint("");
            editModel.setHint("");
            editTypeSign.setHint("");
            tvStartTime.setHint("");
            tvEndTime.setHint("");

            editModel.setCompoundDrawables(null, null, null, null);
            tvStatus.setCompoundDrawables(null, null, null, null);
            tvStartTime.setCompoundDrawables(null, null, null, null);
            tvEndTime.setCompoundDrawables(null, null, null, null);

            titleSetting.setVisibility(View.GONE);
        } else {
            Constant.isEditStatus = false;
            tvProjectNo.setText(System.currentTimeMillis() + "");
            String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
            tvStartTime.setText(time);
            tvEndTime.setText(time);
        }
    }

    public void projectSavePOST() {
        ProgressDialog.show(this, false, "正在加载。。。。");
        params.clear();
        params.put("name", toRequestBody(editName.getText().toString()));
        params.put("project_no", toRequestBody(tvProjectNo.getText().toString()));
        params.put("total_money", toRequestBody(editTotalMoney.getText().toString()));
        params.put("address", toRequestBody(editAddress.getText().toString()));
        params.put("detailed_address", toRequestBody(editDetailedAddress.getText().toString()));
        params.put("dep_name", toRequestBody(editDepName.getText().toString()));
        params.put("parent_project", toRequestBody(editParentProject.getText().toString()));
        params.put("model", toRequestBody(editModel.getText().toString()));
        params.put("status", toRequestBody(tvStatus.getText().toString()));
        params.put("type_sign", toRequestBody(editTypeSign.getText().toString()));
        params.put("start_time", toRequestBody(tvStartTime.getText().toString()));
        params.put("end_time", toRequestBody(tvEndTime.getText().toString()));
        params.put("content", toRequestBody(editContent.getText().toString()));
        for(int i=0;i<photoList.size();i++){
            if(!photoList.get(i).equals("")){
                File file = new File(photoList.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                params.put("files\"; filename=\"" + i + ".jpg", requestFile);
            }
        }

        BaseRequest.getInstance().getService()
                .projectSavePOST(params)
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

    @OnClick({R.id.title_back, R.id.title_setting, R.id.tv_start_time, R.id.tv_end_time, R.id.tv_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.tv_start_time:
                showDay(1);
                break;
            case R.id.tv_end_time:
                showDay(2);
                break;
            case R.id.tv_status:
                Intent intent = new Intent();
                intent.setClass(this, ProjectStatusSearchActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.title_setting:
                if(TextUtils.isEmpty(editName.getText().toString())){
                    Utils.showToast("请输入项目名称");
                    break;
                }

                if(TextUtils.isEmpty(tvProjectNo.getText().toString())){
                    Utils.showToast("请输入项目编号");
                    break;
                }

                if(TextUtils.isEmpty(editTotalMoney.getText().toString())){
                    Utils.showToast("请输入计划投资");
                    break;
                }

                if(TextUtils.isEmpty(editAddress.getText().toString())){
                    Utils.showToast("请选择项目地点");
                    break;
                }

                if(TextUtils.isEmpty(tvStatus.getText().toString())){
                    Utils.showToast("请选择项目状态");
                    break;
                }

                if(TextUtils.isEmpty(tvStartTime.getText().toString())){
                    Utils.showToast("请选择计划开始时间");
                    break;
                }

                if(TextUtils.isEmpty(tvEndTime.getText().toString())){
                    Utils.showToast("请选择计划结束时间");
                    break;
                }

                if(TextUtils.isEmpty(editContent.getText().toString())){
                    Utils.showToast("请输入工程概况");
                    break;
                }

                if(photoList.size() == 0){
                    Utils.showToast("请拍摄一张照片");
                    break;
                }

                projectSavePOST();
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
                    tvStartTime.setText(time);
                } else if(type == 2){
                    tvEndTime.setText(time);
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
                        CheckProjectStatusBean clickedCheckProjectStatusBean = (CheckProjectStatusBean)data.getSerializableExtra("search_project_status_item");
                        if (clickedCheckProjectStatusBean != null) {
                            mSelectProjectStatus = clickedCheckProjectStatusBean.getType();
                            String name = clickedCheckProjectStatusBean.getName();
                            tvStatus.setText(name);
                        }
                    }
                    break;
            }
        }
    }
}
