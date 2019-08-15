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
import com.patrol.terminal.adapter.WorkingLogAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.LocalGcjbBean;
import com.patrol.terminal.bean.LocalWorkingLogBean;
import com.patrol.terminal.bean.WorkingLogBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
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
    private WorkingLogAdapter workingLogAdapter;
    private List<LocalWorkingLogBean> workingLogList = new ArrayList<>();
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

//        String userName = SPUtil.getUserName(this);
//        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));

//        WorkingLogBean workingLogBean = new WorkingLogBean();
//        workingLogBean.setProject_name("定期巡视");
//        workingLogBean.setWorking_name(userName);
//        workingLogBean.setContent("杆塔倾斜");
//        workingLogBean.setReport_name(userName);
//        workingLogBean.setTime(time);
//        workingLogBean.setStatus(1);
//        workingLogList.add(workingLogBean);
//
//        workingLogBean = new WorkingLogBean();
//        workingLogBean.setProject_name("绝缘子检测");
//        workingLogBean.setWorking_name(userName);
//        workingLogBean.setContent("正常");
//        workingLogBean.setReport_name(userName);
//        workingLogBean.setTime(time);
//        workingLogBean.setStatus(2);
//        workingLogList.add(workingLogBean);
//
//        workingLogBean = new WorkingLogBean();
//        workingLogBean.setProject_name("电阻检测");
//        workingLogBean.setWorking_name(userName);
//        workingLogBean.setContent("需要更换");
//        workingLogBean.setReport_name(userName);
//        workingLogBean.setTime(time);
//        workingLogBean.setStatus(3);
//        workingLogList.add(workingLogBean);

        workingLogList.clear();
        workingLogList.addAll(LocalWorkingLogBean.getWorkingLogList(logType + ""));

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

        workingLogAdapter = new WorkingLogAdapter(R.layout.item_working_log, logType);
        planRv.setAdapter(workingLogAdapter);
        planRv.useDefaultLoadMore();
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
            }
        });

        workingLogAdapter.setNewData(workingLogList);

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
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    workingLogList.clear();
                    workingLogList.addAll(LocalWorkingLogBean.getWorkingLogList(logType + ""));
                    workingLogAdapter.setNewData(workingLogList);
                    break;
            }
        }
    }
}
