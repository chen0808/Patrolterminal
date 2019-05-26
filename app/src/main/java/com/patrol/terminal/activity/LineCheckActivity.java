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
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.LineCheckBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.SavaEleLineBean;
import com.patrol.terminal.bean.SavaLineBean;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*通讯录*/
public class LineCheckActivity extends BaseActivity {

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
    private int year;
    private int month;
    private String id;
    private int selectPosin=-1;
    private List<LineCheckBean> results = new ArrayList<>();
    private LineCheckAdapter adapter;
    private LineCheckBean selectBean;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_check);
        ButterKnife.bind(this);

        from = getIntent().getStringExtra("from");

        if ("Temporary".equals(from)){
            titleName.setText("选择线路");
        }else {
            titleName.setText("保电计划制定");
        }
        year = getIntent().getIntExtra("year", 2019);
        month = getIntent().getIntExtra("month", 5);
        id = getIntent().getStringExtra("id");

        adapter = new LineCheckAdapter(this, results);
        rvLineCheck.setAdapter(adapter);

        rvLineCheck.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosin = position;
                adapter.notifyDataSetChanged();
                selectBean = results.get(position);

            }
        });
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("完成");
        initData();
    }

    private void initData() {
        ProgressDialog.show(this, false, "正在加载。。。");
        BaseRequest.getInstance().getService()
                .getLineList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineCheckBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineCheckBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            results = t.getResults();
                            adapter.setData(results);
                        } else {
                            Toast.makeText(LineCheckActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
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
                    if (selectBean==null){
                        Toast.makeText(LineCheckActivity.this,"请选择一条线路",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ("Temporary".equals(from)){
                        Intent intent=new Intent();
                        Bundle bundle=new Bundle();

                        bundle.putSerializable("bean",selectBean);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK,intent);
                        finish();
                    }else {
                        saveMonthPlan();
                    }

                break;
            case R.id.search_line:
                String trim = lineNameEt.getText().toString().trim();
                for (int i = 0; i <results.size() ; i++) {
                    LineCheckBean lineCheckBean = results.get(i);
                    if (lineCheckBean.getName().contains(trim)){
                        rvLineCheck.setSelection(i);
                    }
                }
                break;
        }
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

    //获取每个班组负责的线路
    public void saveMonthPlan() {
       ProgressDialog.show(this,false,"正在加载。。。");
        SavaEleLineBean bean=new SavaEleLineBean();
        bean.setLine_id(selectBean.getId());
        bean.setLine_name(selectBean.getName());
        bean.setYear(year+"");
        bean.setMonth(month+"");
        bean.setRepair_id(id);
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .saveMonthPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                         if (t.getCode()==1){
                              Toast.makeText(LineCheckActivity.this,"制定成功",Toast.LENGTH_SHORT).show();
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

}
