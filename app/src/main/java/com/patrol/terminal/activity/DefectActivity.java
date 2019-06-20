package com.patrol.terminal.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectIngAdapter;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DefectActivity extends BaseActivity {
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
    private MyFragmentPagerAdapter taskPagerAdapter;
    private DefectIngAdapter groupTaskAdapter;
    private String jobType;
    private int pageNum = 1;
    private int count = 10;
    private String search = "";
    private long time = 0;
  private List<DefectFragmentBean> defectdata=new ArrayList<>();
    private String dep_id;
    //private List<DefectDetailBean> mDefectDetailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect);
        ButterKnife.bind(this);
        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        dep_id = SPUtil.getDepId(this);
        if ((jobType.contains("_zz") || jobType.contains("_zg")) && !jobType.contains("b_zz")) {
            dep_id = null;
        }
        initview();
        getAllDef();
    }

    private void getAllDef() {

        ProgressDialog.show(DefectActivity.this, false, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getAllDefact(pageNum, count, search, "find_time desc,start_name")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectFragmentBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectFragmentBean>> t) throws Exception {
                        ProgressDialog.cancle();

                        if (t.getCode() == 1) {
                            List<DefectFragmentBean> result = t.getResults();
                            if (result != null && result.size() > 0) {
                                planRv.loadMoreFinish(false, true);
                                setDataToList(result);
                            }else {
                                planRv.loadMoreFinish(true, false);
                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }



    private void setDataToList(List<DefectFragmentBean> beans) {

//        for (int i = 0; i < 10; i++) {
//            result.add(i, new DefectDetailBean("461faasdgarea466", "巡视" + i + "号杆塔", String.valueOf(i % 2), "巡视类型", "2019-04-15",
//                    "备注:备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注",
//                    "检测人", "工作负责人", "工作班组", "是否确认", "确认审核员id", "杆塔名"));
//        }
        if (pageNum==1){
            groupTaskAdapter.setNewData(beans);
        }else {
            groupTaskAdapter.addData(beans);
        }

    }

    private void initview() {
        titleName.setText("缺陷查询");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        planRv.setLayoutManager(manager);
        groupTaskAdapter = new DefectIngAdapter(R.layout.fragment_defect_item, defectdata, 0);
        planRv.setAdapter(groupTaskAdapter);
        planRv.useDefaultLoadMore();
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                getAllDef();
            }
        });
        String data[] = getResources().getStringArray(R.array.auto_textview_contents);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DefectActivity.
                this, android.R.layout.simple_dropdown_item_1line, data);
        tvContent.setAdapter(adapter);
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
                search=tvContent.getText().toString();
                if (delayRun != null) {
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }

                handler.postDelayed(delayRun, 1000);

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

    @OnClick({R.id.title_back, R.id.search_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.search_delete:
                searchDelete.setVisibility(View.GONE);
                search = "";
                tvContent.setText("");
                pageNum=1;
                groupTaskAdapter.setNewData(defectdata);
                getAllDef();
                break;
        }
    }

    private Handler handler = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
//            getSearchResult(editString, "all", 1, "true");
               pageNum=1;
            groupTaskAdapter.setNewData(defectdata);
               getAllDef();
        }
    };


}
