package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.CheckProjectBean;
import com.patrol.terminal.bean.CheckResultBean;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView checkResultRv;

    private View header;
    private View bottom;
    private AddCheckResultAdapter mAddCheckResultAdapter;
    private List<CheckResultBean> mCheckResult = new ArrayList<>();
    private boolean isNatureShow = false;
    private RadioGroup mNatureRg;
    private TextView mProjectContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_safe_check_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleName.setText("新增安全检查");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("保存");

        header = LayoutInflater.from(this).inflate(R.layout.add_safe_check_activity_top, null);
        bottom = LayoutInflater.from(this).inflate(R.layout.add_safe_check_activity_bottom, null);

        RelativeLayout projectRl = header.findViewById(R.id.project_rl);
        mProjectContentTv = header.findViewById(R.id.project_content_tv);

        projectRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddSafeCheckActivity.this, ProjectSearchActivity.class);
                startActivityForResult(intent, 1001);
            }
        });

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

        ViewGroup parentViewGroup = (ViewGroup) header.getParent();
        if (parentViewGroup != null) {
            parentViewGroup.removeAllViews();
        }
        ViewGroup parentViewGroup1 = (ViewGroup) bottom.getParent();
        if (parentViewGroup1 != null) {
            parentViewGroup1.removeAllViews();
        }
        mAddCheckResultAdapter.addHeaderView(header);
        mAddCheckResultAdapter.addFooterView(bottom);

        mNatureRg = header.findViewById(R.id.nature_rg);
        TextView natureContentTv = header.findViewById(R.id.nature_content_tv);
        mNatureRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                mNatureRg.setVisibility(View.GONE);
                isNatureShow = false;
                String[] natureArray = getResources().getStringArray(R.array.safe_check_nature_array);
                switch (checkedId) {
                    case R.id.safe_construction_rb:
                        natureContentTv.setText(natureArray[0]);
                        break;

                    case R.id.safe_education_rb:
                        natureContentTv.setText(natureArray[1]);
                        break;

                    case R.id.financial_audit_rb:
                        natureContentTv.setText(natureArray[2]);
                        break;

                    case R.id.other_rb:
                        natureContentTv.setText(natureArray[3]);
                        break;
                }
            }
        });

        RelativeLayout natureRl = header.findViewById(R.id.nature_rl);
        natureRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNatureShow) {
                    mNatureRg.setVisibility(View.GONE);
                    isNatureShow = false;
                } else {
                    mNatureRg.setVisibility(View.VISIBLE);
                    isNatureShow = true;
                }
            }
        });

        LinearLayout addCheckResultRl = bottom.findViewById(R.id.add_check_result_rl);
        addCheckResultRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加一条数据供填写
                CheckResultBean checkResultBean = new CheckResultBean();
                checkResultBean.setCheckResult(0);
                checkResultBean.setCheckContent(null);
                checkResultBean.setCheckPics(null);
                mCheckResult.add(checkResultBean);

                mAddCheckResultAdapter.setNewData(mCheckResult);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1001:
                if (data != null) {
                    CheckProjectBean clickedCheckProjectBean = data.getParcelableExtra("search_project_item");
                    if (clickedCheckProjectBean != null) {
                        String name = clickedCheckProjectBean.getName();
                        String projectId = clickedCheckProjectBean.getProject_id();   //备用  TODO
                        mProjectContentTv.setText(name);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                finish();
                break;

        }
    }

}
