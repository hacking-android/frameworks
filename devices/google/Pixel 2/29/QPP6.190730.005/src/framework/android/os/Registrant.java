/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

public class Registrant {
    WeakReference refH;
    Object userObj;
    int what;

    @UnsupportedAppUsage
    public Registrant(Handler handler, int n, Object object) {
        this.refH = new WeakReference<Handler>(handler);
        this.what = n;
        this.userObj = object;
    }

    @UnsupportedAppUsage
    public void clear() {
        this.refH = null;
        this.userObj = null;
    }

    public Handler getHandler() {
        WeakReference weakReference = this.refH;
        if (weakReference == null) {
            return null;
        }
        return (Handler)weakReference.get();
    }

    void internalNotifyRegistrant(Object object, Throwable throwable) {
        Handler handler = this.getHandler();
        if (handler == null) {
            this.clear();
        } else {
            Message message = Message.obtain();
            message.what = this.what;
            message.obj = new AsyncResult(this.userObj, object, throwable);
            handler.sendMessage(message);
        }
    }

    @UnsupportedAppUsage
    public Message messageForRegistrant() {
        Object object = this.getHandler();
        if (object == null) {
            this.clear();
            return null;
        }
        object = ((Handler)object).obtainMessage();
        ((Message)object).what = this.what;
        ((Message)object).obj = this.userObj;
        return object;
    }

    public void notifyException(Throwable throwable) {
        this.internalNotifyRegistrant(null, throwable);
    }

    @UnsupportedAppUsage
    public void notifyRegistrant() {
        this.internalNotifyRegistrant(null, null);
    }

    @UnsupportedAppUsage
    public void notifyRegistrant(AsyncResult asyncResult) {
        this.internalNotifyRegistrant(asyncResult.result, asyncResult.exception);
    }

    @UnsupportedAppUsage
    public void notifyResult(Object object) {
        this.internalNotifyRegistrant(object, null);
    }
}

