package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.NextWeekPlanAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DepBean;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.SubmitPlanReqBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.bean.WeekListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.PopListDialog;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.w3c.dom.ls.LSException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NextWeekPlanActivity extends BaseActivity {


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
    @BindView(R.id.task_screen)
    ImageView taskScreen;
    @BindView(R.id.next_plan_done)
    TextView nextPlanDone;
    @BindView(R.id.task_add_iv)
    ImageView taskAddIv;
    @BindView(R.id.ll_35kv)
    LinearLayout ll35kv;
    private String state;
    private NextWeekPlanAdapter monthPlanAdapter;
    private List<WeekListBean> list;
    private String mJobType;
    private List<Tower> lineList = new ArrayList<>();
    private int year;
    private int week;
    private List<DepBean> depList;
    private PopListDialog popWinShare;
    private String depId;
    private Disposable subscribe;
    private int num_total;
    private double kilo_total;
    private int num_110kv;
    private double kilo_110kv;
    private int num_35kv;
    private double kilo_35kv;
    private String state2;
    private String time;
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
//        list = (List<WeekListBean>) getIntent().getSerializableExtra("list");
//        lineList = (List<Tower>) getIntent().getSerializableExtra("linelist");

//        num_total = getIntent().getIntExtra("num_total", 0);
//        kilo_total = getIntent().getDoubleExtra("kilo_total", 0);
//        num_110kv = getIntent().getIntExtra("110kv_num", 0);
//        kilo_110kv = getIntent().getDoubleExtra("110kv_kolo", 0);
//        num_35kv = getIntent().getIntExtra("35kv_num", 0);
//        kilo_35kv = getIntent().getDoubleExtra("35kv_kolo", 0);

//        DecimalFormat decimalFormat = new DecimalFormat("0.00");
//        monthLineTotal.setText("杆段总数 : " + num_total + "条");
//        monthLineKiloTotal.setText("总公里数 : " + decimalFormat.format(kilo_total) + " km");
//        monthLine110kvNum.setText("110kv线路总数 : " + num_110kv + "条");
//        monthLine110kvKilo.setText("公里数 : " + decimalFormat.format(kilo_110kv) + " km");
//        monthLine35kvNum.setText("35kv线路总数 : " + num_35kv + "条");
//        monthLine35kvKilo.setText("公里数 : " + decimalFormat.format(kilo_35kv) + " km");
//        year = getIntent().getIntExtra("year", 0);
//        week = getIntent().getIntExtra("week", 0);
//        WeekListBean weekListBean = list.get(0);

        time = DateUatil.getCurMonth();
        String[] years = time.split("年");
        String[] months = years[1].split("月");
//        month = Integer.parseInt(months[0]) + "";
        year = Integer.valueOf(years[0]);
        week = TimeUtil.getCurrWeek() + 1;
        String from = getIntent().getStringExtra("from");
        if ("todoWeek".equals(from)) {
            year = getIntent().getIntExtra("year", 2019);
            week = getIntent().getIntExtra("week", 25);
        }
        mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
            // 设置监听器。
            nextPlanRv.setSwipeMenuCreator(mSwipeMenuCreator);

            // 菜单点击监听。
            nextPlanRv.setOnItemMenuClickListener(mItemMenuClickListener);
        }
        planTotalTitle.setText(week + "周工作计划汇总");
        String nextBeginTime = TimeUtil.getFirstDayOfWeek(year, week);
        String nextEndTime = TimeUtil.getLastDayOfWeek(year, week);
        titleName.setText("第" + week + "周计划(" + nextBeginTime + "至" + nextEndTime + ")");
        depId = SPUtil.getDepId(this);
        state = getIntent().getStringExtra("audit_status");

        //判断
        if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && ("0".equals(state) || "4".equals(state))) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("提交");
        } else if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && "1".equals(state)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("撤回");
        } else if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            state2 = "1,2,3";
            depId = null;
            taskScreen.setVisibility(View.VISIBLE);
            if ("1".equals(state)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审核");
            }
        } else if (mJobType.contains(Constant.RUN_SUPERVISOR)) {
            state2 = "1,2,3";
            depId = null;
            taskScreen.setVisibility(View.VISIBLE);
            if ("2".equals(state)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审核");
            }
        }
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("getDep")) {
                    depId = type.split("@")[1];
                    getWeekList();
                }
            }
        });
        getWeekList();
        getDepList();
    }

    //获取下周计划
    public void getWeekList() {
        ProgressDialog.show(this, false, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getWeekList(String.valueOf(year), String.valueOf(week), depId, state2, "line_id,name")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WeekListBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<WeekListBean>> t) throws Exception {
                        num_total = 0;
                        kilo_total = 0;
                        list = t.getResults();
//                        monthPlanAdapter.setNewData(results);
                        lineList = getData(list);
                        for (int i = 0; i < list.size(); i++) {
                            WeekListBean weekListBean = list.get(i);
                            num_total++;
                            kilo_total += weekListBean.getTowers_range();
                            if (lineNum.indexOf(weekListBean.getLine_id()) == -1) {
                                lineNum.add(weekListBean.getLine_id());
                            }
                        }
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        monthLineTotal.setText("工作线路 : " + lineNum.size() + "条");
                        monthLineKiloTotal.setText( decimalFormat.format(kilo_total) + "km");
                        monthLine110kvNum.setText("工作杆段 : " + num_total + "段");
                        monthLine110kvKilo.setText(decimalFormat.format(kilo_total) + "km");

                        state = list.get(0).getAudit_status();
                        if ("0".equals(state)) {
                            taskAddIv.setVisibility(View.VISIBLE);

                            nextPlanRv.setSwipeItemMenuEnabled(true);
                        } else {
                            taskAddIv.setVisibility(View.GONE);
                            nextPlanRv.setSwipeItemMenuEnabled(false);

                        }
                        LinearLayoutManager manager = new LinearLayoutManager(NextWeekPlanActivity.this);
                        nextPlanRv.setLayoutManager(manager);
                        monthPlanAdapter = new NextWeekPlanAdapter(R.layout.fragment_plan_item, list, state, mJobType);
                        nextPlanRv.setAdapter(monthPlanAdapter);
                        adapterClick();
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //获取所有班组
    public void getDepList() {

        BaseRequest.getInstance().getService()
                .getDepList("SYS_DEP", "ID,name", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DepBean>>(NextWeekPlanActivity.this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DepBean>> t) throws Exception {
                        depList = t.getResults();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }

    public void adapterClick() {

        monthPlanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(NextWeekPlanActivity.this, WeekPlanDetailActivity.class);
                if (list.get(position).getWeek_id() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bean", list.get(position));
                    intent.putExtras(bundle);

                } else {
                    intent.setClass(NextWeekPlanActivity.this, SpecialPlanDetailActivity.class);
                    intent.putExtra("from", "week");
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bean", list.get(position));
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.task_screen, R.id.task_add_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.task_add_iv:
                Intent intent = new Intent(this, AddWeekPlanActivity.class);
                intent.putExtra("year", year);
                intent.putExtra("week", week);
                startActivityForResult(intent, 10);
                break;
            case R.id.title_setting:
                submit(lineList);
                break;
            case R.id.task_screen:
                if (depList == null || depList.size() == 0) {
                    Toast.makeText(this, "暂未获取班组信息,请稍后再试", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (popWinShare == null) {
                    //自定义的单击事件
                    popWinShare = new PopListDialog(this, Utils.dip2px(this, 100), Utils.dip2px(this, 31) * depList.size(), depList);
                    //监听窗口的焦点事件，点击窗口外面则取消显示
                    popWinShare.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                popWinShare.dismiss();
                            }
                        }
                    });
                }
                //设置默认获取焦点
                popWinShare.setFocusable(true);
                //以某个控件的x和y的偏移量位置开始显示窗口
                popWinShare.showAsDropDown(taskScreen, 0, 0);
                //如果窗口存在，则更新
                popWinShare.update();
                break;
        }
    }

    //提交月计划审核
    public void submit(List<Tower> lineList) {
        if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            CancelOrOkDialog dialog = new CancelOrOkDialog(this, "审核", "不同意", "同意") {
                @Override
                public void ok() {
                    super.ok();
                    submitMonthPlan(lineList, "2");   //同意
                    dismiss();
                }

                @Override
                public void cancle() {
                    super.cancle();
                    submitMonthPlan(lineList, "4");  //不同意
                    dismiss();
                }
            };
            dialog.show();
        } else if (mJobType.contains((Constant.RUNNING_SQUAD_LEADER))) {
            if ("0".equals(state)) {
                CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否提交审核", "取消", "确定") {
                    @Override
                    public void ok() {
                        super.ok();
                        submitMonthPlan(lineList, "1");   //同意
                        dismiss();
                    }

                    @Override
                    public void cancle() {
                        super.cancle();
                        dismiss();
                    }
                };
                dialog.show();
            } else {
                CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否撤回计划", "取消", "确定") {
                    @Override
                    public void ok() {
                        super.ok();
                        submitMonthPlan(lineList, "0");   //同意
                        dismiss();
                    }

                    @Override
                    public void cancle() {
                        super.cancle();
                        dismiss();
                    }
                };
                dialog.show();
            }


        } else if (mJobType.contains(Constant.RUN_SUPERVISOR)) {
            CancelOrOkDialog dialog = new CancelOrOkDialog(this, "审核", "不同意", "同意") {
                @Override
                public void ok() {
                    super.ok();
                    submitMonthPlan(lineList, "3");   //同意
                    dismiss();
                }

                @Override
                public void cancle() {
                    super.cancle();
                    submitMonthPlan(lineList, "4");  //不同意
                    dismiss();
                }
            };
            dialog.show();
        }
    }

    //提交周计划审核
    public void submitMonthPlan(List<Tower> list, String status) {
        ProgressDialog.show(NextWeekPlanActivity.this, false, "正在加载中...");
        SubmitPlanReqBean bean = new SubmitPlanReqBean();
        bean.setYear(year + "");
        bean.setWeek(week + "");
        bean.setAudit_status(status);
        bean.setFrom_user_id(SPUtil.getUserId(NextWeekPlanActivity.this));
        bean.setTowers(list);
        BaseRequest.getInstance().getService()
                .submitWeekPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            if ("1".equals(status)) {
                                Toast.makeText(NextWeekPlanActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(NextWeekPlanActivity.this, "审核成功", Toast.LENGTH_SHORT).show();
                            }
                            RxRefreshEvent.publish("refreshTodo");
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(NextWeekPlanActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //删除周计划
    public void deleteWeekPlan(String id, int position) {
        ProgressDialog.show(NextWeekPlanActivity.this, false, "正在加载中...");
        BaseRequest.getInstance().getService()
                .deleteWeekPlan(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            num_total--;
                            kilo_total = kilo_total - list.get(position).getTowers_range();
                            DecimalFormat decimalFormat = new DecimalFormat("0.00");
                            monthLineTotal.setText("杆段 : " + num_total + "条");
                            monthLineKiloTotal.setText(decimalFormat.format(kilo_total) + " km");
                            list.remove(position);
                            monthPlanAdapter.notifyItemRemoved(position);
                            if (list.size()==0){
                            setResult(RESULT_OK);}
                        } else {
                            Toast.makeText(NextWeekPlanActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    private List<Tower> getData(List<WeekListBean> results) {
        List<Tower> lineList = new ArrayList<>();
        if (results != null) {
            for (int j = 0; j < results.size(); j++) {
                WeekListBean weekListBean = results.get(j);
//                num_total++;
//                kilo_total += monthPlanBean.getLine_length();
//                if (monthPlanBean.getVoltage_level().contains("110")) {
//                    kilo_110kv = kilo_110kv + monthPlanBean.getLine_length();
//                    num_110kv++;
//                } else if (monthPlanBean.getVoltage_level().contains("35")) {
//                    kilo_35kv = kilo_35kv + monthPlanBean.getLine_length();
//                    num_35kv++;
//                }
                //当身份是专责时，获取需要审批的列表
                if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(weekListBean.getAudit_status())) {
                    Tower lineBean = new Tower();
                    lineBean.setId(weekListBean.getId());
                    lineList.add(lineBean);
                    //当身份是主管时，获取需要审批的列表
                } else if (mJobType.contains(Constant.RUN_SUPERVISOR) && "2".equals(weekListBean.getAudit_status())) {
                    Tower lineBean = new Tower();
                    lineBean.setId(weekListBean.getId());
                    lineList.add(lineBean);
                    //当身份是班长时，获取需要审核的列表
                } else if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && ("0".equals(weekListBean.getAudit_status()) || "1".equals(weekListBean.getAudit_status()) || "4".equals(weekListBean.getAudit_status()))) {
                    Tower lineBean = new Tower();
                    lineBean.setId(weekListBean.getId());
                    lineList.add(lineBean);
                }
            }
        }
        return lineList;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            getWeekList();
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
            SwipeMenuItem deleteItem1 = new SwipeMenuItem(NextWeekPlanActivity.this);
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
            deleteWeekPlan(list.get(position).getId(), position);
            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
        }
    };
}
