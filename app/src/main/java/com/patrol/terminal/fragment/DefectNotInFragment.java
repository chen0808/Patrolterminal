package com.patrol.terminal.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.patrol.terminal.activity.ReviewTaskActivity;
import com.patrol.terminal.adapter.DefectBanjiAdapter;
import com.patrol.terminal.adapter.DefectBanjiXLAdapter;
import com.patrol.terminal.adapter.DefectIngTabAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.BanjiBean;
import com.patrol.terminal.bean.BanjiXLBean;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.patrol.terminal.bean.InAuditPostBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialogNew;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//缺陷未入库
public class DefectNotInFragment extends BaseFragment {
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.tv_qx_content)
    AutoCompleteTextView tvContent;
    @BindView(R.id.search_delete)
    ImageView searchDelete;

    @BindView(R.id.defect_filter)
    TextView defectFilter;
    @BindView(R.id.qx_sx_save)
    Button qx_sx_save;
    @BindView(R.id.select_banji)
    ListView lv_banji;
    @BindView(R.id.select_xianlu)
    ListView lv_xianlu;
    @BindView(R.id.ly_sx)
    RelativeLayout ry_sx;

    private DefectIngTabAdapter groupTaskAdapter;
    private DefectBanjiAdapter banjiAdapter;
    private DefectBanjiXLAdapter banjixlAdapter;
    private int pageNum = 1;
    private int count = 10;
    private String search_name = "";
    private String line_name = "";
    private List<DefectFragmentBean> defectList = new ArrayList<>();
    private List<DefectFragmentBean> searchList = new ArrayList<>();
    private String dep_id;
    private List<BanjiBean> banjiList = new ArrayList<>();
    private List<BanjiXLBean> banjixlList = new ArrayList<>();
    private String mJobType;
    private Context mContext;
    private String year;
    private String month;
    private String day;
    private int curPosition = -1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_defect_not_in, null);
        return view;
    }

    @Override
    protected void initData() {
        mContext = getActivity();

        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        initdate(time);

        mJobType = SPUtil.getString(mContext, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        dep_id = SPUtil.getDepId(mContext);
        if ((mJobType.contains("_zz") || mJobType.contains("_zg")) && !mJobType.contains("b_zz")) {
            dep_id = null;
        }

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        planRv.setLayoutManager(manager);
        planRv.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));

        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                int width = getResources().getDimensionPixelOffset(R.dimen.dp_70);
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                SwipeMenuItem addItem;
                if(defectList.get(viewType).getIn_status().equals("2") && mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)){
                    // 注意：哪边不想要菜单，那么不要添加即可。
                    addItem = new SwipeMenuItem(mContext)
                            .setBackground(R.drawable.swip_menu_item_1)
                            .setText("驳回")
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

                    addItem = new SwipeMenuItem(mContext)
                            .setBackground(R.drawable.swip_menu_item_2)
                            .setText("复核")
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);
                    rightMenu.addMenuItem(addItem);
                } else if (defectList.get(viewType).getIn_status().equals("1") && mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
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
//                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
//                Toast.makeText(mContext, direction + " " + adapterPosition + " " + menuPosition, Toast.LENGTH_SHORT).show();

                if(mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)){
                    switch (menuPosition) {
                        case 0:
                            submit("4", adapterPosition);
                            break;
                        case 1:
                            submit("3", adapterPosition);
                            break;
                        case 2:
                            curPosition = adapterPosition;
                            Intent intent2 = new Intent(mContext, ReviewTaskActivity.class);
                            intent2.putExtra("id", defectList.get(adapterPosition).getId());
                            startActivityForResult(intent2, 10);
                            break;
                        case 3:
                            break;
                        default:
                            break;
                    }
                } else if(mJobType.contains(Constant.RUNNING_SQUAD_LEADER)){
                    switch (menuPosition) {
                        case 0:
                            submit("4", adapterPosition);
                            break;
                        case 1:
                            submit("2", adapterPosition);
                            break;
                        default:
                            break;
                    }
                }
            }
        };

        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mMenuItemClickListener);

        //班级
        banjiAdapter = new DefectBanjiAdapter(mContext, banjiList);
        lv_banji.setAdapter(banjiAdapter);
        banjixlAdapter = new DefectBanjiXLAdapter(mContext, banjixlList);
        lv_xianlu.setAdapter(banjixlAdapter);
        groupTaskAdapter = new DefectIngTabAdapter(R.layout.fragment_defect_not_in_item, 1);
        planRv.setAdapter(groupTaskAdapter);
        planRv.useDefaultLoadMore();
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                getBanjiXLQx(search_name, line_name);
            }
        });

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
//                defectFilter.setText(banjiList.get(position).getName().substring(0, 2));
                String depid = banjiList.get(position).getId();
                getAllBanjiXL(depid);
            }
        });

        getBanjiXLQx(search_name, line_name);
        getAllBanji();
    }

    /**
     * 获取班级列表
     */
    public void getAllBanji() {
        //班长和班员传参数  不传查所有的
        String depId = "";
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
                                    //默认加载第一条数据
                                    String depid = banjiList.get(0).getId();
//                                    defectFilter.setText(banjiList.get(0).getName().substring(0, 2));
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

    private void setDataToList(List<DefectFragmentBean> beans) {
        if (pageNum == 1) {
            groupTaskAdapter.setNewData(beans);
        } else {
            groupTaskAdapter.addData(beans);
        }
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
    @OnClick(R.id.defect_filter)
    public void qxSelet() {
        if (ry_sx.getVisibility() == View.VISIBLE) {
            ry_sx.setVisibility(View.GONE);
        } else {
            ry_sx.setVisibility(View.VISIBLE);
        }
    }

    //班级线路缺陷
    public void getBanjiXLQx(String search_name, String line_id) {
//        ProgressDialog.show(mContext, true, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getXLDefact(pageNum, count, line_id, search_name,"grade_sign desc,find_time desc", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectFragmentBean>>(mContext) {
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

    @OnClick({R.id.search_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_delete:
                searchDelete.setVisibility(View.GONE);
                search_name = "";
                tvContent.setText("");
            break;
        }
    }

    //提交缺陷审核
    public void submit(String in_status, int adapterPosition) {
        if(in_status.equals("4")){
            CancelOrOkDialogNew dialog = new CancelOrOkDialogNew(mContext, "驳回", "取消", "确定") {
                @Override
                public void ok() {
                    super.ok();
                    inAuditPOST("4", adapterPosition);
                }

                @Override
                public void cancle() {
                    super.cancle();
                }
            };
            dialog.show();
        } else {
            if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
                CancelOrOkDialogNew dialog = new CancelOrOkDialogNew(mContext, "入库", "取消", "确定") {
                    @Override
                    public void ok() {
                        super.ok();
                        inAuditPOST("3", adapterPosition);
                    }

                    @Override
                    public void cancle() {
                        super.cancle();
                    }
                };
                dialog.show();
            } else {
                CancelOrOkDialogNew dialog = new CancelOrOkDialogNew(mContext, "通过", "取消", "确定") {
                    @Override
                    public void ok() {
                        super.ok();
                        inAuditPOST("2", adapterPosition);
                    }

                    @Override
                    public void cancle() {
                        super.cancle();
                    }
                };
                dialog.show();
            }
        }
    }

    public void inAuditPOST(String in_status, int adapterPosition) {
        ProgressDialog.show(mContext, false, "正在加载。。。。");
        InAuditPostBean inAuditPostBean = new InAuditPostBean();
        inAuditPostBean.setId(defectList.get(adapterPosition).getId());
        inAuditPostBean.setIn_status(in_status);
        inAuditPostBean.setFrom_user_id(SPUtil.getUserId(mContext));
        inAuditPostBean.setFrom_user_name(SPUtil.getUserName(mContext));
        inAuditPostBean.setLine_name(defectList.get(adapterPosition).getLine_name());
        inAuditPostBean.setTower_name(defectList.get(adapterPosition).getTower_name());
        if(in_status.equals("3")){
            inAuditPostBean.setClose_time(year + "-" + month + "-" + day);
        }
        BaseRequest.getInstance().getService()
                .inAuditPOST(inAuditPostBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(mContext) {

                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if(t.getCode() == 1){
                            defectList.get(adapterPosition).setIn_status(in_status);
                            groupTaskAdapter.notifyItemChanged(adapterPosition);

//                            tvContent.setText("");
//                            search_name = "";
//                            pageNum = 1;
//                            line_name = banjixlAdapter.getSelectLine();
//                            defectList.clear();
//                            getBanjiXLQx(search_name, line_name);
                            Toast.makeText(mContext,"处理完成",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }

    //初始化日期
    public void initdate(String time) {
        String[] times = time.split("年");
        String[] months = times[1].split("月");
        year = times[0];
        month = months[0];
        day = months[1].split("日")[0];
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1101) {
            switch (requestCode) {
                case 10:
                    if(curPosition != -1){
                        defectList.get(curPosition).setIn_status("5");
                        groupTaskAdapter.notifyItemChanged(curPosition);
                        curPosition = -1;
                    }
                    break;
            }
        }
    }
}
