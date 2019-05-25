package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.SpecialAttrAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.bean.PatrolLevel3;
import com.patrol.terminal.bean.PatrolLevel4;
import com.patrol.terminal.bean.SpecialAttrBean;
import com.patrol.terminal.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SpecialAttrActivity extends BaseActivity {
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
    @BindView(R.id.rv_special_attr)
    RecyclerView rvSpecialAttr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_attr);
        ButterKnife.bind(this);
        titleName.setText("隐患列表");
        String tower_id = (String) SPUtil.get(this, "ids", "tower_id", "");
        BaseRequest.getInstance().getService().getSpecialAttr(tower_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<SpecialAttrBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<SpecialAttrBean>> t) throws Exception {
                        List<SpecialAttrBean> results = t.getResults();
                        rvSpecialAttr.setLayoutManager(new LinearLayoutManager(SpecialAttrActivity.this));
                        SpecialAttrAdapter adapter = new SpecialAttrAdapter(getData(results));
                        rvSpecialAttr.setAdapter(adapter);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    private List<MultiItemEntity> getData(List<SpecialAttrBean> results) {
        List<MultiItemEntity> list = new ArrayList<>();
        List<SpecialAttrBean> listLevel1 = new ArrayList<>();
        List<SpecialAttrBean> listLevel2 = new ArrayList<>();
        List<SpecialAttrBean> listLevel3 = new ArrayList<>();
        List<SpecialAttrBean> listLevel4 = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getLevel_no() == 1) {
                listLevel1.add(results.get(i));
            } else if (results.get(i).getLevel_no() == 2) {
                listLevel2.add(results.get(i));
            } else if (results.get(i).getLevel_no() == 3) {
                listLevel3.add(results.get(i));
            } else if (results.get(i).getLevel_no() == 4) {
                listLevel4.add(results.get(i));
            }
        }
        for (int i = 0; i < listLevel1.size(); i++) {
            PatrolLevel1 level1 = new PatrolLevel1(listLevel1.get(i).getName());
            for (int j = 0; j < listLevel2.size(); j++) {
                if (listLevel1.get(i).getId().equals(listLevel2.get(j).getP_id())) {
                    PatrolLevel2 level2 = new PatrolLevel2("", false, "", listLevel2.get(j).getName(), listLevel2.get(j).getId());
                    level1.addSubItem(j, level2);
                    for (int k = 0; k < listLevel3.size(); k++) {
                        if (listLevel2.get(j).getId().equals(listLevel3.get(k).getP_id())) {
                            PatrolLevel3 level3 = new PatrolLevel3(listLevel3.get(k).getName(), listLevel3.get(k).getId());
                            level2.addSubItem(k, level3);
                            for (int l = 0; l < listLevel4.size(); l++) {
                                if (listLevel3.get(k).getId().equals(listLevel4.get(l).getP_id())) {
                                    PatrolLevel4 level4 = new PatrolLevel4(listLevel4.get(l).getName(), listLevel4.get(l).getId());
                                    level3.addSubItem(l, level4);
                                }
                            }
                        }
                    }
                }
            }
            list.add(level1);
        }
        return list;
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
