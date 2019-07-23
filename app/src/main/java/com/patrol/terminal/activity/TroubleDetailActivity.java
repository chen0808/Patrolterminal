package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.AcrossAdapter;
import com.patrol.terminal.adapter.BirdAdapter;
import com.patrol.terminal.adapter.BreakAdapter;
import com.patrol.terminal.adapter.DisasterAdapter;
import com.patrol.terminal.adapter.FireAdapter;
import com.patrol.terminal.adapter.ThunderAdapter;
import com.patrol.terminal.adapter.WindAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.TroubleDetailBean;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 隐患详情
 */
public class TroubleDetailActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

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
    @BindView(R.id.btn_bird)
    RadioButton btnBird;
    @BindView(R.id.btn_break)
    RadioButton btnBreak;
    @BindView(R.id.btn_disaster)
    RadioButton btnDisaster;
    @BindView(R.id.btn_thunder)
    RadioButton btnThunder;
    @BindView(R.id.btn_wind)
    RadioButton btnWind;
    @BindView(R.id.btn_fire)
    RadioButton btnFire;
    @BindView(R.id.btn_across)
    RadioButton btnAcross;
    @BindView(R.id.rv_trouble)
    RecyclerView rvTrouble;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    private String lineId;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_detail);
        ButterKnife.bind(this);
        lineId = getIntent().getStringExtra("line_id");
        type = getIntent().getStringExtra("type");
        rgTab.check(rgTab.getChildAt(0).getId());
        String[] array = getResources().getStringArray(R.array.trouble_type);
        for (int i = 0; i < array.length; i++) {
            if (type != null && type.equals(array[i])) {
                if (i > 3) {
                    rgTab.check(rgTab.getChildAt(i - 2).getId());
                }
            }
        }
        getAcross();
        rgTab.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == rgTab.getChildAt(0).getId()) {
            getAcross();
        } else if (checkedId == rgTab.getChildAt(1).getId()) {
            getBird();
        } else if (checkedId == rgTab.getChildAt(2).getId()) {
            getThunder();
        } else if (checkedId == rgTab.getChildAt(3).getId()) {
            getWind();
        } else if (checkedId == rgTab.getChildAt(4).getId()) {
            getFire();
        } else if (checkedId == rgTab.getChildAt(5).getId()) {
            getBreak();
        } else if (checkedId == rgTab.getChildAt(6).getId()) {
            getDisaster();
        }
    }

    @OnClick({R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    private void getBird() {
        ProgressDialog.show(this, true, "正在加载...");
        BaseRequest.getInstance().getService()
                .getBird(lineId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleDetailBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleDetailBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            List<TroubleDetailBean> result = t.getResults();
                            rvTrouble.setLayoutManager(new LinearLayoutManager(TroubleDetailActivity.this));
                            BirdAdapter adapter = new BirdAdapter(R.layout.item_bird, result);
                            rvTrouble.setAdapter(adapter);
                            adapter.setEmptyView(R.layout.empty, rvTrouble);
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    private void getBreak() {
        ProgressDialog.show(this, true, "正在加载...");
        BaseRequest.getInstance().getService()
                .getBreak(lineId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleDetailBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleDetailBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            List<TroubleDetailBean> result = t.getResults();
                            rvTrouble.setLayoutManager(new LinearLayoutManager(TroubleDetailActivity.this));
                            BreakAdapter adapter = new BreakAdapter(R.layout.item_break, result);
                            rvTrouble.setAdapter(adapter);
                            adapter.setEmptyView(R.layout.empty, rvTrouble);
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    private void getDisaster() {
        ProgressDialog.show(this, true, "正在加载...");
        BaseRequest.getInstance().getService()
                .getDisaster(lineId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleDetailBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleDetailBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            List<TroubleDetailBean> result = t.getResults();
                            rvTrouble.setLayoutManager(new LinearLayoutManager(TroubleDetailActivity.this));
                            DisasterAdapter adapter = new DisasterAdapter(R.layout.item_disaster, result);
                            rvTrouble.setAdapter(adapter);
                            adapter.setEmptyView(R.layout.empty, rvTrouble);
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    private void getThunder() {
        ProgressDialog.show(this, true, "正在加载...");
        BaseRequest.getInstance().getService()
                .getThunder(lineId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleDetailBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleDetailBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            List<TroubleDetailBean> result = t.getResults();
                            rvTrouble.setLayoutManager(new LinearLayoutManager(TroubleDetailActivity.this));
                            ThunderAdapter adapter = new ThunderAdapter(R.layout.item_thunder, result);
                            rvTrouble.setAdapter(adapter);
                            adapter.setEmptyView(R.layout.empty, rvTrouble);
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    private void getWind() {
        ProgressDialog.show(this, true, "正在加载...");
        BaseRequest.getInstance().getService()
                .getWind(lineId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleDetailBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleDetailBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            List<TroubleDetailBean> result = t.getResults();
                            rvTrouble.setLayoutManager(new LinearLayoutManager(TroubleDetailActivity.this));
                            WindAdapter adapter = new WindAdapter(R.layout.item_wind, result);
                            rvTrouble.setAdapter(adapter);
                            adapter.setEmptyView(R.layout.empty, rvTrouble);
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    private void getFire() {
        ProgressDialog.show(this, true, "正在加载...");
        BaseRequest.getInstance().getService()
                .getFire(lineId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleDetailBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleDetailBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            List<TroubleDetailBean> result = t.getResults();
                            rvTrouble.setLayoutManager(new LinearLayoutManager(TroubleDetailActivity.this));
                            FireAdapter adapter = new FireAdapter(R.layout.item_fire, result);
                            rvTrouble.setAdapter(adapter);
                            adapter.setEmptyView(R.layout.empty, rvTrouble);
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    private void getAcross() {
        ProgressDialog.show(this, true, "正在加载...");
        BaseRequest.getInstance().getService()
                .getAcross(lineId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleDetailBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleDetailBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            List<TroubleDetailBean> result = t.getResults();
                            rvTrouble.setLayoutManager(new LinearLayoutManager(TroubleDetailActivity.this));
                            AcrossAdapter adapter = new AcrossAdapter(R.layout.item_across, result);
                            rvTrouble.setAdapter(adapter);
                            adapter.setEmptyView(R.layout.empty, rvTrouble);
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }
}
