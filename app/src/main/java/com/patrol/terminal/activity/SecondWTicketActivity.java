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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.TaskContentAdapter;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.AddressBookLevel2;
import com.patrol.terminal.bean.ClassMemberBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.SecondTicketBean;
import com.patrol.terminal.bean.SignBean;
import com.patrol.terminal.bean.TaskContentBean;
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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SecondWTicketActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


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
    @BindView(R.id.planned_working_hours)
    TextView plannedWorkingHours;
    @BindView(R.id.tv_s_time)
    TextView tvSTime;
    @BindView(R.id.tv_e_time)
    TextView tvETime;
    @BindView(R.id.et_remark_safe)
    TextView etRemarkSafe;
    @BindView(R.id.iv_signature_pad)
    ImageView ivSignaturePad;
    @BindView(R.id.cb_group_time_a)
    CheckBox cbGroupTimeA;
    @BindView(R.id.tv_group_time_a)
    TextView tvGroupTimeA;
    @BindView(R.id.iv_signature_pad_2)
    ImageView ivSignaturePad2;
    @BindView(R.id.cb_group_time_b)
    CheckBox cbGroupTimeB;
    @BindView(R.id.tv_group_time_b)
    TextView tvGroupTimeB;
    @BindView(R.id.iv_signature_pad_3)
    ImageView ivSignaturePad3;
    @BindView(R.id.cb_group_time_c)
    CheckBox cbGroupTimeC;
    @BindView(R.id.tv_group_time_c)
    TextView tvGroupTimeC;
    @BindView(R.id.iv_signature_pad_4)
    ImageView ivSignaturePad4;
    @BindView(R.id.cb_work_a_time)
    CheckBox cbWorkATime;
    @BindView(R.id.tv_work_a_time)
    TextView tvWorkATime;
    @BindView(R.id.iv_signature_pad_5)
    ImageView ivSignaturePad5;
    @BindView(R.id.cb_work_b_time)
    CheckBox cbWorkBTime;
    @BindView(R.id.tv_work_b_time)
    TextView tvWorkBTime;
    @BindView(R.id.tv_delay_time)
    TextView tvDelayTime;
    @BindView(R.id.et_remark)
    EditText etRemark;
    List<MultipartBody.Part> files = new ArrayList<>();
    Map<String, RequestBody> params = new HashMap<>();
    @BindView(R.id.iv_task_add)
    ImageView ivTaskAdd;
    @BindView(R.id.rv_task_content)
    RecyclerView rvTaskContent;
    @BindView(R.id.unit_tv)
    TextView unitTv;
    @BindView(R.id.iv_signature_pad_person)
    ImageView ivSignaturePadPerson;
    private List<AddressBookLevel2> nameList = new ArrayList<>();
    private List<File> mPicList = new ArrayList<>();
    private List<TaskContentBean> taskContentBeans = new ArrayList<>();
    private TaskContentAdapter contentAdapter;
    private String taskId;
    private String leaderName;
    private String leaderId;
    private String ticketType;
    private String ticketTaskType;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_working_ticket);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {
        titleName.setText("电力线路第二种工作票");
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
                //status = bean.getStatus();    //TODO  linmeng
                tvUnitId.setText(bean.getRepair_content());//单位
                etTicketNumber.setText("暂无");//编号
                tvDepId.setText("带电作业班");//班组
                tvLeaderId.setText(leaderName);//班组负责人
                tvSTime.setText(bean.getStart_time());//开始时间
                tvETime.setText(bean.getEnd_time());//结束时间
            }

        } else {
            OverhaulMonthBean bean = getIntent().getParcelableExtra("bean");

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
            etRemark.setEnabled(false);
            etRemarkSafe.setEnabled(false);
            etTicketNumber.setEnabled(false);
        } else {
            cbGroupTimeA.setOnCheckedChangeListener(this);
            cbGroupTimeB.setOnCheckedChangeListener(this);
            cbGroupTimeC.setOnCheckedChangeListener(this);
            cbWorkATime.setOnCheckedChangeListener(this);
            cbWorkBTime.setOnCheckedChangeListener(this);
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
        BaseRequest.getInstance().getService().searchSecondTicket(taskId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SecondTicketBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<SecondTicketBean> t) throws Exception {
                        SecondTicketBean results = t.getResults();
                        if (results == null) {
                            rvTaskContent.setLayoutManager(new LinearLayoutManager(SecondWTicketActivity.this));
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

    //
    private void showPic(List<SecondTicketBean.FileListBean> list, ImageView iv, String fileName) {
        for (SecondTicketBean.FileListBean fileListBean : list) {
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
                tvPerson.setText("共" + j + "人");
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
            File file2 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad2).getDrawable()).getBitmap(), "sign2");
            mPicList.add(file2);
        }
        if (ivSignaturePad3.getDrawable() != null) {
            File file3 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad3).getDrawable()).getBitmap(), "princpial1");
            mPicList.add(file3);
        }
        if (ivSignaturePad4.getDrawable() != null) {
            File file4 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad4).getDrawable()).getBitmap(), "princpial2");
            mPicList.add(file4);
        }
        if (ivSignaturePad5.getDrawable() != null) {
            File file5 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad5).getDrawable()).getBitmap(), "princpial3");
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

    @OnClick({R.id.title_back, R.id.tv_crew_id, R.id.iv_signature_pad, R.id.iv_signature_pad_2,
            R.id.iv_signature_pad_3, R.id.iv_signature_pad_4, R.id.iv_signature_pad_5,
            R.id.title_setting, R.id.iv_task_add, R.id.tv_delay_time, R.id.tv_s_time, R.id.tv_e_time})
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
                PickerUtils.showDate(SecondWTicketActivity.this, tvSTime);
                break;
            case R.id.tv_e_time:
                PickerUtils.showDate(SecondWTicketActivity.this, tvETime);
                break;
            case R.id.iv_signature_pad:
                if (ticketType != null && status.equals(Constant.STATUS_SIGN)) {
                    Intent intent1 = new Intent();
                    intent1.setClass(this, SignActivity.class);
                    startActivity(intent1);
                    SignBean.setIndex(1);
                }
                break;
            case R.id.iv_signature_pad_2:
                if (ticketType != null && status.equals(Constant.STATUS_SIGN)) {
                    Intent intent1 = new Intent();
                    intent1.setClass(this, SignActivity.class);
                    startActivity(intent1);
                    SignBean.setIndex(2);
                }
                break;
            case R.id.iv_signature_pad_3:
                if (ticketType != null && status.equals(Constant.STATUS_PRINCPIAL)) {
                    Intent intent1 = new Intent();
                    intent1.setClass(this, SignActivity.class);
                    startActivity(intent1);
                    SignBean.setIndex(3);
                }
                break;
            case R.id.iv_signature_pad_4:
                if (ticketType != null && status.equals(Constant.STATUS_PRINCPIAL)) {
                    Intent intent1 = new Intent();
                    intent1.setClass(this, SignActivity.class);
                    startActivity(intent1);
                    SignBean.setIndex(4);
                }
                break;
            case R.id.iv_signature_pad_5:
                if (ticketType != null && status.equals(Constant.STATUS_PRINCPIAL)) {
                    Intent intent1 = new Intent();
                    intent1.setClass(this, SignActivity.class);
                    startActivity(intent1);
                    SignBean.setIndex(5);
                }
                break;
            case R.id.title_setting:
                SecondTicketBean bean = getData();
                addPicList();
                Map<String, RequestBody> params = setParams(bean);
                files.clear();
                for (int i = 0; i < mPicList.size(); i++) {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), mPicList.get(i));
                    params.put("file\"; filename=\"" + mPicList.get(i).getName(), requestFile);
                }
                BaseRequest.getInstance().getService().upLoadSecondTicket(params).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            protected void onSuccees(BaseResult t) throws Exception {
                                Toast.makeText(SecondWTicketActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                Toast.makeText(SecondWTicketActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SecondWTicketActivity.this, "请补全任务信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    taskContentBeans.add(new TaskContentBean(et1.getText().toString(), et2.getText().toString(), et3.getText().toString()));
                    contentAdapter.setNewData(taskContentBeans);
                });
                break;
            case R.id.tv_delay_time:
                PickerUtils.showDate(SecondWTicketActivity.this, tvDelayTime);
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
                tvPerson.setText("(共" + j + "人)");
            }
        }
    }

    private SecondTicketBean getData() {
        SecondTicketBean bean = new SecondTicketBean();
        bean.setCrew_id(tvCrewId.getText().toString());
        bean.setPlan_s_time(tvSTime.getText().toString());
        bean.setPlan_e_time(tvETime.getText().toString());
        bean.setRemark_time_a(tvGroupTimeA.getText().toString());
        bean.setRemark_time_b(tvGroupTimeB.getText().toString());
        bean.setRemark_time_c(tvGroupTimeC.getText().toString());
        bean.setWork_a_time(tvWorkATime.getText().toString());
        bean.setWork_b_time(tvWorkBTime.getText().toString());
        bean.setDelay_time(tvDelayTime.getText().toString());
        bean.setRemark(etRemark.getText().toString());
        bean.setSecWorkList(taskContentBeans);
        return bean;
    }

    private void setData(SecondTicketBean results) {
        tvCrewId.setText(results.getCrew_id());
        String crewId = results.getCrew_id() == null ? "" : results.getCrew_id();
        tvPerson.setText("共" + (crewId.length() - crewId.replace(" ", "").length()) + "人");
        tvSTime.setText(results.getPlan_s_time());
        tvETime.setText(results.getPlan_e_time());
        tvGroupTimeA.setText(results.getRemark_time_a());
        tvGroupTimeB.setText(results.getRemark_time_b());
        tvGroupTimeC.setText(results.getRemark_time_c());
        tvWorkATime.setText(results.getWork_a_time());
        tvWorkBTime.setText(results.getWork_b_time());
        tvDelayTime.setText(results.getDelay_time());

        showPic(results.getFileList(), ivSignaturePad, "sign1.jpg");
        showPic(results.getFileList(), ivSignaturePad2, "sign2.jpg");
        showPic(results.getFileList(), ivSignaturePad3, "princpial1.jpg");
        showPic(results.getFileList(), ivSignaturePad4, "princpial2.jpg");
        showPic(results.getFileList(), ivSignaturePad5, "princpial3.jpg");

        //工作任务
        taskContentBeans.clear();
        if (results.getSecWorkList() != null && results.getSecWorkList().size() > 0) {
            taskContentBeans.addAll(results.getSecWorkList());
        }
        rvTaskContent.setLayoutManager(new LinearLayoutManager(this));
        contentAdapter = new TaskContentAdapter(R.layout.item_task_content, taskContentBeans);
        rvTaskContent.setAdapter(contentAdapter);
    }

    private Map<String, RequestBody> setParams(SecondTicketBean bean) {
        params.put("task_id", toRequestBody(taskId));
        params.put("type", toRequestBody(ticketType));
        params.put("task_type", toRequestBody(ticketTaskType));
        params.put("crew_id", toRequestBody(bean.getCrew_id()));
        params.put("plan_s_time", toRequestBody(bean.getPlan_s_time()));
        params.put("plan_e_time", toRequestBody(bean.getPlan_e_time()));
        params.put("remark_time_a", toRequestBody(bean.getRemark_time_b()));
        params.put("remark_time_b", toRequestBody(bean.getRemark_time_b()));
        params.put("remark_time_c", toRequestBody(bean.getRemark_time_c()));
        params.put("work_a_time", toRequestBody(bean.getWork_a_time()));
        params.put("work_b_time", toRequestBody(bean.getWork_b_time()));
        params.put("delay_time", toRequestBody(bean.getDelay_time()));
        params.put("remark", toRequestBody(bean.getRemark()));

        for (int i = 0; i < bean.getSecWorkList().size(); i++) {
            params.put("secWorkList[" + i + "].work_name", toRequestBody(bean.getSecWorkList().get(i).getWork_name()));
            params.put("secWorkList[" + i + "].work_range", toRequestBody(bean.getSecWorkList().get(i).getWork_range()));
            params.put("secWorkList[" + i + "].work_content", toRequestBody(bean.getSecWorkList().get(i).getWork_content()));
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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.cb_group_time_a:
                if (isChecked) {
                    tvGroupTimeA.setText(DateUatil.getCurrTime());
                } else {
                    tvGroupTimeA.setText(Constant.WORK_TICKET_TIME);
                }

                break;

            case R.id.cb_group_time_b:
                if (isChecked) {
                    tvGroupTimeB.setText(DateUatil.getCurrTime());
                } else {
                    tvGroupTimeB.setText(Constant.WORK_TICKET_TIME);
                }
                break;

            case R.id.cb_group_time_c:
                if (isChecked) {
                    tvGroupTimeC.setText(DateUatil.getCurrTime());
                } else {
                    tvGroupTimeC.setText(Constant.WORK_TICKET_TIME);
                }
                break;

            case R.id.cb_work_a_time:
                if (isChecked) {
                    tvWorkATime.setText(DateUatil.getCurrTime());
                } else {
                    tvWorkATime.setText(Constant.WORK_TICKET_TIME);
                }
                break;

            case R.id.cb_work_b_time:
                if (isChecked) {
                    tvWorkBTime.setText(DateUatil.getCurrTime());
                } else {
                    tvWorkBTime.setText(Constant.WORK_TICKET_TIME);
                }
                break;
        }

    }
}
