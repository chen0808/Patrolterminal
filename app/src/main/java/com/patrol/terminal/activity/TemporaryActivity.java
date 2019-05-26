package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LineCheckBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.SavaLineBean;
import com.patrol.terminal.utils.DateUatil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TemporaryActivity extends BaseActivity {


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
    @BindView(R.id.month_plan_type)
    TextView monthPlanType;
    @BindView(R.id.month_plan_month)
    TextView monthPlanMonth;
    @BindView(R.id.month_plan_line)
    TextView monthPlanLine;
    @BindView(R.id.month_yes)
    TextView monthYes;
    @BindView(R.id.create_group_task)
    TextView createGroupTask;

    private List<String> typeNameList = new ArrayList<>();
    private List<LineTypeBean> typeList=new ArrayList<>();
    private String typeName;
    private LineCheckBean lineCheckBean;
    private String type_id;
    private String sign;
    private String month;
    private String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("制定临时月计划");
        String time = DateUatil.getCurMonth();
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0])+"";
        year = years[0];
        getLineType();
    }

    @OnClick({R.id.title_back, R.id.month_plan_type, R.id.month_plan_line, R.id.month_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.month_plan_type:
                showType();
                break;
            case R.id.month_plan_line:
                Intent intent=new Intent(this,LineCheckActivity.class);
                intent.putExtra("from","Temporary");
                startActivityForResult(intent,24);
                break;
            case R.id.month_yes:
                if (lineCheckBean==null){
                    Toast.makeText(this, "请选择一条线路", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (type_id==null){
                    Toast.makeText(this, "请选择工作类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveMonthPlan();
                break;
        }
    }
    //获取工作类型
    public void getLineType() {
        typeNameList.clear();
        BaseRequest.getInstance().getService()
                .getLineType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        typeList = t.getResults();
                        for (int i = 0; i < typeList.size(); i++) {
                            LineTypeBean lineTypeBean = typeList.get(i);
                            typeNameList.add(lineTypeBean.getName());
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
    //类型添加选项框
    private void showType() {// 不联动的多级选项
        if (typeList.size() == 0) {
            Toast.makeText(this, "没有获取到工作类型信息，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {




            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                typeName = typeList.get(options1).getName();
                monthPlanType.setText(typeName);
                type_id = typeList.get(options1).getId();
                sign = typeList.get(options1).getSign();

            }
        }).build();
        pvOptions.setPicker(typeNameList);
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==24&&resultCode==RESULT_OK){
            if (data!=null){
                lineCheckBean = (LineCheckBean) data.getSerializableExtra("bean");
                monthPlanLine.setText(lineCheckBean.getName());
            }
        }
    }
    //获取每个班组负责的线路
    public void saveMonthPlan() {
        SavaLineBean bean=new SavaLineBean();
       bean.setLine_id(lineCheckBean.getId());
       bean.setLine_name(lineCheckBean.getName());
       bean.setType_id(type_id);
       bean.setType_sign(sign);
       bean.setType_name(typeName);
       bean.setYear(year);
       bean.setMonth(month);
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .saveMonthPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode()==1){
                            Toast.makeText(TemporaryActivity.this,"制定成功",Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
}
