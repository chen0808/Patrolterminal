package com.patrol.terminal.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GetToPostCheckAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GetToPostCheckBean;
import com.patrol.terminal.bean.PostCheckBean;
import com.patrol.terminal.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class GetToPostCheckActivity extends BaseActivity {

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
    @BindView(R.id.work_task_lv)
    NoScrollListView getToPostLl;
    @BindView(R.id.safe_lv)
    NoScrollListView safeLv;
    @BindView(R.id.control_card_submit)
    TextView controlCardSubmit;

    private GetToPostCheckAdapter mGetToPostCheckAdapter1;
    private GetToPostCheckAdapter mGetToPostCheckAdapter2;
    private List<GetToPostCheckBean> getToPostCheckBeans1 = new ArrayList<>();
    private List<GetToPostCheckBean> getToPostCheckBeans2 = new ArrayList<>();
    private List<PostCheckBean> posCtheckBean = new ArrayList<>();
    private String task_id = "aaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.get_to_post_check_activity);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        titleBack.setVisibility(View.VISIBLE);
        titleName.setText("到岗到位检查");

        mGetToPostCheckAdapter1 = new GetToPostCheckAdapter(this);
        getToPostLl.setAdapter(mGetToPostCheckAdapter1);

        mGetToPostCheckAdapter2 = new GetToPostCheckAdapter(this);
        safeLv.setAdapter(mGetToPostCheckAdapter2);

        getToPostCheck();
    }

    private void getToPostCheck() {
        getToPostCheckBeans1.clear();
        getToPostCheckBeans2.clear();
        BaseRequest.getInstance().getService()
                .getToPostCheck()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GetToPostCheckBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<GetToPostCheckBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<GetToPostCheckBean> getToPostCheckBeans = t.getResults();
                            for (int i = 0; i < getToPostCheckBeans.size(); i++) {
                                if (getToPostCheckBeans.get(i).getCheck_id().equals("1")) {
                                    getToPostCheckBeans1.add(getToPostCheckBeans.get(i));

                                } else if (getToPostCheckBeans.get(i).getCheck_id().equals("2")) {
                                    getToPostCheckBeans2.add(getToPostCheckBeans.get(i));

                                }
                            }

                            mGetToPostCheckAdapter1.setData(getToPostCheckBeans1);
                            mGetToPostCheckAdapter2.setData(getToPostCheckBeans2);

                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @OnClick({R.id.control_card_submit, R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.control_card_submit:
                posCtheckBean.clear();
                for (int i = 0; i < getToPostCheckBeans1.size(); i++) {
                    posCtheckBean.add(new PostCheckBean(getToPostCheckBeans1.get(i).getTask_position_id(), String.valueOf(getToPostCheckBeans1.get(i).getStatus()), task_id));
                }
                for (int i = 0; i < getToPostCheckBeans2.size(); i++) {
                    posCtheckBean.add(new PostCheckBean(getToPostCheckBeans2.get(i).getTask_position_id(), String.valueOf(getToPostCheckBeans2.get(i).getStatus()), task_id));
                }
                String json = new Gson().toJson(posCtheckBean);
                Log.d("TAG", json);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                ;
                BaseRequest.getInstance().getService().sendPostCheck(body).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            protected void onSuccees(BaseResult t) throws Exception {

                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }
                        });
                break;
        }
    }


}
