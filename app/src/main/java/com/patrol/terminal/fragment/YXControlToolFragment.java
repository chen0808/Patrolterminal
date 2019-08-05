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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ControlToolAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AllControlCarBean;
import com.patrol.terminal.bean.CardTool;
import com.patrol.terminal.bean.ControlToolBeanList;
import com.patrol.terminal.bean.DefectPlanDetailBean;
import com.patrol.terminal.bean.EqToolTemp;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.AddToolDialog;
import com.patrol.terminal.widget.NoScrollListView;
import com.patrol.terminal.widget.SignDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class YXControlToolFragment extends BaseFragment {

    @BindView(R.id.control_xianlu_name)
    TextView controlXianluName;
    @BindView(R.id.control_card_name)
    TextView controlCardName;
    @BindView(R.id.control_card_div)
    NoScrollListView controlCardDiv;
    @BindView(R.id.control_xianlu_name_2)
    TextView controlXianluName2;
    @BindView(R.id.control_card_name_2)
    TextView controlCardName2;
    @BindView(R.id.control_card_div_2)
    NoScrollListView controlCardDiv2;
    @BindView(R.id.control_card_submit)
    TextView controlCardSubmit;
    @BindView(R.id.add_btn_01)
    Button addBtn01;
    @BindView(R.id.add_btn_02)
    Button addBtn02;
    @BindView(R.id.iv_signature_pad)
    ImageView ivSignaturePad;

    private List<CardTool> mControlToolList1 = new ArrayList<>();
    private List<CardTool> mControlToolList2 = new ArrayList<>();

    private Context mContext;
    private Activity mActivity;

    private ControlToolAdapter depdapter1;
    private ControlToolAdapter depdapter2;

    private int enterType;
    private boolean isFzrUpdate = false;
    private AllControlCarBean allControlCarBean;
    private String taskId = "";

    private boolean isCanClick = true;  //默认能点击，填写和更新状态
    private List<CardTool> workToolsBeans = null;
    private List<EqToolTemp> eqToolTemp1=new ArrayList<>();
    private List<EqToolTemp> eqToolTemp2=new ArrayList<>();
    private List<File> mPicList = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_tool_card, null);
        return view;
    }

    @Override
    protected void initData() {
        mContext = getActivity();
        mActivity = getActivity();
        enterType = mActivity.getIntent().getIntExtra(Constant.CONTROL_CARD_ENTER_TYPE, Constant.IS_OTHER_LOOK);  //是否为查看模式
        String jobType = SPUtil.getString(mActivity, Constant.USER, Constant.JOBTYPE, "");

        getToolDetail();
        DefectPlanDetailBean bean = (DefectPlanDetailBean) mActivity.getIntent().getSerializableExtra("bean");
        if (bean!=null){
            taskId = bean.getId();
        }



        //ControlCardBean controlBean = (ControlCardBean) mActivity.getIntent().getSerializableExtra("id");
        AllControlCarBean allControlCarBean = (AllControlCarBean) mActivity.getIntent().getSerializableExtra("allControlBean");
        if (allControlCarBean != null) {
            workToolsBeans = allControlCarBean.getCardTool();
        }

        depdapter1 = new ControlToolAdapter(getContext());
        depdapter2 = new ControlToolAdapter(getContext());
        controlCardDiv.setAdapter(depdapter1);
        controlCardDiv2.setAdapter(depdapter2);
        mControlToolList1.clear();
        mControlToolList2.clear();

        switch (enterType) {
            case Constant.IS_OTHER_LOOK:
                isCanClick = false;
                controlCardSubmit.setVisibility(View.GONE);
                addBtn01.setVisibility(View.GONE);
                addBtn02.setVisibility(View.GONE);
                if (workToolsBeans.size() > 0) {
                    getAllInfo(workToolsBeans);
                }

                break;

            case Constant.IS_FZR_WRITE:
                isCanClick = true;
                controlCardSubmit.setVisibility(View.VISIBLE);
                addBtn01.setVisibility(View.VISIBLE);
                addBtn02.setVisibility(View.VISIBLE);
                break;

            case Constant.IS_FZR_UPDATE:   //更新或者填写
                isCanClick = true;
                controlCardSubmit.setVisibility(View.VISIBLE);
                addBtn01.setVisibility(View.VISIBLE);
                addBtn02.setVisibility(View.VISIBLE);

                if (workToolsBeans.size() > 0) {
                    getAllInfo(workToolsBeans);
                }
                break;
        }


        if (isCanClick) {
            controlCardDiv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    AddToolDialog.update(mContext, depdapter1, mControlToolList1, position,eqToolTemp1);

                }
            });

            controlCardDiv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    AddToolDialog.update(mContext, depdapter2, mControlToolList2, position,eqToolTemp2);
                }
            });

        }
    }

    private void getAllInfo(List<CardTool> workToolsBeans) {
        if (workToolsBeans != null && workToolsBeans.size() > 0) {
            int j = 0, k = 0;
            for (int i = 0; i < workToolsBeans.size(); i++) {
                CardTool bean = workToolsBeans.get(i);
                String toolType = bean.getTool_type();
                if (toolType.equals("0")) {
                    bean.setNum(j++);
                    mControlToolList1.add(bean);
                } else if (toolType.equals("1")) {
                    bean.setNum(k++);
                    mControlToolList2.add(bean);
                }
            }

            depdapter1.setData(mControlToolList1);
            depdapter2.setData(mControlToolList2);
        }
    }


    @OnClick({R.id.add_btn_01, R.id.add_btn_02, R.id.control_card_submit, R.id.iv_signature_pad})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_btn_01:
                AddToolDialog.show(mContext, depdapter1, mControlToolList1, eqToolTemp1);

                break;

            case R.id.add_btn_02:
                AddToolDialog.show(mContext, depdapter2, mControlToolList2, eqToolTemp2);

                break;

            case R.id.control_card_submit:
                List<CardTool> controlToolBeans = new ArrayList<>();
                for (int i = 0; i < mControlToolList1.size(); i++) {

                    CardTool bean = mControlToolList1.get(i);
                    bean.setTool_type("0");
                    bean.setTool_id(bean.getId());
                    bean.setTask_repair_id(taskId);
                    controlToolBeans.add(bean);
                }
                for (int i = 0; i < mControlToolList2.size(); i++) {
                    CardTool bean = mControlToolList2.get(i);
                    bean.setTool_type("1");
                    bean.setTool_id(bean.getId());
                    bean.setTask_repair_id(taskId);
                    controlToolBeans.add(bean);
                }
                BaseRequest.getInstance().getService()
                        .postControlTool(controlToolBeans)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<ControlToolBeanList>(mContext) {
                            @Override
                            protected void onSuccees(BaseResult<ControlToolBeanList> t) throws Exception {
                                Log.w("linmeng", "t.toString():" + t.toString());
                                if (t.getCode() == 1) {
                                    Toast.makeText(mContext, "上传成功！", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(mContext, t.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }
                        });
                break;
            case R.id.iv_signature_pad:
                if (isCanClick) {

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

    public void getToolDetail() {
        BaseRequest.getInstance().getService()
                .getToolType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<EqToolTemp>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<EqToolTemp>> t) throws Exception {
                        List<EqToolTemp> results = t.getResults();
                        for (int i = 0; i < results.size(); i++) {
                            EqToolTemp eqToolTemp = results.get(i);
                            if ("0".equals(eqToolTemp.getTool_type())){
                                eqToolTemp1.add(eqToolTemp);
                            }else if ("1".equals(eqToolTemp.getTool_type())){
                                eqToolTemp2.add(eqToolTemp);
                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}

