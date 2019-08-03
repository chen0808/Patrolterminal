package com.patrol.terminal.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.PicterAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.FangLeiTodoBean;
import com.patrol.terminal.bean.InAuditTroubleReqBean;
import com.patrol.terminal.bean.LocalTroubleTypeBean;
import com.patrol.terminal.network.ApiServise;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.DangerSubmitView;
import com.patrol.terminal.widget.PinchImageView;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 待办管理
 */
public class DangerVerifyActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

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
    @BindView(R.id.danger_type)
    DangerSubmitView dangerType;
    @BindView(R.id.danger_find_dep)
    DangerSubmitView dangerFindDep;
    @BindView(R.id.danger_line_name)
    DangerSubmitView dangerLineName;
    @BindView(R.id.danger_line_level)
    DangerSubmitView dangerLineLevel;
    @BindView(R.id.danger_tower_name)
    DangerSubmitView dangerTowerName;
    @BindView(R.id.danger_find_time)
    DangerSubmitView dangerFindTime;
    @BindView(R.id.danger_more_ll)
    LinearLayout dangerMoreLl;
    @BindView(R.id.danger_special_content)
    DangerSubmitView dangerSpecialContent;
    @BindView(R.id.danger_special_info)
    LinearLayout dangerSpecialInfo;
    @BindView(R.id.danger_submit_no)
    TextView dangerSubmitNo;
    @BindView(R.id.danger_submit_yes)
    TextView dangerSubmitYes;
    @BindView(R.id.danger_pic)
    RecyclerView dangerPic;
    @BindView(R.id.danger_special_pic)
    RecyclerView dangerSpecialPic;
    @BindView(R.id.danger_special_pic_tv)
    TextView yh_pic_tv;

    @BindView(R.id.btn_ll)
    LinearLayout btnLl;
    @BindView(R.id.danger_level)
    DangerSubmitView dangerDangerLevel;
    private String f_id;
    private String id;
    private String find_dep_id;
    private String line_name;
    private String tower_name;
    private String audit_status;
    private PicterAdapter adapter1;
    private PicterAdapter adapter2;
    private List<FangLeiTodoBean.TroubleFileListBean> eqTowerWaresImgList = new ArrayList<>();
    private List<FangLeiTodoBean.TroubleFileListBean> troubleFileList = new ArrayList<>();
    private String type_name;
    private String dangerLevel;
    private String find_dep_name;
    private String type_id;
    private String mJobType;
//    private String flow_sign;//隐患类别  23、三跨，24、防鸟，25、防雷，26、防风，27、防山火，28、防外破，29、地灾

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_verify);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        ProgressDialog.show(this, true, "正在加载。。。。");
        mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        titleName.setText("隐患审核");

        dangerPic.setLayoutManager(new GridLayoutManager(this, 5));
        dangerSpecialPic.setLayoutManager(new GridLayoutManager(this, 5));
        adapter1 = new PicterAdapter(R.layout.item_pic, troubleFileList);
        adapter2 = new PicterAdapter(R.layout.item_pic, eqTowerWaresImgList);
        dangerPic.setAdapter(adapter1);
        dangerSpecialPic.setAdapter(adapter2);

        adapter1.setOnItemClickListener(this);
        adapter2.setOnItemClickListener(this);

        String flow_sign = getIntent().getStringExtra("flow_sign");
        String data_id = getIntent().getStringExtra("task_id");
        audit_status = getIntent().getStringExtra("audit_status");

        switch (audit_status) {
            case "1"://待班长
                if (!mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {//班长
                    btnLl.setVisibility(View.GONE);
                }
                break;
            case "2"://待专责 审核
                if (!mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {//专责
                    btnLl.setVisibility(View.GONE);
                }
                dangerSubmitYes.setText("转巡视");
                break;
            case "3": //通过
            case "4"://不通过
                btnLl.setVisibility(View.GONE);
                break;
        }
        getTodoDetail(flow_sign, data_id);

    }


    //获取防雷待办详情 23、三跨，24、防鸟，25、防雷，26、防风，27、防山火，28、防外破，29、地灾
    public void getTodoDetail(String flow_sign, String data_id) {
        ApiServise apiServise = BaseRequest.getInstance().getService();
        Observable<BaseResult<FangLeiTodoBean>> observable = null;

        if (flow_sign.equals(Constant.FLOW_SIGN_FL))
            observable = apiServise.getFangLeiTodo(data_id);
        else if (flow_sign.equals(Constant.FLOW_SIGN_FF))
            observable = apiServise.getFangFengTodo(data_id);
        else if (flow_sign.equals(Constant.FLOW_SIGN_FSH))
            observable = apiServise.getShanhuoTodo(data_id);
        else if (flow_sign.equals(Constant.FLOW_SIGN_SK))
            observable = apiServise.getSanKuaTodo(data_id);
        else if (flow_sign.equals(Constant.FLOW_SIGN_FN))
            observable = apiServise.getFangNiaoHaiTodo(data_id);
        else if (flow_sign.equals(Constant.FLOW_SIGN_FWP))
            observable = apiServise.getFangWaiPoTodo(data_id);
        else if (flow_sign.equals(Constant.FLOW_SIGN_DZ))
            observable = apiServise.getDiZaiTodo(data_id);


        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FangLeiTodoBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<FangLeiTodoBean> t) throws Exception {
                        FangLeiTodoBean results = t.getResults();
                        f_id = results.getId();
                        id = results.getTask_trouble_id();
                        find_dep_id = results.getFind_dep_id();
                        find_dep_name = results.getFind_dep_name();
                        line_name = results.getLine_name();
                        tower_name = results.getTower_name();
                        type_name = results.getType_name();
                        type_id = results.getType_id();
                        dangerLevel = StringUtil.getDangerLevel(results.getGrade_sign());
                        troubleFileList = results.getTroubleFileList();
                        dangerDangerLevel.setContent(dangerLevel);
                        dangerType.setContent(results.getType_name());
                        dangerFindDep.setContent(results.getFind_dep_name());
                        dangerLineLevel.setContent(results.getLine_level());
                        dangerLineName.setContent(results.getLine_name());
                        dangerTowerName.setContent(results.getTower_name());
                        dangerFindTime.setContent(results.getFind_time());
                        adapter1.setNewData(troubleFileList);

                        if (!TextUtils.isEmpty(results.getWares_name()) && !results.getWares_name().equals(Constant.YH_BUGUANLIAN)) {//特殊属性
                            dangerSpecialInfo.setVisibility(View.VISIBLE);
                            dangerSpecialContent.setContent(results.getWares_name());

                            eqTowerWaresImgList = results.getEqTowerWaresImgList();
                            if (eqTowerWaresImgList == null || eqTowerWaresImgList.size() == 0) {
                                dangerSpecialPic.setVisibility(View.GONE);
                                yh_pic_tv.setText("  照片：无");
                            } else {
                                adapter2.setNewData(eqTowerWaresImgList);
                            }

                        }

                        detailView(results);
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    public void detailView(FangLeiTodoBean results) {
        List<View> list = new ArrayList<>();

        switch (type_id) {
            case LocalTroubleTypeBean.TROUBLE_SK_INT:
            case LocalTroubleTypeBean.TROUBLE_FN_INT:
            case LocalTroubleTypeBean.TROUBLE_FWP_INT:
            case LocalTroubleTypeBean.TROUBLE_DZ_INT:
            case LocalTroubleTypeBean.TROUBLE_FSH_INT:
            case LocalTroubleTypeBean.TROUBLE_FF_INT://防雷防风一样
            case LocalTroubleTypeBean.TROUBLE_FL_INT:
                DangerSubmitView view1 = new DangerSubmitView(this, "审核状态：", StringUtil.getDefectState(results.getIn_status()));
                DangerSubmitView view2 = new DangerSubmitView(this, "处理时间：", results.getDeal_time() == null ? "无" : results.getDeal_time());
                DangerSubmitView view3 = new DangerSubmitView(this, "厂家：", results.getCompany() == null ? "暂无" : results.getCompany());
                DangerSubmitView view4 = new DangerSubmitView(this, "处理措施：", results.getAdvice_deal_notes() == null ? "暂无" : results.getAdvice_deal_notes());
                DangerSubmitView view5 = new DangerSubmitView(this, "备注：", results.getRemark() == null ? "无" : results.getRemark());
                list.add(view1);
                list.add(view2);
                list.add(view3);
                list.add(view4);
                list.add(view5);
                break;
        }


        for (int i = 0; i < list.size(); i++) {
            dangerMoreLl.addView(list.get(i));
        }
    }


    @OnClick({R.id.title_back, R.id.danger_submit_no, R.id.danger_submit_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.danger_submit_no://驳回
                inAuditTrouble("4");
                break;
            case R.id.danger_submit_yes://通过
                if ("1".equals(audit_status)) {
                    inAuditTrouble("2");
                } else if ("2".equals(audit_status)) {
                    inAuditTrouble("3");
                }
                break;
        }
    }

    public void inAuditTrouble(String status) {
        ProgressDialog.show(this, true, "正在加载。。。。");
        InAuditTroubleReqBean bean = new InAuditTroubleReqBean();
        bean.setIn_status(status);
        bean.setF_id(f_id);
        bean.setId(id);
        bean.setFrom_user_id(SPUtil.getUserId(this));
        bean.setType_id(type_id);
        bean.setFind_dep_id(find_dep_id);
        bean.setLine_name(line_name);
        bean.setTower_name(tower_name);
        BaseRequest.getInstance().getService()
                .inAuditTrouble(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(DangerVerifyActivity.this, "处理成功", Toast.LENGTH_SHORT).show();
                        if ("3".equals(status)) {//转巡视
                            Intent intent = new Intent(DangerVerifyActivity.this, DangerToPatrolActivity.class);
                            intent.putExtra("danger_type", type_name);
                            intent.putExtra("danger_level", dangerLevel);
                            intent.putExtra("line_name", line_name);
                            intent.putExtra("dep_name", find_dep_name);
                            intent.putExtra("tower_name", tower_name);
                            intent.putExtra("find_dep_id", find_dep_id);
                            intent.putExtra("f_id", f_id);
                            intent.putExtra("id", id);
                            intent.putExtra("type_id", type_id);
                            startActivity(intent);
                        }
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<FangLeiTodoBean.TroubleFileListBean> troubleFileList = adapter.getData();
        FangLeiTodoBean.TroubleFileListBean item = troubleFileList.get(position);
        showBigImage(BaseUrl.BASE_URL + item.getFile_path() + item.getFilename());
    }

    //查看大图
    private void showBigImage(String url) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_big_image);
        PinchImageView iv = dialog.findViewById(R.id.iv);
        Glide.with(this).load(url).into(iv);
        dialog.show();
    }


}
