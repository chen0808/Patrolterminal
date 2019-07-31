package com.patrol.terminal.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.patrol.terminal.adapter.TroubleTabAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.BanjiBean;
import com.patrol.terminal.bean.BanjiXLBean;
import com.patrol.terminal.bean.TroubleFragmentBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//隐患库已消缺
public class TroubleIsRidFragment extends BaseFragment {
    @BindView(R.id.iv_yh_search)
    ImageView ivSearch;
    @BindView(R.id.yh_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.tv_yh_content)
    AutoCompleteTextView tvContent;
    @BindView(R.id.search_yh_delete)
    ImageView searchDelete;
    @BindView(R.id.trouble_filter)
    TextView troubleFilter;
    @BindView(R.id.yh_sx_save)
    Button qx_sx_save;
    @BindView(R.id.select_yh_banji)
    ListView lv_banji;
    @BindView(R.id.select_yh_xianlu)
    ListView lv_xianlu;
    @BindView(R.id.ly_sx)
    RelativeLayout ry_sx;

    private TroubleTabAdapter adapter;
    private int page_num = 1;
    private int page_size = 10;
    private List<TroubleFragmentBean> troubleList = new ArrayList<>();
    private List<TroubleFragmentBean> searchList = new ArrayList<>();
    private String search = "";
    private DefectBanjiAdapter banjiAdapter;
    private DefectBanjiXLAdapter banjixlAdapter;
    private List<BanjiBean> banjiList = new ArrayList<>();
    private List<BanjiXLBean> banjixlList = new ArrayList<>();
    private String line_id = "";
    private String mJobType;
    private Context mContext;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trouble_not_in, null);
        return view;
    }

    @Override
    protected void initData() {
        mContext = getActivity();

        mJobType = SPUtil.getString(mContext, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        planRv.setLayoutManager(manager);
        planRv.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));

        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                int width = getResources().getDimensionPixelOffset(R.dimen.dp_70);
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                // 注意：哪边不想要菜单，那么不要添加即可。
                SwipeMenuItem addItem;
                if(troubleList.get(viewType).getIn_status().equals("2") && mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)){
                    // 注意：哪边不想要菜单，那么不要添加即可。
                    addItem = new SwipeMenuItem(mContext)
                            .setBackground(R.drawable.swip_menu_item_1)
                            .setText("驳回")
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);
                    rightMenu.addMenuItem(addItem);

                    addItem = new SwipeMenuItem(mContext)
                            .setBackground(R.drawable.swip_menu_item_2)
                            .setText("复核")
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);
                    rightMenu.addMenuItem(addItem);

                    addItem = new SwipeMenuItem(mContext)
                            .setBackground(R.drawable.swip_menu_item_3)
                            .setText("入库")
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);
                    rightMenu.addMenuItem(addItem);
                } else if (troubleList.get(viewType).getIn_status().equals("1") && mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                    addItem = new SwipeMenuItem(mContext)
                            .setBackground(R.drawable.swip_menu_item_1)
                            .setText("驳回")
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);
                    rightMenu.addMenuItem(addItem);

                    addItem = new SwipeMenuItem(mContext)
                            .setBackground(R.drawable.swip_menu_item_3)
                            .setText("通过")
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);

                    rightMenu.addMenuItem(addItem);
                }
            }
        };

        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);

        OnItemMenuClickListener mMenuItemClickListener = new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                Toast.makeText(mContext, direction + " " + adapterPosition + " " + menuPosition, Toast.LENGTH_SHORT).show();
            }
        };

        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mMenuItemClickListener);

        adapter = new TroubleTabAdapter(R.layout.fragment_trouble_not_in_item, 3);
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
        banjiAdapter = new DefectBanjiAdapter(mContext, banjiList);
        lv_banji.setAdapter(banjiAdapter);
        banjixlAdapter = new DefectBanjiXLAdapter(mContext, banjixlList);
        lv_xianlu.setAdapter(banjixlAdapter);

        lv_banji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                banjiAdapter.setIndexPosition(position);
//                troubleFilter.setText(banjiList.get(position).getName().substring(0, 2));
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

    @OnClick({R.id.search_yh_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
        getAllTrouble();
    }

    //缺陷筛选
    @OnClick(R.id.trouble_filter)
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
//        ProgressDialog.show(mContext, true, "正在加载...");
        BaseRequest.getInstance().getService()
                .getSelectDanger(page_num, page_size, line_id, search, "grade_sign desc,find_time desc", "3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleFragmentBean>>(mContext) {
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
        mJobType = SPUtil.getString(mContext, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains("xb_")) {
            depId = SPUtil.getDepId(mContext);
        }

        BaseRequest.getInstance().getService()
                .getAllBanji("SYS_DEP", "ID,name", "1", depId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<BanjiBean>>(mContext) {
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
//                                    troubleFilter.setText(banjiList.get(0).getName().substring(0, 2));
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
                .subscribe(new BaseObserver<List<BanjiXLBean>>(mContext) {
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
                        Toast.makeText(mContext, "获取线路失败请稍后再试", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
