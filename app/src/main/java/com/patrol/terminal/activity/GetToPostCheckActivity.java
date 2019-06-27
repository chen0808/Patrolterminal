package com.patrol.terminal.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GetToPostCheckBean;
import com.patrol.terminal.bean.PostCheckBean;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

//到岗位置检查
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
    LinearLayout workTaskLv;
    @BindView(R.id.safe_lv)
    LinearLayout safeLv;
    @BindView(R.id.control_card_submit)
    TextView controlCardSubmit;

    private List<PostCheckBean> posCtheckBean = new ArrayList<>();
    private String task_id = "";
    private List<GetToPostCheckBean> getToPostCheckBeans;
    private String audit_status;

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
        task_id = getIntent().getStringExtra("task_id");
        audit_status = getIntent().getStringExtra("audit_status");

        if ("0".equals(audit_status)||"4".equals(audit_status)){
            controlCardSubmit.setVisibility(View.VISIBLE);
        }else {
            controlCardSubmit.setVisibility(View.GONE);

        }
        getToPostCheck();
    }

    private void getToPostCheck() {
        ProgressDialog.show(this,false,"正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getToPostCheck(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GetToPostCheckBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<GetToPostCheckBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            getToPostCheckBeans = t.getResults();
                            for (int i = 0; i < getToPostCheckBeans.size(); i++) {
                                GetToPostCheckBean getToPostCheckBean = getToPostCheckBeans.get(i);
                                View view=View.inflate(GetToPostCheckActivity.this,R.layout.item_get_to_post_listview,null);
                                TextView mContentTv = view.findViewById(R.id.get_to_post_content_tv);
                                CheckBox mCb = view.findViewById(R.id.get_to_post_content_cb);
                                if ("1".equals(getToPostCheckBean.getStatus())){
                                    mCb.setChecked(true);
                                }else if ("0".equals(getToPostCheckBean.getStatus())){
                                    mCb.setChecked(false);
                                }
                               mCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                   @Override
                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                       if (isChecked){
                                           getToPostCheckBean.setStatus("1");
                                       }else {
                                           getToPostCheckBean.setStatus("0");
                                       }
                                   }
                               });
                                mContentTv.setText(getToPostCheckBean.getCheck_content());


                                if (getToPostCheckBeans.get(i).getCheck_id().equals("1")) {

                                    workTaskLv.addView(view);
                                } else if (getToPostCheckBeans.get(i).getCheck_id().equals("2")) {
                                    safeLv.addView(view);

                                }
                            }
                            ProgressDialog.cancle();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
            ProgressDialog.cancle();
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
                for (int i = 0; i < getToPostCheckBeans.size(); i++) {
                    posCtheckBean.add(new PostCheckBean(getToPostCheckBeans.get(i).getTask_position_id(), getToPostCheckBeans.get(i).getStatus(), task_id));
                }
                String json = new Gson().toJson(posCtheckBean);
                Log.d("TAG", json);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                BaseRequest.getInstance().getService().sendPostCheck(body).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            protected void onSuccees(BaseResult t) throws Exception {
                                if (t.getCode() == 1) {
                                    Toast.makeText(GetToPostCheckActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                    finish();
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
