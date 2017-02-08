package cn.droidlover.xdroid.rx;


import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by waitou on 17/1/11.
 * 利用rxJava实现的eventBus替代otto
 */

public class RxBus {
    /**
     * 参考网址: http://hanhailong.com/2015/10/09/RxBus%E2%80%94%E9%80%9A%E8%BF%87RxJava%E6%9D%A5%E6%9B%BF%E6%8D%A2EventBus/
     * http://www.loongwind.com/archives/264.html
     * https://theseyears.gitbooks.io/android-architecture-journey/content/rxbus.html
     */
    private static volatile RxBus defaultInstance;

    private final Subject<Object, Object> bus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault() {
        RxBus rxBus = defaultInstance;
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                rxBus = defaultInstance;
                if (defaultInstance == null) {
                    rxBus = new RxBus();
                    defaultInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    /**
     * 提供一个事件
     */
    public void post(Object o) {
        bus.onNext(o);
    }

    /**
     * 提供了一个事件,根据code进行分发
     */
    public void post(int code, Object o) {
        bus.onNext(new RxBusBaseEvent<>(code, o));
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    /**
     * 根据专递的code 和 eventType 类型返回特定类型 eventType的观察者
     * 对于注册了 code为 0 ， class为voidMessage的观察者，那么就接收不到code为0之外的voidMessage。
     */
    public <T> Observable<T> toObservable(final int code, final Class<T> eventType) {
        return bus.ofType(RxBusBaseEvent.class)
                .filter(event -> event.getCode() == code && eventType.isInstance(event.getObject()))
                .map(RxBusBaseEvent::getObject)
                .cast(eventType);
    }

}
