/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Message;

public class AsyncResult {
    @UnsupportedAppUsage
    public Throwable exception;
    @UnsupportedAppUsage
    public Object result;
    @UnsupportedAppUsage
    public Object userObj;

    @UnsupportedAppUsage
    public AsyncResult(Object object, Object object2, Throwable throwable) {
        this.userObj = object;
        this.result = object2;
        this.exception = throwable;
    }

    @UnsupportedAppUsage
    public static AsyncResult forMessage(Message message) {
        AsyncResult asyncResult = new AsyncResult(message.obj, null, null);
        message.obj = asyncResult;
        return asyncResult;
    }

    @UnsupportedAppUsage
    public static AsyncResult forMessage(Message message, Object object, Throwable throwable) {
        message.obj = object = new AsyncResult(message.obj, object, throwable);
        return object;
    }
}

