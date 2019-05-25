package com.patrol.terminal.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadActivity extends BaseActivity {
    private static final int TAKE_PHOTO = 0;
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.iv_add_pic)
    ImageView ivAddPic;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private Uri photoURI;
    private File photoFile;
    @BindView(R.id.et_msg)
    EditText etMsg;
    //声明AMapLocationClient类对象
    AMapLocationClient mLocationClient = null;
    private LatLonPoint latLonPoint;
    /**
     * 定位回调监听器
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    double currentLat = amapLocation.getLatitude();//获取纬度
                    double currentLon = amapLocation.getLongitude();//获取经度
                    latLonPoint = new LatLonPoint(currentLat, currentLon);  // latlng形式的
                    /*currentLatLng = new LatLng(currentLat, currentLon);*/   //latlng形式的
                    Log.i("currentLocation", "currentLat : " + currentLat + " currentLon : " + currentLon);
                    amapLocation.getAccuracy();//获取精度信息
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        titleName.setText("巡视打卡");
        getCurrentLocationLatLng();
    }

    /**
     * 初始化定位并设置定位回调监听
     */
    private void getCurrentLocationLatLng() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

 /* //设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景） 设置了场景就不用配置定位模式等
    option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
    if(null != locationClient){
        locationClient.setLocationOption(option);
        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        locationClient.stopLocation();
        locationClient.startLocation();
    }*/
        // 同时使用网络定位和GPS定位,优先返回最高精度的定位结果,以及对应的地址描述信息
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //只会使用网络定位
        /* mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);*/
        //只使用GPS进行定位
        /*mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);*/
        // 设置为单次定位 默认为false
        /*mLocationOption.setOnceLocation(true);*/
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。默认连续定位 切最低时间间隔为1000ms
        mLocationOption.setInterval(3500);
        //设置是否返回地址信息（默认返回地址信息）
        /*mLocationOption.setNeedAddress(true);*/
        //关闭缓存机制 默认开启 ，在高精度模式和低功耗模式下进行的网络定位结果均会生成本地缓存,不区分单次定位还是连续定位。GPS定位结果不会被缓存。
        /*mLocationOption.setLocationCacheEnable(false);*/
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();//销毁定位客户端。
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLocationClient != null) {
            mLocationClient.startLocation(); // 启动定位
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位
        }
    }

    @OnClick({R.id.title_back, R.id.iv_add_pic, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_add_pic:
                //拍照
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    //创建一个File
                    photoFile = new File(Environment.getExternalStorageDirectory(),
                            "tempImage" + ".jpg");
                    if (photoFile != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //如果是7.0及以上的系统使用FileProvider的方式创建一个Uri
                            photoURI = FileProvider.getUriForFile(this, "com.patrol.terminal.fileprovider", photoFile);
                            takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        } else {
                            //7.0以下使用这种方式创建一个Uri
                            photoURI = Uri.fromFile(photoFile);
                        }
                        //将Uri传递给系统相机
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, TAKE_PHOTO);
                    }
                }
                break;
            case R.id.btn_commit:
                Map<String, String> params = new HashMap<>();
                params.put("user_id", "zhangsan");
                params.put("lon", String.valueOf(latLonPoint.getLongitude()));
                params.put("lat", String.valueOf(latLonPoint.getLatitude()));
                params.put("task_id", "aaa");
                params.put("comments", etMsg.getText().toString());
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), photoFile);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", photoFile.getName(), requestFile);
                BaseRequest.getInstance().getService().upload(params, body).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            protected void onSuccees(BaseResult t) throws Exception {
                                Log.e("fff", t.getMsg());
                                Toast.makeText(UploadActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                Log.e("fff", e.toString());
                            }
                        });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
//                    Intent intent = new Intent("com.android.camera.action.CROP");
//                    intent.setDataAndType(imageUri, "image/*");
//                    intent.putExtra("scale", true);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
//                }
//                break;
//            case CROP_PHOTO:
//                    if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoURI));
                        ivAddPic.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

//    /**
//     * 打开文件
//     * 当手机中没有一个app可以打开file时会抛ActivityNotFoundException
//     *
//     * @param context     activity
//     * @param file        File
//     * @param contentType 文件类型如：文本（text/html）
//     */
//    public void startActionFile(Context context, File file, String contentType) throws ActivityNotFoundException {
//        if (context == null) {
//            return;
//        }
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.setDataAndType(getUriForFile(context, file), contentType);
//        if (!(context instanceof Activity)) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 打开相机
//     *
//     * @param activity    Activity
//     * @param file        File
//     * @param requestCode result requestCode
//     */
//    public void startActionCapture(Activity activity, File file, int requestCode) {
//        if (activity == null) {
//            return;
//        }
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file));
//        activity.startActivityForResult(intent, requestCode);
//    }
//
//    private Uri getUriForFile(Context context, File file) {
//        if (context == null || file == null) {
//            throw new NullPointerException();
//        }
//        if (Build.VERSION.SDK_INT >= 24) {
//            imageUri = FileProvider.getUriForFile(context.getApplicationContext(), "com.patrol.terminal.fileprovider", file);
//        } else {
//            imageUri = Uri.fromFile(file);
//        }
//        return imageUri;
//    }
}
