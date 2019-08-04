package com.patrol.terminal;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.patrol.terminal.utils.ExceptionCrashHandler;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

public class MyApp extends Application {

    private static Context mContext;

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        //初始化DBFLOW
        FlowManager.init(this);
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);//添加日志
        ExceptionCrashHandler.getInstance().init(this);

        Logger.addLogAdapter(new AndroidLogAdapter());

//        Stetho.initializeWithDefaults(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}

