package com.patrol.terminal.overhaul;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.InitiateProjectAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.InitiateProjectBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//项目立项
public class InitiateProjectActivity extends BaseActivity {

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
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;

    private int logType = 0;
    private Context mContext;
    private InitiateProjectAdapter initiateProjectAdapter;
    private List<InitiateProjectBean> initiateProjectList = new ArrayList<>();
    private int pageNum = 1;
    private int count = 10;
    private String search_name = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_project);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        mContext = this;

        titleName.setText("项目");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingIv.setImageResource(R.mipmap.add_white);
        titleSettingTv.setText("");

        String userName = SPUtil.getUserName(this);
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));

//        InitiateProjectBean initiateProjectBean = new InitiateProjectBean();
//        initiateProjectBean.setName("定期巡视");
//        initiateProjectBean.setCreate_name(userName);
//        initiateProjectBean.setProject_no("378529");
//        initiateProjectBean.setContent("杆塔倾斜");
//        initiateProjectBean.setStart_time(time);
//        initiateProjectBean.setStatus(1+"");
//        initiateProjectList.add(initiateProjectBean);
//
//        initiateProjectBean = new InitiateProjectBean();
//        initiateProjectBean.setName("绝缘子检测");
//        initiateProjectBean.setCreate_name(userName);
//        initiateProjectBean.setProject_no("378457");
//        initiateProjectBean.setContent("正常");
//        initiateProjectBean.setStart_time(time);
//        initiateProjectBean.setStatus(2 + "");
//        initiateProjectList.add(initiateProjectBean);
//
//        initiateProjectBean = new InitiateProjectBean();
//        initiateProjectBean.setName("电阻检测");
//        initiateProjectBean.setCreate_name(userName);
//        initiateProjectBean.setProject_no("378555");
//        initiateProjectBean.setContent("需要更换");
//        initiateProjectBean.setStart_time(time);
//        initiateProjectBean.setStatus(3 + "");
//        initiateProjectList.add(initiateProjectBean);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        planRv.setLayoutManager(manager);
        planRv.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));

        initiateProjectAdapter = new InitiateProjectAdapter(R.layout.item_initiate_project, logType);
        planRv.setAdapter(initiateProjectAdapter);
        planRv.useDefaultLoadMore();
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                getProjectList(search_name);
            }
        });

//        initiateProjectAdapter.setNewData(initiateProjectList);

        getProjectList(search_name);
    }

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                intent = new Intent(this, InitiateProjectAddActivity.class);
                intent.putExtra("logType", logType);
                startActivity(intent);
                break;
        }
    }

    //获取项目列表
    public void getProjectList(String search_name) {
        ProgressDialog.show(mContext, true, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getProjectList(pageNum, count, search_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<InitiateProjectBean>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<InitiateProjectBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            List<InitiateProjectBean> result = t.getResults();
                            if (result != null && result.size() > 0 && result.size() == 10) {
                                planRv.loadMoreFinish(false, true);
                            } else {
                                planRv.loadMoreFinish(true, false);
                            }
                            initiateProjectList.clear();
                            initiateProjectList.addAll(result);
                            setDataToList(result);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }
                });
    }

    private void setDataToList(List<InitiateProjectBean> beans) {
        if (pageNum == 1) {
            initiateProjectAdapter.setNewData(beans);
        } else {
            initiateProjectAdapter.addData(beans);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_ADDRESS_BOOK) {
            }
        }
    }
}
