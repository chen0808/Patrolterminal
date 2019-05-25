package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.AddressBookAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AddressBookBean;
import com.patrol.terminal.bean.AddressBookLevel2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*通讯录*/
public class AddressBookActivity extends BaseActivity {
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @BindView(R.id.title_setting_tv)
    TextView titleSettingTv;
    @BindView(R.id.title_setting)
    RelativeLayout titleSetting;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.rv_address_book)
    RecyclerView rvAddressBook;
    private List<AddressBookLevel2> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        ButterKnife.bind(this);
        list = (List<AddressBookLevel2>) getIntent().getSerializableExtra("nameList");
        titleName.setText("通讯录");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("完成");
        initData();
    }

    private void initData() {
        if (list == null || list.size() == 0) {
            BaseRequest.getInstance().getService()
                    .getAllUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<AddressBookBean>>(this) {
                        @Override
                        protected void onSuccees(BaseResult<List<AddressBookBean>> t) throws Exception {
                            if (t.getCode() == 1) {
                                List<AddressBookBean> results = t.getResults();
                                //SPUtil.putString(LoginActivity.this, Constant.USER, Constant.USERNAME, results.getName());
                                setDatatoList(results);


                            } else {
                                Toast.makeText(AddressBookActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                            Log.d("TAG", e.getMessage());
                        }
                    });
        } else {
            rvAddressBook.setLayoutManager(new LinearLayoutManager(this));
            AddressBookAdapter adapter = new AddressBookAdapter(R.layout.item_address_book_2, list);
            rvAddressBook.setAdapter(adapter);
        }
    }

    private void setDatatoList(List<AddressBookBean> addressBookBeanList) {
        list.clear();
        for (int j = 0; j < addressBookBeanList.size(); j++) {
            String name = addressBookBeanList.get(j).getName();
            String userId = addressBookBeanList.get(j).getId();
            String contentBzString = "";
            List<AddressBookBean.DepListBean> depList = addressBookBeanList.get(j).getDepList();
            if (depList != null && depList.size() > 0) {
                for (int i = 0; i < depList.size(); i++) {
                    contentBzString += depList.get(i).getDep_name();
                    if (i < depList.size() - 1) {
                        contentBzString += "/";
                    }
                }
            }

            String contentJobString = "";
            List<AddressBookBean.RoleListBean> roleList = addressBookBeanList.get(j).getRoleList();
            if (roleList != null && roleList.size() > 0) {
                for (int i = 0; i < roleList.size(); i++) {
                    contentJobString += roleList.get(i).getRole_name();
                    if (i < roleList.size() - 1) {
                        contentJobString += "/";
                    }
                }
            }
            AddressBookLevel2 level2 = new AddressBookLevel2(name, contentBzString, contentJobString, false, userId);
            list.add(level2);
        }

        rvAddressBook.setLayoutManager(new LinearLayoutManager(this));
        AddressBookAdapter adapter = new AddressBookAdapter(R.layout.item_address_book_2, list);
        rvAddressBook.setAdapter(adapter);
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.rl_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                Intent data = new Intent();
                data.putExtra("nameList", (Serializable) list);
                setResult(RESULT_OK, data);
                finish();
                break;
            case R.id.rl_search:
                break;
        }
    }
}
