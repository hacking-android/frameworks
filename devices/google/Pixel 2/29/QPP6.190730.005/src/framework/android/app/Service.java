/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.Application;
import android.app.IActivityManager;
import android.app.Notification;
import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Service
extends ContextWrapper
implements ComponentCallbacks2 {
    public static final int START_CONTINUATION_MASK = 15;
    public static final int START_FLAG_REDELIVERY = 1;
    public static final int START_FLAG_RETRY = 2;
    public static final int START_NOT_STICKY = 2;
    public static final int START_REDELIVER_INTENT = 3;
    public static final int START_STICKY = 1;
    public static final int START_STICKY_COMPATIBILITY = 0;
    public static final int START_TASK_REMOVED_COMPLETE = 1000;
    public static final int STOP_FOREGROUND_DETACH = 2;
    public static final int STOP_FOREGROUND_REMOVE = 1;
    private static final String TAG = "Service";
    @UnsupportedAppUsage
    private IActivityManager mActivityManager = null;
    @UnsupportedAppUsage
    private Application mApplication = null;
    @UnsupportedAppUsage
    private String mClassName = null;
    @UnsupportedAppUsage
    private boolean mStartCompatibility = false;
    @UnsupportedAppUsage
    private ActivityThread mThread = null;
    @UnsupportedAppUsage
    private IBinder mToken = null;

    public Service() {
        super(null);
    }

    @UnsupportedAppUsage
    public final void attach(Context context, ActivityThread activityThread, String string2, IBinder iBinder, Application application, Object object) {
        this.attachBaseContext(context);
        this.mThread = activityThread;
        this.mClassName = string2;
        this.mToken = iBinder;
        this.mApplication = application;
        this.mActivityManager = (IActivityManager)object;
        boolean bl = this.getApplicationInfo().targetSdkVersion < 5;
        this.mStartCompatibility = bl;
    }

    public final void detachAndCleanUp() {
        this.mToken = null;
    }

    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("nothing to dump");
    }

    public final Application getApplication() {
        return this.mApplication;
    }

    final String getClassName() {
        return this.mClassName;
    }

    public final int getForegroundServiceType() {
        int n;
        int n2 = 0;
        try {
            IActivityManager iActivityManager = this.mActivityManager;
            ComponentName componentName = new ComponentName((Context)this, this.mClassName);
            n = iActivityManager.getForegroundServiceType(componentName, this.mToken);
        }
        catch (RemoteException remoteException) {
            n = n2;
        }
        return n;
    }

    public abstract IBinder onBind(Intent var1);

    @Override
    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onCreate() {
    }

    public void onDestroy() {
    }

    @Override
    public void onLowMemory() {
    }

    public void onRebind(Intent intent) {
    }

    @Deprecated
    public void onStart(Intent intent, int n) {
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        this.onStart(intent, n2);
        return this.mStartCompatibility ^ true;
    }

    public void onTaskRemoved(Intent intent) {
    }

    @Override
    public void onTrimMemory(int n) {
    }

    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Deprecated
    @UnsupportedAppUsage
    public final void setForeground(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setForeground: ignoring old API call on ");
        stringBuilder.append(this.getClass().getName());
        Log.w("Service", stringBuilder.toString());
    }

    public final void startForeground(int n, Notification notification) {
        try {
            IActivityManager iActivityManager = this.mActivityManager;
            ComponentName componentName = new ComponentName((Context)this, this.mClassName);
            iActivityManager.setServiceForeground(componentName, this.mToken, n, notification, 0, -1);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public final void startForeground(int n, Notification notification, int n2) {
        try {
            IActivityManager iActivityManager = this.mActivityManager;
            ComponentName componentName = new ComponentName((Context)this, this.mClassName);
            iActivityManager.setServiceForeground(componentName, this.mToken, n, notification, 0, n2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public final void stopForeground(int n) {
        try {
            IActivityManager iActivityManager = this.mActivityManager;
            ComponentName componentName = new ComponentName((Context)this, this.mClassName);
            iActivityManager.setServiceForeground(componentName, this.mToken, 0, null, n, 0);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public final void stopForeground(boolean bl) {
        this.stopForeground((int)bl);
    }

    public final void stopSelf() {
        this.stopSelf(-1);
    }

    public final void stopSelf(int n) {
        IActivityManager iActivityManager = this.mActivityManager;
        if (iActivityManager == null) {
            return;
        }
        try {
            ComponentName componentName = new ComponentName((Context)this, this.mClassName);
            iActivityManager.stopServiceToken(componentName, this.mToken, n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public final boolean stopSelfResult(int n) {
        IActivityManager iActivityManager = this.mActivityManager;
        if (iActivityManager == null) {
            return false;
        }
        try {
            ComponentName componentName = new ComponentName((Context)this, this.mClassName);
            boolean bl = iActivityManager.stopServiceToken(componentName, this.mToken, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StartArgFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StartResult {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StopForegroundFlags {
    }

}

