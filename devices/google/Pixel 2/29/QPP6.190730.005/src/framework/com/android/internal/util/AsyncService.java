/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Slog;

public abstract class AsyncService
extends Service {
    public static final int CMD_ASYNC_SERVICE_DESTROY = 16777216;
    public static final int CMD_ASYNC_SERVICE_ON_START_INTENT = 16777215;
    protected static final boolean DBG = true;
    private static final String TAG = "AsyncService";
    AsyncServiceInfo mAsyncServiceInfo;
    Handler mHandler;
    protected Messenger mMessenger;

    public abstract AsyncServiceInfo createHandler();

    public Handler getHandler() {
        return this.mHandler;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mAsyncServiceInfo = this.createHandler();
        this.mHandler = this.mAsyncServiceInfo.mHandler;
        this.mMessenger = new Messenger(this.mHandler);
    }

    @Override
    public void onDestroy() {
        Slog.d("AsyncService", "onDestroy");
        Message message = this.mHandler.obtainMessage();
        message.what = 16777216;
        this.mHandler.sendMessage(message);
    }

    @Override
    public int onStartCommand(Intent intent, int n, int n2) {
        Slog.d("AsyncService", "onStartCommand");
        Message message = this.mHandler.obtainMessage();
        message.what = 16777215;
        message.arg1 = n;
        message.arg2 = n2;
        message.obj = intent;
        this.mHandler.sendMessage(message);
        return this.mAsyncServiceInfo.mRestartFlags;
    }

    public static final class AsyncServiceInfo {
        public Handler mHandler;
        public int mRestartFlags;
    }

}

