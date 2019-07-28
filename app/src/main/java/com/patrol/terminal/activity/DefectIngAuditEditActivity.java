package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectAuditPicEditAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.CLCSTypeBean;
import com.patrol.terminal.bean.DefectFragmentDetailBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialogNew;
import com.patrol.terminal.widget.ProgressDialog;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

//缺陷审核编辑
public class DefectIngAuditEditActivity extends BaseActivity {

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
    @BindView(R.id.tower_name)
    TextView towerName;
    @BindView(R.id.defect_content)
    TextView defectContent;
    @BindView(R.id.patrol_name)
    TextView patrolName;
    @BindView(R.id.defect_category_name)
    TextView defectCategoryName;
    @BindView(R.id.defect_grade_name)
    TextView defectGradeName;
    @BindView(R.id.defect_find_time)
    TextView defectFindTime;
    @BindView(R.id.defect_deal_notes)
    TextView defectDealNotes;
    @BindView(R.id.deal_user_name)
    TextView dealUserName;
    @BindView(R.id.deal_dep_name)
    TextView dealDepName;
    @BindView(R.id.deal_time)
    TextView dealTime;
    @BindView(R.id.defect_status)
    TextView defectStatus;
    @BindView(R.id.defect_find_user_name)
    TextView defectFindUserName;
    @BindView(R.id.defect_find_dep_name)
    TextView defectFindDepName;
    @BindView(R.id.defect_deadline)
    TextView defectDeadline;
    @BindView(R.id.deffect_img)
    TextView deffectImg;
    @BindView(R.id.defect_gridView)
    GridView defectGridView;
    @BindView(R.id.stock_in)
    TextView stockIn;
    @BindView(R.id.turn_to_repair)
    TextView turnToRepair;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.rg_content_type)
    RadioGroup rgContentType;
    @BindView(R.id.check_one_button)
    RadioButton checkOneRb;
    @BindView(R.id.check_two_button)
    RadioButton checkTwoRb;
    @BindView(R.id.check_three_button)
    RadioButton checkThreeRb;
    @BindView(R.id.defect_spinner)
    NiceSpinner defectSpinner;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    private DefectAuditPicEditAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private DefectFragmentDetailBean bean;
    private String year;
    private String month;
    private String day;
    private List<CLCSTypeBean> clcsTypeList;//处理措施方法
    private List<String> clcsListStr;
    private String id;
    private int picIndex = 0;
    private Map<String, RequestBody> params = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("data_id");
        setContentView(R.layout.activity_defect_audit_edit);
        ButterKnife.bind(this);
        initview(id);
    }

    private void initview(String id) {
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        defectDeadline.setText(time);
        initdate(time);

        getClcsData();
        clcsListStr = new ArrayList<>();
        for (int i = 0; i < clcsTypeList.size(); i++) {
            clcsListStr.add(clcsTypeList.get(i).getName());
        }

        defectSpinner.setBackgroundColor(getResources().getColor(R.color.transparent));
        defectSpinner.attachDataSource(clcsListStr);

        checkOneRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOneRb.setChecked(true);
                checkTwoRb.setChecked(false);
                checkThreeRb.setChecked(false);
                bean.setGrade_id("10C639F13341484997EE8D955322BE02");
                bean.setGrade_name("危急");
            }
        });

        checkTwoRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOneRb.setChecked(false);
                checkTwoRb.setChecked(true);
                checkThreeRb.setChecked(false);
                bean.setGrade_id("2CEB42DA67764AC0BF911B02FB579775");
                bean.setGrade_name("严重");
            }
        });

        checkThreeRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOneRb.setChecked(false);
                checkTwoRb.setChecked(false);
                checkThreeRb.setChecked(true);
                bean.setGrade_id("37E5647975394B1E952DC5D2796C7D73");
                bean.setGrade_name("一般");
            }
        });

        defectSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                CLCSTypeBean typeBean = clcsTypeList.get(position);
                bean.setDeal_notes(typeBean.getName());
//                item.setClcsName(typeBean.getName());
//                item.setClcsId(typeBean.getId());
//                item.update();
            }
        });

        titleName.setText("缺陷复核");
        BaseRequest.getInstance().getService().getDefectDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DefectFragmentDetailBean>() {
                    @Override
                    protected void onSuccees(BaseResult<DefectFragmentDetailBean> t) throws Exception {
                        bean = t.getResults();

                        //缺陷入库状态（0：编制，1：待班长审核，2：待专责审核，3：审核通过，4：审核不通过, 5: 待复核）
                        switch (bean.getIn_status()) {
                            case "0":
                                defectStatus.setText("编制");
                                defectStatus.setTextColor(getResources().getColor(R.color.blue));
                                break;
                            case "1":
                                defectStatus.setText("待班长审核");
                                defectStatus.setTextColor(getResources().getColor(R.color.line_point_1));
                                break;
                            case "2":
                                defectStatus.setText("待专责审核");
                                defectStatus.setTextColor(getResources().getColor(R.color.line_point_0));
                                break;
                            case "3":
                                defectStatus.setText("审核通过");
                                defectStatus.setTextColor(getResources().getColor(R.color.green));
                                defectGradeName.setVisibility(View.VISIBLE);
                                rgContentType.setVisibility(View.GONE);
                                layoutBottom.setVisibility(View.GONE);
                                defectDealNotes.setVisibility(View.VISIBLE);
                                defectSpinner.setVisibility(View.GONE);
                                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
                                layoutParams.bottomMargin = 0;
                                scrollView.setLayoutParams(layoutParams);
                                titleName.setText("缺陷记录");
                                break;
                            case "4":
                                defectStatus.setText("审核不通过");
                                defectStatus.setTextColor(getResources().getColor(R.color.write_red));
                                break;
                            case "5":
                                defectStatus.setText("待复核");
                                defectStatus.setTextColor(getResources().getColor(R.color.orange));
                                layoutBottom.setVisibility(View.GONE);
                                titleName.setText("缺陷记录");
                                break;
                        }

                        if(bean.getContent() != null){
                            defectContent.setText(bean.getContent());
                        }
                        if(bean.getTower_name() != null){
                            towerName.setText(bean.getTower_name());
                        }
                        if(bean.getPatrol_name() != null){
                            patrolName.setText(bean.getPatrol_name());
                        }
                        if(bean.getFind_time() != null){
                            defectFindTime.setText(bean.getFind_time());
                        }
                        if (bean.getDeal_user_name() != null) {
                            dealUserName.setText(bean.getDeal_user_name());
                        }
                        if (bean.getDeal_dep_name() != null) {
                            dealDepName.setText(bean.getDeal_dep_name());
                        }
                        if (bean.getDeal_time() != null) {
                            dealTime.setText(bean.getDeal_time());
                        }
                        if(bean.getLine_name() != null){
                            defectLineName.setText(bean.getLine_name());
                        }
                        if(bean.getFind_user_name() != null){
                            defectFindUserName.setText(bean.getFind_user_name());
                        }
                        if (bean.getFind_dep_name() != null) {
                            defectFindDepName.setText(bean.getFind_dep_name());
                        }
                        if(bean.getCategory_name() != null){
                            defectCategoryName.setText(bean.getCategory_name());
                        }

                        defectGradeName.setText(bean.getGrade_name());

                        if ("一般".equals(bean.getGrade_name())) {
                            checkOneRb.setChecked(false);
                            checkTwoRb.setChecked(false);
                            checkThreeRb.setChecked(true);

                            Calendar c = Calendar.getInstance();//获得一个日历的实例
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                            Date date = new Date(System.currentTimeMillis());
                            c.setTime(date);//设置日历时间
                            c.add(Calendar.MONTH,1);//在日历的月份上增加6个月
                            defectDeadline.setText(sdf.format(c.getTime()));
                            initdate(sdf.format(c.getTime()));
                        } else if ("严重".equals(bean.getGrade_name())) {
                            checkOneRb.setChecked(false);
                            checkTwoRb.setChecked(true);
                            checkThreeRb.setChecked(false);

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                            Date date = new Date(System.currentTimeMillis());
                            c.setTime(date);
                            c.add(Calendar.DATE,7);
                            defectDeadline.setText(sdf.format(c.getTime()));
                            initdate(sdf.format(c.getTime()));
                        } else if ("危急".equals(bean.getGrade_name())) {
                            checkOneRb.setChecked(true);
                            checkTwoRb.setChecked(false);
                            checkThreeRb.setChecked(false);

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                            Date date = new Date(System.currentTimeMillis());
                            c.setTime(date);
                            c.add(Calendar.DATE,1);
                            defectDeadline.setText(sdf.format(c.getTime()));
                            initdate(sdf.format(c.getTime()));
                        }

                        if (bean.getDeal_notes() != null) {
                            defectDealNotes.setText(bean.getDeal_notes());
                            if(clcsListStr != null){
                                for(int i=0;i<clcsListStr.size();i++){
                                    if(clcsListStr.get(i).contains(bean.getDeal_notes())){
                                        defectSpinner.setSelectedIndex(i);
                                        break;
                                    }
                                }
                            }
                        }

                        if (bean.getFileList() != null && bean.getFileList().size() > 0) {
                            deffectImg.setVisibility(View.VISIBLE);
                            mPicList.clear();

                            for (int i = 0; i < bean.getFileList().size(); i++) {
                                String url = BaseUrl.BASE_URL + bean.getFileList().get(i).getFile_path() + bean.getFileList().get(i).getFilename();
                                mPicList.add(url);
                                int j = i;
                                String path = Environment.getExternalStorageDirectory().getPath()
                                        + "/MyPhoto/" + id + "_" + i + ".jpg";
                                Glide.with(DefectIngAuditEditActivity.this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        try {
                                            FileUtil.saveFile(resource, path);
                                            mPicList.set(j, path);
                                            mGridViewAddImgAdapter.notifyDataSetChanged();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            if(!bean.getIn_status().equals("3") && bean.getFileList().size() < 5){
                                mPicList.add("");
                            }

                            mGridViewAddImgAdapter = new DefectAuditPicEditAdapter(DefectIngAuditEditActivity.this, mPicList, bean.getIn_status());
                            defectGridView.setAdapter(mGridViewAddImgAdapter);
                            defectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                                    picIndex = position;
                                    if (position == parent.getChildCount() - 1) {
                                        startCamera();
                                    } else {
                                        viewPluImg(position);
                                    }
                                }
                            });
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @OnClick({R.id.title_back, R.id.defect_deadline, R.id.stock_in, R.id.turn_to_repair})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.defect_deadline:
                if(!bean.getIn_status().equals("3")){
                    showDay();
                }
                break;
            case R.id.stock_in:
                submit("3");
                break;
            case R.id.turn_to_repair:
                break;
        }
    }

    public void updateAndImgPOST(String in_status) {
        ProgressDialog.show(this, false, "正在加载。。。。");
        params.clear();
        params.put("id", toRequestBody(bean.getId()));
        params.put("task_check_id", toRequestBody(bean.getTask_check_id()));
        params.put("in_status", toRequestBody(in_status));
        params.put("from_user_id", toRequestBody(SPUtil.getUserId(this)));
        params.put("from_user_name", toRequestBody(SPUtil.getUserName(this)));
        params.put("line_name", toRequestBody(bean.getLine_name()));
        params.put("tower_name", toRequestBody(bean.getTower_name()));
        params.put("grade_name", toRequestBody(bean.getGrade_name()));
        params.put("deal_notes", toRequestBody(bean.getDeal_notes()));
        params.put("close_time", toRequestBody(year + "-" + month + "-" + day));
        for(int i=0;i<mPicList.size();i++){
            if(!mPicList.get(i).equals("")){
                File file = new File(mPicList.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                params.put("defect_file\"; filename=\"" + i + ".jpg", requestFile);
            }
        }

        BaseRequest.getInstance().getService()
                .updateAndImgPOST(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {

                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if(t.getCode() == 1){
                            Toast.makeText(DefectIngAuditEditActivity.this,"处理完成",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(DefectIngAuditEditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constant.IMG_LIST, mPicList);
        intent.putExtra("isDelPic", "0");
        intent.putExtra(Constant.POSITION, position);
        startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
    }

    //打开相机
    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constant.DEFECT_AUDIT_EDIT_REQUEST_CODE);
    }

    //初始化日期
    public void initdate(String time) {
        String[] times = time.split("年");
        String[] months = times[1].split("月");
        year = times[0];
        month = months[0];
        day = months[1].split("日")[0];
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
                initdate(time);
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

    //提交缺陷审核
    public void submit(String in_status) {
        CancelOrOkDialogNew dialog = new CancelOrOkDialogNew(this, "入库", "取消", "确定") {
            @Override
            public void ok() {
                super.ok();
                updateAndImgPOST(in_status);
            }

            @Override
            public void cancle() {
                super.cancle();
            }
        };
        dialog.show();
    }

    public void getClcsData() {
        clcsTypeList = SQLite.select().from(CLCSTypeBean.class).queryList();
        if (clcsTypeList == null || clcsTypeList.size() == 0) {
            String assetsJson = getFromAssets("clcs.json");
            List<CLCSTypeBean> clcsList = new Gson().fromJson(assetsJson, new TypeToken<ArrayList<CLCSTypeBean>>() {
            }.getType());
            clcsTypeList.addAll(clcsList);
        }
    }

    //从asset获取json
    public String getFromAssets(String fileName) {
        String line = "";
        String result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while ((line = bufReader.readLine()) != null)
                result += line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                switch (requestCode) {
                    case Constant.DEFECT_AUDIT_EDIT_REQUEST_CODE:
                        Bundle bundle = data.getExtras();
                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        String path = Environment.getExternalStorageDirectory().getPath()
                                + "/MyPhoto/" + id + "_" + picIndex + ".jpg";
                        FileUtil.saveFile(bitmap, path);
                        mPicList.set(picIndex, path);
                        if(mPicList.size() < 5){
                            mPicList.add("");
                        }
                        mGridViewAddImgAdapter.notifyDataSetChanged();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public RequestBody toRequestBody(String value) {
        if (value != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
            return requestBody;
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            return requestBody;
        }
    }
}
