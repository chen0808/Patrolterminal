package com.patrol.terminal.overhaul;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.CheckResultBean;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddSafeCheckActivity extends BaseActivity {
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
    @BindView(R.id.project_tv)
    TextView projectTv;
    @BindView(R.id.project_rl)
    RelativeLayout projectRl;
    @BindView(R.id.nature_tv)
    TextView natureTv;
    @BindView(R.id.nature_rl)
    RelativeLayout natureRl;
    @BindView(R.id.safe_construction_rb)
    RadioButton safeConstructionRb;
    @BindView(R.id.safe_education_rb)
    RadioButton safeEducationRb;
    @BindView(R.id.financial_audit_rb)
    RadioButton financialAuditRb;
    @BindView(R.id.other_rb)
    RadioButton otherRb;
    @BindView(R.id.nature_rg)
    RadioGroup natureRg;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.date_rl)
    RelativeLayout dateRl;
    @BindView(R.id.check_person_tv)
    TextView checkPersonTv;
    @BindView(R.id.check_person_rl)
    RelativeLayout checkPersonRl;
    @BindView(R.id.check_items_tv)
    TextView checkItemsTv;
    @BindView(R.id.check_items_rl)
    RelativeLayout checkItemsRl;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView checkResultRv;
    @BindView(R.id.record_person)
    TextView recordPerson;
    @BindView(R.id.add_check_result_rl)
    LinearLayout addCheckResultRl;

    private AddCheckResultAdapter mAddCheckResultAdapter;
    private List<CheckResultBean> mCheckResult = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_safe_check_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleName.setText("新增安全检查");

        // 设置监听器。
        checkResultRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        checkResultRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        checkResultRv.setLayoutManager(manager);

        //默认有一条数据供填写
        CheckResultBean checkResultBean = new CheckResultBean();
        checkResultBean.setCheckResult(0);
        checkResultBean.setCheckContent(null);
        checkResultBean.setCheckPics(null);
        mCheckResult.add(checkResultBean);

        mAddCheckResultAdapter = new AddCheckResultAdapter(R.layout.add_check_result_item, mCheckResult);
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

    @OnClick({R.id.title_back, R.id.title_setting, R.id.project_rl, R.id.nature_rl, R.id.date_rl, R.id.check_person_rl, R.id.add_check_result_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                break;
            case R.id.project_rl:
                break;
            case R.id.nature_rl:
                break;
            case R.id.date_rl:
                break;
            case R.id.check_person_rl:
                break;
            case R.id.add_check_result_rl:
                //添加一条数据供填写
                CheckResultBean checkResultBean = new CheckResultBean();
                checkResultBean.setCheckResult(0);
                checkResultBean.setCheckContent(null);
                checkResultBean.setCheckPics(null);
                mCheckResult.add(checkResultBean);

                mAddCheckResultAdapter.setNewData(mCheckResult);

                break;
        }
    }

}
