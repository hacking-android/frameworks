/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

public abstract class SettingInjectorService
extends Service {
    public static final String ACTION_INJECTED_SETTING_CHANGED = "android.location.InjectedSettingChanged";
    public static final String ACTION_SERVICE_INTENT = "android.location.SettingInjectorService";
    public static final String ATTRIBUTES_NAME = "injected-location-setting";
    public static final String ENABLED_KEY = "enabled";
    public static final String MESSENGER_KEY = "messenger";
    public static final String META_DATA_NAME = "android.location.SettingInjectorService";
    public static final String SUMMARY_KEY = "summary";
    private static final String TAG = "SettingInjectorService";
    private final String mName;

    public SettingInjectorService(String string2) {
        this.mName = string2;
    }

    private void onHandleIntent(Intent intent) {
        String string2;
        String string3 = null;
        try {
            string3 = string2 = this.onGetSummary();
        }
        catch (Throwable throwable) {
            this.sendStatus(intent, string3, false);
            throw throwable;
        }
        boolean bl = this.onGetEnabled();
        this.sendStatus(intent, string2, bl);
    }

    public static final void refreshSettings(Context context) {
        context.sendBroadcast(new Intent(ACTION_INJECTED_SETTING_CHANGED));
    }

    private void sendStatus(Intent object, String string2, boolean bl) {
        Messenger messenger = (Messenger)((Intent)object).getParcelableExtra(MESSENGER_KEY);
        if (messenger == null) {
            return;
        }
        Message message = Message.obtain();
        Object object2 = new Bundle();
        ((BaseBundle)object2).putString(SUMMARY_KEY, string2);
        ((BaseBundle)object2).putBoolean(ENABLED_KEY, bl);
        message.setData((Bundle)object2);
        if (Log.isLoggable(TAG, 3)) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(this.mName);
            ((StringBuilder)object2).append(": received ");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(", summary=");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(", enabled=");
            ((StringBuilder)object2).append(bl);
            ((StringBuilder)object2).append(", sending message: ");
            ((StringBuilder)object2).append(message);
            Log.d(TAG, ((StringBuilder)object2).toString());
        }
        try {
            messenger.send(message);
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append(this.mName);
            ((StringBuilder)object).append(": sending dynamic status failed");
            Log.e(TAG, ((StringBuilder)object).toString(), remoteException);
        }
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return null;
    }

    protected abstract boolean onGetEnabled();

    protected abstract String onGetSummary();

    @Override
    public final void onStart(Intent intent, int n) {
        super.onStart(intent, n);
    }

    @Override
    public final int onStartCommand(Intent intent, int n, int n2) {
        this.onHandleIntent(intent);
        this.stopSelf(n2);
        return 2;
    }
}

