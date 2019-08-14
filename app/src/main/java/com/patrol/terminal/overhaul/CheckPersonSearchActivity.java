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
import com.patrol.terminal.adapter.CheckPersonGridAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.CheckProjectBean;
import com.patrol.terminal.bean.UserBean;
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

public class CheckPersonSearchActivity extends BaseActivity implements TextWatcher {

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
    SwipeRecyclerView personRv;

    private CheckPersonAdapter mCheckPersonAdapter;
    private List<UserBean> mPersonList = new ArrayList<>();
    private List<UserBean> mFilterPersonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.check_project_activity_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleName.setText("项目选择");
        projectSearchEt.setHint("检索人名");

        // 设置监听器。
        personRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        personRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        personRv.setLayoutManager(manager);

        UserBean userBean = new UserBean();
        userBean.setId("0");
        userBean.setName("叶怀刚");
        mPersonList.add(userBean);

        UserBean userBean1 = new UserBean();
        userBean1.setId("1");
        userBean1.setName("林栋");
        mPersonList.add(userBean1);

        UserBean userBean2 = new UserBean();
        userBean2.setId("2");
        userBean2.setName("桑彦斌");
        mPersonList.add(userBean2);

        UserBean userBean3 = new UserBean();
        userBean3.setId("3");
        userBean3.setName("徐向军");
        mPersonList.add(userBean3);

        UserBean userBean4 = new UserBean();
        userBean4.setId("4");
        userBean4.setName("周星星");
        mPersonList.add(userBean4);

        UserBean userBean5 = new UserBean();
        userBean5.setId("5");
        userBean5.setName("鲍小蕾");
        mPersonList.add(userBean5);

        UserBean userBean6 = new UserBean();
        userBean6.setId("6");
        userBean6.setName("谭玉洁");
        mPersonList.add(userBean6);

        UserBean userBean7 = new UserBean();
        userBean7.setId("7");
        userBean7.setName("樊少皇");
        mPersonList.add(userBean7);

        UserBean userBean8 = new UserBean();
        userBean8.setId("8");
        userBean8.setName("王波");
        mPersonList.add(userBean8);

        UserBean userBean9 = new UserBean();
        userBean9.setId("9");
        userBean9.setName("徐泽");
        mPersonList.add(userBean9);

        UserBean userBean10 = new UserBean();
        userBean10.setId("10");
        userBean10.setName("何少军");
        mPersonList.add(userBean10);

        UserBean userBean11 = new UserBean();
        userBean11.setId("11");
        userBean11.setName("肖占喜");
        mPersonList.add(userBean11);

        mCheckPersonAdapter = new CheckPersonAdapter(R.layout.project_search_item, mPersonList);
        personRv.setAdapter(mCheckPersonAdapter);
        mCheckPersonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UserBean clickUserBean = (UserBean) adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("search_user_item", clickUserBean);
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
            mCheckPersonAdapter.setNewData(mPersonList);
        }else {
            mFilterPersonList.clear();
            for (int i = 0; i < mPersonList.size();i++) {
                String name = mPersonList.get(i).getName();
                if (name.contains(editStr)) {
                    mFilterPersonList.add(mPersonList.get(i));
                }
            }
            mCheckPersonAdapter.setNewData(mFilterPersonList);
        }

    }
}
