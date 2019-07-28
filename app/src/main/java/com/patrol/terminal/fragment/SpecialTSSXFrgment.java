package com.patrol.terminal.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.AutoCursorAdapter;
import com.patrol.terminal.adapter.TssxAddAdapter;
import com.patrol.terminal.adapter.TssxEditAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.JDDZbean_Table;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.PersonalTaskListBean_Table;
import com.patrol.terminal.bean.TSSXBean;
import com.patrol.terminal.bean.TSSXLocalBean;
import com.patrol.terminal.bean.TSSXLocalBean_Table;
import com.patrol.terminal.bean.TssxToEqTowerWares;
import com.patrol.terminal.bean.TssxToFile;
import com.patrol.terminal.sqlite.DefactContentDBHelper;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.NoScrollViewPager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * 特殊属性
 */
public class SpecialTSSXFrgment extends BaseFragment {


    @BindView(R.id.tssx_add)
    ImageButton tssx_add;
    @BindView(R.id.xs_tssx_pager)
    NoScrollViewPager xs_tssx_pager;
    @BindView(R.id.grid_addtssx)
    GridView grid_addtssx;
    @BindView(R.id.rl_add)
    RelativeLayout rl_add;
    @BindView(R.id.grid_addbtn)
    Button grid_addbtn;
    @BindView(R.id.xs_tssx_lv)
    ListView xs_tssx_lv;
    @BindView(R.id.tssx_select_title)
    RadioGroup sankua_rad;
    @BindView(R.id.tssx_sankua)
    RadioButton tssx_sankua;

    private View view;
    /**
     * 特殊属性列表
     */
    private List<TSSXBean> tssxBeanList = new ArrayList<>();
    /**
     * 添加的特殊属性列表
     */
    private List<TSSXBean> addTssxList = new ArrayList<>();
    /**
     * 添加特殊屬性adapter
     */
    private TssxAddAdapter tssxAddAdapter;
    /**
     * 添加的特殊属性列表
     */
    private List<TSSXBean> skTssxList = new ArrayList<>();
    private List<TSSXBean> lfTssxList = new ArrayList<>();
    private List<TSSXBean> qtTssxList = new ArrayList<>();


    private TssxEditAdapter skEditAdapter;
    private TssxEditAdapter lfEditAdapter;
    private TssxEditAdapter qtEditAdapter;

    private String TYPE_SK = "9F2DF17853FC468884A3F37260FDFED6";
    private String TYPE_LF = "98F764466504450DBC4C490F6DB512C2";
    private String TYPE_QT = "B2E7DF49D7AB4B358D5DD4BF4DF639EC";

    private String TYPE_SK_INT = "1";
    private String TYPE_LF_INT = "2";
    private String TYPE_QT_INT = "3";

    private DefactContentDBHelper defactContentDBHelper;
    private Cursor cursor;
    private String task_id;
    private String line_id;
    private String tower_id;
    private int indexPosition;
    private int itemIndex;
    private String indexKey;
    private String oldPhotoStr;
    private boolean isAddPhtot = false;

    private PersonalTaskListBean personalTaskListBean;
    private boolean isSave = false;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_xs_tssx, null);

        return view;
    }

    @Override
    protected void initData() {
        skEditAdapter = new TssxEditAdapter(getActivity(), SpecialTSSXFrgment.this);
        lfEditAdapter = new TssxEditAdapter(getActivity(), SpecialTSSXFrgment.this);
        qtEditAdapter = new TssxEditAdapter(getActivity(), SpecialTSSXFrgment.this);

        task_id = getActivity().getIntent().getStringExtra("task_id");
        line_id = getActivity().getIntent().getStringExtra("line_id");
        tower_id = getActivity().getIntent().getStringExtra("tower_id");

        intTSSX();
        initClick();
        loadLocalData();
        if (Constant.patrol_record_audit_status.equals("1") || Constant.patrol_record_audit_status.equals("2") || Constant.patrol_record_audit_status.equals("3")) {
            saveTssx();
        } else {
            loadLocalData();
        }

        personalTaskListBean = SQLite.select().from(PersonalTaskListBean.class)
                .where(PersonalTaskListBean_Table.id.eq(task_id), JDDZbean_Table.user_id.eq(SPUtil.getUserId(getActivity())))
                .querySingle();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume

        } else {
            //相当于Fragment的onPause
        }
    }

    public void initClick() {
        defactContentDBHelper = new DefactContentDBHelper(getActivity());
        cursor = defactContentDBHelper.queryAll();
        AutoCursorAdapter cursorAdapter = new AutoCursorAdapter(getActivity(), cursor);

        //是否可编辑状态  不可编辑  0,4可以编辑  123不能编辑
        if (Constant.patrol_record_audit_status.equals("1") || Constant.patrol_record_audit_status.equals("2") || Constant.patrol_record_audit_status.equals("3")) {
            tssx_add.setVisibility(View.GONE);
            xs_tssx_lv.setEnabled(false);
        } else {
            tssx_add.setVisibility(View.VISIBLE);
            xs_tssx_lv.setEnabled(true);
        }

        sankua_rad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tssx_sankua:
                        xs_tssx_lv.setAdapter(skEditAdapter);
                        skEditAdapter.setData(skTssxList);
                        break;
                    case R.id.tssx_liufang:
                        xs_tssx_lv.setAdapter(lfEditAdapter);
                        lfEditAdapter.setData(lfTssxList);
                        break;
                    case R.id.tssx_qita:
                        xs_tssx_lv.setAdapter(qtEditAdapter);
                        qtEditAdapter.setData(qtTssxList);
                        break;
                }
            }
        });

        //添加属性
        tssx_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                标记离线操作
                if (personalTaskListBean != null && !isSave) {
                    isSave = true;
                    personalTaskListBean.setIs_save("0");
                    personalTaskListBean.update();
                    getActivity().setResult(RESULT_OK);
                }

                if (rl_add.getVisibility() == View.VISIBLE) {
                    rl_add.setVisibility(View.GONE);
                    sankua_rad.check(tssx_sankua.getId());
                    xs_tssx_lv.setAdapter(skEditAdapter);
                } else {
                    rl_add.setVisibility(View.VISIBLE);
                }
                tssxAddAdapter.setData(tssxBeanList);
            }
        });
        /**
         * 保存按钮
         */
        grid_addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_add.setVisibility(View.GONE);
                addTssxList.clear();
                addTssxList.addAll(tssxAddAdapter.getCheckList());
                refreshPage(addTssxList);
            }
        });

        skEditAdapter.setOnclickAdapter(new TssxEditAdapter.onClickAdapter() {
            @Override
            public void delItemObject(TSSXBean bean) {
                tssxAddAdapter.removeItem(bean);
                delBean(bean.getKey());
            }

            @Override
            public AutoCursorAdapter getCursorAdapter() {
                return cursorAdapter;
            }

            @Override
            public Cursor getCursor() {
                return cursor;
            }

            @Override
            public void updateYhnr(String task_key, String yhnr) {
                updateYhnrBean(task_key, yhnr);
            }

            @Override
            public void updateDJ(String task_key, String dj) {
                updateDjBean(task_key, dj);
            }

            @Override
            public void updatePhoto(String task_key, int index, int position, String oldPhoto, boolean isAdd) {
                indexPosition = index;
                itemIndex = position;
                indexKey = task_key;
                oldPhotoStr = oldPhoto;
                isAddPhtot = isAdd;
            }

            @Override
            public void delTssxPhoto(String key, String photoStr) {
                delDataPhoto(key, photoStr);
            }

        });

        lfEditAdapter.setOnclickAdapter(new TssxEditAdapter.onClickAdapter() {
            @Override
            public void delItemObject(TSSXBean bean) {
                tssxAddAdapter.removeItem(bean);
                delBean(bean.getKey());
            }

            @Override
            public AutoCursorAdapter getCursorAdapter() {
                return cursorAdapter;
            }

            @Override
            public Cursor getCursor() {
                return cursor;
            }

            @Override
            public void updateYhnr(String task_key, String yhnr) {
                updateYhnrBean(task_key, yhnr);
            }

            @Override
            public void updateDJ(String task_key, String dj) {
                updateDjBean(task_key, dj);
            }

            @Override
            public void updatePhoto(String task_key, int index, int position, String oldPhoto, boolean isAdd) {
                indexPosition = index;
                itemIndex = position;
                indexKey = task_key;
                oldPhotoStr = oldPhoto;
                isAddPhtot = isAdd;
            }

            @Override
            public void delTssxPhoto(String key, String photoStr) {
                delDataPhoto(key, photoStr);
            }

        });

        qtEditAdapter.setOnclickAdapter(new TssxEditAdapter.onClickAdapter() {
            @Override
            public void delItemObject(TSSXBean bean) {
                tssxAddAdapter.removeItem(bean);
                delBean(bean.getKey());
            }

            @Override
            public AutoCursorAdapter getCursorAdapter() {
                return cursorAdapter;
            }

            @Override
            public Cursor getCursor() {
                return cursor;
            }

            @Override
            public void updateYhnr(String task_key, String yhnr) {
                updateYhnrBean(task_key, yhnr);
            }

            @Override
            public void updateDJ(String task_key, String dj) {
                updateDjBean(task_key, dj);
            }

            @Override
            public void updatePhoto(String task_key, int index, int position, String oldPhoto, boolean isAdd) {
                indexPosition = index;
                itemIndex = position;
                indexKey = task_key;
                oldPhotoStr = oldPhoto;
                isAddPhtot = isAdd;
            }

            @Override
            public void delTssxPhoto(String key, String photoStr) {
                delDataPhoto(key, photoStr);
            }

        });
    }



    //网络获取保存本地
    public void saveTssx() {
//        ProgressDialog.show(getContext(),false,"正在加载中...");
        if (!TextUtils.isEmpty(tower_id)) {
            BaseRequest.getInstance().getService()
                    .getTssxList(tower_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<TssxToEqTowerWares>>(getContext()) {
                        @Override
                        protected void onSuccees(BaseResult<List<TssxToEqTowerWares>> tssx) throws Exception {
//                        ProgressDialog.cancle();
                            if (tssx.getResults().size() > 0) {
                                List<TSSXBean> typeBeanList = new ArrayList<>();
                                for (TssxToEqTowerWares bean : tssx.getResults()) {
                                    TSSXBean tssxBean = new TSSXBean();
                                    tssxBean.setKey(bean.getWares_id());
                                    tssxBean.setValues(bean.getWares_name());
//                                    tssxBean.setDj(setDjIdStatus(bean.getTaskTrouble().getTrouble_level()));
                                    tssxBean.setParKey(intToKey(bean.getPda_sign()));//特殊属性父id
                                    tssxBean.setYhnr(bean.getRemarks());

                                    if (bean.getEqTowerWaresImgList() != null) {
                                        tssxBean.setPhotoList(fileListToList(bean.getEqTowerWaresImgList()));
                                    }

                                    typeBeanList.add(tssxBean);

                                }
                                //不可编辑时表示已上传 删除本地相关熟悉
                                if (Constant.patrol_record_audit_status.equals("1") || Constant.patrol_record_audit_status.equals("2") || Constant.patrol_record_audit_status.equals("3")) {
                                    List<TSSXLocalBean> tssxLocalBean = SQLite.select().from(TSSXLocalBean.class)
                                            .where(TSSXLocalBean_Table.task_id.is(task_id))
                                            .queryList();
                                    for (int i = 0; i < tssxLocalBean.size(); i++) {
                                        tssxLocalBean.get(i).delete();
                                    }
                                }

                                refreshPage(typeBeanList);
                                refreshAddTssxData(typeBeanList);
                            }
                        }

                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                            ProgressDialog.cancle();
                        }
                    });
        }

    }

    /**
     * 设置隐患等级
     */
    public String setDjIdStatus(String djid) {
        String YHDJStr = "";
        if (djid.equals(Constant.DJ_YB)) {
            YHDJStr = Constant.DJ_YB_STR;
        } else if (djid.equals(Constant.DJ_YZ)) {
            YHDJStr = Constant.DJ_YZ_STR;
        } else if (djid.equals(Constant.DJ_WJ)) {
            YHDJStr = Constant.DJ_WJ_STR;
        }
        return YHDJStr;
    }

    //（1：三跨；2：八防：3：其他）
    public String intToKey(String type) {
        if (type.equals("1"))
            return TYPE_SK;
        else if (type.equals("2"))
            return TYPE_LF;
        else if (type.equals("3"))
            return TYPE_QT;
        return "3";
    }

    //网络获取失败加载本地的
    public void loadLocalData() {
        List<TSSXLocalBean> tssxList = SQLite.select().from(TSSXLocalBean.class)
                .where(TSSXLocalBean_Table.task_id.is(task_id))
                .and(TSSXLocalBean_Table.tower_id.is(tower_id))
                .queryList();
        Log.e("tssxList", tssxList.size() + "");
        skTssxList.clear();
        lfTssxList.clear();
        qtTssxList.clear();

        for (int i = 0; i < tssxList.size(); i++) {
            TSSXBean tssxBean = new TSSXBean();
            tssxBean.setKey(tssxList.get(i).getKey());
            tssxBean.setParKey(tssxList.get(i).getParKey());
            tssxBean.setValues(tssxList.get(i).getValues());
            tssxBean.setYhnr(tssxList.get(i).getYhnr());
            tssxBean.setDj(tssxList.get(i).getDj());

            List<String> photoList = new ArrayList<>();
            String[] photoStr = tssxList.get(i).getPhotoStr().split(";");
            if (photoStr.length > 0) {
                for (int d = 0; d < photoStr.length; d++) {
                    if(!TextUtils.isEmpty(photoStr[d]))
                        photoList.add(photoStr[d]);
                }
            }
            tssxBean.setPhotoList(photoList);

            if (tssxList.get(i).getParKey().equals(TYPE_SK)) {
                skTssxList.add(tssxBean);
            } else if (tssxList.get(i).getParKey().equals(TYPE_LF)) {
                lfTssxList.add(tssxBean);
            } else if (tssxList.get(i).getParKey().equals(TYPE_QT)) {
                qtTssxList.add(tssxBean);
            }
        }

        sankua_rad.check(tssx_sankua.getId());
        xs_tssx_lv.setAdapter(skEditAdapter);

        skEditAdapter.setData(skTssxList);
        lfEditAdapter.setData(lfTssxList);
        qtEditAdapter.setData(qtTssxList);

        xs_tssx_lv.setVisibility(View.VISIBLE);

        refreshAddTssxData(skTssxList);
        refreshAddTssxData(lfTssxList);
        refreshAddTssxData(qtTssxList);

    }

    //刷新页面数据
    public void refreshPage(List<TSSXBean> typeBeanList) {
        initTssxData(typeBeanList);

        sankua_rad.check(tssx_sankua.getId());
        xs_tssx_lv.setAdapter(skEditAdapter);

        skEditAdapter.setData(skTssxList);
        lfEditAdapter.setData(lfTssxList);
        qtEditAdapter.setData(qtTssxList);

        xs_tssx_lv.setVisibility(View.VISIBLE);
    }

    //回显特殊属性选择列表
    public void refreshAddTssxData(List<TSSXBean> localList) {
        for (TSSXBean locaBean : localList) {
            for (TSSXBean addBean : tssxBeanList) {
                if (addBean.getKey().equals(locaBean.getKey())) {
                    addBean.setCheck(true);
                }
            }
        }
        tssxAddAdapter.setData(tssxBeanList);
    }

    public List<String> fileListToList(List<TssxToFile> fileList) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            list.add(BaseUrl.BASE_URL + fileList.get(i).getFile_path() + fileList.get(i).getFilename());
        }
        return list;
    }

    //删除本地照片数据
    public void delDataPhoto(String key, String photoStr) {
        TSSXLocalBean product = SQLite.select()
                .from(TSSXLocalBean.class)
                .where(TSSXLocalBean_Table.key.is(key))
                .and(TSSXLocalBean_Table.task_id.is(task_id))
                .querySingle();
        String[] photo = product.getPhotoStr().split(";");
        List<String> photoList = new ArrayList<>();
        for (int i = 0; i < photo.length; i++) {
            if (!photo[i].equals(photoStr))
                photoList.add(photo[i]);
        }

        product.setPhotoStr(photoListToStr(photoList));
        product.update();
    }
    //更新等级
    public void updateDjBean(String key, String dj) {
        SQLite.update(TSSXLocalBean.class)
                .set(TSSXLocalBean_Table.dj.eq(dj))
                .where(TSSXLocalBean_Table.key.is(key))
                .and(TSSXLocalBean_Table.task_id.is(task_id))
                .execute();
    }
    //更新隐患内容
    public void updateYhnrBean(String key, String yhnr) {
        SQLite.update(TSSXLocalBean.class)
                .set(TSSXLocalBean_Table.yhnr.eq(yhnr))
                .where(TSSXLocalBean_Table.key.is(key))
                .and(TSSXLocalBean_Table.task_id.is(task_id))
                .execute();
    }

    //刪除数据库 添加的属性
    public void delBean(String key) {
        List<TSSXLocalBean> product = SQLite.select()
                .from(TSSXLocalBean.class)
                .where(TSSXLocalBean_Table.key.is(key))
                .and(TSSXLocalBean_Table.task_id.is(task_id))
                .queryList();//查询单个
        if (product.size() > 0) {
            for (TSSXLocalBean bean : product) {
                bean.delete();
            }
        }

    }
    //初始化数据保存本地
    public void initTssxData(List<TSSXBean> typeBeanList) {
        skTssxList.clear();
        lfTssxList.clear();
        qtTssxList.clear();

        String[] data = DateUatil.getDateStr().split("-");
        int count = typeBeanList.size();
        for (int i = 0; i < count; i++) {
            TSSXBean bean = typeBeanList.get(i);
            if (bean.getParKey().equals(TYPE_SK)) {
                skTssxList.add(bean);
            } else if (bean.getParKey().equals(TYPE_LF)) {
                lfTssxList.add(bean);
            } else if (bean.getParKey().equals(TYPE_QT)) {
                qtTssxList.add(bean);
            }

            TSSXLocalBean tssxLocalBean = SQLite.select().from(TSSXLocalBean.class)
                    .where(TSSXLocalBean_Table.task_id.is(task_id))
                    .and(TSSXLocalBean_Table.key.is(typeBeanList.get(i).getKey())).querySingle();
            TSSXLocalBean localBean = new TSSXLocalBean();
            if (tssxLocalBean == null) {
                localBean.setDj(bean.getDj());
                localBean.setKey(bean.getKey());
                localBean.setParKey(bean.getParKey());
                localBean.setValues(bean.getValues());
                localBean.setLine_id(line_id);
                localBean.setTask_id(task_id);
                localBean.setTower_id(tower_id);
                localBean.setYear(formatStr(data[0]));
                localBean.setMonth(formatStr(data[1]));
                localBean.setDay(formatStr(data[2]));
                localBean.setPhotoStr(photoListToStr(bean.getPhotoList()));
                localBean.save();
            } else {
                localBean.setDj(tssxLocalBean.getDj());
                localBean.setKey(tssxLocalBean.getKey());
                localBean.setParKey(bean.getParKey());
                localBean.setValues(tssxLocalBean.getValues());
                localBean.setLine_id(line_id);
                localBean.setTask_id(task_id);
                localBean.setTower_id(tower_id);
                localBean.setYear(tssxLocalBean.getYear());
                localBean.setMonth(tssxLocalBean.getMonth());
                localBean.setDay(tssxLocalBean.getDay());
                localBean.setPhotoStr(photoListToStr(bean.getPhotoList()));
                localBean.update();
            }
        }
    }

    public String formatStr(String data) {
        return Integer.valueOf(data).toString();
    }


    public String photoListToStr(List<String> list) {
        String photo2 = "";
        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.isEmpty(photo2))
                photo2 = list.get(i);
            else
                photo2 = photo2 + ";" + list.get(i);
        }
        return photo2;
    }

    /**
     *添加和更新
     * @param task_key
     * @param photoPath
     */
    public void updatePhoto(String task_key, String photoPath, String oldPhoto, boolean isAdd) {
        List<String> photoList = new ArrayList<>();
        String photo = "";
        TSSXLocalBean product = SQLite.select()
                .from(TSSXLocalBean.class)
                .where(TSSXLocalBean_Table.key.is(task_key))
                .and(TSSXLocalBean_Table.task_id.is(task_id))
                .querySingle();

        String[] photoStr = product.getPhotoStr().split(";");

        if (isAdd) {

            for (int i = 0; i < photoStr.length; i++) {
                photoList.add(photoStr[i]);
            }
            photoList.add(photoPath);

        } else {

            for (int i = 0; i < photoStr.length; i++) {
                photoList.add(photoStr[i]);
            }

            int index = photoList.indexOf(oldPhoto);
            if (index != -1)
                photoList.set(index, photoPath);

        }

        product.setPhotoStr(photoListToStr(photoList));
        product.update();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.DEFECT_REQUEST_CODE:
                    Log.d("TAG", "success");
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    String path = Environment.getExternalStorageDirectory().getPath()
                            + "/MyPhoto/" + DateUatil.getDateStr() + "_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                    try {
                        //保存本地成功 刷新刷新数据添加到页面
                        FileUtil.saveFile(bitmap, path);
                        Log.e("保存照片地址", path);
                        switch (sankua_rad.getCheckedRadioButtonId()) {
                            case R.id.tssx_sankua:
                                skEditAdapter.setPhotoData(path, indexPosition, itemIndex);
                                updatePhoto(indexKey, path, oldPhotoStr, isAddPhtot);
                                break;
                            case R.id.tssx_liufang:
                                lfEditAdapter.setPhotoData(path, indexPosition, itemIndex);
                                updatePhoto(indexKey, path, oldPhotoStr, isAddPhtot);
                                break;
                            case R.id.tssx_qita:
                                qtEditAdapter.setPhotoData(path, indexPosition, itemIndex);
                                updatePhoto(indexKey, path, oldPhotoStr, isAddPhtot);
                                break;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    public void intTSSX() {
        tssxBeanList = new ArrayList<TSSXBean>();
        //交叉跨越
        tssxBeanList.add(new TSSXBean("274EE1B7A4F444F9AD8F218791BA2740", "9F2DF17853FC468884A3F37260FDFED6", "重要输电通道"));
        tssxBeanList.add(new TSSXBean("E1C4D215842044CE82BE1F05704238B0", "9F2DF17853FC468884A3F37260FDFED6", "高速铁路"));
        tssxBeanList.add(new TSSXBean("D048D313FB3C4D62B6A4A6AF0E1BBBB7", "9F2DF17853FC468884A3F37260FDFED6", "高速公路"));
        tssxBeanList.add(new TSSXBean("3A7703BA2EFA4B9E86732DB1B0FA4E3F", "9F2DF17853FC468884A3F37260FDFED6", "普通铁路"));
        tssxBeanList.add(new TSSXBean("2F9D2BEF6B08403FAD1FED246B79410B", "9F2DF17853FC468884A3F37260FDFED6", "普通公路"));
        tssxBeanList.add(new TSSXBean("41EA3E6D6EA843B593EA674BC29F1727", "9F2DF17853FC468884A3F37260FDFED6", "热力管道"));
        tssxBeanList.add(new TSSXBean("EF61B35409A64CC1966264F45012A139", "9F2DF17853FC468884A3F37260FDFED6", "高楼"));
        tssxBeanList.add(new TSSXBean("22B28CC35E47447586E692FEC54006E3", "9F2DF17853FC468884A3F37260FDFED6", "民房"));
        tssxBeanList.add(new TSSXBean("CBD917DEE74B47079552D88D0875778C", "9F2DF17853FC468884A3F37260FDFED6", "厂房"));
        tssxBeanList.add(new TSSXBean("2C9F831710564224BCB16CBCD86DE40E", "9F2DF17853FC468884A3F37260FDFED6", "成片林"));
        tssxBeanList.add(new TSSXBean("213C9D09E0E64CA4B0DCFA54595F5066", "9F2DF17853FC468884A3F37260FDFED6", "苗圃"));
        tssxBeanList.add(new TSSXBean("A0EB5B09737243E1841AA9E058CF02F1", "9F2DF17853FC468884A3F37260FDFED6", "零星树木"));
        tssxBeanList.add(new TSSXBean("5E9C3B53A67C495DBAFC43ADB0FEE87A", "9F2DF17853FC468884A3F37260FDFED6", "0.4千伏电力"));//线路
        tssxBeanList.add(new TSSXBean("B4B0E1D6A6864E01A40B443E147D0B0F", "9F2DF17853FC468884A3F37260FDFED6", "220V电力线路"));
        tssxBeanList.add(new TSSXBean("2DF697AC80C94F11A922AB165B5284F8", "9F2DF17853FC468884A3F37260FDFED6", "380V电力线路"));
        tssxBeanList.add(new TSSXBean("B0F2F7B7AB0640ECA8C7C03D0BFA45FD", "9F2DF17853FC468884A3F37260FDFED6", "10kV线路"));
        tssxBeanList.add(new TSSXBean("1B749F66C7284ADDA2245C0B6C83DA1C", "9F2DF17853FC468884A3F37260FDFED6", "35kV线路"));
        tssxBeanList.add(new TSSXBean("3C4C08D0062748F881685CBF067999BE", "9F2DF17853FC468884A3F37260FDFED6", "110kV线路"));
        tssxBeanList.add(new TSSXBean("F0C11CA24469469594D733F6A1467A4B", "9F2DF17853FC468884A3F37260FDFED6", "220kV线路"));
        tssxBeanList.add(new TSSXBean("143A3B1C79BD45E9AA31D5D0797C7364", "9F2DF17853FC468884A3F37260FDFED6", "330kV线路"));
        tssxBeanList.add(new TSSXBean("98386414B6F447AABA072D834B7BAD15", "9F2DF17853FC468884A3F37260FDFED6", "通讯线"));
        tssxBeanList.add(new TSSXBean("B8B48592F5FE465DA36E6A207904A327", "9F2DF17853FC468884A3F37260FDFED6", "通航河流"));
        tssxBeanList.add(new TSSXBean("2243F7A2B8074FBE80DEAE9CF7505B13", "9F2DF17853FC468884A3F37260FDFED6", "一般河流"));

        //八防
        tssxBeanList.add(new TSSXBean("CE0954EF596447CA9458CC230234E01A", "98F764466504450DBC4C490F6DB512C2", "防鸟害"));
        tssxBeanList.add(new TSSXBean("EC3507FA707F431EAB2829888F7E368F", "98F764466504450DBC4C490F6DB512C2", "公墓区"));
        tssxBeanList.add(new TSSXBean("1CFA112BA67842579D1DF0D0DFBD8BBC", "98F764466504450DBC4C490F6DB512C2", "山火易发区"));
        tssxBeanList.add(new TSSXBean("965586AE9E2748EAAB4C2FDD464396E6", "98F764466504450DBC4C490F6DB512C2", "田间麦场"));
        tssxBeanList.add(new TSSXBean("9A7B316C7AF24004B7AF61CF43F953CE", "98F764466504450DBC4C490F6DB512C2", "重要林区"));
        tssxBeanList.add(new TSSXBean("23EB52FFB8E648BD936CBA8CC1AEDE21", "98F764466504450DBC4C490F6DB512C2", "高大山区"));
        tssxBeanList.add(new TSSXBean("167110830E6F478AA8E9C6BA85EB6848", "98F764466504450DBC4C490F6DB512C2", "雷电密集区"));
        tssxBeanList.add(new TSSXBean("145EBA8EA8634AC1BE32D3201A0D6465", "98F764466504450DBC4C490F6DB512C2", "C1级以上雷区"));
        tssxBeanList.add(new TSSXBean("78F6A3F642284FA2983FF3C42E2B7D8C", "98F764466504450DBC4C490F6DB512C2", "微地形气象区"));
        tssxBeanList.add(new TSSXBean("CD1A12BA8DB3466980CA48CE7C33EA0B", "98F764466504450DBC4C490F6DB512C2", "大风口"));
        tssxBeanList.add(new TSSXBean("B35AFDECC6A444439E6E48BBCC467644", "98F764466504450DBC4C490F6DB512C2", "垭口"));
        tssxBeanList.add(new TSSXBean("539AAE2C366F43DB96C068BE555A01C0", "98F764466504450DBC4C490F6DB512C2", "风偏放电"));
        tssxBeanList.add(new TSSXBean("C59D8D3C45394AB291C80F083A234E39", "98F764466504450DBC4C490F6DB512C2", "重污区"));
        tssxBeanList.add(new TSSXBean("B07ACBE745DD471784D1783445BEA879", "98F764466504450DBC4C490F6DB512C2", "防覆冰"));
        tssxBeanList.add(new TSSXBean("54E6640B411C4AFCAF766D987EB898C2", "98F764466504450DBC4C490F6DB512C2", "煤矿采空区"));
        tssxBeanList.add(new TSSXBean("DF12733B54EF443B8695A3C5BB4621E3", "98F764466504450DBC4C490F6DB512C2", "山体滑坡区"));
        tssxBeanList.add(new TSSXBean("D9FCE93C347D46B58AED96DB463CCEF0", "98F764466504450DBC4C490F6DB512C2", "地质沉陷区"));
        tssxBeanList.add(new TSSXBean("FB76395583E34DD5833E94FB75BD1DFF", "98F764466504450DBC4C490F6DB512C2", "高大机械施工"));
        tssxBeanList.add(new TSSXBean("785CC6BE3D8349A4BC4AC68F3F35439A", "98F764466504450DBC4C490F6DB512C2", "违章建筑"));
        tssxBeanList.add(new TSSXBean("81DD3E1DC5884FC3AA580DC93BA0F577", "98F764466504450DBC4C490F6DB512C2", "挖山取土区"));
        tssxBeanList.add(new TSSXBean("D24DE68B16264B1E8247903EAC8CC25B", "98F764466504450DBC4C490F6DB512C2", "易飘物"));//（彩钢板、防尘网及塑料膜等）
        tssxBeanList.add(new TSSXBean("386A4131AB594B999A68D4C6D767824F", "98F764466504450DBC4C490F6DB512C2", "风筝集中区"));
        tssxBeanList.add(new TSSXBean("5B3B30B300E6418BAB8E23CEEA446522", "98F764466504450DBC4C490F6DB512C2", "易燃易爆区"));
        tssxBeanList.add(new TSSXBean("3EC88457FA3041F596B86045BB66DF53", "98F764466504450DBC4C490F6DB512C2", "杂草焚烧"));
        tssxBeanList.add(new TSSXBean("197A79E3BA0A48C6AAECCEDFCEAA6C7C", "98F764466504450DBC4C490F6DB512C2", "加油站"));
        tssxBeanList.add(new TSSXBean("B2E24FE1927A4E189AF3A1C017B432B8", "98F764466504450DBC4C490F6DB512C2", "炼油厂"));

        //其他
        tssxBeanList.add(new TSSXBean("FEFFD17137CA4E3891DCC3C28457FFA2", "B2E7DF49D7AB4B358D5DD4BF4DF639EC", "单线输路通道"));
//        tssxBeanList.add(new TSSXBean("460116CD2E3A42DDAC92D432033EDD4B", "B2E7DF49D7AB4B358D5DD4BF4DF639EC", "重要输电通道"));

        tssxAddAdapter = new TssxAddAdapter(getActivity(), tssxBeanList);
        grid_addtssx.setAdapter(tssxAddAdapter);


    }

}
