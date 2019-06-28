package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AcrossBean;
import com.patrol.terminal.bean.BirdBean;
import com.patrol.terminal.bean.BreakBean;
import com.patrol.terminal.bean.DisasterBean;
import com.patrol.terminal.bean.FireBean;
import com.patrol.terminal.bean.FloodBean;
import com.patrol.terminal.bean.ThunderBean;
import com.patrol.terminal.bean.TowerListBean;
import com.patrol.terminal.bean.WaresListBean;
import com.patrol.terminal.bean.WindBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.PickerUtils;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TestActivity extends BaseActivity {
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
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.level)
    Spinner level;
    @BindView(R.id.type)
    Spinner type;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.iv_et1)
    ImageView ivEt1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.iv_et2)
    ImageView ivEt2;
    @BindView(R.id.et3)
    EditText et3;
    @BindView(R.id.iv_et3)
    ImageView ivEt3;
    @BindView(R.id.et4)
    EditText et4;
    @BindView(R.id.iv_et4)
    ImageView ivEt4;
    @BindView(R.id.et5)
    EditText et5;
    @BindView(R.id.iv_et5)
    ImageView ivEt5;
    @BindView(R.id.et6)
    EditText et6;
    @BindView(R.id.iv_et6)
    ImageView ivEt6;
    @BindView(R.id.cb1)
    CheckBox cb1;
    @BindView(R.id.cb2)
    CheckBox cb2;
    @BindView(R.id.cb3)
    CheckBox cb3;
    @BindView(R.id.cb4)
    CheckBox cb4;
    @BindView(R.id.cb5)
    CheckBox cb5;
    @BindView(R.id.cb6)
    CheckBox cb6;
    @BindView(R.id.iv_item_save)
    ImageView ivItemSave;
    @BindView(R.id.sp_tower_small)
    Spinner spTowerSmall;
    @BindView(R.id.sp_tower_big)
    Spinner spTowerBig;
    @BindView(R.id.tv_line_name)
    TextView tvLineName;
    @BindView(R.id.et_across_name)
    EditText etAcrossName;
    @BindView(R.id.tv_lat_lon_small)
    TextView tvLatLonSmall;
    @BindView(R.id.tv_lat_lon_big)
    TextView tvLatLonBig;
    @BindView(R.id.et_tension)
    EditText etTension;
    @BindView(R.id.sp_tower_start)
    Spinner spTowerStart;
    @BindView(R.id.sp_tower_end)
    Spinner spTowerEnd;
    @BindView(R.id.iv_expand2)
    ImageView ivExpand2;
    @BindView(R.id.et_bird_orders_company)
    EditText etBirdOrdersCompany;
    @BindView(R.id.tv_bird_orders_time)
    TextView tvBirdOrdersTime;
    @BindView(R.id.tv_bird_arrival_time)
    TextView tvBirdArrivalTime;
    @BindView(R.id.et7)
    EditText et7;
    @BindView(R.id.cb7)
    CheckBox cb7;
    @BindView(R.id.iv_et7)
    ImageView ivEt7;
    @BindView(R.id.et_bird_deal_notes)
    EditText etBirdDealNotes;
    @BindView(R.id.sp_tower_start_bird)
    Spinner spTowerStartBird;
    @BindView(R.id.sp_tower_end_bird)
    Spinner spTowerEndBird;
    @BindView(R.id.et_bird_total)
    EditText etBirdTotal;
    @BindView(R.id.tv_bird_plan_time)
    TextView tvBirdPlanTime;
    @BindView(R.id.btn_add_bird)
    Button btnAddBird;
    @BindView(R.id.et_year_bird)
    EditText etYearBird;
    @BindView(R.id.et_bird_category)
    EditText etBirdCategory;
    @BindView(R.id.tv_bird_reporting_time)
    TextView tvBirdReportingTime;
    @BindView(R.id.et_bird_batch)
    EditText etBirdBatch;
    @BindView(R.id.ll_bird)
    LinearLayout llBird;
    @BindView(R.id.sp_tower_start_thunder)
    Spinner spTowerStartThunder;
    @BindView(R.id.sp_tower_end_thunder)
    Spinner spTowerEndThunder;
    @BindView(R.id.et_thunder_num)
    EditText etThunderNum;
    @BindView(R.id.et_thunder_deal_notes)
    EditText etThunderDealNotes;
    @BindView(R.id.et_thunder_company)
    EditText etThunderCompany;
    @BindView(R.id.tv_thunder_time)
    TextView tvThunderTime;
    @BindView(R.id.ll_thunder)
    LinearLayout llThunder;
    @BindView(R.id.iv_expand3)
    ImageView ivExpand3;
    @BindView(R.id.cb_thunder_status)
    CheckBox cbThunderStatus;
    @BindView(R.id.btn_add_thunder)
    Button btnAddThunder;
    @BindView(R.id.sp_tower_start_wind)
    Spinner spTowerStartWind;
    @BindView(R.id.sp_tower_end_wind)
    Spinner spTowerEndWind;
    @BindView(R.id.et_wind_deal_notes)
    EditText etWindDealNotes;
    @BindView(R.id.cb_wind_status)
    CheckBox cbWindStatus;
    @BindView(R.id.tv_wind_plan_time)
    TextView tvWindPlanTime;
    @BindView(R.id.tv_wind_done_time)
    TextView tvWindDoneTime;
    @BindView(R.id.btn_add_wind)
    Button btnAddWind;
    @BindView(R.id.ll_wind)
    LinearLayout llWind;
    @BindView(R.id.btn_add_fire)
    Button btnAddFire;
    @BindView(R.id.ll_fire)
    LinearLayout llFire;
    @BindView(R.id.btn_add_break)
    Button btnAddBreak;
    @BindView(R.id.ll_break)
    LinearLayout llBreak;
    @BindView(R.id.btn_add_disaster)
    Button btnAddDisaster;
    @BindView(R.id.ll_disaster)
    LinearLayout llDisaster;
    @BindView(R.id.iv_expand4)
    ImageView ivExpand4;
    @BindView(R.id.iv_expand5)
    ImageView ivExpand5;
    @BindView(R.id.iv_expand6)
    ImageView ivExpand6;
    @BindView(R.id.iv_expand7)
    ImageView ivExpand7;
    @BindView(R.id.sp_tower_start_fire)
    Spinner spTowerStartFire;
    @BindView(R.id.sp_tower_end_fire)
    Spinner spTowerEndFire;
    @BindView(R.id.et_fire_reason)
    EditText etFireReason;
    @BindView(R.id.et_fire_deal_notes)
    EditText etFireDealNotes;
    @BindView(R.id.tv_fire_done_time)
    TextView tvFireDoneTime;
    @BindView(R.id.cb_fire_status)
    CheckBox cbFireStatus;
    @BindView(R.id.sp_tower_start_disaster)
    Spinner spTowerStartDisaster;
    @BindView(R.id.sp_tower_end_disaster)
    Spinner spTowerEndDisaster;
    @BindView(R.id.et_disaster_disaster_type)
    EditText etDisasterDisasterType;
    @BindView(R.id.et_disaster_join_reason)
    EditText etDisasterJoinReason;
    @BindView(R.id.et_disaster_main_reason)
    EditText etDisasterMainReason;
    @BindView(R.id.et_disaster_scale)
    EditText etDisasterScale;
    @BindView(R.id.et_disaster_deal_notes)
    EditText etDisasterDealNotes;
    @BindView(R.id.et_disaster_final_deal)
    EditText etDisasterFinalDeal;
    @BindView(R.id.tv_disaster_plan_time)
    TextView tvDisasterPlanTime;
    @BindView(R.id.tv_disaster_done_time)
    TextView tvDisasterDoneTime;
    @BindView(R.id.cb_disaster_status)
    CheckBox cbDisasterStatus;
    @BindView(R.id.sp_tower_start_break)
    Spinner spTowerStartBreak;
    @BindView(R.id.sp_tower_end_break)
    Spinner spTowerEndBreak;
    @BindView(R.id.et_break_content)
    EditText etBreakContent;
    @BindView(R.id.et_break_trouble_wares)
    EditText etBreakTroubleWares;
    @BindView(R.id.et_break_duty_unit)
    EditText etBreakDutyUnit;
    @BindView(R.id.et_break_contact)
    EditText etBreakContact;
    @BindView(R.id.et_break_phone)
    EditText etBreakPhone;
    @BindView(R.id.cb_break_is_video)
    CheckBox cbBreakIsVideo;
    @BindView(R.id.tv_break_plan_time)
    TextView tvBreakPlanTime;
    @BindView(R.id.tv_break_done_time)
    TextView tvBreakDoneTime;
    @BindView(R.id.et_break_company)
    EditText etBreakCompany;
    @BindView(R.id.cb_break_status)
    CheckBox cbBreakStatus;
    @BindView(R.id.et_break_deal_notes_first)
    EditText etBreakDealNotesFirst;
    @BindView(R.id.et_break_deal_notes_second)
    EditText etBreakDealNotesSecond;
    @BindView(R.id.et_break_deal_notes_third)
    EditText etBreakDealNotesThird;
    @BindView(R.id.et_break_deal_notes_fourth)
    EditText etBreakDealNotesFourth;
    @BindView(R.id.et_break_deal_notes_fifth)
    EditText etBreakDealNotesFifth;
    @BindView(R.id.iv_expand8)
    ImageView ivExpand8;
    @BindView(R.id.sp_tower_start_flood)
    Spinner spTowerStartFlood;
    @BindView(R.id.sp_tower_end_flood)
    Spinner spTowerEndFlood;
    @BindView(R.id.et_flood_content)
    EditText etFloodContent;
    @BindView(R.id.et_flood_deal_notes)
    EditText etFloodDealNotes;
    @BindView(R.id.et_flood_deal_type)
    EditText etFloodDealType;
    @BindView(R.id.et_flood_earth_work)
    EditText etFloodEarthWork;
    @BindView(R.id.tv_flood_done_time)
    TextView tvFloodDoneTime;
    @BindView(R.id.cb_flood_is_deal)
    CheckBox cbFloodIsDeal;
    @BindView(R.id.cb_flood_is_repair)
    CheckBox cbFloodIsRepair;
    @BindView(R.id.cb_flood_status)
    CheckBox cbFloodStatus;
    @BindView(R.id.btn_add_flood)
    Button btnAddFlood;
    @BindView(R.id.ll_flood)
    LinearLayout llFlood;
    private String lineId;
    private List<String> towerNameList = new ArrayList<>();
    private String typeName;
    private String acrossName;
    private String smallTowerId;
    private String smallTowerName;
    private String bigTowerId;
    private String bigTowerName;
    private String tension;
    private String isIndpendent;
    private String isDouble;
    private String isDrainage;
    private String isMetal;
    private String isVideo;
    private String isAdss;
    private List<TowerListBean> results;
    private String startId;
    private String startName;
    private String endName;
    private String endId;
    private String typeId;
    private String lineName;
    private String taskId;
    private String towers;
    private String towerId;
    private String endIdBird;
    private String startIdBird;
    private String startNameBird;
    private String endNameBird;
    private String towersBird;
    private String installed;
    private String startIdThunder;
    private String endIdThunder;
    private String startNameThunder;
    private String endNameThunder;
    private String towersThunder;
    private String statusThunder;
    private String startIdWind;
    private String endIdWind;
    private String startNameWind;
    private String endNameWind;
    private String towersWind;
    private String statusWind;
    private String endIdFire;
    private String startIdFire;
    private String statusFire;
    private String startNameFire;
    private String endNameFire;
    private String towersFire;
    private String startNameBreak;
    private String endNameBreak;
    private String towersBreak;
    private String startIdDisaster;
    private String endIdDisaster;
    private String startNameDisaster;
    private String endNameDisaster;
    private String towersDisaster;
    private String statusDisaster;
    private String endIdBreak;
    private String startIdBreak;
    private String statusBreak;
    private String isBreakVideo;
    private String startIdFlood;
    private String endIdFlood;
    private String startNameFlood;
    private String endNameFlood;
    private String towersFlood;
    private String statusFlood;
    private String isFloodDeal;
    private String isFloodRepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        titleName.setText("特殊属性编辑");
        lineName = getIntent().getStringExtra("line_name");
        lineId = (String) SPUtil.get(this, "ids", "line_id", "");
        taskId = (String) SPUtil.get(this, "ids", "task_id", "");
        towerId = (String) SPUtil.get(this, "ids", "tower_id", "");
        tvLineName.setText(lineName);
        initData();
    }

    private void initData() {
        BaseRequest.getInstance().getService()
                .getWares("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WaresListBean>>(this) {


                    @Override
                    protected void onSuccees(BaseResult<List<WaresListBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        List<WaresListBean> results = t.getResults();

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }

                });

        BaseRequest.getInstance().getService()
                .towerList(lineId, "sort")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TowerListBean>>(this) {


                    @Override
                    protected void onSuccees(BaseResult<List<TowerListBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        results = t.getResults();
                        towerNameList.clear();
                        for (int i = 0; i < results.size(); i++) {
                            towerNameList.add(results.get(i).getName());
                        }
                        spTowerSmall.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerBig.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerStart.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerEnd.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerStartBird.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerEndBird.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerStartThunder.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerEndThunder.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerStartWind.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerEndWind.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerStartFire.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerEndFire.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerStartBreak.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerEndBreak.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerStartDisaster.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerEndDisaster.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerStartFlood.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                        spTowerEndFlood.setAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_dropdown_item_1line, towerNameList));
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }

                });
        spTowerSmall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                smallTowerId = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerBig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bigTowerId = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startId = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endId = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerStartBird.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startIdBird = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerEndBird.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endIdBird = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerStartThunder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startIdThunder = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerEndThunder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endIdThunder = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerStartWind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startIdWind = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerEndWind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endIdWind = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerStartFire.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startIdFire = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerEndFire.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endIdFire = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerStartBreak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startIdBreak = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerEndBreak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endIdBreak = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerStartDisaster.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startIdDisaster = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerEndDisaster.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endIdDisaster = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerStartFlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startIdFlood = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTowerEndFlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endIdFlood = results.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.title_back, R.id.iv_expand, R.id.iv_expand2, R.id.iv_expand3, R.id.iv_expand4, R.id.iv_expand5,
            R.id.iv_expand6, R.id.iv_expand7, R.id.iv_expand8, R.id.iv_et1, R.id.iv_et2, R.id.iv_et3,
            R.id.iv_et4, R.id.iv_et5, R.id.iv_et6, R.id.iv_et7, R.id.btn_add, R.id.btn_add_bird, R.id.btn_add_thunder,
            R.id.btn_add_wind, R.id.btn_add_fire, R.id.btn_add_break, R.id.btn_add_disaster, R.id.btn_add_flood, R.id.iv_item_save,
            R.id.tv_bird_orders_time, R.id.tv_bird_arrival_time, R.id.tv_bird_plan_time, R.id.tv_bird_reporting_time, R.id.tv_thunder_time
            , R.id.tv_fire_done_time, R.id.tv_wind_plan_time, R.id.tv_wind_done_time, R.id.tv_break_plan_time, R.id.tv_break_done_time
            , R.id.tv_disaster_plan_time, R.id.tv_disaster_done_time, R.id.tv_flood_done_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_expand:
                if (llContent.getVisibility() == View.VISIBLE) {
                    ivExpand.setImageResource(R.mipmap.next);
                    llContent.setVisibility(View.GONE);
                } else {
                    ivExpand.setImageResource(R.mipmap.btn_down);
                    llContent.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_expand2:
                if (llContent.getVisibility() == View.VISIBLE) {
                    ivExpand2.setImageResource(R.mipmap.next);
                    llBird.setVisibility(View.GONE);
                } else {
                    ivExpand2.setImageResource(R.mipmap.btn_down);
                    llBird.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_expand3:
                if (llThunder.getVisibility() == View.VISIBLE) {
                    ivExpand3.setImageResource(R.mipmap.next);
                    llThunder.setVisibility(View.GONE);
                } else {
                    ivExpand3.setImageResource(R.mipmap.btn_down);
                    llThunder.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_expand4:
                if (llWind.getVisibility() == View.VISIBLE) {
                    ivExpand4.setImageResource(R.mipmap.next);
                    llWind.setVisibility(View.GONE);
                } else {
                    ivExpand4.setImageResource(R.mipmap.btn_down);
                    llWind.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_expand5:
                if (llFire.getVisibility() == View.VISIBLE) {
                    ivExpand5.setImageResource(R.mipmap.next);
                    llFire.setVisibility(View.GONE);
                } else {
                    ivExpand5.setImageResource(R.mipmap.btn_down);
                    llFire.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_expand6:
                if (llBreak.getVisibility() == View.VISIBLE) {
                    ivExpand6.setImageResource(R.mipmap.next);
                    llBreak.setVisibility(View.GONE);
                } else {
                    ivExpand6.setImageResource(R.mipmap.btn_down);
                    llBreak.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_expand7:
                if (llDisaster.getVisibility() == View.VISIBLE) {
                    ivExpand7.setImageResource(R.mipmap.next);
                    llDisaster.setVisibility(View.GONE);
                } else {
                    ivExpand7.setImageResource(R.mipmap.btn_down);
                    llDisaster.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_expand8:
                if (llFlood.getVisibility() == View.VISIBLE) {
                    ivExpand8.setImageResource(R.mipmap.next);
                    llFlood.setVisibility(View.GONE);
                } else {
                    ivExpand8.setImageResource(R.mipmap.btn_down);
                    llFlood.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_et1:
                if (et1.getVisibility() == View.VISIBLE) {
                    et1.setVisibility(View.GONE);
                    cb1.setVisibility(View.VISIBLE);
                } else {
                    et1.setVisibility(View.VISIBLE);
                    cb1.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_et2:
                if (et2.getVisibility() == View.VISIBLE) {
                    et2.setVisibility(View.GONE);
                    cb2.setVisibility(View.VISIBLE);
                } else {
                    et2.setVisibility(View.VISIBLE);
                    cb2.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_et3:
                if (et3.getVisibility() == View.VISIBLE) {
                    et3.setVisibility(View.GONE);
                    cb3.setVisibility(View.VISIBLE);
                } else {
                    et3.setVisibility(View.VISIBLE);
                    cb3.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_et4:
                if (et4.getVisibility() == View.VISIBLE) {
                    et4.setVisibility(View.GONE);
                    cb4.setVisibility(View.VISIBLE);
                } else {
                    et4.setVisibility(View.VISIBLE);
                    cb4.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_et5:
                if (et5.getVisibility() == View.VISIBLE) {
                    et5.setVisibility(View.GONE);
                    cb5.setVisibility(View.VISIBLE);
                } else {
                    et5.setVisibility(View.VISIBLE);
                    cb5.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_et6:
                if (et6.getVisibility() == View.VISIBLE) {
                    et6.setVisibility(View.GONE);
                    cb6.setVisibility(View.VISIBLE);
                } else {
                    et6.setVisibility(View.VISIBLE);
                    cb6.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_et7:
                if (et7.getVisibility() == View.VISIBLE) {
                    et7.setVisibility(View.GONE);
                    cb7.setVisibility(View.VISIBLE);
                } else {
                    et7.setVisibility(View.VISIBLE);
                    cb7.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_add:
                Constant.isContent = true;
                save();
                break;
            case R.id.btn_add_bird:
                saveBird();
                break;
            case R.id.btn_add_thunder:
                saveThunder();
                break;
            case R.id.btn_add_wind:
                saveWind();
                break;
            case R.id.btn_add_fire:
                saveFire();
                break;
            case R.id.btn_add_break:
                saveBreak();
                break;
            case R.id.btn_add_disaster:
                saveDisaster();
                break;
            case R.id.btn_add_flood:
                saveFlood();
                break;
            case R.id.iv_item_save:
                Constant.isContent = true;
                break;
            case R.id.tv_bird_arrival_time:
                PickerUtils.showDate(this, tvBirdArrivalTime);
                break;
            case R.id.tv_bird_orders_time:
                PickerUtils.showDate(this, tvBirdOrdersTime);
                break;
            case R.id.tv_bird_plan_time:
                PickerUtils.showDate(this, tvBirdPlanTime);
                break;
            case R.id.tv_bird_reporting_time:
                PickerUtils.showDate(this, tvBirdReportingTime);
                break;
            case R.id.tv_thunder_time:
                PickerUtils.showDate(this, tvThunderTime);
                break;
            case R.id.tv_fire_done_time:
                PickerUtils.showDate(this, tvFireDoneTime);
                break;
            case R.id.tv_wind_plan_time:
                PickerUtils.showDate(this, tvWindPlanTime);
                break;
            case R.id.tv_wind_done_time:
                PickerUtils.showDate(this, tvWindDoneTime);
                break;
            case R.id.tv_break_plan_time:
                PickerUtils.showDate(this, tvBreakPlanTime);
                break;
            case R.id.tv_break_done_time:
                PickerUtils.showDate(this, tvBreakDoneTime);
                break;
            case R.id.tv_disaster_plan_time:
                PickerUtils.showDate(this, tvDisasterPlanTime);
                break;
            case R.id.tv_disaster_done_time:
                PickerUtils.showDate(this, tvDisasterDoneTime);
                break;
            case R.id.tv_flood_done_time:
                PickerUtils.showDate(this, tvFloodDoneTime);
                break;
        }
    }

    //提交三跨
    private void save() {
        typeName = type.getSelectedItem().toString();
        switch (typeName) {
            case "普通铁路":
                typeId = "3A7703BA2EFA4B9E86732DB1B0FA4E3F";
                break;
            case "高速铁路":
                typeId = "F0C697FDE56E42959F52B6620544240D";
                break;
            case "高速公路":
                typeId = "2F9D2BEF6B08403FAD1FED246B79410B";
                break;
        }
        acrossName = etAcrossName.getText().toString();
        startName = spTowerStart.getSelectedItem().toString();
        endName = spTowerEnd.getSelectedItem().toString();
        towers = startName + "-" + endName;
        smallTowerName = spTowerSmall.getSelectedItem().toString();
        bigTowerName = spTowerBig.getSelectedItem().toString();
        tension = etTension.getText().toString();
        if (et1.getVisibility() == View.VISIBLE) {
            isDrainage = et1.getText().toString();
        } else {
            isDrainage = cb1.isChecked() ? "1" : "0";
        }
        if (et2.getVisibility() == View.VISIBLE) {
            isIndpendent = et2.getText().toString();
        } else {
            isIndpendent = cb2.isChecked() ? "1" : "0";
        }
        if (et3.getVisibility() == View.VISIBLE) {
            isMetal = et3.getText().toString();
        } else {
            isMetal = cb3.isChecked() ? "1" : "0";
        }
        if (et4.getVisibility() == View.VISIBLE) {
            isDouble = et4.getText().toString();
        } else {
            isDouble = cb1.isChecked() ? "1" : "0";
        }
        if (et5.getVisibility() == View.VISIBLE) {
            isVideo = et5.getText().toString();
        } else {
            isVideo = cb5.isChecked() ? "1" : "0";
        }
        if (et6.getVisibility() == View.VISIBLE) {
            isAdss = et6.getText().toString();
        } else {
            isAdss = cb6.isChecked() ? "1" : "0";
        }

        AcrossBean bean = new AcrossBean();
        bean.setType_name(typeName);
        bean.setTask_id(taskId);
        bean.setTowers(towers);
        bean.setTower_id(towerId);
        bean.setType_id(typeId);
        bean.setLine_id(lineId);
        bean.setAcross_name(acrossName);
        bean.setStart_id(startId);
        bean.setStart_name(startName);
        bean.setEnd_id(endId);
        bean.setEnd_name(endName);
        bean.setSmall_tower_id(smallTowerId);
        bean.setSmall_tower_name(smallTowerName);
        bean.setBig_tower_id(bigTowerId);
        bean.setBig_tower_name(bigTowerName);
        bean.setTension(tension);
        bean.setIs_independent(isIndpendent);
        bean.setIs_double(isDouble);
        bean.setIs_drainage(isDrainage);
        bean.setIs_metal(isMetal);
        bean.setIs_video(isVideo);
        bean.setIs_adss(isAdss);
        String json = new Gson().toJson(bean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getService()
                .saveAcross(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {


                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }

                });
    }

    //提交防鸟
    private void saveBird() {
        startNameBird = spTowerStartBird.getSelectedItem().toString();
        endNameBird = spTowerEndBird.getSelectedItem().toString();
        towersBird = startNameBird + "-" + endNameBird;
        if (et7.getVisibility() == View.VISIBLE) {
            installed = et7.getText().toString();
        } else {
            installed = cb7.isChecked() ? "1" : "0";
        }

        BirdBean bean = new BirdBean();
        bean.setType_id("CE0954EF596447CA9458CC230234E01A");
        bean.setTask_id(taskId);
        bean.setTowers(towersBird);
        bean.setTower_id(towerId);
        bean.setLine_id(lineId);
        bean.setStart_id(startIdBird);
        bean.setStart_name(startNameBird);
        bean.setEnd_id(endIdBird);
        bean.setEnd_name(endNameBird);
        bean.setDeal_notes(etBirdDealNotes.getText().toString());
        bean.setTotal(etBirdTotal.getText().toString());
        bean.setYear(etYearBird.getText().toString());
        bean.setCategory(etBirdCategory.getText().toString());
        bean.setOrders_company(etBirdOrdersCompany.getText().toString());
        bean.setArrival_time(tvBirdArrivalTime.getText().toString());
        bean.setOrders_time(tvBirdOrdersTime.getText().toString());
        bean.setPlan_time(tvBirdPlanTime.getText().toString());
        bean.setReporting_time(tvBirdReportingTime.getText().toString());
        bean.setInstalled(installed);
        bean.setBatch(etBirdBatch.getText().toString());

        String json = new Gson().toJson(bean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getService()
                .saveBird(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {


                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }

                });
    }

    //防雷
    public void saveThunder() {
        startNameThunder = spTowerStartThunder.getSelectedItem().toString();
        endNameThunder = spTowerEndThunder.getSelectedItem().toString();
        towersThunder = startNameThunder + "-" + endNameThunder;
        if (et7.getVisibility() == View.VISIBLE) {
            installed = et7.getText().toString();
        } else {
            installed = cb7.isChecked() ? "1" : "0";
        }
        if (cbThunderStatus.isChecked()) {
            statusThunder = "1";
        } else {
            statusThunder = "0";
        }

        ThunderBean bean = new ThunderBean();
        bean.setType_id("0D9A1042B0954BC28827C40F27FF2D06");
        bean.setTask_id(taskId);
        bean.setTowers(towersThunder);
        bean.setTower_id(towerId);
        bean.setLine_id(lineId);
        bean.setStart_id(startIdThunder);
        bean.setStart_name(startNameThunder);
        bean.setEnd_id(endIdThunder);
        bean.setEnd_name(endNameThunder);
        bean.setTotal(Integer.valueOf(etThunderNum.getText().toString()));
        bean.setDeal_notes(etThunderDealNotes.getText().toString());
        bean.setCompany(etThunderCompany.getText().toString());
        bean.setReserve_time(tvThunderTime.getText().toString());
        bean.setStatus(statusThunder);

        String json = new Gson().toJson(bean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getService()
                .saveThunder(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {


                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }

                });
    }

    //防风
    public void saveWind() {
        startNameWind = spTowerStartWind.getSelectedItem().toString();
        endNameWind = spTowerEndWind.getSelectedItem().toString();
        towersWind = startNameWind + "-" + endNameWind;
        if (cbWindStatus.isChecked()) {
            statusWind = "1";
        } else {
            statusWind = "0";
        }

        WindBean bean = new WindBean();
        bean.setType_id("DA73CB1A0C444E719B94F24B4110EE0D");
        bean.setTask_id(taskId);
        bean.setTowers(towersWind);
        bean.setTower_id(towerId);
        bean.setLine_id(lineId);
        bean.setStart_id(startIdWind);
        bean.setStart_name(startNameWind);
        bean.setEnd_id(endIdWind);
        bean.setEnd_name(endNameWind);
        bean.setDeal_notes(etWindDealNotes.getText().toString());
        bean.setStatus(statusWind);
        bean.setPlan_time(tvWindPlanTime.getText().toString());
        bean.setDone_time(tvWindDoneTime.getText().toString());

        String json = new Gson().toJson(bean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getService()
                .saveWind(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {


                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }

                });
    }

    //防火
    public void saveFire() {
        startNameFire = spTowerStartFire.getSelectedItem().toString();
        endNameFire = spTowerEndFire.getSelectedItem().toString();
        towersFire = startNameFire + "-" + endNameFire;
        if (cbFireStatus.isChecked()) {
            statusFire = "1";
        } else {
            statusFire = "0";
        }

        FireBean bean = new FireBean();
        bean.setType_id("CFC9004FE3784E4EBD07A9638A3FF3C9");
        bean.setTask_id(taskId);
        bean.setTowers(towersFire);
        bean.setTower_id(towerId);
        bean.setLine_id(lineId);
        bean.setStart_id(startIdFire);
        bean.setStart_name(startNameFire);
        bean.setEnd_id(endIdFire);
        bean.setEnd_name(endNameFire);
        bean.setReason(etFireReason.getText().toString());
        bean.setDeal_notes(etFireDealNotes.getText().toString());
        bean.setDone_time(tvFireDoneTime.getText().toString());
        bean.setStatus(statusFire);

        String json = new Gson().toJson(bean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getService()
                .saveFire(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {


                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }

                });
    }

    //外破
    public void saveBreak() {
        startNameBreak = spTowerStartBreak.getSelectedItem().toString();
        endNameBreak = spTowerEndBreak.getSelectedItem().toString();
        towersBreak = startNameBreak + "-" + endNameBreak;
        if (cbBreakIsVideo.isChecked()) {
            isBreakVideo = "1";
        } else {
            isBreakVideo = "0";
        }
        if (cbBreakStatus.isChecked()) {
            statusBreak = "1";
        } else {
            statusBreak = "0";
        }

        BreakBean bean = new BreakBean();
        bean.setType_id("D71CFDD7A45B42688BE85747B8F25A86");
        bean.setTask_id(taskId);
        bean.setTowers(towersBreak);
        bean.setTower_id(towerId);
        bean.setLine_id(lineId);
        bean.setStart_id(startIdBreak);
        bean.setStart_name(startNameBreak);
        bean.setEnd_id(endIdBreak);
        bean.setEnd_name(endNameBreak);
        bean.setStatus(statusBreak);
        bean.setContent(etBreakContent.getText().toString());
        bean.setTrouble_wares(etBreakTroubleWares.getText().toString());
        bean.setDuty_unit(etBreakDutyUnit.getText().toString());
        bean.setContact(etBreakContact.getText().toString());
        bean.setPhone(etBreakPhone.getText().toString());
        bean.setDeal_notes_first(etBreakDealNotesFirst.getText().toString());
        bean.setDeal_notes_second(etBreakDealNotesSecond.getText().toString());
        bean.setDeal_notes_third(etBreakDealNotesThird.getText().toString());
        bean.setDeal_notes_fourth(etBreakDealNotesFourth.getText().toString());
        bean.setDeal_notes_fifth(etBreakDealNotesFifth.getText().toString());
        bean.setIs_video(isBreakVideo);
        bean.setPlan_time(tvBreakPlanTime.getText().toString());
        bean.setDone_time(tvBreakDoneTime.getText().toString());
        bean.setCompany(etBreakCompany.getText().toString());
        bean.setStatus(statusBreak);

        String json = new Gson().toJson(bean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getService()
                .saveBreak(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {


                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }

                });
    }

    //地灾
    public void saveDisaster() {
        startNameDisaster = spTowerStartDisaster.getSelectedItem().toString();
        endNameDisaster = spTowerEndDisaster.getSelectedItem().toString();
        towersDisaster = startNameDisaster + "-" + endNameDisaster;
        if (et7.getVisibility() == View.VISIBLE) {
            installed = et7.getText().toString();
        } else {
            installed = cb7.isChecked() ? "1" : "0";
        }
        if (cbDisasterStatus.isChecked()) {
            statusDisaster = "1";
        } else {
            statusDisaster = "0";
        }

        DisasterBean bean = new DisasterBean();
        bean.setType_id("1DCEECE9026343C0A16F918BFB74FF81");
        bean.setTask_id(taskId);
        bean.setTowers(towersDisaster);
        bean.setTower_id(towerId);
        bean.setLine_id(lineId);
        bean.setStart_id(startIdDisaster);
        bean.setStart_name(startNameDisaster);
        bean.setEnd_id(endIdDisaster);
        bean.setEnd_name(endNameDisaster);
        bean.setDisaster_type(etDisasterDisasterType.getText().toString());
        bean.setJoin_reason(etDisasterJoinReason.getText().toString());
        bean.setMain_reason(etDisasterMainReason.getText().toString());
        bean.setScale(etDisasterScale.getText().toString());
        bean.setDeal_notes(etDisasterDealNotes.getText().toString());
        bean.setFinal_deal(etDisasterFinalDeal.getText().toString());
        bean.setPlan_time(tvDisasterPlanTime.getText().toString());
        bean.setDone_time(tvDisasterDoneTime.getText().toString());
        bean.setStatus(statusDisaster);

        String json = new Gson().toJson(bean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getService()
                .saveDisaster(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {


                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }

                });
    }

    //防洪防汛
    public void saveFlood() {
        startNameFlood = spTowerStartFlood.getSelectedItem().toString();
        endNameFlood = spTowerEndFlood.getSelectedItem().toString();
        towersFlood = startNameFlood + "-" + endNameFlood;
        if (cbFloodIsDeal.isChecked()) {
            isFloodDeal = "1";
        } else {
            isFloodDeal = "0";
        }
        if (cbFloodIsRepair.isChecked()) {
            isFloodRepair = "1";
        } else {
            isFloodRepair = "0";
        }
        if (cbFloodStatus.isChecked()) {
            statusFlood = "1";
        } else {
            statusFlood = "0";
        }

        FloodBean bean = new FloodBean();
        bean.setType_id("23F5DB1BEC7F41EC893B4BC52F961F88");
        bean.setTask_id(taskId);
        bean.setTowers(towersFlood);
        bean.setTower_id(towerId);
        bean.setLine_id(lineId);
        bean.setStart_id(startIdFlood);
        bean.setStart_name(startNameFlood);
        bean.setEnd_id(endIdFlood);
        bean.setEnd_name(endNameFlood);
        bean.setContent(etFloodContent.getText().toString());
        bean.setDeal_notes(etFloodDealNotes.getText().toString());
        bean.setDeal_type(etFloodDealType.getText().toString());
        bean.setEarth_work(etFloodEarthWork.getText().toString());
        bean.setDone_time(tvFloodDoneTime.getText().toString());
        bean.setIs_deal(isFloodDeal);
        bean.setIs_repair(isFloodRepair);
        bean.setStatus(statusFlood);

        String json = new Gson().toJson(bean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getService()
                .saveFlood(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {


                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }

                });
    }
}
