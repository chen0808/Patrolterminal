package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MonitoringRecordAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.DayListBean;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*质量监督记录表*/
public class MonitoringRecordActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.rv_monitoring_record)
    RecyclerView rvMonitoringRecord;
    private String[] data = {"一、现场情况", "二、到岗到位检查", "三、现场反违章检查", "四、违章检查参考", "五、检查监督要求"};
    private DayListBean dayListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_record);
        ButterKnife.bind(this);
        titleName.setText("到场质量监督记录");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvMonitoringRecord.setLayoutManager(manager);
        MonitoringRecordAdapter adapter = new MonitoringRecordAdapter(R.layout.item_monitoring_record, Arrays.asList(data));
        rvMonitoringRecord.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

        initData();

    }

    private void initData() {
        dayListBean = getIntent().getParcelableExtra("bean");
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent();
                intent.putExtra("bean", dayListBean);
                intent.setClass(this, SituationOnSiteActivity.class);
                startActivity(intent);
                break;

            case 1:
                Intent intent1 = new Intent();
                intent1.setClass(this, GetToPostCheckActivity.class);
                startActivity(intent1);
                break;

            case 2:
                Intent intent2 = new Intent();
                intent2.setClass(this, FieldAntiInspectionActivity.class);
                startActivity(intent2);
                break;

            case 3:
                Intent intent3 = new Intent();
                intent3.setClass(this, IllegalInspectionReferenceActivity.class);
                startActivity(intent3);
                break;

            case 4:
                Intent intent4 = new Intent();
                intent4.setClass(this, InspectionRequirementsActivity.class);
                startActivity(intent4);
                break;
        }
    }


}
