package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.PlusImageActivity;
import com.patrol.terminal.adapter.TssxPhotoAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.NoticeBean;
import com.patrol.terminal.bean.UserBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

//发布电子公告
public class ElectronicNoticeAddActivity extends BaseActivity {

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
    @BindView(R.id.edit_title)
    TextView editTitle;
    @BindView(R.id.tv_announce_user)
    TextView tvAnnounceUser;
    @BindView(R.id.edit_content)
    TextView editContent;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.defect_gridView)
    GridView defectGridView;

    private ArrayList<String> photoList = new ArrayList<>();
    private TssxPhotoAdapter photoAdapter;
    private int position;//点击图片项
    private Map<String, RequestBody> params = new HashMap<>();
    private NoticeBean noticeBean;
    private String mSelectPersonId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_notice_add);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        noticeBean = (NoticeBean)intent.getSerializableExtra("NoticeBean");

        titleName.setText("电子公告");

        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("发布");

        photoAdapter = new TssxPhotoAdapter(this, photoList);
        defectGridView.setAdapter(photoAdapter);
        defectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                if(noticeBean != null){
                    viewPluImg(position);
                } else {
                    startCamera();
                }
            }
        });

        if(noticeBean != null){
            editTitle.setText(noticeBean.getTitle());
            tvAnnounceUser.setText(noticeBean.getAnnounce_user());
            editContent.setText(noticeBean.getContent());
            tvEndTime.setText(noticeBean.getEnd_time());

            Constant.isEditStatus = true;
            if (noticeBean.getTempImgList() != null && noticeBean.getTempImgList().size() > 0) {
                photoList.clear();
                for (int i = 0; i < noticeBean.getTempImgList().size(); i++) {
                    String path = BaseUrl.BASE_URL + noticeBean.getTempImgList().get(i).getFile_path() + noticeBean.getTempImgList().get(i).getFilename();
                    photoList.add(path);
                }
                photoAdapter.setAddStatus(false);
                photoAdapter.notifyDataSetChanged();
            } else {
                defectGridView.setVisibility(View.GONE);
            }

            editTitle.setEnabled(false);
            tvAnnounceUser.setEnabled(false);
            editContent.setEnabled(false);
            tvEndTime.setEnabled(false);

            editTitle.setHint("");
            tvAnnounceUser.setHint("");
            editContent.setHint("");
            tvEndTime.setHint("");

            tvAnnounceUser.setCompoundDrawables(null, null, null, null);
            tvEndTime.setCompoundDrawables(null, null, null, null);

            titleSetting.setVisibility(View.GONE);
        } else {
            Constant.isEditStatus = false;
            String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
            tvEndTime.setText(time);
        }
    }

    public void noticeSavePOST() {
        ProgressDialog.show(this, false, "正在加载。。。。");
        params.clear();
        params.put("title", toRequestBody(editTitle.getText().toString()));
        params.put("announce_user", toRequestBody(tvAnnounceUser.getText().toString()));
        params.put("content", toRequestBody(editContent.getText().toString()));
        params.put("end_time", toRequestBody(tvEndTime.getText().toString()));
        for(int i=0;i<photoList.size();i++){
            if(!photoList.get(i).equals("")){
                File file = new File(photoList.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                params.put("files\"; filename=\"" + i + ".jpg", requestFile);
            }
        }

        BaseRequest.getInstance().getService()
                .noticeSavePOST(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {

                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if(t.getCode() == 1){
                            Utils.showToast("提交成功");
                            setResult(RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }

                });
    }

    public RequestBody toRequestBody(String value) {
        if (value != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
            return requestBody;
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            return requestBody;
        }
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.tv_announce_user, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.tv_end_time:
                showDay();
                break;
            case R.id.tv_announce_user:
                Intent intent = new Intent();
                intent.setClass(this, CheckPersonSearchActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.title_setting:
                if(TextUtils.isEmpty(editTitle.getText().toString())){
                    Utils.showToast("请输入标题");
                    break;
                }

                if(TextUtils.isEmpty(tvAnnounceUser.getText().toString())){
                    Utils.showToast("请选择发布人");
                    break;
                }

                if(TextUtils.isEmpty(editContent.getText().toString())){
                    Utils.showToast("请输入内容");
                    break;
                }

                if(TextUtils.isEmpty(tvEndTime.getText().toString())){
                    Utils.showToast("请选择失效日期");
                    break;
                }

                if(photoList.size() == 0){
                    Utils.showToast("请拍摄一张照片");
                    break;
                }

                noticeSavePOST();
                break;
        }
    }

    public void showDay() {
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        int curMonth = Integer.parseInt(months[0]);
        int curYear = Integer.parseInt(years[0]);
        int curDay = Integer.parseInt(days[0]);
        if(curMonth == 1){
            curMonth = 12;
            curYear = curYear - 1;
        } else {
            curMonth = curMonth - 1;
        }

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(curYear, curMonth, curDay);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);

        //时间选择器 ，自定义布局
        //选中事件回调
        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = DateUatil.getDay(date);
                tvEndTime.setText(time);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    //打开相机
    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constant.DEFECT_REQUEST_CODE);
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constant.IMG_LIST, photoList);
        intent.putExtra("isDelPic", "0");
        intent.putExtra(Constant.POSITION, position);
        startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.DEFECT_REQUEST_CODE:
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    String path = Environment.getExternalStorageDirectory().getPath()
                            + "/MyPhoto/" + DateUatil.getDateStr() + "_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                    try {
                        //保存本地成功 刷新刷新数据添加到页面
                        FileUtil.saveFile(bitmap, path);
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
                case 1001:
                    if (data != null) {
                        UserBean clickedUserBean = data.getParcelableExtra("search_user_item");
                        if (clickedUserBean != null) {
                            mSelectPersonId = clickedUserBean.getId();
                            String name = clickedUserBean.getName();
                            tvAnnounceUser.setText(name);
                        }
                    }
                    break;
            }
        }
    }
}
