package com.patrol.terminal.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.FieldAntiInspectionAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.FieldAntiInspectionBean;
import com.patrol.terminal.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class FieldAntiInspectionActivity extends BaseActivity {
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
    @BindView(R.id.guanliweizhang_lv)
    NoScrollListView guanliweizhangLv;
    @BindView(R.id.xingweiweizhang_lv)
    NoScrollListView xingweiweizhangLv;
    @BindView(R.id.zhuagnzhiweizhang_lv)
    NoScrollListView zhuagnzhiweizhangLv;
    @BindView(R.id.control_card_submit)
    TextView controlCardSubmit;

    private FieldAntiInspectionAdapter mFieldAntiInspectionAdapter1;
    private FieldAntiInspectionAdapter mFieldAntiInspectionAdapter2;
    private FieldAntiInspectionAdapter mFieldAntiInspectionAdapter3;

    private List<FieldAntiInspectionBean> fieldAntiInspectionBeans1 = new ArrayList<>();
    private List<FieldAntiInspectionBean> fieldAntiInspectionBeans2 = new ArrayList<>();
    private List<FieldAntiInspectionBean> fieldAntiInspectionBeans3 = new ArrayList<>();

    private List<FieldAntiInspectionBean> fieldAntiInspectionBeans = new ArrayList<>();
    private String task_id = "aaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.field_antiinspectioin_activity);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        titleBack.setVisibility(View.VISIBLE);
        titleName.setText("现场反违章检查");

        mFieldAntiInspectionAdapter1 = new FieldAntiInspectionAdapter(this);
        guanliweizhangLv.setAdapter(mFieldAntiInspectionAdapter1);

        mFieldAntiInspectionAdapter2 = new FieldAntiInspectionAdapter(this);
        xingweiweizhangLv.setAdapter(mFieldAntiInspectionAdapter2);

        mFieldAntiInspectionAdapter3 = new FieldAntiInspectionAdapter(this);
        zhuagnzhiweizhangLv.setAdapter(mFieldAntiInspectionAdapter3);

        getFieldAntiInspection();
    }

    private void getFieldAntiInspection() {
        fieldAntiInspectionBeans1.clear();
        fieldAntiInspectionBeans2.clear();
        fieldAntiInspectionBeans3.clear();
        BaseRequest.getInstance().getService()
                .getFieldAntiInspection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<FieldAntiInspectionBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<FieldAntiInspectionBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<FieldAntiInspectionBean> fieldAntiInspectionBeans = t.getResults();
                            for (int i = 0; i < fieldAntiInspectionBeans.size(); i++) {
                                if (fieldAntiInspectionBeans.get(i).getIllegal().equals("1")) {
                                    fieldAntiInspectionBeans1.add(fieldAntiInspectionBeans.get(i));

                                } else if (fieldAntiInspectionBeans.get(i).getIllegal().equals("2")) {
                                    fieldAntiInspectionBeans2.add(fieldAntiInspectionBeans.get(i));

                                } else if (fieldAntiInspectionBeans.get(i).getIllegal().equals("3")) {
                                    fieldAntiInspectionBeans3.add(fieldAntiInspectionBeans.get(i));

                                }
                            }

                            mFieldAntiInspectionAdapter1.setData(fieldAntiInspectionBeans1);
                            mFieldAntiInspectionAdapter2.setData(fieldAntiInspectionBeans2);
                            mFieldAntiInspectionAdapter3.setData(fieldAntiInspectionBeans3);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @OnClick({R.id.title_back, R.id.control_card_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.control_card_submit:
                fieldAntiInspectionBeans.clear();
                for (int i = 0; i < fieldAntiInspectionBeans1.size(); i++) {
                    fieldAntiInspectionBeans.add(new FieldAntiInspectionBean(fieldAntiInspectionBeans1.get(i).getTask_illegal_id(), fieldAntiInspectionBeans1.get(i).getCheck_user() == null ? "x" : fieldAntiInspectionBeans1.get(i).getCheck_user(), task_id));
                }
                for (int i = 0; i < fieldAntiInspectionBeans2.size(); i++) {
                    fieldAntiInspectionBeans.add(new FieldAntiInspectionBean(fieldAntiInspectionBeans2.get(i).getTask_illegal_id(), fieldAntiInspectionBeans2.get(i).getCheck_user() == null ? "x" : fieldAntiInspectionBeans2.get(i).getCheck_user(), task_id));
                }
                for (int i = 0; i < fieldAntiInspectionBeans3.size(); i++) {
                    fieldAntiInspectionBeans.add(new FieldAntiInspectionBean(fieldAntiInspectionBeans3.get(i).getTask_illegal_id(), fieldAntiInspectionBeans3.get(i).getCheck_user() == null ? "x" : fieldAntiInspectionBeans3.get(i).getCheck_user(), task_id));
                }
                String json = new Gson().toJson(fieldAntiInspectionBeans);
                Log.d("TAG", json);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
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
