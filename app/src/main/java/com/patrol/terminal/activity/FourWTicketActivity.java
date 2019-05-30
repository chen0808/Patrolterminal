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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.SafeAdapter;
import com.patrol.terminal.adapter.TaskContentAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.AddressBookLevel2;
import com.patrol.terminal.bean.ClassMemberBean;
import com.patrol.terminal.bean.FourTicketBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.SignBean;
import com.patrol.terminal.bean.TicketSafeContent;
import com.patrol.terminal.bean.TicketSign;
import com.patrol.terminal.bean.TicketUser;
import com.patrol.terminal.bean.TicketWork;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.PickerUtils;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.SignDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/*事故应急抢修单*/
public class FourWTicketActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {


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
    @BindView(R.id.rv_remark_safe)
    RecyclerView rvRemarkSafe;
    @BindView(R.id.iv_signature_pad)
    ImageView ivSignaturePad;
    @BindView(R.id.time_checkbox)
    CheckBox timeCheckbox;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.iv_signature_pad_2)
    ImageView ivSignaturePad2;
    @BindView(R.id.iv_signature_pad_3)
    ImageView ivSignaturePad3;
    @BindView(R.id.iv_signature_pad_4)
    ImageView ivSignaturePad4;
    @BindView(R.id.time_checkbox_1)
    CheckBox timeCheckbox1;
    @BindView(R.id.time_tv_1)
    TextView timeTv1;
    @BindView(R.id.unit_tv)
    TextView unitTv;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.et_repair_content)
    EditText etRepairContent;
    @BindView(R.id.et_care_content)
    EditText etCareContent;
    @BindView(R.id.et_extra_content)
    EditText etExtraContent;
    @BindView(R.id.et_done_content)
    EditText etDoneContent;
    @BindView(R.id.et_duty_user_name)
    EditText etDutyUserName;
    @BindView(R.id.et_task_user_name)
    EditText etTaskUserName;
    private List<AddressBookLevel2> nameList = new ArrayList<>();
    private List<File> mPicList = new ArrayList<>();
    private List<TicketWork> workList = new ArrayList<>();
    private TaskContentAdapter contentAdapter;
    private Map<String, RequestBody> params = new HashMap<>();
    private String taskId;
    private String leaderName;
    private String leaderId;
    private String ticketType;
    private String ticketTaskType;
    //    private String status;
    private List<TicketSign> signList = new ArrayList<>();
    private List<TicketUser> userList = new ArrayList<>();
    private List<TicketSafeContent> safeList = new ArrayList<>();
    private FourTicketBean results;
    private SafeAdapter safeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_working_ticket);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {
        signList.clear();
        userList.clear();
        safeList.clear();
        titleName.setText("国网兰州供电公司电力线路事故应急抢修单");
        titleName.setTextSize(16.0f);
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("提交");
        String jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");

        leaderName = getIntent().getStringExtra("leaderName");
        leaderId = getIntent().getStringExtra("leaderId");
        ticketType = getIntent().getStringExtra("type");
        ticketTaskType = getIntent().getStringExtra("task_type");

        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {  //专责接受的Bean不一样
            OverhaulYearBean bean = getIntent().getParcelableExtra("bean");

            if (bean != null) {
                taskId = bean.getId();
                //status = bean.getStatus();   //TODO  linmeng
                tvUnitId.setText(bean.getRepair_content());//单位
                etTicketNumber.setText("暂无");//编号
                tvDepId.setText("带电作业班");//班组
                tvLeaderId.setText(leaderName);//班组负责人
            }
        } else {
            OverhaulMonthBean bean = getIntent().getParcelableExtra("bean");
            if (bean != null) {
                taskId = bean.getRepair_id();
//                status = bean.getRepair_status();
//                OverhaulMonthBean.PlanRepairBean planRepairBean = bean.getPlanRepair();
//                tvUnitId.setText(planRepairBean.getRepair_content());
//                etTicketNumber.setText("暂无");
//                tvDepId.setText("带电作业班");
//                tvLeaderId.setText(leaderName);
            }
        }

//        if (status.equals(Constant.STATUS_PRINCPIAL)) {
//            etRemark.setEnabled(false);
//            etRepairSite.setEnabled(false);
//            etSupplementStep.setEnabled(false);
//            etRepairDecorate.setEnabled(false);
//            etRepairHeader.setEnabled(false);
//            etRemarkSafe.setEnabled(false);
//            etTicketNumber.setEnabled(false);
//        } else {
        timeCheckbox.setOnCheckedChangeListener(this);
        timeCheckbox1.setOnCheckedChangeListener(this);
//        }

//        //注意事项
//        BaseRequest.getInstance().getService().getTicketSafe(ticketType, ticketTaskType).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<TicketSafeBean>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<TicketSafeBean>> t) throws Exception {
//                        List<TicketSafeBean> results = t.getResults();
//                        if (results != null && results.size() > 0) {
//                            String safeWay = results.get(0).getSafe_way();
//                            String remarkSafe = safeWay.replace("<p>", "").replace("</p>", "");
//                            String remarkSafe2 = remarkSafe.replace("。（", "。\n  （");
//                            etRemarkSafe.setText(remarkSafe2);
//                        }
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                    }
//                });
        //已填写数据
        BaseRequest.getInstance().getService().searchFourTicket(taskId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FourTicketBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<FourTicketBean> t) throws Exception {
                        results = t.getResults();
                        if (results != null) {
                            setData(results);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.d("TAG", e.getMessage());
                    }
                });
    }

    private FourTicketBean getData() {
        FourTicketBean bean = new FourTicketBean();
        bean.setBegin_time(tvStartTime.getText().toString());
        bean.setEnd_time(tvEndTime.getText().toString());

        bean.setDuty_user_name(etDutyUserName.getText().toString());
        bean.setTask_user_name(etTaskUserName.getText().toString());
        bean.setRepair_content(etRepairContent.getText().toString());
        bean.setCare_content(etCareContent.getText().toString());
        bean.setExtra_content(etExtraContent.getText().toString());
        bean.setDone_content(etDoneContent.getText().toString());
        bean.setSignList(signList);
        bean.setUserList(userList);
        List<TicketSafeContent> data = safeAdapter.getData();
        safeList.addAll(data);
        bean.setSafeList(safeList);
        return bean;
    }

    private void setData(FourTicketBean results) {
        String crew = getCrew(results.getUserList());
        tvCrewId.setText(crew);
        tvPerson.setText("共" + (crew.length() - crew.replace(" ", "").length()) + "人");
        tvStartTime.setText(results.getBegin_time());
        tvEndTime.setText(results.getDone_time());
        rvRemarkSafe.setLayoutManager(new LinearLayoutManager(FourWTicketActivity.this));
        safeAdapter = new SafeAdapter(R.layout.item_safe, results.getSafeList());
        rvRemarkSafe.setAdapter(safeAdapter);
        etDutyUserName.setText(results.getDuty_user_name());
        etTaskUserName.setText(results.getTask_user_name());
        etRepairContent.setText(results.getRepair_content());
        etCareContent.setText(results.getCare_content());
        etDoneContent.setText(results.getDone_content());
        etExtraContent.setText(results.getExtra_content());

        for (int i = 0; i < results.getSignList().size(); i++) {
            String sign = results.getSignList().get(i).getSign();
            switch (sign) {
                case "1":
                    showPic(results.getSignList().get(i), ivSignaturePad, sign + ".jpg");
                    if (!results.getSignList().get(i).getSign_time().equals(getResources().getString(R.string.work_ticket_time))) {
                        timeTv.setText(results.getSignList().get(i).getSign_time());
                        timeCheckbox.setVisibility(View.GONE);
                    }
                    break;
                case "2":
                    showPic(results.getSignList().get(i), ivSignaturePad2, sign + ".jpg");
//                    tvGroupTimeB.setText(results.getSignList().get(i).getSign_time());
//                    cbGroupTimeB.setVisibility(View.GONE);
                    break;
                case "3":
                    showPic(results.getSignList().get(i), ivSignaturePad3, sign + ".jpg");
//                    tvGroupTimeC.setText(results.getSignList().get(i).getSign_time());
//                    cbGroupTimeC.setVisibility(View.GONE);
                    break;
                case "4":
                    showPic(results.getSignList().get(i), ivSignaturePad4, sign + ".jpg");
                    if (!results.getSignList().get(i).getSign_time().equals(getResources().getString(R.string.work_ticket_time))) {
                        timeTv1.setText(results.getSignList().get(i).getSign_time());
                        timeCheckbox1.setVisibility(View.GONE);
                    }
                    break;
            }
        }

//        //工作任务
//        workList.clear();
//        if (results.getWorkList() != null && results.getWorkList().size() > 0) {
//            workList.addAll(results.getWorkList());
//        }
//        rvTaskContent.setLayoutManager(new LinearLayoutManager(this));
//        contentAdapter = new TaskContentAdapter(R.layout.item_task_content, workList);
//        rvTaskContent.setAdapter(contentAdapter);
    }

    private String getCrew(List<TicketUser> userList) {
        String userName = "";
        if (userList.size() > 0) {
            for (int i = 0; i < userList.size(); i++) {
                userName += userList.get(i).getUser_name() + " ";
                this.userList.add(userList.get(i));
            }
        }
        return userName;
    }

    private Map<String, RequestBody> setParams(FourTicketBean bean) {
        params.put("id", toRequestBody(results == null ? "" : results.getId()));
        params.put("task_id", toRequestBody(taskId));
        params.put("type", toRequestBody(ticketType));
        params.put("task_type", toRequestBody(ticketTaskType));
        params.put("begin_time", toRequestBody(bean.getBegin_time()));
        params.put("done_time", toRequestBody(bean.getDone_time()));
        params.put("duty_user_name", toRequestBody(bean.getDuty_user_name()));
        params.put("task_user_name", toRequestBody(bean.getTask_user_name()));
        params.put("care_content", toRequestBody(bean.getCare_content()));
        params.put("done_content", toRequestBody(bean.getDone_content()));
        params.put("extra_content", toRequestBody(bean.getExtra_content()));
        params.put("repair_content", toRequestBody(bean.getRepair_content()));

        //PDA工作人员集合
        if (bean.getUserList() != null) {
            for (int i = 0; i < bean.getUserList().size(); i++) {
                params.put("userList[" + i + "].user_name", toRequestBody(bean.getUserList().get(i).getUser_name()));
            }
        }

        addPicList();
        // PDA人员签名集合
        if (bean.getSignList() != null) {
            for (int i = 0; i < bean.getSignList().size(); i++) {
                params.put("signList[" + i + "].sign", toRequestBody(bean.getSignList().get(i).getSign()));
                params.put("signList[" + i + "].sign_time", toRequestBody(bean.getSignList().get(i).getSign_time()));
                params.put("signList[" + i + "].file", toRequestBody(bean.getSignList().get(i).getFile()));
            }
        }

        if (bean.getSafeList() != null) {
            for (int i = 0; i < bean.getSafeList().size(); i++) {
                params.put("safeList[" + i + "].ticket_safe_content", toRequestBody(bean.getSafeList().get(i).getTicket_safe_content()));
            }
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

    //图片展示
    private void showPic(TicketSign sign, ImageView iv, String fileName) {
        Glide.with(this).asBitmap().load(BaseUrl.BASE_URL + sign.getFile_path() + sign.getFilename()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                iv.setImageBitmap(resource);
                Log.d("TAG", BaseUrl.BASE_URL + sign.getFile_path() + sign.getFilename());
                if (iv.getDrawable() != null) {
                    File file = SignDialog.saveBitmapFile(resource, fileName.replace(".jpg", ""));
                    String base64 = FileUtil.fileToBase64(file);
//                    signList.add(new TicketSign(sign.getSign(), "", base64));
                }
            }
        });
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
                        userList.add(new TicketUser(workers[i]));
                        tvCrewId.setText(nameArray);
                        tvPerson.setText("(共" + j + "人)");
                    }
                }
                dialog.dismiss();

            }
        });
        builder.show();
    }


    //获取所有人签名
    private void addPicList() {
        if (ivSignaturePad.getDrawable() != null) {
            File file1 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad).getDrawable()).getBitmap(), "1");
            signList.add(new TicketSign("1", timeTv.getText().toString(), FileUtil.fileToBase64(file1)));
        }
        if (ivSignaturePad2.getDrawable() != null) {
            File file2 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad2).getDrawable()).getBitmap(), "2");
            signList.add(new TicketSign("2", getResources().getString(R.string.work_ticket_time), FileUtil.fileToBase64(file2)));
        }
        if (ivSignaturePad3.getDrawable() != null) {
            File file3 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad3).getDrawable()).getBitmap(), "3");
            signList.add(new TicketSign("3", getResources().getString(R.string.work_ticket_time), FileUtil.fileToBase64(file3)));
        }
        if (ivSignaturePad4.getDrawable() != null) {
            File file4 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad4).getDrawable()).getBitmap(), "4");
            signList.add(new TicketSign("4", timeTv1.getText().toString(), FileUtil.fileToBase64(file4)));
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
                    timeTv.setText(getResources().getString(R.string.work_ticket_time));
                }

                break;

            case R.id.time_checkbox_1:
                if (isChecked) {
                    timeTv1.setText(DateUatil.getCurrTime());
                } else {
                    timeTv1.setText(getResources().getString(R.string.work_ticket_time));
                }
                break;


        }

    }

    @OnClick({R.id.title_back, R.id.tv_crew_id, R.id.iv_signature_pad, R.id.iv_signature_pad_2,
            R.id.iv_signature_pad_3, R.id.iv_signature_pad_4, R.id.title_setting, R.id.tv_start_time, R.id.tv_end_time})
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
            case R.id.tv_start_time:
                PickerUtils.showDate(this, tvStartTime);
                break;
            case R.id.tv_end_time:
                PickerUtils.showDate(this, tvEndTime);
                break;
            case R.id.iv_signature_pad:
//                if (ticketType != null && status.equals(Constant.STATUS_LICENCE)) {
                Intent intent = new Intent();
                intent.setClass(this, SignActivity.class);
                startActivity(intent);
                SignBean.setIndex(1);
//                }
                break;
            case R.id.iv_signature_pad_2:
//                if (ticketType != null && status.equals(Constant.STATUS_LICENCE)) {
                Intent intent2 = new Intent();
                intent2.setClass(this, SignActivity.class);
                startActivity(intent2);
                SignBean.setIndex(2);
//                }
                break;
            case R.id.iv_signature_pad_3:
//                if (ticketType != null && status.equals(Constant.STATUS_PRINCPIAL)) {
                Intent intent3 = new Intent();
                intent3.setClass(this, SignActivity.class);
                startActivity(intent3);
                SignBean.setIndex(3);
//                }
                break;
            case R.id.iv_signature_pad_4:
//                if (ticketType != null && status.equals(Constant.STATUS_LICENCE)) {
                Intent intent4 = new Intent();
                intent4.setClass(this, SignActivity.class);
                startActivity(intent4);
                SignBean.setIndex(4);
//                }
                break;
            case R.id.title_setting:
                ProgressDialog.show(this, true, "正在上传...");
                FourTicketBean bean = getData();
                Map<String, RequestBody> params = setParams(bean);
                BaseRequest.getInstance().getService().upLoadFourTicket(params).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            protected void onSuccees(BaseResult t) throws Exception {
                                ProgressDialog.cancle();
                                Toast.makeText(FourWTicketActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                ProgressDialog.cancle();
                                Toast.makeText(FourWTicketActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
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
                builder.show().setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty() || et3.getText().toString().isEmpty()) {
                            Toast.makeText(FourWTicketActivity.this, "请补全任务信息", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        workList.add(new TicketWork(et1.getText().toString(), et2.getText().toString(), et3.getText().toString()));
                        contentAdapter.setNewData(workList);
                    }
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
