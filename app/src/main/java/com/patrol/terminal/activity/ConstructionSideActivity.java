package com.patrol.terminal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectIngTabAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.patrol.terminal.utils.Constant;
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
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;

    private int logType = 0;
    private Context mContext;
    private DefectIngTabAdapter groupTaskAdapter;
    private List<DefectFragmentBean> defectList = new ArrayList<>();
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
        titleSettingIv.setImageResource(R.mipmap.add_black);
        titleSettingTv.setText("");

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
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                Toast.makeText(mContext, direction + " " + adapterPosition + " " + menuPosition, Toast.LENGTH_SHORT).show();
            }
        };

        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mMenuItemClickListener);

        groupTaskAdapter = new DefectIngTabAdapter(R.layout.fragment_defect_not_in_item, 3);
        planRv.setAdapter(groupTaskAdapter);
        planRv.useDefaultLoadMore();
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                getBanjiXLQx("", "");
            }
        });

        getBanjiXLQx("", "");
    }

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                intent = new Intent(this, WorkingLogDetailActivity.class);
                intent.putExtra("logType", logType);
                startActivity(intent);
                break;
        }
    }

    //班级线路缺陷
    public void getBanjiXLQx(String search_name, String line_id) {
        ProgressDialog.show(mContext, true, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getXLDefact(pageNum, count, line_id, search_name,"grade_sign desc,find_time desc", "3")
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

    private void setDataToList(List<DefectFragmentBean> beans) {
        if (pageNum == 1) {
            groupTaskAdapter.setNewData(beans);
        } else {
            groupTaskAdapter.addData(beans);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_ADDRESS_BOOK) {
            }
        }
    }
}
