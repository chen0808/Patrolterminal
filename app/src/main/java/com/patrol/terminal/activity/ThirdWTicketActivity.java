package com.patrol.terminal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.TaskContentAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.AddressBookLevel2;
import com.patrol.terminal.bean.ClassMemberBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.SignBean;
import com.patrol.terminal.bean.TaskContentBean;
import com.patrol.terminal.bean.ThirdTicketBean;
import com.patrol.terminal.bean.TicketSafeBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.PickerUtils;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.SignDialog;

import java.io.File;
import java.util.ArrayList;
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

/*电力线路带电作业工作票*/
public class ThirdWTicketActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

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
    @BindView(R.id.unit_tv)
    TextView unitTv;
    @BindView(R.id.tv_unit_id)
    TextView tvUnitId;
    @BindView(R.id.number_tv)
    TextView numberTv;
    @BindView(R.id.et_ticket_number)
    TextView etTicketNumber;
    @BindView(R.id.tv_leader_id)
    TextView tvLeaderId;
    @BindView(R.id.teams_and_groups_tv)
    TextView teamsAndGroupsTv;
    @BindView(R.id.tv_dep_id)
    TextView tvDepId;
    @BindView(R.id.staff_in_the_workshop_tv)
    TextView staffInTheWorkshopTv;
    @BindView(R.id.tv_crew_id)
    TextView tvCrewId;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.iv_task_add)
    ImageView ivTaskAdd;
    @BindView(R.id.rv_task_content)
    RecyclerView rvTaskContent;
    @BindView(R.id.planned_working_hours)
    TextView plannedWorkingHours;
    @BindView(R.id.tv_s_time)
    TextView tvSTime;
    @BindView(R.id.tv_e_time)
    TextView tvETime;
    @BindView(R.id.et_stop_reclose)
    EditText etStopReclose;
    @BindView(R.id.et_work_conditions)
    EditText etWorkConditions;
    @BindView(R.id.et_remark_safe)
    TextView etRemarkSafe;
    @BindView(R.id.iv_signature_pad)
    ImageView ivSignaturePad;
    @BindView(R.id.time_checkbox)
    CheckBox timeCheckbox;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.iv_signature_pad_2)
    ImageView ivSignaturePad2;
    @BindView(R.id.et_licensor)
    EditText etLicensor;
    @BindView(R.id.time_checkbox_3)
    CheckBox timeCheckbox3;
    @BindView(R.id.time_tv_3)
    TextView timeTv3;
    @BindView(R.id.iv_signature_pad_3)
    ImageView ivSignaturePad3;
    @BindView(R.id.et_supply_safety_measures)
    EditText etSupplySafetyMeasures;
    @BindView(R.id.iv_signature_pad_4)
    ImageView ivSignaturePad4;
    @BindView(R.id.iv_signature_pad_5)
    ImageView ivSignaturePad5;
    @BindView(R.id.time_checkbox_5)
    CheckBox timeCheckbox5;
    @BindView(R.id.time_tv_5)
    TextView timeTv5;
    @BindView(R.id.et_remark)
    EditText etRemark;
    private List<AddressBookLevel2> nameList = new ArrayList<>();
    private List<File> mPicList = new ArrayList<>();
    private List<TaskContentBean> taskContentBeans = new ArrayList<>();
    private TaskContentAdapter contentAdapter;
    private Map<String, RequestBody> params = new HashMap<>();
    private String taskId;
    private String leaderName;
    private String leaderId;
    private String ticketType;
    private String ticketTaskType;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_working_ticket);
        ButterKnife.bind(this);
        initview();


    }

    private void initview() {
        titleName.setText("电力线路带电作业工作票");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("提交");
        String jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");

        leaderName = getIntent().getStringExtra("leaderName");
        leaderId = getIntent().getStringExtra("leaderId");
        ticketType = getIntent().getStringExtra("type");
        ticketTaskType = getIntent().getStringExtra("task_type");

        if (jobType.equals(Constant.REFURBISHMENT_SPECIALIZED)) {  //专责接受的Bean不一样
            OverhaulYearBean bean = getIntent().getParcelableExtra("bean");

            if (bean != null) {
                taskId = bean.getId();
                //status = bean.getStatus();   //TODO  linmeng
                tvUnitId.setText(bean.getRepair_content());//单位
                etTicketNumber.setText("暂无");//编号
                tvDepId.setText("带电作业班");//班组
                tvLeaderId.setText(leaderName);//班组负责人
                tvSTime.setText(bean.getStart_time());//开始时间
                tvETime.setText(bean.getEnd_time());//结束时间
            }

        } else {
            OverhaulMonthBean bean = getIntent().getParcelableExtra("bean");
//
//            if (bean != null) {
//                taskId = bean.getRepair_id();
//                status = bean.getRepair_status();
//                OverhaulMonthBean.PlanRepairBean planRepairBean = bean.getPlanRepair();
//                tvUnitId.setText(planRepairBean.getRepair_content());
//                etTicketNumber.setText("暂无");
//                tvDepId.setText("带电作业班");
//                tvLeaderId.setText(leaderName);
//                tvSTime.setText(planRepairBean.getStart_time());
//                tvETime.setText(planRepairBean.getEnd_time());
//            }
        }

        if (status.equals(Constant.STATUS_PRINCPIAL)) {
            etSupplySafetyMeasures.setEnabled(false);
            etWorkConditions.setEnabled(false);
            etStopReclose.setEnabled(false);
            etLicensor.setEnabled(false);
            etRemark.setEnabled(false);
            etTicketNumber.setEnabled(false);
            etRemarkSafe.setEnabled(false);
        } else {
            timeCheckbox.setOnCheckedChangeListener(this);
            timeCheckbox3.setOnCheckedChangeListener(this);
            timeCheckbox5.setOnCheckedChangeListener(this);
        }

        //注意事项
        BaseRequest.getInstance().getService().getTicketSafe(ticketType, ticketTaskType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TicketSafeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TicketSafeBean>> t) throws Exception {
                        List<TicketSafeBean> results = t.getResults();
                        if (results != null && results.size() > 0) {
                            String safeWay = results.get(0).getSafe_way();
                            String remarkSafe = safeWay.replace("<p>", "").replace("</p>", "");
                            String remarkSafe2 = remarkSafe.replace("。（", "。\n  （");
                            etRemarkSafe.setText(remarkSafe2);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
        //已填写数据
        BaseRequest.getInstance().getService().searchThirdTicket(taskId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ThirdTicketBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<ThirdTicketBean> t) throws Exception {
                        ThirdTicketBean results = t.getResults();
                        if (results == null) {
                            rvTaskContent.setLayoutManager(new LinearLayoutManager(ThirdWTicketActivity.this));
                            contentAdapter = new TaskContentAdapter(R.layout.item_task_content, taskContentBeans);
                            rvTaskContent.setAdapter(contentAdapter);
                        } else {
                            setData(results);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.d("TAG", e.getMessage());
                    }
                });
    }

    private ThirdTicketBean getData() {
        ThirdTicketBean bean = new ThirdTicketBean();
        bean.setCrew_id(tvCrewId.getText().toString());
        bean.setS_time(tvSTime.getText().toString());
        bean.setE_time(tvETime.getText().toString());
        bean.setStop_reclose(etStopReclose.getText().toString());
        bean.setWork_conditions(etWorkConditions.getText().toString());
        bean.setSign_time(timeTv.getText().toString());
        bean.setRegulation_time(timeTv3.getText().toString());
        bean.setSupply_safety_measures(etSupplySafetyMeasures.getText().toString());
        bean.setFinal_regulation_time(timeTv5.getText().toString());

        bean.setRemark(etRemark.getText().toString());
        bean.setWorkList(taskContentBeans);
        return bean;
    }

    private void setData(ThirdTicketBean results) {
        //工作任务
        taskContentBeans.clear();
        if (results.getWorkList() != null && results.getWorkList().size() > 0) {
            taskContentBeans.addAll(results.getWorkList());
        }
        rvTaskContent.setLayoutManager(new LinearLayoutManager(this));
        contentAdapter = new TaskContentAdapter(R.layout.item_task_content, taskContentBeans);
        rvTaskContent.setAdapter(contentAdapter);

        tvCrewId.setText(results.getCrew_id());
        String crewId = results.getCrew_id() == null ? "" : results.getCrew_id();
        tvPerson.setText("共" + (crewId.length() - crewId.replace(" ", "").length()) + "人");
        tvSTime.setText(results.getS_time());
        tvETime.setText(results.getE_time());
        etStopReclose.setText(results.getStop_reclose());
        etWorkConditions.setText(results.getWork_conditions());
        timeTv.setText(results.getSign_time());
        timeTv3.setText(results.getRegulation_time());
        etSupplySafetyMeasures.setText(results.getSupply_safety_measures());
        timeTv5.setText(results.getFinal_regulation_time());

        showPic(results.getFileList(), ivSignaturePad, "sign1.jpg");
        showPic(results.getFileList(), ivSignaturePad2, "princpial1.jpg");
        showPic(results.getFileList(), ivSignaturePad3, "leader1.jpg");
        showPic(results.getFileList(), ivSignaturePad4, "accept1.jpg");
        showPic(results.getFileList(), ivSignaturePad5, "princpial2.jpg");
    }

    private Map<String, RequestBody> setParams(ThirdTicketBean bean) {
        params.put("task_id", toRequestBody(taskId));
        params.put("type", toRequestBody(ticketType));
        params.put("task_type", toRequestBody(ticketTaskType));
        params.put("crew_id", toRequestBody(bean.getCrew_id()));
        params.put("s_time", toRequestBody(bean.getS_time()));
        params.put("e_time", toRequestBody(bean.getE_time()));
        params.put("stop_reclose", toRequestBody(bean.getStop_reclose()));
        params.put("work_conditions", toRequestBody(bean.getWork_conditions()));
        params.put("sign_time", toRequestBody(bean.getSign_time()));
        params.put("regulation_time", toRequestBody(bean.getRegulation_time()));
        params.put("supply_safety_measures", toRequestBody(bean.getSupply_safety_measures()));
        params.put("final_regulation_time", toRequestBody(bean.getFinal_regulation_time()));
        params.put("remark", toRequestBody(bean.getRemark()));

        for (int i = 0; i < bean.getWorkList().size(); i++) {
            params.put("workList[" + i + "].eq_name", toRequestBody(bean.getWorkList().get(i).getWork_name()));
            params.put("workList[" + i + "].work_range", toRequestBody(bean.getWorkList().get(i).getWork_range()));
            params.put("workList[" + i + "].work_content", toRequestBody(bean.getWorkList().get(i).getWork_content()));
        }

        for (int i = 0; i < mPicList.size(); i++) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), mPicList.get(i));
            params.put("file\"; filename=\"" + mPicList.get(i).getName(), requestFile);
        }
        return params;
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

    private void showPic(List<ThirdTicketBean.FileListBean> list, ImageView iv, String fileName) {
        for (ThirdTicketBean.FileListBean fileListBean : list) {
            if (fileListBean.getContent().equals(fileName)) {
                Glide.with(this).asBitmap().load(BaseUrl.BASE_URL + fileListBean.getFile_path() + fileListBean.getFilename()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        iv.setImageBitmap(resource);
                        Log.d("TAG", BaseUrl.BASE_URL + fileListBean.getFile_path() + fileListBean.getFilename());
                        if (iv.getDrawable() != null) {
                            File file = SignDialog.saveBitmapFile(resource, fileName.replace(".jpg", ""));
                            mPicList.add(file);
                        }
                    }
                });
            }
        }
    }

    private void getAllSendToPerson() {
        BaseRequest.getInstance().getService()
                .getAllClassMember("B7FF21A674F144DE8D13EB8B3B79E64F")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ClassMemberBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<ClassMemberBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            if (t.getResults() != null && t.getResults().size() > 0) {
                                List<ClassMemberBean.UserListBean> userListBeans = t.getResults().get(0).getUserList();
                                String[] workers = new String[userListBeans.size()];
                                String[] workers_id = new String[userListBeans.size()];
                                for (int i = 0; i < userListBeans.size(); i++) {
                                    String userName = userListBeans.get(i).getName();
                                    String userId = userListBeans.get(i).getId();
                                    workers[i] = userName;
                                    workers_id[i] = userId;
                                }
                                showSingleChooseDialog("工作班人员", workers);
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }

    public void showSingleChooseDialog(String title, String[] workers) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        boolean[] checkedItems = new boolean[workers.length];
        builder.setMultiChoiceItems(workers, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedItems[which] = isChecked;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameArray = "";
                int j = 0;
                for (int i = 0; i < workers.length; i++) {
                    if (checkedItems[i]) {
                        nameArray += workers[i] + " ";
                        j++;
                    }
                }
                tvCrewId.setText(nameArray);
                tvPerson.setText("(共" + j + "人)");
                dialog.dismiss();

            }
        });
        builder.show();
    }

    private void addPicList() {
        if (ivSignaturePad.getDrawable() != null) {
            File file1 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad).getDrawable()).getBitmap(), "sign1");
            mPicList.add(file1);
        }
        if (ivSignaturePad2.getDrawable() != null) {
            File file2 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad2).getDrawable()).getBitmap(), "princpial1");
            mPicList.add(file2);
        }
        if (ivSignaturePad3.getDrawable() != null) {
            File file3 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad3).getDrawable()).getBitmap(), "leader1");
            mPicList.add(file3);
        }
        if (ivSignaturePad4.getDrawable() != null) {
            File file4 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad4).getDrawable()).getBitmap(), "accept1");
            mPicList.add(file4);
        }
        if (ivSignaturePad5.getDrawable() != null) {
            File file5 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad5).getDrawable()).getBitmap(), "princpial2");
            mPicList.add(file5);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap bitmap = SignBean.getBitmap();
        int index = SignBean.getIndex();
        if (bitmap != null) {
            switch (index) {
                case 1:
                    ivSignaturePad.setImageBitmap(bitmap);
                    break;
                case 2:
                    ivSignaturePad2.setImageBitmap(bitmap);
                    break;
                case 3:
                    ivSignaturePad3.setImageBitmap(bitmap);
                    break;
                case 4:
                    ivSignaturePad4.setImageBitmap(bitmap);
                    break;
                case 5:
                    ivSignaturePad5.setImageBitmap(bitmap);
                    break;

            }
        }
        SignBean.setBitmap(null);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.time_checkbox:
                if (isChecked) {
                    timeTv.setText(DateUatil.getCurrTime());
                } else {
                    timeTv.setText(Constant.WORK_TICKET_TIME);
                }

                break;

            case R.id.time_checkbox_3:
                if (isChecked) {
                    timeTv3.setText(DateUatil.getCurrTime());
                } else {
                    timeTv3.setText(Constant.WORK_TICKET_TIME);
                }

                break;

            case R.id.time_checkbox_5:
                if (isChecked) {
                    timeTv5.setText(DateUatil.getCurrTime());
                } else {
                    timeTv5.setText(Constant.WORK_TICKET_TIME);
                }
                break;
        }

    }

    @OnClick({R.id.title_back, R.id.tv_crew_id, R.id.iv_signature_pad, R.id.iv_signature_pad_2,
            R.id.iv_signature_pad_3, R.id.iv_signature_pad_4, R.id.iv_signature_pad_5,
            R.id.title_setting, R.id.iv_task_add, R.id.tv_s_time, R.id.tv_e_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.tv_crew_id:
//                Intent intent = new Intent(this, AddressBookActivity.class);
//                intent.putExtra("nameList", (Serializable) nameList);
//                startActivityForResult(intent, Constant.REQUEST_CODE_ADDRESS_BOOK);
                getAllSendToPerson();
                break;
            case R.id.tv_s_time:
                PickerUtils.showDate(ThirdWTicketActivity.this, tvSTime);
                break;
            case R.id.tv_e_time:
                PickerUtils.showDate(ThirdWTicketActivity.this, tvETime);
                break;
            case R.id.iv_signature_pad:
                if (ticketType != null && status.equals(Constant.STATUS_SIGN)) {
                    Intent intent = new Intent();
                    intent.setClass(this, SignActivity.class);
                    startActivity(intent);
                    SignBean.setIndex(1);
                }
                break;
            case R.id.iv_signature_pad_2:
                if (ticketType != null && status.equals(Constant.STATUS_PRINCPIAL)) {
                    Intent intent = new Intent();
                    intent.setClass(this, SignActivity.class);
                    startActivity(intent);
                    SignBean.setIndex(2);
                }
                break;
            case R.id.iv_signature_pad_3:
                if (ticketType != null && status.equals(Constant.STATUS_LEADER)) {
                    Intent intent = new Intent();
                    intent.setClass(this, SignActivity.class);
                    startActivity(intent);
                    SignBean.setIndex(3);
                }
                break;
            case R.id.iv_signature_pad_4:
                if (ticketType != null && status.equals(Constant.STATUS_ACCEPT)) {
                    Intent intent = new Intent();
                    intent.setClass(this, SignActivity.class);
                    startActivity(intent);
                    SignBean.setIndex(4);
                }
                break;
            case R.id.iv_signature_pad_5:
                if (ticketType != null && status.equals(Constant.STATUS_PRINCPIAL)) {
                    Intent intent = new Intent();
                    intent.setClass(this, SignActivity.class);
                    startActivity(intent);
                    SignBean.setIndex(5);
                }
                break;
            case R.id.title_setting:
                ThirdTicketBean bean = getData();
                addPicList();
                Map<String, RequestBody> params = setParams(bean);
                BaseRequest.getInstance().getService().upLoadThirdTicket(params).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            protected void onSuccees(BaseResult t) throws Exception {
                                Toast.makeText(ThirdWTicketActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                Toast.makeText(ThirdWTicketActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.iv_task_add:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_task_add, null);
                builder.setView(dialogView);
                EditText et1 = dialogView.findViewById(R.id.et_1);
                EditText et2 = dialogView.findViewById(R.id.et_2);
                EditText et3 = dialogView.findViewById(R.id.et_3);
                builder.show().setOnDismissListener(listener -> {
                    if (et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty() || et3.getText().toString().isEmpty()) {
                        Toast.makeText(ThirdWTicketActivity.this, "请补全任务信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    taskContentBeans.add(new TaskContentBean(et1.getText().toString(), et2.getText().toString(), et3.getText().toString()));
                    contentAdapter.setNewData(taskContentBeans);
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_ADDRESS_BOOK) {
                nameList = (List<AddressBookLevel2>) data.getSerializableExtra("nameList");
                String nameArray = "";
                int j = 0;
                for (int i = 0; i < nameList.size(); i++) {
                    if (nameList.get(i).isTag()) {
                        nameArray += nameList.get(i).getContent() + " ";
                        j++;
                    }
                }
                tvCrewId.setText(nameArray);
                tvPerson.setText("共" + j + "人");
            }
        }
    }
}
