package com.patrol.terminal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.WorkingLogAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.WorkingLogBean;
import com.patrol.terminal.utils.Utils;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//施工方日志
public class ConstructionSideActivity extends BaseActivity {

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
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;

    private int logType = 0;
    private Context mContext;
    private WorkingLogAdapter workingLogAdapter;
    private List<WorkingLogBean> workingLogList = new ArrayList<>();
    private List<WorkingLogBean> searchList = new ArrayList<>();
    private String search_name = "";
    private int pageNum = 1;
    private int count = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_side);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        mContext = this;

        Intent intent = getIntent();
        logType = intent.getIntExtra("logType", 0);
        switch (logType) {
            case 1:
                titleName.setText("施工方日志");
                break;
            case 2:
                titleName.setText("监理方日志");
                break;
            case 3:
                titleName.setText("建设方日志");
                break;
            default:
                break;
        }

        titleSetting.setVisibility(View.VISIBLE);
        titleSettingIv.setImageResource(R.mipmap.add_white);
        titleSettingTv.setText("");

//        workingLogList.clear();
//        workingLogList.addAll(LocalWorkingLogBean.getWorkingLogList(logType + ""));

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
                addItem = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.swip_menu_item_1)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                rightMenu.addMenuItem(addItem);
            }
        };

        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);

        OnItemMenuClickListener mMenuItemClickListener = new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();

                deleteLog(workingLogList.get(adapterPosition).getId(), adapterPosition);
            }
        };

        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mMenuItemClickListener);

        workingLogAdapter = new WorkingLogAdapter(R.layout.item_working_log, logType);
        planRv.setAdapter(workingLogAdapter);
        planRv.useDefaultLoadMore();
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                getLogList(search_name);
            }
        });

        workingLogAdapter.setNewData(workingLogList);

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
                    workingLogAdapter.setNewData(workingLogList);
                } else {
                    searchList.clear();
                    for (int i = 0; i < workingLogList.size(); i++) {
                        if (workingLogList.get(i).getTemp_project_name().contains(search_name)) {
                            searchList.add(workingLogList.get(i));
                        }
                    }
                    workingLogAdapter.setNewData(searchList);
                }
            }
        });

        getLogList(search_name);
    }

    //获取施工日志列表
    public void getLogList(String search_name) {
        ProgressDialog.show(mContext, true, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getLogList(pageNum, count, "created_date desc", search_name, logType + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WorkingLogBean>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<WorkingLogBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            List<WorkingLogBean> result = t.getResults();
                            if (result != null && result.size() > 0 && result.size() == 10) {
                                planRv.loadMoreFinish(false, true);
                            } else {
                                planRv.loadMoreFinish(true, false);
                            }
                            workingLogList.clear();
                            workingLogList.addAll(result);
                            setDataToList(result);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }
                });
    }

    //删除施工日志
    public void deleteLog(String id, int position) {
        ProgressDialog.show(mContext, true, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .deleteLog(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            workingLogList.remove(position);
                            workingLogAdapter.setNewData(workingLogList);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }
                });
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.search_delete})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                intent = new Intent(this, WorkingLogDetailActivity.class);
                intent.putExtra("logType", logType);
                startActivityForResult(intent, 100);
                break;
            case R.id.search_delete:
                searchDelete.setVisibility(View.GONE);
                search_name = "";
                tvContent.setText("");
                break;
        }
    }

    private void setDataToList(List<WorkingLogBean> beans) {
        if (pageNum == 1) {
            workingLogAdapter.setNewData(beans);
        } else {
            workingLogAdapter.addData(beans);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
//                    workingLogList.clear();
//                    workingLogList.addAll(LocalWorkingLogBean.getWorkingLogList(logType + ""));
//                    workingLogAdapter.setNewData(workingLogList);

                    getLogList(search_name);
                    break;
            }
        }
    }
}
