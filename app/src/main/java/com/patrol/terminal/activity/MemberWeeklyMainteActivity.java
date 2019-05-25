package com.patrol.terminal.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AddressBookLevel2;
import com.patrol.terminal.bean.ClassMemberBean;
import com.patrol.terminal.bean.ControlCardBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulSendUserBean;
import com.patrol.terminal.bean.OverhaulSendUserBean2;
import com.patrol.terminal.bean.OverhaulUserInfo;
import com.patrol.terminal.bean.OverhaulZZSendBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.SignDialog;

import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*工作人员上报周检修工作*/
public class MemberWeeklyMainteActivity extends BaseActivity {

//
//    @BindView(R.id.title_back)
//    RelativeLayout titleBack;
//    @BindView(R.id.title_name)
//    TextView titleName;
//    @BindView(R.id.title_setting_iv)
//    ImageView titleSettingIv;
//    @BindView(R.id.title_setting_tv)
//    TextView titleSettingTv;
//    @BindView(R.id.title_setting)
//    RelativeLayout titleSetting;
//    @BindView(R.id.week_maintenace_tv)
//    TextView weekMaintenaceTv;
//    @BindView(R.id.other_tv)
//    TextView otherTv;
//    @BindView(R.id.work_ticket_tv)
//    TextView workTicketTv;
//    @BindView(R.id.control_card)
//    TextView controlCard;
//    @BindView(R.id.weekly_maintenance_rl)
//    RelativeLayout weeklyMaintenanceRl;
//    @BindView(R.id.tv_accept)
//    NiceSpinner tvAccept;
//    @BindView(R.id.iv_signature_pad)
//    ImageView ivSignaturePad;
//    @BindView(R.id.tv_name1)
//    TextView tvName1;
//    @BindView(R.id.tv_sign)
//    NiceSpinner tvSign;
//    @BindView(R.id.tv_licence)
//    NiceSpinner tvLicence;
//    @BindView(R.id.tv_line_name)
//    TextView tvLineName;
//    @BindView(R.id.tv_start_time)
//    TextView tvStartTime;
//    @BindView(R.id.tv_content)
//    TextView tvContent;
//    @BindView(R.id.tv_source)
//    TextView tvSource;
//    @BindView(R.id.btn_commit)
//    Button btnCommit;
//    @BindView(R.id.ns_work_ticket)
//    NiceSpinner nsWorkTicket;
//    @BindView(R.id.btn_file)
//    Button btnFile;
//    @BindView(R.id.ns_file)
//    NiceSpinner nsFile;
//    @BindView(R.id.ll_upload_file)
//    LinearLayout llUploadFile;
//    @BindView(R.id.ns_control_card)
//    NiceSpinner nsControlCard;
//    private List<AddressBookLevel2> nameList1 = new ArrayList<>();
//    private List<AddressBookLevel2> nameList2 = new ArrayList<>();
//    //List<OverhaulZZSendBean.UserInfo> userList = new ArrayList<>();
//    private List<OverhaulUserInfo> userData = new ArrayList<>();
//    private List<String> data = new ArrayList<>();
//    private List<String> data2 = new ArrayList<>();
//    private List<String> data3 = new ArrayList<>();
//    private List<OverhaulSendUserBean2> overhaulSendUserBeans = new ArrayList<>();
//    private List<ClassMemberBean.UserListBean> userListBeans;
//    private OverhaulMonthBean bean;
//    private int nicePosition = 0;
//
//
//    private List<String> nameType = new ArrayList<>();
//    private ControlCardBean controlCardId;
//    private int signPosition = 0;
//    private int licencePosition = 0;
//    private int acceptPosition;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.weekly_maintenance_activity);
//        ButterKnife.bind(this);
//        initView();
//    }
//
//    private void initView() {
//        titleName.setText("周检修工作");
//        bean = getIntent().getParcelableExtra("bean");
////        if (bean.getPlanRepair() != null) {
////            OverhaulMonthBean.PlanRepairBean planRepairBean = bean.getPlanRepair();
////            tvLineName.setText("停电线路名称：" + planRepairBean.getLine_name());
////            tvStartTime.setText("停电计划时间：" + planRepairBean.getStart_time());
////            tvContent.setText("工作内容：" + planRepairBean.getRepair_content());
////            tvSource.setText("任务来源：" + planRepairBean.getTask_source());
////        }
//
//
//        data3.clear();
//        data3.add("第一种工作票");
//        data3.add("第二种工作票");
//        data3.add("电力线路带电作业工作票");
//        nsWorkTicket.attachDataSource(data3);
//        nsWorkTicket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                nicePosition = position;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        String depId = SPUtil.getDepId(this);
////        if (Constant.POWER_CONSERVATION_SPECIALIZED.equals(depId) || Constant.ACCEPTANCE_CHECK_SPECIALIZED.equals(depId)) {
//        llUploadFile.setVisibility(View.VISIBLE);
////        } else {
////            llUploadFile.setVisibility(View.GONE);
////        }
//
//
//        getAllSendToPerson();
//        getAllSendToPerson2();
//        controlCard();
//    }
//
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
//    private void getAllSendToPerson() {
//        BaseRequest.getInstance().getService()
//                .getSendOverhaulUsers2()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<OverhaulSendUserBean2>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<OverhaulSendUserBean2>> t) throws Exception {
//                        if (t.getCode() == 1) {
//                            for (int i = 0; i < t.getResults().size(); i++) {
//                                OverhaulSendUserBean2 bean = t.getResults().get(i);
//                                if (!bean.getUserList().isEmpty() && bean.getUserList().size() > 0 && bean.getName().contains("专责")) {
//                                    for (int i1 = 0; i1 < bean.getUserList().size(); i1++) {
//                                        overhaulSendUserBeans.add(bean);
//                                    }
//                                }
//                            }
//                            data.clear();
//                            for (int i = 0; i < overhaulSendUserBeans.size(); i++) {
//                                for (int i1 = 0; i1 < overhaulSendUserBeans.get(i).getUserList().size(); i1++) {
//                                    data.add(overhaulSendUserBeans.get(i).getUserList().get(i1).getName());
//                                    userData.add(new OverhaulUserInfo(overhaulSendUserBeans.get(i).getUserList().get(i1).getId()));
//                                }
//                            }
//                            tvSign.attachDataSource(data);
//                            tvSign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    signPosition = position;
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });
//                            tvLicence.attachDataSource(data);
//                            tvLicence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    licencePosition = position;
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });
//                        }
//
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                    }
//                });
//    }
//
//    private void getAllSendToPerson2() {
//        BaseRequest.getInstance().getService()
//                .getAllClassMember("B7FF21A674F144DE8D13EB8B3B79E64F")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<ClassMemberBean>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<ClassMemberBean>> t) throws Exception {
//                        if (t.getCode() == 1) {
//                            if (t.getResults() != null && t.getResults().size() > 0) {
//                                userListBeans = t.getResults().get(0).getUserList();
//                                data2.clear();
//                                for (int i = 0; i < userListBeans.size(); i++) {
//                                    data2.add(userListBeans.get(i).getName());
//                                }
//                                tvAccept.attachDataSource(data2);
//                                tvAccept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                        acceptPosition = position;
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> parent) {
//
//                                    }
//                                });
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                    }
//                });
//    }
//
//    private void sendToMember() {
////        //传递参数：overhaulSendUserBeans， 状态：待分发（专责制定后），已分发（专责分派下去给班长后），已分派（班长分派给班员后）  发送出去    TODO
////        OverhaulZZSendBean overhaulZZSendBean = new OverhaulZZSendBean();
////        overhaulZZSendBean.setId(bean.getId());
////        overhaulZZSendBean.setStatus("3");
////        userList.add(new OverhaulZZSendBean.UserInfo(userListBeans.get(acceptPosition).getId()));
////        userList.add(new OverhaulZZSendBean.UserInfo(userData.get(signPosition).getUser_id()));
////        userList.add(new OverhaulZZSendBean.UserInfo(userData.get(licencePosition).getUser_id()));
////        overhaulZZSendBean.setUserList(userList);
////
////        BaseRequest.getInstance().getService()
////                .sendOverhaulMonitorPlan(overhaulZZSendBean)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(new BaseObserver<List<OverhaulSendUserBean>>(this) {
////                    @Override
////                    protected void onSuccees(BaseResult<List<OverhaulSendUserBean>> t) throws Exception {
////                        if (t.getCode() == 1) {
////                            //result = t.getResults();
////                            finish();
////                        } else {
////                        }
////                    }
////
////                    @Override
////                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
////                    }
////                });
//    }
//
//
//
//
//
//    @OnClick({R.id.title_back, R.id.tv_name1, R.id.tv_accept, R.id.iv_signature_pad, R.id.work_ticket_tv, R.id.control_card, R.id.tv_sign, R.id.tv_licence, R.id.btn_commit, R.id.btn_file})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.title_back:
//                finish();
//                break;
//            case R.id.tv_name1:
//                Intent intent1 = new Intent(this, AddressBookActivity.class);
//                intent1.putExtra("nameList", (Serializable) nameList1);
//                startActivityForResult(intent1, Constant.REQUEST_CODE_ADDRESS_BOOK);
//                break;
//            case R.id.iv_signature_pad:
//                Dialog dialog = SignDialog.show(this, ivSignaturePad);
//                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        if (ivSignaturePad.getDrawable() != null) {
//                            File file = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad).getDrawable()).getBitmap(), "");
//                        }
//                    }
//                });
//                break;
//            case R.id.work_ticket_tv:
//                switch (nicePosition) {
//                    case 0:
//                        Intent intent11 = new Intent(MemberWeeklyMainteActivity.this, FirstWTicketActivity.class);
//                        intent11.putExtra("bean", bean);
//                        startActivity(intent11);
//                        break;
//                    case 1:
//                        Intent intent12 = new Intent(MemberWeeklyMainteActivity.this, SecondWTicketActivity.class);
//                        intent12.putExtra("bean", bean);
//                        startActivity(intent12);
//                        break;
//                    case 2:
//                        Intent intent13 = new Intent(MemberWeeklyMainteActivity.this, ThirdWTicketActivity.class);
//                        intent13.putExtra("bean", bean);
//                        startActivity(intent13);
//                        break;
//                }
//                break;
//            case R.id.control_card:
//                Intent intent4 = new Intent(this, ControlCardActivity.class);
//                intent4.putExtra("bean", bean);
//                intent4.putExtra("id", controlCardId);
//                startActivity(intent4);
//                break;
//            case R.id.btn_commit:
//                sendToMember();
//                break;
//            case R.id.btn_file:
//
//                break;
//        }
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == Constant.REQUEST_CODE_ADDRESS_BOOK) {
//                nameList1 = (List<AddressBookLevel2>) data.getSerializableExtra("nameList");
//                String nameArray = "";
//                for (int i = 0; i < nameList1.size(); i++) {
//                    if (nameList1.get(i).isTag()) {
//                        nameArray += nameList1.get(i).getContent() + ",";
//                    }
//                }
//                tvName1.setText(nameArray.substring(0, nameArray.length() - 1));
//            } else if (requestCode == Constant.REQUEST_CODE_ADDRESS_BOOK2) {
//                nameList2 = (List<AddressBookLevel2>) data.getSerializableExtra("nameList");
//                String nameArray = "";
//                for (int i = 0; i < nameList2.size(); i++) {
//                    if (nameList2.get(i).isTag()) {
//                        nameArray += nameList2.get(i).getContent() + ",";
//                    }
//                }
//                tvAccept.setText(nameArray.substring(0, nameArray.length() - 1));
//            } else if (requestCode == Constant.REQUEST_CODE_ADDRESS_BOOK3) {
//                nameList2 = (List<AddressBookLevel2>) data.getSerializableExtra("nameList");
//                String nameArray = "";
//                for (int i = 0; i < nameList2.size(); i++) {
//                    if (nameList2.get(i).isTag()) {
//                        nameArray += nameList2.get(i).getContent() + ",";
//                    }
//                }
//                tvSign.setText(nameArray.substring(0, nameArray.length() - 1));
//            } else if (requestCode == Constant.REQUEST_CODE_ADDRESS_BOOK4) {
//                nameList2 = (List<AddressBookLevel2>) data.getSerializableExtra("nameList");
//                String nameArray = "";
//                for (int i = 0; i < nameList2.size(); i++) {
//                    if (nameList2.get(i).isTag()) {
//                        nameArray += nameList2.get(i).getContent() + ",";
//                    }
//                }
//                tvLicence.setText(nameArray.substring(0, nameArray.length() - 1));
//            }
//
//        }
//    }
}
