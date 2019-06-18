package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.CreatePlanActivity;
import com.patrol.terminal.base.BaseActivity;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/*检修其他人员主界面*/
public class OverhaulWeekPlanActivity extends BaseActivity {

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
    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.plan_submit)
    TextView planSubmit;
    @BindView(R.id.plan_create)
    TextView planCreate;
    @BindView(R.id.task_add)
    ImageView taskAdd;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;

    private OverhaulWeekTaskAdapter weekAdapter;
    private String time;
    private List<OverhaulMonthBean> results = new ArrayList<>();

    private List<String> years = new ArrayList<>();
    private List<List<String>> months = new ArrayList<>();
    private List<List<List<String>>> weeks = new ArrayList<>();

    //private TimePickerView pvTime;
    private String year,month/*,day*/;
    private String week;
    private String status;
    private String userId;
    private Disposable subscribe;

    private String jobType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overhaul_week_plan);
        ButterKnife.bind(this);
        initData();
    }

    protected void initData() {
         titleName.setText("周检修计划");
        //time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        initDate();
        taskAdd.setVisibility(View.GONE);
        time = SPUtil.getString(this, "date", "overhaulTime", DateUatil.getTime(new Date(System.currentTimeMillis())));
        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");



//        if (jobType.contains(Constant.REFURBISHMENT_LEADER)|| jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)
//                || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)|| jobType.contains(Constant.SAFETY_SPECIALIZED)) { //班长发布周检修工作
////            userId = SPUtil.getUserId(this());
//            status="1,2,3";
//
//        } else if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {   //专责发布周检修工作
//
//        } else if (jobType.contains(Constant.REFURBISHMENT_MEMBER)) {  //班员接受周检修工作   TODO  班员里面分负责人和普通班员
//            userId = SPUtil.getUserId(this());
//            status="2,3";
//        }
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        week = DateUatil.getWeekNum()+"";
        taskTitle.setText("周计划列表");
        //taskDate.setText(time + "第" + week + "周");

        Map<String ,Object> taskDateStr = DateUatil.getScopeForWeeks(Integer.parseInt(year), Integer.parseInt(month),  Integer.parseInt(week));
        String beginDate = (String)taskDateStr.get("beginDate");
        String endDate = (String)taskDateStr.get("endDate");
        taskDate.setText(beginDate + "至" + endDate + "（" +  "第" + week + "周"+ "）");

        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        planRv.setLayoutManager(manager);
        weekAdapter = new OverhaulWeekTaskAdapter(R.layout.fragment_overhaul_week_item, results, 1);
        planRv.setAdapter(weekAdapter);
        weekAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent();
                intent.setClass(OverhaulWeekPlanActivity.this, OverhaulWeekPlanDetailActivity.class);
                Bundle bundle = new Bundle();
                if (results.get(position) != null) {
                    bundle.putString("id", results.get(position).getRepair_id());
                }
                intent.putExtras(bundle);
                startActivity(intent);
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

//            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
//            // 2. 指定具体的高，比如80;
//            // 3. WRAP_CONTENT，自身高度，不推荐;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            SwipeMenuItem deleteItem = new SwipeMenuItem(this());
//            deleteItem.setWidth(width);
//            deleteItem.setHeight(height);
//            deleteItem.setTextSize(15);
//            deleteItem.setTextColorResource(R.color.white);
//            deleteItem.setBackground(R.color.orange_vip);
//            deleteItem.setText("编辑");
//            SwipeMenuItem deleteItem1 = new SwipeMenuItem(this());
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


    //获取周计划列表
    public void getWeekList() {
           //其他人员获取周计划
//            BaseRequest.getInstance().getService()
//                    .getOverhaulWeekList(year,month,week,SPUtil.getUserId(this)/*,status*/)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new BaseObserver<List<OverhaulMonthBean>>(this) {
//
//                        @Override
//                        protected void onSuccees(BaseResult<List<OverhaulMonthBean>> t) throws Exception {
//                            results = t.getResults();
//                            weekAdapter.setNewData(results);
//                        }
//
//                        @Override
//                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                            Log.e("fff", e.toString());
//                        }
//                    });
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


    @OnClick({R.id.title_back,R.id.task_date, R.id.task_add,R.id.plan_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showWeek();
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.task_add:
                Intent intent = new Intent(this, OverhaulAddWeekPlanActivity.class);
                startActivityForResult(intent,10);
                break;
            case R.id.plan_create:
                Intent intent1 = new Intent(this, CreatePlanActivity.class);
                intent1.putExtra("from",0);
                startActivityForResult(intent1,10);
                break;
        }
    }

   /* public void showDay() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
//是否只显示中间选中项的label文字，false则每项item全部都带有label。
        pvTime = new TimePickerBuilder(this(), new OnTimeSelectListener() {
               @Override
               public void onTimeSelect(Date date, View v) {//选中事件回调
                   time = DateUatil.getDay(date);
                   taskDate.setText(time);

               }
           })
                   .setDate(selectedDate)
                   .setRangDate(startDate, endDate)
                   .setContentTextSize(18)
                   .setLineSpacingMultiplier(1.2f)
                   .setTextXOffset(0, 0, 0, 40, 0, -40)
                   .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                   .setDividerColor(0xFF24AD9D)
                   .setType(new boolean[]{true, true, true, false, false, false})
                   .setLabel("年", "月", "日", "时", "分", "秒")
                   .build();
        pvTime.show();
    }*/

    //展示月份
    public void showWeek() {

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                //返回的分别是三个级别的选中位置
                String time =  years.get(options1)  + months.get(options1).get(option2)  + weeks.get(options1).get(option2).get(options3) ;
                year=years.get(options1).split("年")[0];
                month= months.get(options1).get(option2).split("月")[0];
                week=  weeks.get(options1).get(option2).get(options3).substring(1,2);
                //taskDate.setText(time);

                Map<String ,Object> taskDateStr = DateUatil.getScopeForWeeks(Integer.parseInt(year), Integer.parseInt(month),  Integer.parseInt(week));
                String beginDate = (String)taskDateStr.get("beginDate");
                String endDate = (String)taskDateStr.get("endDate");
                taskDate.setText(beginDate + "至" + endDate + "（" +  "第" + week + "周"+ "）");

                getWeekList();
            }
        }).build();
        pvOptions.setPicker(years, months, weeks);
        pvOptions.setSelectOptions(years.indexOf(year + "年"),Integer.parseInt(month)-1,Integer.parseInt(week)-1);
        pvOptions.show();
    }

    /*public void inteDate(){
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0])+"";
        year =years[0];
        day=Integer.parseInt(days[0])+"";
    }*/

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
        if (requestCode==10&&resultCode==-1){
            //getDayList();
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
