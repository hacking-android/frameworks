/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 */
package com.android.internal.telephony.uicc.euicc.async;

import android.os.AsyncResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.internal.telephony.uicc.euicc.async.AsyncResultCallback;

public abstract class AsyncMessageInvocation<Request, Response>
implements Handler.Callback {
    public boolean handleMessage(Message object) {
        AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
        object = (AsyncResultCallback)asyncResult.userObj;
        try {
            ((AsyncResultCallback)object).onResult(this.parseResult(asyncResult));
        }
        catch (Throwable throwable) {
            ((AsyncResultCallback)object).onException(throwable);
        }
        return true;
    }

    public final void invoke(Request Request2, AsyncResultCallback<Response> asyncResultCallback, Handler handler) {
        this.sendRequestMessage(Request2, new Handler(handler.getLooper(), (Handler.Callback)this).obtainMessage(0, asyncResultCallback));
    }

    protected abstract Response parseResult(AsyncResult var1) throws Throwable;

    protected abstract void sendRequestMessage(Request var1, Message var2);
}

