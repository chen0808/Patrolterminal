package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectPicAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.patrol.terminal.bean.PatrolRecordPicBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DefectIngDetailActivity extends BaseActivity {


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
    @BindView(R.id.defect_content)
    TextView defectContent;
    @BindView(R.id.defect_line_name)
    TextView defectLineName;
    @BindView(R.id.defect_tower_name)
    TextView defectTowerName;
    @BindView(R.id.defect_find_name)
    TextView defectFindName;
    @BindView(R.id.defect_allote_status)
    TextView defectAlloteStatus;
    @BindView(R.id.defect_deal_name)
    TextView defectDealName;
    @BindView(R.id.defect_dep_name)
    TextView defectDepName;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.defect_gridView)
    GridView defectGridView;

    private DefectPicAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_defect_ing);
        ButterKnife.bind(this);

        initview();
    }

    private void initview() {
        DefectFragmentBean bean = getIntent().getParcelableExtra("bean");
        titleName.setText("缺陷详情");
        defectContent.setText("内容：" + bean.getContent());
//        defectDepName.setText("工作班组：" + bean.getDeal_dep_name());
        getPartrolRecord(bean.getId());
        mGridViewAddImgAdapter = new DefectPicAdapter(this, mPicList);
        defectGridView.setAdapter(mGridViewAddImgAdapter);
        defectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                    viewPluImg(position);

            }
        });
    }

    @OnClick({R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    //查询接电电阻
    public void getPartrolRecord(String id) {

        ProgressDialog.show(this, false, "正在加载。。。。");
        BaseRequest.getInstance().getService()
                .getDefectPic(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolRecordPicBean>>(this) {


                    @Override
                    protected void onSuccees(BaseResult<List<PatrolRecordPicBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<PatrolRecordPicBean> results = t.getResults();
                            for (int i = 0; i < results.size(); i++) {
                                PatrolRecordPicBean overhaulFileBean = results.get(i);
                                String file_path = overhaulFileBean.getFile_path();
                                if (overhaulFileBean.getFilename() != null) {
                                    String compressPath = BaseUrl.BASE_URL + file_path.substring(1, file_path.length()) + overhaulFileBean.getFilename();

                                    mPicList.add(compressPath);
                                }
                            }
                            mGridViewAddImgAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                });
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constant.IMG_LIST, mPicList);
        intent.putExtra(Constant.POSITION, position);
        startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
    }
}
