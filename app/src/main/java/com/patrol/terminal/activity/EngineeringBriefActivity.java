package com.patrol.terminal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 工程简报
 */
public class EngineeringBriefActivity extends AppCompatActivity {

    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;

    @BindView(R.id.gcjb_yzf)
    TextView gcjb_yzf;
    @BindView(R.id.gcjb_jlf)
    TextView gcjb_jlf;
    @BindView(R.id.gcjb_sgf)
    TextView gcjb_sgf;
    @BindView(R.id.gcjb_add)
    ImageButton gcjb_add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineering_brief);
        ButterKnife.bind(this);

        titleName.setText("工程简报");



    }

    @OnClick({R.id.title_back, R.id.gcjb_yzf, R.id.gcjb_jlf, R.id.gcjb_sgf, R.id.gcjb_add})
    public void onViewClicked(View view) {
        Intent intent = new Intent(EngineeringBriefActivity.this,EngineeringBriefListActivity.class);

        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.gcjb_yzf:
                intent.putExtra(Constant.GCJB_TYPE_STR,Constant.GCJB_YZF_STR);
                startActivity(intent);
                break;
            case R.id.gcjb_jlf:
                intent.putExtra(Constant.GCJB_TYPE_STR,Constant.GCJB_JLF_STR);
                startActivity(intent);
                break;
            case R.id.gcjb_sgf:
                intent.putExtra(Constant.GCJB_TYPE_STR,Constant.GCJB_SGF_STR);
                startActivity(intent);
                break;
            case R.id.gcjb_add:
                Intent intent2 = new Intent(EngineeringBriefActivity.this,EngineeringBriefAddActivity.class);
                intent2.putExtra(Constant.GCJB_TYPE_STR,Constant.GCJB_SGF_STR);
                startActivity(intent2);
                break;

        }
    }


}
