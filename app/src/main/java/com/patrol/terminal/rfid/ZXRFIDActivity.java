package com.patrol.terminal.rfid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.external.rfid.IRfid;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ZXRFIDActivity extends BaseActivity implements View.OnClickListener, IRfid.QueryCallbackListener, IRfid.CallbackListener {

	private static final String TAG = ZXRFIDActivity.class.getSimpleName();
	public static final String EXTRA_TAG_OPERATION = "tagOperation";

	private TextView mTvResult;
	private Button mBtnInit;
	private Button mBtnSearch;
	private Button mBtnStopSearch;
	private Button mBtnOpen;
	private Button mBtnClose;
	private ProgressBar mProgressInit;
	private ScrollView mScrollResult;

	private Set<String> mResultSet;
	private boolean mIsInit;

	private static final int REQUEST_TAKE_PHOTO_PERMISSION = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkPremission();
		setContentView(R.layout.activity_zxrfid);
		initView();
	}

	private void checkPremission() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			//申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.CAMERA,
							Manifest.permission.ACCESS_COARSE_LOCATION,
							Manifest.permission.ACCESS_FINE_LOCATION,
							Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.READ_EXTERNAL_STORAGE,},
					REQUEST_TAKE_PHOTO_PERMISSION);
		}
	}

	private void initView() {
		mBtnInit = findViewById(R.id.btn_init);
		mBtnSearch = findViewById(R.id.btn_search);
		mBtnStopSearch = findViewById(R.id.btn_searchstop);
		mTvResult = findViewById(R.id.tv_result);
		mTvResult.setClickable(false);
		mBtnOpen = findViewById(R.id.btn_open);
		mBtnClose = findViewById(R.id.btn_close);
		mProgressInit = findViewById(R.id.progress_init);
		mScrollResult = findViewById(R.id.scroll_result);

		mBtnInit.setOnClickListener(this);
		mBtnSearch.setOnClickListener(this);
		mBtnStopSearch.setOnClickListener(this);
		mBtnOpen.setOnClickListener(this);
		mBtnClose.setOnClickListener(this);
		mTvResult.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.btn_init) {
			mIsInit = false;
			mScrollResult.setVisibility(View.GONE);
			mProgressInit.setVisibility(View.VISIBLE);
			RFIDManager.getRFIDInstance().init(this, "001583EA5423", "", this);
		} else if (id == R.id.btn_search) {
			RFIDManager.getRFIDInstance().searchTag(this);

		} else if (id == R.id.btn_searchstop) {
			RFIDManager.getRFIDInstance().stopSearchTag(this);

		} else if (id == R.id.btn_open) {
			RFIDManager.getRFIDInstance().open(this);

		} else if (id == R.id.btn_close) {
			RFIDManager.getRFIDInstance().close(this);

		} else if (id == R.id.tv_result) {
			if (mResultSet.size() == 0) {
				Toast.makeText(this, "无标签数据", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intentTagOperation = new Intent(this, TagOperationActivity.class);
			String[] resultArray = new String[mResultSet.size()];
			List<String> resultList = new ArrayList<>(mResultSet);
			for (int i = 0; i < resultList.size(); i++) {
				resultArray[i] = resultList.get(i);
			}
			intentTagOperation.putExtra(EXTRA_TAG_OPERATION, resultArray);
			startActivity(intentTagOperation);
		}
	}

	@Override
	public void callback(boolean success, String msg, List<String> results) {
		runOnUiThread(() -> {
			if (results != null) {
				if (mResultSet == null) {
					mResultSet = new HashSet<>();
				}
				for (String result : results) {
					mResultSet.add(result);
					mTvResult.setText(getString(R.string.tv_result, mTvResult.getText().toString(), result));
					mTvResult.setClickable(true);
					Log.e(TAG, result);
				}
			} else {
				mTvResult.setText(getString(R.string.tv_result, mTvResult.getText().toString(), msg));
			}
		});
	}

	@Override
	public void callback(boolean success, int index, String msg) {
		runOnUiThread(() -> {
			if (!mIsInit) {
				mIsInit = true;
				mProgressInit.setVisibility(View.GONE);
				mScrollResult.setVisibility(View.VISIBLE);
			}
			mTvResult.setText(getString(R.string.tv_result, mTvResult.getText().toString(), msg));
			Log.e(TAG, msg);
		});

	}
}
