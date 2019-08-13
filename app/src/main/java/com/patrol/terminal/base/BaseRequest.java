package com.patrol.terminal.base;

import com.patrol.terminal.network.ApiServise;
import com.patrol.terminal.utils.InterceptorUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fg on 2018/7/25.
 */

public class BaseRequest {
    //retrofit底层用的okHttp,所以设置超时还需要okHttp
    //然后设置5秒超时
    //其中DEFAULT_TIMEOUT是我这边定义的一个常量
    //TimeUnit为java.util.concurrent包下的时间单位
    //TimeUnit.SECONDS这里为秒的单位

    OkHttpClient client=new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)//设置连接超时时间
            .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(20, TimeUnit.SECONDS)//设置写入超时时间
            .addInterceptor(InterceptorUtil.HeaderInterceptor())//添加其他拦截器
            .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
//            .addInterceptor(InterceptorUtil.interceptor)//日志
            .build();
    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()) //添加Gson转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rx转换器
            .baseUrl(BaseUrl.BASE_URL) //baseurl
            .client(client)
            .build();
    public ApiServise apiServise = retrofit.create(ApiServise.class);

    private static BaseRequest instance;

    public static synchronized BaseRequest getInstance(){
        if(instance == null)
            instance = new BaseRequest();
        return instance;
    }

    public ApiServise getService() {
        return apiServise;
    }
}
