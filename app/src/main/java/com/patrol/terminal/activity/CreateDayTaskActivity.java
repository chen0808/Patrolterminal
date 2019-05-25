package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LineBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.WeekLineBean;
import com.patrol.terminal.bean.WeekPlanBean;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.WeekPlanView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateDayTaskActivity extends AppCompatActivity {

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
    @BindView(R.id.day_task_name)
    EditText dayTaskName;
    @BindView(R.id.day_task_type)
    TextView dayTaskType;
    @BindView(R.id.day_task_class)
    TextView dayTaskClass;
    @BindView(R.id.day_execute_date)
    TextView dayExecuteDate;
    @BindView(R.id.day_num)
    TextView dayNum;
    @BindView(R.id.day_task_detail)
    EditText dayTaskDetail;
    @BindView(R.id.day_task_line_ll)
    LinearLayout dayTaskLineLl;
    @BindView(R.id.day_add_line)
    TextView dayAddLine;
    @BindView(R.id.day_delet_line)
    TextView dayDeletLine;
    @BindView(R.id.day_yes)
    TextView dayYes;

    List<String> typeList = new ArrayList<>();
    List<String> lineList = new ArrayList<>();
    List<String> towerLine = new ArrayList<>();
    List<String> addList = new ArrayList<>();
    private List<LineBean> results;
    private String year;
    private String month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_day_task);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("生成任务");
    }

    @OnClick({R.id.title_back, R.id.day_yes, R.id.day_add_line, R.id.day_delet_line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.day_yes:
                saveWeekPlan();
                break;
            case R.id.day_add_line:
                showLine();
                break;
            case R.id.day_delet_line:
                showAddLine();
                break;


        }
    }

    //保存周计划
    public void saveWeekPlan() {

        WeekPlanBean bean = new WeekPlanBean();
//        List<WeekLineBean.LineMakeBean.TowerMakeBean> list=new ArrayList<>();
//        List<WeekLineBean.LineMakeBean> list1=new ArrayList<>();
//        WeekLineBean.LineMakeBean.TowerMakeBean bean2=new  WeekLineBean.LineMakeBean.TowerMakeBean("1","2");
//        WeekLineBean.LineMakeBean.TowerMakeBean bean3=new  WeekLineBean.LineMakeBean.TowerMakeBean("2","3");
//        list.add(bean2);
//        list.add(bean3);
//        WeekLineBean.LineMakeBean bean1=new WeekLineBean.LineMakeBean(list,"1","2");
//        WeekLineBean.LineMakeBean bean4=new WeekLineBean.LineMakeBean(list,"1","2");
//        list1.add(bean1);
//        list1.add(bean4);
//        bean.setEqLineList(list1);
        bean.setName(dayTaskName.getText().toString().trim());


        BaseRequest.getInstance().getService()
                .saveWeekPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    //获取每个班组负责的线路
    public void getLine() {

        WeekLineBean bean = new WeekLineBean();
        BaseRequest.getInstance().getService()
                .getLine("","", SPUtil.getDepId(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineBean>> t) throws Exception {
                        results = t.getResults();
                        if (results != null) {
                            for (int i = 0; i < results.size(); i++) {
                                lineList.add(results.get(i).getName());
                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }

    //获取线路类型
    public void getLineType() {
        WeekLineBean bean = new WeekLineBean();
        BaseRequest.getInstance().getService()
                .getLineType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        List<LineTypeBean> result = t.getResults();
                        for (int i = 0; i < result.size(); i++) {
                            LineTypeBean lineTypeBean = result.get(i);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    //所有线路选择框
    private void showLine() {// 不联动的多级选项
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                for (int i = 0; i < results.size(); i++) {
                    if (lineList.get(options1).equals(results.get(i).getName())) {
                        WeekPlanView view = new WeekPlanView(CreateDayTaskActivity.this, results.get(i), year, month);
                        dayTaskLineLl.addView(view);
                    }
                }
                addList.add(lineList.get(options1));
                lineList.remove(options1);
            }
        }).build();
        pvOptions.setPicker(lineList);
        pvOptions.show();

    }

    //已添加选项框
    private void showAddLine() {// 不联动的多级选项
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                dayTaskLineLl.removeViewAt(options1);
                lineList.add(addList.get(options1));
                addList.remove(options1);
            }
        }).build();
        pvOptions.setPicker(addList);
        pvOptions.show();


    }


}
