/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.android.internal.telephony.uicc.euicc.async;

import android.os.Handler;
import com.android.internal.telephony.uicc.euicc.async.AsyncResultCallback;

public final class AsyncResultHelper {
    private AsyncResultHelper() {
    }

    public static <T> void returnResult(final T t, final AsyncResultCallback<T> asyncResultCallback, Handler handler) {
        if (handler == null) {
            asyncResultCallback.onResult(t);
        } else {
            handler.post(new Runnable(){

                @Override
                public void run() {
                    asyncResultCallback.onResult(t);
                }
            });
        }
    }

    public static void throwException(final Throwable throwable, final AsyncResultCallback<?> asyncResultCallback, Handler handler) {
        if (handler == null) {
            asyncResultCallback.onException(throwable);
        } else {
            handler.post(new Runnable(){

                @Override
                public void run() {
                    asyncResultCallback.onException(throwable);
                }
            });
        }
    }

}

