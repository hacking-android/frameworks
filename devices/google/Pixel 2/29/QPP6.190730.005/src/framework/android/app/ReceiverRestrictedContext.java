/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ReceiverCallNotAllowedException;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.UserHandle;
import java.util.concurrent.Executor;

class ReceiverRestrictedContext
extends ContextWrapper {
    @UnsupportedAppUsage
    ReceiverRestrictedContext(Context context) {
        super(context);
    }

    @Override
    public boolean bindIsolatedService(Intent intent, int n, String string2, Executor executor, ServiceConnection serviceConnection) {
        throw new ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to bind to services");
    }

    @Override
    public boolean bindService(Intent intent, int n, Executor executor, ServiceConnection serviceConnection) {
        throw new ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to bind to services");
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int n) {
        throw new ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to bind to services");
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        return this.registerReceiver(broadcastReceiver, intentFilter, null, null);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String string2, Handler handler) {
        if (broadcastReceiver == null) {
            return super.registerReceiver(null, intentFilter, string2, handler);
        }
        throw new ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to register to receive intents");
    }

    @Override
    public Intent registerReceiverAsUser(BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String string2, Handler handler) {
        if (broadcastReceiver == null) {
            return super.registerReceiverAsUser(null, userHandle, intentFilter, string2, handler);
        }
        throw new ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to register to receive intents");
    }
}

