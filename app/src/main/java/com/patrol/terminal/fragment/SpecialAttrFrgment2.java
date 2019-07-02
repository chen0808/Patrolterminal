package com.patrol.terminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.luck.picture.lib.entity.LocalMedia;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.PlusImageActivity;
import com.patrol.terminal.activity.TestActivity;
import com.patrol.terminal.adapter.GridViewAdapter2;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.PictureSelectorConfig;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SpecialAttrFrgment2 extends BaseFragment {
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.ns_content_type)
    NiceSpinner nsContentType;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.iv_expand2)
    ImageView ivExpand2;
    @BindView(R.id.iv_add2)
    ImageView ivAdd2;
    @BindView(R.id.ns_content_type2)
    NiceSpinner nsContentType2;
    @BindView(R.id.gridView2)
    GridView gridView2;
    @BindView(R.id.ll_content2)
    LinearLayout llContent2;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.iv_save2)
    ImageView ivSave2;
    @BindView(R.id.et_content2)
    EditText etContent2;
    @BindView(R.id.content2)
    EditText content2;
    @BindView(R.id.rl_1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl_item1)
    RelativeLayout rlItem1;
    private String[] nsType = {"一般（III类）", "重大（II类）", "紧急（I类）"};
    private GridViewAdapter2 mGridViewAddImgAdapter;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private List<LocalMedia> localMedia;
    private boolean isExpand1 = false;
    private boolean isExpand2 = false;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_attr2, null);
        return view;
    }

    @Override
    protected void initData() {
        nsContentType.attachDataSource(Arrays.asList(nsType));
        nsContentType2.attachDataSource(Arrays.asList(nsType));
        initGridView(gridView, 111);
        initGridView(gridView2, 222);
    }

    private void initGridView(GridView gridView, int code) {
        mGridViewAddImgAdapter = new GridViewAdapter2(getActivity(), mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (mPicList.size() == Constant.MAX_SELECT_PIC_NUM) {
                        //最多添加5张图片
                        viewPluImg(position, code);
                    } else {
                        //添加凭证图片
                        selectPic(Constant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position, code);
                }
            }
        });
    }

    //查看大图
    private void viewPluImg(int position, int code) {
        Intent intent = new Intent(getActivity(), PlusImageActivity.class);
        intent.putStringArrayListExtra(Constant.IMG_LIST, mPicList);
        intent.putExtra(Constant.POSITION, position);
        startActivityForResult(intent, code);
    }

    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(getActivity(), maxTotal);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constant.isContent) {
            rlItem1.setVisibility(View.VISIBLE);
            rl1.setVisibility(View.VISIBLE);
        }
//        localMedia = ((PatrolRecordActivity) getActivity()).getPics();
        if (localMedia != null) {
            refreshAdapter(localMedia);
        }
    }

    // 处理选择的照片的地址
    public void refreshAdapter(List<LocalMedia> picList) {
        mPicList.clear();
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick({R.id.iv_expand, R.id.iv_expand2, R.id.iv_add, R.id.iv_add2, R.id.btn_add, R.id.iv_save2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_expand:
                isExpand1 = !isExpand1;
                if (isExpand1) {
                    ivExpand.setImageResource(R.mipmap.next);
                    rl1.setVisibility(View.GONE);
                    llContent.setVisibility(View.GONE);
                } else {
                    ivExpand.setImageResource(R.mipmap.btn_down);
                    rl1.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_expand2:
                isExpand2 = !isExpand2;
                if (isExpand2) {
                    ivExpand2.setImageResource(R.mipmap.next);
                    rl2.setVisibility(View.GONE);
                    llContent2.setVisibility(View.GONE);
                } else {
                    ivExpand2.setImageResource(R.mipmap.btn_down);
                    rl2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_add:
                if (llContent.getVisibility() == View.VISIBLE) {
                    llContent.setVisibility(View.GONE);
                } else {
                    llContent.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_add2:
                if (llContent2.getVisibility() == View.VISIBLE) {
                    llContent2.setVisibility(View.GONE);
                } else {
                    llContent2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_add:
                getActivity().startActivity(new Intent(getActivity(), TestActivity.class));
                break;
            case R.id.iv_save2:
//                if (mPicList.size() < 1) {
//                    Toast.makeText(getActivity(), "照片不能为空", Toast.LENGTH_SHORT).show();
//                } else {
                llContent2.setVisibility(View.GONE);
                content2.setText(etContent2.getText());
                content2.setVisibility(View.VISIBLE);
//                }
                break;
        }
    }
}
