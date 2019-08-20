package com.patrol.terminal.overhaul;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.CheckProjectServiceBean;
import com.patrol.terminal.bean.CheckResultBean;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectDetailActivity extends BaseActivity {
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
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.project_name_tv)
    TextView projectNameTv;
    @BindView(R.id.nature_tv)
    TextView natureTv;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.check_person_tv)
    TextView checkPersonTv;
    @BindView(R.id.check_item_tv)
    TextView checkItemTv;
    @BindView(R.id.check_result_rv)
    SwipeRecyclerView checkResultRv;
    @BindView(R.id.state_tv)
    TextView stateTv;

    //private List<CheckResultBean> mCheckResult = new ArrayList<>();
    private List<CheckProjectServiceBean.TempCheckResultListBean> checkResultList;
    private ShowCheckResultAdapter mAddCheckResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.project_detail_activity_layout);
        ButterKnife.bind(this);
        initView();
    }



    private void initView() {
        titleName.setText("安全查看");

        // 设置监听器。
        checkResultRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        checkResultRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        checkResultRv.setLayoutManager(manager);

        CheckProjectServiceBean checkProjectServiceBean = getIntent().getParcelableExtra("click_check_project_bean");
        checkResultList = checkProjectServiceBean.getTempCheckResultList();

        projectNameTv.setText(checkProjectServiceBean.getTemp_project_name());
        switch (checkProjectServiceBean.getState_sign()) {
            case "0":
                stateTv.setText("通过");
                break;

            case "1":
                stateTv.setText("正常");
                break;

            case "2":
                stateTv.setText("待整改");
                break;
        }

        String[] natureArray = getResources().getStringArray(R.array.safe_check_nature_array);
        switch (checkProjectServiceBean.getType_sign()) {
            case "0":
                natureTv.setText(natureArray[0]);
                break;

            case "1":
                natureTv.setText(natureArray[1]);
                break;

            case "2":
                natureTv.setText(natureArray[2]);
                break;

            case "3":
                natureTv.setText(natureArray[3]);
                break;
        }

        dateTv.setText(checkProjectServiceBean.getTime());
        checkPersonTv.setText(checkProjectServiceBean.getCheck_user_name());
        checkItemTv.setText(checkProjectServiceBean.getCheck_project());

        //从数据库获取检查结果  TODO
        mAddCheckResultAdapter = new ShowCheckResultAdapter(this, R.layout.show_check_result_item, checkResultList);
        checkResultRv.setAdapter(mAddCheckResultAdapter);

    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);
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


    @OnClick(R.id.title_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
