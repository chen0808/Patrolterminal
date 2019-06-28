package com.patrol.terminal.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectListAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DefectListBean;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DefectListActivity extends BaseActivity {
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
    @BindView(R.id.rv_defect)
    RecyclerView rvDefect;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_content)
    AutoCompleteTextView tvContent;
    @BindView(R.id.search_delete)
    ImageView searchDelete;
    private String category;
    private String search;
    private List<DefectListBean> defectList = new ArrayList<>();
    private List<DefectListBean> searchList = new ArrayList<>();
    private DefectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_list);
        ButterKnife.bind(this);
        titleName.setText("缺陷库列表");
        category = getIntent().getStringExtra("category");
        initData();
        search();
    }

    private void initData() {
        ProgressDialog.show(DefectListActivity.this, false, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getDefectList(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectListBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectListBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        defectList = t.getResults();
                        rvDefect.setLayoutManager(new LinearLayoutManager(DefectListActivity.this));
                        adapter = new DefectListAdapter(R.layout.item_defect, defectList);
                        rvDefect.setAdapter(adapter);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    private void search() {
        tvContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = s.toString();
                if (s.length() == 0) {
                    searchDelete.setVisibility(View.GONE);
                } else {
                    searchDelete.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                search = tvContent.getText().toString();
               /* if (delayRun != null) {
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }

                handler.postDelayed(delayRun, 1000);*/
                if (search.equals("")) {
                    adapter.setNewData(defectList);
                } else {
                    searchList.clear();
                    for (int i = 0; i < defectList.size(); i++) {
                        if (defectList.get(i).getContent().contains(search)) {
                            searchList.add(defectList.get(i));
                        }
                    }
                    adapter.setNewData(searchList);
                }
            }

        });
    }

    @OnClick({R.id.title_back, R.id.rv_defect, R.id.search_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.search_delete:
                tvContent.setText("");
                break;
        }
    }
}
