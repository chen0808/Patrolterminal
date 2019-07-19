package com.patrol.terminal;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.patrol.terminal.utils.ExceptionCrashHandler;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化DBFLOW
        FlowManager.init(this);
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);//添加日志
        ExceptionCrashHandler.getInstance().init(this);

        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}

