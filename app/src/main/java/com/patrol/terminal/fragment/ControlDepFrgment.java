package com.patrol.terminal.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ControlDepdapter1;
import com.patrol.terminal.adapter.ControlDepdapter2;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.AllControlCarBean;
import com.patrol.terminal.bean.CardControlProject;
import com.patrol.terminal.bean.CardControlSafe;
import com.patrol.terminal.bean.CardControlSign;
import com.patrol.terminal.bean.ControlCardBean;
import com.patrol.terminal.bean.ControlDepWorkBean2;
import com.patrol.terminal.bean.ControlDepWorkInfo2;
import com.patrol.terminal.bean.DefectPlanDetailBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulZzTaskBean;
import com.patrol.terminal.bean.SelectWorkerBean;
import com.patrol.terminal.bean.CardControl;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.NoScrollListView;
import com.patrol.terminal.widget.SignDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ControlDepFrgment extends BaseFragment {
    @BindView(R.id.control_card_type)
    TextView controlCardType;
    @BindView(R.id.control_card_dep)
    TextView controlCardDep;
    @BindView(R.id.control_card_personal)
    TextView controlCardPersonal;
    @BindView(R.id.control_card_ticket)
    TextView controlCardTicket;
    @BindView(R.id.control_card_start_time)
    TextView controlCardStartTime;
    @BindView(R.id.control_card_end_time)
    TextView controlCardEndTime;
    @BindView(R.id.control_card_div)
    NoScrollListView controlCardDiv;
    @BindView(R.id.control_card_guide)
    NoScrollListView controlCardGuide;
    @BindView(R.id.iv_signature_pad)
    ImageView ivSignaturePad;
    @BindView(R.id.control_card_submit)
    TextView controlCardSubmit;
    @BindView(R.id.tv_control_name)
    TextView tvControlName;

    private List<CardControlProject> mControlDepShowList1 = new ArrayList<>();

    private Context mContext;
    private List<CardControlProject> controlDepWorkBeans = new ArrayList<>();
    private List<File> mPicList = new ArrayList<>();
    private List<CardControlSafe> controlDepWorkBeans2;
    private Activity mActivity;
    private int enterType;
    private boolean isFzrUpdate = false;
    //private boolean isLook;                 //只有负责人为填写修改状态，其他人为查看状态。
    //private boolean isFzrUpdate = false;  //负责人是填写状态，还是修改状态，默认为填写状态。
    //private AllControlCarBean allControlCarBean = null;
    private String leaderName = "";  //负责人
    private String leaderId = "";    //负责人ID
    private String taskId = "";     //任务ID
//    private int year;
//    private int month;

    private boolean isCanClick = true;  //默认能点击，填写和更新状态
    private AllControlCarBean workControlCardBean = null;

    private SelectWorkerBean workerSelectList;
    private String controlName;
    private DefectPlanDetailBean bean;
    private ControlDepdapter1 depdapter1;
    private CardControl cardControl;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_card, null);
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Bitmap bitmap = SignBean.getBitmap();
//        if (bitmap != null) {
//            ivSignaturePad.setImageBitmap(bitmap);
//        }
//
//        SignBean.setBitmap(null);
//    }


    @Override
    protected void initData() {
        mActivity = getActivity();
        mContext = getActivity();
        String jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");


        enterType = mActivity.getIntent().getIntExtra(Constant.CONTROL_CARD_ENTER_TYPE, Constant.IS_OTHER_LOOK);  //是否为查看模式
        workerSelectList = mActivity.getIntent().getParcelableExtra("selectedUserListBeans");
        workControlCardBean = (AllControlCarBean) mActivity.getIntent().getSerializableExtra("allControlBean");

        bean = (DefectPlanDetailBean) mActivity.getIntent().getSerializableExtra("bean");
        cardControl = workControlCardBean.getCardControl();
        if (cardControl == null) {
            initfirst();
        } else {
            initSecond();
        }

        ControlCardBean controlBean = (ControlCardBean) mActivity.getIntent().getSerializableExtra("id");

        switch (enterType) {
            case Constant.IS_OTHER_LOOK:    //其他人员查看
                isCanClick = false;
                controlCardSubmit.setVisibility(View.GONE);
                if (cardControl != null) {

                } else {
                    if (controlBean != null) {
                        divide(controlBean.getId());
                        safe(controlBean.getId());
                    }
                }
                break;

            case Constant.IS_FZR_WRITE:    //负责人填写
                isCanClick = true;
                controlCardSubmit.setVisibility(View.VISIBLE);

                break;

            case Constant.IS_FZR_UPDATE:    //负责人更新  OR  填写
                isCanClick = true;
                controlCardSubmit.setVisibility(View.VISIBLE);
                if (cardControl != null) {
                    isFzrUpdate = true;

                } else {
                    isFzrUpdate = false;
                }
                break;
        }


    }


    //第一次进来没数据
    public void initfirst() {
        if (bean != null) {
            List<DefectPlanDetailBean.TaskDefectUserListBean> taskDefectUserList = bean.getTaskDefectUserList();
            for (int i = 0; i < taskDefectUserList.size(); i++) {
                DefectPlanDetailBean.TaskDefectUserListBean taskDefectUserListBean = taskDefectUserList.get(i);
                if ("2".equals(taskDefectUserListBean.getSign())) {
                    leaderName = taskDefectUserListBean.getUser_name();
                    leaderId = taskDefectUserListBean.getId();
                }
            }
            taskId = bean.getId();
            //String status = bean.getTask_status();
//                year = bean.getYear();
//                month = bean.getMonth();
            controlName = bean.getLine_name() + bean.getTower_name() + bean.getDeal_notes();
            tvControlName.setText(controlName);
            controlCardType.setText("单班组作业");
            controlCardDep.setText(bean.getDeal_dep_name());

            //getFzrInfo(bean.getRepair_id(), "2");
            controlCardPersonal.setText(leaderName);
            controlCardTicket.setText("无");
            controlCardStartTime.setText(bean.getDeal_time());
            controlCardEndTime.setText(bean.getClose_time());
        }
    }

    //初始化有数据的时候
    public void initSecond() {
        leaderName = cardControl.getDuty_user_name();
        leaderId = cardControl.getDuty_user_id();
        tvControlName.setText(cardControl.getContent());
        controlCardType.setText(cardControl.getProperty());
        controlCardDep.setText(cardControl.getDep_name());

        //getFzrInfo(bean.getRepair_id(), "2");
        controlCardPersonal.setText(leaderName);
        controlCardTicket.setText("无");
        controlCardStartTime.setText(cardControl.getStart_time());
        controlCardEndTime.setText(cardControl.getEnd_time());
        List<CardControlSign> signList = cardControl.getSignList();
        if (signList != null) {
            for (int i = 0; i < signList.size(); i++) {
                CardControlSign sign = signList.get(i);
                String filePath = sign.getFile_path();
                filePath = filePath.substring(1, filePath.length());
                String fileName = sign.getFilename();
                String url = filePath + fileName;
                if (url != null) {
                    showSign(url);
                }
            }
        }
        lookDivide(cardControl.getProjectList());
        lookSafe(cardControl.getSafeList());
    }

    private void showSign(String url) {
        //显示签名
        Log.w("linmeng", "url:" + BaseUrl.BASE_URL + url);
        if (url != null) {
            Glide.with(this).load(BaseUrl.BASE_URL + url).into(ivSignaturePad);
        }
    }


    private void lookDivide(List<CardControlProject> workProjectUsersBeans) {
        mControlDepShowList1 = workProjectUsersBeans;

        if (workerSelectList != null) {
            depdapter1 = new ControlDepdapter1(getContext(), mControlDepShowList1, isCanClick, workerSelectList.getUserInfos());   //更新模式
        } else {
            depdapter1 = new ControlDepdapter1(getContext(), mControlDepShowList1, isCanClick, null);   //查看模式
        }

        controlCardDiv.setAdapter(depdapter1);
    }


    //危险点分析及安全措施指南
    private void safe(String id) {
        BaseRequest.getInstance().getService()
                .getControlDepWork2(id, Constant.SORT_ASC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<CardControlSafe>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<CardControlSafe>> t) throws Exception {
                        Log.w("linmeng", "t.toString():" + t.toString());
                        if (t.getCode() == 1) {
                            controlDepWorkBeans2 = t.getResults();
                            updateInfo2(controlDepWorkBeans2);

                        } else {
                            Toast.makeText(mContext, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //人员分工
    private void divide(String id) {
        BaseRequest.getInstance().getService()
                .getControlDepWork1(id, Constant.SORT_ASC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<CardControlProject>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<CardControlProject>> t) throws Exception {
                        Log.w("linmeng", "t.toString():" + t.toString());
                        if (t.getCode() == 1) {
                            controlDepWorkBeans = t.getResults();
                            updateInfo1(controlDepWorkBeans);

                        } else {
                            Toast.makeText(mContext, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void updateInfo1(List<CardControlProject> controlDepWorkBeans) {
        mControlDepShowList1.clear();

        for (int i = 0; i < controlDepWorkBeans.size(); i++) {
            CardControlProject cardControlProject = controlDepWorkBeans.get(i);
            cardControlProject.setDivisonNo(i + 1);
            String user_name = cardControlProject.getUser_name();
            String user_id = cardControlProject.getUser_id();
            if (i == 0) {
                cardControlProject.setUser_name(user_name == null ? leaderName : user_name);   //1是定死的负责人
                cardControlProject.setUser_id(user_id == null ? leaderId : user_id);
            } else {
                cardControlProject.setUser_name(user_name == null ? "" : user_name);   //1是定死的负责人
                cardControlProject.setUser_id(user_id == null ? "" : user_id);
            }
            cardControlProject.setCard_project_id(bean.getId());
            mControlDepShowList1.add(cardControlProject);
        }

        depdapter1 = new ControlDepdapter1(getContext(), mControlDepShowList1, isCanClick, workerSelectList.getUserInfos());
        controlCardDiv.setAdapter(depdapter1);
    }

    private void updateInfo2(List<CardControlSafe> cardControlSafes) {

        for (int i = 0; i < cardControlSafes.size(); i++) {
            CardControlSafe cardControlSafe = cardControlSafes.get(i);
            cardControlSafe.setDivisonNo(i + 1);
            String user_name = cardControlSafe.getDuty_user_name();
            String user_id = cardControlSafe.getDuty_user_id();
            cardControlSafe.setDuty_user_name(user_name == null ? leaderName : user_name);   //1是定死的负责人
            cardControlSafe.setDuty_user_id(user_id == null ? leaderId : user_id);
        }

        ControlDepdapter2 depdapter2 = new ControlDepdapter2(getContext(), cardControlSafes);
        controlCardGuide.setAdapter(depdapter2);
    }

    private void lookSafe(List<CardControlSafe> cardControlSafes) {


        ControlDepdapter2 depdapter2 = new ControlDepdapter2(getContext(), cardControlSafes);
        controlCardGuide.setAdapter(depdapter2);
    }

    @OnClick({R.id.control_card_start_time, R.id.control_card_end_time, R.id.control_card_submit, R.id.iv_signature_pad})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.control_card_start_time:
                //PickerUtils.showDate(mContext, controlCardStartTime);
                break;
            case R.id.control_card_end_time:
                //PickerUtils.showDate(mContext, controlCardEndTime);
                break;
            case R.id.control_card_submit:
                saveDepControl();
                break;
            case R.id.iv_signature_pad:
                if (isCanClick) {

//                    Intent intent = new Intent();
//                    intent.setClass(getActivity(), SignActivity.class);
//                    startActivity(intent);

                    Dialog dialog1 = SignDialog.show(getActivity(), ivSignaturePad);
                    dialog1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (ivSignaturePad.getDrawable() != null) {
                                File file1 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad).getDrawable()).getBitmap(), "controlDep");
                                mPicList.add(file1);
                            }
                        }
                    });
                }
                break;
        }
    }


    private void saveDepControl() {
        List<CardControlProject> data = depdapter1.getData();
        List<CardControlSign> signList = new ArrayList<>();
        for (int i = 0; i < mPicList.size(); i++) {
            CardControlSign signbean = new CardControlSign();
            File file = mPicList.get(i);
            String sign = FileUtil.fileToBase64(file);
            signbean.setSign("0");
            signbean.setFile(sign);
            signList.add(signbean);
        }
        CardControl cardControl = new CardControl();
        cardControl.setWriter_user_id(leaderId);
        cardControl.setWriter_user_name(leaderName);
        cardControl.setContent(controlName);
        cardControl.setProperty("单班组作业");
        cardControl.setDep_id(bean.getDeal_dep_id());
        cardControl.setDep_name(bean.getDeal_dep_name());
        cardControl.setDuty_user_id(leaderId);
        cardControl.setDuty_user_name(leaderName);
        cardControl.setStart_time(bean.getDeal_time());
        cardControl.setEnd_time(bean.getClose_time());
        cardControl.setProjectList(data);
        cardControl.setSafeList(controlDepWorkBeans2);
        cardControl.setSignList(signList);
        cardControl.setTask_repair_id(bean.getId());
        BaseRequest.getInstance().getService().saveDepControl(cardControl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        Toast.makeText(getActivity(), "上传成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}