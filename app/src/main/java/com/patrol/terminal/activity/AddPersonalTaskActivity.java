package com.patrol.terminal.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.PersonalTaskSelectAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AddGroupTaskReqBean;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.DepUserBean;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddPersonalTaskActivity extends BaseActivity {


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
    @BindView(R.id.group_task_type)
    TextView groupTaskType;
    @BindView(R.id.group_task_group)
    TextView groupTaskGroup;
    @BindView(R.id.group_task_type_lv)
    NoScrollListView groupTaskTypeLv;
    @BindView(R.id.add_more_iv)
    ImageView addMoreIv;
    @BindView(R.id.trouble_more)
    LinearLayout troubleMore;
    @BindView(R.id.group_task_no_data)
    TextView groupTaskNoData;
    @BindView(R.id.manth_plan_detail_lv)
    NoScrollListView manthPlanDetailLv;
    @BindView(R.id.mon_plan_type_ll)
    LinearLayout monPlanTypeLl;
    @BindView(R.id.month_yes)
    TextView monthYes;



    private AddPersonalTaskAdapter adapter;
    private PersonalTaskSelectAdapter selectAdapter;
    private List<GroupTaskBean> results;
    private Disposable subscribe;

    private String year;
    private String month;
    private int type = 0;
    private List<GroupTaskBean> taskList = new ArrayList<>();

    private List<String> typeName = new ArrayList<>();
    private List<LineTypeBean> typeList = new ArrayList<>();
    private String type_id;
    private String type_val;
    private String time;
    private String day;
    private String w_id;
    private String d_id;
    private List<DepUserBean> personalList;
    private List<DepUserBean> addPeoList = new ArrayList<>();
    private  List<GroupTaskBean> selectBean=new ArrayList<>();
    private AlertDialog personalDialog;
    private int dutyPositon;
    private String[] personals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_task);
        ButterKnife.bind(this);
        initview();
        getLineType();
        getAddGroupList();
        getPersonal();
    }

    private void initview() {
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        titleName.setText("制定个人任务");
        adapter = new AddPersonalTaskAdapter(this, taskList);
        selectAdapter = new PersonalTaskSelectAdapter(this, selectBean);
        groupTaskTypeLv.setAdapter(adapter);
        manthPlanDetailLv.setAdapter(selectAdapter);
        groupTaskTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectBean.add(results.get(position));

                selectAdapter.setData(selectBean);
            }
        });
    }

    public void setVisibility(List<GroupTaskBean> list) {
        if (list.size() == 0) {
            groupTaskNoData.setVisibility(View.VISIBLE);
        } else {
            groupTaskNoData.setVisibility(View.GONE);
        }
        if (list.size() > 4) {
            troubleMore.setVisibility(View.VISIBLE);
        } else {
            troubleMore.setVisibility(View.GONE);
        }
        adapter.setData(list);
    }

    //获取小组任务
    public void getAddGroupList() {
        BaseRequest.getInstance().getService()
                .getAddGroupList(year, month, day, SPUtil.getUserId(this),type_id,"0","0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GroupTaskBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<GroupTaskBean>> t) throws Exception {
                        results = t.getResults();
                        setVisibility(results);


                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.e("fff", e.toString());
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
                groupTaskType.setText(typeList.get(options1).getName());
                type_id = typeList.get(options1).getId();
                type_val = typeList.get(options1).getVal();
                getAddGroupList();
            }
        }).build();
        pvOptions.setPicker(typeName);
        pvOptions.show();
    }


    //获取每个班组负责的工作类型
    public void getLineType() {
        typeName.clear();
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
                            typeName.add(lineTypeBean.getName());
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    //获取每个班组组员列表
    public void getPersonal() {

        BaseRequest.getInstance().getService()
                .getGroupPersonal(year, month, day,SPUtil.getDepId(this), SPUtil.getUserId(this),"2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DepUserBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DepUserBean>> t) throws Exception {
                        personalList = t.getResults();
                        personals = new String[personalList.size()];
                        for (int i = 0; i < personalList.size(); i++) {
                            personals[i]=personalList.get(i).getUser_name();
                        }


                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
//
//    //获取缺陷库
//    public void getDefect() {
//        list1.clear();
//        BaseRequest.getInstance().getService()
//                .getDefect("", "0", "1")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<DefectBean>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<DefectBean>> t) throws Exception {
//                        List<DefectBean> results = t.getResults();
//                        list1.addAll(results);
//                        setVisibility(list1);
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//                    }
//                });
//    }


//    //获取已经添加的缺陷列表
//    public void getHaveDefect() {
//        BaseRequest.getInstance().getService()
//                .getHaveDefect(month_id, week_id, day_id, group_id, task_id, "1", "1")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<DefectBean>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<DefectBean>> t) throws Exception {
//                        List<DefectBean> results = t.getResults();
//                        selectType.addAll(results);
//                        selectAdapter.setData(selectType);
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//                    }
//                });
//    }

    //保存
    public void savaGroupTask() {

        if (selectBean.size()==0){
            Toast.makeText(this,"请添加任务",Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < selectBean.size(); i++) {
            GroupTaskBean groupTaskBean = selectBean.get(i);
            if (addPeoList.size()==0){
                groupTaskBean.setIs_rob("1");
            }else {
            groupTaskBean.setUser_id(addPeoList.get(0).getUser_id());
            groupTaskBean.setUser_name(addPeoList.get(0).getUser_name());
            }
            groupTaskBean.setFrom_user_id(SPUtil.getUserId(this));
            groupTaskBean.setFrom_user_name(SPUtil.getUserName(this));
        }
        BaseRequest.getInstance().getService()
                .addPersonTask(selectBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            setResult(RESULT_OK);
                            RxRefreshEvent.publish("refreshGroup");
                            Toast.makeText(AddPersonalTaskActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddPersonalTaskActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

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

    @OnClick({R.id.title_back, R.id.group_task_type, R.id.group_task_group,  R.id.add_more_iv, R.id.month_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.group_task_type:
                if (typeName.size()==0){
                    Toast.makeText(this,"暂未获取到类别信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                showType();
                break;
            case R.id.group_task_group:
                if (personals.length==0){
                    Toast.makeText(this,"暂未获取到组员列表信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                showPersonalGroup();
                break;
            case R.id.add_more_iv:
                if (type == 0) {
                    type = 1;
                    addMoreIv.setImageResource(R.mipmap.icon_newol_up);
                } else {
                    type = 0;
                    addMoreIv.setImageResource(R.mipmap.icon_newol_down);
                }
                adapter.setType(type);
                break;
            case R.id.month_yes:
                savaGroupTask();
                break;
        }
    }

    public void inteDate() {
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
    }

     class AddPersonalTaskAdapter extends BaseAdapter {
        private Context context;
        private List<GroupTaskBean> lineTypeBeans;
        private int type=0;
        public AddPersonalTaskAdapter(Context context, List<GroupTaskBean> traceList) {
            this.context = context;
            this.lineTypeBeans = traceList;
        }

        @Override
        public int getCount() {

            if (type==0){
                if (lineTypeBeans.size()<4){
                    return lineTypeBeans.size();
                }
                return 4;
            }else {
                return lineTypeBeans.size();
            }

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
                convertView = LayoutInflater.from(context).inflate(R.layout.item_add_group_task, parent, false);
                holder.itemTroubleName = (TextView) convertView.findViewById(R.id.add_group_task_name);
                holder.itemTroubleTower = (TextView) convertView.findViewById(R.id.add_group_task_tower);
                holder.taskType = (TextView) convertView.findViewById(R.id.add_group_task_type);
                holder.itemTroubleCheck = (CheckBox) convertView.findViewById(R.id.add_group_task_check);
                holder.itemTaskCheck = (RadioButton) convertView.findViewById(R.id.add_group_task_rb);
                holder.item = (RelativeLayout) convertView.findViewById(R.id.personal_task_item);

                convertView.setTag(holder);
            }
            GroupTaskBean listBean = lineTypeBeans.get(position);

            holder.itemTroubleName.setText(listBean.getLine_name());
            holder.itemTroubleTower.setText(listBean.getName());
            AdapterUtils.setText( holder.taskType,StringUtil.getTypeSign(listBean.getType_sign()));
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectBean.size()==0){
                        if (listBean.getId()!=null){
                        listBean.setGroup_list_id(listBean.getId());
                        listBean.setId(null);}
                        selectBean.add(listBean);
                        holder.itemTroubleCheck.setChecked(true);
                    }else {
                        int isExit=0;
                        for (int i = 0; i < selectBean.size(); i++) {
                            GroupTaskBean dayOfWeekBean = selectBean.get(i);
                            if (dayOfWeekBean.getGroup_list_id().equals(listBean.getGroup_list_id())){
                                isExit=1;
                                selectBean.remove(i);
                                holder.itemTroubleCheck.setChecked(false);
                                break;
                            }
                        }
                        if (isExit==0){
                            if (listBean.getId()!=null){
                                listBean.setGroup_list_id(listBean.getId());
                                listBean.setId(null);}
                            selectBean.add(listBean);
                            holder.itemTroubleCheck.setChecked(true);
                        }
                    }
                    selectAdapter.setData(selectBean);

                }
            });
            return convertView;
        }

        public void setData(List<GroupTaskBean> typeBeanList) {
            lineTypeBeans = typeBeanList;
            notifyDataSetChanged();

        }


         class ViewHolder {
            private   TextView itemTroubleName,itemTroubleTower;
            private   TextView taskType;
             private   RelativeLayout item;
            private CheckBox itemTroubleCheck;
             private RadioButton itemTaskCheck;
        }

        public  void setType(int type){
            this.type=type;
            notifyDataSetChanged();

        }
    }

    public void showPersonalGroup(){

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
                String s = groupTaskGroup.getText().toString();
                groupTaskGroup.setText(personals[options1]);
                String name = personals[options1];
                if (s.isEmpty()) {

                } else {
                    for (int i = 0; i < addPeoList.size(); i++) {
                        DepUserBean depUserBean = addPeoList.get(i);
                        if ("2".equals(depUserBean.getSign())) {
                            addPeoList.remove(i);
                        }
                    }
                }
                for (int i = 0; i < personalList.size(); i++) {
                    DepUserBean depUserBean = personalList.get(i);
                    if (name.equals(depUserBean.getUser_name())) {
                        addPeoList.add(depUserBean);
                    }
                }
                monthYes.setText("指派");
                monthYes.setBackgroundColor(getResources().getColor(R.color.base_status_bar));
                personalDialog.dismiss();
            }
        });

        personalDialog = alertBuilder.create();
        personalDialog.show();
    }
}
