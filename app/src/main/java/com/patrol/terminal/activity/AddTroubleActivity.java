package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.TssxPhotoAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LocalAddTrouble;
import com.patrol.terminal.bean.LocalTroubleTypeBean;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.TSSXLocalBean;
import com.patrol.terminal.sqlite.AppDataBase;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.CustomSpinner;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 添加隐患
 */
public class AddTroubleActivity extends BaseActivity {

    @BindView(R.id.title_back)
    RelativeLayout title_back;
    @BindView(R.id.title_name)
    TextView titleName;

    @BindView(R.id.fl_bz)
    EditText fl_bz;
    @BindView(R.id.fl_yhnr)
    EditText fl_yhnr;

    @BindView(R.id.yh_radiogroup)
    RadioGroup yh_radiogroup;
    @BindView(R.id.yh_rad_yz)
    RadioButton yh_rad_yz;
    @BindView(R.id.yh_rad_yb)
    RadioButton yh_rad_yb;
    @BindView(R.id.yh_rad_wj)
    RadioButton yh_rad_wj;
    @BindView(R.id.fl_gth)
    TextView fl_gth;
    @BindView(R.id.fl_clcs)
    CustomSpinner clcsSpinner;
    @BindView(R.id.fl_stsx)
    CustomSpinner tssxSpinner;
    @BindView(R.id.yh_fl_gridview)
    GridView yh_gridview;
    @BindView(R.id.trouble_type)
    CustomSpinner typeSpinner;


    private String task_id;
    private String line_id;
    private String line_name;
    private String tower_id;
    private String tower_name;
    private String YHDJStr;//隐患等级
    private PersonalTaskListBean personalTaskListBean;

    private List<String> photoList = new ArrayList<>();
    private TssxPhotoAdapter photoAdapter;
    private int position;//点击图片项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trouble);
        ButterKnife.bind(this);
        titleName.setText("添加隐患");
        task_id = getIntent().getStringExtra("task_id");
        line_id = getIntent().getStringExtra("line_id");
        line_name = getIntent().getStringExtra("line_name");// 线路名字
        tower_id = getIntent().getStringExtra("tower_id");// 杆塔id
        tower_name = getIntent().getStringExtra("tower_name");// 杆塔名字

        getLocalList();
        initView();
        if (Utils.isNetworkConnected(this)) {
            getTroubleType();
        } else {
            initClcsType();
        }

    }

    public void initView() {
        fl_gth.setText(line_name + " " + tower_name);
        //隐患等级
        yh_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                YHDJStr = rb.getText().toString().trim();
            }
        });

        photoAdapter = new TssxPhotoAdapter(this, photoList);
        yh_gridview.setAdapter(photoAdapter);
        yh_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                startCamera();
            }
        });

        //隐患类别
        typeSpinner.setOnItemSelectedListener(new CustomSpinner.OnItemSelectedListenerSpinner() {
            @Override
            public void onItemSelected(CustomSpinner parent, View view, int i, long l) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LocalTroubleTypeBean.TroubleType type = (LocalTroubleTypeBean.TroubleType) typeSpinner.getSelectObject();

                        clcsSpinner.attachDataSource(LocalTroubleTypeBean.indexList(type.getTypeStr()));
                    }
                });
            }
        });

    }

    @OnClick(R.id.add_fl_save)
    void saveFLTrouble() {
        saveTrouble();
    }

    public void getLocalList() {
        clcsSpinner.setSpinnerHeight(390);
        typeSpinner.attachDataSource(LocalTroubleTypeBean.getTroubleType());

        personalTaskListBean = PersonalTaskListBean.getPersonalTask(this, task_id);

        List<TSSXLocalBean> tssxLocalList = TSSXLocalBean.getTssxList(task_id, tower_id);//TSSXLocalBean.getTssxList(task_id, tower_id);
        if (tssxLocalList.size() > 0) {
            TSSXLocalBean txxsLocal = new TSSXLocalBean();
            txxsLocal.setKey("");
            txxsLocal.setValues("不关联属性");
            tssxLocalList.add(0, txxsLocal);
        }
//        tssxLocalList.addAll(TSSXLocalBean.getTssxList(task_id, tower_id));
        tssxSpinner.attachDataSource(tssxLocalList);

    }

    public void getTroubleType() {
        BaseRequest.getInstance().getService()
                .getTroubleType("sk,fnh,fl,ff,fsh,fwp,dz")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LocalTroubleTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LocalTroubleTypeBean>> t) throws Exception {
                        if (t.isSuccess()) {

                            FlowManager.getDatabase(AppDataBase.class).
                                    beginTransactionAsync(new ITransaction() {
                                        @Override
                                        public void execute(DatabaseWrapper databaseWrapper) {
                                            LocalTroubleTypeBean.delAllData();

                                            List<LocalTroubleTypeBean> list = t.getResults();
                                            for (int i = 0; i < list.size(); i++) {
                                                LocalTroubleTypeBean bean = list.get(i);
                                                bean.save();
                                            }
                                            initClcsType();
//                                            LocalTroubleTypeBean.TroubleType type = (LocalTroubleTypeBean.TroubleType)typeSpinner.getSelectObject();
//                                            clcsSpinner.attachDataSource(LocalTroubleTypeBean.indexList(type.getTypeStr()));
                                        }
                                    }).build().executeSync();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        initClcsType();
                    }
                });
    }

    //处理离线  处理措施
    public void initClcsType() {
        LocalTroubleTypeBean.TroubleType type = (LocalTroubleTypeBean.TroubleType) typeSpinner.getSelectObject();
        List<LocalTroubleTypeBean> clcsList = LocalTroubleTypeBean.indexList(type.getTypeStr());
        if (clcsList != null)
            clcsSpinner.attachDataSource(clcsList);
        else
            clcsSpinner.attachDataSource(null);
    }


    public void saveTrouble() {

        if (TextUtils.isEmpty(YHDJStr)) {
            Toast.makeText(this, "请选择隐含等级", Toast.LENGTH_LONG).show();
            return;
        }

        TSSXLocalBean tssxBean = (TSSXLocalBean) tssxSpinner.getSelectObject();
//        if (tssxBean != null) {
//            Toast.makeText(this, "请选择关联特殊属性", Toast.LENGTH_LONG).show();
//            return;
//        }

        if (photoList.size() == 0) {
            Toast.makeText(this, "请添加至少一张图片", Toast.LENGTH_LONG).show();
            return;
        }

        LocalTroubleTypeBean.TroubleType type = (LocalTroubleTypeBean.TroubleType) typeSpinner.getSelectObject();

        LocalAddTrouble trouble = new LocalAddTrouble();
        trouble.setTask_id(task_id);
        trouble.setType_id(type.getId());//"3"
        trouble.setType_name(type.getName());//"防雷"
        trouble.setGrade_sign(djToStr(YHDJStr));
        trouble.setContent(fl_yhnr.getText().toString().trim());
        trouble.setLine_id(line_id);
        trouble.setLine_name(line_name);
        trouble.setTower_id(tower_id);
        trouble.setTower_name(tower_name);
        trouble.setFind_user_id(personalTaskListBean.getUser_id());
        trouble.setFrom_user_id(personalTaskListBean.getUser_id());
        trouble.setFind_user_name(personalTaskListBean.getUser_name());
        trouble.setFind_dep_id(personalTaskListBean.getDep_id());
        trouble.setFind_dep_name(personalTaskListBean.getDep_name());
        trouble.setIsdownload("1");
        trouble.setIn_status("1");
        trouble.setRemarks(fl_bz.getText().toString().trim());
        if (tssxBean != null) {
            trouble.setWares_id(tssxBean.getKey());
            trouble.setWares_name(tssxBean.getValues());
        } else {
            trouble.setWares_id("");
            trouble.setWares_name("");
        }
        trouble.setAdvice_deal_notes(clcsSpinner.getSelectItem());

        String path = "";
        for (int j = 0; j < photoList.size(); j++) {
            if (!TextUtils.isEmpty(path)) {
                path = path + ";" + photoList.get(j);
            } else {
                path = photoList.get(j);
            }
        }
        trouble.setTroubleFiles(path);
        trouble.save();
        Toast.makeText(AddTroubleActivity.this, "保存成功", Toast.LENGTH_LONG).show();

        // 标记离线操作
        if (personalTaskListBean != null) {
            personalTaskListBean.setIs_save("0");
            personalTaskListBean.update();
        }

        setResult(RESULT_OK);
        finish();

    }


    public String djToStr(String djStr) {
        if (TextUtils.isEmpty(djStr)) djStr = Constant.DJ_YB_STR;
        String str = "";
        if (djStr.equals("一般")) {
            str = "1";
        } else if (djStr.equals("严重")) {
            str = "2";
        } else if (djStr.equals("危急")) {
            str = "3";
        }
        return str;
    }

    @OnClick(R.id.title_back)
    void titleBack() {
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.DEFECT_REQUEST_CODE:
                    Log.d("TAG", "success");
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    String path = Environment.getExternalStorageDirectory().getPath()
                            + "/MyPhoto/" + DateUatil.getDateStr() + "_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                    try {
                        //保存本地成功 刷新刷新数据添加到页面
                        FileUtil.saveFile(bitmap, path);
                        Log.e("保存照片地址", path);
                        if (position == photoList.size()) {
                            photoList.add(path);
                        } else {
                            photoList.set(position, path);
                        }

                        photoAdapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    //打开相机
    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constant.DEFECT_REQUEST_CODE);
    }


}
