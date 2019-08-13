/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

public abstract class IntentService
extends Service {
    private String mName;
    private boolean mRedelivery;
    @UnsupportedAppUsage
    private volatile ServiceHandler mServiceHandler;
    private volatile Looper mServiceLooper;

    public IntentService(String string2) {
        this.mName = string2;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Object object = new StringBuilder();
        ((StringBuilder)object).append("IntentService[");
        ((StringBuilder)object).append(this.mName);
        ((StringBuilder)object).append("]");
        object = new HandlerThread(((StringBuilder)object).toString());
        ((Thread)object).start();
        this.mServiceLooper = ((HandlerThread)object).getLooper();
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper);
    }

    @Override
    public void onDestroy() {
        this.mServiceLooper.quit();
    }

    protected abstract void onHandleIntent(Intent var1);

    @Override
    public void onStart(Intent intent, int n) {
        Message message = this.mServiceHandler.obtainMessage();
        message.arg1 = n;
        message.obj = intent;
        this.mServiceHandler.sendMessage(message);
    }

    @Override
    public int onStartCommand(Intent intent, int n, int n2) {
        this.onStart(intent, n2);
        n = this.mRedelivery ? 3 : 2;
        return n;
    }

    public void setIntentRedelivery(boolean bl) {
        this.mRedelivery = bl;
    }

    private final class ServiceHandler
    extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            IntentService.this.onHandleIntent((Intent)message.obj);
            IntentService.this.stopSelf(message.arg1);
        }
    }

}

