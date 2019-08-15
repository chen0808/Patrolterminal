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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.CheckProjectBean;
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

public class CheckProjectActivity extends BaseActivity implements TextWatcher {
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

    private CheckProjectAdapter mCheckProjectAdapter;
    private List<CheckProjectBean> mCheckProject = new ArrayList<>();
    private List<CheckProjectBean> mFilterCheckProject = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_project_activity_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleName.setText("安全检查");

        // 设置监听器。
        projectRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        projectRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        projectRv.setLayoutManager(manager);

        CheckProjectBean checkProjectBean = new CheckProjectBean();
        checkProjectBean.setProject_id("0");
        checkProjectBean.setName("综合调控中心工程");
        checkProjectBean.setContent("杆塔倾斜");
        checkProjectBean.setCreate_person_name("创建人:张三");
        checkProjectBean.setProject_result_status(0);
        checkProjectBean.setTime("2019-04-12 14:59");
        mCheckProject.add(checkProjectBean);

        CheckProjectBean checkProjectBean1 = new CheckProjectBean();
        checkProjectBean1.setProject_id("1");
        checkProjectBean1.setName("综合调控中心工程111");
        checkProjectBean1.setContent("杆塔倾斜");
        checkProjectBean1.setCreate_person_name("创建人:张三");
        checkProjectBean1.setProject_result_status(1);
        checkProjectBean1.setTime("2019-04-12 14:59");
        mCheckProject.add(checkProjectBean1);

        CheckProjectBean checkProjectBean2 = new CheckProjectBean();
        checkProjectBean2.setProject_id("2");
        checkProjectBean2.setName("丽水盆地");
        checkProjectBean2.setContent("杆塔倾斜");
        checkProjectBean2.setCreate_person_name("创建人:张三");
        checkProjectBean2.setProject_result_status(2);
        checkProjectBean2.setTime("2019-04-12 14:59");
        mCheckProject.add(checkProjectBean2);

        mCheckProjectAdapter = new CheckProjectAdapter(R.layout.check_project_item, mCheckProject, 0);
        projectRv.setAdapter(mCheckProjectAdapter);
        mCheckProjectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(CheckProjectActivity.this, ProjectDetailActivity.class);
                CheckProjectBean clickCheckProjectBean = (CheckProjectBean)adapter.getItem(position);
                startActivity(intent);

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
            mCheckProjectAdapter.setNewData(mCheckProject);
        }else {
            mFilterCheckProject.clear();
            for (int i = 0; i < mCheckProject.size();i++) {
                String name = mCheckProject.get(i).getName();
                if (name.contains(editStr)) {
                    mFilterCheckProject.add(mCheckProject.get(i));
                }
            }
            mCheckProjectAdapter.setNewData(mFilterCheckProject);
        }

    }


}
