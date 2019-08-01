package com.patrol.terminal.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LineCheckBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.SavaEleLineBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*选择线路*/
public class LineCheckDayActivity extends BaseActivity {

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
    @BindView(R.id.line_name_et)
    EditText lineNameEt;
    @BindView(R.id.search_line)
    ImageView searchLine;
    @BindView(R.id.rv_line_check)
    ListView rvLineCheck;
    @BindView(R.id.plan_empty)
    TextView empty;

    private String year;
    private String month;
    private String dep_id;
    private String id;
    private int selectPosin = -1;
    private List<LineCheckBean> results = new ArrayList<>();
    private List<LineCheckBean> searchResults = new ArrayList<>();
    private LineCheckAdapter adapter;
    private LineCheckBean selectBean;
    private String from;
    private String type_sign;
    private String week;
    private String day;
    private String startMonth;
    private String startDay;
    private String[] start;
    private String endMonth;
    private String endDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_check);
        ButterKnife.bind(this);
        empty.setText("本班组本周所有线路均有巡视计划");
        from = getIntent().getStringExtra("from");
        String jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        if ("Temporary".equals(from)) {
            titleName.setText("选择线路");
        } else {
            titleName.setText("保电计划制定");
        }
        year = getIntent().getStringExtra("year");
        month = getIntent().getStringExtra("month");
        week = getIntent().getStringExtra("week");
        day = getIntent().getStringExtra("day");
        if (month != null) {
        } else if (week != null) {
            //获取当前周起始和终止日期
            String beginDate = TimeUtil.getFirstDayOfWeek(new Date(System.currentTimeMillis()));
            String end2Date = TimeUtil.getLastDayOfWeek(new Date(System.currentTimeMillis()));
            String[] start = beginDate.split("-");
            startMonth = start[0];
            startDay = start[1];
            String[] end = end2Date.split("-");
            endMonth = end[0];
            endDay = end[1];
            if (startMonth == endMonth) {
                month = startMonth;
            } else {
                month = Integer.valueOf(startMonth) + "," + Integer.valueOf(endMonth);
            }
        }
        id = getIntent().getStringExtra("id");
        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {   //检修班班长，组员,验收，保电，安全专责只能看周计划
            dep_id = SPUtil.getDepId(this);
            type_sign = "2,4,7,8,9,10";
        }
        adapter = new LineCheckAdapter(this, results);
        rvLineCheck.setAdapter(adapter);
        rvLineCheck.setEmptyView(empty);
        rvLineCheck.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosin = position;
                if (searchResults != null && searchResults.size() > 0) {
                    selectBean = searchResults.get(position);
                } else {
                    selectBean = results.get(position);
                }
                adapter.notifyDataSetChanged();
            }
        });
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("完成");
        initData();
    }

    private void initData() {
        ProgressDialog.show(this, false, "正在加载。。。");
        BaseRequest.getInstance().getService()
                .getLineListday(year, week,day, dep_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineCheckBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineCheckBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            results = t.getResults();
                            adapter.setData(results);
                        } else {
                            Toast.makeText(LineCheckDayActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.d("TAG", e.getMessage());
                        ProgressDialog.cancle();
                    }
                });
    }


    @OnClick({R.id.title_back, R.id.title_setting, R.id.search_line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                if (selectBean == null) {
                    Toast.makeText(LineCheckDayActivity.this, "请选择一条线路", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("Temporary".equals(from)) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("bean", selectBean);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    saveMonthPlan();
                }

                break;
            case R.id.search_line:
                searchResults.clear();
                String trim = lineNameEt.getText().toString().trim();
                for (int i = 0; i < results.size(); i++) {
                    LineCheckBean lineCheckBean = results.get(i);
                    if (lineCheckBean.getName().contains(trim)) {
//                        rvLineCheck.setSelection(i);
                        searchResults.add(lineCheckBean);
                    }
                }
                adapter.setData(searchResults);
                break;
        }
    }

    //获取每个班组负责的线路
    public void saveMonthPlan() {
        ProgressDialog.show(this, false, "正在加载。。。");
        SavaEleLineBean bean = new SavaEleLineBean();
        bean.setLine_id(selectBean.getId());
        bean.setLine_name(selectBean.getName());
        bean.setYear(year + "");
        bean.setMonth(month + "");
        bean.setRepair_id(id);
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .saveMonthPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            Toast.makeText(LineCheckDayActivity.this, "制定成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    class LineCheckAdapter extends BaseAdapter {
        private Context context;
        private List<LineCheckBean> lineTypeBeans;

        public LineCheckAdapter(Context context, List<LineCheckBean> traceList) {
            this.context = context;
            this.lineTypeBeans = traceList;
        }

        @Override
        public int getCount() {

            return lineTypeBeans.size();


        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_line_check, parent, false);
                holder.lineCheckName = (TextView) convertView.findViewById(R.id.line_check_line_name);
                holder.lineCheckDep = (TextView) convertView.findViewById(R.id.line_check_dep_name);
                holder.lineCheckRb = (RadioButton) convertView.findViewById(R.id.line_check_rb);
                convertView.setTag(holder);
            }
            LineCheckBean listBean = lineTypeBeans.get(position);
            holder.lineCheckName.setText(listBean.getName());
            holder.lineCheckDep.setText(listBean.getDep_name());
            if (selectPosin == position) {
                holder.lineCheckRb.setChecked(true);
            } else {
                holder.lineCheckRb.setChecked(false);
            }
            return convertView;
        }

        public void setData(List<LineCheckBean> typeBeanList) {
            lineTypeBeans = typeBeanList;
            notifyDataSetChanged();

        }


        class ViewHolder {
            private TextView lineCheckName;
            private TextView lineCheckDep;
            private RadioButton lineCheckRb;
        }

    }

}
