package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddMonthPlanActivity;
import com.patrol.terminal.activity.ChangePlanActivity;
import com.patrol.terminal.activity.CommitDefectActivity;
import com.patrol.terminal.activity.LineCheckActivity;
import com.patrol.terminal.bean.MonthLevel1;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.ArrayList;
import java.util.List;

public class MonthPlanListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    private String state;
    private String mJobType;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    public MonthPlanListAdapter(List<MultiItemEntity> data, String state, String mJobType) {
        super(data);
        addItemType(TYPE_1, R.layout.item_patrol_content_1);
        addItemType(TYPE_2, R.layout.fragment_plan_item);
        this.state = state;
        this.mJobType = mJobType;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MultiItemEntity item) {
        switch (viewHolder.getItemViewType()) {
            case TYPE_1:
                int pos = viewHolder.getAdapterPosition();
                MonthLevel1 item1 = (MonthLevel1) item;
                viewHolder.setText(R.id.tv_title, item1.getTitle()).setImageResource(R.id.iv_expand, item1.isExpanded() ? R.mipmap.next : R.mipmap.btn_down);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (item1.isExpanded()) {
                            collapse(pos);
                        } else {
                            for (int i = 0; i < mData.size(); i++) {
                                collapse(i);
                            }
                            expand(pos);
//      }
                        }
                    }
                });
                break;
            case TYPE_2:
                MonthPlanBean monthPlanBean = (MonthPlanBean) item;
                if (monthPlanBean.getMonth_id() != null) {
                    if (monthPlanBean.getFull_plan().contains("巡")) {
                        viewHolder.setText(R.id.item_plan_date_tv, "巡");
                    } else if (monthPlanBean.getFull_plan().contains("特")) {
                        viewHolder.setText(R.id.item_plan_date_tv, "特");
                    } else {
                        viewHolder.setText(R.id.item_plan_date_tv, "检");
                    }
                    if (state != null) {
                        viewHolder.setVisible(R.id.plan_to_change, false);
                    } else {
                        if ("0".equals(monthPlanBean.getAudit_status())) {
                            viewHolder.setVisible(R.id.plan_to_change, true);
                        } else {
                            viewHolder.setVisible(R.id.plan_to_change, false);
                        }

                    }
                    viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_mon_bg);
                    viewHolder.setVisible(R.id.item_line_state, false);
                    HorizontalLineView horizontalLineView = viewHolder.getView(R.id.item_plan_status);
                    viewHolder.setText(R.id.item_line_status, StringUtil.getYXBstate(monthPlanBean.getAudit_status()));
                    horizontalLineView.setStatus(monthPlanBean.getAudit_status());
                    horizontalLineView.setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.item_plan_device_name, monthPlanBean.getLine_name())
                            .setText(R.id.item_plan_content, "类型 : " + monthPlanBean.getFull_plan());
                    viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, AddMonthPlanActivity.class);
                            intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
                            intent.putExtra("month_id", monthPlanBean.getMonth_id());
                            intent.putExtra("line_name", monthPlanBean.getLine_name());
                            intent.putExtra("year", monthPlanBean.getYear() + "");
                            intent.putExtra("month", monthPlanBean.getMonth() + "");
                            intent.putExtra("type", monthPlanBean.getFull_plan());
                            mContext.startActivity(intent);
                        }
                    });
                } else {
                    viewHolder.setText(R.id.item_plan_date_tv, "保")
                            .setVisible(R.id.plan_to_change, true);
                    viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, LineCheckActivity.class);
                            intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
                            intent.putExtra("id", monthPlanBean.getId());
                            intent.putExtra("year", monthPlanBean.getYear());
                            intent.putExtra("month", monthPlanBean.getMonth());
                            mContext.startActivity(intent);
                        }
                    });
                    View view = viewHolder.getView(R.id.item_plan_status);
                    view.setVisibility(View.GONE);
                    viewHolder.setText(R.id.item_plan_device_name, monthPlanBean.getRepair_content())
                            .setText(R.id.item_plan_content, "停电区域 : " + monthPlanBean.getBlackout_range())
                            .setText(R.id.item_line_status, "停电时间 : " + monthPlanBean.getStart_time() + " - " + monthPlanBean.getEnd_time());
                    ;
                }
                break;
        }
    }

}
