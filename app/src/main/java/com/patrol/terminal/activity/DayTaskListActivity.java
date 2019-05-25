package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DayTaskAdapter;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.utils.DateUatil;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DayTaskListActivity extends AppCompatActivity {


    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @BindView(R.id.title_setting_tv)
    TextView titleSettingTv;

    @BindView(R.id.dat_task_rv)
    SwipeRecyclerView datTaskRv;
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_setting)
    RelativeLayout titleSetting;
    private DayTaskAdapter dayPlanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_task_list);
        ButterKnife.bind(this);
    }

    private void initview() {
        titleName.setText("日任务列表");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setVisibility(View.GONE);
        titleSettingTv.setVisibility(View.VISIBLE);
        titleSettingIv.setImageResource(R.mipmap.add_black);
        // 设置监听器。
        datTaskRv.setSwipeMenuCreator(mSwipeMenuCreator);

        // 菜单点击监听。
        datTaskRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        datTaskRv.setLayoutManager(manager);
        ArrayList<MonthPlanBean> list = new ArrayList<>();
        for (int i = 0; i < DateUatil.getWeekNum(); i++) {
            MonthPlanBean bean = new MonthPlanBean();
            list.add(bean);
        }
        dayPlanAdapter = new DayTaskAdapter(R.layout.fragment_plan_item, list);
        datTaskRv.setAdapter(dayPlanAdapter);
    }


    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(DayTaskListActivity.this);
            deleteItem.setWidth(width);
            deleteItem.setHeight(height);
            deleteItem.setTextSize(15);
            deleteItem.setTextColorResource(R.color.white);
            deleteItem.setBackground(R.color.orange_vip);
            deleteItem.setText("编辑");
            SwipeMenuItem deleteItem1 = new SwipeMenuItem(DayTaskListActivity.this);
            deleteItem1.setWidth(width);
            deleteItem1.setHeight(height);
            deleteItem1.setBackground(R.color.home_red);
            deleteItem1.setTextSize(15);
            deleteItem1.setTextColorResource(R.color.white);
            deleteItem1.setText("删除");
            // 各种文字和图标属性设置。
            rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。
            rightMenu.addMenuItem(deleteItem1); // 在Item右侧添加一个菜单。
            // 注意：哪边不想要菜单，那么不要添加即可。
        }
    };
    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
        }
    };

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:

                startActivity(new Intent(this,CreateDayTaskActivity.class));
                break;
        }
    }
}
