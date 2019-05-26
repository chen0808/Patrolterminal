package com.patrol.terminal.overhaul;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.OverPlanReqBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.widget.CancelOrOkDialog;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OverhaulWeekTaskAdapter extends BaseQuickAdapter<OverhaulMonthBean, BaseViewHolder> {

     private String year,month;
     private int mFromType;
     private int mIsTodoTYpe = 0;

    public OverhaulWeekTaskAdapter(int layoutResId, @Nullable List<OverhaulMonthBean> data, String year, String month) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
    }
    public OverhaulWeekTaskAdapter(int layoutResId, @Nullable List<OverhaulMonthBean> data, int fromType) {
        super(layoutResId, data);
        this.year=year;
        this.month=month;
        this.mFromType = fromType;
    }

    public void setIsTodoType (int isTodoTYpe) {
        this.mIsTodoTYpe = isTodoTYpe;
    }



    @Override
    protected void convert(BaseViewHolder viewHolder, OverhaulMonthBean item) {
        viewHolder.setText(R.id.item_plan_date_tv, "周");

        if (mFromType == 2) {  //检修待办进来
            viewHolder.setText(R.id.plan_to_change, "审核");

            if (mIsTodoTYpe == 1) {  //已办
                viewHolder.setVisible(R.id.plan_to_change, false);
            }else {                   //待办
                viewHolder.setVisible(R.id.plan_to_change, true);
            }

            viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //弹框提交审核
                    CancelOrOkDialog dialog = new CancelOrOkDialog(mContext, "处理审核", "不同意", "同意") {
                        @Override
                        public void ok() {
                            super.ok();
                            getBzAgentsTodo("5", item.getId());   //同意
                            dismiss();
                        }

                        @Override
                        public void cancel() {
                            super.cancel();
                            getBzAgentsTodo("6", item.getId());    //不同意
                            dismiss();
                        }
                    };
                    dialog.show();

                }
            });
        }else {
            viewHolder.setVisible(R.id.plan_to_change, false);
        }

        //viewHolder.setVisible(R.id.item_plan_status, false);
        if (item != null) {
            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name());
            viewHolder.setText(R.id.item_plan_content, item.getTask_content());
            viewHolder.setText(R.id.item_start_time, "开始时间：" + item.getStart_time());
            viewHolder.setText(R.id.item_end_time, "结束时间：" + item.getEnd_time());
        }

        //HorizontalLineView view = viewHolder.getView(R.id.item_plan_status);

        if ("1".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 待班长分发");
        } else if ("2".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 待负责人提交");
        } else if ("3".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 已提交");
        } else if ("4".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 待班长审核");
        }else if ("5".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 审核通过");
        }else if ("6".equals(item.getTask_status())) {
            viewHolder .setTextColor(R.id.item_line_status, mContext.getResources().getColor(R.color.write_red))
                    .setText(R.id.item_line_status, "状态 : 审核不通过");
        }

        //view.setStatus(item.getStatus());
    }


    /*审核*/
    private void getBzAgentsTodo(String task_status, String taskId) {
        BaseRequest.getInstance().getService()
                .updateBzAgents(task_status, taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulMonthBean>>(mContext) {

                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulMonthBean>> t) throws Exception {
                        List<OverhaulMonthBean> overhaulMonthBeans = t.getResults();
                        //更新
                        RxRefreshEvent.publish("todoUpdate");

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
    //                    fragTodoRef.setRefreshing(false);
                    }
                });

    }

}