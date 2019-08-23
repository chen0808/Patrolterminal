package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
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
import com.patrol.terminal.adapter.WorkWeeklyListAdapter;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.patrol.terminal.bean.LocalGcjbBean;
import com.patrol.terminal.bean.LocalWorkWeeklyBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 工作周报  列表
 */
public class WorkWeeklyListActivity extends AppCompatActivity {

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
    @BindView(R.id.title_qx_content)
    EditText title_qx_content;
    @BindView(R.id.title_search_delete)
    ImageView title_search_delete;


    private List<LocalWorkWeeklyBean> workList = new ArrayList<>();
    private List<LocalWorkWeeklyBean> searchList = new ArrayList<>();
    private WorkWeeklyListAdapter adapter;
    private String type;
    private int pageNum = 1;
    private int count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineering_brief_list);
        ButterKnife.bind(this);

        titleName.setText("工作周报");

        title_qx_content.setHint("请输入");
        title_setting_tv.setText("添加");
        title_setting.setVisibility(View.VISIBLE);

        initView();
    }


    public void initView() {

        gclb_lsit.setLayoutManager(new LinearLayoutManager(this));
        gclb_lsit.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));
        adapter = new WorkWeeklyListAdapter(R.layout.item_workweekly_list, workList);
        gclb_lsit.setAdapter(adapter);

        gclb_lsit.useDefaultLoadMore();
        gclb_lsit.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                quesyList();
            }
        });

        title_qx_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    title_search_delete.setVisibility(View.GONE);
                } else {
                    title_search_delete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search_name = title_qx_content.getText().toString();
                if (TextUtils.isEmpty(search_name)) {
                    adapter.setNewData(workList);
                } else {
                    searchList.clear();
                    for (int i = 0; i < workList.size(); i++) {
                        if (workList.get(i).getWork_bzzj().contains(search_name)) {
                            searchList.add(workList.get(i));
                        }
                    }
                    adapter.setNewData(searchList);
                }
            }
        });

        quesyList();
    }

    public void quesyList() {
        ProgressDialog.show(this);
        BaseRequest.getInstance().getService()
                .queryWorklyGET(pageNum + "", count + "","created_date desc")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LocalWorkWeeklyBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LocalWorkWeeklyBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
//                            workList.clear();
                            workList.addAll(t.getResults());
                            adapter.notifyDataSetChanged();
                            if (workList.size() == 0) {
                                Utils.showToast("暂无周报");
                            }

                            List<LocalWorkWeeklyBean> result = t.getResults();
                            if (result != null && result.size() > 0 && result.size() == pageNum) {
                                gclb_lsit.loadMoreFinish(false, true);
                            } else {
                                gclb_lsit.loadMoreFinish(true, false);
                            }

                            if (pageNum == 1) {
                                if (result.size() == 0) {
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

    @OnClick({R.id.title_back, R.id.title_setting,R.id.title_search_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                Intent intent = new Intent();
                intent.setClass(this, WorkWeeklyAddActivity.class);
                startActivityForResult(intent, Constant.GCJB_ADD);
                break;
            case R.id.title_search_delete:
                title_search_delete.setVisibility(View.GONE);
                title_qx_content.setText("");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Constant.GCJB_ADD) {
            pageNum = 1;
            quesyList();
        }
    }
}
