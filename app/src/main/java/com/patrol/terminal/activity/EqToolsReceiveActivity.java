package com.patrol.terminal.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.EqToolsReceiveAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.EqToolsBean;
import com.patrol.terminal.bean.EqToolsReceiveBean;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.CancelOrOkDialogNew;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*工器具领用*/
public class EqToolsReceiveActivity extends BaseActivity {
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
    @BindView(R.id.tv_qx_content)
    AutoCompleteTextView tvContent;
    @BindView(R.id.search_delete)
    ImageView searchDelete;
    @BindView(R.id.control_card_div)
    SwipeRecyclerView controlCardDiv;
    @BindView(R.id.txt_submit)
    TextView txtSubmit;

    private List<EqToolsBean> eqToolsList = new ArrayList<>();;
    private List<EqToolsBean> searchList = new ArrayList<>();
    private List<EqToolsReceiveBean> receiveList = new ArrayList<>();
    private EqToolsReceiveAdapter eqToolsReceiveAdapter;
    private String search_name = "";
    private int pageNum = 1;
    private int count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eq_tools_receive);
        ButterKnife.bind(this);
        initview();
        getEqTools(search_name);
    }

    private void initview() {
        titleName.setText("工器具领用");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        controlCardDiv.setLayoutManager(manager);
        eqToolsReceiveAdapter = new EqToolsReceiveAdapter(R.layout.item_eq_tool_receive, this);
        controlCardDiv.setAdapter(eqToolsReceiveAdapter);

        tvContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search_name = s.toString();
                if (s.length() == 0) {
                    searchDelete.setVisibility(View.GONE);
                } else {
                    searchDelete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                search_name = tvContent.getText().toString();
                if (TextUtils.isEmpty(search_name)) {
                    eqToolsReceiveAdapter.setNewData(eqToolsList);
                } else {
                    searchList.clear();
                    for (int i = 0; i < eqToolsList.size(); i++) {
                        if (eqToolsList.get(i).getName().contains(search_name)) {
                            searchList.add(eqToolsList.get(i));
                        }
                    }
                    eqToolsReceiveAdapter.setNewData(searchList);
                }
            }
        });
    }

    @OnClick({R.id.title_back, R.id.txt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.txt_submit:
                receiveList.clear();
                if(eqToolsList != null && eqToolsList.size() > 0){
                    for(int i=0;i<eqToolsList.size();i++){
                        if(eqToolsList.get(i).getTotal() != null && eqToolsList.get(i).getTotal().intValue() > 0){
                            EqToolsReceiveBean eqToolsReceiveBean = new EqToolsReceiveBean();
                            eqToolsReceiveBean.setEq_tools_id(eqToolsList.get(i).getId());
                            eqToolsReceiveBean.setEq_tools_name(eqToolsList.get(i).getName());
                            eqToolsReceiveBean.setType(eqToolsList.get(i).getType());
                            eqToolsReceiveBean.setUnit(eqToolsList.get(i).getUnit());
                            eqToolsReceiveBean.setTotal(eqToolsList.get(i).getTotal());
                            eqToolsReceiveBean.setBrand(eqToolsList.get(i).getBrand());
                            eqToolsReceiveBean.setUser_id(SPUtil.getUserId(this));
                            eqToolsReceiveBean.setUser_name(SPUtil.getUserName(this));
                            eqToolsReceiveBean.setDep_id(SPUtil.getDepId(this));
                            eqToolsReceiveBean.setDep_name(SPUtil.getDepName(this));
                            eqToolsReceiveBean.setRemarks(eqToolsList.get(i).getRemarks());
                            eqToolsReceiveBean.setTool_type(eqToolsList.get(i).getTool_type());
                            receiveList.add(eqToolsReceiveBean);
                        }
                    }
                }
                submit();
                break;
        }
    }


    //获取工器具台账
    public void getEqTools(String search_name) {
        ProgressDialog.show(this, true, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getEqTools(search_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<EqToolsBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<EqToolsBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            eqToolsList = t.getResults();
                            for(int i=0;i<eqToolsList.size();i++){
                                eqToolsList.get(i).setNumber(i);
                            }
                            eqToolsReceiveAdapter.addData(eqToolsList);
                            eqToolsReceiveAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //工器具批量领用
    public void getToolReceive() {
        ProgressDialog.show(this, true, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getToolReceive(receiveList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        if (t.isSuccess()) {
                            Utils.showToast("领用成功");
                            finish();
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //提交缺陷审核
    public void submit() {
        CancelOrOkDialogNew dialog = new CancelOrOkDialogNew(this, "领用", "取消", "确定") {
            @Override
            public void ok() {
                super.ok();
                getToolReceive();
            }

            @Override
            public void cancle() {
                super.cancle();
            }
        };
        dialog.show();
    }
}
