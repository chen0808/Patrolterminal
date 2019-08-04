package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ToolRecordAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.EqToolsOut;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 领用记录查询
 */
public class ToolRecordListActivity extends BaseActivity {
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
    @BindView(R.id.toolRecordLv)
    ListView toolRecordLv;

    private List<EqToolsOut> list = new ArrayList<>();
    private ToolRecordAdapter toolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_record);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        //isLook = getIntent().getBooleanExtra("is_look", false);  //是否为查看模式
        titleName.setText("领用记录");
        toolAdapter = new ToolRecordAdapter(this);
        toolRecordLv.setAdapter(toolAdapter);
        getToolRecordList();

    }


    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }


    public void getToolRecordList() {
        BaseRequest.getInstance().getService()
                .getToolRecordList(SPUtil.getUserId(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<EqToolsOut>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<EqToolsOut>> t) throws Exception {
                        if (t.isSuccess()) {

                            toolAdapter.setData(t.getResults());
                            if (t.getResults().size() == 0) {
                                Utils.showToast("暂无领用记录");
                            }

                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

}
