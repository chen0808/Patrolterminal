package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DangerPatrolReqBean;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.DangerSubmitView;
import com.patrol.terminal.widget.DateSelectViewUtil;
import com.patrol.terminal.widget.ProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 隐患巡视转计划
 */
public class DangerToPatrolActivity extends BaseActivity {


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
    @BindView(R.id.danger_patrol_type)
    DangerSubmitView dangerPatrolType;
    @BindView(R.id.danger_patrol_dep)
    DangerSubmitView dangerPatrolDep;
    @BindView(R.id.danger_patrol_level)
    DangerSubmitView dangerPatrolLevel;
    @BindView(R.id.danger_patrol_line)
    DangerSubmitView dangerPatrolLine;
    @BindView(R.id.danger_patrol_tower)
    DangerSubmitView dangerPatrolTower;
    @BindView(R.id.danger_patrol_end_time)
    TextView dangerPatrolEndTime;
    @BindView(R.id.danger_patrol_date)
    EditText dangerPatrolDate;
    @BindView(R.id.danger_patrol_save)
    TextView dangerPatrolSave;
    private String type_id;
    private String id;
    private String f_id;
    private String time="";
    private String tower_name;
    private String line_name;
    private String find_dep_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_to_patrol);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("隐患巡视计划");
        String danger_type = getIntent().getStringExtra("danger_type");
        String danger_level = getIntent().getStringExtra("danger_level");
        line_name = getIntent().getStringExtra("line_name");
        String dep_name = getIntent().getStringExtra("dep_name");
        tower_name = getIntent().getStringExtra("tower_name");
        find_dep_id = getIntent().getStringExtra("find_dep_id");

        type_id = getIntent().getStringExtra("type_id");
        id = getIntent().getStringExtra("id");
        f_id = getIntent().getStringExtra("f_id");

        dangerPatrolType.setContent(danger_type);
        dangerPatrolLevel.setContent(danger_level);
        dangerPatrolDep.setContent(dep_name);
        dangerPatrolLine.setContent(line_name);
        dangerPatrolTower.setContent(tower_name);

    }


    @OnClick({R.id.title_back, R.id.danger_patrol_end_time, R.id.danger_patrol_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.danger_patrol_end_time:
//                showDay();
                DateSelectViewUtil.init(this).setOnDateSelectClick(new DateSelectViewUtil.onDateSelectClick() {
                    @Override
                    public void onItemSelect(String date, View v) {
                        time = date;
                        dangerPatrolEndTime.setText(time);
                    }
                }).show();

                break;
            case R.id.danger_patrol_save:
                createDangerPatrol();
                break;
        }
    }


    public void createDangerPatrol() {
        ProgressDialog.show(this);
        String day = dangerPatrolDate.getText().toString().trim();
        if ("".equals(time)){
            Toast.makeText(this,"请先选择结束日期",Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(day)){
            Toast.makeText(this,"请先选择周期",Toast.LENGTH_SHORT).show();
            return;
        }

        SPUtil.getUserId(this);
        DangerPatrolReqBean bean = new DangerPatrolReqBean();

        bean.setF_id(f_id);
        bean.setId(id);
        bean.setClose_time(time);
        bean.setType_id(type_id);
        bean.setPatrol_cycle(Integer.parseInt(day));
        bean.setIn_status("1");
        bean.setFrom_user_id(SPUtil.getUserId(this));
        bean.setFrom_user_name(SPUtil.getUserName(this));
        bean.setFind_dep_id(find_dep_id);
        bean.setLine_name(line_name);
        bean.setTower_name(tower_name);

        BaseRequest.getInstance().getService()
                .createDangerPatrol(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        Toast.makeText(DangerToPatrolActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        ProgressDialog.cancle();
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }
}
