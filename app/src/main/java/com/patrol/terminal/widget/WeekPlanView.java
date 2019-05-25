package com.patrol.terminal.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.EqTower;
import com.patrol.terminal.bean.LineBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeekPlanView extends LinearLayout {
    @BindView(R.id.week_line_name)
    TextView weekLineName;
    @BindView(R.id.week_line_no)
    TextView weekLineNo;
    @BindView(R.id.week_line_level)
    TextView weekLineLevel;
    @BindView(R.id.week_line_type)
    TextView weekLineType;
    @BindView(R.id.week_line_start)
    TextView weekLineStart;
    @BindView(R.id.week_line_end)
    TextView weekLineEnd;
    @BindView(R.id.incontinuity_tower)
    FlowGroupView incontinuityTower;
    @BindView(R.id.add_incontinuity_tower)
    ImageView addIncontinuityTower;

    private Context mContext;
    private List<String> towerList = new ArrayList<>();
    private List<EqTower> towers = new ArrayList<EqTower>();
    private LineBean bean;
    private List<EqTower> eqTowers = new ArrayList<>();
    private final String id;
    private int start;
    private int end;

    public WeekPlanView(Context context, LineBean lineBean, String year, String month) {
        super(context);
        mContext = context;
        bean = lineBean;
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_week_plan, this, true);
        ButterKnife.bind(inflate);
        id = lineBean.getId();
        weekLineName.setText(lineBean.getName());
        weekLineNo.setText(lineBean.getLine_no());
        weekLineLevel.setText(lineBean.getLine_level());
        weekLineType.setText(lineBean.getVoltage_level());
        for (int i = 0; i < towers.size(); i++) {
            EqTower tower = towers.get(i);
            towerList.add(tower.getTowers());
        }
//        getTower(year,month, id);

    }

    @OnClick({R.id.week_line_start, R.id.week_line_end, R.id.add_incontinuity_tower})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.week_line_start:
                showLineStart();
                break;
            case R.id.week_line_end:
                showLineEnd();
                break;
            case R.id.add_incontinuity_tower:
                showIncontinuityTower();
                break;
        }
    }

    //线路类型添加选项框
    private void showLineStart() {// 不联动的多级选项
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                weekLineStart.setText(towerList.get(options1));
                for (int i = 0; i < towers.size(); i++) {
                    EqTower tower = towers.get(i);
                    if (towerList.get(options1).equals(tower.getTowers())){
                        start = i;
                    }
                }
            }
        }).build();
        pvOptions.setPicker(towerList);
        pvOptions.show();
    }

    //不连续杆塔选择框
    private void showIncontinuityTower() {// 不联动的多级选项
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String name= towerList.get(options1);
                addTextView(name);
                for (int i = 0; i <towers.size() ; i++) {
                    EqTower tower = towers.get(i);
                    if (name.equals(tower.getTowers())){
                        eqTowers.add(tower);
                    }
                }
                towerList.remove(options1);
            }
        }).build();
        pvOptions.setPicker(towerList);
        pvOptions.show();
    }

    //线路类型添加选项框
    private void showLineEnd() {// 不联动的多级选项
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                weekLineEnd.setText(towerList.get(options1));
                for (int i = 0; i < towers.size(); i++) {
                    EqTower tower = towers.get(i);
                    if (towerList.get(options1).equals(tower.getTowers())){
                        end = i;
                    }
                }
            }
        }).build();
        pvOptions.setPicker(towerList);
        pvOptions.show();
    }

    /**
     * 动态添加布局
     *
     * @param str
     */
    private void addTextView(String str) {
        TextView child = new TextView(mContext);
        MarginLayoutParams params = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        child.setLayoutParams(params);
        child.setText(str);
        child.setBackgroundResource(R.drawable.tower_tv);
        child.setTextColor(Color.WHITE);
        initEvents(child);//监听
        incontinuityTower.addView(child);
    }

    /**
     * 为每个view 添加点击事件
     */
    private void initEvents(final TextView tv) {
        tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String tower = tv.getText().toString();
                for (int i = 0; i < eqTowers.size(); i++) {
                    if (tower.equals(eqTowers.get(i).getTowers())) {
                        towerList.add(i, tower);
                        eqTowers.remove(i);
                    }
                }
                incontinuityTower.removeView(v);
            }
        });
    }

    public List<String> getLineDetail() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < eqTowers.size(); i++) {
            EqTower tower = eqTowers.get(i);
            String id = tower.getId();
            int i1 = list.indexOf(id);
            if (i1==-1){
                list.add(id);
            }
        }
        int abs = Math.abs(start - end);
        int c=start>end?end:start;
        for (int i = 0; i <abs+1 ; i++) {
            EqTower tower = towers.get(i + c);
            String id = tower.getId();
            int i1 = list.indexOf(id);
            if (i1==-1){
                list.add(id);
            }

        }
        return list;
    }

//    //获取每个班组负责的线路
//    public void getTower(String year,String month,String id) {
//        BaseRequest.getInstance().getService()
//                .getTower(year,month,SPUtil.getDepId(this),id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<EqTower>>(mContext) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<EqTower>> t) throws Exception {
//                        towers = t.getResults();
//
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                    }
//                });
//
//    }
}
