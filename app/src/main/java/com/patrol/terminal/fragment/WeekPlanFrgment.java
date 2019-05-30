package com.patrol.terminal.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddWeekPlanActivity;
import com.patrol.terminal.activity.SpecialPlanDetailActivity;
import com.patrol.terminal.activity.TemporaryWeekActivity;
import com.patrol.terminal.activity.WeekPlanDetailActivity;
import com.patrol.terminal.adapter.WeekPlanAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.SubmitPlanReqBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.bean.WeekListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.PopMenmuDialog;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeekPlanFrgment extends BaseFragment {


    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_add)
    ImageView taskAdd;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.plan_submit)
    TextView planSubmit;
    @BindView(R.id.plan_create)
    TextView planCreate;

    private TimePickerView pvTime;
    private String time;
    private WeekPlanAdapter weekPlanAdapter;
    private List<String> years = new ArrayList<>();
    private List<List<String>> months = new ArrayList<>();
    private List<List<List<String>>> weeks = new ArrayList<>();
    private String year;
    private String month;
    private String week;
    private String state;
    private List<Tower> lineList = new ArrayList<>();
    private String mJobType;
    private String depId;
    private List<WeekListBean> results=new ArrayList<>();
    private String currWeek;
    private PopMenmuDialog popWinShare;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        initdata();
        depId = SPUtil.getDepId(getContext());
        mJobType = SPUtil.getString(getActivity(), Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            state="1,2,3,4,5";
            depId=null;
            planSubmit.setText("审核");
            taskAdd.setVisibility(View.INVISIBLE);
            planCreate.setVisibility(View.INVISIBLE);
        }
        time = DateUatil.getCurMonth();
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        week = DateUatil.getWeekNum() + "";
        currWeek = DateUatil.getWeekNum() + "";
        taskTitle.setText("周计划列表");
        Map<String, Object> scopeForWeeks = TimeUtil.getScopeForWeeks(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(currWeek));
       String beginDate = TimeUtil.dateToDate((String) scopeForWeeks.get("beginDate"));
        String  endDate = TimeUtil.dateToDate((String) scopeForWeeks.get("endDate"));
        String startDay=beginDate.split("月")[1];
        String endDay=endDate.split("月")[1];
        taskDate.setText(time +startDay+"-"+endDay+ "(" + currWeek + ")");

        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        weekPlanAdapter = new WeekPlanAdapter(R.layout.fragment_plan_item, results);
        planRv.setAdapter(weekPlanAdapter);
        weekPlanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), WeekPlanDetailActivity.class);
                if (results.get(position).getWeek_id()!=null) {
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("bean", results.get(position));
                    intent.putExtras(bundle);

                }else {
                        intent.setClass(getContext(), SpecialPlanDetailActivity.class);
                        intent.putExtra("from","week");
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("bean", results.get(position));
                        intent.putExtras(bundle);
                    }
                startActivity(intent);
            }
        });
        getWeekList();
    }


    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
//            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);
//
//            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
//            // 2. 指定具体的高，比如80;
//            // 3. WRAP_CONTENT，自身高度，不推荐;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
//            deleteItem.setWidth(width);
//            deleteItem.setHeight(height);
//            deleteItem.setTextSize(15);
//            deleteItem.setTextColorResource(R.color.white);
//            deleteItem.setBackground(R.color.orange_vip);
//            deleteItem.setText("编辑");
//            SwipeMenuItem deleteItem1 = new SwipeMenuItem(getContext());
//            deleteItem1.setWidth(width);
//            deleteItem1.setHeight(height);
//            deleteItem1.setBackground(R.color.home_red);
//            deleteItem1.setTextSize(15);
//            deleteItem1.setTextColorResource(R.color.white);
//            deleteItem1.setText("删除");
//            // 各种文字和图标属性设置。
//            rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。
//            rightMenu.addMenuItem(deleteItem1); // 在Item右侧添加一个菜单。
//            // 注意：哪边不想要菜单，那么不要添加即可。
        }
    };


    public void getWeekList() {
        BaseRequest.getInstance().getService()
                .getWeekList(year, month, week, depId,state,"type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WeekListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<WeekListBean>> t) throws Exception {
                        lineList.clear();
                        results = t.getResults();
                        weekPlanAdapter.setNewData(results);
                        for (int i = 0; i < results.size(); i++) {
                            WeekListBean weekListBean = results.get(i);
                                //当身份是运行班专责时，获取到需要审核的列表
                            if (weekListBean.getWeek_id()!=null){
                                if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(results.get(i).getAudit_status())) {
                                    WeekListBean bean = results.get(i);
                                    Tower lineBean = new Tower();
                                    lineBean.setId(bean.getId());
                                    lineList.add(lineBean);
                                    //当身份是运行班专责时，获取到需要发布的列表
                                } else if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && ("0".equals(results.get(i).getAudit_status())||"3".equals(results.get(i).getAudit_status()))){
                                    WeekListBean bean = results.get(i);
                                    Tower lineBean = new Tower();
                                    lineBean.setId(bean.getId());
                                    lineList.add(lineBean);
                                }
                            }
                        }
                        if (lineList.size()!=0){
                            planSubmit.setVisibility(View.VISIBLE);
                        }else {
                            planSubmit.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }


    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

//            // 左侧还是右侧菜单：
//            int direction = menuBridge.getDirection();
//            // 菜单在Item中的Position：
//            int menuPosition = menuBridge.getPosition();
        }
    };


    @OnClick({R.id.task_date, R.id.task_add, R.id.plan_create, R.id.plan_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showWeek();
                break;
            case R.id.plan_submit:
                if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) || mJobType.contains(Constant.RUN_SUPERVISOR)) {
                    CancelOrOkDialog dialog = new CancelOrOkDialog(getActivity(), "一键审核", "不同意", "同意") {
                        @Override
                        public void ok() {
                            super.ok();
                            submitWeekPlan("2");   //同意
                            dismiss();
                        }

                        @Override
                        public void cancel() {
                            super.cancel();
                            submitWeekPlan("3");  //不同意
                            dismiss();
                        }
                    };
                    dialog.show();
                } else if (mJobType.contains((Constant.RUNNING_SQUAD_LEADER))) {
                    CancelOrOkDialog dialog = new CancelOrOkDialog(getActivity(), "是否一键提交审核", "取消", "确定") {
                        @Override
                        public void ok() {
                            super.ok();
                            submitWeekPlan("1");   //同意
                            dismiss();
                        }

                        @Override
                        public void cancel() {
                            super.cancel();
                            dismiss();
                        }
                    };
                    dialog.show();
                }
                break;
            case R.id.task_add:
                if (popWinShare == null) {
                    //自定义的单击事件
                    OnClickLintener paramOnClickListener = new OnClickLintener();
                    popWinShare = new PopMenmuDialog(getActivity(), paramOnClickListener, dip2px(getContext(), 140), dip2px(getContext(), 80));
                    //监听窗口的焦点事件，点击窗口外面则取消显示
                    popWinShare.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                popWinShare.dismiss();
                            }
                        }
                    });
                    popWinShare.setTitle("制定下周计划","制定临时计划");
                }

                //设置默认获取焦点
                popWinShare.setFocusable(true);
                //以某个控件的x和y的偏移量位置开始显示窗口
                popWinShare.showAsDropDown(taskAdd, 0, 0);
                //如果窗口存在，则更新
                popWinShare.update();
                break;

            case R.id.plan_create:
                break;
        }
    }
    class OnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.all:
                    startActivityForResult(new Intent(getContext(), AddWeekPlanActivity.class), 10);
                    break;
                case R.id.popmenmu1:
                    startActivityForResult(new Intent(getContext(), TemporaryWeekActivity.class), 10);
                    break;
                case R.id.popmenmu2:

                    break;
                default:
                    break;
            }
            popWinShare.dismiss();
        }

    }
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    //提交周计划审核
    public void submitWeekPlan(String status) {
        SubmitPlanReqBean bean = new SubmitPlanReqBean();
        bean.setAudit_status(status);
        bean.setTowers(lineList);
        BaseRequest.getInstance().getService()
                .submitWeekPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            if ("1".equals(status)){
                                Toast.makeText(getContext(), "一键提交成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "一键审核成功", Toast.LENGTH_SHORT).show();
                            }
                            getWeekList();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    //初始化月份数据
    public void initdata() {
        for (int i = 2018; i < 2100; i++) {
            years.add(i + "年");
            ArrayList<String> options2Items_01 = new ArrayList<>();
            List<List<String>> toptions2Items1 = new ArrayList<>();
            for (int j = 1; j < 13; j++) {
                options2Items_01.add(j + "月");

                List<String> options3Items_01 = new ArrayList<>();
                int weekNumOfMonth = DateUatil.getWeekNumOfMonth(i + "", j + "");
                for (int y = 1; y < weekNumOfMonth + 1; y++) {

                    Map<String, Object> scopeForWeeks = TimeUtil.getScopeForWeeks(i,j,y);
                    String beginDate =TimeUtil.dateToDay((String) scopeForWeeks.get("beginDate"));
                    String endDate =TimeUtil.dateToDay((String) scopeForWeeks.get("endDate"));
                    options3Items_01.add(beginDate+"-" + endDate+ "("+y+")");
                }
                toptions2Items1.add(options3Items_01);

            }
            weeks.add(toptions2Items1);
            months.add(options2Items_01);
        }

    }

    //展示月份
    public void showWeek() {

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                //返回的分别是三个级别的选中位置
                String time = years.get(options1) + months.get(options1).get(option2) + weeks.get(options1).get(option2).get(options3);
                year = years.get(options1).split("年")[0];
                month = months.get(options1).get(option2).split("月")[0];
                String date = weeks.get(options1).get(option2).get(options3);
                week =date.substring(date.length()-2,date.length()-1);
                taskDate.setText(time);
                getWeekList();
            }
        }).build();
        pvOptions.setPicker(years, months, weeks);
        pvOptions.setSelectOptions(years.indexOf(year + "年"), Integer.parseInt(month) - 1, Integer.parseInt(currWeek) - 1);
        pvOptions.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            getWeekList();
        }
    }
}
