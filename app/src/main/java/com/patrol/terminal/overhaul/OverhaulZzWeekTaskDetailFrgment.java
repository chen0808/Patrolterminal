package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liulishuo.filedownloader.FileDownloader;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.ControlCardActivity;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AddressBookLevel2;
import com.patrol.terminal.bean.AllControlCarBean;
import com.patrol.terminal.bean.ClassMemberBean;
import com.patrol.terminal.bean.ControlCardBean;
import com.patrol.terminal.bean.OverhaulSendUserBean2;
import com.patrol.terminal.bean.OverhaulUserInfo;
import com.patrol.terminal.bean.OverhaulZzTaskBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.NoScrollListView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/*其他人员周任务*/
public class OverhaulZzWeekTaskDetailFrgment extends BaseFragment {
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
    @BindView(R.id.week_plan_device_name)
    TextView weekPlanDeviceName;
    @BindView(R.id.month_plan_month)
    TextView monthPlanMonth;
    @BindView(R.id.week_plan_time)
    TextView weekPlanTime;
    @BindView(R.id.work_of_task)
    TextView workOfTask;
    @BindView(R.id.work_of_task_tv)
    TextView workOfTaskTv;
    @BindView(R.id.risk_level)
    TextView riskLevel;
    @BindView(R.id.risk_level_tv)
    TextView riskLevelTv;
    @BindView(R.id.week_plan_content)
    TextView weekPlanContent;
    @BindView(R.id.control_card_name)
    TextView controlCardName;
    @BindView(R.id.add_btn)
    Button addBtn;
    @BindView(R.id.work_task_lv)
    NoScrollListView workTaskLv;
    @BindView(R.id.task_ll)
    LinearLayout taskLl;
    @BindView(R.id.week_plan_remark)
    TextView weekPlanRemark;
    @BindView(R.id.tv_accept)
    NiceSpinner tvAccept;
    @BindView(R.id.tv_sign)
    NiceSpinner tvSign;
    @BindView(R.id.tv_sign2)
    NiceSpinner tvSign2;
    @BindView(R.id.tv_licence)
    NiceSpinner tvLicence;
    @BindView(R.id.ll_person)
    LinearLayout llPerson;
    @BindView(R.id.work_ticket_tv)
    TextView workTicketTv;
    @BindView(R.id.ns_work_ticket)
    NiceSpinner nsWorkTicket;
    @BindView(R.id.ns_work_ticket2)
    NiceSpinner nsWorkTicket2;
    @BindView(R.id.control_card)
    TextView controlCard;
    @BindView(R.id.ns_control_card)
    NiceSpinner nsControlCard;
    @BindView(R.id.ll_control_card)
    LinearLayout llControlCard;
    @BindView(R.id.need_upload_yes)
    RadioButton needUploadYes;
    @BindView(R.id.need_upload_no)
    RadioButton needUploadNo;
    @BindView(R.id.need_upload_rg)
    RadioGroup needUploadRg;
    @BindView(R.id.need_upload_ll)
    LinearLayout needUploadLl;
    @BindView(R.id.btn_file)
    Button btnFile;
    @BindView(R.id.ns_file)
    NiceSpinner nsFile;
    @BindView(R.id.ll_upload_file)
    LinearLayout llUploadFile;
    @BindView(R.id.power_preservation_iv)
    ImageView powerPreservationIv;
    @BindView(R.id.power_preservation_grogress)
    TextView powerPreservationGrogress;
    @BindView(R.id.power_preservation_rl)
    RelativeLayout powerPreservationRl;
    @BindView(R.id.acceptance_plan_iv)
    ImageView acceptancePlanIv;
    @BindView(R.id.acceptance_plan_grogress)
    TextView acceptancePlanGrogress;
    @BindView(R.id.acceptance_plan_rl)
    RelativeLayout acceptancePlanRl;
    @BindView(R.id.ll_work_ticket)
    LinearLayout llWorkTicket;
    @BindView(R.id.dian_risk_level)
    TextView dianRiskLevel;
    @BindView(R.id.dian_risk_level_tv)
    TextView dianRiskLevelTv;
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.select_worker_tv)
    TextView selectWorkerTv;
    @BindView(R.id.work_person_select_ll)
    LinearLayout workPersonSelectLl;
    private String jobType;
    private int nicePosition = 0;
    private List<String> nameType = new ArrayList<>();
    private ControlCardBean controlCardId;
    private AllControlCarBean allControlCarBean;
    // List<OverhaulZZSendBean.UserInfo> userList = new ArrayList<>();
    private List<String> fileList = new ArrayList<>();
    private int filePosition = 0;
    private List<AddressBookLevel2> nameList1 = new ArrayList<>();
    private List<AddressBookLevel2> nameList2 = new ArrayList<>();
    private List<OverhaulUserInfo> userData = new ArrayList<>();
    private List<String> data = new ArrayList<>();
    private List<String> data2 = new ArrayList<>();
    private List<String> data3 = new ArrayList<>();
    private List<String> data4 = new ArrayList<>();
    private List<OverhaulSendUserBean2> overhaulSendUserBeans = new ArrayList<>();
    private List<ClassMemberBean.UserListBean> userListBeans;
    private int signPosition = 0;
    private int signPosition2 = 0;
    private int licencePosition = 0;
    private int acceptPosition = 0;
    private OverhaulZzTaskBean overhaulZzTaskBean;
    private int nicePosition2 = 0;
    private String voltage_statu = "1";
    private String powername;
    private String powerURL;
    private String acceptname;
    private String acceptURL;

    private String taskId;
    private Disposable subscribe;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_overhaul_week_task_detail_plan, container, false);
        FileDownloader.setup(getContext());
        taskId = getActivity().getIntent().getStringExtra("id");
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        initId();
        return view;
    }

    @Override
    protected void initData() {
        titleItem.setVisibility(View.GONE);
        initView();
    }


    private void initId() {  //查询任务

        BaseRequest.getInstance().getService().getZzTaskInfo(taskId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<OverhaulZzTaskBean>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<OverhaulZzTaskBean> t) throws Exception {
                        overhaulZzTaskBean = t.getResults();

                        initView();
                        //initFileList();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    private void initView() {
        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {
            titleSetting.setVisibility(View.GONE);
            controlCard.setText("查看控制卡");
            nsControlCard.setVisibility(View.GONE);
        }

        weekPlanDeviceName.setText(overhaulZzTaskBean.getLine_name());
        weekPlanTime.setText(overhaulZzTaskBean.getBlackout_days() + "天");
        workOfTaskTv.setText(overhaulZzTaskBean.getTask_source());
        riskLevelTv.setText(overhaulZzTaskBean.getRisk_level());
        dianRiskLevelTv.setText("2");
        weekPlanContent.setText(overhaulZzTaskBean.getTask_content());
        weekPlanRemark.setText(overhaulZzTaskBean.getRemark());
//        needUploadRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.need_upload_yes:
//                        llUploadFile.setVisibility(View.VISIBLE);
//                        voltage_statu = "1";
//                        break;
//                    case R.id.need_upload_no:
//                        llUploadFile.setVisibility(View.GONE);
//                        voltage_statu = "0";
//                        break;
//                }
//            }
//        });
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                if (s.startsWith("publishRepair")) {
                    initId();
                }
            }
        });
//        initTicket1();

        controlCard();
        getControlCardContent();
        //getFzrInfo(planId, "2");

        getAllSendToPerson();
        getAllSendToPerson2();
    }

/*    private void initTicket1() {
        //专责进来没有选择的时候显示,其他时候隐藏
        if (planRepairBean.getTicket_type().equals("0") && jobType.contains(Constant.REFURBISHMENT_MEMBER) && "2".equals(overhaulMonthBean.getStatus())) {
            nsWorkTicket.setVisibility(View.VISIBLE);
            nsWorkTicket2.setVisibility(View.VISIBLE);
            workTicketTv.setText("填写工作票");
            nicePosition = 1;
            nicePosition2 = 1;
            data3.clear();
            data3.add("第一种工作票");
            data3.add("第二种工作票");
            data3.add("电力线路带电作业工作票");
            data3.add("事故应急抢修单");
            nsWorkTicket.attachDataSource(data3);
            data4.clear();
            data4.add("各类检修工作");
            data4.add("修建树木");
            data4.add("竣工验收");
            nsWorkTicket2.attachDataSource(data4);
            nsWorkTicket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    nicePosition = position + 1;
                    initTicket2(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void initTicket2(int position) {
        if (planRepairBean.getTicket_task_type().equals("0")) {
            data4.clear();
            if (position == 0) {
                data4.add("各类检修工作");
                data4.add("修建树木");
                data4.add("竣工验收");
            } else if (position == 1) {
                data4.add("悬挂相序牌");
                data4.add("调整拉线");
                data4.add("拆除异物");
                data4.add("拆除鸟巢");
                data4.add("擦拭热点");
                data4.add("安装放松垫");
                data4.add("安装,拆除在线监测设备");
            } else if (position == 2) {
                data4.add("拆除异物");
                data4.add("处理引流发热");
                data4.add("更换导线防震锤");
                data4.add("更换地线金具开口销");
                data4.add("调整地线防震锤");
            } else if (position == 3) {
                data4.add("");
            }
            nsWorkTicket2.attachDataSource(data4);
            nsWorkTicket2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    nicePosition2 = position + 1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }*/

   /* public void initFileList() {
        fileList.clear();
        File[] files = new File("/storage/emulated/0").listFiles();

        for (File file : files) {
            if (file.getName().endsWith("xls") || file.getName().endsWith("xlsx") || file.getName().endsWith("doc") || file.getName().endsWith("docx"))
                fileList.add(file.getName());
        }
        if (fileList.size() > 0) {
            nsFile.attachDataSource(fileList);
        }
        nsFile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }*/

//    private String leaderName;
//    private String leaderId;
//
//    private void getFzrInfo(String planId, String status) {
//        final OverhaulMonthBean overhaulMonthBean;
//        BaseRequest.getInstance().getService()
//                .getFzrInfoByWeekId(planId, status)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<OverhaulMonthBean>>(getContext()) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<OverhaulMonthBean>> t) throws Exception {
//                        if (t.getCode() == 1) {
//                            if (t.getResults() != null && t.getResults().size() > 0) {
//
//                                leaderName = t.getResults().get(0).getUser_name();
//                                leaderId = t.getResults().get(0).getUser_id();
////                                controlCardPersonal.setText(leaderName);
//                            }
//                        } else {
//                            Toast.makeText(mContext, t.getMsg(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//                    }
//                });
//    }

    private void controlCard() {
        BaseRequest.getInstance().getService()
                .controlCardType("kzk")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ControlCardBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<ControlCardBean>> t) throws Exception {
                        Log.w("linmeng", "t.toString():" + t.toString());
                        if (t.getCode() == 1) {
                            List<ControlCardBean> results = t.getResults();
                            for (int i = 0; i < results.size(); i++) {
                                nameType.add(results.get(i).getName());
                            }
                            nsControlCard.attachDataSource(nameType);
                            controlCardId = results.get(0);
                            nsControlCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    controlCardId = results.get(position);
                                    Log.w("linmeng", "controlCardId:" + controlCardId.getId());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            Toast.makeText(mContext, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void getControlCardContent() {
        BaseRequest.getInstance().getService()
                .getControlCardContent(overhaulZzTaskBean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AllControlCarBean>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<AllControlCarBean> t) throws Exception {

                        if (t.getCode() == 1) {
                            allControlCarBean = t.getResults();
                            if (jobType.contains(Constant.REFURBISHMENT_MEMBER)) {  //负责人进来, 填写过的将数据带过去
                                if (allControlCarBean == null) {   //负责人第一次进来
                                    nsControlCard.setVisibility(View.VISIBLE);
                                } else {
                                    if (allControlCarBean.getCardControl() == null && allControlCarBean.getCardQuality() == null && allControlCarBean.getCardTool().size() == 0) {
                                        nsControlCard.setVisibility(View.VISIBLE);
                                    } else {
//                                    nsWorkTicket.setVisibility(View.GONE);
                                        nsControlCard.setVisibility(View.GONE);
                                    }
                                }

                            } else {   //其他人进控制卡
                                nsControlCard.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void getAllSendToPerson() {
        BaseRequest.getInstance().getService()
                .getSendOverhaulUsers2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulSendUserBean2>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulSendUserBean2>> t) throws Exception {
                        if (t.getCode() == 1) {
                            for (int i = 0; i < t.getResults().size(); i++) {
                                OverhaulSendUserBean2 bean = t.getResults().get(i);
                                if (!bean.getUserList().isEmpty() && bean.getUserList().size() > 0 && bean.getName().contains("专责")) {
                                    for (int i1 = 0; i1 < bean.getUserList().size(); i1++) {
                                        overhaulSendUserBeans.add(bean);
                                    }
                                }
                            }
                            data.clear();
                            for (int i = 0; i < overhaulSendUserBeans.size(); i++) {
                                for (int i1 = 0; i1 < overhaulSendUserBeans.get(i).getUserList().size(); i1++) {
                                    String userId = overhaulSendUserBeans.get(i).getUserList().get(i1).getId();
                                    String userName = overhaulSendUserBeans.get(i).getUserList().get(i1).getName();
                                    data.add(userName);
                                    userData.add(new OverhaulUserInfo(userId, userName));
                                }
                            }
                            tvSign.attachDataSource(data);
                            tvSign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    signPosition = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            tvSign2.attachDataSource(data);
                            tvSign2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    signPosition2 = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            tvLicence.attachDataSource(data);
                            tvLicence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    licencePosition = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    private void getAllSendToPerson2() {
        BaseRequest.getInstance().getService()
                .getAllClassMember("B7FF21A674F144DE8D13EB8B3B79E64F")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ClassMemberBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<ClassMemberBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            if (t.getResults() != null && t.getResults().size() > 0) {
                                userListBeans = t.getResults().get(0).getUserList();
                                data2.clear();
                                for (int i = 0; i < userListBeans.size(); i++) {
                                    data2.add(userListBeans.get(i).getName());
                                }
                                tvAccept.attachDataSource(data2);
                                tvAccept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        acceptPosition = position;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

//    private void sendToMember() {
////        OverhaulFzrSendBean overhaulFzrSendBean = new OverhaulFzrSendBean();
////        overhaulFzrSendBean.setId(overhaulMonthBean.getId());
////        overhaulFzrSendBean.setTask_status("3");
////        List<OverhaulFzrSendBean.UserInfo> userList = new ArrayList<>();
////
////        OverhaulFzrSendBean.UserInfo userInfo1 = new OverhaulFzrSendBean.UserInfo();
////        userInfo1.setUser_id(userData.get(signPosition).getUser_id());
////        userInfo1.setUser_name(userData.get(signPosition).getUser_name());
////        userInfo1.setSign("6");
////        userList.add(userInfo1);
////
////        OverhaulFzrSendBean.UserInfo userInfo2 = new OverhaulFzrSendBean.UserInfo();
////        userInfo2.setUser_id(userData.get(signPosition2).getUser_id());
////        userInfo2.setUser_name(userData.get(signPosition2).getUser_name());
////        userInfo2.setSign("6");
////        userList.add(userInfo2);
////
////        OverhaulFzrSendBean.UserInfo userInfo3 = new OverhaulFzrSendBean.UserInfo();
////        userInfo3.setUser_id(userData.get(licencePosition).getUser_id());
////        userInfo3.setUser_name(userData.get(licencePosition).getUser_name());
////        userInfo3.setSign("7");
////        userList.add(userInfo3);
////
////        overhaulFzrSendBean.setUserList(userList);
////
////        BaseRequest.getInstance().getService()
////                .sendOverhaulFzrPlan(overhaulFzrSendBean)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(new BaseObserver<List<OverhaulSendUserBean>>(getContext()) {
////                    @Override
////                    protected void onSuccees(BaseResult<List<OverhaulSendUserBean>> t) throws Exception {
////                        if(t.getCode() == 1) {
////                            //result = t.getResults();
////                            Toast.makeText(getContext(), "分发成功！", Toast.LENGTH_SHORT).show();
////                            //产生待办
////                            deal();
////                            //更新详情状态
////                            initId();
////                        }else {
////                            Toast.makeText(getContext(), "分发失败！", Toast.LENGTH_SHORT).show();
////                        }
////                    }
////
////                    @Override
////                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
////                    }
////                });
////
////    }


//    public void openWord(String fileurl) {
//        FileUtil fileUtil = new FileUtil();
//        if (fileurl.endsWith("doc") || fileurl.endsWith("docx")) {
//            Intent intent = fileUtil.getWordFileIntent(getContext(), getContext().getPackageName(), fileurl);
//            startActivity(intent);
//        } else if (fileurl.endsWith("xls") || fileurl.endsWith("xlsx")) {
//            Intent intent = fileUtil.getExcelFileIntent(getContext(), getContext().getPackageName(), fileurl);
//            startActivity(intent);
//        }
//    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.control_card, R.id.btn_file, R.id.acceptance_plan_rl, R.id.power_preservation_rl, R.id.work_ticket_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.acceptance_plan_rl:
//                String defaultSaveRootPath1 = FileDownloadUtils.getDefaultSaveRootPath();
//                String fileurl = defaultSaveRootPath1.substring(0, defaultSaveRootPath1.length() - 5) + acceptname;
//                File file1 = new File(fileurl);
//                //判断文件是否存在，如果存在就打开，不存在就下载
//                if (file1.exists()) {
//                    openWord(fileurl);
//                } else {
//                    String url = BaseUrl.BASE_URL + acceptURL;
//                    downloadFile(url, fileurl, 2);
//                }
                break;
            case R.id.power_preservation_rl:
//                String defaultSaveRootPath = FileDownloadUtils.getDefaultSaveRootPath();
//                String path = defaultSaveRootPath.substring(0, defaultSaveRootPath.length() - 5) + powername;
//                File file = new File(path);
//                //判断文件是否存在，如果存在就打开，不存在就下载
//                if (file.exists()) {
//                    openWord(path);
//                } else {
//                    String url = BaseUrl.BASE_URL + powerURL;
//                    downloadFile(url, path, 1);
//                }
                break;
            case R.id.btn_file:
                //上传文件
                //uploadWord();
                break;
            case R.id.title_setting:
                break;
            case R.id.work_ticket_tv:
//                if (planRepairBean != null && !planRepairBean.getTicket_type().equals("0")) {
//                    nicePosition = Integer.valueOf(planRepairBean.getTicket_type());
//                    nicePosition2 = Integer.valueOf(planRepairBean.getTicket_task_type());
//                    Log.d("task__type", "type:" + nicePosition + "------task_type:" + nicePosition2);
//                }
//                if (jobType.contains(Constant.REFURBISHMENT_MEMBER) && "2".equals(overhaulMonthBean.getStatus())) {  //负责人进来, 填写过的将数据带过去
//                    switch (nicePosition) {
//                        case 1:
//                            Intent intent11 = new Intent(OverhaulWeekPlanDetailActivity.getContext(), FirstWTicketActivity.class);
//                            intent11.putExtra("bean", overhaulMonthBean);
//                            intent11.putExtra("type", String.valueOf(nicePosition));
//                            intent11.putExtra("task_type", String.valueOf(nicePosition2));
//                            intent11.putExtra("leaderName", leaderName);
//                            startActivity(intent11);
//                            break;
//                        case 2:
//                            Intent intent12 = new Intent(OverhaulWeekPlanDetailActivity.getContext(), SecondWTicketActivity.class);
//                            intent12.putExtra("bean", overhaulMonthBean);
//                            intent12.putExtra("type", String.valueOf(nicePosition));
//                            intent12.putExtra("task_type", String.valueOf(nicePosition2));
//                            intent12.putExtra("leaderName", leaderName);
//                            startActivity(intent12);
//                            break;
//                        case 3:
//                            Intent intent13 = new Intent(OverhaulWeekPlanDetailActivity.getContext(), ThirdWTicketActivity.class);
//                            intent13.putExtra("bean", overhaulMonthBean);
//                            intent13.putExtra("type", String.valueOf(nicePosition));
//                            intent13.putExtra("task_type", String.valueOf(nicePosition2));
//                            intent13.putExtra("leaderName", leaderName);
//                            startActivity(intent13);
//                            break;
//                        case 4:
//                            Intent intent14 = new Intent(OverhaulWeekPlanDetailActivity.getContext(), FourWTicketActivity.class);
//                            intent14.putExtra("bean", overhaulMonthBean);
//                            intent14.putExtra("type", String.valueOf(nicePosition));
//                            intent14.putExtra("task_type", String.valueOf(nicePosition2));
//                            intent14.putExtra("leaderName", leaderName);
//                            startActivity(intent14);
//                            break;
//                    }
//                } else {      //其他人员进来
//                    if (overhaulZzTaskBean.get().equals("0")) {
//                        Toast.makeText(getContext(), "当前无工作票！", Toast.LENGTH_SHORT).show();
//                    } else {
////                        for (OverhaulMonthBean.UserListBean userListBean : results.getUserList()) {
//////                            if (userListBean.getId().equals(SPUtil.getUserId(getContext()))) {
//////                                status = userListBean.getStatus();
//////                            }
//////                        }
//                        switch (nicePosition) {
//                            case 1:
//                                Intent intent11 = new Intent(getContext(), FirstWTicketActivity.class);
//                                intent11.putExtra("bean", overhaulMonthBean);
//                                intent11.putExtra("type", String.valueOf(nicePosition));
//                                intent11.putExtra("task_type", String.valueOf(nicePosition2));
//                                intent11.putExtra("leaderName", leaderName);
//                                startActivity(intent11);
//                                break;
//                            case 2:
//                                Intent intent12 = new Intent(getContext(), SecondWTicketActivity.class);
//                                intent12.putExtra("bean", overhaulMonthBean);
//                                intent12.putExtra("type", String.valueOf(nicePosition));
//                                intent12.putExtra("task_type", String.valueOf(nicePosition2));
//                                intent12.putExtra("leaderName", leaderName);
//                                startActivity(intent12);
//                                break;
//                            case 3:
//                                Intent intent13 = new Intent(getContext(), ThirdWTicketActivity.class);
//                                intent13.putExtra("bean", overhaulMonthBean);
//                                intent13.putExtra("type", String.valueOf(nicePosition));
//                                intent13.putExtra("task_type", String.valueOf(nicePosition2));
//                                intent13.putExtra("leaderName", leaderName);
//                                startActivity(intent13);
//                                break;
//
//                            case 4:
//                                Intent intent14 = new Intent(getContext(), FourWTicketActivity.class);
//                                intent14.putExtra("bean", overhaulMonthBean);
//                                intent14.putExtra("type", String.valueOf(nicePosition));
//                                intent14.putExtra("task_type", String.valueOf(nicePosition2));
//                                intent14.putExtra("leaderName", leaderName);
//                                startActivity(intent14);
//                                break;
//                        }
//                    }
//                }
                break;
            case R.id.control_card:
                int entenType;
                if (allControlCarBean == null) {
                    Toast.makeText(getContext(), "当前无控制卡！", Toast.LENGTH_SHORT).show();
                } else {
                    if (allControlCarBean.getCardTool() == null && allControlCarBean.getCardQuality() == null && allControlCarBean.getCardTool().size() == 0) {
                        Toast.makeText(getContext(), "当前无控制卡！", Toast.LENGTH_SHORT).show();
                    } else {
                        entenType = Constant.IS_OTHER_LOOK;
                        Intent intent2 = new Intent(getContext(), ControlCardActivity.class);
                        intent2.putExtra(Constant.CONTROL_CARD_ENTER_TYPE, entenType); //其他人员查看模式
                        intent2.putExtra("bean", overhaulZzTaskBean);
                        intent2.putExtra("allControlBean", allControlCarBean);
                        intent2.putExtra("id", controlCardId);
                        //intent2.putExtra("leaderName", leaderName);
                        //intent2.putExtra("leaderId", leaderId);
                        startActivityForResult(intent2, 1001);
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            getControlCardContent();
        }
    }

  /*  private void uploadWord() {
        ProgressDialog.show(getContext(), false, "正在上传....");
        Map<String, RequestBody> params = new HashMap<>();
        params.put("data_id", toRequestBody(overhaulMonthBean.getRepair_id()));
        if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)) {
            params.put("repair_type", toRequestBody("1"));  //0保电1验收
        } else if (jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
            params.put("repair_type", toRequestBody("2"));  //0保电1验收
        }
        File file = new File("/storage/emulated/0/" + fileList.get(filePosition));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        params.put("file\"; filename=\"" + file.getName(), requestFile);
        BaseRequest.getInstance().getService()
                .uploadWords(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            Toast.makeText(getContext(), "文件上传成功", Toast.LENGTH_SHORT).show();
                            initId();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }*/

    public RequestBody toRequestBody(String value) {
        if (value != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
            return requestBody;
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            return requestBody;
        }
    }


//    private void downloadFile(String path, String saveLocalPath, int type) {
//        if (type == 1) {
//            powerPreservationGrogress.setVisibility(View.VISIBLE);
//        } else {
//            acceptancePlanGrogress.setVisibility(View.VISIBLE);
//        }
//        FileDownloader.getImpl().create(path)
//                .setPath(saveLocalPath)
//                .setListener(new FileDownloadListener() {
//                    @Override
//                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                    }
//
//                    @Override
//                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
//                    }
//
//                    @Override
//                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                        int percent = (int) ((double) soFarBytes / (double) totalBytes * 100);
//                        if (type == 1) {
//                            powerPreservationGrogress.setText(percent + "%");
//                        } else {
//                            acceptancePlanGrogress.setText(percent + "%");
//                        }
//                    }
//
//                    @Override
//                    protected void blockComplete(BaseDownloadTask task) {
//                    }
//
//                    @Override
//                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
//                    }
//
//                    @Override
//                    protected void completed(BaseDownloadTask task) {
//                        if (type == 1) {
//                            powerPreservationGrogress.setVisibility(View.GONE);
//                        } else {
//                            acceptancePlanGrogress.setVisibility(View.GONE);
//                        }
//                        Toast.makeText(getContext(), "文件下载完成", Toast.LENGTH_SHORT).show();
//                        openWord(saveLocalPath);
//                    }
//
//                    @Override
//                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                    }
//
//                    @Override
//                    protected void error(BaseDownloadTask task, Throwable e) {
//                        if (type == 1) {
//                            powerPreservationGrogress.setVisibility(View.GONE);
//                        } else {
//                            acceptancePlanGrogress.setVisibility(View.GONE);
//                        }
//                        Toast.makeText(getContext(), "文件下载失败", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    protected void warn(BaseDownloadTask task) {
//                    }
//                }).start();
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
