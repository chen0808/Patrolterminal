package com.patrol.terminal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.patrol.terminal.adapter.GroundLineAdapter;
import com.patrol.terminal.adapter.LicensingEndAdapter;
import com.patrol.terminal.adapter.LicensingStartedAdapter;
import com.patrol.terminal.adapter.SafeAdapter;
import com.patrol.terminal.adapter.WorkAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.ClassMemberBean;
import com.patrol.terminal.bean.FirstTicketBean;
import com.patrol.terminal.bean.LineName;
import com.patrol.terminal.bean.OverhaulFzrSendBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulSendUserBean;
import com.patrol.terminal.bean.OverhaulUserInfo;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.SelectWorkerBean;
import com.patrol.terminal.bean.SignBean;
import com.patrol.terminal.bean.TicketFirstEnd;
import com.patrol.terminal.bean.TicketFirstGround;
import com.patrol.terminal.bean.TicketFirstPermit;
import com.patrol.terminal.bean.TicketSafeContent;
import com.patrol.terminal.bean.TicketSign;
import com.patrol.terminal.bean.TicketUser;
import com.patrol.terminal.bean.TicketWork;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.GetViewBitmapUtils;
import com.patrol.terminal.utils.PickerUtils;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.QfrDialog;
import com.patrol.terminal.widget.SignDialog;

import java.io.File;
import java.io.Serializable;
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

public class FirstWTicketActivity extends BaseActivity {


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
    @BindView(R.id.tv_dep_id)
    TextView tvDepId;
    @BindView(R.id.staff_in_the_workshop_tv)
    TextView staffInTheWorkshopTv;
    @BindView(R.id.tv_crew_id)
    TextView tvCrewId;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.tv_double_name)
    TextView tvDoubleName;
    @BindView(R.id.planned_working_hours)
    TextView plannedWorkingHours;
    @BindView(R.id.tv_s_time)
    TextView tvSTime;
    @BindView(R.id.tv_e_time)
    TextView tvETime;
    @BindView(R.id.et_device_safe)
    EditText etDeviceSafe;
    @BindView(R.id.rv_remark_safe)
    RecyclerView rvRemarkSafe;
    @BindView(R.id.ground_lines_to_hang_tv)
    TextView groundLinesToHangTv;
    @BindView(R.id.ground_lines_to_hang_ll)
    LinearLayout groundLinesToHangLl;
    @BindView(R.id.ground_lines_to_hang_listView)
    RecyclerView groundLinesToHangListView;
    @BindView(R.id.iv_signature_pad)
    ImageView ivSignaturePad;
    @BindView(R.id.iv_signature_pad_2)
    ImageView ivSignaturePad2;
    @BindView(R.id.iv_signature_pad_3)
    ImageView ivSignaturePad3;
    @BindView(R.id.licensing_started_ll)
    LinearLayout licensingStartedLl;
    @BindView(R.id.licensing_started_listView)
    RecyclerView licensingStartedListView;
    @BindView(R.id.iv_signature_pad_4)
    ImageView ivSignaturePad4;
    @BindView(R.id.iv_signature_pad_5)
    ImageView ivSignaturePad5;
    @BindView(R.id.iv_signature_pad_6)
    ImageView ivSignaturePad6;
    @BindView(R.id.iv_signature_pad_7)
    ImageView ivSignaturePad7;
    @BindView(R.id.iv_signature_pad_8)
    ImageView ivSignaturePad8;
    @BindView(R.id.work_end_ll)
    LinearLayout workEndLl;
    @BindView(R.id.et_custody_man)
    EditText etCustodyMan;
    @BindView(R.id.et_custody_content)
    EditText etCustodyContent;
    @BindView(R.id.et_other)
    EditText etOther;
    @BindView(R.id.et_switch_safe)
    EditText etSwitchSafe;
    @BindView(R.id.et_old_leader)
    TextView etOldLeader;
    @BindView(R.id.et_person_change)
    EditText etPersonChange;
    @BindView(R.id.iv_task_add)
    ImageView ivTaskAdd;
    @BindView(R.id.rv_task_content)
    RecyclerView rvTaskContent;
    @BindView(R.id.iv_ground_lines_add)
    ImageView ivGroundLinesAdd;
    @BindView(R.id.iv_licensing_started_add)
    ImageView ivLicensingStartedAdd;
    @BindView(R.id.iv_licensing_end_add)
    ImageView ivLicensingEndAdd;
    @BindView(R.id.licensing_end_listView)
    RecyclerView licensingEndListView;
    @BindView(R.id.tv_postpone)
    TextView tvPostpone;
    Map<String, RequestBody> params = new HashMap<>();
    @BindView(R.id.et_remove_ground_no)
    EditText etRemoveGroundNo;
    @BindView(R.id.et_remove_ground_num)
    EditText etRemoveGroundNum;
    @BindView(R.id.tv_leader)
    TextView tvLeader;
    @BindView(R.id.iv_safe)
    ImageView ivSafe;
    @BindView(R.id.iv_safe_change)
    ImageView ivSafeChange;
    @BindView(R.id.tv_sign_time1)
    TextView tvSignTime1;
    @BindView(R.id.tv_sign_time2)
    TextView tvSignTime2;
    @BindView(R.id.tv_sign_time3)
    TextView tvSignTime3;
    @BindView(R.id.tv_sign_time4)
    TextView tvSignTime4;
    @BindView(R.id.tv_sign_time5)
    TextView tvSignTime5;
    @BindView(R.id.tv_sign_time6)
    TextView tvSignTime6;
    @BindView(R.id.tv_sign_time7)
    TextView tvSignTime7;
    @BindView(R.id.tv_sign_time8)
    TextView tvSignTime8;
    @BindView(R.id.btn_pic)
    Button btnPic;
    @BindView(R.id.btn_preview)
    Button btnPreview;
    @BindView(R.id.sv_ticket)
    ScrollView svTicket;
    private List<TicketWork> workList = new ArrayList<>();
    private List<TicketFirstGround> groundList = new ArrayList<>();
    private List<TicketFirstPermit> permitList = new ArrayList<>();
    private List<TicketFirstEnd> endList = new ArrayList<>();
    private List<TicketSign> signList = new ArrayList<>();
    private List<TicketUser> userList = new ArrayList<>();
    private List<TicketSafeContent> safeList = new ArrayList<>();

    private WorkAdapter workAdapter = new WorkAdapter(R.layout.item_task_content, null);
    private GroundLineAdapter groundLineAdapter = new GroundLineAdapter(R.layout.item_ground_line, null);
    private LicensingStartedAdapter licensingStartedAdapter = new LicensingStartedAdapter(R.layout.item_licensing_started, null);
    private LicensingEndAdapter licensingEndAdapter = new LicensingEndAdapter(R.layout.item_licensing_started, null);
    private String leaderName = "";
    private String leaderId = "";
    private String taskId;
    private String ticketType;
    private String ticketTaskType;
    private FirstTicketBean results;
    private String lineId;
    private String applyDepId;
    private SafeAdapter safeAdapter;
    //    private String status;

    private SelectWorkerBean workerList;
    private boolean[] mulchoice;

    private String taskContent;  //工作任务
    private String status = "0";  //工作票状态
    private List<OverhaulUserInfo> userData = new ArrayList<>();
    private int signPosition = 0;
    private int signPosition2 = 0;
    private String photoPath;
    private String photoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_working_ticket);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        workList.clear();
        groundList.clear();
        permitList.clear();
        endList.clear();
        signList.clear();
        userList.clear();
        safeList.clear();

        rvTaskContent.setLayoutManager(new LinearLayoutManager(FirstWTicketActivity.this));
        rvTaskContent.setAdapter(workAdapter);

        groundLinesToHangListView.setLayoutManager(new LinearLayoutManager(FirstWTicketActivity.this));
        groundLinesToHangListView.setAdapter(groundLineAdapter);

        //确认工作票1-6页
        licensingStartedListView.setLayoutManager(new LinearLayoutManager(FirstWTicketActivity.this));
        licensingStartedListView.setAdapter(licensingStartedAdapter);

        //工作终结报告
        licensingEndListView.setLayoutManager(new LinearLayoutManager(FirstWTicketActivity.this));
        licensingEndListView.setAdapter(licensingEndAdapter);


        String jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        String userId = SPUtil.getString(this, Constant.USER, Constant.USERID, "");
        String userName = SPUtil.getString(this, Constant.USER, Constant.USERNAME, "");

        workerList = getIntent().getParcelableExtra("selectedUserListBeans");
        if (workerList != null && workerList.getUserInfos() != null && workerList.getUserInfos().size() > 0) {
            mulchoice = new boolean[workerList.getUserInfos().size()];
        }

        if (jobType.contains(Constant.REFURBISHMENT_MEMBER) || jobType.contains(Constant.REFURBISHMENT_TEMA_LEADER)) {
            leaderName = userName;
            leaderId = userId;
        }

        ticketType = getIntent().getStringExtra("type");
        ticketTaskType = getIntent().getStringExtra("task_type");

        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {  //专责接受的Bean不一样
            OverhaulYearBean bean = getIntent().getParcelableExtra("bean");

            if (bean != null) {
                taskId = bean.getId();
                //status = bean.getStatus();          //TODO  linmeng
                tvUnitId.setText(bean.getRepair_content());//单位
                etTicketNumber.setText("暂无");//编号
                tvDepId.setText("带电作业班");//班组
//                tvLeaderId.setText(leaderName);//班组负责人
                tvSTime.setText(bean.getStart_time());//开始时间
                tvETime.setText(bean.getEnd_time());//结束时间
            }

        } else {
            OverhaulMonthBean bean = getIntent().getParcelableExtra("bean");

            if (bean != null) {
                taskId = bean.getId();
                lineId = bean.getLine_id();
                applyDepId = bean.getApply_dep_id();
                tvUnitId.setText(bean.getApply_dep_name());//单位
                etTicketNumber.setText("暂无");
                tvDepId.setText("带电作业班");
                tvLeaderId.setText(leaderName);
                tvSTime.setText(bean.getStart_time());
                tvETime.setText(bean.getEnd_time());

                taskContent = bean.getTask_content();
            }
        }

        titleName.setText("电力线路第一种工作票");
        titleSetting.setVisibility(View.VISIBLE);


//        if (!status.equals(Constant.STATUS_PRINCPIAL)) {
//        etTicketNumber.setEnabled(false);
//        etOther.setEnabled(false);
//        etCustodyContent.setEnabled(false);
//        etRemarkSafe.setEnabled(false);
//        etDeviceSafe.setEnabled(false);
//        etDoubleName.setEnabled(false);
//        etCustodyMan.setEnabled(false);
//        etOldLeader.setEnabled(false);
//        etPersonChange.setEnabled(false);
//        etSwitchSafe.setEnabled(false);
//        } else {
//        }


        //默认6.1

        // etSwitchSafe


//        //注意事项
//        BaseRequest.getInstance().getService().getTicketSafe("1", "1").subscribeOn(Schedulers.io())
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

        if ("0".equals(status)) {        //第一次进来发送给签发人
            titleSettingTv.setText("发送");
        }

        getDefaultSafe();
        if (taskId == null) {
            return;
        }
        //已填写数据
        BaseRequest.getInstance().getService().searchFirstTicket(taskId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FirstTicketBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<FirstTicketBean> t) throws Exception {
                        results = t.getResults();
                        if (results == null) {
                            //默认工作任务

                            workList = new ArrayList<>();
                            String[] taskStrs = taskContent.split("；");
                            for (int i = 0; i < taskStrs.length; i++) {
                                TicketWork ticketWork = new TicketWork("", taskStrs[i]);
                                workList.add(ticketWork);
                            }

                            workAdapter.setNewData(workList);
                            groundLineAdapter.setNewData(groundList);
                            licensingStartedAdapter.setNewData(permitList);
                            licensingEndAdapter.setNewData(endList);
                        } else {
                            setData(results);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.d("TAG", e.getMessage());
                    }
                });

        Log.w("linmeng", "lineId:" + lineId);
        //线路或双重设备名称
        BaseRequest.getInstance().getService().getDoubleLine(lineId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LineName>(this) {
                    @Override
                    protected void onSuccees(BaseResult<LineName> t) throws Exception {
                        Log.w("linmeng", "t.getResults():" + t.getResults());
                        LineName result = t.getResults();
                        if (result != null) {
                            Log.w("linmeng", "result.getVoltage_level():" + result.getVoltage_level());
                            Log.w("linmeng", "result.getName():" + result.getName());
                            Log.w("linmeng", "reresult.getLine_no():" + result.getLine_no());
                            Log.w("linmeng", "result.getLine_color():" + result.getLine_color());

                            tvDoubleName.setText(result.getVoltage_level() + result.getName() +
                                    ",线路编号：" + result.getLine_no() + "，色标：" + result.getLine_color() + ",位置称号：左线");
                            etSwitchSafe.setText(result.getName() + "转为检修状态，拉开" + result.getStart_power_station()
                                    + result.getName() + "开关及各侧刀闸，拉开" + result.getEnd_power_station() + "1117开和一线开关及各侧刀闸。");
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.w("linmeng", "onFailure risNetWorkError:" + e.toString());
                    }
                });
    }

    private void getDefaultSafe() {
        //默认注意事项
        BaseRequest.getInstance().getService().getTicketSafe("1", "1", "sort").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TicketSafeContent>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TicketSafeContent>> t) throws Exception {
                        List<TicketSafeContent> results = t.getResults();
                        if (results != null && results.size() > 0) {
                            rvRemarkSafe.setLayoutManager(new LinearLayoutManager(FirstWTicketActivity.this));
                            safeAdapter = new SafeAdapter(R.layout.item_safe, results);
                            rvRemarkSafe.setAdapter(safeAdapter);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    //获取界面上的数据
    private FirstTicketBean getData() {
        FirstTicketBean bean = new FirstTicketBean();
        bean.setLine_id(lineId);
        bean.setDuty_user_id(leaderId);
        bean.setDuty_user_name(leaderName);
        bean.setTask_id(taskId);
        bean.setUnit_id(applyDepId);
        //bean.setWork_dep_id();                   TODO by linmeng
        bean.setBegin_time(tvSTime.getText().toString());
        bean.setEnd_time(tvETime.getText().toString());
        bean.setRelate_device_operate(etSwitchSafe.getText().toString());
        bean.setRetain_device(etDeviceSafe.getText().toString());
        bean.setDelay_time(tvPostpone.getText().toString());
        bean.setRemove_ground_no(etRemoveGroundNo.getText().toString());
        bean.setRemove_ground_num(etRemoveGroundNum.getText().toString());
        bean.setGuarder_name(etCustodyMan.getText().toString());
        bean.setGuarder_content(etCustodyContent.getText().toString());
        bean.setOther_content(etOther.getText().toString());
        if ("0".equals(status)) { //工作负责人第一次发送给签发人
            status = "1";
            bean.setStatus(status);
        } else if ("2".equals(status)) {  //签发人签字后，负责人再次填写提交
            status = "3";
        }
        bean.setStatus(status);


        //工作任务
        workList = workAdapter.getData();
        bean.setWorkList(workList);

        bean.setGroundList(groundList);
        bean.setPermitList(permitList);
        bean.setEndList(endList);
        bean.setSignList(signList);
        bean.setUserList(userList);
        List<TicketSafeContent> data = safeAdapter.getData();
        safeList.addAll(data);
        bean.setSafeList(safeList);
        return bean;
    }

    private void sendToQfr() {
        OverhaulFzrSendBean overhaulFzrSendBean = new OverhaulFzrSendBean();
        overhaulFzrSendBean.setId(taskId);
        overhaulFzrSendBean.setTask_status("2");   //本次还不是最后提交，只是发送给签发人 3为最后提交
        List<OverhaulFzrSendBean.UserInfo> sendSignuserList = new ArrayList<>();

        OverhaulFzrSendBean.UserInfo userInfo1 = new OverhaulFzrSendBean.UserInfo();
        userInfo1.setUser_id(userData.get(signPosition).getUser_id());
        userInfo1.setUser_name(userData.get(signPosition).getUser_name());
        userInfo1.setSign(Constant.STATUS_SIGN);
        sendSignuserList.add(userInfo1);

        OverhaulFzrSendBean.UserInfo userInfo2 = new OverhaulFzrSendBean.UserInfo();
        userInfo2.setUser_id(userData.get(signPosition2).getUser_id());
        userInfo2.setUser_name(userData.get(signPosition2).getUser_name());
        userInfo2.setSign(Constant.STATUS_SIGN);
        sendSignuserList.add(userInfo2);

//        OverhaulFzrSendBean.UserInfo userInfo3 = new OverhaulFzrSendBean.UserInfo();
//        userInfo3.setUser_id(userData.get(licencePosition).getUser_id());
//        userInfo3.setUser_name(userData.get(licencePosition).getUser_name());
//        userInfo3.setSign(Constant.STATUS_LICENCE);
//        sendSignuserList.add(userInfo3);
//        overhaulFzrSendBean.setUserList(userList);

        overhaulFzrSendBean.setUserList(sendSignuserList);
        overhaulFzrSendBean.setPlan_type_sign("12");
        String dep_id = SPUtil.getString(this, Constant.USER, Constant.DEPID, "");
        overhaulFzrSendBean.setDep_id(dep_id);
        String user_id = SPUtil.getString(this, Constant.USER, Constant.USERID, "");
        overhaulFzrSendBean.setUser_id(user_id);
        overhaulFzrSendBean.setTask_content(taskContent);


        BaseRequest.getInstance().getService()
                .sendOverhaulFzrPlan(overhaulFzrSendBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulSendUserBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulSendUserBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            //result = t.getResults();
                            Toast.makeText(FirstWTicketActivity.this, "发送给签发人成功！", Toast.LENGTH_SHORT).show();
                            //产生待办
                            //deal();
                            //更新详情状态
                            //initId();
                            finish();
                        } else {
                            Toast.makeText(FirstWTicketActivity.this, "提交失败！", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }

    private void initId() {  //查询任务
//        BaseRequest.getInstance().getService().getTaskInfo(taskId).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<OverhaulMonthBean>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<OverhaulMonthBean> t) throws Exception {
//                        overhaulMonthBean = t.getResults();
//
//                        initView();
//                        initFileList();
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                    }
//                });
    }

    //从接口获取到的数据展示在界面上
    private void setData(FirstTicketBean results) {
        status = results.getStatus();  //状态
        if ("0".equals(status)) {        //第一次进来发送给签发人
            titleSettingTv.setText("发送");
        } else if ("1".equals(status)) {   //签发人还未签字进来
            //titleSetting.setVisibility(View.GONE);
            titleSettingTv.setText("提交");   //暂时让编辑，待办还未做好  TODO
        } else if ("2".equals(status)) {   //签发人签字后进来提交内容
            titleSettingTv.setText("提交");
        } else if ("3".equals(status)) {   //负责人提交工作票后仅查看
            titleSetting.setVisibility(View.GONE);
        }

        String crew = getCrew(results.getUserList(), "1");
        tvCrewId.setText(crew);
        tvPerson.setText("共" + (crew.length() - crew.replace(" ", "").length()) + "人");
        String crew2 = getCrew(results.getUserList(), "2");
        etOldLeader.setText(crew2);
        String crew3 = getCrew(results.getUserList(), "3");
        tvLeader.setText(crew3);
        tvLeaderId.setText(results.getDuty_user_name());     //工作负责人
        //tvPerson.setText(crew2 + crew3);
        tvSTime.setText(results.getBegin_time());
        tvETime.setText(results.getEnd_time());
        rvRemarkSafe.setLayoutManager(new LinearLayoutManager(this));
        safeAdapter = new SafeAdapter(R.layout.item_safe, results.getSafeList());
        rvRemarkSafe.setAdapter(safeAdapter);
        etSwitchSafe.setText(results.getRelate_device_operate());
        etDeviceSafe.setText(results.getRetain_device());
        tvPostpone.setText(results.getDelay_time());
        etRemoveGroundNo.setText(results.getRemove_ground_no());
        etRemoveGroundNum.setText(results.getRemove_ground_num());
        etCustodyMan.setText(results.getGuarder_name());
        etCustodyContent.setText(results.getGuarder_content());
        etOther.setText(results.getOther_content());
        status = results.getStatus();

        for (int i = 0; i < results.getSignList().size(); i++) {
            switch (results.getSignList().get(i).getSign()) {
                case "1":
                    showPic(results.getSignList().get(i), ivSignaturePad, "sign1.jpg");
                    if (null != results.getSignList().get(i).getSign_time() || !("").equals(results.getSignList().get(i).getSign_time())) {
                        tvSignTime1.setText(results.getSignList().get(i).getSign_time());
                    }
                    break;
                case "2":
                    showPic(results.getSignList().get(i), ivSignaturePad2, "sign2.jpg");
                    if (null != results.getSignList().get(i).getSign_time() || !("").equals(results.getSignList().get(i).getSign_time())) {
                        tvSignTime2.setText(results.getSignList().get(i).getSign_time());
                    }
                    break;
                case "3":
                    showPic(results.getSignList().get(i), ivSignaturePad3, "princpial1.jpg");
                    if (null != results.getSignList().get(i).getSign_time() || !("").equals(results.getSignList().get(i).getSign_time())) {
                        tvSignTime3.setText(results.getSignList().get(i).getSign_time());
                    }
                    break;
                case "4":
                    showPic(results.getSignList().get(i), ivSignaturePad4, "accept1.jpg");
                    if (null != results.getSignList().get(i).getSign_time() || !("").equals(results.getSignList().get(i).getSign_time())) {
                        tvSignTime4.setText(results.getSignList().get(i).getSign_time());
                    }
                    break;
                case "5":
                    showPic(results.getSignList().get(i), ivSignaturePad5, "sign3.jpg");
                    if (null != results.getSignList().get(i).getSign_time() || !("").equals(results.getSignList().get(i).getSign_time())) {
                        tvSignTime5.setText(results.getSignList().get(i).getSign_time());
                    }
                    break;
                case "6":
                    showPic(results.getSignList().get(i), ivSignaturePad6, "princpial2.jpg");
                    if (null != results.getSignList().get(i).getSign_time() || !("").equals(results.getSignList().get(i).getSign_time())) {
                        tvSignTime6.setText(results.getSignList().get(i).getSign_time());
                    }
                    break;
                case "7":
                    showPic(results.getSignList().get(i), ivSignaturePad7, "princpial3.jpg");
                    if (null != results.getSignList().get(i).getSign_time() || !("").equals(results.getSignList().get(i).getSign_time())) {
                        tvSignTime7.setText(results.getSignList().get(i).getSign_time());
                    }
                    break;
                case "8":
                    showPic(results.getSignList().get(i), ivSignaturePad8, "licence1.jpg");
                    if (null != results.getSignList().get(i).getSign_time() || !("").equals(results.getSignList().get(i).getSign_time())) {
                        tvSignTime8.setText(results.getSignList().get(i).getSign_time());
                    }
                    break;
            }
        }

        //工作任务
        if (results.getWorkList() != null && results.getWorkList().size() > 0) {
            workList.addAll(results.getWorkList());
        }
        rvTaskContent.setLayoutManager(new LinearLayoutManager(this));
        workAdapter = new WorkAdapter(R.layout.item_task_content, workList);
        rvTaskContent.setAdapter(workAdapter);

        //应挂的接地线
        if (results.getGroundList() != null && results.getGroundList().size() > 0) {
            groundList.addAll(results.getGroundList());
        }
        groundLinesToHangListView.setLayoutManager(new LinearLayoutManager(this));
        groundLineAdapter = new GroundLineAdapter(R.layout.item_ground_line, groundList);
        groundLinesToHangListView.setAdapter(groundLineAdapter);

        //确认工作票1-6页
        if (results.getWorkList() != null && results.getPermitList().size() > 0) {
            permitList.addAll(results.getPermitList());
        }
        licensingStartedListView.setLayoutManager(new LinearLayoutManager(this));
        licensingStartedAdapter = new LicensingStartedAdapter(R.layout.item_licensing_started, permitList);
        licensingStartedListView.setAdapter(licensingStartedAdapter);

        //工作终结报告
        if (results.getWorkList() != null && results.getEndList().size() > 0) {
            endList.addAll(results.getEndList());
        }
        licensingEndListView.setLayoutManager(new LinearLayoutManager(this));
        licensingEndAdapter = new LicensingEndAdapter(R.layout.item_licensing_started, endList);
        licensingEndListView.setAdapter(licensingEndAdapter);
    }

    private String getCrew(List<TicketUser> userList, String user_status) {
        String userName = "";
        if (userList.size() > 0) {
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUser_status().equals(user_status)) {
                    userName += userList.get(i).getUser_name() + " ";
                    this.userList.add(userList.get(i));
                }
            }
        }
        return userName;
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

    //参数
    private Map<String, RequestBody> setParams(FirstTicketBean bean) {
        params.put("id", toRequestBody(results == null ? "" : results.getId()));
        params.put("line_id", toRequestBody(bean.getLine_id()));
        params.put("task_id", toRequestBody(bean.getTask_id()));
        params.put("duty_user_id", toRequestBody(bean.getDuty_user_id()));
        params.put("duty_user_name", toRequestBody(bean.getDuty_user_name()));
        params.put("ticket_type", toRequestBody("1"));
        params.put("unit_id", toRequestBody(bean.getUnit_id()));
        params.put("unit_name", toRequestBody(tvUnitId.getText().toString()));
        params.put("work_dep_id", toRequestBody(tvDepId.getText().toString()));
        params.put("work_dep_name", toRequestBody(tvDepId.getText().toString()));
        params.put("begin_time", toRequestBody(bean.getBegin_time()));
        params.put("end_time", toRequestBody(bean.getEnd_time()));
        params.put("relate_device_operate", toRequestBody(bean.getRelate_device_operate()));
        params.put("retain_device", toRequestBody(bean.getRetain_device()));
        params.put("delay_time", toRequestBody(bean.getDelay_time()));
        params.put("remove_ground_no", toRequestBody(bean.getRemove_ground_no()));
        params.put("remove_ground_num", toRequestBody(bean.getRemove_ground_num()));
        params.put("guarder_name", toRequestBody(bean.getGuarder_name()));
        params.put("guarder_content", toRequestBody(bean.getGuarder_content()));
        params.put("other_content", toRequestBody(bean.getOther_content()));
        params.put("status", toRequestBody(bean.getStatus()));

        // PDA工作任务集合
        if (bean.getWorkList() != null) {
            for (int i = 0; i < bean.getWorkList().size(); i++) {
                params.put("workList[" + i + "].work_range", toRequestBody(bean.getWorkList().get(i).getWork_range()));
                params.put("workList[" + i + "].work_content", toRequestBody(bean.getWorkList().get(i).getWork_content()));
            }
        }

        // PDA接地线集合
        if (bean.getGroundList() != null) {
            for (int i = 0; i < bean.getGroundList().size(); i++) {
                params.put("groundList[" + i + "].install_site", toRequestBody(bean.getGroundList().get(i).getInstall_site()));
                params.put("groundList[" + i + "].ground_id", toRequestBody(bean.getGroundList().get(i).getGround_id()));
                params.put("groundList[" + i + "].install_time", toRequestBody(bean.getGroundList().get(i).getInstall_time()));
                params.put("groundList[" + i + "].remove_time", toRequestBody(bean.getGroundList().get(i).getRemove_time()));
            }
        }

        // PDA许可集合
        if (bean.getPermitList() != null) {
            for (int i = 0; i < bean.getPermitList().size(); i++) {
                params.put("permitList[" + i + "].permit_way", toRequestBody(bean.getPermitList().get(i).getPermit_way()));
                params.put("permitList[" + i + "].permit_user_name", toRequestBody(bean.getPermitList().get(i).getPermit_user_name()));
                params.put("permitList[" + i + "].sign_file_id", toRequestBody(bean.getPermitList().get(i).getSign_file_id()));
                params.put("permitList[" + i + "].file", toRequestBody(bean.getPermitList().get(i).getFile()));
                params.put("permitList[" + i + "].permit_time", toRequestBody(bean.getPermitList().get(i).getPermit_time()));
            }
        }

        // PDA终结报告集合
        if (bean.getEndList() != null) {
            for (int i = 0; i < bean.getEndList().size(); i++) {
                params.put("endList[" + i + "].end_way", toRequestBody(bean.getEndList().get(i).getEnd_way()));
                params.put("endList[" + i + "].permit_user_name", toRequestBody(bean.getEndList().get(i).getPermit_user_name()));
                params.put("endList[" + i + "].file", toRequestBody(bean.getEndList().get(i).getFile()));
                params.put("endList[" + i + "].end_time", toRequestBody(bean.getEndList().get(i).getEnd_time()));
            }
        }


        // PDA人员签名集合
        addPicList();
        if (bean.getSignList() != null) {
            for (int i = 0; i < bean.getSignList().size(); i++) {
                params.put("signList[" + i + "].sign", toRequestBody(bean.getSignList().get(i).getSign()));
                params.put("signList[" + i + "].sign_time", toRequestBody(bean.getSignList().get(i).getSign_time()));
                params.put("signList[" + i + "].file", toRequestBody(bean.getSignList().get(i).getFile()));
            }
        }

        //PDA工作人员集合
        if (bean.getUserList() != null) {
            for (int i = 0; i < bean.getUserList().size(); i++) {
                params.put("userList[" + i + "].user_name", toRequestBody(bean.getUserList().get(i).getUser_name()));
                params.put("userList[" + i + "].user_status", toRequestBody(bean.getUserList().get(i).getUser_status()));
                params.put("userList[" + i + "].sign", toRequestBody(bean.getUserList().get(i).getSign()));
            }
        }

        //注意事项
        if (bean.getSafeList() != null) {
            for (int i = 0; i < bean.getSafeList().size(); i++) {
                params.put("safeList[" + i + "].ticket_safe_content", toRequestBody(bean.getSafeList().get(i).getTicket_safe_content()));
                params.put("safeList[" + i + "].ticket_safe_id", toRequestBody(bean.getSafeList().get(i).getTicket_safe_id()));
            }
        }
        return params;
    }

    public RequestBody toRequestBody(String value) {
        if (value != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
            Log.d("Tag", value);
            return requestBody;
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            return requestBody;
        }
    }

    private void getAllSendToPerson(String user_status) {
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
                                showSingleChooseDialog("工作班人员", workers, user_status);
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }

    public void showSingleChooseDialog(String title, String[] workers, String user_status) {
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
                        userList.add(new TicketUser(workers[i], user_status, "2"));
                        if (user_status.equals("1")) {
                            tvCrewId.setText(nameArray);
                            tvPerson.setText("(共" + j + "人)");
                        } else if (user_status.equals("2")) {
                            etOldLeader.setText(nameArray);
                        } else if (user_status.equals("3")) {
                            tvLeader.setText(nameArray);
                            tvLeaderId.setText(nameArray);
                        }
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
            signList.add(new TicketSign("1", tvSignTime1.getText().toString(), FileUtil.fileToBase64(file1)));
        }
        if (ivSignaturePad2.getDrawable() != null) {
            File file2 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad2).getDrawable()).getBitmap(), "2");
            signList.add(new TicketSign("2", tvSignTime2.getText().toString(), FileUtil.fileToBase64(file2)));
        }
        if (ivSignaturePad3.getDrawable() != null) {
            File file3 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad3).getDrawable()).getBitmap(), "3");
            signList.add(new TicketSign("3", tvSignTime3.getText().toString(), FileUtil.fileToBase64(file3)));
        }
        if (ivSignaturePad4.getDrawable() != null) {
            File file4 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad4).getDrawable()).getBitmap(), "4");
            signList.add(new TicketSign("4", tvSignTime4.getText().toString(), FileUtil.fileToBase64(file4)));
        }
        if (ivSignaturePad5.getDrawable() != null) {
            File file5 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad5).getDrawable()).getBitmap(), "5");
            signList.add(new TicketSign("5", tvSignTime5.getText().toString(), FileUtil.fileToBase64(file5)));
        }
        if (ivSignaturePad6.getDrawable() != null) {
            File file6 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad6).getDrawable()).getBitmap(), "6");
            signList.add(new TicketSign("6", tvSignTime6.getText().toString(), FileUtil.fileToBase64(file6)));
        }
        if (ivSignaturePad7.getDrawable() != null) {
            File file7 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad7).getDrawable()).getBitmap(), "7");
            signList.add(new TicketSign("7", tvSignTime7.getText().toString(), FileUtil.fileToBase64(file7)));
        }
        if (ivSignaturePad8.getDrawable() != null) {
            File file8 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad8).getDrawable()).getBitmap(), "8");
            signList.add(new TicketSign("8", tvSignTime8.getText().toString(), FileUtil.fileToBase64(file8)));
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
                    tvSignTime1.setText(DateUatil.getMin());
                    break;
                case 2:
                    ivSignaturePad2.setImageBitmap(bitmap);
                    tvSignTime2.setText(DateUatil.getMin());
                    break;
                case 3:
                    ivSignaturePad3.setImageBitmap(bitmap);
                    tvSignTime3.setText(DateUatil.getMin());
                    break;
                case 4:
                    ivSignaturePad4.setImageBitmap(bitmap);
                    tvSignTime4.setText(DateUatil.getMin());
                    break;
                case 5:
                    ivSignaturePad5.setImageBitmap(bitmap);
                    tvSignTime5.setText(DateUatil.getMin());
                    break;
                case 6:
                    ivSignaturePad6.setImageBitmap(bitmap);
                    tvSignTime6.setText(DateUatil.getMin());
                    break;
                case 7:
                    ivSignaturePad7.setImageBitmap(bitmap);
                    tvSignTime7.setText(DateUatil.getMin());
                    break;
                case 8:
                    ivSignaturePad8.setImageBitmap(bitmap);
                    tvSignTime8.setText(DateUatil.getMin());
                    break;
            }
            if (index >= 100 && index < 200) {
                permitList.get(index - 100).setFile(FileUtil.fileToBase64(SignDialog.saveBitmapFile(bitmap, "permit.jpg")));
                licensingStartedAdapter.setData(index - 100, permitList.get(index - 100));
            } else if (index >= 200) {
                endList.get(index - 200).setFile(FileUtil.fileToBase64(SignDialog.saveBitmapFile(bitmap, "end.jpg")));
                licensingEndAdapter.setData(index - 200, this.endList.get(index - 200));
            }
        }
        SignBean.setBitmap(null);
    }

    @OnClick({R.id.title_back, R.id.iv_signature_pad, R.id.iv_signature_pad_2, R.id.iv_signature_pad_3,
            R.id.iv_signature_pad_4, R.id.iv_signature_pad_5, R.id.iv_signature_pad_6, R.id.iv_signature_pad_7,
            R.id.iv_signature_pad_8, R.id.tv_crew_id, R.id.title_setting, R.id.iv_task_add, R.id.iv_ground_lines_add,
            R.id.iv_licensing_started_add, R.id.iv_licensing_end_add, R.id.tv_postpone,
            R.id.tv_s_time, R.id.tv_e_time, R.id.tv_sign_time1, R.id.tv_sign_time2, R.id.tv_sign_time3, R.id.tv_sign_time4,
            R.id.tv_sign_time5, R.id.tv_sign_time6, R.id.tv_sign_time7, R.id.tv_sign_time8,
            R.id.et_old_leader, R.id.tv_leader, R.id.iv_safe, R.id.iv_safe_change, R.id.btn_pic, R.id.btn_preview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_safe_change:
                Intent intent = new Intent(this, SafeListActivity.class);
                intent.putExtra("chooseList", (Serializable) safeAdapter.getData());
                startActivityForResult(intent, Constant.SAFE_LIST);
                break;
            case R.id.iv_safe:
                startActivity(new Intent(this, TicketSafeActivity.class));
                break;
            case R.id.et_old_leader:
                getAllSendToPerson("2");
                break;
            case R.id.tv_leader:
                getAllSendToPerson("3");
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                if ("0".equals(status)) {     //发送给签发人
                    if (TextUtils.isEmpty(tvCrewId.getText().toString())) {
                        Toast.makeText(this, "请选择工作班人员！", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (workList.size() > 0) {
                        for (int i = 0; i < workList.size(); i++) {
                            String workRangeStr = workList.get(i).getWork_range();
                            if (TextUtils.isEmpty(workRangeStr)) {
                                Toast.makeText(this, "请填写工作任务的工作地点或地段！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }

                    //弹框选择签发人
                    showSelectQfrDialog();

                } else if ("2".equals(status)) {   //签发人已经签字需改状态
                    ProgressDialog.show(FirstWTicketActivity.this, true, "正在上传...");
                    FirstTicketBean bean = getData();
                    Map<String, RequestBody> params = setParams(bean);
                    BaseRequest.getInstance().getService().upLoadFirstTicket(params).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver(FirstWTicketActivity.this) {
                                @Override
                                protected void onSuccees(BaseResult t) throws Exception {
                                    ProgressDialog.cancle();
                                    Toast.makeText(FirstWTicketActivity.this, "提交工作票成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                    ProgressDialog.cancle();
                                    Toast.makeText(FirstWTicketActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                }


                break;
            case R.id.iv_signature_pad:
//                if (ticketType != null && status.equals(Constant.STATUS_SIGN)) {
                Intent intent1 = new Intent();
                intent1.setClass(this, SignActivity.class);
                startActivity(intent1);
                SignBean.setIndex(1);
//                }
                break;
            case R.id.iv_signature_pad_2:
//                if (ticketType != null && status.equals(Constant.STATUS_SIGN)) {
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
//                if (ticketType != null && status.equals(Constant.STATUS_ACCEPT)) {
                Intent intent4 = new Intent();
                intent4.setClass(this, SignActivity.class);
                startActivity(intent4);
                SignBean.setIndex(4);
//                }

                break;
            case R.id.iv_signature_pad_5:
//                if (ticketType != null && status.equals(Constant.STATUS_SIGN)) {
                Intent intent5 = new Intent();
                intent5.setClass(this, SignActivity.class);
                startActivity(intent5);
                SignBean.setIndex(5);
//                }

                break;
            case R.id.iv_signature_pad_6:
//                if (ticketType != null && status.equals(Constant.STATUS_PRINCPIAL)) {
                Intent intent6 = new Intent();
                intent6.setClass(this, SignActivity.class);
                startActivity(intent6);
                SignBean.setIndex(6);
//                }

                break;
            case R.id.iv_signature_pad_7:
//                if (ticketType != null && status.equals(Constant.STATUS_PRINCPIAL)) {
                Intent intent7 = new Intent();
                intent7.setClass(this, SignActivity.class);
                startActivity(intent7);
                SignBean.setIndex(7);
//                }

                break;
            case R.id.iv_signature_pad_8:
//                if (ticketType != null && status.equals(Constant.STATUS_LICENCE)) {
                Intent intent8 = new Intent();
                intent8.setClass(this, SignActivity.class);
                startActivity(intent8);
                SignBean.setIndex(8);
//                }

                break;
            case R.id.tv_crew_id:
                //getAllSendToPerson("1");
                if (mulchoice == null) {
                    Toast.makeText(this, "无可选择的工作班人员!", Toast.LENGTH_SHORT).show();
                    return;
                }

                getAllSendToPerson();
                break;
            case R.id.tv_s_time:
                PickerUtils.showDate(FirstWTicketActivity.this, tvSTime);
                break;
            case R.id.tv_e_time:
                PickerUtils.showDate(FirstWTicketActivity.this, tvETime);
                break;
            case R.id.iv_task_add:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_task_add, null);
                builder.setView(dialogView);
                TextView tvName = dialogView.findViewById(R.id.tv_name);
                tvName.setVisibility(View.GONE);
                EditText et1 = dialogView.findViewById(R.id.et_1);
                et1.setVisibility(View.GONE);
                EditText et2 = dialogView.findViewById(R.id.et_2);
                EditText et3 = dialogView.findViewById(R.id.et_3);
                builder.show().setOnDismissListener(listener -> {
                    if (et2.getText().toString().isEmpty() || et3.getText().toString().isEmpty()) {
                        Toast.makeText(FirstWTicketActivity.this, "请补全任务信息", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //添加新任务的时候，之前的Edit Text内容未保存  TODO

                    workList.add(new TicketWork(et2.getText().toString(), et3.getText().toString()));
                    if (workAdapter != null) {
                        workAdapter.setNewData(workList);
                    }
                });
                break;
            case R.id.iv_ground_lines_add:
                groundList.add(new TicketFirstGround("", "", getResources().getString(R.string.work_ticket_time), getResources().getString(R.string.work_ticket_time)));
                groundLineAdapter.setNewData(groundList);
                break;
            case R.id.iv_licensing_started_add:
                permitList.add(new TicketFirstPermit("", "", "", getResources().getString(R.string.work_ticket_time)));
                licensingStartedAdapter.setNewData(permitList);
                break;
            case R.id.iv_licensing_end_add:
                endList.add(new TicketFirstEnd("", "", "", getResources().getString(R.string.work_ticket_time)));
                licensingEndAdapter.setNewData(endList);
                break;
            case R.id.tv_postpone:
                PickerUtils.showDate(FirstWTicketActivity.this, tvPostpone);
                break;
            case R.id.btn_pic:
                View view1 = getLayoutInflater().inflate(R.layout.activity_third_working_ticket, null);
                Bitmap bitmapFromScroll = GetViewBitmapUtils.getBitmapFromScroll(svTicket);
                photoPath = Environment.getExternalStorageDirectory().getPath();
                photoName = "firstTicket" + System.currentTimeMillis();
                GetViewBitmapUtils.savePhoto(bitmapFromScroll, photoPath, photoName);
                Toast.makeText(this, "在文件路径" + photoPath + "下，已生成图片", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_preview:
                if (photoPath != null && photoName != null) {
                    Intent intentPreview = new Intent(this, PreviewActivity.class);
                    intentPreview.putExtra("photo", photoPath + "/" + photoName);
                    startActivity(intentPreview);
                } else {
                    Toast.makeText(this, "请先生成图片", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void showSelectQfrDialog() {
        QfrDialog qfrDialog = new QfrDialog(this, "选择签发人", "取消", "确定", userData) {
            @Override
            public void ok() {
                super.ok();
                signPosition = getQfr1Position();
                signPosition2 = getQfr2Position();

                ProgressDialog.show(FirstWTicketActivity.this, true, "正在上传...");
                FirstTicketBean bean = getData();
                Map<String, RequestBody> params = setParams(bean);
                BaseRequest.getInstance().getService().upLoadFirstTicket(params).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver(FirstWTicketActivity.this) {
                            @Override
                            protected void onSuccees(BaseResult t) throws Exception {
                                ProgressDialog.cancle();
                                //Toast.makeText(FirstWTicketActivity.this, "提交工作票成功！", Toast.LENGTH_SHORT).show();
                                sendToQfr();
                                //finish();
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                ProgressDialog.cancle();
                                Toast.makeText(FirstWTicketActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                dismiss();
            }

            @Override
            public void cancle() {
                super.cancle();
                dismiss();
            }
        };
        qfrDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.SAFE_LIST) {
                List<TicketSafeContent> sureList = (List<TicketSafeContent>) data.getSerializableExtra("sureList");
                safeAdapter.setNewData(sureList);
            }
        }
    }

    private void getAllSendToPerson() {
        List<SelectWorkerBean.SelectUserInfo> workerSelectUserList = workerList.getUserInfos();
        if (workerSelectUserList != null && workerSelectUserList.size() > 0) {
            String[] workers = new String[workerSelectUserList.size()];
            String[] workers_id = new String[workerSelectUserList.size()];

            for (int i = 0; i < workerSelectUserList.size(); i++) {
                workers[i] = workerSelectUserList.get(i).getUserName();
                workers_id[i] = workerSelectUserList.get(i).getUserId();
            }

            showSelectDialog(workers, workerSelectUserList);
        }
    }


    private void showSelectDialog(String[] workers, List<SelectWorkerBean.SelectUserInfo> workerSelectUserList) {
        //List<SelectWorkerBean.SelectUserInfo> selectUserInfos = new ArrayList<>();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(FirstWTicketActivity.this);
        builder.setTitle("工作人员选择");
        builder.setMultiChoiceItems(workers, mulchoice, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                mulchoice[which] = isChecked;
                Log.i("MyTest", "-->which=" + which);

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = "";
                for (int i = 0; i < workers.length; i++) {
                    if (mulchoice[i]) {
                        s = s + workers[i] + ",";
                        TicketUser ticketUser = new TicketUser(workerSelectUserList.get(i).getUserName());
                        ticketUser.setUser_id(workerSelectUserList.get(i).getUserId());
                        ticketUser.setUser_status("1");
                        ticketUser.setSign("2");
                        userList.add(ticketUser);
                    }
                }
                //selectWorkerBean.setUserInfos(selectUserInfos);
                tvCrewId.setText(s.substring(0, s.length() - 1));
                tvPerson.setText("共" + userList.size() + "人");
            }
        });

        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }


}
