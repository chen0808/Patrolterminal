package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ExameAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PatrolListBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ExameFrgment extends BaseFragment {


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
    @BindView(R.id.frag_exame_rv)
    RecyclerView fragExameRv;
    @BindView(R.id.frag_exame_ref)
    SwipeRefreshLayout fragExameRef;
    @BindView(R.id.rg_exame)
    RadioGroup rgExame;

    private ExameAdapter exameAdapter;
    private TimePickerView pvTime;
    private List<PatrolListBean> results = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exame_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        RadioButton child = (RadioButton) rgExame.getChildAt(0);
        child.setChecked(true);
        titleName.setText("待办列表");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        fragExameRv.setLayoutManager(manager);
        titleBack.setVisibility(View.GONE);
        exameAdapter = new ExameAdapter(R.layout.item_exame_fragment, results);
        fragExameRv.setAdapter(exameAdapter);
        fragExameRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPatrolList();
            }
        });
        getPatrolList();
    }


    public void getPatrolList() {
        BaseRequest.getInstance().getService()
                .getPatrolList(null, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<PatrolListBean>> t) throws Exception {
                        fragExameRef.setRefreshing(false);
                        results = t.getResults();
                        exameAdapter.setNewData(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        fragExameRef.setRefreshing(false);
                    }
                });
    }
}
