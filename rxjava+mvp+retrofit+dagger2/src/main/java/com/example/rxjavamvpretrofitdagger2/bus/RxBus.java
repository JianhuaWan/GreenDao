package com.example.rxjavamvpretrofitdagger2.bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by wanjianhua on 2017/4/17.
 */

public class RxBus {
    private static volatile RxBus mInstance;

    private final Subject bus;


    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 单例模式RxBus
     *
     * @return
     */
    public static RxBus getInstance() {

        RxBus rxBus2 = mInstance;
        if (mInstance == null) {
            synchronized (RxBus.class) {
                rxBus2 = mInstance;
                if (mInstance == null) {
                    rxBus2 = new RxBus();
                    mInstance = rxBus2;
                }
            }
        }

        return rxBus2;
    }


    /**
     * 发送消息
     *
     * @param object
     */
    public void post(Object object) {

        bus.onNext(object);

    }

    /**
     * 接收消息
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
