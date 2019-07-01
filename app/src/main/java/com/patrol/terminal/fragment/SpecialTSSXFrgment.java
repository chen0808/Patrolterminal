package com.patrol.terminal.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.adapter.TssxAddAdapter;
import com.patrol.terminal.adapter.TssxEditAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.bean.TSSXBean;
import com.patrol.terminal.sqlite.DefactContentDBHelper;
import com.patrol.terminal.sqlite.MyOpenhelper;
import com.patrol.terminal.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 特殊属性
 */
public class SpecialTSSXFrgment extends BaseFragment {


    @BindView(R.id.tssx_sankua)
    Button tssx_sankua;
    @BindView(R.id.tssx_liufang)
    Button tssx_liufang;
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

    private MyFragmentPagerAdapter pagerAdapter;
    private View view;
    /**特殊属性列表*/
    private List<TSSXBean> tssxBeanList = new ArrayList<>();
    /**添加的特殊属性列表*/
    private List<TSSXBean> addTssxList = new ArrayList<>();
    /**添加特殊屬性adapter*/
    private TssxAddAdapter tssxAddAdapter;

    private SpecialTSSXSKFrgment skFragment;
    private SpecialTSSXLFFrgment lfFragment;

    private TssxEditAdapter skEditAdapter;
    private TssxEditAdapter lfEditAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_xs_tssx, null);

        return view;
    }

    @Override
    protected void initData() {
//        List<Fragment> fragmentList = new ArrayList<>();
//        skFragment = new SpecialTSSXSKFrgment();
//        lfFragment = new SpecialTSSXLFFrgment();
//        fragmentList.add(skFragment); //三跨
//        fragmentList.add(lfFragment);  //六防
//        pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
//        xs_tssx_pager.setAdapter(pagerAdapter);
//        xs_tssx_pager.setCurrentItem(0);

        skEditAdapter = new TssxEditAdapter(getActivity());
        lfEditAdapter = new TssxEditAdapter(getActivity());
        xs_tssx_lv.setAdapter(skEditAdapter);



        intTSSX();
        initClick();

    }


    public void initClick() {

        xs_tssx_pager.setOffscreenPageLimit(1);
        xs_tssx_pager.setNoScroll(true);
        tssx_sankua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xs_tssx_pager.setCurrentItem(0);
            }
        });
        tssx_liufang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xs_tssx_pager.setCurrentItem(1);
            }
        });
        //属性选择
        tssx_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rl_add.getVisibility() == View.VISIBLE){
                    rl_add.setVisibility(View.GONE);
                }else
                rl_add.setVisibility(View.VISIBLE);
                tssxAddAdapter.setData(tssxBeanList);
//                xs_tssx_pager.setVisibility(View.GONE);
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
                Log.e("保存按钮","添加条数"+addTssxList.size());

                //刷新fragment 页面数据
//                skFragment.setTssxAdapter(addTssxList);
//                lfFragment.setTssxAdapter(addTssxList);

//                skEditAdapter.setData(addTssxList,cursorAdapter,cursor);
                skEditAdapter.setData(addTssxList,null,null);
                xs_tssx_lv.setAdapter(skEditAdapter);
                xs_tssx_lv.setVisibility(View.VISIBLE);
            }
        });

    }


    public void intTSSX() {
        tssxBeanList = new ArrayList<TSSXBean>();

        tssxBeanList.add(new TSSXBean("B8B48592F5FE465DA36E6A207904A327", "C317315E61BB44B4B2B4ECD8A0CC7419", "通航河流"));
        tssxBeanList.add(new TSSXBean("274EE1B7A4F444F9AD8F218791BA2740", "807FEEA3D127451AAE44D00D527B882F", "重要输电通道"));
        tssxBeanList.add(new TSSXBean("EC3507FA707F431EAB2829888F7E368F", "CFC9004FE3784E4EBD07A9638A3FF3C9", "公墓区"));
        tssxBeanList.add(new TSSXBean("23EB52FFB8E648BD936CBA8CC1AEDE21", "0D9A1042B0954BC28827C40F27FF2D06", "高大山区"));
        tssxBeanList.add(new TSSXBean("FB76395583E34DD5833E94FB75BD1DFF", "D71CFDD7A45B42688BE85747B8F25A86", "高大机械施工"));
        tssxBeanList.add(new TSSXBean("B07ACBE745DD471784D1783445BEA879", "51CCF6004B864B539BAC2C36ACF8BF86", "防覆冰"));
//        tssxBeanList.add(new TSSXBean("","",""));
        tssxBeanList.add(new TSSXBean("B8B48592F5FE465DA36E6A207904A327", "C317315E61BB44B4B2B4ECD8A0CC7419", "通航河流"));
        tssxBeanList.add(new TSSXBean("274EE1B7A4F444F9AD8F218791BA2740", "807FEEA3D127451AAE44D00D527B882F", "重要输电通道"));
        tssxBeanList.add(new TSSXBean("EC3507FA707F431EAB2829888F7E368F", "CFC9004FE3784E4EBD07A9638A3FF3C9", "公墓区"));
        tssxBeanList.add(new TSSXBean("23EB52FFB8E648BD936CBA8CC1AEDE21", "0D9A1042B0954BC28827C40F27FF2D06", "高大山区"));
        tssxBeanList.add(new TSSXBean("FB76395583E34DD5833E94FB75BD1DFF", "D71CFDD7A45B42688BE85747B8F25A86", "高大机械施工"));
        tssxBeanList.add(new TSSXBean("B07ACBE745DD471784D1783445BEA879", "51CCF6004B864B539BAC2C36ACF8BF86", "防覆冰"));
        tssxBeanList.add(new TSSXBean("B8B48592F5FE465DA36E6A207904A327", "C317315E61BB44B4B2B4ECD8A0CC7419", "通航河流"));
        tssxBeanList.add(new TSSXBean("274EE1B7A4F444F9AD8F218791BA2740", "807FEEA3D127451AAE44D00D527B882F", "重要输电通道"));
        tssxBeanList.add(new TSSXBean("EC3507FA707F431EAB2829888F7E368F", "CFC9004FE3784E4EBD07A9638A3FF3C9", "公墓区"));
        tssxBeanList.add(new TSSXBean("23EB52FFB8E648BD936CBA8CC1AEDE21", "0D9A1042B0954BC28827C40F27FF2D06", "高大山区"));
        tssxBeanList.add(new TSSXBean("FB76395583E34DD5833E94FB75BD1DFF", "D71CFDD7A45B42688BE85747B8F25A86", "高大机械施工"));
        tssxBeanList.add(new TSSXBean("B07ACBE745DD471784D1783445BEA879", "51CCF6004B864B539BAC2C36ACF8BF86", "防覆冰"));
        tssxBeanList.add(new TSSXBean("B8B48592F5FE465DA36E6A207904A327", "C317315E61BB44B4B2B4ECD8A0CC7419", "通航河流"));
        tssxBeanList.add(new TSSXBean("274EE1B7A4F444F9AD8F218791BA2740", "807FEEA3D127451AAE44D00D527B882F", "重要输电通道"));
        tssxBeanList.add(new TSSXBean("EC3507FA707F431EAB2829888F7E368F", "CFC9004FE3784E4EBD07A9638A3FF3C9", "公墓区"));
        tssxBeanList.add(new TSSXBean("23EB52FFB8E648BD936CBA8CC1AEDE21", "0D9A1042B0954BC28827C40F27FF2D06", "高大山区"));
        tssxBeanList.add(new TSSXBean("FB76395583E34DD5833E94FB75BD1DFF", "D71CFDD7A45B42688BE85747B8F25A86", "高大机械施工"));
        tssxBeanList.add(new TSSXBean("B07ACBE745DD471784D1783445BEA879", "51CCF6004B864B539BAC2C36ACF8BF86", "防覆冰"));
        tssxBeanList.add(new TSSXBean("B8B48592F5FE465DA36E6A207904A327", "C317315E61BB44B4B2B4ECD8A0CC7419", "通航河流"));
        tssxBeanList.add(new TSSXBean("274EE1B7A4F444F9AD8F218791BA2740", "807FEEA3D127451AAE44D00D527B882F", "重要输电通道"));
        tssxBeanList.add(new TSSXBean("EC3507FA707F431EAB2829888F7E368F", "CFC9004FE3784E4EBD07A9638A3FF3C9", "公墓区"));
        tssxBeanList.add(new TSSXBean("23EB52FFB8E648BD936CBA8CC1AEDE21", "0D9A1042B0954BC28827C40F27FF2D06", "高大山区"));
        tssxBeanList.add(new TSSXBean("FB76395583E34DD5833E94FB75BD1DFF", "D71CFDD7A45B42688BE85747B8F25A86", "高大机械施工"));
        tssxBeanList.add(new TSSXBean("B07ACBE745DD471784D1783445BEA879", "51CCF6004B864B539BAC2C36ACF8BF86", "防覆冰"));
        tssxBeanList.add(new TSSXBean("B8B48592F5FE465DA36E6A207904A327", "C317315E61BB44B4B2B4ECD8A0CC7419", "通航河流"));
        tssxBeanList.add(new TSSXBean("274EE1B7A4F444F9AD8F218791BA2740", "807FEEA3D127451AAE44D00D527B882F", "重要输电通道"));
        tssxBeanList.add(new TSSXBean("EC3507FA707F431EAB2829888F7E368F", "CFC9004FE3784E4EBD07A9638A3FF3C9", "公墓区"));
        tssxBeanList.add(new TSSXBean("23EB52FFB8E648BD936CBA8CC1AEDE21", "0D9A1042B0954BC28827C40F27FF2D06", "高大山区"));
        tssxBeanList.add(new TSSXBean("FB76395583E34DD5833E94FB75BD1DFF", "D71CFDD7A45B42688BE85747B8F25A86", "高大机械施工"));
        tssxBeanList.add(new TSSXBean("B07ACBE745DD471784D1783445BEA879", "51CCF6004B864B539BAC2C36ACF8BF86", "防覆冰"));
        tssxBeanList.add(new TSSXBean("B8B48592F5FE465DA36E6A207904A327", "C317315E61BB44B4B2B4ECD8A0CC7419", "通航河流"));
        tssxBeanList.add(new TSSXBean("274EE1B7A4F444F9AD8F218791BA2740", "807FEEA3D127451AAE44D00D527B882F", "重要输电通道"));
        tssxBeanList.add(new TSSXBean("EC3507FA707F431EAB2829888F7E368F", "CFC9004FE3784E4EBD07A9638A3FF3C9", "公墓区"));
        tssxBeanList.add(new TSSXBean("23EB52FFB8E648BD936CBA8CC1AEDE21", "0D9A1042B0954BC28827C40F27FF2D06", "高大山区"));
        tssxBeanList.add(new TSSXBean("FB76395583E34DD5833E94FB75BD1DFF", "D71CFDD7A45B42688BE85747B8F25A86", "高大机械施工"));
        tssxBeanList.add(new TSSXBean("B07ACBE745DD471784D1783445BEA879", "51CCF6004B864B539BAC2C36ACF8BF86", "防覆冰"));
        tssxBeanList.add(new TSSXBean("B8B48592F5FE465DA36E6A207904A327", "C317315E61BB44B4B2B4ECD8A0CC7419", "通航河流"));
        tssxBeanList.add(new TSSXBean("274EE1B7A4F444F9AD8F218791BA2740", "807FEEA3D127451AAE44D00D527B882F", "重要输电通道"));
        tssxBeanList.add(new TSSXBean("EC3507FA707F431EAB2829888F7E368F", "CFC9004FE3784E4EBD07A9638A3FF3C9", "公墓区"));
        tssxBeanList.add(new TSSXBean("23EB52FFB8E648BD936CBA8CC1AEDE21", "0D9A1042B0954BC28827C40F27FF2D06", "高大山区"));
        tssxBeanList.add(new TSSXBean("FB76395583E34DD5833E94FB75BD1DFF", "D71CFDD7A45B42688BE85747B8F25A86", "高大机械施工"));
        tssxBeanList.add(new TSSXBean("B07ACBE745DD471784D1783445BEA879", "51CCF6004B864B539BAC2C36ACF8BF86", "防覆冰"));
        tssxBeanList.add(new TSSXBean("B8B48592F5FE465DA36E6A207904A327", "C317315E61BB44B4B2B4ECD8A0CC7419", "通航河流"));
        tssxBeanList.add(new TSSXBean("274EE1B7A4F444F9AD8F218791BA2740", "807FEEA3D127451AAE44D00D527B882F", "重要输电通道"));
        tssxBeanList.add(new TSSXBean("EC3507FA707F431EAB2829888F7E368F", "CFC9004FE3784E4EBD07A9638A3FF3C9", "公墓区"));
        tssxBeanList.add(new TSSXBean("23EB52FFB8E648BD936CBA8CC1AEDE21", "0D9A1042B0954BC28827C40F27FF2D06", "高大山区"));
        tssxBeanList.add(new TSSXBean("FB76395583E34DD5833E94FB75BD1DFF", "D71CFDD7A45B42688BE85747B8F25A86", "高大机械施工"));
        tssxBeanList.add(new TSSXBean("B07ACBE745DD471784D1783445BEA879", "51CCF6004B864B539BAC2C36ACF8BF86", "防覆冰"));
        tssxBeanList.add(new TSSXBean("B8B48592F5FE465DA36E6A207904A327", "C317315E61BB44B4B2B4ECD8A0CC7419", "通航河流"));
        tssxBeanList.add(new TSSXBean("274EE1B7A4F444F9AD8F218791BA2740", "807FEEA3D127451AAE44D00D527B882F", "重要输电通道"));
        tssxBeanList.add(new TSSXBean("EC3507FA707F431EAB2829888F7E368F", "CFC9004FE3784E4EBD07A9638A3FF3C9", "公墓区"));
        tssxBeanList.add(new TSSXBean("23EB52FFB8E648BD936CBA8CC1AEDE21", "0D9A1042B0954BC28827C40F27FF2D06", "高大山区"));
        tssxBeanList.add(new TSSXBean("FB76395583E34DD5833E94FB75BD1DFF", "D71CFDD7A45B42688BE85747B8F25A86", "高大机械施工"));
        tssxBeanList.add(new TSSXBean("B07ACBE745DD471784D1783445BEA879", "51CCF6004B864B539BAC2C36ACF8BF86", "防覆冰"));

        tssxAddAdapter = new TssxAddAdapter(getActivity(), tssxBeanList);
        grid_addtssx.setAdapter(tssxAddAdapter);


    }

//    DefactContentDBHelper defactContentDBHelper = new DefactContentDBHelper(getActivity());
//    Cursor cursor = defactContentDBHelper.queryAll();
//    CursorAdapter cursorAdapter = new CursorAdapter(getActivity(), cursor) {
//
//        @Override
//        public View newView(Context context, Cursor cursor, ViewGroup parent) {
//            return LayoutInflater.from(context).inflate(R.layout.simple_defact_tv_content_item_layout, parent, false);
//        }
//
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//            TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
//            int contentColumnIndex = cursor.getColumnIndex(MyOpenhelper.DefactTvColumns.CONTENT);
//            String content = cursor.getString(contentColumnIndex);
//            contentTv.setText(content);
//        }
//
//        @Override
//        public String convertToString(Cursor cursor) {
//            return cursor.getString(cursor.getColumnIndex(MyOpenhelper.DefactTvColumns.CONTENT));
//        }
//
//        @Override
//        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
//            if (getFilterQueryProvider() != null) {
//                return getFilterQueryProvider().runQuery(constraint);
//            }
//
//            DefactContentDBHelper defactContentDBHelper = new DefactContentDBHelper(getActivity());
//            Cursor cursor = defactContentDBHelper.queryFliter(String.valueOf(constraint));
//            Log.w("linmeng", "cursor.getCount() filter:" + cursor.getCount());
//            return cursor;
//        }
//    };


}
