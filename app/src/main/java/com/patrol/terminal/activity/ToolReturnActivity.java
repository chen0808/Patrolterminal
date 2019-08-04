package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ToolReturnAdapter;
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
 * 工器具归还
 */
public class ToolReturnActivity extends BaseActivity {
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
    @BindView(R.id.btn_return)
    Button btn_return;
    @BindView(R.id.tool_status)
    TextView tool_status;


    private List<EqToolsOut> list = new ArrayList<>();
    private ToolReturnAdapter toolAdapter;
    private boolean isQuanxuan = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_guihuan);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        //isLook = getIntent().getBooleanExtra("is_look", false);  //是否为查看模式
        titleName.setText("工器具归还");
        tool_status.setText("全选");

        toolAdapter = new ToolReturnAdapter(this);
        toolRecordLv.setAdapter(toolAdapter);
        getToolRecordList();

    }


    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.btn_return)
    void saveReturn() {
        saveToolReturn();
    }

    @OnClick(R.id.tool_status)
    void quanxuan() {

        if (!isQuanxuan) {
            isQuanxuan = true;
            tool_status.setText("反选");
        } else {
            isQuanxuan = false;
            tool_status.setText("全选");
        }

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCheck(isQuanxuan);
        }
        toolAdapter.notifyDataSetChanged();
    }

    public void saveToolReturn() {
        ProgressDialog.show(this);
        List<EqToolsOut> returnList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isCheck() && list.get(i).getOut_status().equals("0")) {
                list.get(i).setOut_status("1");
                returnList.add(list.get(i));
            }
        }

        if (returnList.size() == 0) {
            Utils.showToast("请选择归还工器具");
            return;
        }

        BaseRequest.getInstance().getService()
                .getToolReturn(returnList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        if (t.isSuccess()) {
                            Utils.showToast("归还成功");
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

    public void getToolRecordList() {

        ProgressDialog.show(this);
        BaseRequest.getInstance().getService()
                .getToolRecordList(SPUtil.getUserId(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<EqToolsOut>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<EqToolsOut>> t) throws Exception {
                        if (t.isSuccess()) {

                            list = t.getResults();
                            toolAdapter.setData(list);
                            if (t.getResults().size() == 0) {
                                Utils.showToast("暂无可归还工器具");
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
