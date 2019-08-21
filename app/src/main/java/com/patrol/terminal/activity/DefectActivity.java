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
import com.patrol.terminal.adapter.DefectIngAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.BanjiBean;
import com.patrol.terminal.bean.BanjiXLBean;
import com.patrol.terminal.bean.DefectFragmentBean;
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
 * 缺陷查询
 */
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
    @BindView(R.id.tv_qx_content)
    AutoCompleteTextView tvContent;
    @BindView(R.id.search_delete)
    ImageView searchDelete;

    @BindView(R.id.btn_sx)
    Button btn_sx;
    @BindView(R.id.qx_sx_save)
    Button qx_sx_save;
    @BindView(R.id.select_banji)
    ListView lv_banji;
    @BindView(R.id.select_xianlu)
    ListView lv_xianlu;
    @BindView(R.id.ly_sx)
    RelativeLayout ry_sx;

    private DefectIngAdapter groupTaskAdapter;
    private DefectBanjiAdapter banjiAdapter;
    private DefectBanjiXLAdapter banjixlAdapter;
    private String jobType;
    private int pageNum = 1;
    private int count = 10;
    private String search_name = "";
    private String line_name = "";
    private List<DefectFragmentBean> defectList = new ArrayList<>();
    private List<DefectFragmentBean> searchList = new ArrayList<>();
    private String dep_id;
    private List<BanjiBean> banjiList = new ArrayList<>();
    private List<BanjiXLBean> banjixlList = new ArrayList<>();
    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {
        @Override
        public void run() {
            pageNum = 1;
            getBanjiXLQx(search_name, line_name);
        }
    };

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
        getBanjiXLQx(search_name, line_name);
        getAllBanji();
    }

    /**
     * 获取班级列表
     */
    public void getAllBanji() {
        //班长和班员传参数  不传查所有的
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
                                    //默认加载第一条数据
                                    String depid = banjiList.get(0).getId();
                                    btn_sx.setText(banjiList.get(0).getName().substring(0, 2));
                                    getAllBanjiXL(depid);
                                    banjiAdapter.setIndexPosition(0);
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
                        Toast.makeText(DefectActivity.this, "获取线路失败请稍后再试", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setDataToList(List<DefectFragmentBean> beans) {
        if (pageNum == 1) {
            groupTaskAdapter.setNewData(beans);
        } else {
            groupTaskAdapter.addData(beans);
        }

    }

    private void initview() {
        titleName.setText("缺陷库");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        planRv.setLayoutManager(manager);
        //班级
        banjiAdapter = new DefectBanjiAdapter(this, banjiList);
        lv_banji.setAdapter(banjiAdapter);
        banjixlAdapter = new DefectBanjiXLAdapter(this, banjixlList);
        lv_xianlu.setAdapter(banjixlAdapter);
        groupTaskAdapter = new DefectIngAdapter(R.layout.fragment_defect_item, 0);
        planRv.setAdapter(groupTaskAdapter);

        planRv.useDefaultLoadMore();
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                getBanjiXLQx(search_name, line_name);
            }
        });
//        String data[] = getResources().getStringArray(R.array.auto_textview_contents);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DefectActivity.
//                this, android.R.layout.simple_dropdown_item_1line, data);
//        tvContent.setAdapter(adapter);
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
                    groupTaskAdapter.setNewData(defectList);
                } else {
                    searchList.clear();
                    for (int i = 0; i < defectList.size(); i++) {
                        if (defectList.get(i).getLine_name().contains(search_name) ||
                                defectList.get(i).getTower_name().contains(search_name)) {
                            searchList.add(defectList.get(i));
                        }
                    }
                    groupTaskAdapter.setNewData(searchList);
                }
            }
        });

        lv_banji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                banjiAdapter.setIndexPosition(position);
                btn_sx.setText(banjiList.get(position).getName().substring(0, 2));
                String depid = banjiList.get(position).getId();
                getAllBanjiXL(depid);
            }
        });


    }

    //筛选线路查询
    @OnClick(R.id.qx_sx_save)
    void queryXLDefect() {
        ry_sx.setVisibility(View.GONE);
        tvContent.setText("");
        search_name = "";
        pageNum = 1;
        line_name = banjixlAdapter.getSelectLine();
        defectList.clear();
//        if(!TextUtils.isEmpty(line_name))
        getBanjiXLQx(search_name, line_name);
    }

    //缺陷筛选
    @OnClick(R.id.btn_sx)
    public void qxSelet() {
        if (ry_sx.getVisibility() == View.VISIBLE) {
            ry_sx.setVisibility(View.GONE);
        } else {
            ry_sx.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (subscribe != null) {
//            subscribe.dispose();
//        }
    }

    //班级线路缺陷
    public void getBanjiXLQx(String search_name, String line_id) {
        ProgressDialog.show(DefectActivity.this, false, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getXLDefact(pageNum, count, line_id, search_name,"grade_sign desc,find_time desc", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectFragmentBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectFragmentBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            List<DefectFragmentBean> result = t.getResults();
                            if (result != null && result.size() > 0 && result.size() == 10) {
                                planRv.loadMoreFinish(false, true);
                            } else {
                                planRv.loadMoreFinish(true, false);
                            }
                            defectList.addAll(result);
                            setDataToList(result);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    private Handler handler = new Handler();

    @OnClick({R.id.title_back, R.id.search_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.search_delete:
                searchDelete.setVisibility(View.GONE);
                search_name = "";
                tvContent.setText("");
                break;
        }
    }


}
