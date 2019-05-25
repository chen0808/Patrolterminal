package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*其他人员周任务*/
public class OverhaulWeekTaskFrgment extends BaseFragment {
    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_add)
    ImageView taskAdd;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;

    private OverhaulWeekTaskAdapter weekTaskAdapter;
    private String time;
    private List<OverhaulMonthBean> results = new ArrayList<>();

    private List<String> years = new ArrayList<>();
    private List<List<String>> months = new ArrayList<>();
    private List<List<List<String>>> weeks = new ArrayList<>();

    //private TimePickerView pvTime;
    private String year,month/*,day*/;
    private String week;
    private String userId;
    private String sign;
    private Disposable subscribe;

    private String jobType;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {

        //time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        initDate();
        taskAdd.setVisibility(View.GONE);
        time = SPUtil.getString(getContext(), "date", "overhaulTime", DateUatil.getTime(new Date(System.currentTimeMillis())));
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        userId = SPUtil.getUserId(getContext());

        if (jobType.equals(Constant.REFURBISHMENT_LEADER)) {
            sign = "3";
        }else if (jobType.equals(Constant.ACCEPTANCE_CHECK_SPECIALIZED)){
            sign = "1";
        }else if (jobType.equals(Constant.SAFETY_SPECIALIZED)) {
            sign = "2";
        }else if (jobType.equals(Constant.REFURBISHMENT_MEMBER)) {
            sign = "4";
        }

//        if (jobType.equals(Constant.REFURBISHMENT_LEADER)|| jobType.equals(Constant.POWER_CONSERVATION_SPECIALIZED)
//                || jobType.equals(Constant.ACCEPTANCE_CHECK_SPECIALIZED)|| jobType.equals(Constant.SAFETY_SPECIALIZED)) { //班长发布周检修工作
////            userId = SPUtil.getUserId(getContext());
//            status="1,2,3";
//
//        } else if (jobType.equals(Constant.REFURBISHMENT_SPECIALIZED)) {   //专责发布周检修工作
//
//        } else if (jobType.equals(Constant.REFURBISHMENT_MEMBER)) {  //班员接受周检修工作   TODO  班员里面分负责人和普通班员
//            userId = SPUtil.getUserId(getContext());
//            status="2,3";
//        }

        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        week = DateUatil.getWeekNum()+"";
        taskTitle.setText("周计划列表");
        taskDate.setText(time + "第" + week + "周");

        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        weekTaskAdapter = new OverhaulWeekTaskAdapter(R.layout.fragment_overhaul_week_item, results);
        planRv.setAdapter(weekTaskAdapter);
        weekTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent();
                intent.setClass(getContext(), OverhaulWeekPlanDetailActivity.class);
                Bundle bundle = new Bundle();
                if (results.get(position) != null) {
                    bundle.putString("id", results.get(position).getId());
                }
                intent.putExtras(bundle);
                startActivityForResult(intent, 1001);
            }
        });
        //getDayList();
        getWeekList();
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                     if (s.startsWith("publishRepair")){
                         getWeekList();
                     }
            }
        });
    }


    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);
        }
    };


   //其他人员获取周任务列表
    public void getWeekList() {
        Log.w("linmeng", "sign:" + sign);

            BaseRequest.getInstance().getService()
                    .getOverhaulTaskList(year,month,week, userId, sign)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<OverhaulMonthBean>>(getContext()) {

                        @Override
                        protected void onSuccees(BaseResult<List<OverhaulMonthBean>> t) throws Exception {
                            results = t.getResults();
                            weekTaskAdapter.setNewData(results);
                        }

                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                            Log.e("fff", e.toString());
                        }
                    });
    }


    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
        }
    };


    @OnClick({R.id.task_date, R.id.task_add/*,R.id.plan_create*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showWeek();
                break;
            case R.id.task_add:
                Intent intent = new Intent(getContext(), OverhaulAddWeekPlanActivity.class);
                startActivityForResult(intent,10);
                break;
//            case R.id.plan_create:
//                Intent intent1 = new Intent(getContext(), CreatePlanActivity.class);
//                intent1.putExtra("from",0);
//                startActivityForResult(intent1,10);
//                break;
        }
    }


    //展示月份
    public void showWeek() {

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                //返回的分别是三个级别的选中位置
                String time =  years.get(options1)  + months.get(options1).get(option2)  + weeks.get(options1).get(option2).get(options3) ;
                year=years.get(options1).split("年")[0];
                month= months.get(options1).get(option2).split("月")[0];
                week=  weeks.get(options1).get(option2).get(options3).substring(1,2);
                taskDate.setText(time);
                getWeekList();
            }
        }).build();
        pvOptions.setPicker(years, months, weeks);
        pvOptions.setSelectOptions(years.indexOf(year + "年"),Integer.parseInt(month)-1,Integer.parseInt(week)-1);
        pvOptions.show();
    }


    //初始化月份数据
    public void initDate() {
        List<List<String>> list =new ArrayList<>();
        for (int i = 2017; i < 2100; i++) {
            years.add(i + "年");
            List<String> monthList=new ArrayList<>();

            for (int j= 1; j< 13; j++) {
                monthList.add(j + "月");
                List<String> weekList=new ArrayList<>();

                int weekNumOfMonth = DateUatil.getWeekNumOfMonth(i + "", j + "");
                for (int y = 1; y < weekNumOfMonth+1; y++) {
                    weekList.add("第" +y + "周");
                }
                list.add(weekList);
                months.add(monthList);

            }

            weeks.add(list);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001){
            getWeekList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe!=null){
            subscribe.dispose();
        }
    }
}
