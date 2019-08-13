/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.MessageQueue;

public class RequestHandlerThread
extends HandlerThread {
    public static final int MSG_POKE_IDLE_HANDLER = -1;
    private Handler.Callback mCallback;
    private volatile Handler mHandler;
    private final ConditionVariable mIdle = new ConditionVariable(true);
    private final MessageQueue.IdleHandler mIdleHandler = new MessageQueue.IdleHandler(){

        @Override
        public boolean queueIdle() {
            RequestHandlerThread.this.mIdle.open();
            return false;
        }
    };
    private final ConditionVariable mStarted = new ConditionVariable(false);

    public RequestHandlerThread(String string2, Handler.Callback callback) {
        super(string2, 10);
        this.mCallback = callback;
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean hasAnyMessages(int[] arrn) {
        MessageQueue messageQueue = this.mHandler.getLooper().getQueue();
        synchronized (messageQueue) {
            int n = arrn.length;
            int n2 = 0;
            while (n2 < n) {
                int n3 = arrn[n2];
                if (this.mHandler.hasMessages(n3)) {
                    return true;
                }
                ++n2;
            }
            return false;
        }
    }

    @Override
    protected void onLooperPrepared() {
        this.mHandler = new Handler(this.getLooper(), this.mCallback);
        this.mStarted.open();
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeMessages(int[] arrn) {
        MessageQueue messageQueue = this.mHandler.getLooper().getQueue();
        synchronized (messageQueue) {
            int n = arrn.length;
            int n2 = 0;
            while (n2 < n) {
                int n3 = arrn[n2];
                this.mHandler.removeMessages(n3);
                ++n2;
            }
            return;
        }
    }

    public Handler waitAndGetHandler() {
        this.waitUntilStarted();
        return this.getHandler();
    }

    public void waitUntilIdle() {
        Handler handler = this.waitAndGetHandler();
        MessageQueue messageQueue = handler.getLooper().getQueue();
        if (messageQueue.isIdle()) {
            return;
        }
        this.mIdle.close();
        messageQueue.addIdleHandler(this.mIdleHandler);
        handler.sendEmptyMessage(-1);
        if (messageQueue.isIdle()) {
            return;
        }
        this.mIdle.block();
    }

    public void waitUntilStarted() {
        this.mStarted.block();
    }

}

