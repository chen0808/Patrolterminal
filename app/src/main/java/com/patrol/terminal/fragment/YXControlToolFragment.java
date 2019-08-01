package com.patrol.terminal.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ControlToolAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AllControlCarBean;
import com.patrol.terminal.bean.ControlToolBean;
import com.patrol.terminal.bean.ControlToolBeanList;
import com.patrol.terminal.bean.ControlToolInfo;
import com.patrol.terminal.bean.DefectPlanDetailBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulZzTaskBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.AddToolDialog;
import com.patrol.terminal.widget.NoScrollListView;

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
    private List<ControlToolInfo> mControlToolList1 = new ArrayList<>();
    private List<ControlToolInfo> mControlToolList2 = new ArrayList<>();

    private Context mContext;
    private Activity mActivity;

    private ControlToolAdapter depdapter1;
    private ControlToolAdapter depdapter2;

    private int enterType;
    private boolean isFzrUpdate = false;
    private AllControlCarBean allControlCarBean;
    private String taskId = "";

    private boolean isCanClick = true;  //默认能点击，填写和更新状态
    private  List<AllControlCarBean.CardTool> workToolsBeans = null;

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


        DefectPlanDetailBean bean = (DefectPlanDetailBean) mActivity.getIntent().getSerializableExtra("bean");
                taskId = bean.getId();


        //ControlCardBean controlBean = (ControlCardBean) mActivity.getIntent().getSerializableExtra("id");
        AllControlCarBean allControlCarBean = mActivity.getIntent().getParcelableExtra("allControlBean");
        if (allControlCarBean != null) {
            workToolsBeans = allControlCarBean.getWorkTools();
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


        if(isCanClick) {
            controlCardDiv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    AddToolDialog.update(mContext, depdapter1, mControlToolList1, position);

                }
            });

            controlCardDiv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    AddToolDialog.update(mContext, depdapter2, mControlToolList2, position);
                }
            });

        }
    }

    private void getAllInfo(List<AllControlCarBean.CardTool> workToolsBeans) {
        if (workToolsBeans != null && workToolsBeans.size() > 0) {
            int j = 0,k = 0;
            for (int i = 0; i < workToolsBeans.size(); i++) {
                AllControlCarBean.CardTool item = workToolsBeans.get(i);
                String toolType = item.getTool_type();

                ControlToolInfo bean = new ControlToolInfo();
                bean.setName(item.getTool_name());
                bean.setType(item.getType());
                bean.setUnit(item.getUnit());
                bean.setTotal(item.getTotal());
                bean.setDetail(item.getDetail());
                bean.setTool_type(toolType);
                bean.setId(item.getId());
                if (toolType.equals("0")) {
                    bean.setNum(j++);
                    mControlToolList1.add(bean);
                }else if (toolType.equals("1")) {
                    bean.setNum(k++);
                    mControlToolList2.add(bean);
                }
            }

            depdapter1.setData(mControlToolList1);
            depdapter2.setData(mControlToolList2);
        }
    }


    @OnClick({R.id.add_btn_01, R.id.add_btn_02, R.id.control_card_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_btn_01:
                AddToolDialog.show(mContext, depdapter1, mControlToolList1);

                break;

            case R.id.add_btn_02:
                AddToolDialog.show(mContext, depdapter2, mControlToolList2);

                break;

            case R.id.control_card_submit:
                List<ControlToolBean> controlToolBeans = new ArrayList<>();
                for (int i = 0; i < mControlToolList1.size(); i++) {

                    ControlToolBean bean = new ControlToolBean();
                    bean.setCheck_task_id(taskId);
                    bean.setTool_name(mControlToolList1.get(i).getName());
                    bean.setType(mControlToolList1.get(i).getType());
                    bean.setUnit(mControlToolList1.get(i).getUnit());
                    bean.setTotal(mControlToolList1.get(i).getTotal());
                    bean.setDetail(mControlToolList1.get(i).getDetail());
                    bean.setTool_type("0");
//                    if (enterType == Constant.IS_FZR_UPDATE && isFzrUpdate) {  //更新
//                        String id = mControlToolList1.get(i).getId();
//                        if (id != null) {
//                            bean.setId(id);
//                        }
//                    }
                    controlToolBeans.add(bean);
                }

                for (int i = 0; i < mControlToolList2.size(); i++) {
                    ControlToolBean bean = new ControlToolBean();
                    bean.setCheck_task_id(taskId);
                    bean.setTool_name(mControlToolList2.get(i).getName());
                    bean.setType(mControlToolList2.get(i).getType());
                    bean.setUnit(mControlToolList2.get(i).getUnit());
                    bean.setTotal(mControlToolList2.get(i).getTotal());
                    bean.setDetail(mControlToolList2.get(i).getDetail());
                    bean.setTool_type("1");
//                    if (enterType == Constant.IS_FZR_UPDATE && isFzrUpdate) {  //更新
//                        String id = mControlToolList2.get(i).getId();
//                        if (id != null) {
//                            bean.setId(id);
//                        }
//                    }
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
        }
    }
}
