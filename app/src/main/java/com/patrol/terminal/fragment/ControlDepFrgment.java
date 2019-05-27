package com.patrol.terminal.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.patrol.terminal.activity.SignActivity;
import com.patrol.terminal.adapter.ControlDepdapter1;
import com.patrol.terminal.adapter.ControlDepdapter2;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.AllControlCarBean;
import com.patrol.terminal.bean.ClassMemberBean;
import com.patrol.terminal.bean.ControlCardBean;
import com.patrol.terminal.bean.ControlDepWorkBean;
import com.patrol.terminal.bean.ControlDepWorkBean2;
import com.patrol.terminal.bean.ControlDepWorkInfo;
import com.patrol.terminal.bean.ControlDepWorkInfo2;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.OverhaulZzTaskBean;
import com.patrol.terminal.bean.SelectWorkerBean;
import com.patrol.terminal.bean.SignBean;
import com.patrol.terminal.bean.WorkControlCardBean;
import com.patrol.terminal.bean.WorkProjectUser;
import com.patrol.terminal.utils.Constant;
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

    private List<ControlDepWorkInfo> mControlDepShowList1 = new ArrayList<>();
    private List<ControlDepWorkInfo2> mControlDepShowList2 = new ArrayList<>();
    Map<String, RequestBody> params = new HashMap<>();

    private Context mContext;
    private List<ControlDepWorkBean> controlDepWorkBeans = new ArrayList<>();
    private List<File> mPicList = new ArrayList<>();
    private List<ControlDepWorkBean2> controlDepWorkBeans2;
    private Activity mActivity;
    private int enterType;
    private boolean isFzrUpdate = false;
    //private boolean isLook;                 //只有负责人为填写修改状态，其他人为查看状态。
    //private boolean isFzrUpdate = false;  //负责人是填写状态，还是修改状态，默认为填写状态。
    //private AllControlCarBean allControlCarBean = null;
    private String leaderName = "";  //负责人
    private String leaderId = "";    //负责人ID
    private String taskId = "";     //任务ID
    private int year;
    private int month;

    private boolean isCanClick = true;  //默认能点击，填写和更新状态
    private AllControlCarBean.WorkControlCardBean workControlCardBean = null;

    private SelectWorkerBean workerSelectList;



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
        String userId = SPUtil.getString(getContext(), Constant.USER, Constant.USERID, "");
        String userName = SPUtil.getString(getContext(), Constant.USER, Constant.USERNAME, "");

        if (jobType.contains(Constant.REFURBISHMENT_MEMBER)||jobType.contains(Constant.REFURBISHMENT_TEMA_LEADER)) {
            leaderName = userName;
            leaderId = userId;
        }

        enterType = mActivity.getIntent().getIntExtra(Constant.CONTROL_CARD_ENTER_TYPE, Constant.IS_OTHER_LOOK);  //是否为查看模式

//        leaderName = mActivity.getIntent().getStringExtra("leaderName");
//        leaderId = mActivity.getIntent().getStringExtra("leaderId");

        workerSelectList =  mActivity.getIntent().getParcelableExtra("selectedUserListBeans");

        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {  //专责接受的Bean不一样
            OverhaulZzTaskBean bean = mActivity.getIntent().getParcelableExtra("bean");

            if (bean != null) {
                taskId = bean.getId();
                //String status = bean.getTask_status();
                year = bean.getYear();
                month = bean.getMonth();
                tvControlName.setText(bean.getTask_content());
                controlCardType.setText("暂无");
                controlCardDep.setText("带电作业班");

                //getFzrInfo(bean.getRepair_id(), "2");
                controlCardPersonal.setText(leaderName);
                controlCardTicket.setText("暂无");
                controlCardStartTime.setText(bean.getStart_time());
                controlCardEndTime.setText(bean.getEnd_time());
            }

        }else {
            OverhaulMonthBean bean = mActivity.getIntent().getParcelableExtra("bean");

            if (bean != null) {
                taskId = bean.getId();
                year = bean.getYear();
                month = bean.getMonth();
                //String status = bean.getStatus();
                tvControlName.setText(bean.getTask_content());
                controlCardType.setText("暂无");
                controlCardDep.setText("带电作业班");

                //getFzrInfo(bean.getRepair_id(), "2");

                controlCardPersonal.setText(leaderName);
                controlCardTicket.setText("暂无");
                controlCardStartTime.setText(bean.getStart_time());
                controlCardEndTime.setText(bean.getEnd_time());
            }
        }


        ControlCardBean controlBean = (ControlCardBean) mActivity.getIntent().getSerializableExtra("id");
        getWorkControlCard();

        switch (enterType) {
            case Constant.IS_OTHER_LOOK:    //其他人员查看
                isCanClick = false;
                controlCardSubmit.setVisibility(View.GONE);
                if (workControlCardBean != null) {
                    lookDivide(workControlCardBean.getWorkProjectUsers());
                    lookSafe(workControlCardBean.getWorkSafeUsers());

                    AllControlCarBean.WorkControlCardBean.SysFile sysFile = workControlCardBean.getSysFile();
                    if (sysFile != null) {
                        String filePath = sysFile.getFile_path();
                        filePath = filePath.substring(1, filePath.length());
                        String fileName = sysFile.getFilename();
                        String url = filePath + fileName;
                        if (url != null) {
                            showSign(url);
                        }
                    }
                }else {
                    if (controlBean != null) {
                        divide(controlBean.getId());
                        safe(controlBean.getId());
                    }
                }
                break;

            case Constant.IS_FZR_WRITE:    //负责人填写
                isCanClick = true;
                controlCardSubmit.setVisibility(View.VISIBLE);

                if (controlBean != null) {
                    divide(controlBean.getId());
                    safe(controlBean.getId());
                }
                break;

            case Constant.IS_FZR_UPDATE:    //负责人更新  OR  填写
                isCanClick = true;
                controlCardSubmit.setVisibility(View.VISIBLE);
                if (workControlCardBean != null) {
                    isFzrUpdate = true;
                    lookDivide(workControlCardBean.getWorkProjectUsers());
                    lookSafe(workControlCardBean.getWorkSafeUsers());

                    AllControlCarBean.WorkControlCardBean.SysFile sysFile = workControlCardBean.getSysFile();
                    if (sysFile != null) {
                        String filePath = sysFile.getFile_path();
                        filePath = filePath.substring(1, filePath.length());
                        String fileName = sysFile.getFilename();
                        String url = filePath + fileName;
                        if (url != null) {
                            showSign(url);
                        }
                    }
                }else {
                    isFzrUpdate = false;
                    if (controlBean != null) {
                        divide(controlBean.getId());
                        safe(controlBean.getId());
                    }
                }
                break;
        }



    }

    private void showSign(String url) {
        //显示签名
        Log.w("linmeng", "url:" + BaseUrl.BASE_URL + url);
        if (url!=null) {
            Glide.with(this).load(BaseUrl.BASE_URL + url).into(ivSignaturePad);
        }
    }

    private void getWorkControlCard() {
        AllControlCarBean allControlCarBean = mActivity.getIntent().getParcelableExtra("allControlBean");
        if (allControlCarBean != null) {
            workControlCardBean = allControlCarBean.getWorkControlCard();
            if (workControlCardBean != null) {
                leaderName = workControlCardBean.getDuty_user_name();
                leaderId = workControlCardBean.getDuty_user_id();
                controlCardPersonal.setText(leaderName);
            }
        }
    }


    private void lookDivide(List<AllControlCarBean.WorkControlCardBean.WorkProjectUsersBean> workProjectUsersBeans) {
        mControlDepShowList1.clear();

        for (int i = 0; i < workProjectUsersBeans.size(); i++) {
            ControlDepWorkInfo info = new ControlDepWorkInfo();
            info.setDivisonNo(i + 1);
            info.setContent(workProjectUsersBeans.get(i).getWork_project_name());
//            if (i == 0) {
//                info.setDivisonName(leaderName);   //1是定死的负责人
//            } else {
            info.setDivisonName(workProjectUsersBeans.get(i).getUser_name());
//            }
            info.setUserId(workProjectUsersBeans.get(i).getUser_id());
            info.setW_p_id(workProjectUsersBeans.get(i).getWork_project_id());
            mControlDepShowList1.add(info);
        }

        ControlDepdapter1 depdapter1;
        if (workerSelectList != null) {
             depdapter1 = new ControlDepdapter1(getContext(), mControlDepShowList1, isCanClick, workerSelectList.getUserInfos());   //更新模式
        }else {
             depdapter1 = new ControlDepdapter1(getContext(), mControlDepShowList1, isCanClick, null);   //查看模式
        }

        controlCardDiv.setAdapter(depdapter1);
    }

    private void lookSafe(List<AllControlCarBean.WorkControlCardBean.WorkSafeRelationsBean> workSafeRelationsBean) {
        mControlDepShowList2.clear();

        for (int i = 0; i < workSafeRelationsBean.size(); i++) {
            ControlDepWorkInfo2 info = new ControlDepWorkInfo2();
            info.setDivisonNo(i + 1);
            info.setSaveContent(workSafeRelationsBean.get(i).getDanger());
            info.setContent(workSafeRelationsBean.get(i).getContent());
            info.setDivisonName(workSafeRelationsBean.get(i).getRespon_name());
            info.setUserId(workSafeRelationsBean.get(i).getRespon_id());
            info.setW_s_id(workSafeRelationsBean.get(i).getWork_safe_id());

            mControlDepShowList2.add(info);
        }

        ControlDepdapter2 depdapter2 = new ControlDepdapter2(getContext(), mControlDepShowList2);
        controlCardGuide.setAdapter(depdapter2);
    }


    //危险点分析及安全措施指南
    private void safe(String id) {
        BaseRequest.getInstance().getService()
                .getControlDepWork2(id, Constant.SORT_ASC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ControlDepWorkBean2>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<ControlDepWorkBean2>> t) throws Exception {
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
                .subscribe(new BaseObserver<List<ControlDepWorkBean>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<ControlDepWorkBean>> t) throws Exception {
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

    private void updateInfo1(List<ControlDepWorkBean> controlDepWorkBeans) {
        mControlDepShowList1.clear();

        for (int i = 0; i < controlDepWorkBeans.size(); i++) {
            ControlDepWorkInfo info = new ControlDepWorkInfo();
            info.setDivisonNo(i + 1);
            info.setContent(controlDepWorkBeans.get(i).getName());
            if (i == 0) {
                info.setDivisonName(leaderName);   //1是定死的负责人
                info.setUserId(leaderId);
            } else {
                info.setDivisonName("");
                info.setUserId("");
            }
            info.setW_p_id(controlDepWorkBeans.get(i).getWork_project_id());
            mControlDepShowList1.add(info);
        }

        ControlDepdapter1 depdapter1 = new ControlDepdapter1(getContext(), mControlDepShowList1, isCanClick, workerSelectList.getUserInfos());
        controlCardDiv.setAdapter(depdapter1);
    }

    private void updateInfo2(List<ControlDepWorkBean2> controlDepWorkBeans2) {
        mControlDepShowList2.clear();

        for (int i = 0; i < controlDepWorkBeans2.size(); i++) {
            ControlDepWorkInfo2 info = new ControlDepWorkInfo2();
            info.setDivisonNo(i + 1);
            info.setSaveContent(controlDepWorkBeans2.get(i).getDanger());
            info.setContent(controlDepWorkBeans2.get(i).getContent());
            info.setDivisonName(leaderName);   //这里默认为负责人，，也可修改为，取出来的数据
            info.setUserId(leaderId);
            info.setW_s_id(controlDepWorkBeans2.get(i).getWork_safe_id());

            mControlDepShowList2.add(info);
        }

        ControlDepdapter2 depdapter2 = new ControlDepdapter2(getContext(), mControlDepShowList2);
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
                WorkControlCardBean bean = setValue();
                putValue(bean);
                upLoadControlCard();
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

    private WorkControlCardBean setValue() {
        WorkControlCardBean bean = new WorkControlCardBean();
        bean.setTask_id(taskId);
        bean.setYear(year + "");
        bean.setMonth(month + "");

        bean.setDuty_user_id(leaderId);
        bean.setDuty_user_name(leaderName);

        List<WorkProjectUser> workProjectUsers = new ArrayList<>();
        List<WorkControlCardBean.WorkSafeRelation> workSafeRelation = new ArrayList<>();

//        getWorkControlCard();   //进来没出去，连续点击两次提交的时候需要重新查询一次更改为更新状态
//        if (workControlCardBean != null) {
//            isFzrUpdate = true;
//        }

        //更新修改一样
        for (int i = 0; i < mControlDepShowList1.size(); i++) {
            workProjectUsers.add(new WorkProjectUser(mControlDepShowList1.get(i).getUserId(), mControlDepShowList1.get(i).getW_p_id()/*controlDepWorkBeans.get(i).getId()*/));
        }
        bean.setWorkProjectUsers(workProjectUsers);

        for (int i = 0; i < mControlDepShowList2.size(); i++) {
            workSafeRelation.add(new WorkControlCardBean.WorkSafeRelation(mControlDepShowList2.get(i).getW_s_id(), mControlDepShowList2.get(i).getUserId()/*负责人*/));
        }
        bean.setWorkSafeRelations(workSafeRelation);


        /*if(enterType == Constant.IS_FZR_UPDATE && isFzrUpdate) { //更新
//            if (workControlCardBean.getId() != null) {
//                String controlId = workControlCardBean.getId();
//                bean.setId(controlId);
//            }

            //if (workControlCardBean.getWorkProjectUsers() != null) {
               // List<AllControlCarBean.WorkControlCardBean.WorkProjectUsersBean> workProjectUsersBeans = workControlCardBean.getWorkProjectUsers();
                for (int i = 0; i < mControlDepShowList1.size(); i++) {
                    workProjectUsers.add(new WorkProjectUser(mControlDepShowList1.get(i).getUserId(), mControlDepShowList1.get(i).getW_p_id()*//*workProjectUsersBeans.get(i).getW_p_id()*//*));
                }
                bean.setWorkProjectUsers(workProjectUsers);
            //}

            //if (workControlCardBean.getWorkSafeRelations() != null) {
                //List<AllControlCarBean.WorkControlCardBean.WorkSafeRelationsBean> workSafeRelationsBeans = workControlCardBean.getWorkSafeRelations();
                for (int i = 0; i < mControlDepShowList2.size(); i++) {
                    workSafeRelation.add(new WorkControlCardBean.WorkSafeRelation(mControlDepShowList2.get(i).getW_s_id()*//*workSafeRelationsBeans.get(i).getId()*//*, mControlDepShowList2.get(i).getUserId()*//*负责人*//*));
                }
                bean.setWorkSafeRelations(workSafeRelation);
            //}
        }else {

        }*/

        return bean;
    }

    private void upLoadControlCard() {
        BaseRequest.getInstance().getService().upLoadControlCard(params).subscribeOn(Schedulers.io())
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

//    private void setControlSignPicture(String taskId) {
//        //http://localhost:9096/work/control/download?task_id=8EEC95D8FA0A4FA08CF6EF35E72FC1F3
//
//        String url = BaseUrl.BASE_URL + "work/control/download" + "?task_id=" + taskId;
//        if (url!=null){
//        Glide.with(this).load(url).into(ivSignaturePad);}
//    }

    private void putValue(WorkControlCardBean bean) {
//        if (bean.getId() != null) {
//            params.put("id", toRequestBody(bean.getId()));
//        }
        params.put("duty_user_id", toRequestBody(bean.getDuty_user_id()));
        params.put("duty_user_name", toRequestBody(bean.getDuty_user_name()));
        params.put("check_task_id", toRequestBody(bean.getTask_id()));
        params.put("year", toRequestBody(bean.getYear()));
        params.put("month", toRequestBody(bean.getMonth()));

        params.put("start_time", toRequestBody("2019年12月25日"));   //TODO
        params.put("end_time", toRequestBody("2019年12月25日"));

        for (int i = 0; i < bean.getWorkProjectUsers().size(); i++) {
            params.put("workProjectUsers[" + i + "].user_id", toRequestBody(bean.getWorkProjectUsers().get(i).getU_id()));
//            if (enterType == Constant.IS_FZR_UPDATE && isFzrUpdate) {  //更新
//                params.put("workProjectUsers[" + i + "].id", toRequestBody(bean.getWorkProjectUsers().get(i).getW_p_id()));
//            } else {    //填写
                params.put("workProjectUsers[" + i + "].work_project_id", toRequestBody(bean.getWorkProjectUsers().get(i).getW_p_id()));
//            }

        }

        for (int i = 0; i < bean.getWorkSafeRelations().size(); i++) {
            params.put("workSafeUsers[" + i + "].work_safe_id", toRequestBody(bean.getWorkSafeRelations().get(i).getW_s_id()));
            params.put("workSafeUsers[" + i + "].respon_id", toRequestBody(bean.getWorkSafeRelations().get(i).getRespon()));
        }

        if (mPicList != null && mPicList.size() > 0) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mPicList.get(0));
            params.put("file\"; filename=\"" + mPicList.get(0).getName(), requestFile);
        }
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

}
