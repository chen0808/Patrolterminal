package com.patrol.terminal.activity;

import android.os.Bundle;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.widget.ProgressDialog;

public class TroubleDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_detail);
        initData();
    }

    private void initData() {
        ProgressDialog.show(this, true, "正在加载...");
       /* BaseRequest.getInstance().getService()
                .getBird(page_num, page_size, "线")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleFragmentBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleFragmentBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<TroubleFragmentBean> result = t.getResults();
                            if (result != null && result.size() > 0) {
                                planRv.loadMoreFinish(false, true);
                                troubleList.addAll(result);
                                setDataToList(troubleList);
                                for (int i = 0; i < result.size(); i++) {
                                    lineList.add(result.get(i).getLine_name());
                                }
                            } else {
                                planRv.loadMoreFinish(true, false);
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });*/
    }
}
