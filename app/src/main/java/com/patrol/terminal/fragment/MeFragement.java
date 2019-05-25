package com.patrol.terminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.activity.MyPerformanceActivity;
import com.patrol.terminal.activity.ScoreListActivity;
import com.patrol.terminal.activity.SendCarActivity;
import com.patrol.terminal.activity.SendCarTemporaryActivity;
import com.patrol.terminal.activity.SettingActivity;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MeFragement extends BaseFragment {


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
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.rl_send_car)
    RelativeLayout rlSendCar;
    @BindView(R.id.rl_send_car_temporary)
    RelativeLayout rlSendCarTemporary;
    @BindView(R.id.rl_my_performance)
    RelativeLayout rlMyPerformance;
    @BindView(R.id.rl_team_assessment)
    RelativeLayout rlTeamAssessment;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_dep)
    TextView myDep;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    @Override
    protected void initData() {
        titleBack.setVisibility(View.GONE);
        titleName.setText("我的");
        myDep.setText(SPUtil.getDepName(getContext()));
        myName.setText( SPUtil.getString(getContext(), Constant.USER, Constant.USERNAME,""));
    }

    @OnClick({R.id.rl_team_assessment, R.id.rl_my_performance, R.id.rl_send_car, R.id.rl_setting, R.id.rl_send_car_temporary})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_team_assessment:
                startActivity(new Intent(getActivity(), ScoreListActivity.class));
                break;
            case R.id.rl_my_performance:
                startActivity(new Intent(getActivity(), MyPerformanceActivity.class));
                break;
            case R.id.rl_send_car:
                startActivity(new Intent(getActivity(), SendCarActivity.class));
                break;
            case R.id.rl_send_car_temporary:
                startActivity(new Intent(getActivity(), SendCarTemporaryActivity.class));
                break;
            case R.id.rl_setting:
                startActivityForResult(new Intent(getActivity(), SettingActivity.class), 10);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            getActivity().finish();
        }
    }
}
