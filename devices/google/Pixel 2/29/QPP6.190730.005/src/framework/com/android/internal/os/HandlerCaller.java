/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.internal.os.SomeArgs;

@Deprecated
public class HandlerCaller {
    final Callback mCallback;
    final Handler mH;
    final Looper mMainLooper;

    public HandlerCaller(Context object, Looper looper, Callback callback, boolean bl) {
        object = looper != null ? looper : ((Context)object).getMainLooper();
        this.mMainLooper = object;
        this.mH = new MyHandler(this.mMainLooper, bl);
        this.mCallback = callback;
    }

    public void executeOrSendMessage(Message message) {
        if (Looper.myLooper() == this.mMainLooper) {
            this.mCallback.executeMessage(message);
            message.recycle();
            return;
        }
        this.mH.sendMessage(message);
    }

    public Handler getHandler() {
        return this.mH;
    }

    public boolean hasMessages(int n) {
        return this.mH.hasMessages(n);
    }

    @UnsupportedAppUsage
    public Message obtainMessage(int n) {
        return this.mH.obtainMessage(n);
    }

    public Message obtainMessageBO(int n, boolean bl, Object object) {
        return this.mH.obtainMessage(n, (int)bl, 0, object);
    }

    public Message obtainMessageBOO(int n, boolean bl, Object object, Object object2) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        return this.mH.obtainMessage(n, (int)bl, 0, someArgs);
    }

    public Message obtainMessageI(int n, int n2) {
        return this.mH.obtainMessage(n, n2, 0);
    }

    public Message obtainMessageII(int n, int n2, int n3) {
        return this.mH.obtainMessage(n, n2, n3);
    }

    public Message obtainMessageIIII(int n, int n2, int n3, int n4, int n5) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.argi1 = n2;
        someArgs.argi2 = n3;
        someArgs.argi3 = n4;
        someArgs.argi4 = n5;
        return this.mH.obtainMessage(n, 0, 0, someArgs);
    }

    public Message obtainMessageIIIIII(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.argi1 = n2;
        someArgs.argi2 = n3;
        someArgs.argi3 = n4;
        someArgs.argi4 = n5;
        someArgs.argi5 = n6;
        someArgs.argi6 = n7;
        return this.mH.obtainMessage(n, 0, 0, someArgs);
    }

    public Message obtainMessageIIIIO(int n, int n2, int n3, int n4, int n5, Object object) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.argi1 = n2;
        someArgs.argi2 = n3;
        someArgs.argi3 = n4;
        someArgs.argi4 = n5;
        return this.mH.obtainMessage(n, 0, 0, someArgs);
    }

    public Message obtainMessageIIO(int n, int n2, int n3, Object object) {
        return this.mH.obtainMessage(n, n2, n3, object);
    }

    public Message obtainMessageIIOO(int n, int n2, int n3, Object object, Object object2) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        return this.mH.obtainMessage(n, n2, n3, someArgs);
    }

    public Message obtainMessageIIOOO(int n, int n2, int n3, Object object, Object object2, Object object3) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        someArgs.arg3 = object3;
        return this.mH.obtainMessage(n, n2, n3, someArgs);
    }

    public Message obtainMessageIIOOOO(int n, int n2, int n3, Object object, Object object2, Object object3, Object object4) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        someArgs.arg3 = object3;
        someArgs.arg4 = object4;
        return this.mH.obtainMessage(n, n2, n3, someArgs);
    }

    @UnsupportedAppUsage
    public Message obtainMessageIO(int n, int n2, Object object) {
        return this.mH.obtainMessage(n, n2, 0, object);
    }

    @UnsupportedAppUsage
    public Message obtainMessageIOO(int n, int n2, Object object, Object object2) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        return this.mH.obtainMessage(n, n2, 0, someArgs);
    }

    public Message obtainMessageIOOO(int n, int n2, Object object, Object object2, Object object3) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        someArgs.arg3 = object3;
        return this.mH.obtainMessage(n, n2, 0, someArgs);
    }

    @UnsupportedAppUsage
    public Message obtainMessageO(int n, Object object) {
        return this.mH.obtainMessage(n, 0, 0, object);
    }

    @UnsupportedAppUsage
    public Message obtainMessageOO(int n, Object object, Object object2) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        return this.mH.obtainMessage(n, 0, 0, someArgs);
    }

    @UnsupportedAppUsage
    public Message obtainMessageOOO(int n, Object object, Object object2, Object object3) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        someArgs.arg3 = object3;
        return this.mH.obtainMessage(n, 0, 0, someArgs);
    }

    public Message obtainMessageOOOO(int n, Object object, Object object2, Object object3, Object object4) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        someArgs.arg3 = object3;
        someArgs.arg4 = object4;
        return this.mH.obtainMessage(n, 0, 0, someArgs);
    }

    public Message obtainMessageOOOOII(int n, Object object, Object object2, Object object3, Object object4, int n2, int n3) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        someArgs.arg3 = object3;
        someArgs.arg4 = object4;
        someArgs.argi5 = n2;
        someArgs.argi6 = n3;
        return this.mH.obtainMessage(n, 0, 0, someArgs);
    }

    public Message obtainMessageOOOOO(int n, Object object, Object object2, Object object3, Object object4, Object object5) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        someArgs.arg3 = object3;
        someArgs.arg4 = object4;
        someArgs.arg5 = object5;
        return this.mH.obtainMessage(n, 0, 0, someArgs);
    }

    public void removeMessages(int n) {
        this.mH.removeMessages(n);
    }

    public void removeMessages(int n, Object object) {
        this.mH.removeMessages(n, object);
    }

    @UnsupportedAppUsage
    public void sendMessage(Message message) {
        this.mH.sendMessage(message);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SomeArgs sendMessageAndWait(Message message) {
        if (Looper.myLooper() == this.mH.getLooper()) {
            throw new IllegalStateException("Can't wait on same thread as looper");
        }
        SomeArgs someArgs = (SomeArgs)message.obj;
        someArgs.mWaitState = 1;
        this.mH.sendMessage(message);
        synchronized (someArgs) {
            do {
                int n;
                if ((n = someArgs.mWaitState) != 1) {
                    // MONITOREXIT [3, 5, 6] lbl10 : MonitorExitStatement: MONITOREXIT : var2_3
                    someArgs.mWaitState = 0;
                    return someArgs;
                }
                try {
                    someArgs.wait();
                }
                catch (InterruptedException interruptedException) {
                    return null;
                }
            } while (true);
        }
    }

    public void sendMessageDelayed(Message message, long l) {
        this.mH.sendMessageDelayed(message, l);
    }

    public static interface Callback {
        public void executeMessage(Message var1);
    }

    class MyHandler
    extends Handler {
        MyHandler(Looper looper, boolean bl) {
            super(looper, null, bl);
        }

        @Override
        public void handleMessage(Message message) {
            HandlerCaller.this.mCallback.executeMessage(message);
        }
    }

}

