package com.patrol.terminal.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.LocalPatrolDefectBean;
import com.patrol.terminal.sqlite.DefactContentDBHelper;
import com.patrol.terminal.sqlite.MyOpenhelper;
import com.patrol.terminal.utils.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DefectTabAdapter extends BaseQuickAdapter<LocalPatrolDefectBean, BaseViewHolder> {
    private final Fragment fragment;
    private GridViewAdapter3 mGridViewAddImgAdapter;
    private int tab;

    public DefectTabAdapter(Fragment fragment, int layoutResId, @Nullable List<LocalPatrolDefectBean> data, int tab) {
        super(layoutResId, data);
        this.fragment = fragment;
        this.tab = tab;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalPatrolDefectBean item) {

        GridView gridView = helper.getView(R.id.gridView);
        helper.setText(R.id.tv_content, item.getPatrol_name());
        RelativeLayout rlTitle = helper.getView(R.id.rl_title);
        RadioGroup rgTitle = helper.getView(R.id.rg_title);
        RadioButton defectTrue = (RadioButton) rgTitle.getChildAt(0);
        RadioButton defectFalse = (RadioButton) rgTitle.getChildAt(1);
        LinearLayout llCOntent = helper.getView(R.id.ll_content);
        AutoCompleteTextView tvDiverWay = helper.getView(R.id.tv_diver_way);
        tvDiverWay.setText(item.getContent());
        tvDiverWay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setContent(s.toString());
                item.update();
            }
        });

        RadioGroup rgContentType = helper.getView(R.id.rg_content_type);
        RadioButton checkOneRb = (RadioButton) rgContentType.getChildAt(0);
        RadioButton checkTwoRb = (RadioButton) rgContentType.getChildAt(1);
        RadioButton checkThreeRb = (RadioButton) rgContentType.getChildAt(2);

        if (item.getGrade_id() != null) {
            switch (item.getGrade_id()) {
                case "10C639F13341484997EE8D955322BE02"://危急
                    checkOneRb.setChecked(true);
                    checkTwoRb.setChecked(false);
                    checkThreeRb.setChecked(false);
                    break;
                case "2CEB42DA67764AC0BF911B02FB579775"://严重
                    checkOneRb.setChecked(false);
                    checkTwoRb.setChecked(true);
                    checkThreeRb.setChecked(false);
                    break;
                case "37E5647975394B1E952DC5D2796C7D73"://一般
                    checkOneRb.setChecked(false);
                    checkTwoRb.setChecked(false);
                    checkThreeRb.setChecked(true);
                    break;
            }
        }

        if (item.getStatus() != null) {
            if (item.getStatus().equals("0")) {
                defectTrue.setChecked(true);
                llCOntent.setVisibility(View.GONE);
            } else if (item.getStatus().equals("1")) {
                defectFalse.setChecked(true);
                llCOntent.setVisibility(View.VISIBLE);
            }
        }
        defectTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getStatus().equals("0") || item.getStatus() == null) {
                    defectTrue.setChecked(true);
                    item.setStatus("1");
                } else if (item.getStatus().equals("1")) {
                    defectTrue.setChecked(false);
                    item.setStatus("0");
                }
                item.update();
            }
        });
        defectFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getStatus().equals("1") || item.getStatus() == null) {
                    defectFalse.setChecked(true);
                    item.setStatus("0");
                } else if (item.getStatus().equals("0")) {
                    defectFalse.setChecked(false);
                    item.setStatus("1");
                }
                item.update();
            }
        });
        rlTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llCOntent.getVisibility() == View.VISIBLE) {
                    llCOntent.setVisibility(View.GONE);
                    item.setStatus("0");
                } else {
                    if (defectFalse.isChecked()) {
                        llCOntent.setVisibility(View.VISIBLE);
                        item.setStatus("1");
                    }
                }
                item.update();
            }
        });

        DefactContentDBHelper defactContentDBHelper = new DefactContentDBHelper(mContext);
        Cursor cursor = defactContentDBHelper.queryAll();

        CursorAdapter cursorAdapter = new CursorAdapter(mContext, cursor) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.simple_defact_tv_content_item_layout, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
                int contentColumnIndex = cursor.getColumnIndex(MyOpenhelper.DefactTvColumns.CONTENT);
                String content = cursor.getString(contentColumnIndex);
                contentTv.setText(content);
            }

            @Override
            public String convertToString(Cursor cursor) {
                return cursor.getString(cursor.getColumnIndex(MyOpenhelper.DefactTvColumns.CONTENT));
            }

            @Override
            public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
                if (getFilterQueryProvider() != null) {
                    return getFilterQueryProvider().runQuery(constraint);
                }

                DefactContentDBHelper defactContentDBHelper = new DefactContentDBHelper(mContext);
                Cursor cursor = defactContentDBHelper.queryFliter(String.valueOf(constraint));
                Log.w("linmeng", "cursor.getCount() filter:" + cursor.getCount());
                return cursor;
            }
        };

        tvDiverWay.setAdapter(cursorAdapter);
        tvDiverWay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor1 = cursorAdapter.getCursor();
                if (cursor1 != null && cursor1.getCount() > 0) {
                    boolean isExist = cursor1.moveToPosition(position);
                    if (isExist) {
                        String levelStr = cursor1.getString(cursor.getColumnIndex(MyOpenhelper.DefactTvColumns.LEVEL));
                        Log.w("linmeng", "levelStr:" + levelStr);   //这里获取的是缺陷等级，给陈飞用！  TODO
                        if (TextUtils.isEmpty(levelStr)) {
                            checkOneRb.setChecked(true);
                            checkTwoRb.setChecked(false);
                            checkThreeRb.setChecked(false);
                            item.setGrade_id("37E5647975394B1E952DC5D2796C7D73");
                        } else {
                            if (levelStr.contains("危急")) {
                                checkOneRb.setChecked(true);
                                checkTwoRb.setChecked(false);
                                checkThreeRb.setChecked(false);
                                item.setGrade_id("10C639F13341484997EE8D955322BE02");
                            } else if (levelStr.contains("严重")) {
                                checkOneRb.setChecked(false);
                                checkTwoRb.setChecked(true);
                                checkThreeRb.setChecked(false);
                                item.setGrade_id("2CEB42DA67764AC0BF911B02FB579775");
                            } else if (levelStr.contains("一般")) {
                                checkOneRb.setChecked(false);
                                checkTwoRb.setChecked(false);
                                checkThreeRb.setChecked(true);
                                item.setGrade_id("37E5647975394B1E952DC5D2796C7D73");
                            } else {   //默认为一般
                                checkOneRb.setChecked(true);
                                checkTwoRb.setChecked(false);
                                checkThreeRb.setChecked(false);
                                item.setGrade_id("37E5647975394B1E952DC5D2796C7D73");
                            }
                        }
                        item.update();
                    }
                }
            }
        });
        rgContentType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rgContentType.getChildAt(0).getId()) {
                    item.setGrade_id("10C639F13341484997EE8D955322BE02");
                } else if (checkedId == rgContentType.getChildAt(1).getId()) {
                    item.setGrade_id("2CEB42DA67764AC0BF911B02FB579775");
                } else if (checkedId == rgContentType.getChildAt(2).getId()) {
                    item.setGrade_id("37E5647975394B1E952DC5D2796C7D73");
                }
            }
        });
//        if (item.getStatus().equals("0")) {
//            helper.setImageResource(R.id.iv_check, R.mipmap.patrol_undefined);
//        } else if (item.getStatus().equals("1")) {
//            helper.setImageResource(R.id.iv_check, R.mipmap.patrol_false);
//            helper.setGone(R.id.ll_content, true);
//        } else {
//            helper.setImageResource(R.id.iv_check, R.mipmap.patrol_true);
//            helper.setGone(R.id.ll_content, false);
//        }
//        ImageView ivCheck2 = helper.getView(R.id.iv_check);
//        ivCheck2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(mContext).setTitle(item.getName()).setItems(strings, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        //Toast.makeText(context, "您已经选择了: " + which + ":" + workers[which],Toast.LENGTH_LONG).show();
//                        if (which == 0) {
//                            helper.setImageResource(R.id.iv_check, R.mipmap.patrol_false);
//                            item.setStatus("1");
//                            mItem1.setStatus("1");
//                        } else {
//                            helper.setImageResource(R.id.iv_check, R.mipmap.patrol_true);
//                            item2.setStatus("2");
//                        }
//                        notifyDataSetChanged();
//                        dialog.dismiss();
//                    }
//                }).show();
//            }
//        });
//        RelativeLayout rlContent = helper.getView(R.id.rl_content);
//        LinearLayout llContent = helper.getView(R.id.ll_content);
//        TextView tvItemContent = helper.getView(R.id.tv_item_content);
//        EditText etContent = helper.getView(R.id.et_content);
//        TextView tvContentType = helper.getView(R.id.tv_content_type);
//        tvContentType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, DefectListActivity.class);
//                intent.putExtra("category", item.getCategory());
//                mContext.startActivity(intent);
//            }
//        });
//        Spinner spStart = helper.getView(R.id.sp_start);
//        Spinner spEnd = helper.getView(R.id.sp_end);
//        TextView tvTime = helper.getView(R.id.tv_time);
//        ImageView ivCommit = helper.getView(R.id.iv_commit);
//        RadioGroup rgContentType=helper.getView(R.id.rg_content_type);

//        ImageView ivEdit = helper.getView(R.id.iv_edit);
//        ivEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rlContent.setVisibility(View.GONE);
//                llContent.setVisibility(View.VISIBLE);
//            }
//        });

        if (Constant.patrol_record_audit_status.equals("1") || Constant.patrol_record_audit_status.equals("2")) {
            rlTitle.setEnabled(false);
            tvDiverWay.setEnabled(false);
            defectTrue.setEnabled(false);
            defectFalse.setEnabled(false);
            checkOneRb.setEnabled(false);
            checkTwoRb.setEnabled(false);
            checkThreeRb.setEnabled(false);
            gridView.setEnabled(false);
            initGridViewFromOnline(gridView, item.getOnline_pics());

        } else {
            initGridView(gridView, helper.getAdapterPosition(), item.getPics(), item.getTask_id(), item.getPatrol_id());
        }
    }

    private void initGridViewFromOnline(GridView gridView, String pics) {
        List<String> list = new ArrayList<>();
        if (pics != null) {
            String[] split = pics.split(";");
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
            }
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        GridViewAdapter4 adapter = new GridViewAdapter4(mContext, list);
        gridView.setAdapter(adapter);
    }

    private void initGridView(GridView gridView, int pos, String pics, String task_id, String patrol_id) {
        List<File> list = new ArrayList<>();
        if (pics != null) {
            String[] split = pics.split(";");
            for (int i = 0; i < split.length; i++) {
                list.add(new File(split[i]));
            }
        }
        if (list != null) {
            mGridViewAddImgAdapter = new GridViewAdapter3(mContext, list, task_id, patrol_id);
        } else {
            list = new ArrayList<>();
            mGridViewAddImgAdapter = new GridViewAdapter3(mContext, list, task_id, patrol_id);
        }
        gridView.setAdapter(mGridViewAddImgAdapter);
        List<File> finalList = list;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (finalList.size() == Constant.MAX_SELECT_PIC_NUM) {
                        //查看大图
//                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        startCamera();
                        Constant.defect_tab = tab;
                        Constant.defect_index = pos;
                        Constant.defect_patrol_id = patrol_id;
                        Constant.defect_patrol_index = position;
                    }
                } else {
//                    viewPluImg(position);
                }
            }
        });
    }

    //打开相机
    private void startCamera() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fragment.startActivityForResult(intent, Constant.DEFECT_REQUEST_CODE);
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

    //    // 处理选择的照片的地址
//    public void refreshAdapter(List<LocalMedia> picList) {
//        mPicList.clear();
//        for (LocalMedia localMedia : picList) {
//            //被压缩后的图片路径
//            if (localMedia.isCompressed()) {
//                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
//                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
//                mGridViewAddImgAdapter.notifyDataSetChanged();
//            }
//        }
//    }

    //查看大图
//    private void viewPluImg(int position) {
//        Intent intent = new Intent(mContext, PlusImageActivity.class);
//        intent.putStringArrayListExtra(Constant.IMG_LIST, mPicList);
//        intent.putExtra(Constant.POSITION, position);
//        activity.startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
//    }
//    private void uploadItem(PatrolLevel2 item2, String content, String time, RelativeLayout rlContent, LinearLayout llContent, TextView tvContent) {
//        params.clear();
//        params.put("task_id", toRequestBody(item2.getTask_id()));
//        params.put("grade_id", toRequestBody(grade_id));
//        params.put("content", toRequestBody(content));
//        params.put("patrol_id", toRequestBody(item2.getPatrol_id()));
//        params.put("category_id", toRequestBody(item2.getCategory()));
//        params.put("find_time", toRequestBody(time));
//        params.put("start_id", toRequestBody(startTowerId));
//        params.put("start_name", toRequestBody(startTowerName));
//        params.put("end_id", toRequestBody(endTowerId));
//        params.put("end_name", toRequestBody(endTowerName));
//        String line_id = (String) SPUtil.get(mContext, "ids", "line_id", "");
//        if (line_id != null || !line_id.equals("")) {
//            params.put("line_id", toRequestBody(line_id));
//        }
//        String line_name = (String) SPUtil.get(mContext, "ids", "line_name", "");
//        if (line_name != null || !line_name.equals("")) {
//            params.put("line_name", toRequestBody(line_name));
//        }
//        String find_user_id = (String) SPUtil.get(mContext, "ids", "find_user_id", "");
//        if (find_user_id != null || !find_user_id.equals("")) {
//            params.put("find_user_id", toRequestBody(find_user_id));
//        }
//        String find_user_name = (String) SPUtil.get(mContext, "ids", "find_user_name", "");
//        if (find_user_name != null || !find_user_name.equals("")) {
//            params.put("find_user_name", toRequestBody(find_user_name));
//        }
//        params.put("towerList[0].id", toRequestBody(item2.getId()));
//        //杆塔名从个人任务获取
//        String tower_name = (String) SPUtil.get(mContext, "ids", "tower_name", "");
//        if (tower_name != null || !tower_name.equals("")) {
//            params.put("start_name", toRequestBody(tower_name));
//            params.put("towerList[0].name", toRequestBody(tower_name));
//        } else {
//            params.put("start_name", toRequestBody("#001"));
//            params.put("towerList[0].name", toRequestBody("#001"));
//        }
//        if (mPicList.size() == 0) {
//            Toast.makeText(mContext, "请上传图片", Toast.LENGTH_SHORT).show();
//        } else {
//            for (int i = 0; i < mPicList.size(); i++) {
//                File file = new File(mPicList.get(i));
//                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
////            params.put("towerList[0].file", requestFile);
//                params.put("file\"; filename=\"" + file.getName(), requestFile);
//            }
//        }
//        BaseRequest.getInstance().getService().commitPatrolContent(params).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver(mContext) {
//                    @Override
//                    protected void onSuccees(BaseResult t) throws Exception {
//                        ProgressDialog.cancle();
//                        Toast.makeText(mContext, "上传成功！", Toast.LENGTH_SHORT).show();
////                        RxRefreshEvent.publish("updateDefect@2");
//                        rlContent.setVisibility(View.VISIBLE);
//                        llContent.setVisibility(View.GONE);
//                        tvContent.setText(content);
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        ProgressDialog.cancle();
//                        Toast.makeText(mContext, "上传失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}
