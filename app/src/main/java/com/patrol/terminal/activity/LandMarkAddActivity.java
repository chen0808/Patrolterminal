package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.CheckProjectServiceBean;
import com.patrol.terminal.bean.InitiateProjectBean2;
import com.patrol.terminal.bean.LocalLandMarkBean;
import com.patrol.terminal.bean.LocalLandMarkBean2;
import com.patrol.terminal.bean.LocalWorkWeeklyBean;
import com.patrol.terminal.overhaul.ProjectSearchActivity;
import com.patrol.terminal.overhaul.ProjectSearchActivityNew;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * 里程碑
 */
public class LandMarkAddActivity extends AppCompatActivity {

    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_tv)
    TextView title_setting_tv;
    @BindView(R.id.title_setting)
    RelativeLayout title_setting;

    @BindView(R.id.landmark_add_jblb)
    TextView landmark_add_jblb;
    @BindView(R.id.landmark_bar)
    SeekBar landmarkBar;
    @BindView(R.id.gcjb_jd_tv)
    TextView gcjb_jd_tv;
    @BindView(R.id.landmark_sgqk)
    EditText landmarkSgqk;
    @BindView(R.id.landmark_bz)
    EditText landmarkBz;
    @BindView(R.id.landmark_save)
    Button landmarkSave;
    @BindView(R.id.landmark_add_ssxm)
    TextView landmark_add_ssxm;

    private int probar;
    private boolean addStatus = false;
    private InitiateProjectBean2 clickedCheckProjectBean;
    private int checkedItem = 0;//上报阶段  项目状态（0：项目前期，1：项目立项，2：设计管理，3：招标管理，4：合同管理，5：进度管理，6：前期，7：实施准备，8：在建，9：停缓期，10：验收，11：验收，12：竣工，13：保内，14：保外，15：解除，16：完成）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_add);
        ButterKnife.bind(this);

        titleName.setText("里程碑");

        initView();
    }


    public void initView() {

        landmark_add_jblb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LandMarkAddActivity.this);
                alertBuilder.setTitle("选择上报阶段");
                alertBuilder.setSingleChoiceItems(Constant.lcbList, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int options1) {
                        checkedItem = options1;
                        landmark_add_jblb.setText(Constant.lcbList[options1]);
                        dialog.dismiss();
//                        if(LocalLandMarkBean.addStatus(types[options1])){
//                            Utils.showToast("该阶段已上报");
//                            addStatus = true;
//                            return;
//                        }else{
//                            addStatus = false;
//                        }
                    }
                });
                AlertDialog typeDialog = alertBuilder.create();
                typeDialog.show();
            }
        });

        landmarkBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probar = progress;
                gcjb_jd_tv.setText("进度（"+progress+"%）");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    @OnClick({R.id.title_back,R.id.landmark_save,R.id.landmark_add_ssxm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.landmark_add_ssxm:
                Intent intent = new Intent();
                intent.setClass(this, ProjectSearchActivityNew.class);
                startActivityForResult(intent, Constant.GCJB_ADD_PROJECT);
                break;
            case R.id.landmark_save:

                if(clickedCheckProjectBean == null){
                    Utils.showToast("请选择上报项目");
                    return;
                }

                String jblb = landmark_add_jblb.getText().toString();
                String qkms = landmarkSgqk.getText().toString().trim();
                if(jblb.equals("请选择")){
                   Utils.showToast("请选择上报阶段");
                    return;
                }

                if(addStatus){
                    Utils.showToast("该阶段已上报,请重新选择");
                    return;
                }

                if(TextUtils.isEmpty(qkms)){
                    Utils.showToast("请填写情况描述");
                    return;
                }
                if(probar == 0){
                    Utils.showToast("请选择阶段进度");
                    return;
                }

                LocalLandMarkBean2 bean = new LocalLandMarkBean2();
                bean.setUser_name(SPUtil.getUserName(this));
                bean.setTemp_project_id(clickedCheckProjectBean.getName());
                bean.setTemp_project_name(clickedCheckProjectBean.getProject_no());
                bean.setContent(qkms);
                bean.setStatus(String.valueOf(checkedItem));
                bean.setPlan(probar+"");
                bean.setRemarks(landmarkBz.getText().toString().trim());
                bean.setCreated_date(DateUatil.getTime());

                saveLcb(bean);
                break;

        }
    }

    public void saveLcb(LocalLandMarkBean2 bean) {
        ProgressDialog.show(this);
        BaseRequest.getInstance().getService()
                .saveLcbPOST(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast("提交成功");

                        Intent intent = new Intent();
                        intent.putExtra("search_project_item", clickedCheckProjectBean);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.GCJB_ADD_PROJECT:
                    clickedCheckProjectBean = data.getParcelableExtra("search_project_item");
                    if (clickedCheckProjectBean != null) {
                        landmark_add_ssxm.setText(clickedCheckProjectBean.getName());
                    }
                    break;
            }
        }
    }

}
