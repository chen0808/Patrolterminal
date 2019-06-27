package com.patrol.terminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.luck.picture.lib.entity.LocalMedia;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.PatrolRecordActivity;
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

public class SpecialAttrFrgment3 extends BaseFragment {

    @BindView(R.id.iv_expand_sankua)
    ImageView ivExpandSankua;
    @BindView(R.id.rl_item1)
    RelativeLayout rlItem1;
    @BindView(R.id.gaosugonglu_iv_add)
    ImageView gaosugongluIvAdd;
    @BindView(R.id.gaosugonglu_rl)
    RelativeLayout gaosugongluRl;
    @BindView(R.id.ns_content_type_guagasugonglu)
    NiceSpinner nsContentTypeGuagasugonglu;
    @BindView(R.id.gridView_guagasugonglu)
    GridView gridViewGuagasugonglu;
    @BindView(R.id.guagasugonglu_item_layout)
    LinearLayout guagasugongluItemLayout;
    @BindView(R.id.gaosutielu_iv_add)
    ImageView gaosutieluIvAdd;
    @BindView(R.id.gaosutielu_rl)
    RelativeLayout gaosutieluRl;
    @BindView(R.id.ns_content_type_gaosutielu)
    NiceSpinner nsContentTypeGaosutielu;
    @BindView(R.id.gridView_gaosutielu)
    GridView gridViewGaosutielu;
    @BindView(R.id.guagasutielu_item_layout)
    LinearLayout guagasutieluItemLayout;
    @BindView(R.id.putongtielu_iv_add)
    ImageView putongtieluIvAdd;
    @BindView(R.id.putongtielu_rl)
    RelativeLayout putongtieluRl;
    @BindView(R.id.ns_content_type_guaputongtielu)
    NiceSpinner nsContentTypeGuaputongtielu;
    @BindView(R.id.gridView_guaputongtielu)
    GridView gridViewGuaputongtielu;
    @BindView(R.id.guaputongtielu_item_layout)
    LinearLayout guaputongtieluItemLayout;
    @BindView(R.id.sankua_all_info_ll)
    LinearLayout sankuaAllInfoLl;
    @BindView(R.id.iv_expand_liufang)
    ImageView ivExpandLiufang;
    @BindView(R.id.fagnwaipo_iv_add)
    ImageView fagnwaipoIvAdd;
    @BindView(R.id.fangwaipo_rl)
    RelativeLayout fangwaipoRl;
    @BindView(R.id.et_content_fangwaipo)
    EditText etContentFangwaipo;
    @BindView(R.id.ns_content_type_fangwaipo)
    NiceSpinner nsContentTypeFangwaipo;
    @BindView(R.id.gridView_fangwaipo)
    GridView gridViewFangwaipo;
    @BindView(R.id.iv_save2)
    ImageView ivSave2;
    @BindView(R.id.fangwaipo_item_ll)
    LinearLayout fangwaipoItemLl;
    @BindView(R.id.fangshanhong_iv_add)
    ImageView fangshanhongIvAdd;
    @BindView(R.id.fangshanhong_rl)
    RelativeLayout fangshanhongRl;
    @BindView(R.id.et_content2_fangshanhong)
    EditText etContent2Fangshanhong;
    @BindView(R.id.ns_content_type2_fagnshanhong)
    NiceSpinner nsContentType2Fagnshanhong;
    @BindView(R.id.gridView2_fagnshanhong)
    GridView gridView2Fagnshanhong;
    @BindView(R.id.iv_save2_fagnshanhong)
    ImageView ivSave2Fagnshanhong;
    @BindView(R.id.fangshanhong_item_ll)
    LinearLayout fangshanhongItemLl;
    @BindView(R.id.fangniao_iv_add)
    ImageView fangniaoIvAdd;
    @BindView(R.id.fangniao_rl)
    RelativeLayout fangniaoRl;
    @BindView(R.id.et_content2_fangniao)
    EditText etContent2Fangniao;
    @BindView(R.id.ns_content_type2_fagnniao)
    NiceSpinner nsContentType2Fagnniao;
    @BindView(R.id.gridView2_fagnniao)
    GridView gridView2Fagnniao;
    @BindView(R.id.iv_save2_fagnniao)
    ImageView ivSave2Fagnniao;
    @BindView(R.id.fangniao_item_ll)
    LinearLayout fangniaoItemLl;
    @BindView(R.id.fanglei_iv_add)
    ImageView fangleiIvAdd;
    @BindView(R.id.fanglei_rl)
    RelativeLayout fangleiRl;
    @BindView(R.id.et_content2_fanglei)
    EditText etContent2Fanglei;
    @BindView(R.id.ns_content_type2_fanglei)
    NiceSpinner nsContentType2Fanglei;
    @BindView(R.id.gridView2_fanglei)
    GridView gridView2Fanglei;
    @BindView(R.id.iv_save2_fanglei)
    ImageView ivSave2Fanglei;
    @BindView(R.id.fanglei_item_ll)
    LinearLayout fangleiItemLl;
    @BindView(R.id.fangfeng_iv_add)
    ImageView fangfengIvAdd;
    @BindView(R.id.fangfeng_rl)
    RelativeLayout fangfengRl;
    @BindView(R.id.et_content2_fangfeng)
    EditText etContent2Fangfeng;
    @BindView(R.id.ns_content_type2_fangfeng)
    NiceSpinner nsContentType2Fangfeng;
    @BindView(R.id.gridView2_fangfeng)
    GridView gridView2Fangfeng;
    @BindView(R.id.iv_save2_fangfeng)
    ImageView ivSave2Fangfeng;
    @BindView(R.id.fangfeng_item_ll)
    LinearLayout fangfengItemLl;
    @BindView(R.id.fangdizai_iv_add)
    ImageView fangdizaiIvAdd;
    @BindView(R.id.fangdizai_rl)
    RelativeLayout fangdizaiRl;
    @BindView(R.id.liufang_all_info_ll)
    LinearLayout liufangAllInfoLl;
    @BindView(R.id.content2)
    EditText content2;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.et_content2_fangdizai)
    EditText etContent2Fangdizai;
    @BindView(R.id.ns_content_type2_fangdizai)
    NiceSpinner nsContentType2Fangdizai;
    @BindView(R.id.gridView2_fangdizai)
    GridView gridView2Fangdizai;
    @BindView(R.id.iv_save2_fangdizai)
    ImageView ivSave2Fangdizai;
    @BindView(R.id.fangdizai_item_ll)
    LinearLayout fangdizaiItemLl;
    @BindView(R.id.tv_diver_way)
    AutoCompleteTextView tvDiverWay;

    private String[] nsType = {"一般（III类）", "重大（II类）", "紧急（I类）"};
    private GridViewAdapter2 mGridViewAddImgAdapter;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private List<LocalMedia> localMedia;
    private boolean isExpand1 = false;
    private boolean isExpand2 = false;


    boolean isSankuaOpen = false;   //三跨
    boolean isLiufangOpen = false;  //六防
    boolean isGsglOpen = false;     //跨高速公路
    boolean isGsTlOpen = false;     //跨高速铁路
    boolean isPttlOpen = false;     //跨普通铁路
    boolean isFwpOpen = false;      //防外破
    boolean isFswOpen = false;      //防山洪
    boolean isFnOpen = false;       //防鸟
    boolean isFlOpen = false;       //防雷
    boolean isFfOpen = false;       //防风
    boolean isFdzOpen = false;      //防地灾

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_attr3, null);
        return view;
    }

    @Override
    protected void initData() {
        nsContentTypeGuagasugonglu.attachDataSource(Arrays.asList(nsType));
        nsContentTypeGaosutielu.attachDataSource(Arrays.asList(nsType));
        nsContentTypeGuaputongtielu.attachDataSource(Arrays.asList(nsType));
        nsContentTypeFangwaipo.attachDataSource(Arrays.asList(nsType));
        nsContentType2Fagnshanhong.attachDataSource(Arrays.asList(nsType));
        nsContentType2Fagnniao.attachDataSource(Arrays.asList(nsType));
        nsContentType2Fanglei.attachDataSource(Arrays.asList(nsType));
        nsContentType2Fangfeng.attachDataSource(Arrays.asList(nsType));
        nsContentType2Fangdizai.attachDataSource(Arrays.asList(nsType));

        initGridView(gridViewGuagasugonglu, 111);
        initGridView(gridViewGaosutielu, 222);
        initGridView(gridViewGuaputongtielu, 333);

        initGridView(gridViewFangwaipo, 444);
        initGridView(gridView2Fagnshanhong, 555);
        initGridView(gridView2Fagnniao, 666);
        initGridView(gridView2Fanglei, 777);
        initGridView(gridView2Fangfeng, 888);
        initGridView(gridView2Fangdizai, 999);

        String data[] = getResources().getStringArray(R.array.auto_textview_contents);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, data);
        tvDiverWay.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 111:
                break;

            case 222:
                break;

            case 333:
                break;

            case 444:
                break;

            case 555:
                break;

            case 666:
                break;

            case 777:
                break;

            case 888:
                break;

            case 999:
                break;

            default:
                break;

        }
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
//        if (Constant.isContent) {
//            rlItem1.setVisibility(View.VISIBLE);
//            rl1.setVisibility(View.VISIBLE);
//        }
        localMedia = ((PatrolRecordActivity) getActivity()).getPics();
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

   /* @OnClick({R.id.iv_expand, R.id.iv_expand2, R.id.iv_add, R.id.iv_add2, R.id.btn_add, R.id.iv_save2})
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
    }*/


    @OnClick({R.id.iv_expand_sankua, R.id.iv_expand_liufang, R.id.gaosugonglu_iv_add, R.id.gaosutielu_iv_add, R.id.putongtielu_iv_add, R.id.fagnwaipo_iv_add, R.id.fangshanhong_iv_add, R.id.fangniao_iv_add, R.id.fanglei_iv_add, R.id.fangfeng_iv_add, R.id.fangdizai_iv_add, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_expand_sankua:
                if (!isSankuaOpen) {  //三跨按钮未打开
                    sankuaAllInfoLl.setVisibility(View.VISIBLE);
                    isSankuaOpen = true;
                } else {
                    sankuaAllInfoLl.setVisibility(View.GONE);
                    isSankuaOpen = false;
                }

                break;

            case R.id.iv_expand_liufang:
                if (!isLiufangOpen) {  //六防按钮未打开
                    liufangAllInfoLl.setVisibility(View.VISIBLE);
                    isLiufangOpen = true;
                } else {
                    liufangAllInfoLl.setVisibility(View.GONE);
                    isLiufangOpen = false;
                }

                break;

            case R.id.gaosugonglu_iv_add:
                if (!isGsglOpen) {  //三跨下，跨高速公路
                    guagasugongluItemLayout.setVisibility(View.VISIBLE);
                    isGsglOpen = true;
                } else {
                    guagasugongluItemLayout.setVisibility(View.GONE);
                    isGsglOpen = false;
                }

                break;
            case R.id.gaosutielu_iv_add:
                if (!isGsTlOpen) {  //三跨下，跨高速铁路
                    guagasutieluItemLayout.setVisibility(View.VISIBLE);
                    isGsTlOpen = true;
                } else {
                    guagasutieluItemLayout.setVisibility(View.GONE);
                    isGsTlOpen = false;
                }
                break;
            case R.id.putongtielu_iv_add:
                if (!isPttlOpen) {  //三跨下，跨普通铁路
                    guaputongtieluItemLayout.setVisibility(View.VISIBLE);
                    isPttlOpen = true;
                } else {
                    guaputongtieluItemLayout.setVisibility(View.GONE);
                    isPttlOpen = false;
                }
                break;
            case R.id.fagnwaipo_iv_add:
                if (!isFwpOpen) {  //六防下，防外破
                    fangwaipoItemLl.setVisibility(View.VISIBLE);
                    isFwpOpen = true;
                } else {
                    fangwaipoItemLl.setVisibility(View.GONE);
                    isFwpOpen = false;
                }
                break;
            case R.id.fangshanhong_iv_add:
                if (!isFswOpen) {  //六防下，防山洪
                    fangshanhongItemLl.setVisibility(View.VISIBLE);
                    isFswOpen = true;
                } else {
                    fangshanhongItemLl.setVisibility(View.GONE);
                    isFswOpen = false;
                }
                break;
            case R.id.fangniao_iv_add:
                if (!isFnOpen) {  //六防下，防鸟
                    fangniaoItemLl.setVisibility(View.VISIBLE);
                    isFnOpen = true;
                } else {
                    fangniaoItemLl.setVisibility(View.GONE);
                    isFnOpen = false;
                }
                break;
            case R.id.fanglei_iv_add:
                if (!isFlOpen) {  //六防下，防雷
                    fangleiItemLl.setVisibility(View.VISIBLE);
                    isFlOpen = true;
                } else {
                    fangleiItemLl.setVisibility(View.GONE);
                    isFlOpen = false;
                }
                break;
            case R.id.fangfeng_iv_add:
                if (!isFfOpen) {  //六防下，防雷
                    fangfengItemLl.setVisibility(View.VISIBLE);
                    isFfOpen = true;
                } else {
                    fangfengItemLl.setVisibility(View.GONE);
                    isFfOpen = false;
                }
                break;
            case R.id.fangdizai_iv_add:
                if (!isFdzOpen) {  //六防下，防雷
                    fangdizaiItemLl.setVisibility(View.VISIBLE);
                    isFdzOpen = true;
                } else {
                    fangdizaiItemLl.setVisibility(View.GONE);
                    isFdzOpen = false;
                }
                break;
            case R.id.btn_add:
                String lineName = ((PatrolRecordActivity) getActivity()).getLineName();
                Intent intent = new Intent(getActivity(), TestActivity.class);
                intent.putExtra("line_name", lineName);
                getActivity().startActivity(intent);
                break;
        }
    }
}
