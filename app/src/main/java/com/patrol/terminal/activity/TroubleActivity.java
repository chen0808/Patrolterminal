package com.patrol.terminal.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.TroubleAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.TroubleFragmentBean;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TroubleActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @BindView(R.id.title_setting_tv)
    TextView titleSettingTv;
    @BindView(R.id.title_setting)
    RelativeLayout titleSetting;
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.tv_content)
    AutoCompleteTextView tvContent;
    @BindView(R.id.search_delete)
    ImageView searchDelete;
    private TroubleAdapter adapter;
    private int page_num = 1;
    private int page_size = 10;
    private List<TroubleFragmentBean> troubleList = new ArrayList<>();
    private List<TroubleFragmentBean> searchList = new ArrayList<>();
    private List<String> lineList = new ArrayList<>();
    private String search;
    private List<TroubleFragmentBean> defectdata = new ArrayList<>();
    //private List<DefectDetailBean> result = new ArrayList<>();
    private Handler handler = new Handler();
    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
//            getSearchResult(editString, "all", 1, "true");
            page_num = 1;
            adapter.setNewData(troubleList);
            getAllTrouble();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble);
        ButterKnife.bind(this);
        initview();
        getAllTrouble();
        search();
    }

    private void setDataToList(List<TroubleFragmentBean> beans) {
        if (page_num == 1) {
            adapter.setNewData(beans);
        } else {
            adapter.addData(beans);
        }
    }

    private void search() {
//        String data[] = getResources().getStringArray(R.array.auto_textview_contents);
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(TroubleActivity.
//                this, android.R.layout.simple_dropdown_item_1line, lineList);
//        tvContent.setAdapter(adapter1);
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
                    adapter.setNewData(troubleList);
                } else {
                    searchList.clear();
                    for (int i = 0; i < troubleList.size(); i++) {
                        if (troubleList.get(i).getLine_name().contains(search)) {
                            searchList.add(troubleList.get(i));
                        }
                    }
                    adapter.setNewData(searchList);
                }
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (subscribe != null) {
//            subscribe.dispose();
//        }
    }

    @OnClick({R.id.title_back, R.id.iv_search, R.id.search_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_search:

                break;
            case R.id.search_delete:
                searchDelete.setVisibility(View.GONE);
                search = "";
                tvContent.setText("");
                break;
        }
    }

    private void getAllTrouble() {
        ProgressDialog.show(this, true, "正在加载...");
        BaseRequest.getInstance().getService()
                .getAllDanger(page_num, page_size, "线")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleFragmentBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleFragmentBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            List<TroubleFragmentBean> result = t.getResults();
                            if (result != null && result.size() > 0) {
                                planRv.loadMoreFinish(false, true);
                                troubleList.addAll(result);
                                setDataToList(result);
                                for (int i = 0; i < result.size(); i++) {
                                    lineList.add(result.get(i).getLine_name());
                                }
                            } else {
                                planRv.loadMoreFinish(true, false);
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void initview() {
        titleName.setText("隐患查询");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        planRv.setLayoutManager(manager);
        adapter = new TroubleAdapter(R.layout.fragment_defect_item, defectdata);
        planRv.setAdapter(adapter);
        planRv.useDefaultLoadMore();
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                page_num++;
                getAllTrouble();
            }
        });
    }
}
