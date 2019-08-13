/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.app.INotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.notification.Condition;
import android.service.notification.IConditionProvider;
import android.util.Log;

@Deprecated
public abstract class ConditionProviderService
extends Service {
    @Deprecated
    public static final String EXTRA_RULE_ID = "android.service.notification.extra.RULE_ID";
    @Deprecated
    public static final String META_DATA_CONFIGURATION_ACTIVITY = "android.service.zen.automatic.configurationActivity";
    @Deprecated
    public static final String META_DATA_RULE_INSTANCE_LIMIT = "android.service.zen.automatic.ruleInstanceLimit";
    @Deprecated
    public static final String META_DATA_RULE_TYPE = "android.service.zen.automatic.ruleType";
    public static final String SERVICE_INTERFACE = "android.service.notification.ConditionProviderService";
    private final String TAG;
    private final H mHandler;
    boolean mIsConnected;
    private INotificationManager mNoMan;
    private Provider mProvider;

    public ConditionProviderService() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ConditionProviderService.class.getSimpleName());
        stringBuilder.append("[");
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("]");
        this.TAG = stringBuilder.toString();
        this.mHandler = new H();
    }

    private final INotificationManager getNotificationInterface() {
        if (this.mNoMan == null) {
            this.mNoMan = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        }
        return this.mNoMan;
    }

    public static final void requestRebind(ComponentName componentName) {
        INotificationManager iNotificationManager = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        try {
            iNotificationManager.requestBindProvider(componentName);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isBound() {
        if (!this.mIsConnected) {
            Log.w(this.TAG, "Condition provider service not yet bound.");
        }
        return this.mIsConnected;
    }

    @Deprecated
    public final void notifyCondition(Condition condition) {
        if (condition == null) {
            return;
        }
        this.notifyConditions(condition);
    }

    @Deprecated
    public final void notifyConditions(Condition ... arrcondition) {
        if (this.isBound() && arrcondition != null) {
            try {
                this.getNotificationInterface().notifyConditions(this.getPackageName(), this.mProvider, arrcondition);
            }
            catch (RemoteException remoteException) {
                Log.v(this.TAG, "Unable to contact notification manager", remoteException);
            }
            return;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (this.mProvider == null) {
            this.mProvider = new Provider();
        }
        return this.mProvider;
    }

    public abstract void onConnected();

    public void onRequestConditions(int n) {
    }

    public abstract void onSubscribe(Uri var1);

    public abstract void onUnsubscribe(Uri var1);

    public final void requestUnbind() {
        INotificationManager iNotificationManager = this.getNotificationInterface();
        try {
            iNotificationManager.requestUnbindProvider(this.mProvider);
            this.mIsConnected = false;
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private final class H
    extends Handler {
        private static final int ON_CONNECTED = 1;
        private static final int ON_SUBSCRIBE = 3;
        private static final int ON_UNSUBSCRIBE = 4;

        private H() {
        }

        @Override
        public void handleMessage(Message message) {
            block8 : {
                String string2;
                block6 : {
                    block7 : {
                        string2 = null;
                        if (!ConditionProviderService.this.mIsConnected) {
                            return;
                        }
                        int n = message.what;
                        if (n == 1) break block6;
                        if (n == 3) break block7;
                        if (n != 4) break block8;
                        string2 = "onUnsubscribe";
                        ConditionProviderService.this.onUnsubscribe((Uri)message.obj);
                    }
                    string2 = "onSubscribe";
                    ConditionProviderService.this.onSubscribe((Uri)message.obj);
                }
                string2 = "onConnected";
                try {
                    ConditionProviderService.this.onConnected();
                }
                catch (Throwable throwable) {
                    String string3 = ConditionProviderService.this.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error running ");
                    stringBuilder.append(string2);
                    Log.w(string3, stringBuilder.toString(), throwable);
                }
            }
        }
    }

    private final class Provider
    extends IConditionProvider.Stub {
        private Provider() {
        }

        @Override
        public void onConnected() {
            ConditionProviderService conditionProviderService = ConditionProviderService.this;
            conditionProviderService.mIsConnected = true;
            conditionProviderService.mHandler.obtainMessage(1).sendToTarget();
        }

        @Override
        public void onSubscribe(Uri uri) {
            ConditionProviderService.this.mHandler.obtainMessage(3, uri).sendToTarget();
        }

        @Override
        public void onUnsubscribe(Uri uri) {
            ConditionProviderService.this.mHandler.obtainMessage(4, uri).sendToTarget();
        }
    }

}

