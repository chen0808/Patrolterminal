package com.patrol.terminal.utils;

import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.PlanTypeBean;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ljx on 2016/7/5.
 */
public class RxRefreshEvent {
    //类似界面回调的那种
    private static PublishSubject<String> observable=PublishSubject.create();

    private static PublishSubject<PlanTypeBean> typeObservable=PublishSubject.create();

    private static PublishSubject<DayOfWeekBean> dayobservable=PublishSubject.create();
    public static PublishSubject<DayOfWeekBean> getDayObservable() {
        return dayobservable;
    }
    public static void publishDay(DayOfWeekBean bean) {
        dayobservable.onNext(bean);
    }
    public static PublishSubject<PlanTypeBean> getTypeObservable() {
        return typeObservable;
    }

    public static void publishType(PlanTypeBean typeBean) {
        typeObservable.onNext(typeBean);
    }

    public static Observable<String> getObservable(){
        return observable;
    }


    public static void publish(String type){
        observable.onNext(type);
    }



}
