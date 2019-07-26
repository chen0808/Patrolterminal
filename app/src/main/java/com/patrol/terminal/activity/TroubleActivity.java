package com.patrol.terminal.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectBanjiAdapter;
import com.patrol.terminal.adapter.DefectBanjiXLAdapter;
import com.patrol.terminal.adapter.TroubleAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.BanjiBean;
import com.patrol.terminal.bean.BanjiXLBean;
import com.patrol.terminal.bean.TroubleFragmentBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 隐患
 */
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
    @BindView(R.id.iv_yh_search)
    ImageView ivSearch;
    @BindView(R.id.yh_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.tv_yh_content)
    AutoCompleteTextView tvContent;
    @BindView(R.id.search_yh_delete)
    ImageView searchDelete;

    @BindView(R.id.btn_yh_sx)
    Button btn_sx;
    @BindView(R.id.yh_sx_save)
    Button qx_sx_save;
    @BindView(R.id.select_yh_banji)
    ListView lv_banji;
    @BindView(R.id.select_yh_xianlu)
    ListView lv_xianlu;
    @BindView(R.id.ly_sx)
    RelativeLayout ry_sx;

    private TroubleAdapter adapter;
    private int page_num = 1;
    private int page_size = 10;
    private List<TroubleFragmentBean> troubleList = new ArrayList<>();
    private List<TroubleFragmentBean> searchList = new ArrayList<>();
    private String search = "";
    private List<TroubleFragmentBean> defectdata = new ArrayList<>();
    private DefectBanjiAdapter banjiAdapter;
    private DefectBanjiXLAdapter banjixlAdapter;
    private List<BanjiBean> banjiList = new ArrayList<>();
    private List<BanjiXLBean> banjixlList = new ArrayList<>();
    private String line_id = "";

    private Handler handler = new Handler();
    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {
        @Override
        public void run() {
            page_num = 1;
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
        getAllBanji();
    }

    private void setDataToList(List<TroubleFragmentBean> beans) {
        if (page_num == 1) {
            adapter.setNewData(beans);
        } else {
            adapter.addData(beans);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (subscribe != null) {
//            subscribe.dispose();
//        }
    }

    @OnClick({R.id.title_back, R.id.iv_yh_search, R.id.search_yh_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_yh_search:

                break;
            case R.id.search_yh_delete:
                searchDelete.setVisibility(View.GONE);
                search = "";
                tvContent.setText("");
                break;
        }
    }

    //筛选线路查询
    @OnClick(R.id.yh_sx_save)
    void queryXLDefect() {
        ry_sx.setVisibility(View.GONE);
        tvContent.setText("");
        search = "";
        page_num = 1;
        troubleList.clear();
        line_id = banjixlAdapter.getSelectLine();
//        if(!TextUtils.isEmpty(line_id))
        getAllTrouble();
    }

    //缺陷筛选
    @OnClick(R.id.btn_yh_sx)
    void qxSelet() {
        if (ry_sx.getVisibility() == View.VISIBLE) {
            ry_sx.setVisibility(View.GONE);
        } else {
            ry_sx.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取隐患
     */
    private void getAllTrouble() {
        ProgressDialog.show(this, true, "正在加载...");
        BaseRequest.getInstance().getService()
                .getSelectDanger(page_num, page_size, line_id, search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleFragmentBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleFragmentBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            List<TroubleFragmentBean> result = t.getResults();
                            if (result != null && result.size() > 0 && result.size() == 10) {
                                planRv.loadMoreFinish(false, true);
                            } else {
                                planRv.loadMoreFinish(true, false);
                            }
                            troubleList.addAll(result);
                            setDataToList(result);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    /**
     * 获取班级列表
     */
    public void getAllBanji() {
        //班长和班员传参数 不传查所有的
        String depId = "";
        String mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains("xb_")) {
            depId = SPUtil.getDepId(this);
        }

        BaseRequest.getInstance().getService()
                .getAllBanji("SYS_DEP", "ID,name", "1", depId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<BanjiBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<BanjiBean>> t) throws Exception {
                        if (t.isSuccess()) {
                            List<BanjiBean> result = t.getResults();
                            if (result != null && result.size() > 0) {
                                banjiList.clear();
                                banjiList.addAll(result);
                                if (banjiList.size() > 0) {
                                    banjiAdapter.setIndexPosition(0);
                                    String depid = banjiList.get(0).getId();
                                    btn_sx.setText(banjiList.get(0).getName().substring(0, 2));
                                    getAllBanjiXL(depid);
                                } else
                                    banjiAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }


    /**
     * 获取班级线路
     */
    public void getAllBanjiXL(String depid) {
        BaseRequest.getInstance().getService()
                .getAllBanjiXL("EQ_LINE", "id,name,dep_id", depid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<BanjiXLBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<BanjiXLBean>> t) throws Exception {
                        if (t.isSuccess()) {
                            List<BanjiXLBean> result = t.getResults();
                            banjixlList.clear();
                            banjixlList.addAll(result);
                            banjixlAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(TroubleActivity.this, "获取线路失败请稍后再试", Toast.LENGTH_LONG).show();
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

        //班级
        banjiAdapter = new DefectBanjiAdapter(this, banjiList);
        lv_banji.setAdapter(banjiAdapter);
        banjixlAdapter = new DefectBanjiXLAdapter(this, banjixlList);
        lv_xianlu.setAdapter(banjixlAdapter);

        lv_banji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                banjiAdapter.setIndexPosition(position);
                btn_sx.setText(banjiList.get(position).getName().substring(0, 2));
                String depid = banjiList.get(position).getId();
                getAllBanjiXL(depid);
            }
        });

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
                if (TextUtils.isEmpty(search)) {
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
}
