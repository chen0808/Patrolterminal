package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.EqToolsAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.EqToolsBean;
import com.patrol.terminal.widget.NoScrollListView;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*工器具台账*/
public class ControlCardTaizhangActivity extends BaseActivity {
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
    @BindView(R.id.control_card_div)
    NoScrollListView controlCardDiv;

    private List<EqToolsBean> eqToolsList;
    private EqToolsAdapter eqToolsAdapter;
    private int pageNum = 1;
    private int count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_card_taizhang);
        ButterKnife.bind(this);
        initview();
        getEqTools("", "");
    }

    private void initview() {
        titleName.setText("工器具台账");
        eqToolsAdapter = new EqToolsAdapter(this);
        controlCardDiv.setAdapter(eqToolsAdapter);
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    //获取工器具台账
    public void getEqTools(String search_name, String line_id) {
        ProgressDialog.show(this, true, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getEqTools()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<EqToolsBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<EqToolsBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            eqToolsList = t.getResults();
                            eqToolsAdapter.setData(eqToolsList);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
