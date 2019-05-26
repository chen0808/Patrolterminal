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
import com.patrol.terminal.bean.FourTicketBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulYearBean;
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
    @BindView(R.id.iv_task_add)
    ImageView ivTaskAdd;
    @BindView(R.id.rv_task_content)
    RecyclerView rvTaskContent;
    @BindView(R.id.et_remark_safe)
    TextView etRemarkSafe;
    @BindView(R.id.et_repair_header)
    EditText etRepairHeader;
    @BindView(R.id.et_repair_decorate)
    EditText etRepairDecorate;
    @BindView(R.id.et_supplement_step)
    EditText etSupplementStep;
    @BindView(R.id.iv_signature_pad)
    ImageView ivSignaturePad;
    @BindView(R.id.time_checkbox)
    CheckBox timeCheckbox;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.iv_signature_pad_2)
    ImageView ivSignaturePad2;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.iv_signature_pad_3)
    ImageView ivSignaturePad3;
    @BindView(R.id.iv_signature_pad_4)
    ImageView ivSignaturePad4;
    @BindView(R.id.time_checkbox_1)
    CheckBox timeCheckbox1;
    @BindView(R.id.time_tv_1)
    TextView timeTv1;
    @BindView(R.id.et_repair_site)
    EditText etRepairSite;
    @BindView(R.id.unit_tv)
    TextView unitTv;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
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
        setContentView(R.layout.activity_four_working_ticket);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {

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
//            OverhaulMonthBean bean = getIntent().getParcelableExtra("bean");
//            if (bean != null) {
//                taskId = bean.getRepair_id();
//                status = bean.getRepair_status();
//                OverhaulMonthBean.PlanRepairBean planRepairBean = bean.getPlanRepair();
//                tvUnitId.setText(planRepairBean.getRepair_content());
//                etTicketNumber.setText("暂无");
//                tvDepId.setText("带电作业班");
//                tvLeaderId.setText(leaderName);
//            }
        }

        if (status.equals(Constant.STATUS_PRINCPIAL)) {
            etRemark.setEnabled(false);
            etRepairSite.setEnabled(false);
            etSupplementStep.setEnabled(false);
            etRepairDecorate.setEnabled(false);
            etRepairHeader.setEnabled(false);
            etRemarkSafe.setEnabled(false);
            etTicketNumber.setEnabled(false);
        } else {
            timeCheckbox.setOnCheckedChangeListener(this);
            timeCheckbox1.setOnCheckedChangeListener(this);
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
        BaseRequest.getInstance().getService().searchFourTicket(taskId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FourTicketBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<FourTicketBean> t) throws Exception {
                        FourTicketBean results = t.getResults();
                        if (results == null) {
                            rvTaskContent.setLayoutManager(new LinearLayoutManager(FourWTicketActivity.this));
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

    private FourTicketBean getData() {
        FourTicketBean bean = new FourTicketBean();
        bean.setCrew_id(tvCrewId.getText().toString());
        bean.setRepair_site(etRepairSite.getText().toString());
        bean.setRepair_head(etRepairHeader.getText().toString());
        bean.setRepair_decorate(etRepairDecorate.getText().toString());
        bean.setSupplement_step(etSupplementStep.getText().toString());
        bean.setLicensor_repair_time(timeTv.getText().toString());
        bean.setRepair_end_time(timeTv1.getText().toString());
        bean.setRemark(etRemark.getText().toString());
        bean.setWorkList(taskContentBeans);
        return bean;
    }

    private void setData(FourTicketBean results) {
        tvCrewId.setText(results.getCrew_id());
        String crewId = results.getCrew_id() == null ? "" : results.getCrew_id();
        tvPerson.setText("共" + (crewId.length() - crewId.replace(" ", "").length()) + "人");
        etRepairSite.setText(results.getRepair_site());
        etRepairHeader.setText(results.getRepair_head());
        etRepairDecorate.setText(results.getRepair_decorate());
        etSupplementStep.setText(results.getSupplement_step());
        timeTv.setText(results.getLicensor_repair_time());
        etRemark.setText(results.getRemark());
        timeTv1.setText(results.getRepair_end_time());

        showPic(results.getFileList(), ivSignaturePad, "licence1.jpg");
        showPic(results.getFileList(), ivSignaturePad2, "licence2.jpg");
        showPic(results.getFileList(), ivSignaturePad3, "princpial1.jpg");
        showPic(results.getFileList(), ivSignaturePad4, "licence3.jpg");

//        //工作任务
        taskContentBeans.clear();
        if (results.getWorkList() != null && results.getWorkList().size() > 0) {
            taskContentBeans.addAll(results.getWorkList());
        }
        rvTaskContent.setLayoutManager(new LinearLayoutManager(this));
        contentAdapter = new TaskContentAdapter(R.layout.item_task_content, taskContentBeans);
        rvTaskContent.setAdapter(contentAdapter);
    }

    private Map<String, RequestBody> setParams(FourTicketBean bean) {
        params.put("task_id", toRequestBody(taskId));
        params.put("type", toRequestBody(ticketType));
        params.put("task_type", toRequestBody(ticketTaskType));
        params.put("crew_id", toRequestBody(bean.getCrew_id()));
        params.put("repair_site", toRequestBody(bean.getRepair_site()));
        params.put("repair_head", toRequestBody(bean.getRepair_head()));
        params.put("repair_decorate", toRequestBody(bean.getRepair_decorate()));
        params.put("supplement_step", toRequestBody(bean.getSupplement_step()));
        params.put("licensor_repair_time", toRequestBody(bean.getLicensor_repair_time()));
        params.put("repair_end_time", toRequestBody(bean.getRepair_end_time()));
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

    private void showPic(List<FourTicketBean.FileListBean> list, ImageView iv, String fileName) {
        for (FourTicketBean.FileListBean fileListBean : list) {
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
            File file1 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad).getDrawable()).getBitmap(), "licence1");
            mPicList.add(file1);
        }
        if (ivSignaturePad2.getDrawable() != null) {
            File file2 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad2).getDrawable()).getBitmap(), "licence2");
            mPicList.add(file2);
        }
        if (ivSignaturePad3.getDrawable() != null) {
            File file3 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad3).getDrawable()).getBitmap(), "princpial1");
            mPicList.add(file3);
        }
        if (ivSignaturePad4.getDrawable() != null) {
            File file4 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad4).getDrawable()).getBitmap(), "licence3");
            mPicList.add(file4);
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
                    timeTv.setText(Constant.WORK_TICKET_TIME);
                }

                break;

            case R.id.time_checkbox_1:
                if (isChecked) {
                    timeTv1.setText(DateUatil.getCurrTime());
                } else {
                    timeTv1.setText(Constant.WORK_TICKET_TIME);
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
                if (ticketType != null && status.equals(Constant.STATUS_LICENCE)) {
                    Intent intent = new Intent();
                    intent.setClass(this, SignActivity.class);
                    startActivity(intent);
                    SignBean.setIndex(1);
                }
                break;
            case R.id.iv_signature_pad_2:
                if (ticketType != null && status.equals(Constant.STATUS_LICENCE)) {
                    Intent intent = new Intent();
                    intent.setClass(this, SignActivity.class);
                    startActivity(intent);
                    SignBean.setIndex(2);
                }
                break;
            case R.id.iv_signature_pad_3:
                if (ticketType != null && status.equals(Constant.STATUS_PRINCPIAL)) {
                    Intent intent = new Intent();
                    intent.setClass(this, SignActivity.class);
                    startActivity(intent);
                    SignBean.setIndex(3);
                }
                break;
            case R.id.iv_signature_pad_4:
                if (ticketType != null && status.equals(Constant.STATUS_LICENCE)) {
                    Intent intent = new Intent();
                    intent.setClass(this, SignActivity.class);
                    startActivity(intent);
                    SignBean.setIndex(4);
                }
                break;
            case R.id.title_setting:
                FourTicketBean bean = getData();
                addPicList();
                Map<String, RequestBody> params = setParams(bean);
                BaseRequest.getInstance().getService().upLoadFourTicket(params).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            protected void onSuccees(BaseResult t) throws Exception {
                                Toast.makeText(FourWTicketActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
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
                        taskContentBeans.add(new TaskContentBean(et1.getText().toString(), et2.getText().toString(), et3.getText().toString()));
                        contentAdapter.setNewData(taskContentBeans);
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
