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
import com.patrol.terminal.adapter.EqToolsAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.EqToolsBean;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
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
    @BindView(R.id.tv_qx_content)
    AutoCompleteTextView tvContent;
    @BindView(R.id.search_delete)
    ImageView searchDelete;
    @BindView(R.id.control_card_div)
    SwipeRecyclerView controlCardDiv;

    private List<EqToolsBean> eqToolsList = new ArrayList<>();;
    private List<EqToolsBean> searchList = new ArrayList<>();
    private EqToolsAdapter eqToolsAdapter;
    private String search_name = "";
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
        LinearLayoutManager manager = new LinearLayoutManager(this);
        controlCardDiv.setLayoutManager(manager);
        eqToolsAdapter = new EqToolsAdapter(R.layout.item_eq_tool_division);
        controlCardDiv.setAdapter(eqToolsAdapter);

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
                    eqToolsAdapter.setNewData(eqToolsList);
                } else {
                    searchList.clear();
                    for (int i = 0; i < eqToolsList.size(); i++) {
                        if (eqToolsList.get(i).getName().contains(search_name)) {
                            searchList.add(eqToolsList.get(i));
                        }
                    }
                    eqToolsAdapter.setNewData(searchList);
                }
            }
        });
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    //获取工器具台账
    public void getEqTools(String search_name, String line_id) {
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
                            eqToolsAdapter.addData(eqToolsList);
                            eqToolsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
