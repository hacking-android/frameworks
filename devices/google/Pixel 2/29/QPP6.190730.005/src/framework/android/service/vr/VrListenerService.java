/*
 * Decompiled with CFR 0.145.
 */
package android.service.vr;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.service.vr.IVrListener;

public abstract class VrListenerService
extends Service {
    private static final int MSG_ON_CURRENT_VR_ACTIVITY_CHANGED = 1;
    public static final String SERVICE_INTERFACE = "android.service.vr.VrListenerService";
    private final IVrListener.Stub mBinder = new IVrListener.Stub(){

        @Override
        public void focusedActivityChanged(ComponentName componentName, boolean bl, int n) {
            VrListenerService.this.mHandler.obtainMessage(1, (int)bl, n, componentName).sendToTarget();
        }
    };
    private final Handler mHandler = new VrListenerHandler(Looper.getMainLooper());

    public static final boolean isVrModePackageEnabled(Context object, ComponentName componentName) {
        if ((object = ((Context)object).getSystemService(ActivityManager.class)) == null) {
            return false;
        }
        return ((ActivityManager)object).isVrModePackageEnabled(componentName);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public void onCurrentVrActivityChanged(ComponentName componentName) {
    }

    @UnsupportedAppUsage
    public void onCurrentVrActivityChanged(ComponentName componentName, boolean bl, int n) {
        if (bl) {
            componentName = null;
        }
        this.onCurrentVrActivityChanged(componentName);
    }

    private final class VrListenerHandler
    extends Handler {
        public VrListenerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            boolean bl = true;
            if (n == 1) {
                VrListenerService vrListenerService = VrListenerService.this;
                ComponentName componentName = (ComponentName)message.obj;
                if (message.arg1 != 1) {
                    bl = false;
                }
                vrListenerService.onCurrentVrActivityChanged(componentName, bl, message.arg2);
            }
        }
    }

}

