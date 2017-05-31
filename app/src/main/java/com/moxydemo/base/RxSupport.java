package com.moxydemo.base;

import rx.Subscription;

/**
 * Created by Vyacheslav on 22.09.2016.
 */

public interface RxSupport {

    void rxUnsubscribe();

    void rxAddSubscription(Subscription sub);

    void initRxSubscriptions();
}
