package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.patrol.terminal.bean.LocalLandMarkBean;
import com.patrol.terminal.bean.LocalWorkWeeklyBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private int probar;
    private boolean addStatus = false;


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
                String[] types = new String[]{"项目前期", "项目立项", "设计管理", "招标管理",
                        "合同管理","进度管理","前期","实施准备","在建","停缓建","验收","竣工",
                        "保内","保外","解除"};
                int checkedItem = 0;
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LandMarkAddActivity.this);
                alertBuilder.setTitle("选择上报阶段");
                alertBuilder.setSingleChoiceItems(types, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int options1) {

                        landmark_add_jblb.setText(types[options1]);
                        dialog.dismiss();
                        if(LocalLandMarkBean.addStatus(types[options1])){
                            Utils.showToast("该阶段已上报");
                            addStatus = true;
                            return;
                        }else{
                            addStatus = false;
                        }
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


    @OnClick({R.id.title_back,R.id.landmark_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.landmark_save:

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

                LocalLandMarkBean bean = new LocalLandMarkBean();
                bean.setUser_name("马宝龙");
                bean.setLandmark_sbjd(jblb);
                bean.setLandmark_jd(probar);
                bean.setLandmark_qkms(qkms);
                bean.setLandmark_bz(landmarkBz.getText().toString().trim());
                bean.setDate(DateUatil.getTime());
                bean.setYear(DateUatil.getYear());
                bean.save();
                setResult(RESULT_OK);
                Utils.showToast("保存成功");
                finish();
                break;

        }
    }

}
