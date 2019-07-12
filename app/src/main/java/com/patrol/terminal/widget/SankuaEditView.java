package com.patrol.terminal.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.AutoCursorAdapter;
import com.patrol.terminal.adapter.GridViewAdapter2;
import com.patrol.terminal.adapter.TssxPhotoAdapter;
import com.patrol.terminal.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 三跨六防通用编辑框
 */
public class SankuaEditView extends LinearLayout {

    private View view;
    private Context context;
    private AutoCompleteTextView sankua_yhnr;
    private RadioGroup sankua_rad;
    private GridView sk_gridview;
    private TextView item_title;
    private LinearLayout item_context;
    private RelativeLayout item_title_rl;

    private ImageView item_tssx_del;

    private GridViewAdapter2 mGridViewAddImgAdapter;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源

    private String YHDJStr;

    private CursorAdapter cursorAdapter;
    private onTssxClick mOnTssxClick;
    private TssxPhotoAdapter photoAdapter;

    public SankuaEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        view = (View) LayoutInflater.from(context).inflate(R.layout.cusview_sankua, this, true);
        sankua_yhnr = (AutoCompleteTextView) view.findViewById(R.id.sankua_yhnr);
        sankua_rad = (RadioGroup) view.findViewById(R.id.sankua_rad);
        sk_gridview = view.findViewById(R.id.sk_gridview);
        item_title = view.findViewById(R.id.item_tssx_title);
        item_context = view.findViewById(R.id.ll_content);
        item_title_rl = view.findViewById(R.id.item_title_rl);
        item_tssx_del = view.findViewById(R.id.item_tssx_del);

        initClick();

    }

    public void setItemYhnr(String yhnr)
    {
        if(yhnr == null)
            sankua_yhnr.setText("");
        else
            sankua_yhnr.setText(yhnr);
    }

    /**
     * 设置item title
     *
     * @param title
     */
    public void setItemTilte(String title) {
        item_title.setText(title);
    }

    /**
     * 是否显示
     *
     * @param isShow
     */
    public void setViewVisibility(boolean isShow) {
        if (isShow) {
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(GONE);
        }
    }

    /**
     * 当前控件显示状态
     *
     * @return
     */
    public boolean getShowState() {
        if (view.getVisibility() == VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回隐患等级文字
     *
     * @return
     */
    public String getRadioDJ() {
        if (TextUtils.isEmpty(YHDJStr))
            return "一般";
        else
            return YHDJStr;
    }

    /**
     * 返回隐患内容
     *
     * @return
     */
    public String getYHStr() {
        return sankua_yhnr.getText().toString().trim();
    }

    public String setDjStrToKey(String djid) {
        String djKey = "";
        if (djid.equals("一般")) {
            djKey = Constant.DJ_YB;
        } else if (djid.equals("严重")) {
            djKey = Constant.DJ_YZ;
        } else if (djid.equals("危急")) {
            djKey = Constant.DJ_WJ;
        }
        return djKey;
    }

    public void setDjStatus(String dj) {

        RadioButton rb;
        if(dj == null|| TextUtils.isEmpty(dj))
            YHDJStr = "一般";
        else
            YHDJStr = dj.trim();

        int count = sankua_rad.getChildCount();
        for (int i = 0; i < count; i++) {
            rb = (RadioButton) sankua_rad.getChildAt(i);
            if (YHDJStr.equals(rb.getText().toString().trim())) {
                sankua_rad.check(rb.getId());
            }
        }
        mOnTssxClick.getDj(YHDJStr);
    }


    public void initClick() {

        sankua_rad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                YHDJStr = rb.getText().toString().trim();

                mOnTssxClick.getDj(YHDJStr);
            }
        });

        item_title_rl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item_context.isShown()) {
                    item_context.setVisibility(GONE);
                } else {
                    item_context.setVisibility(VISIBLE);
                }
            }
        });


        sankua_yhnr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOnTssxClick.onAutoItemClick(parent,view,position,id);
            }
        });

        item_tssx_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnTssxClick.clickTitleDel();
            }
        });

        sankua_yhnr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                mOnTssxClick.addTextChangedListener(sankua_yhnr.getText().toString().trim());
                sankua_yhnr.setSelection(editable.toString().length());
            }
        });

        sk_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mOnTssxClick.onItemClick(adapterView,view,i,l);
            }
        });


    }

    /**
     * 禁用页面按钮
     * @param disable
     */
    public void  disableView(boolean disable)
    {
        sk_gridview.setEnabled(disable);
        sankua_yhnr.setEnabled(disable);
        disableRadioGroup(disable);
        if (!disable) {
            item_tssx_del.setVisibility(GONE);
        }else{
            item_tssx_del.setVisibility(VISIBLE);
        }

    }

    /**
     * 禁用RadioGroup
     *
     * @param isEnable false 禁用 true 可用
     */
    public void disableRadioGroup(Boolean isEnable) {
        for (int i = 0; i < sankua_rad.getChildCount(); i++) {
            sankua_rad.getChildAt(i).setEnabled(isEnable);
        }

    }


    public void setOnItemClick(onTssxClick click){
        this.mOnTssxClick = click;
    }

    public interface onTssxClick{
        void clickTitleDel();
        void onAutoItemClick(AdapterView<?> parent, View view, int position, long id);
        void addTextChangedListener(String txt);//文字变化监听
        void getDj(String djStr);
        void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
        void delListPhoto(String photoStr);
    }

    //文字提示
    public void setAutoAdapter(AutoCursorAdapter cursorAdapter)
    {
        sankua_yhnr.setAdapter(cursorAdapter);
    }

    //拍照
    public void setPhotoAdapter(TssxPhotoAdapter photoAdapter)
    {
        this.photoAdapter = photoAdapter;
        sk_gridview.setAdapter(photoAdapter);

        this.photoAdapter.setDelItem(new TssxPhotoAdapter.onDelPhotoAdapter() {
            @Override
            public void onDelPhotoAdapterStr(String delPhotoStr) {
                mOnTssxClick.delListPhoto(delPhotoStr);
            }
        });
    }

    public void setNotifyDataSetChanged(List<String> list){
        photoAdapter.setNotifyDataSetChanged(list);

    }

}
