package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.EngineeringBriefListAdapter;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.CheckProjectBean;
import com.patrol.terminal.bean.CheckProjectServiceBean;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.patrol.terminal.bean.InitiateProjectBean;
import com.patrol.terminal.bean.InitiateProjectBean2;
import com.patrol.terminal.bean.LocalGcjbBean;
import com.patrol.terminal.overhaul.ProjectSearchActivity;
import com.patrol.terminal.overhaul.ProjectSearchActivityNew;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * 工程简报 简报列表
 */
public class EngineeringBriefListActivity extends AppCompatActivity {

    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_tv)
    TextView title_setting_tv;
    @BindView(R.id.title_setting)
    RelativeLayout title_setting;

    @BindView(R.id.gclb_lsit)
    SwipeRecyclerView gclb_lsit;
    @BindView(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @BindView(R.id.title_qx_content)
    EditText titleQxContent;

    private List<LocalGcjbBean> gcjbList = new ArrayList<>();
    private EngineeringBriefListAdapter adapter;
    private String type;
    private int pageNum = 1;
    private final int count = 10;
    private String projectName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineering_brief_list);
        ButterKnife.bind(this);

        titleQxContent.setFocusable(false);
        type = getIntent().getStringExtra(Constant.GCJB_TYPE_STR);
        if (type.equals(Constant.GCJB_YZF_STR)) {
            titleName.setText("业主方简报");
        } else if (type.equals(Constant.GCJB_JLF_STR)) {
            titleName.setText("监理方简报");
        } else if (type.equals(Constant.GCJB_SGF_STR) || type.equals(Constant.GCJB_ADD_STR)) {
            titleName.setText("施工方简报");
        }

        title_setting_tv.setText("添加");
        title_setting.setVisibility(View.VISIBLE);

        initView();
    }


    public void initView() {

        gclb_lsit.setLayoutManager(new LinearLayoutManager(this));
        gclb_lsit.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));
        adapter = new EngineeringBriefListAdapter(R.layout.item_project_list, gcjbList);
        gclb_lsit.setAdapter(adapter);
        gclb_lsit.useDefaultLoadMore();
        gclb_lsit.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                quesyList();
            }
        });

    }

    public void quesyList() {
        ProgressDialog.show(this);
        BaseRequest.getInstance().getService()
                .queryListPOST(pageNum + "", count + "", projectName, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LocalGcjbBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LocalGcjbBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {

                            List<LocalGcjbBean> result = t.getResults();
                            if (result != null && result.size() > 0 && result.size() == pageNum) {
                                gclb_lsit.loadMoreFinish(false, true);
                            } else {
                                gclb_lsit.loadMoreFinish(true, false);
                            }
                            gcjbList.addAll(result);
                            if (pageNum == 1) {
                                if(result.size()==0){
                                    Utils.showToast("暂无该项目简报");
                                }
                                adapter.setNewData(result);
                            } else {
                                adapter.addData(result);
                            }

                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }
                });
    }


    @OnClick({R.id.title_back, R.id.title_setting, R.id.title_qx_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                Intent intent = new Intent();
                intent.setClass(this, EngineeringBriefAddActivity.class);
                intent.putExtra(Constant.GCJB_TYPE_STR, type);
                startActivityForResult(intent, Constant.GCJB_ADD);
                break;
            case R.id.title_qx_content:
                Intent intent2 = new Intent();
                intent2.setClass(this, ProjectSearchActivityNew.class);
                startActivityForResult(intent2, Constant.GCJB_ADD_PROJECT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.GCJB_ADD:
                    projectName = data.getStringExtra("projectname");
                    titleQxContent.setText(projectName);
                    pageNum = 1;
                    quesyList();
                    break;
                case Constant.GCJB_ADD_PROJECT:
                    InitiateProjectBean2 clickedCheckProjectBean = data.getParcelableExtra("search_project_item");
                    if (clickedCheckProjectBean != null) {
                        titleQxContent.setText(clickedCheckProjectBean.getName());
                        projectName = clickedCheckProjectBean.getName();
                        pageNum = 1;
                        quesyList();
                    }
                    break;
            }
        }
    }


}
