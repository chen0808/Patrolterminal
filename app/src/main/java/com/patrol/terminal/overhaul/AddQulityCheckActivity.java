package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.CheckResultBean;
import com.patrol.terminal.bean.InitiateProjectBean2;
import com.patrol.terminal.bean.UserBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.File;
import java.io.IOException;
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

/*新增安全检查*/
public class AddQulityCheckActivity extends BaseActivity {
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
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView checkResultRv;

    private View header;
    private View bottom;
    private AddCheckResultAdapter mAddCheckResultAdapter;

    private List<CheckResultBean> mCheckResult = new ArrayList<>();
    private boolean isNatureShow = false;
    private RadioGroup mNatureRg;
    private TextView mProjectContentTv;
    private TextView mCheckPersonContentTv;
    private TextView mTimeTv;
    private EditText mCheckItemEt;
    private long mTime;

    private String mSelectProjectId;   //选中的项目ID
    private String mSelectProjectName;
    private String mSelectNatureType = "0";
    private String mSelectPersonId;   //选择人人物ID
    private String mSelectPersonName;

//    private String[] mProjectNames;
//    private String[] mProjectIds;

    private Map<String, RequestBody> params = new HashMap<>();

    private HashMap<Long,List<String>> mPhotoMap =new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_safe_check_activity);
        ButterKnife.bind(this);
        //initData();
        initView();
    }

//    private void initData() {
//        BaseRequest.getInstance().getService()
//                .getProjectList("0", "0", "")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<CheckProjectServiceBean>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<CheckProjectServiceBean>> t) throws Exception {
//                        if (t.isSuccess()) {
//                            List<CheckProjectServiceBean> mProjectBeans = t.getResults();
//                            for (int i = 0; i < mProjectBeans.size(); i++) {
//                                String projectId = mProjectBeans.get(i).getTemp_project_id();
//                                String projectName = mProjectBeans.get(i).getTemp_project_name();
//
//                                mProjectIds[i] = projectId;
//                                mProjectNames[i] = projectName;
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        ProgressDialog.cancle();
//                    }
//                });
//    }

    private void initView() {
        titleName.setText("新增质量检查");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("保存");

        header = LayoutInflater.from(this).inflate(R.layout.add_safe_check_activity_top, null);
        bottom = LayoutInflater.from(this).inflate(R.layout.add_safe_check_activity_bottom, null);

        RelativeLayout projectRl = header.findViewById(R.id.project_rl);
        mProjectContentTv = header.findViewById(R.id.project_content_tv);

        projectRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddQulityCheckActivity.this, ProjectSearchActivityNew.class);
//                intent.putExtra("project_ids", mProjectIds);
//                intent.putExtra("project_names", mProjectNames);
                startActivityForResult(intent, 1001);
            }
        });

        // 设置监听器。
        checkResultRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        checkResultRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        checkResultRv.setLayoutManager(manager);

        //默认有一条数据供填写
        CheckResultBean checkResultBean = new CheckResultBean();
        checkResultBean.setCheckResultId(System.currentTimeMillis());
        checkResultBean.setCheckResult(1);
        checkResultBean.setCheckContent(null);
        List<CheckResultBean.PictureInfo> firstBitmapList = new ArrayList<>();
        checkResultBean.setCheckPics(firstBitmapList);
        mCheckResult.add(checkResultBean);

        mAddCheckResultAdapter = new AddCheckResultAdapter(this, R.layout.add_check_result_item, mCheckResult);
        checkResultRv.setAdapter(mAddCheckResultAdapter);

        ViewGroup parentViewGroup = (ViewGroup) header.getParent();
        if (parentViewGroup != null) {
            parentViewGroup.removeAllViews();
        }
        ViewGroup parentViewGroup1 = (ViewGroup) bottom.getParent();
        if (parentViewGroup1 != null) {
            parentViewGroup1.removeAllViews();
        }
        mAddCheckResultAdapter.addHeaderView(header);
        mAddCheckResultAdapter.addFooterView(bottom);

        mNatureRg = header.findViewById(R.id.nature_rg);
        TextView natureContentTv = header.findViewById(R.id.nature_content_tv);
        mNatureRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                mNatureRg.setVisibility(View.GONE);
                isNatureShow = false;
                String[] natureArray = getResources().getStringArray(R.array.safe_check_nature_array);
                switch (checkedId) {
                    case R.id.safe_construction_rb:
                        natureContentTv.setText(natureArray[0]);
                        mSelectNatureType = "1";
                        break;

                    case R.id.safe_education_rb:
                        natureContentTv.setText(natureArray[1]);
                        mSelectNatureType = "2";
                        break;

                    case R.id.financial_audit_rb:
                        natureContentTv.setText(natureArray[2]);
                        mSelectNatureType = "3";
                        break;

                    case R.id.other_rb:
                        natureContentTv.setText(natureArray[3]);
                        mSelectNatureType = "4";
                        break;
                }
            }
        });

        RelativeLayout natureRl = header.findViewById(R.id.nature_rl);
        natureRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNatureShow) {
                    mNatureRg.setVisibility(View.GONE);
                    isNatureShow = false;
                } else {
                    mNatureRg.setVisibility(View.VISIBLE);
                    isNatureShow = true;
                }
            }
        });

        LinearLayout addCheckResultRl = bottom.findViewById(R.id.add_check_result_rl);
        addCheckResultRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加一条数据供填写
                CheckResultBean checkResultBean = new CheckResultBean();
                checkResultBean.setCheckResultId(System.currentTimeMillis());
                checkResultBean.setCheckResult(1);
                checkResultBean.setCheckContent(null);
                List<CheckResultBean.PictureInfo> addBitmapList = new ArrayList<>();
                checkResultBean.setCheckPics(addBitmapList);
                mCheckResult.add(checkResultBean);
                mAddCheckResultAdapter.setNewData(mCheckResult);
            }
        });

        mTimeTv = header.findViewById(R.id.time_tv);
        mTime = System.currentTimeMillis();
        mTimeTv.setText(DateUatil.getMinTime(mTime));   //默认显示当前时间
        mTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();

            }
        });

        RelativeLayout checkPersonRl = header.findViewById(R.id.check_person_rl);
        mCheckPersonContentTv = header.findViewById(R.id.check_person_content_tv);
        checkPersonRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddQulityCheckActivity.this, CheckPersonSearchActivity.class);
                startActivityForResult(intent, 1002);
            }
        });

        mCheckItemEt = header.findViewById(R.id.check_item_et);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1001:
                if (data != null) {
                    InitiateProjectBean2 clickedCheckProjectBean = data.getParcelableExtra("search_project_item");
                    if (clickedCheckProjectBean != null) {
                        mSelectProjectId = clickedCheckProjectBean.getId();   //备用  TODO
                        Log.w("linmeng", "onActivityResult clickedCheckProjectBean.getName():" + clickedCheckProjectBean.getName());
                        mSelectProjectName = clickedCheckProjectBean.getName();
                        mProjectContentTv.setText(mSelectProjectName);
                    }
                }
                break;

            case 1002:
                if (data != null) {
                    UserBean clickedUserBean = data.getParcelableExtra("search_user_item");
                    if (clickedUserBean != null) {
                        mSelectPersonId = clickedUserBean.getId();   //备用  TODO
                        mSelectPersonName = clickedUserBean.getName();
                        mCheckPersonContentTv.setText(mSelectPersonName);
                    }
                }
                break;

            case 1003:   //  照相机
               List<String> list= mPhotoMap.get(Constant.checkResultId);
               if (list==null){
                   list=new ArrayList<>();
                   mPhotoMap.put(Constant.checkResultId,list);
               }
                Log.d("TAG", "success");
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");

                String path = Environment.getExternalStorageDirectory().getPath()
                        + "/MyPhoto/" + Constant.CHECK_RESULT + "_" + Constant.checkResultId + "_" + System.currentTimeMillis() + ".jpg";
                try {
                    FileUtil.saveFile(bitmap, path);
                    list.add(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < mCheckResult.size(); i++) {
                    long checkResultId = mCheckResult.get(i).getCheckResultId();
                    if (Constant.checkResultId  == checkResultId) {

                        CheckResultBean.PictureInfo pictureInfo = new CheckResultBean.PictureInfo();
                        pictureInfo.setBitmap(bitmap);
                        pictureInfo.setBitmapId(mCheckResult.get(i).getCheckPics().size());
                        mCheckResult.get(i).getCheckPics().add(pictureInfo);
                        Log.w("linmeng", "mCheckResult.get(i).getCheckPics().size():" + mCheckResult.get(i).getCheckPics().size());
                        mAddCheckResultAdapter.setNewData(mCheckResult);
                    }
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);
        }
    };

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

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                if (TextUtils.isEmpty(mSelectProjectId) || TextUtils.isEmpty(mSelectPersonId)
                        || TextUtils.isEmpty(mCheckItemEt.getText().toString().trim()) || mSelectNatureType.equals("0")) {
                    Toast.makeText(AddQulityCheckActivity.this, "请填写完必填选项!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //保存到本地数据库

                addProjectInfo();
                break;

        }
    }

    public void showTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mTime = date.getTime();
                mTimeTv.setText(DateUatil.getMinTime(mTime));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        Utils.hideClickStatus(this);
        pvTime.show();
    }

  /*  private void addProjectInfo() {
        CheckProjectServiceBean checkProjectServiceBean = new CheckProjectServiceBean();
        checkProjectServiceBean.setTemp_project_id(mSelectProjectId);
        checkProjectServiceBean.setTemp_project_name(mSelectProjectName);
        checkProjectServiceBean.setType_sign(mSelectNatureType);
        checkProjectServiceBean.setTime(DateUatil.getMinTime(mTime));
        checkProjectServiceBean.setCheck_user_name(mSelectPersonName);
        checkProjectServiceBean.setCheck_project(mCheckItemEt.getText().toString());

        List<CheckProjectServiceBean.TempCheckResultListBean> tempResultList = new ArrayList<>();
        for (int i = 0; i < mCheckResult.size(); i++) {
            CheckProjectServiceBean.TempCheckResultListBean resultBean = new CheckProjectServiceBean.TempCheckResultListBean();
            int result = mCheckResult.get(i).getCheckResult();
            resultBean.setResult(String.valueOf(result));
            resultBean.setContent(mCheckResult.get(i).getCheckContent());
            resultBean.setTemp_check_id(String .valueOf(mCheckResult.get(i).getCheckResultId()));

            List<CheckProjectServiceBean.TempCheckResultListBean.TempImgListBean> imgList = new ArrayList<>();
            CheckProjectServiceBean.TempCheckResultListBean.TempImgListBean tempImgListBean = new CheckProjectServiceBean.TempCheckResultListBean.TempImgListBean();
            tempImgListBean.set

            imgList.add()
            resultBean.setTempImgList(imgList);

            tempResultList.add(resultBean);

        }
        checkProjectServiceBean.setTempCheckResultList(tempResultList);
    }*/


    public RequestBody toRequestBody(String value) {
        if (value != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
            return requestBody;
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            return requestBody;
        }
    }


    public void addProjectInfo() {
        //ProgressDialog.show(this, false, "正在加载。。。。");
        params.clear();
        params.put("temp_project_id", toRequestBody(mSelectProjectId));
        params.put("temp_project_name", toRequestBody(mSelectProjectName));
        params.put("type_sign", toRequestBody(mSelectNatureType));
        params.put("time", toRequestBody(DateUatil.getMinTime(mTime)));
        params.put("check_user_name", toRequestBody(mSelectPersonName));
        params.put("check_project", toRequestBody(mCheckItemEt.getText().toString()));

        for (int k = 0 ; k < mCheckResult.size(); k++) {
            params.put("tempQualityResultList["+k+"].result", toRequestBody(String.valueOf(mCheckResult.get(k).getCheckResult())));
            params.put("tempQualityResultList["+k+"].content", toRequestBody(mCheckResult.get(k).getCheckContent()));
            long  checkid= mCheckResult.get( k).getCheckResultId();
            List<String>  photoList= mPhotoMap.get(checkid);
            for(int i = 0;i<photoList.size();i++){
                if(!photoList.get(i).equals("")){
                    File file = new File(photoList.get(i));
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    params.put("tempQualityResultList["+k+"].files\"; filename=\"" + i + ".jpg", requestFile);
                }
            }
        }

        BaseRequest.getInstance().getService()
                .addQualityInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {

                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if(t.getCode() == 1){
                            Utils.showToast("提交成功");
                            setResult(RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }

                });
    }


}
