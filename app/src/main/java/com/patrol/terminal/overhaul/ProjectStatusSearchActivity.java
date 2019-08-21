package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.CheckProjectStatusBean;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectStatusSearchActivity extends BaseActivity implements TextWatcher {

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
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.project_search_et)
    EditText projectSearchEt;
    @BindView(R.id.delete_iv)
    ImageView deleteIv;
    @BindView(R.id.project_rv)
    SwipeRecyclerView projectRv;

    private CheckProjectStatusAdapter mCheckProjectStatusAdapter;
    private List<CheckProjectStatusBean> mCheckStatusProject = new ArrayList<>();
    private List<CheckProjectStatusBean> mFilterCheckProject = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.check_project_status_activity_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleName.setText("项目状态");

        // 设置监听器。
        projectRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        projectRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        projectRv.setLayoutManager(manager);

        CheckProjectStatusBean checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("0");
        checkProjectStatusBean.setName("项目前期");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("1");
        checkProjectStatusBean.setName("项目立项");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("2");
        checkProjectStatusBean.setName("设计管理");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("3");
        checkProjectStatusBean.setName("招标管理");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("4");
        checkProjectStatusBean.setName("合同管理");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("5");
        checkProjectStatusBean.setName("进度管理");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("6");
        checkProjectStatusBean.setName("前期");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("7");
        checkProjectStatusBean.setName("实施准备");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("8");
        checkProjectStatusBean.setName("在建");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("9");
        checkProjectStatusBean.setName("停缓期");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("10");
        checkProjectStatusBean.setName("验收");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("11");
        checkProjectStatusBean.setName("竣工");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("12");
        checkProjectStatusBean.setName("保内");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("13");
        checkProjectStatusBean.setName("保外");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("14");
        checkProjectStatusBean.setName("解除");
        mCheckStatusProject.add(checkProjectStatusBean);

        checkProjectStatusBean = new CheckProjectStatusBean();
        checkProjectStatusBean.setType("15");
        checkProjectStatusBean.setName("完成");
        mCheckStatusProject.add(checkProjectStatusBean);

        mCheckProjectStatusAdapter = new CheckProjectStatusAdapter(R.layout.project_search_item, mCheckStatusProject, 1);
        projectRv.setAdapter(mCheckProjectStatusAdapter);
        mCheckProjectStatusAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CheckProjectStatusBean clickedCheckProjectStatusBean = (CheckProjectStatusBean) adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("search_project_status_item", clickedCheckProjectStatusBean);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        projectSearchEt.addTextChangedListener(this);

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

    @OnClick({R.id.title_back, R.id.delete_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.delete_iv:
                projectSearchEt.setText("");
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String editStr = projectSearchEt.getText().toString();
        if (TextUtils.isEmpty(editStr)) {
            mCheckProjectStatusAdapter.setNewData(mCheckStatusProject);
        } else {
            mFilterCheckProject.clear();
            for (int i = 0; i < mCheckStatusProject.size();i++) {
                String name = mCheckStatusProject.get(i).getName();
                if (name.contains(editStr)) {
                    mFilterCheckProject.add(mCheckStatusProject.get(i));
                }
            }
            mCheckProjectStatusAdapter.setNewData(mFilterCheckProject);
        }
    }
}
