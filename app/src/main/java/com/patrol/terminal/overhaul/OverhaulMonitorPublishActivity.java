package com.patrol.terminal.overhaul;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.ClassMemberBean;
import com.patrol.terminal.bean.OverhaulMonitorSendBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulSendUserBean;
import com.patrol.terminal.bean.OverhaulZZSendBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*班长发布周检修工作*/
public class OverhaulMonitorPublishActivity extends BaseActivity {
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
    @BindView(R.id.week_maintenace_tv)
    TextView weekMaintenaceTv;
    @BindView(R.id.other_tv)
    TextView otherTv;
    @BindView(R.id.outage_line_name)
    TextView outageLineName;
    @BindView(R.id.outage_plan_time)
    TextView outagePlanTime;
    @BindView(R.id.work_content)
    TextView workContent;
    @BindView(R.id.task_sourse)
    TextView taskSourse;
    @BindView(R.id.weekly_maintenance_ll)
    LinearLayout weeklyMaintenanceLl;
    @BindView(R.id.select_member_spinner)
    Spinner selectMemberSpinner;
    @BindView(R.id.send_btn)
    Button sendBtn;
    @BindView(R.id.weekly_maintenance_rl)
    RelativeLayout weeklyMaintenanceRl;
    @BindView(R.id.rist_level)
    TextView ristLevel;

//    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
//    private GridViewAdapter mGridViewAddImgAdapter; //展示上传的图片，文件的适配器
//
//    private ArrayList<WeekReceiveBean> mReceiverList = new ArrayList<>();
//    private WeekReceiveAdapter mListViewReceiveAdapter;

    //private List<OverhaulSendUserBean> overhaulSendUserBeans = new ArrayList<>();
    private ClassMemberBean.UserListBean selectMemberBean;

    private OverhaulMonthBean overhaulMonthBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_weekly_maintenance_activity);
        ButterKnife.bind(this);

        initView();

//        initGridView();
//        initListView();
    }

    private void initView() {
        titleName.setText("周检修工作");

        overhaulMonthBean = getIntent().getParcelableExtra("bean");
        outageLineName.setText(overhaulMonthBean.getLine_name());
        outagePlanTime.setText(overhaulMonthBean.getBlackout_days()+"天");
        workContent.setText(overhaulMonthBean.getTask_content());

        taskSourse.setText(overhaulMonthBean.getTask_source());
        ristLevel.setText(overhaulMonthBean.getRisk_level());

        getAllSendToPerson();

    }

    private void getAllSendToPerson() {
        BaseRequest.getInstance().getService()
                .getAllClassMember("B7FF21A674F144DE8D13EB8B3B79E64F")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ClassMemberBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<ClassMemberBean>> t) throws Exception {
                        if(t.getCode() == 1) {
                            if (t.getResults() != null && t.getResults().size() > 0) {
                                List<ClassMemberBean.UserListBean> userListBeans = t.getResults().get(0).getUserList();
                                List<String> list = new ArrayList<String>();
                                for (int i = 0; i < userListBeans.size(); i++) {
                                    String userName = userListBeans.get(i).getName();
                                    list.add(userName);
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(OverhaulMonitorPublishActivity.this, android.R.layout.simple_spinner_item, list);
                                selectMemberSpinner.setAdapter(adapter);

                                selectMemberSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
                                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                        selectMemberBean = userListBeans.get(position);


                                    }
                                    public void onNothingSelected(AdapterView<?> arg0) {
                                    }
                                });
                            }

                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    private void sendToMember() {
        OverhaulMonitorSendBean overhaulMonitorSendBean = new OverhaulMonitorSendBean();
        overhaulMonitorSendBean.setId(overhaulMonthBean.getId());
        overhaulMonitorSendBean.setTask_status("2");
        overhaulMonitorSendBean.setUserId4(selectMemberBean.getId());
        overhaulMonitorSendBean.setUserName4(selectMemberBean.getName());
        overhaulMonitorSendBean.setSign("4");

        BaseRequest.getInstance().getService()
                .sendOverhaulMonitorPlan(overhaulMonitorSendBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulSendUserBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulSendUserBean>> t) throws Exception {
                        if(t.getCode() == 1) {
                            //result = t.getResults();
                            Toast.makeText(OverhaulMonitorPublishActivity.this, "分发成功！", Toast.LENGTH_SHORT).show();
                            RxRefreshEvent.publish("publishRepair");
                            finish();
                        }else {
                            Toast.makeText(OverhaulMonitorPublishActivity.this, "分发失败！", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    //初始化展示上传图片的GridView
    /*private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(this, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (mPicList.size() == Constant.MAX_SELECT_PIC_NUM) {
                        //最多添加5张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(Constant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }*/

    //查看大图
    /*private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constant.IMG_LIST, mPicList);
        intent.putExtra(Constant.POSITION, position);
        startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
    }*/

    /* */

    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     *//*
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == Constant.REQUEST_CODE_MAIN && resultCode == Constant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(Constant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }*/

   /* private void initListView() {
        WeekReceiveBean bean = new WeekReceiveBean();
        bean.setWorkLines("XXXXXX");
        bean.setWorkContent("XXXXX");
        bean.setReceiver("王小龙");

        WeekReceiveBean bean1 = new WeekReceiveBean();
        bean1.setWorkLines("XXXXXXX");
        bean1.setWorkContent("XXXXX");
        bean1.setReceiver("王小龙");

        WeekReceiveBean bean2 = new WeekReceiveBean();
        bean2.setWorkLines("XXXXXXXXXX");
        bean2.setWorkContent("XXXXX");
        bean2.setReceiver("王小龙");

        mReceiverList.add(bean);
        mReceiverList.add(bean1);
        mReceiverList.add(bean2);


        mListViewReceiveAdapter = new WeekReceiveAdapter(this, mReceiverList);
        listView.setAdapter(mListViewReceiveAdapter);
    }*/
    @OnClick({R.id.title_back, R.id.send_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.send_btn:
                sendToMember();
                break;
        }
    }
}
