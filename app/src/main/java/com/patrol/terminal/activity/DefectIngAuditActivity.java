package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectPicAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.DefectFragmentDetailBean;
import com.patrol.terminal.bean.PatrolRecordPicBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//缺陷审核
public class DefectIngAuditActivity extends BaseActivity {

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
    @BindView(R.id.defect_line_name)
    TextView defectLineName;
    @BindView(R.id.defect_start_name)
    TextView defectStartName;
    @BindView(R.id.defect_content)
    TextView defectContent;
    @BindView(R.id.defect_category_name)
    TextView defectCategoryName;
    @BindView(R.id.defect_grade_name)
    TextView defectGradeName;
    @BindView(R.id.defect_find_time)
    TextView defectFindTime;
    @BindView(R.id.defect_deal_notes)
    TextView defectDealNotes;
    @BindView(R.id.defect_deal_time)
    TextView defectDealTime;
    @BindView(R.id.defect_audit_status)
    TextView defectAuditStatus;
    @BindView(R.id.defect_status)
    TextView defectStatus;
    @BindView(R.id.defect_find_user_name)
    TextView defectFindUserName;
    @BindView(R.id.defect_find_dep_name)
    TextView defectFindDepName;
    @BindView(R.id.defect_remark)
    TextView defectRemark;
    @BindView(R.id.defect_deadline)
    TextView defectDeadline;
    @BindView(R.id.deffect_img)
    TextView deffectImg;
    @BindView(R.id.defect_gridView)
    GridView defectGridView;
    @BindView(R.id.reject)
    TextView reject;
    @BindView(R.id.stock_in)
    TextView stockIn;
    @BindView(R.id.review)
    TextView review;
    @BindView(R.id.turn_to_repair)
    TextView turnToRepair;


    private DefectPicAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private String year;
    private String month;
    private String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getIntent().getStringExtra("id");
        setContentView(R.layout.activity_defect_audit);
        ButterKnife.bind(this);
        initview(id);
    }

    private void initview(String id) {
        String mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains("_zz")) {
            review.setVisibility(View.VISIBLE);
            turnToRepair.setVisibility(View.VISIBLE);
        } else {
            review.setVisibility(View.GONE);
            turnToRepair.setVisibility(View.GONE);
        }

        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        defectDeadline.setText(time);

        titleName.setText("缺陷审核");
        BaseRequest.getInstance().getService().getDefectDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DefectFragmentDetailBean>() {
                    @Override
                    protected void onSuccees(BaseResult<DefectFragmentDetailBean> t) throws Exception {
                        DefectFragmentDetailBean bean = t.getResults();
                        defectContent.setText(bean.getContent());
                        defectStartName.setText(bean.getStart_name());
                        defectFindTime.setText(bean.getFind_time());

                        //缺陷状态（0：编制，1：月计划分配，2：周计划分配，3：日计划分配，4：进行中，5：已完成，6：未完成）
                        switch (bean.getStatus()) {
                            case "0":
                                defectStatus.setText("编制");
                                break;
                            case "1":
                                defectStatus.setText("月计划分配");
                                break;
                            case "2":
                                defectStatus.setText("周计划分配");
                                break;
                            case "3":
                                defectStatus.setText("日计划分配");
                                break;
                            case "4":
                                defectStatus.setText("进行中");
                                break;
                            case "5":
                                defectStatus.setText("已完成");
                                break;
                            case "6":
                                defectStatus.setText("未完成");
                                break;
                        }
                        if (bean.getDeal_time() != null) {
                            defectDealTime.setText(bean.getDeal_time());
                        }
                        defectLineName.setText(bean.getLine_name());
                        defectFindUserName.setText(bean.getFind_user_name());
                        if (bean.getRemark() != null) {
                            defectRemark.setText(bean.getRemark());
                        }
                        if (bean.getFind_dep_name() != null) {
                            defectFindDepName.setText(bean.getFind_dep_name());
                        }
                        defectCategoryName.setText(bean.getCategory_name());
                        defectGradeName.setText(bean.getGrade_name());

                        if ("0".equals(bean.getAudit_status())) {
                            defectAuditStatus.setText("审核中");
                        } else if ("1".equals(bean.getAudit_status())) {
                            defectAuditStatus.setText("已审核");
                        }

                        if ("一般".equals(bean.getGrade_name())) {
                            defectGradeName.setTextColor(getResources().getColor(R.color.blue));

                            Calendar c = Calendar.getInstance();//获得一个日历的实例
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                            Date date = new Date(System.currentTimeMillis());
                            c.setTime(date);//设置日历时间
                            c.add(Calendar.MONTH,1);//在日历的月份上增加6个月
                            defectDeadline.setText(sdf.format(c.getTime()));
                        } else if ("严重".equals(bean.getGrade_name())) {
                            defectGradeName.setTextColor(getResources().getColor(R.color.line_point_1));

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                            Date date = new Date(System.currentTimeMillis());
                            c.setTime(date);
                            c.add(Calendar.DATE,7);
                            defectDeadline.setText(sdf.format(c.getTime()));
                        } else if ("危急".equals(bean.getGrade_name())) {
                            defectGradeName.setTextColor(getResources().getColor(R.color.line_point_0));

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                            Date date = new Date(System.currentTimeMillis());
                            c.setTime(date);
                            c.add(Calendar.DATE,1);
                            defectDeadline.setText(sdf.format(c.getTime()));
                        }
                        defectGradeName.setText(bean.getGrade_name());
                        if (bean.getDeal_notes() != null) {
                            defectDealNotes.setText(bean.getDeal_notes());
                        }


//        defectDepName.setText("工作班组：" + bean.getDeal_dep_name());
//                        getPartrolRecord(bean.getId());
                        if (bean.getFileList() != null && bean.getFileList().size() > 0) {
                            deffectImg.setVisibility(View.VISIBLE);
                            mPicList.clear();
                            for (int i = 0; i < bean.getFileList().size(); i++) {
                                mPicList.add(BaseUrl.BASE_URL + bean.getFileList().get(i).getFile_path() + bean.getFileList().get(i).getFilename());
                            }
                            mGridViewAddImgAdapter = new DefectPicAdapter(DefectIngAuditActivity.this, mPicList);
                            defectGridView.setAdapter(mGridViewAddImgAdapter);
                            defectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                                    viewPluImg(position);

                                }
                            });
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @OnClick({R.id.title_back, R.id.defect_deadline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.defect_deadline:
                showDay();
                break;
        }
    }

    public void getPartrolRecord(String id) {
        ProgressDialog.show(this, false, "正在加载。。。。");
        BaseRequest.getInstance().getService()
                .getDefectPic(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolRecordPicBean>>(this) {
                    
                    @Override
                    protected void onSuccees(BaseResult<List<PatrolRecordPicBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<PatrolRecordPicBean> results = t.getResults();
                            if (results.size() > 0) {
                                deffectImg.setVisibility(View.VISIBLE);
                                for (int i = 0; i < results.size(); i++) {
                                    PatrolRecordPicBean overhaulFileBean = results.get(i);
                                    String file_path = overhaulFileBean.getFile_path();
                                    if (overhaulFileBean.getFilename() != null) {
                                        String compressPath = BaseUrl.BASE_URL + file_path.substring(1, file_path.length()) + overhaulFileBean.getFilename();

                                        mPicList.add(compressPath);
                                    }
                                }
                            } else {
                                deffectImg.setVisibility(View.GONE);
                            }
                            mGridViewAddImgAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                });
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constant.IMG_LIST, mPicList);
        intent.putExtra(Constant.POSITION, position);
        startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
    }

    public void showDay() {
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        int curMonth = Integer.parseInt(months[0]);
        int curYear = Integer.parseInt(years[0]);
        int curDay = Integer.parseInt(days[0]);
        if(curMonth == 1){
            curMonth = 12;
            curYear = curYear - 1;
        } else {
            curMonth = curMonth - 1;
        }

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(curYear, curMonth, curDay);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = DateUatil.getDay(date);
                defectDeadline.setText(time);
                String[] times = time.split("年");
                String[] months = times[1].split("月");
                year = times[0];
                month = months[0];
                day = months[1].split("日")[0];
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
}
