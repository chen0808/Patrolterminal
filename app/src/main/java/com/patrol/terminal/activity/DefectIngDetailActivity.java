package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectPicAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.DefectFragmentDetailBean;
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

/**
 * 缺陷详情
 */
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

    @BindView(R.id.defect_line_name)
    TextView defect_line_name;
    @BindView(R.id.defect_ganta)
    TextView defect_ganta;
    @BindView(R.id.defect_content)
    TextView defect_content;
    @BindView(R.id.defect_qxrkzt)
    TextView defect_qxrkzt;
    @BindView(R.id.defect_xsnr)
    TextView defect_xsnr;
    @BindView(R.id.defect_qxlb)
    TextView defect_qxlb;
    @BindView(R.id.defect_qxjb)
    TextView defect_qxjb;
    @BindView(R.id.defect_fxsj)
    TextView defect_fxsj;
    @BindView(R.id.defect_clcs)
    TextView defect_clcs;
    @BindView(R.id.defect_xqbz)
    TextView defect_xqbz;
    @BindView(R.id.defect_xqsj)
    TextView defect_xqsj;
    @BindView(R.id.defect_clr)
    TextView defect_clr;
    @BindView(R.id.defect_fxr)
    TextView defect_fxr;
    @BindView(R.id.defect_fxrbm)
    TextView defect_fxrbm;
    //    @BindView(R.id.defect_wcqq)
//    TextView defect_wcqq;
//    @BindView(R.id.defect_ggbz)
//    TextView defect_ggbz;
    @BindView(R.id.defect_wczt)
    TextView defect_wczt;


    @BindView(R.id.deffect_img)
    TextView deffectImg;
    @BindView(R.id.defect_gridView)
    GridView defectGridView;
    private DefectPicAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getIntent().getStringExtra("id");
        setContentView(R.layout.activity_defect_ing);
        ButterKnife.bind(this);
        initview(id);
    }

    private void initview(String id) {
        titleName.setText("缺陷详情");
        ProgressDialog.show(this, false, "正在加载中...");
        BaseRequest.getInstance().getService().getDefectDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DefectFragmentDetailBean>() {
                    @Override
                    protected void onSuccees(BaseResult<DefectFragmentDetailBean> t) throws Exception {
                        DefectFragmentDetailBean bean = t.getResults();
                        defect_line_name.setText(bean.getLine_name());
                        defect_ganta.setText(bean.getTower_name());
                        defect_content.setText(bean.getContent());
                        defect_xsnr.setText(bean.getPatrol_name());
                        defect_qxlb.setText(bean.getCategory_name());

                        String grade_name = bean.getGrade_name();
                        if (grade_name.equals("一般")) {
                            defect_qxjb.setTextColor(getResources().getColor(R.color.blue));
                        } else if (grade_name.equals("严重")) {
                            defect_qxjb.setTextColor(getResources().getColor(R.color.line_point_1));
                        } else if (grade_name.equals("危急")) {
                            defect_qxjb.setTextColor(getResources().getColor(R.color.line_point_0));
                        }
                        defect_qxjb.setText(bean.getGrade_name());

                        defect_fxsj.setText(bean.getFind_time());
                        defect_clcs.setText(bean.getDeal_notes());
                        defect_xqbz.setText(bean.getDeal_dep_name());
                        defect_xqsj.setText(bean.getDeal_time());

                        switch (bean.getIn_status()) {
                            case "0":
                                defect_qxrkzt.setText("编制");
                                defect_qxrkzt.setTextColor(getResources().getColor(R.color.blue));
                                break;
                            case "1":
                                defect_qxrkzt.setText("待班长审核");
                                defect_qxrkzt.setTextColor(getResources().getColor(R.color.line_point_1));
                                break;
                            case "2":
                                defect_qxrkzt.setText("待专责审核");
                                defect_qxrkzt.setTextColor(getResources().getColor(R.color.line_point_0));
                                break;
                            case "3":
                                defect_qxrkzt.setText("复核中");
                                defect_qxrkzt.setTextColor(getResources().getColor(R.color.line_point_3));
                                break;
                            case "4":
                                defect_qxrkzt.setText("审核通过");
                                defect_qxrkzt.setTextColor(getResources().getColor(R.color.green));
                                break;
                            case "5":
                                defect_qxrkzt.setText("审核不通过");
                                defect_qxrkzt.setTextColor(getResources().getColor(R.color.green));
                                break;
                        }

                        defect_clr.setText(bean.getDeal_user_name());
                        defect_fxr.setText(bean.getFind_user_name());
                        defect_fxrbm.setText(bean.getFind_dep_name());

                        switch (bean.getDone_status()) {
                            case "0":
                                defect_wczt.setText("待处理");
                                break;
                            case "1":
                                defect_wczt.setText("月计划分配");
                                break;
                            case "2":
                                defect_wczt.setText("周计划分配");
                                break;
                            case "3":
                                defect_wczt.setText("日计划分配");
                                break;
                            case "4":
                                defect_wczt.setText("进行中");
                                break;
                            case "5":
                                defect_wczt.setText("已完成");
                                break;
                            case "6":
                                defect_wczt.setText("未完成");
                                break;
                        }
//                        defect_ggbz.setText(bean.getRemark());

//                        getPartrolRecord(bean.getId());
                        if (bean.getFileList() != null && bean.getFileList().size() > 0) {
                            deffectImg.setVisibility(View.VISIBLE);
                            mPicList.clear();
                            for (int i = 0; i < bean.getFileList().size(); i++) {
                                String path = BaseUrl.BASE_URL + bean.getFileList().get(i).getFile_path() + bean.getFileList().get(i).getFilename();
                                Logger.d(path);
                                mPicList.add(path);
                            }
                            mGridViewAddImgAdapter = new DefectPicAdapter(DefectIngDetailActivity.this, mPicList);
                            defectGridView.setAdapter(mGridViewAddImgAdapter);
                            defectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                                    viewPluImg(position);
                                }
                            });
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
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
                            if (results.size() > 0) {
                                deffectImg.setVisibility(View.VISIBLE);
                                for (int i = 0; i < results.size(); i++) {
                                    PatrolRecordPicBean overhaulFileBean = results.get(i);
                                    String file_path = overhaulFileBean.getFile_path();
                                    if (overhaulFileBean.getFilename() != null) {
                                        String compressPath = BaseUrl.BASE_URL + file_path.substring(1, file_path.length()) + overhaulFileBean.getFilename();

                                        mPicList.add(compressPath);
                                    }
                                }
                            } else {
                                deffectImg.setVisibility(View.GONE);
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
