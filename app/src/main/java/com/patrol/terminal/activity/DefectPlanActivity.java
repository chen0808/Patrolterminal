package com.patrol.terminal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AllControlCarBean;
import com.patrol.terminal.bean.CardControl;
import com.patrol.terminal.bean.ControlCardBean;
import com.patrol.terminal.bean.DefectFragmentDetailBean;
import com.patrol.terminal.bean.DefectPlanDetailBean;
import com.patrol.terminal.bean.DepPersonalBean;
import com.patrol.terminal.bean.MakeDefectPlanBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.SelectWorkerBean;
import com.patrol.terminal.bean.TaskDefectUser;
import com.patrol.terminal.overhaul.OverhaulWeekPlanDetailActivity;
import com.patrol.terminal.overhaul.OverhaulZzWeekTaskDetailActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.DangerSubmitView;
import com.patrol.terminal.widget.ProgressDialog;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DefectPlanActivity extends BaseActivity {

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
    @BindView(R.id.danger_patrol_content)
    DangerSubmitView dangerPatrolContent;
    @BindView(R.id.danger_patrol_line)
    DangerSubmitView dangerPatrolLine;
    @BindView(R.id.danger_patrol_tower)
    DangerSubmitView dangerPatrolTower;
    @BindView(R.id.danger_patrol_team)
    TextView dangerPatrolTeam;
    @BindView(R.id.danger_patrol_personal)
    TextView dangerPatrolPersonal;
    @BindView(R.id.danger_patrol_start_time)
    TextView dangerPatrolStartTime;
    @BindView(R.id.danger_patrol_end_time)
    TextView dangerPatrolEndTime;
    @BindView(R.id.danger_patrol_save)
    TextView dangerPatrolSave;
    @BindView(R.id.danger_patrol_idea)
    EditText dangerPatrolIdea;
    @BindView(R.id.control_card)
    TextView controlCard;
    @BindView(R.id.ns_control_card)
    NiceSpinner nsControlCard;
    @BindView(R.id.ll_control_card)
    LinearLayout llControlCard;
    @BindView(R.id.danger_patrol_no)
    TextView dangerPatrolNo;
    @BindView(R.id.danger_patrol_yes)
    TextView dangerPatrolYes;
    @BindView(R.id.defect_plan_auditor_ll)
    LinearLayout defectPlanAuditorLl;
    private List<DepPersonalBean.UserListBean> personalList = new ArrayList<>();
    private String[] personals;
    private AlertDialog personalDialog;
    private AlertDialog personalGroupDialog;
    private int dutyPositon = 0;
    private String teamName;
    private ArrayList<String> personalSelect = new ArrayList<>();
    private long start = 0;
    private long end = 0;
    private String startTime;
    private String endTime;
    private String line_name;
    private String tower_name;
    private String find_dep_id;
    private String id;
    private String mJobType;
    private String find_dep_name;
    private String tower_id;
    private String line_id;
    private List<String> nameType = new ArrayList<>();
    private ControlCardBean controlCardId;
    private AllControlCarBean allControlCarBean;
    private  SelectWorkerBean selectWorkerBean = new SelectWorkerBean();
    private String teamId;
    private DefectPlanDetailBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_plan);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("消缺计划");
       String  defect_id = getIntent().getStringExtra("id");
        if (defect_id==null){
            id = getIntent().getStringExtra("task_id");
        }else {
            id=defect_id;
        }

        String audit_status = getIntent().getStringExtra("audit_status");
        mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && audit_status == null&&defect_id!=null) {
            defectPlanAuditorLl.setVisibility(View.GONE);
            dangerPatrolSave.setVisibility(View.VISIBLE);
            getDefectDeatail(id);
            getPersonal();
        } else if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(audit_status)) {
            defectPlanAuditorLl.setVisibility(View.VISIBLE);
            dangerPatrolSave.setVisibility(View.GONE);
            getDefectPlanDetail();
            getCardControl(id);
            controlCard();
            setEnable();
        } else if (mJobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
            controlCard.setText("填写控制卡");
            llControlCard.setVisibility(View.VISIBLE);
            defectPlanAuditorLl.setVisibility(View.GONE);
            dangerPatrolSave.setVisibility(View.GONE);
            getDefectPlanDetail();
            getCardControl(id);
            controlCard();
            setEnable();
        } else {
            llControlCard.setVisibility(View.VISIBLE);
            controlCard.setText("查看控制卡");
            nsControlCard.setVisibility(View.GONE);
            defectPlanAuditorLl.setVisibility(View.GONE);
            dangerPatrolSave.setVisibility(View.GONE);
            getDefectPlanDetail();
            getCardControl(id);
            controlCard();
            setEnable();
        }

    }

    public void setEnable() {
        dangerPatrolIdea.setEnabled(false);
        dangerPatrolIdea.setFocusable(false);
        dangerPatrolTeam.setEnabled(false);
        dangerPatrolTeam.setFocusable(false);
        dangerPatrolPersonal.setEnabled(false);
        dangerPatrolPersonal.setFocusable(false);
        dangerPatrolStartTime.setEnabled(false);
        dangerPatrolStartTime.setFocusable(false);
        dangerPatrolEndTime.setEnabled(false);
        dangerPatrolEndTime.setFocusable(false);
        dangerPatrolIdea.setClickable(false);
        dangerPatrolTeam.setClickable(false);
        dangerPatrolPersonal.setClickable(false);
        dangerPatrolStartTime.setClickable(false);
        dangerPatrolEndTime.setClickable(false);

    }

    @OnClick({R.id.title_back, R.id.danger_patrol_team, R.id.danger_patrol_personal, R.id.danger_patrol_start_time,
            R.id.danger_patrol_end_time, R.id.danger_patrol_save, R.id.danger_patrol_no, R.id.danger_patrol_yes,R.id.control_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.control_card:
                int entenType;
                if ((mJobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)&&teamName.equals(SPUtil.getUserName(this)))) {  //负责人填写状态,提交后不可填写
                    if (allControlCarBean == null) {
                        entenType = Constant.IS_FZR_WRITE;       //负责人填写模式
                    } else {
                        if (allControlCarBean.getCardControl() == null && allControlCarBean.getCardQuality() == null && allControlCarBean.getCardTool().size() == 0) {
                            entenType = Constant.IS_FZR_WRITE;       //负责人填写模式
                        } else {
                            entenType = Constant.IS_FZR_UPDATE;      //负责人更新模式
                        }
                    }

                    Intent intent1 = new Intent(DefectPlanActivity.this, ControlCardActivity.class);
                    intent1.putExtra(Constant.CONTROL_CARD_ENTER_TYPE, entenType);
                    intent1.putExtra("allControlBean", allControlCarBean);    //上次填写的内容存储
                    intent1.putExtra("selectedUserListBeans", selectWorkerBean);
                    intent1.putExtra("bean", bean);
                    intent1.putExtra("from", "yx");
                    intent1.putExtra("id", controlCardId);   //模板
                    //intent1.putExtra("leaderName", leaderName);
                    //intent1.putExtra("leaderId", leaderId);
                    //其他人员进来
                    startActivityForResult(intent1, 1002);
                } else {      //其他人员进来
                    if (allControlCarBean == null) {
                        Toast.makeText(DefectPlanActivity.this, "当前无控制卡！", Toast.LENGTH_SHORT).show();
                    } else {
                        if (allControlCarBean.getCardControl() == null && allControlCarBean.getCardControl() == null && allControlCarBean.getCardTool().size() == 0) {
                            Toast.makeText(DefectPlanActivity.this, "当前无控制卡！", Toast.LENGTH_SHORT).show();
                        } else {
                            entenType = Constant.IS_OTHER_LOOK;
                            Intent intent2 = new Intent(DefectPlanActivity.this, ControlCardActivity.class);
                            intent2.putExtra(Constant.CONTROL_CARD_ENTER_TYPE, entenType); //其他人员查看模式
                            intent2.putExtra("allControlBean", allControlCarBean);
                            intent2.putExtra("from", "yx");
                            intent2.putExtra("id", controlCardId);
                            //intent2.putExtra("leaderName", leaderName);
                            //intent2.putExtra("leaderId", leaderId);
                            startActivityForResult(intent2, 1001);
                        }
                    }
                }
                break;
            case R.id.danger_patrol_team:
                showPersonalGroup();
                break;
            case R.id.danger_patrol_personal:
                showPersonal();
                break;
            case R.id.danger_patrol_start_time:
                showDay(0);
                break;
            case R.id.danger_patrol_end_time:
                showDay(1);
                break;
            case R.id.danger_patrol_no:
                makeDefectPlan("3");
                break;
            case R.id.danger_patrol_yes:
                makeDefectPlan("2");
                break;
            case R.id.danger_patrol_save:
                if (start == 0) {
                    Toast.makeText(this, "请选择起始日期", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (end == 0) {
                    Toast.makeText(this, "请选择结束日期", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(teamName)) {
                    Toast.makeText(this, "请选择负责人", Toast.LENGTH_SHORT).show();
                    return;
                }
                makeDefectPlan("1");
                break;
        }
    }

    //日期选择
    public void showDay(int type) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (type == 0) {
                    startTime = DateUatil.getDate(date);
                    start = date.getTime();
                    if (end < start && end != 0) {
                        if (date.getTime() < System.currentTimeMillis()) {
                            Toast.makeText(DefectPlanActivity.this, "起始时间不能低于起始时间", Toast.LENGTH_SHORT);
                            return;
                        }
                    }
                    if (!startTime.equals(DateUatil.getTime())) {
                        if (date.getTime() < System.currentTimeMillis()) {
                            Toast.makeText(DefectPlanActivity.this, "起始时间不能低于当天", Toast.LENGTH_SHORT);
                            return;
                        }
                    }
                    dangerPatrolStartTime.setText(startTime);
                } else {
                    end = date.getTime();
                    if (end < start && start != 0) {
                        if (date.getTime() < System.currentTimeMillis()) {
                            Toast.makeText(DefectPlanActivity.this, "结束时间不能低于起始时间", Toast.LENGTH_SHORT);
                            return;
                        }
                    }
                    endTime = DateUatil.getDate(date);
                    if (!endTime.equals(DateUatil.getTime())) {
                        if (date.getTime() < System.currentTimeMillis()) {
                            Toast.makeText(DefectPlanActivity.this, "结束时间不能低于当天", Toast.LENGTH_SHORT);
                            return;
                        }
                    }
                    dangerPatrolEndTime.setText(endTime);
                }

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
    }

    //获取每个班组组员列表
    public void getPersonal() {

        BaseRequest.getInstance().getService()
                .getDepPersonal(SPUtil.getDepId(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DepPersonalBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<DepPersonalBean> t) throws Exception {
                        personalList = t.getResults().getUserList();
                        personals = new String[personalList.size()];
                        for (int i = 0; i < personalList.size(); i++) {
                            personals[i] = personalList.get(i).getName();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    //获取消缺计划详情
    public void getDefectPlanDetail() {

        BaseRequest.getInstance().getService()
                .getDefectPlanDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DefectPlanDetailBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<DefectPlanDetailBean> t) throws Exception {
                        bean = t.getResults();
                        line_name = bean.getLine_name();
                        tower_name = bean.getTower_name();
                        find_dep_id = bean.getDeal_dep_id();
                        find_dep_name = bean.getDeal_dep_name();
                        tower_id = bean.getTower_id();
                        line_id = bean.getLine_id();
                        startTime= bean.getDeal_time();
                        endTime= bean.getClose_time();
                        dangerPatrolType.setContent(bean.getCategory_name());
                        dangerPatrolDep.setContent(bean.getDeal_dep_name());
                        dangerPatrolLine.setContent(bean.getLine_name());
                        dangerPatrolTower.setContent(bean.getTower_name());
                        dangerPatrolLevel.setContent(bean.getGrade_name());
                        dangerPatrolContent.setContent(bean.getContent());
                        dangerPatrolIdea.setText(bean.getDeal_notes());
                        dangerPatrolStartTime.setText(bean.getDeal_time());
                        dangerPatrolEndTime.setText(bean.getClose_time());
                        List<DefectPlanDetailBean.TaskDefectUserListBean> taskDefectUserList = bean.getTaskDefectUserList();
                        String names = "";
                        List<SelectWorkerBean.SelectUserInfo> selectUserInfos = new ArrayList<>();
                        for (int i = 0; i < taskDefectUserList.size(); i++) {
                            DepPersonalBean.UserListBean userListBean = new DepPersonalBean.UserListBean();
                            SelectWorkerBean.SelectUserInfo userInfo = new SelectWorkerBean.SelectUserInfo();
                            DefectPlanDetailBean.TaskDefectUserListBean taskDefectUserListBean = taskDefectUserList.get(i);
                            String name = taskDefectUserListBean.getUser_name();
                            if ("2".equals(taskDefectUserListBean.getSign())) {
                                dangerPatrolTeam.setText(name);
                                teamName=name;
                                teamId = taskDefectUserListBean.getUser_id();
                            } else if ("3".equals(taskDefectUserListBean.getSign())) {
                                if ("".equals(names)) {
                                    names = name;
                                } else {
                                    names = names + "," + name;
                                }
                            }

                            userInfo.setUserId(taskDefectUserListBean.getUser_id());
                            userInfo.setUserName(name);
                            userListBean.setName(name);
                            userListBean.setId(taskDefectUserListBean.getUser_id());
                            personalList.add(userListBean);
                            selectUserInfos.add(userInfo);
                        }
                        dangerPatrolPersonal.setText(names);
                        selectWorkerBean.setUserInfos(selectUserInfos);
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    //选择负责人弹框
    public void showPersonalGroup() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择负责人");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setSingleChoiceItems(personals, dutyPositon, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int options1) {

                dutyPositon = options1;
                teamName = personals[options1];
                dangerPatrolTeam.setText(teamName);
                personalSelect.clear();
                String names = dangerPatrolPersonal.getText().toString();
                String[] split = names.split(",");
                for (int i = 0; i < split.length; i++) {
                    String name = split[i];
                    personalSelect.add(name);
                }
                int i = personalSelect.indexOf(teamName);
                if (i != -1) {
                    personalSelect.remove(i);
                    String personal = "";
                    for (int j = 0; j < personalSelect.size(); j++) {
                        String name = personalSelect.get(j);
                        if (j == 0) {
                            personal = name;
                        } else {
                            personal = personal + "," + name;
                        }
                    }
                    dangerPatrolPersonal.setText(personal);
                }
                personalGroupDialog.dismiss();
            }
        });

        personalGroupDialog = alertBuilder.create();
        personalGroupDialog.show();
    }

    //展示组员多选列表
    public void showPersonal() {
        personalSelect.clear();
        boolean[] booleans = new boolean[personals.length];
        String names = dangerPatrolPersonal.getText().toString();
        if (!"".equals(names)) {
            String[] split = names.split(",");
            for (int i = 0; i < split.length; i++) {
                String name = split[i];
                personalSelect.add(name);
            }
        }
        for (int i = 0; i < personals.length; i++) {
            String personal = personals[i];
            int i1 = personalSelect.indexOf(personal);
            if (i1 == -1) {
                booleans[i] = false;
            } else {
                booleans[i] = true;
            }
        }
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择组员");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(personals, booleans, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {

                if (isChecked) {
                    personalSelect.add(personals[i]);
                } else {
                    for (int j = 0; j < personalSelect.size(); j++) {
                        String s = personalSelect.get(j);
                        if (s.equals(personals[i])) {
                            personalSelect.remove(j);
                            break;
                        }
                    }
                }
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                String personal = "";
                for (int j = 0; j < personalSelect.size(); j++) {
                    String name = personalSelect.get(j);
                    if (j == 0) {
                        personal = name;
                    } else {
                        personal = personal + "," + name;
                    }
                }
                dangerPatrolPersonal.setText(personal);
                if (personalSelect.indexOf(teamName) != -1) {
                    teamName = "";
                    dangerPatrolTeam.setText(teamName);
                }
                personalDialog.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                personalSelect.clear();
                personalDialog.dismiss();
            }
        });


        personalDialog = alertBuilder.create();
        personalDialog.show();
    }

    //获取缺陷详情
    public void getDefectDeatail(String id) {
        ProgressDialog.show(this, true, "正在加载中。。。");
        BaseRequest.getInstance().getService().getDefectDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DefectFragmentDetailBean>() {
                    @Override
                    protected void onSuccees(BaseResult<DefectFragmentDetailBean> t) throws Exception {
                        DefectFragmentDetailBean bean = t.getResults();
                        line_name = bean.getLine_name();
                        tower_name = bean.getTower_name();
                        find_dep_id = SPUtil.getDepId(DefectPlanActivity.this);
                        find_dep_name =  SPUtil.getDepName(DefectPlanActivity.this);
                        tower_id = bean.getTower_id();
                        line_id = bean.getLine_id();
                        dangerPatrolType.setContent(bean.getCategory_name());
                        dangerPatrolDep.setContent(bean.getFind_dep_name());
                        dangerPatrolLine.setContent(bean.getLine_name());
                        dangerPatrolTower.setContent(bean.getTower_name());
                        dangerPatrolLevel.setContent(bean.getGrade_name());
                        dangerPatrolContent.setContent(bean.getContent());
                        dangerPatrolIdea.setText(bean.getDeal_notes());
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //提交消缺计划
    public void makeDefectPlan(String status) {
        ProgressDialog.show(this, true, "正在加载中。。。");
          MakeDefectPlanBean bean = new MakeDefectPlanBean();
        List<TaskDefectUser> userlist = new ArrayList<>();
        String deal = dangerPatrolIdea.getText().toString().trim();
        teamName=dangerPatrolTeam.getText().toString();
        if ("".equals(deal)) {
            Toast.makeText(this, "请填写处理措施", Toast.LENGTH_SHORT).show();
            return;
        }
        String names = dangerPatrolPersonal.getText().toString();
        if ("".equals(names)) {
            Toast.makeText(this, "请选择组员", Toast.LENGTH_SHORT).show();
            return;
        } else {
            names = teamName + "," + names;
            String[] split = names.split(",");
            for (int i = 0; i < split.length; i++) {
                String name = split[i];
                for (int j = 0; j < personalList.size(); j++) {
                    DepPersonalBean.UserListBean userListBean = personalList.get(j);
                    if (name.equals(userListBean.getName())) {
                        TaskDefectUser user = new TaskDefectUser();
                        if (i == 0) {
                            user.setSign("2");
                            bean.setDuty_user_id(userListBean.getId());
                            bean.setDuty_user_name(userListBean.getName());
                        } else {
                            user.setSign("3");
                        }
                        user.setTask_defect_id(id);
                        user.setUser_name(userListBean.getName());
                        user.setUser_id(userListBean.getId());
                        userlist.add(user);
                    }
                }
            }
        }

        bean.setDeal_time(startTime);
        bean.setClose_time(endTime);
        bean.setLine_name(line_name);
        bean.setTower_name(tower_name);
        bean.setDeal_dep_id(find_dep_id);
        bean.setTower_id(tower_id);
        bean.setLine_id(line_id);
        bean.setDeal_dep_name(find_dep_name);
        bean.setDeal_notes(deal);
        bean.setMake_status(status);
        bean.setTaskDefectUserList(userlist);
        bean.setFrom_user_id(SPUtil.getUserId(this));
        bean.setId(id);
        BaseRequest.getInstance().getService().makeDefectPOST(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {

                        ProgressDialog.cancle();
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }
    //获取控制卡类型
    private void controlCard() {
        BaseRequest.getInstance().getService()
                .controlCardType("kzk")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ControlCardBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<ControlCardBean>> t) throws Exception {
                        Log.w("linmeng", "t.toString():" + t.toString());
                        if (t.getCode() == 1) {
                            List<ControlCardBean> results = t.getResults();
                            for (int i = 0; i < results.size(); i++) {
                                nameType.add(results.get(i).getName());
                            }
                            nsControlCard.attachDataSource(nameType);
                            controlCardId = results.get(0);
                            nsControlCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    controlCardId = results.get(position);
                                    Log.w("linmeng", "controlCardId:" + controlCardId.getId());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            Toast.makeText(mContext, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
    //班组控制卡数据回显
    private void getCardControl(String id) {
        BaseRequest.getInstance().getService()
                .getCardControl(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AllControlCarBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<AllControlCarBean> t) throws Exception {
                        allControlCarBean = t.getResults();
                        if (mJobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {  //负责人进来, 填写过的将数据带过去
                            if (allControlCarBean == null) {   //负责人第一次进来
                                nsControlCard.setVisibility(View.VISIBLE);
                            } else {
                                if (allControlCarBean.getCardControl() == null && allControlCarBean.getCardQuality() == null && allControlCarBean.getCardTool()== null ) {
                                    nsControlCard.setVisibility(View.VISIBLE);
                                } else {
//                                    nsWorkTicket.setVisibility(View.GONE);
                                    nsControlCard.setVisibility(View.GONE);
                                }
                            }

                        } else {   //其他人进控制卡
                            nsControlCard.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }


}
