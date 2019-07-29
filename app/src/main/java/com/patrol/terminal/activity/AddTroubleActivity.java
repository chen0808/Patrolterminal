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
import com.patrol.terminal.bean.JDDZbean_Table;
import com.patrol.terminal.bean.LocalAddTrouble;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.PersonalTaskListBean_Table;
import com.patrol.terminal.bean.TSSXLocalBean;
import com.patrol.terminal.bean.TSSXLocalBean_Table;
import com.patrol.terminal.bean.TroubleTypeBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CustomSpinner;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddTroubleActivity extends BaseActivity {

    @BindView(R.id.title_back)
    RelativeLayout title_back;
    @BindView(R.id.title_name)
    TextView titleName;

    //    @BindView(R.id.fl_clcs)
//    EditText fl_clcs;
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

    private Disposable subscribe;
    private LocalAddTrouble addTrouble;
    private String task_id;
    private String line_id;
    private String line_name;
    private String tower_id;
    private String tower_name;
    private String YHDJStr;//隐患等级
    private PersonalTaskListBean personalTaskListBean;
    private TSSXLocalBean tssxBean;
    private List<TSSXLocalBean> tssxList;

    private List<TroubleTypeBean> typeList = new ArrayList<>();
    private List<TroubleTypeBean> selectClcsList = new ArrayList<>();
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
        getTroubleType();
        getLocalTssxList();
        initView();
    }

    public void initView() {
        fl_gth.setText(line_name + " " + tower_name);

        tssxSpinner.setOnItemSelectedListener(new CustomSpinner.OnItemSelectedListenerSpinner() {
            @Override
            public void onItemSelected(CustomSpinner parent, View view, int i, long l) {
                tssxBean = tssxList.get(i);
            }
        });

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

    }

    @OnClick(R.id.add_fl_save)
    void saveFLTrouble() {
        String yhnr = fl_yhnr.getText().toString().trim();
        String bz = fl_bz.getText().toString().trim();
        saveTrouble();
    }

    public void getLocalTssxList() {

        personalTaskListBean = SQLite.select().from(PersonalTaskListBean.class)
                .where(PersonalTaskListBean_Table.id.eq(task_id), JDDZbean_Table.user_id.eq(SPUtil.getUserId(this)))
                .querySingle();

        tssxList = SQLite.select().from(TSSXLocalBean.class)
                .where(TSSXLocalBean_Table.task_id.is(task_id))
                .and(TSSXLocalBean_Table.tower_id.is(tower_id))
                .queryList();

        tssxSpinner.attachDataSource(tssxList);
    }

    public void getTroubleType() {
        BaseRequest.getInstance().getService()
                .getTroubleType("sk,fnh,fl,ff,fsh,fwp,dz")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleTypeBean>> t) throws Exception {
                        if (t.isSuccess()) {
                            typeList.clear();
                            typeList.addAll(t.getResults());

                            clcsSpinner.attachDataSource(indexList(TroubleTypeBean.TROUBLE_FL));
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }


    public List<TroubleTypeBean> indexList(String type) {
        selectClcsList.clear();
        for (int i = 0; i < typeList.size(); i++) {
            TroubleTypeBean typeBean = typeList.get(i);
            if (typeBean.getP_code().equals(type)) {
                selectClcsList.add(typeBean);
            }
        }
        return selectClcsList;
    }

    public void saveTrouble() {

        if (TextUtils.isEmpty(YHDJStr)) {
            Toast.makeText(this, "请选择隐含等级", Toast.LENGTH_LONG).show();
            return;
        }

        if (tssxBean == null && tssxList.size() > 0) {
            Toast.makeText(this, "请选择关联特殊属性", Toast.LENGTH_LONG).show();
            return;
        }

        if (photoList.size() > 0) {
            Toast.makeText(this, "请添加至少一张图片", Toast.LENGTH_LONG).show();
            return;
        }

        LocalAddTrouble trouble = new LocalAddTrouble();
        trouble.setTask_id(task_id);
        trouble.setType_id("3");
        trouble.setType_name("防雷");
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

        //                标记离线操作
        if (personalTaskListBean != null) {
            personalTaskListBean.setIs_save("0");
            personalTaskListBean.update();
        }

        setResult(RESULT_OK);
        finish();

//        ProgressDialog.show(this,false,"正在加载中...");
//        Map<String, RequestBody> params = new HashMap<>();
//        params.put("task_id", toRequestBody(task_id));
//        params.put("type_id", toRequestBody("3"));
//        params.put("type_name", toRequestBody("防雷"));
//        params.put("grade_sign", toRequestBody(djToStr(YHDJStr)));
//        params.put("content", toRequestBody(fl_yhnr.getText().toString().trim()));
//        params.put("line_id", toRequestBody(line_id));
//        params.put("line_name", toRequestBody(line_name));
//        params.put("tower_id", toRequestBody(tower_id));
//        params.put("tower_name", toRequestBody(tower_name));
//        params.put("find_user_id", toRequestBody(personalTaskListBean.getUser_id()));
//        params.put("from_user_id", toRequestBody(personalTaskListBean.getUser_id()));
//        params.put("find_user_name", toRequestBody(personalTaskListBean.getUser_name()));
//        params.put("find_dep_name", toRequestBody(personalTaskListBean.getDep_name()));
//        params.put("find_dep_id", toRequestBody(personalTaskListBean.getDep_id()));
//        params.put("in_status", toRequestBody("1"));//默认
//        params.put("remarks", toRequestBody(fl_bz.getText().toString().trim()));
//        if(tssxBean != null){
//            params.put("wares_id", toRequestBody(tssxBean.getKey()));//特殊属性
//            params.put("wares_name", toRequestBody(tssxBean.getValues()));
//        }else{
//            params.put("wares_id",toRequestBody(""));//
//            params.put("wares_name", toRequestBody(""));
//        }
//
//        params.put("advice_deal_notes", toRequestBody(clcsSpinner.getSelectItem()));
//
//        for (int j = 0; j < photoList.size(); j++) {
//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(photoList.get(j)));
//            params.put("troubleFiles\"; filename=\"" + photoList.get(j), requestFile);
//        }
//
//        BaseRequest.getInstance().getService()
//                .saveTrouble(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver(this) {
//                    protected void onSuccees(BaseResult t) throws Exception {
//                        if (t.isSuccess()) {
//                            Toast.makeText(AddTroubleActivity.this, "提交成功", Toast.LENGTH_LONG).show();
//                            setResult(RESULT_OK);
//                            finish();
//                        } else {
//                            Toast.makeText(AddTroubleActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        ProgressDialog.cancle();
//                    }
//
//                });
    }

    public RequestBody toRequestBody(String value) {
        if (!TextUtils.isEmpty(value)) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
            return requestBody;
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            return requestBody;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

}
