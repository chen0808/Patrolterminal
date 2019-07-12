package com.patrol.terminal.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.NextDayPlanAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.WeekListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NextDayPlanActivity extends BaseActivity {


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
    @BindView(R.id.next_plan_rv)
    SwipeRecyclerView nextPlanRv;
    @BindView(R.id.plan_total_title)
    TextView planTotalTitle;
    @BindView(R.id.month_line_total)
    TextView monthLineTotal;
    @BindView(R.id.month_line_kilo_total)
    TextView monthLineKiloTotal;
    @BindView(R.id.month_line_110kv_num)
    TextView monthLine110kvNum;
    @BindView(R.id.month_line_110kv_kilo)
    TextView monthLine110kvKilo;
    @BindView(R.id.month_line_35kv_num)
    TextView monthLine35kvNum;
    @BindView(R.id.month_line_35kv_kilo)
    TextView monthLine35kvKilo;
    @BindView(R.id.task_add_iv)
    ImageView taskAddIv;
    @BindView(R.id.task_screen)
    ImageView taskScreen;
    @BindView(R.id.next_plan_done)
    TextView nextPlanDone;
    @BindView(R.id.ll_35kv)
    LinearLayout ll35kv;
    private NextDayPlanAdapter monthPlanAdapter;
    private List<DayListBean> list;
    private int year;
    private int month, day;
    private int num_total;
    private double kilo_total;
    private List<String> lineNum = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_month_plan);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {

        ll35kv.setVisibility(View.GONE);
        taskAddIv.setVisibility(View.VISIBLE);
        year = getIntent().getIntExtra("year", 2019);
        month = getIntent().getIntExtra("month", 23);
        day = getIntent().getIntExtra("day", 23);
        titleName.setText(year + "年" + month + "月" + day + "日计划");
        planTotalTitle.setText(day + "日工作计划汇总");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        String mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
            // 设置监听器。
            nextPlanRv.setSwipeMenuCreator(mSwipeMenuCreator);

            // 菜单点击监听。
            nextPlanRv.setOnItemMenuClickListener(mItemMenuClickListener);
        }
        nextPlanRv.setLayoutManager(manager);
        monthPlanAdapter = new NextDayPlanAdapter(R.layout.fragment_plan_item, list);
        nextPlanRv.setAdapter(monthPlanAdapter);
        adapterClick();
        getDayList();
    }

    //获取日计划列表
    public void getDayList() {
        ProgressDialog.show(this, false, "正在加载中。。。");
        BaseRequest.getInstance().getService()
                .getDayList(year + "", month + "", day + "", SPUtil.getDepId(this), "line_id,name")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayListBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<DayListBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            num_total = 0;
                            kilo_total = 0;
                            list = t.getResults();

                            for (int i = 0; i < list.size(); i++) {
                                DayListBean dayListBean = list.get(i);
                                num_total++;
                                kilo_total = kilo_total + dayListBean.getTowers_range();
                                if (lineNum.indexOf(dayListBean.getLine_id()) == -1) {
                                    lineNum.add(dayListBean.getLine_id());
                                }
                            }
                            DecimalFormat decimalFormat = new DecimalFormat("0.00");
                            monthLineTotal.setText("工作线路 : " + lineNum.size() + "条");
                            monthLineKiloTotal.setText(decimalFormat.format(kilo_total) + "km");
                            monthLine110kvNum.setText("工作杆段 : " + num_total + "段");
                            monthLine110kvKilo.setText(decimalFormat.format(kilo_total) + "km");
                            monthPlanAdapter.setNewData(list);
                            ProgressDialog.cancle();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Log.e("fff", e.toString());
                    }
                });
    }

    public void adapterClick() {

        monthPlanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent();
                intent.setClass(NextDayPlanActivity.this, DayPlanDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("bean", list.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
    }

    @OnClick({R.id.title_back, R.id.task_add_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.task_add_iv:
                Intent intent = new Intent(this, AddDayPlanActivity.class);
                intent.putExtra("year", year + "");
                intent.putExtra("month", month + "");
                intent.putExtra("day", day + "");
                startActivityForResult(intent, 10);
                break;
        }
    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem1 = new SwipeMenuItem(NextDayPlanActivity.this);
            deleteItem1.setImage(R.mipmap.plan_delete);
            deleteItem1.setWidth(width);
            deleteItem1.setHeight(height);
//            deleteItem1.setBackground(R.color.home_red);
//            deleteItem1.setTextSize(15);
//            deleteItem1.setTextColorResource(R.color.white);
//            deleteItem1.setText("删除");
            // 各种文字和图标属性设置。
            rightMenu.addMenuItem(deleteItem1); // 在Item右侧添加一个菜单。
            // 注意：哪边不想要菜单，那么不要添加即可。
        }
    };
    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            deleteDayPlan(list.get(position).getId(), position);
            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
        }
    };

    //删除周计划
    public void deleteDayPlan(String id, int position) {
        ProgressDialog.show(NextDayPlanActivity.this, false, "正在加载中...");
        BaseRequest.getInstance().getService()
                .deleteDayPlan(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            num_total--;
                            kilo_total = kilo_total - list.get(position).getTowers_range();
                            lineNum.clear();
                            list.remove(position);
                            //删除数据后重新计算线路条数
                            for (int i = 0; i < list.size(); i++) {
                                DayListBean dayListBean = list.get(i);
                                if (lineNum.indexOf(dayListBean.getLine_id()) == -1) {
                                    lineNum.add(dayListBean.getLine_id());
                                }
                            }
                            monthPlanAdapter.notifyItemRemoved(position);
                            DecimalFormat decimalFormat = new DecimalFormat("0.00");
                            monthLineTotal.setText("工作线路 : " + lineNum.size() + "条");
                            monthLineKiloTotal.setText(decimalFormat.format(kilo_total) + "km");
                            monthLine110kvNum.setText("工作杆段 : " + num_total + "段");
                            monthLine110kvKilo.setText(decimalFormat.format(kilo_total) + "km");
                        } else {
                            Toast.makeText(NextDayPlanActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            getDayList();
        }
    }



}
