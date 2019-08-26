package com.patrol.terminal.activity;

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
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.InitiateProjectBean2;
import com.patrol.terminal.overhaul.SearchProjectAdapter;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 里程碑，工程简报 项目选择
 */
public class LandMarkProjectSearchActivity extends BaseActivity implements TextWatcher {

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

    private SearchProjectAdapter mCheckProjectAdapter;
    private List<InitiateProjectBean2> mCheckProject = new ArrayList<>();
    private List<InitiateProjectBean2> mFilterCheckProject = new ArrayList<>();

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.check_project_activity_layout);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra(Constant.GCJB_TYPE_STR);
        if(!TextUtils.isEmpty(type)){
            if (type.equals(Constant.GCJB_YZF_STR)) {
                titleName.setText("业主方简报");
                projectSearchEt.setHint("请选择项目");
            } else if (type.equals(Constant.GCJB_JLF_STR)) {
                titleName.setText("监理方简报");
                projectSearchEt.setHint("请选择项目");
            } else if (type.equals(Constant.GCJB_SGF_STR) || type.equals(Constant.GCJB_ADD_STR)) {
                titleName.setText("施工方简报");
                projectSearchEt.setHint("请选择项目");
            }
        }else{
            titleName.setText("里程碑");
            projectSearchEt.setHint("请选择里程碑项目");
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        projectRv.setLayoutManager(manager);

        mCheckProjectAdapter = new SearchProjectAdapter(R.layout.project_search_item, mCheckProject);
        projectRv.setAdapter(mCheckProjectAdapter);

        mCheckProjectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                InitiateProjectBean2 clickedCheckProjectBean = (InitiateProjectBean2) adapter.getItem(position);
                Intent intent = null;
                if(!TextUtils.isEmpty(type)){
                    intent = new Intent(LandMarkProjectSearchActivity.this,EngineeringBriefListActivity.class);
                    intent.putExtra(Constant.GCJB_TYPE_STR,type);
                }else{
                    intent = new Intent(LandMarkProjectSearchActivity.this,LandMarkActivity.class);
                }
                intent.putExtra("search_project_item", clickedCheckProjectBean);
                startActivity(intent);

            }
        });

        projectSearchEt.addTextChangedListener(this);


    }

    private void initData() {
        ProgressDialog.show(this);
        BaseRequest.getInstance().getService()
                .getProjectList2(0, 0, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<InitiateProjectBean2>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<InitiateProjectBean2>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            mCheckProject = t.getResults();
                            mCheckProjectAdapter.setNewData(mCheckProject);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }
                });
    }

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
