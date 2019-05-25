package com.patrol.terminal.rfid;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.external.rfid.IRfid;
import com.external.rfid.RfidTag;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import androidx.appcompat.widget.AppCompatSpinner;

import static com.patrol.terminal.rfid.ZXRFIDActivity.EXTRA_TAG_OPERATION;

public class TagOperationActivity extends BaseActivity implements IRfid.CallbackListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

	private static final String TAG = TagOperationActivity.class.getSimpleName();

	private AppCompatSpinner mSpinnerTag;
	private AppCompatSpinner mSpinnerArea;
	private Button mBtnRead;
	private Button mBtnWrite;
	private Button mBtnSetAccessPwd;
	private Button mBtnSetDestroyPwd;
	private Button mBtnDelAccessPwd;
	private Button mBtnDelDestroyPwd;
	private EditText mEtAccessPwd;
	private EditText mEtDestroyPwd;
	private EditText mEtOffset;
	private EditText mEtLength;
	private EditText mEtWriteData;
	private TextView mTvResult;

	private String mCurrentTagId;
	private int mCurrentTagArea;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zxtag_operation);

		initView();
	}

	private void initView() {
		mSpinnerTag = findViewById(R.id.spinner_tag);
		mSpinnerArea = findViewById(R.id.spinner_area);
		mBtnRead = findViewById(R.id.btn_read);
		mBtnWrite = findViewById(R.id.btn_write);
		mBtnSetAccessPwd = findViewById(R.id.btn_set_access);
		mBtnSetDestroyPwd = findViewById(R.id.btn_set_destroy);
		mBtnDelAccessPwd = findViewById(R.id.btn_del_access);
		mBtnDelDestroyPwd = findViewById(R.id.btn_del_destroy);
		mEtAccessPwd = findViewById(R.id.et_access_pwd);
		mEtDestroyPwd = findViewById(R.id.et_destroy_pwd);
		mEtOffset = findViewById(R.id.et_offset);
		mEtLength = findViewById(R.id.et_length);
		mEtWriteData = findViewById(R.id.et_write_data);
		mTvResult = findViewById(R.id.tv_result_rw);

		// 数据初始化
		String[] resultArray = getIntent().getStringArrayExtra(EXTRA_TAG_OPERATION);
		ArrayAdapter<String> adapterTag = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resultArray);
		mSpinnerTag.setAdapter(adapterTag);
		ArrayAdapter<CharSequence> adapterArea = ArrayAdapter.createFromResource(this, R.array.tag_area, android.R.layout.simple_spinner_item);
		mSpinnerArea.setAdapter(adapterArea);

		mSpinnerTag.setOnItemSelectedListener(this);
		mSpinnerArea.setOnItemSelectedListener(this);
		mBtnRead.setOnClickListener(this);
		mBtnWrite.setOnClickListener(this);
		mBtnSetAccessPwd.setOnClickListener(this);
		mBtnSetDestroyPwd.setOnClickListener(this);
		mBtnDelAccessPwd.setOnClickListener(this);
		mBtnDelDestroyPwd.setOnClickListener(this);
	}



	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		int i = parent.getId();
		if (i == R.id.spinner_tag) {
			if (view instanceof TextView) {
				mCurrentTagId = ((TextView) view).getText().toString();
			}
		} else if (i == R.id.spinner_area) {
			if (view instanceof TextView) {
				mCurrentTagArea = position;
			}
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.btn_read) {
			readData();
		} else if (id == R.id.btn_write) {
			writeData();
		} else if (id == R.id.btn_set_access) {
			setAccessPwd();
		} else if (id == R.id.btn_set_destroy) {
			setDestroyPwd();
		} else if (id == R.id.btn_del_access) {
			delAccessPwd();
		} else if (id == R.id.btn_del_destroy) {
			delDestroyPwd();
		}
	}

	private void readData() {
		String offset = mEtOffset.getText().toString();
		String len = mEtLength.getText().toString();
		if (offset.isEmpty() || len.isEmpty()) {
			Toast.makeText(this, "未输入起始位置或长度", Toast.LENGTH_SHORT).show();
			return;
		}
		RfidTag tag = new RfidTag();
		tag.setId(mCurrentTagId);
		tag.setMemoryRegion(mCurrentTagArea);
		tag.setOffset(Integer.parseInt(offset));
		tag.setLength(Integer.parseInt(len));
		RFIDManager.getRFIDInstance().read(tag, this);
	}

	private void writeData() {
		String offset = mEtOffset.getText().toString();
		String writeData = mEtWriteData.getText().toString();
		if (offset.isEmpty() || writeData.isEmpty()) {
			Toast.makeText(this, "未输入起始位置或者写入数据", Toast.LENGTH_SHORT).show();
			return;
		}
		RfidTag tag = new RfidTag();
		tag.setId(mCurrentTagId);
		tag.setMemoryRegion(mCurrentTagArea);
		tag.setOffset(Integer.parseInt(offset));
		RFIDManager.getRFIDInstance().write(tag, null, null, writeData, this);
		Toast.makeText(this, "写入功能可参考设置访问密码等操作", Toast.LENGTH_SHORT).show();
	}

	private void setAccessPwd() {
		String accessPwd = mEtAccessPwd.getText().toString();
		if (accessPwd.isEmpty()) {
			Toast.makeText(this, "未输入访问密码", Toast.LENGTH_SHORT).show();
			return;
		}
		RfidTag tag = new RfidTag();
		tag.setId(mCurrentTagId);
		RFIDManager.getRFIDInstance().setAccessPwd(tag, accessPwd, this);
	}

	private void setDestroyPwd() {
		String destroyPwd = mEtDestroyPwd.getText().toString();
		if (destroyPwd.isEmpty()) {
			Toast.makeText(this, "未输入销毁密码", Toast.LENGTH_SHORT).show();
			return;
		}
		RfidTag tag = new RfidTag();
		tag.setId(mCurrentTagId);
		RFIDManager.getRFIDInstance().setKillPwd(tag, destroyPwd, this);
	}

	private void delAccessPwd() {
		RfidTag tag = new RfidTag();
		tag.setId(mCurrentTagId);
		RFIDManager.getRFIDInstance().deleteAccessPwd(tag, this);
	}

	private void delDestroyPwd() {
		RfidTag tag = new RfidTag();
		tag.setId(mCurrentTagId);
		RFIDManager.getRFIDInstance().deleteKillPwd(tag, this);
	}

	@Override
	public void callback(boolean success, int index, String msg) {
		mTvResult.setText(getString(R.string.tv_result, mTvResult.getText().toString(), msg));
		Log.e(TAG, msg);
	}
}
