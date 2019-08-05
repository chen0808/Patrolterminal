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
import com.patrol.terminal.bean.CardControl;
import com.patrol.terminal.bean.CardControlSign;
import com.patrol.terminal.bean.CardQuality;
import com.patrol.terminal.bean.CardQualityStandard;
import com.patrol.terminal.bean.CardQualityUser;
import com.patrol.terminal.bean.ControlCardBean;
import com.patrol.terminal.bean.ControlOperationBean;
import com.patrol.terminal.bean.ControlQualityBean;
import com.patrol.terminal.bean.ControlQualityInfo;
import com.patrol.terminal.bean.DefectPlanDetailBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulZzTaskBean;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class YXNewControlQualityFragment extends BaseFragment {


    @BindView(R.id.new_control_quality_rv)
    RecyclerView newControlQualityRv;
    Map<String, RequestBody> params = new HashMap<>();

    private Context mContext;

    private List<CardQualityStandard> mControlQualityList = new ArrayList<>();
    private List<File> mPicList = new ArrayList<>();
    private List<CardQualityStandard> controlQualityBeans;
    private ControlOperationAdapter depdapter1;
    private Activity mActivity;
    private CardQuality workQualityCardBean = null;
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
    private String controlName;
    private DefectPlanDetailBean bean;
    private CardControl cardControl;
    private ControlQualityAdapter qualityAdapter;

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


        enterType = mActivity.getIntent().getIntExtra(Constant.CONTROL_CARD_ENTER_TYPE, Constant.IS_OTHER_LOOK);  //是否为查看模式
//        leaderName = mActivity.getIntent().getStringExtra("leaderName");
//        leaderId = mActivity.getIntent().getStringExtra("leaderId");
        AllControlCarBean allControlCarBean = (AllControlCarBean) mActivity.getIntent().getSerializableExtra("allControlBean");
        workQualityCardBean = allControlCarBean.getCardQuality();
        ControlCardBean controlBean = (ControlCardBean) mActivity.getIntent().getSerializableExtra("id");
        if (workQualityCardBean == null) {
            getQualityList(controlBean.getId());
            bean = (DefectPlanDetailBean) mActivity.getIntent().getSerializableExtra("bean");
            if (bean != null) {
                taskId = bean.getId();
                List<DefectPlanDetailBean.TaskDefectUserListBean> taskDefectUserList = bean.getTaskDefectUserList();
                String names = "";
                for (int i = 0; i < taskDefectUserList.size(); i++) {
                    DefectPlanDetailBean.TaskDefectUserListBean taskDefectUserListBean = taskDefectUserList.get(i);
                    if ("2".equals(taskDefectUserListBean.getSign())) {
                        leaderName = taskDefectUserListBean.getUser_name();
                        leaderId = taskDefectUserListBean.getId();
                    } else {
                        if ("".equals(names)) {
                            names = taskDefectUserListBean.getUser_name();
                        } else {
                            names = names + "," + taskDefectUserListBean.getUser_name();
                        }
                    }
                }
                controlName = bean.getLine_name() + bean.getTower_name() + bean.getDeal_notes();
                controlCardName.setText(controlName);
                controlCardType.setText(bean.getDeal_dep_name());
                controlCardDep.setText(leaderName);

                //getFzrInfo(bean.getRepair_id(), "2");
                controlCardPersonal.setText(names);
                controlCardNo.setText("暂无");
                controlCardStartTime.setText(bean.getDeal_time());
                controlCardEndTime.setText(bean.getClose_time());

            }
        } else {
            List<CardQualityUser> userList = workQualityCardBean.getUserList();
            String names = "";
            taskId = workQualityCardBean.getId();
            for (int i = 0; i < userList.size(); i++) {
                CardQualityUser cardQualityUser = userList.get(i);

                    if ("".equals(names)) {
                        names = cardQualityUser.getUser_name();
                    } else {
                        names = names + "," + cardQualityUser.getUser_name();
                    }
            }
            leaderId = workQualityCardBean.getDuty_user_id();
            leaderName = workQualityCardBean.getDuty_user_name();
            controlName = workQualityCardBean.getContent();
            controlCardName.setText(controlName);
            controlCardType.setText(workQualityCardBean.getDep_name());
            controlCardDep.setText(leaderName);

            //getFzrInfo(bean.getRepair_id(), "2");
            controlCardPersonal.setText(names);
            controlCardNo.setText("暂无");
            controlCardStartTime.setText(workQualityCardBean.getStart_time());
            controlCardEndTime.setText(workQualityCardBean.getEnd_time());
            String filePath = workQualityCardBean.getFile_path();
            filePath = filePath.substring(1, filePath.length());
            String fileName = workQualityCardBean.getFilename();
            String url = filePath + fileName;
            if (url != null) {
                showSign(url);
            }
            etRemarkTv.setText(workQualityCardBean.getRemark());
            setQualityList(workQualityCardBean.getStandardList());
        }


        switch (enterType) {
            case Constant.IS_OTHER_LOOK:    //查看模式
                isCanClick = false;
                controlCardSubmit.setVisibility(View.GONE);
                etRemarkTv.setVisibility(View.VISIBLE);
                etRemark.setVisibility(View.GONE);

                break;

            case Constant.IS_FZR_WRITE:    //负责人填写
                isCanClick = true;
                controlCardSubmit.setVisibility(View.VISIBLE);
                etRemarkTv.setVisibility(View.GONE);
                etRemark.setVisibility(View.VISIBLE);
                break;

            case Constant.IS_FZR_UPDATE:    //负责人更新  OR  填写
                isCanClick = true;
                controlCardSubmit.setVisibility(View.VISIBLE);
                etRemarkTv.setVisibility(View.GONE);
                etRemark.setVisibility(View.VISIBLE);

                if (workQualityCardBean != null) {
                    isFzrUpdate = true;
                    etRemark.setText(workQualityCardBean.getRemark());
                    setQualityList(workQualityCardBean.getStandardList());

                } else {
                    isFzrUpdate = false;
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



    private void setQualityList(List<CardQualityStandard> workStandardRelationsBeans) {
        mControlQualityList.clear();

        for (int i = 0; i < workStandardRelationsBeans.size(); i++) {
            CardQualityStandard info = workStandardRelationsBeans.get(i);
            info.setDivisonNo(i + 1);
            mControlQualityList.add(info);
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        newControlQualityRv.setLayoutManager(manager);
        qualityAdapter = new ControlQualityAdapter(R.layout.item_control_quality_division, mControlQualityList, isCanClick);
        newControlQualityRv.setAdapter(qualityAdapter);
        ViewGroup parentViewGroup = (ViewGroup) header.getParent();
        if (parentViewGroup != null) {
            parentViewGroup.removeAllViews();
        }
        ViewGroup parentViewGroup1 = (ViewGroup) bottom.getParent();
        if (parentViewGroup1 != null) {
            parentViewGroup1.removeAllViews();
        }
        qualityAdapter.addHeaderView(header);
        qualityAdapter.addFooterView(bottom);
    }


    private void getQualityList(String id) {
        BaseRequest.getInstance().getService()
                .getControlQuality(/*"8235B623B83A4E50A3F006C3F8FCB287"*/id, Constant.SORT_ASC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<CardQualityStandard>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<CardQualityStandard>> t) throws Exception {
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

    private void updateInfo1(List<CardQualityStandard> controlQualityBeans) {
        mControlQualityList.clear();

        for (int i = 0; i < controlQualityBeans.size(); i++) {
            CardQualityStandard controlQualityBean = controlQualityBeans.get(i);
            controlQualityBean.setDivisonNo(i + 1);
            controlQualityBean.setCard_standard_id(controlQualityBean.getId());
            mControlQualityList.add(controlQualityBean);
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        newControlQualityRv.setLayoutManager(manager);
        qualityAdapter = new ControlQualityAdapter(R.layout.item_control_quality_division, mControlQualityList, isCanClick);
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
        }
        bean.setWorkStandardRelations(workStandardRelations);

        return bean;
    }



    private void uploadControlQuality() {

        List<CardQualityUser> userList=new ArrayList<>();
        for (int i = 0; i < bean.getTaskDefectUserList().size(); i++) {
            DefectPlanDetailBean.TaskDefectUserListBean taskDefectUserListBean = bean.getTaskDefectUserList().get(i);
            String sign = taskDefectUserListBean.getSign();
            if ("3".equals(sign)){
                CardQualityUser user=new CardQualityUser();
                user.setUser_id(taskDefectUserListBean.getUser_id());
                user.setUser_name(taskDefectUserListBean.getUser_name());
                userList.add(user);
            }
        }
        List<CardQualityStandard> data = qualityAdapter.getData();
        String remark = etRemark.getText().toString();
        CardQuality cardQuality=new CardQuality();
        cardQuality.setContent(controlName);
        cardQuality.setDep_id(bean.getDeal_dep_id());
        cardQuality.setDep_name(bean.getDeal_dep_name());
        cardQuality.setDuty_user_id(leaderId);
        cardQuality.setDuty_user_name(leaderName);
        cardQuality.setEnd_time(bean.getClose_time());
        cardQuality.setStart_time(bean.getDeal_time());

        cardQuality.setRemark(remark);
        cardQuality.setTask_repair_id(taskId);
        cardQuality.setStandardList(data);
        cardQuality.setUserList(userList);
        for (int i = 0; i < mPicList.size(); i++) {
            File file = mPicList.get(i);
            String sign = FileUtil.fileToBase64(file);
            cardQuality.setFile(sign);
        }
        BaseRequest.getInstance().getService().saveQualityControl(cardQuality).subscribeOn(Schedulers.io())
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
