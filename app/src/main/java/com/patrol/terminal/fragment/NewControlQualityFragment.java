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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ControlOperationAdapter;
import com.patrol.terminal.adapter.ControlQualityAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.AllControlCarBean;
import com.patrol.terminal.bean.ControlCardBean;
import com.patrol.terminal.bean.ControlOperationBean;
import com.patrol.terminal.bean.ControlQualityBean;
import com.patrol.terminal.bean.ControlQualityInfo;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulZzTaskBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.SignDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NewControlQualityFragment extends BaseFragment {


    @BindView(R.id.new_control_quality_rv)
    RecyclerView newControlQualityRv;
    Map<String, RequestBody> params = new HashMap<>();

    private Context mContext;

    private List<ControlQualityInfo> mControlQualityList = new ArrayList<>();
    private List<File> mPicList = new ArrayList<>();
    private List<ControlQualityBean> controlQualityBeans;
    private ControlOperationAdapter depdapter1;
    private Activity mActivity;
    private AllControlCarBean.WorkQualityCardBean workQualityCardBean = null;
    private boolean isCanClick = true;  //默认能点击，填写和更新状态
    private int enterType;
    private boolean isFzrUpdate = false;

    private String taskId = "";     //任务ID
    private String leaderName;
    private String leaderId;
    private View header;
    private View bottom;
    private TextView controlCardName;
    private TextView controlCardType;
    private TextView controlCardDep;
    private TextView controlCardPersonal;
    private TextView controlCardNo;
    private TextView controlCardStartTime;
    private TextView controlCardEndTime;
    private ImageView ivSignaturePad;
    private EditText etRemark;
    private TextView etRemarkTv;
    private TextView controlCardSubmit;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_control_quality, null);
        header = inflater.inflate(R.layout.fragment_new_control_quality_header, null);
        bottom = inflater.inflate(R.layout.fragment_new_control_quality_bottom, null);
        controlCardName = header.findViewById(R.id.control_card_name);
        controlCardType = header.findViewById(R.id.control_card_type);
        controlCardDep = header.findViewById(R.id.control_card_dep);
        controlCardPersonal = header.findViewById(R.id.control_card_personal);
        controlCardNo = header.findViewById(R.id.control_card_no);
        controlCardStartTime = header.findViewById(R.id.control_card_start_time);
        controlCardEndTime = header.findViewById(R.id.control_card_end_time);

        newControlQualityRv = bottom.findViewById(R.id.new_control_quality_rv);
        ivSignaturePad = bottom.findViewById(R.id.iv_signature_pad);
        ivSignaturePad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCanClick) {
                    Dialog dialog1 = SignDialog.show(getActivity(), ivSignaturePad);
                    dialog1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (ivSignaturePad.getDrawable() != null) {
                                File file1 = SignDialog.saveBitmapFile(((BitmapDrawable) (ivSignaturePad).getDrawable()).getBitmap(), "controlQuality");
                                mPicList.add(file1);
                            }
                        }
                    });
                }
            }
        });
        etRemark = bottom.findViewById(R.id.et_remark);
        etRemarkTv = bottom.findViewById(R.id.et_remark_tv);
        controlCardSubmit = bottom.findViewById(R.id.control_card_submit);
        controlCardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlOperationBean bean = setValue();
                putValue(bean);
                uploadControlQuality();
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        mContext = getActivity();
        mActivity = getActivity();

        String jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        String userId = SPUtil.getString(getContext(), Constant.USER, Constant.USERID, "");
        String userName = SPUtil.getString(getContext(), Constant.USER, Constant.USERNAME, "");

        if (jobType.contains(Constant.REFURBISHMENT_MEMBER) || jobType.contains(Constant.REFURBISHMENT_TEMA_LEADER)) {
            leaderName = userName;
            leaderId = userId;
        }

        enterType = mActivity.getIntent().getIntExtra(Constant.CONTROL_CARD_ENTER_TYPE, Constant.IS_OTHER_LOOK);  //是否为查看模式
//        leaderName = mActivity.getIntent().getStringExtra("leaderName");
//        leaderId = mActivity.getIntent().getStringExtra("leaderId");

        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {  //专责接受的Bean不一样
            OverhaulZzTaskBean bean = mActivity.getIntent().getParcelableExtra("bean");
            if (bean != null) {
                taskId = bean.getId();
                controlCardName.setText(bean.getTask_content());
                controlCardType.setText("带电作业班");
                controlCardDep.setText(leaderName);

                //getFzrInfo(bean.getRepair_id(), "2");
                controlCardPersonal.setText(leaderName);
                controlCardNo.setText("暂无");
                controlCardStartTime.setText(bean.getStart_time());
                controlCardEndTime.setText(bean.getEnd_time());
            }

        } else {
            OverhaulMonthBean bean = mActivity.getIntent().getParcelableExtra("bean");
            if (bean != null) {
                taskId = bean.getId();
                controlCardName.setText(bean.getTask_content());
                controlCardType.setText("带电作业班");
                controlCardDep.setText(leaderName);

                //getFzrInfo(bean.getRepair_id(), "2");
                controlCardPersonal.setText(leaderName);
                controlCardNo.setText("暂无");
                controlCardStartTime.setText(bean.getStart_time());
                controlCardEndTime.setText(bean.getEnd_time());
            }
        }

        ControlCardBean controlBean = (ControlCardBean) mActivity.getIntent().getSerializableExtra("id");
        getWorkQualityCard();

        switch (enterType) {
            case Constant.IS_OTHER_LOOK:    //查看模式
                isCanClick = false;
                controlCardSubmit.setVisibility(View.GONE);
                etRemarkTv.setVisibility(View.VISIBLE);
                etRemark.setVisibility(View.GONE);

                if (workQualityCardBean != null) {
                    etRemarkTv.setText(workQualityCardBean.getRemark());
                    setQualityList(workQualityCardBean.getWorkStandardStatuses());

                    AllControlCarBean.WorkQualityCardBean.SysFile sysFile = workQualityCardBean.getSysFile();
                    if (sysFile != null) {
                        String filePath = sysFile.getFile_path();
                        filePath = filePath.substring(1, filePath.length());
                        String fileName = sysFile.getFilename();
                        String url = filePath + fileName;
                        if (url != null) {
                            showSign(url);
                        }
                    }
                } else {  //如果进来质量卡为空,则显示模板,表示上次没填写,但是查看模式,也不可再填写
                    if (controlBean != null) {
                        getQualityList(controlBean.getId());
                    }
                }
                break;

            case Constant.IS_FZR_WRITE:    //负责人填写
                isCanClick = true;
                controlCardSubmit.setVisibility(View.VISIBLE);
                etRemarkTv.setVisibility(View.GONE);
                etRemark.setVisibility(View.VISIBLE);

                if (controlBean != null) {
                    getQualityList(controlBean.getId());
                }
                break;

            case Constant.IS_FZR_UPDATE:    //负责人更新  OR  填写
                isCanClick = true;
                controlCardSubmit.setVisibility(View.VISIBLE);
                etRemarkTv.setVisibility(View.GONE);
                etRemark.setVisibility(View.VISIBLE);

                if (workQualityCardBean != null) {
                    isFzrUpdate = true;
                    etRemark.setText(workQualityCardBean.getRemark());
                    setQualityList(workQualityCardBean.getWorkStandardStatuses());

                    AllControlCarBean.WorkQualityCardBean.SysFile sysFile = workQualityCardBean.getSysFile();
                    if (sysFile != null) {
                        String filePath = sysFile.getFile_path();
                        filePath = filePath.substring(1, filePath.length());
                        String fileName = sysFile.getFilename();
                        String url = filePath + fileName;
                        if (url != null) {
                            showSign(url);
                        }
                    }

                } else {
                    isFzrUpdate = false;
                    if (controlBean != null) {
                        getQualityList(controlBean.getId());
                    }
                }
                break;
        }

    }

    private void showSign(String url) {
        //显示签名
        if (url != null) {
            Glide.with(this).load(BaseUrl.BASE_URL + url).into(ivSignaturePad);
        }
    }

    private void getWorkQualityCard() {
        AllControlCarBean allControlCarBean = mActivity.getIntent().getParcelableExtra("allControlBean");
        if (allControlCarBean != null) {
            workQualityCardBean = allControlCarBean.getWorkQualityCard();
            if (workQualityCardBean != null) {
                leaderId = workQualityCardBean.getDuty_user_id();
                leaderName = workQualityCardBean.getDuty_user_name();
                controlCardPersonal.setText(leaderName);
                controlCardDep.setText(leaderName);
            }
        }
    }

    private void setQualityList(List<AllControlCarBean.WorkQualityCardBean.WorkStandardRelationsBean> workStandardRelationsBeans) {
        mControlQualityList.clear();

        for (int i = 0; i < workStandardRelationsBeans.size(); i++) {
            ControlQualityInfo info = new ControlQualityInfo();
            info.setDivisonNo(i + 1);
            info.setKeyDivison(workStandardRelationsBeans.get(i).getProcess());
            info.setContent(workStandardRelationsBeans.get(i).getStandard());
            info.setSafeDivison(workStandardRelationsBeans.get(i).getWarning());
            info.setCheckInfo(workStandardRelationsBeans.get(i).getStatus());
            info.setW_q_s_id(workStandardRelationsBeans.get(i).getWork_standard_id());
            mControlQualityList.add(info);
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        newControlQualityRv.setLayoutManager(manager);
        ControlQualityAdapter qualityAdapter = new ControlQualityAdapter(R.layout.item_control_quality_division, mControlQualityList, isCanClick);
        newControlQualityRv.setAdapter(qualityAdapter);
        qualityAdapter.addHeaderView(header);
        qualityAdapter.addFooterView(bottom);
    }


    private void getQualityList(String id) {
        BaseRequest.getInstance().getService()
                .getControlQuality(/*"8235B623B83A4E50A3F006C3F8FCB287"*/id, Constant.SORT_ASC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ControlQualityBean>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<ControlQualityBean>> t) throws Exception {
                        Log.w("linmeng", "t.toString():" + t.toString());
                        if (t.getCode() == 1) {
                            controlQualityBeans = t.getResults();
                            updateInfo1(controlQualityBeans);

                        } else {
                            Toast.makeText(mContext, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void updateInfo1(List<ControlQualityBean> controlQualityBeans) {
        mControlQualityList.clear();

        for (int i = 0; i < controlQualityBeans.size(); i++) {
            ControlQualityInfo info = new ControlQualityInfo();
            info.setDivisonNo(i + 1);
            info.setKeyDivison(controlQualityBeans.get(i).getProcess());
            info.setContent(controlQualityBeans.get(i).getStandard());
            info.setSafeDivison(controlQualityBeans.get(i).getWarning());
            info.setType(controlQualityBeans.get(i).getType_id());
            info.setW_q_s_id(controlQualityBeans.get(i).getWork_standard_id());

            mControlQualityList.add(info);
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        newControlQualityRv.setLayoutManager(manager);
        ControlQualityAdapter qualityAdapter = new ControlQualityAdapter(R.layout.item_control_quality_division, mControlQualityList, isCanClick);
        newControlQualityRv.setAdapter(qualityAdapter);
        qualityAdapter.addHeaderView(header);
        qualityAdapter.addFooterView(bottom);
    }

//    @OnClick({R.id.control_card_start_time, R.id.control_card_end_time, R.id.control_card_submit, R.id.iv_signature_pad})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.control_card_start_time:
//                //PickerUtils.showDate(mContext, controlCardStartTime);
//                break;
//            case R.id.control_card_end_time:
//                //PickerUtils.showDate(mContext, controlCardEndTime);
//                break;
//            case R.id.control_card_submit:
//
//                break;
//            case R.id.iv_signature_pad:
//
//                break;
//        }
//    }

    private ControlOperationBean setValue() {
        ControlOperationBean bean = new ControlOperationBean();

        bean.setDuty_user_id(leaderId);
        bean.setDuty_user_name(leaderName);

        bean.setTask_id(taskId);
        bean.setRemark(etRemark.getText().toString());

        List<ControlOperationBean.WorkStandardRelation> workStandardRelations = new ArrayList<>();

        for (int i = 0; i < mControlQualityList.size(); i++) {
            workStandardRelations.add(new ControlOperationBean.WorkStandardRelation(mControlQualityList.get(i).getW_q_s_id(), mControlQualityList.get(i).getCheckInfo() == null ? "未填写" : mControlQualityList.get(i).getCheckInfo()));
        }
        bean.setWorkStandardRelations(workStandardRelations);

        return bean;
    }

    private void putValue(ControlOperationBean bean) {
        params.put("duty_user_id", toRequestBody(bean.getDuty_user_id()));
        params.put("duty_user_name", toRequestBody(bean.getDuty_user_name()));
        params.put("remark", toRequestBody(bean.getRemark()));
        params.put("check_task_id", toRequestBody(bean.getTask_id()));

        for (int i = 0; i < bean.getWorkStandardRelations().size(); i++) {
            params.put("workStandardStatuses[" + i + "].work_standard_id", toRequestBody(bean.getWorkStandardRelations().get(i).getW_q_s_id()));
            params.put("workStandardStatuses[" + i + "].status", toRequestBody(bean.getWorkStandardRelations().get(i).getStatus()));
        }

        if (mPicList != null && mPicList.size() > 0) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mPicList.get(0));
            params.put("file\"; filename=\"" + mPicList.get(0).getName(), requestFile);
        }
    }

    private void uploadControlQuality() {
        BaseRequest.getInstance().getService().upLoadControlQuality(params).subscribeOn(Schedulers.io())
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

    public RequestBody toRequestBody(String value) {
        if (value != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
            return requestBody;
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            return requestBody;
        }
    }

    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //    定义函数动态控制listView的高度
    public void setListViewHeightBasedOnChildren(ListView listView) {
//获取listview的适配器
        ListAdapter listAdapter = listView.getAdapter();
//item的高度
        int itemHeight = 46;
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            totalHeight += Dp2Px(getContext(), itemHeight) + listView.getDividerHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }
}
