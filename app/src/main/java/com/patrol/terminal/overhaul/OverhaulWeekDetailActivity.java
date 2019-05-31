package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.WeekTaskAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.OverhaulSendUserBean;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.OverhaulZZSendBean;
import com.patrol.terminal.bean.WeekTaskInfo;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.NoScrollListView;
import com.patrol.terminal.widget.ProgressDialog;

import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/*专责周计划详情,保电,验收,安全,周详情界面*/
public class OverhaulWeekDetailActivity extends BaseActivity {

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
    @BindView(R.id.dian_risk_level)
    TextView dianRiskLevel;
    @BindView(R.id.dian_risk_level_tv)
    TextView dianRiskLevelTv;

    TextView safeSeekbarNum;
    @BindView(R.id.seekbar_ll)
    LinearLayout seekbarLl;
    @BindView(R.id.plan_progressbar_tv)
    TextView planProgressbarTv;
    @BindView(R.id.plan_progressbar_probar)
    ProgressBar planProgressbarProbar;
    @BindView(R.id.plan_progressbar_num)
    TextView planProgressbarNum;
    @BindView(R.id.plan_progressbar_ll)
    LinearLayout planProgressbarLl;
    @BindView(R.id.baodian_progressbar_tv)
    TextView baodianProgressbarTv;
    @BindView(R.id.baodian_progressbar_probar)
    ProgressBar baodianProgressbarProbar;
    @BindView(R.id.baodian_progressbar_num)
    TextView baodianProgressbarNum;
    @BindView(R.id.baodian_progressbar_ll)
    LinearLayout baodianProgressbarLl;
    @BindView(R.id.yanshou_progressbar_tv)
    TextView yanshouProgressbarTv;
    @BindView(R.id.yanshou_progressbar_probar)
    ProgressBar yanshouProgressbarProbar;
    @BindView(R.id.yanshou_progressbar_num)
    TextView yanshouProgressbarNum;
    @BindView(R.id.yanshou_progressbar_ll)
    LinearLayout yanshouProgressbarLl;
    @BindView(R.id.safe_progressbar_tv)
    TextView safeProgressbarTv;
    @BindView(R.id.safe_progressbar_probar)
    ProgressBar safeProgressbarProbar;
    @BindView(R.id.safe_progressbar_num)
    TextView safeProgressbarNum;
    @BindView(R.id.safe_progressbar_ll)
    LinearLayout safeProgressbarLl;

    private String jobType;
    private String userId;

    //private int nicePosition = 1;
    //private List<String> nameType = new ArrayList<>();
    // private ControlCardBean controlCardId;
    //private AllControlCarBean allControlCarBean;
    private List<String> fileList = new ArrayList<>();
    private int filePosition = 0;
    //    private List<AddressBookLevel2> nameList1 = new ArrayList<>();
//    private List<AddressBookLevel2> nameList2 = new ArrayList<>();
//    private List<OverhaulUserInfo> userData = new ArrayList<>();
//    private List<String> data = new ArrayList<>();
//    private List<String> data2 = new ArrayList<>();
//    private List<String> data3 = new ArrayList<>();
//    private List<String> data4 = new ArrayList<>();
//    private List<OverhaulSendUserBean2> overhaulSendUserBeans = new ArrayList<>();
//    private List<ClassMemberBean.UserListBean> userListBeans;
//    private int signPosition = 0;
//    private int signPosition2 = 0;
//    private int licencePosition = 0;
//    private int acceptPosition;
    private OverhaulYearBean results;
    private int nicePosition2 = 1;
    private String voltage_statu = "1";
    private String powername;
    private String powerURL;
    private String acceptname;
    private String acceptURL;
    private String status;

    private String planId;

    //private List<WeekTaskInfo> mWeekTaskList = new ArrayList<>();
    private List<WeekTaskInfo> taskList = new ArrayList<>();
    private WeekTaskAdapter mWeekTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overhaul_week_plan_detail_plan);
        ButterKnife.bind(this);
        FileDownloader.setup(this);

        planId = getIntent().getStringExtra("id");
        titleName.setText("周检修详情");
        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");


        mWeekTaskAdapter = new WeekTaskAdapter(this);
        workTaskLv.setAdapter(mWeekTaskAdapter);

//        workTaskLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                AddWeekTaskDialog.update(OverhaulWeekDetailActivity.this, mWeekTaskAdapter, mWeekTaskList, position);
//
//            }
//        });
        initId();
        getFile();
    }

    private void initId() {
        ProgressDialog.show(this, false, "正在加载");
        BaseRequest.getInstance().getService().getOverhaulZzWeekPlan(planId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<OverhaulYearBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<OverhaulYearBean> t) throws Exception {
                        OverhaulYearBean overhaulMonthBeanList = t.getResults();
                        if (overhaulMonthBeanList != null) {
                            results = overhaulMonthBeanList;
                        }

                        initView();
                        initFileList();
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    private void getFile() {
        BaseRequest.getInstance().getService().getOverFile(planId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulFileBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulFileBean>> t) throws Exception {
                        List<OverhaulFileBean> sysFiles = t.getResults();

                        //判断是否有上传保电方案
                        if (sysFiles != null) {
                            for (int i = 0; i < sysFiles.size(); i++) {
                                OverhaulFileBean sysFilesBean = sysFiles.get(i);
                                String file_path = sysFilesBean.getFile_path();
                                //判断是否是保电方案
                                if ("1".equals(sysFilesBean.getRepair_type())) {
                                    powerPreservationRl.setVisibility(View.VISIBLE);
                                    powername = sysFilesBean.getFilename();
                                    powerURL = file_path + sysFilesBean.getFilename();
                                    if (powername.endsWith("xls") || powername.endsWith("xlsx")) {
                                        powerPreservationIv.setImageResource(R.mipmap.excel);

                                    } else if (powername.endsWith("doc") || powername.endsWith("docx")) {
                                        powerPreservationIv.setImageResource(R.mipmap.word);
                                    }
                                    //判断是否是验收方案
                                } else if ("2".equals(sysFilesBean.getRepair_type())) {
                                    acceptancePlanRl.setVisibility(View.VISIBLE);
                                    acceptname = sysFilesBean.getFilename();
                                    acceptURL = file_path + acceptname;
                                    if (acceptname.endsWith("xls") || acceptname.endsWith("xlsx")) {
                                        acceptancePlanIv.setImageResource(R.mipmap.excel);

                                    } else if (acceptname.endsWith("doc") || acceptname.endsWith("docx")) {
                                        acceptancePlanIv.setImageResource(R.mipmap.word);
                                    }

                                }
                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }


    private void initView() {
        String status = results.getWeek_audit_status();
        String is_ele = results.getIs_ele();
        titleSettingTv.setText("派发");
        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {   //检修专责
            seekbarLl.setVisibility(View.VISIBLE);
            if ("1".equals(status)) {   //待专责分发
                titleSetting.setVisibility(View.VISIBLE);
                taskLl.setVisibility(View.VISIBLE);
            } else {                   //已分发
                titleSetting.setVisibility(View.GONE);
                taskLl.setVisibility(View.GONE);
            }
        } else {
            if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)) {
                needUploadLl.setVisibility(View.VISIBLE);
            }


            //安全,验收,保电专责
            titleSetting.setVisibility(View.GONE);  //不可分发
            taskLl.setVisibility(View.GONE);        //不添加任务
        }

        if ((jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED) || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED))) {
            llUploadFile.setVisibility(View.VISIBLE);
        }
//        保电专责进来判断是否需要上传保电方案
        if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)) {
            needUploadLl.setVisibility(View.VISIBLE);
            titleSettingTv.setText("保存");
            titleSetting.setVisibility(View.VISIBLE);
            if ("1".equals(is_ele)) {
                needUploadYes.setChecked(true);
            } else {
                needUploadNo.setChecked(true);
                llUploadFile.setVisibility(View.GONE);
            }
        }

        weekPlanDeviceName.setText(results.getLine_name());
        weekPlanTime.setText(results.getBlackout_days() + "天");
        workOfTaskTv.setText(results.getTask_source());
        riskLevelTv.setText(results.getRisk_level());
        dianRiskLevelTv.setText("2");       //回去在做，电网风险等级   TODO
        weekPlanContent.setText(results.getRepair_content());
        weekPlanRemark.setText(results.getRemark());
        planProgressbarTv.setText("周计划进度(" + results.getDone_num() + "/" + results.getAll_num() + "):");
        planProgressbarProbar.setProgress(Integer.valueOf(results.getDone_rate()));
        planProgressbarNum.setText(results.getDone_rate() + "%");
        String repairContentStr = results.getRepair_content();
        if (repairContentStr.contains("；")) {
            String taskStrs[] = repairContentStr.split("；");
            for (int i = 0; i < taskStrs.length; i++) {
                WeekTaskInfo weekTaskInfo = new WeekTaskInfo();
                weekTaskInfo.setWeekContent(taskStrs[i]);
                weekTaskInfo.setNum(i);
                taskList.add(weekTaskInfo);
            }
        } else {
            WeekTaskInfo weekTaskInfo = new WeekTaskInfo();
            weekTaskInfo.setWeekContent(repairContentStr);
            weekTaskInfo.setNum(0);
            taskList.add(weekTaskInfo);
        }
        mWeekTaskAdapter.setData(taskList);


        needUploadRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.need_upload_yes:
                        llUploadFile.setVisibility(View.VISIBLE);
                        voltage_statu = "1";
                        break;
                    case R.id.need_upload_no:
                        llUploadFile.setVisibility(View.GONE);
                        voltage_statu = "0";
                        break;
                }
            }
        });

        //getFzrInfo(planId, "2");

        //getAllSendToPerson();
        //getAllSendToPerson2();
    }


    public void initFileList() {
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

    }

//    private void controlCard() {
//        BaseRequest.getInstance().getService()
//                .controlCardType("kzk")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<ControlCardBean>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<ControlCardBean>> t) throws Exception {
//                        Log.w("linmeng", "t.toString():" + t.toString());
//                        if (t.getCode() == 1) {
//                            List<ControlCardBean> results = t.getResults();
//                            for (int i = 0; i < results.size(); i++) {
//                                nameType.add(results.get(i).getName());
//                            }
//                            nsControlCard.attachDataSource(nameType);
//                            controlCardId = results.get(0);
//                            nsControlCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    controlCardId = results.get(position);
//                                    Log.w("linmeng", "controlCardId:" + controlCardId.getId());
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });
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
//
//    private void getControlCardContent() {
//        BaseRequest.getInstance().getService()
//                .getControlCardContent(results.getId())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<AllControlCarBean>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<AllControlCarBean> t) throws Exception {
//
//                        if (t.getCode() == 1) {
//                            allControlCarBean = t.getResults();
//                            nsControlCard.setVisibility(View.GONE);
//                        }
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//                    }
//                });
//    }

 /*   private void getAllSendToPerson() {
        BaseRequest.getInstance().getService()
                .getSendOverhaulUsers2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulSendUserBean2>>(this) {
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
    }*/

/*    private void getAllSendToPerson2() {
        BaseRequest.getInstance().getService()
                .getAllClassMember("B7FF21A674F144DE8D13EB8B3B79E64F")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ClassMemberBean>>(this) {
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
    }*/

    public void openWord(String fileurl) {
        FileUtil fileUtil = new FileUtil();
        if (fileurl.endsWith("doc") || fileurl.endsWith("docx")) {
            Intent intent = fileUtil.getWordFileIntent(this, this.getPackageName(), fileurl);
            startActivity(intent);
        } else if (fileurl.endsWith("xls") || fileurl.endsWith("xlsx")) {
            Intent intent = fileUtil.getExcelFileIntent(this, this.getPackageName(), fileurl);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) { // 从发布界面返回回来,隐藏派发按钮
            titleSetting.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.btn_file, R.id.acceptance_plan_rl, R.id.power_preservation_rl, R.id.add_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.acceptance_plan_rl:
                String defaultSaveRootPath1 = FileDownloadUtils.getDefaultSaveRootPath();
                String fileurl = defaultSaveRootPath1.substring(0, defaultSaveRootPath1.length() - 5) + acceptname;
                File file1 = new File(fileurl);
                //判断文件是否存在，如果存在就打开，不存在就下载
                if (file1.exists()) {
                    openWord(fileurl);
                } else {
                    String url = BaseUrl.BASE_URL + acceptURL;
                    downloadFile(url, fileurl, 2);
                }
                break;
            case R.id.power_preservation_rl:
                String defaultSaveRootPath = FileDownloadUtils.getDefaultSaveRootPath();
                String path = defaultSaveRootPath.substring(0, defaultSaveRootPath.length() - 5) + powername;
                File file = new File(path);
                //判断文件是否存在，如果存在就打开，不存在就下载
                if (file.exists()) {
                    openWord(path);
                } else {
                    String url = BaseUrl.BASE_URL + powerURL;
                    downloadFile(url, path, 1);
                }
                break;
            case R.id.btn_file:
                //上传文件
                uploadWord();
                break;
            case R.id.title_setting:
                if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {   //专责发布周计划生成任务
                    Intent intent = new Intent();
                    intent.setClass(this, OverhaulPublishActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bean", results);

                    String[] weekTaskStrs = new String[taskList.size()];
                    for (int i = 0; i < weekTaskStrs.length; i++) {
                        weekTaskStrs[i] = taskList.get(i).getWeekContent();
                    }
                    intent.putExtra("weekTaskContents", weekTaskStrs);

                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1001);
                } else if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)) {  //保电专责保存数据
                    savaVoStatus();
                }
                break;


//            case R.id.work_ticket_tv:
//                if (results != null) {
//                    if (results.getTicket_type().equals("0")) {
//                        Toast.makeText(OverhaulWeekDetailActivity.this, "当前无工作票！", Toast.LENGTH_SHORT).show();
//                    } else {
//                        nicePosition = Integer.valueOf(results.getTicket_type());
//                        nicePosition2 = Integer.valueOf(results.getTicket_task_type());
//                        Log.d("task__type2", "type:" + nicePosition + "------task_type:" + nicePosition2);
//                        switch (nicePosition) {
//                            case 1:
//                                Intent intent11 = new Intent(OverhaulWeekDetailActivity.this, FirstWTicketActivity.class);
//                                intent11.putExtra("bean", results);
//                                intent11.putExtra("type", String.valueOf(nicePosition));
//                                intent11.putExtra("task_type", String.valueOf(nicePosition2));
//                                intent11.putExtra("leaderName", leaderName);
//                                startActivity(intent11);
//                                break;
//                            case 2:
//                                Intent intent12 = new Intent(OverhaulWeekDetailActivity.this, SecondWTicketActivity.class);
//                                intent12.putExtra("bean", results);
//                                intent12.putExtra("type", String.valueOf(nicePosition));
//                                intent12.putExtra("task_type", String.valueOf(nicePosition2));
//                                intent12.putExtra("leaderName", leaderName);
//                                startActivity(intent12);
//                                break;
//                            case 3:
//                                Intent intent13 = new Intent(OverhaulWeekDetailActivity.this, ThirdWTicketActivity.class);
//                                intent13.putExtra("bean", results);
//                                intent13.putExtra("type", String.valueOf(nicePosition));
//                                intent13.putExtra("task_type", String.valueOf(nicePosition2));
//                                intent13.putExtra("leaderName", leaderName);
//                                startActivity(intent13);
//                                break;
//                            case 4:
//                                Intent intent14 = new Intent(OverhaulWeekDetailActivity.this, FourWTicketActivity.class);
//                                intent14.putExtra("bean", results);
//                                intent14.putExtra("type", String.valueOf(nicePosition));
//                                intent14.putExtra("task_type", String.valueOf(nicePosition2));
//                                intent14.putExtra("leaderName", leaderName);
//                                startActivity(intent14);
//                                break;
//                        }
//                    }
//                }
//                break;
//            case R.id.control_card:
//                int entenType;
//                if (allControlCarBean == null) {
//                    Toast.makeText(OverhaulWeekDetailActivity.this, "当前无控制卡！", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (allControlCarBean.getWorkControlCard() == null && allControlCarBean.getWorkQualityCard() == null && allControlCarBean.getWorkTools().size() == 0) {
//                        Toast.makeText(OverhaulWeekDetailActivity.this, "当前无控制卡！", Toast.LENGTH_SHORT).show();
//                    } else {
//                        entenType = Constant.IS_OTHER_LOOK;
//                        Intent intent2 = new Intent(OverhaulWeekDetailActivity.this, ControlCardActivity.class);
//                        intent2.putExtra(Constant.CONTROL_CARD_ENTER_TYPE, entenType); //其他人员查看模式
//                        intent2.putExtra("bean", results);
//                        intent2.putExtra("allControlBean", allControlCarBean);
//                        intent2.putExtra("id", controlCardId);
//                        intent2.putExtra("leaderName", leaderName);
//                        intent2.putExtra("leaderId", leaderId);
//                        startActivityForResult(intent2, 1001);
//                    }
//                }
//                break;

            case R.id.add_btn:
                //AddWeekTaskDialog.show(this, mWeekTaskAdapter, mWeekTaskList);
                break;
        }
    }

//    private String leaderName;
//    private String leaderId;
//
//    private void getFzrInfo(String planId, String status) {
//        BaseRequest.getInstance().getService()
//                .getFzrInfoByWeekId(planId, status)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<OverhaulMonthBean>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<OverhaulMonthBean>> t) throws Exception {
//                        if (t.getCode() == 1) {
//                            if (t.getResults() != null && t.getResults().size() > 0) {
//
//                                //leaderName = t.getResults().get(0).getUser_name();
//                               // leaderId = t.getResults().get(0).getUser_id();
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


    private void uploadWord() {
        ProgressDialog.show(this, false, "正在上传....");
        Map<String, RequestBody> params = new HashMap<>();
        params.put("data_id", toRequestBody(results.getId()));
        if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)) {
            params.put("repair_type", toRequestBody("1"));  //1保电2验收
        } else if (jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
            params.put("repair_type", toRequestBody("2"));  //1保电2验收
        }
        File file = new File("/storage/emulated/0/" + fileList.get(filePosition));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        params.put("file\"; filename=\"" + file.getName(), requestFile);
        BaseRequest.getInstance().getService()
                .uploadWords(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            Toast.makeText(OverhaulWeekDetailActivity.this, "文件上传成功", Toast.LENGTH_SHORT).show();
                            getFile();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
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

    private void downloadFile(String path, String saveLocalPath, int type) {

        if (type == 1) {
            powerPreservationGrogress.setVisibility(View.VISIBLE);
        } else {
            acceptancePlanGrogress.setVisibility(View.VISIBLE);
        }
        FileDownloader.getImpl().create(path)
                .setPath(saveLocalPath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        int percent = (int) ((double) soFarBytes / (double) totalBytes * 100);
                        if (type == 1) {
                            powerPreservationGrogress.setText(percent + "%");
                        } else {
                            acceptancePlanGrogress.setText(percent + "%");
                        }
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        if (type == 1) {
                            powerPreservationGrogress.setVisibility(View.GONE);
                        } else {
                            acceptancePlanGrogress.setVisibility(View.GONE);
                        }
                        Toast.makeText(OverhaulWeekDetailActivity.this, "文件下载完成", Toast.LENGTH_SHORT).show();
                        openWord(saveLocalPath);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        if (type == 1) {
                            powerPreservationGrogress.setVisibility(View.GONE);
                        } else {
                            acceptancePlanGrogress.setVisibility(View.GONE);
                        }
                        Toast.makeText(OverhaulWeekDetailActivity.this, "文件下载失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

    private void savaVoStatus() {
        //传递参数：overhaulSendUserBeans， 状态：待分发（专责制定后），已分发（专责分派下去给班长后），已分派（班长分派给班员后）  发送出去    TODO
        OverhaulZZSendBean overhaulZZSendBean = new OverhaulZZSendBean();
        overhaulZZSendBean.setId(results.getId());
        overhaulZZSendBean.setIs_ele(voltage_statu);

        BaseRequest.getInstance().getService()
                .updataOverhaulMonitorPlan(overhaulZZSendBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulSendUserBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulSendUserBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            finish();
                        } else {
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
}
