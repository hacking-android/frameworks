/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.content.pm.ApplicationInfo;
import android.net.LocalSocketAddress;
import android.os.Build;
import android.os.ChildZygoteProcess;
import android.os.Process;
import android.os.ZygoteProcess;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;

public class AppZygote {
    private static final String LOG_TAG = "AppZygote";
    private final ApplicationInfo mAppInfo;
    private final Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private ChildZygoteProcess mZygote;
    private final int mZygoteUid;
    private final int mZygoteUidGidMax;
    private final int mZygoteUidGidMin;

    public AppZygote(ApplicationInfo applicationInfo, int n, int n2, int n3) {
        this.mAppInfo = applicationInfo;
        this.mZygoteUid = n;
        this.mZygoteUidGidMin = n2;
        this.mZygoteUidGidMax = n3;
    }

    @GuardedBy(value={"mLock"})
    private void connectToZygoteIfNeededLocked() {
        String string2 = this.mAppInfo.primaryCpuAbi != null ? this.mAppInfo.primaryCpuAbi : Build.SUPPORTED_ABIS[0];
        try {
            ZygoteProcess zygoteProcess = Process.ZYGOTE_PROCESS;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mAppInfo.processName);
            stringBuilder.append("_zygote");
            this.mZygote = zygoteProcess.startChildZygote("com.android.internal.os.AppZygoteInit", stringBuilder.toString(), this.mZygoteUid, this.mZygoteUid, null, 0, "app_zygote", string2, string2, null, this.mZygoteUidGidMin, this.mZygoteUidGidMax);
            ZygoteProcess.waitForConnectionToZygote(this.mZygote.getPrimarySocketAddress());
            Log.i(LOG_TAG, "Starting application preload.");
            this.mZygote.preloadApp(this.mAppInfo, string2);
            Log.i(LOG_TAG, "Application preload done.");
        }
        catch (Exception exception) {
            Log.e(LOG_TAG, "Error connecting to app zygote", exception);
            this.stopZygoteLocked();
        }
    }

    @GuardedBy(value={"mLock"})
    private void stopZygoteLocked() {
        ChildZygoteProcess childZygoteProcess = this.mZygote;
        if (childZygoteProcess != null) {
            childZygoteProcess.close();
            Process.killProcess(this.mZygote.getPid());
            this.mZygote = null;
        }
    }

    public ApplicationInfo getAppInfo() {
        return this.mAppInfo;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ChildZygoteProcess getProcess() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mZygote != null) {
                return this.mZygote;
            }
            this.connectToZygoteIfNeededLocked();
            return this.mZygote;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stopZygote() {
        Object object = this.mLock;
        synchronized (object) {
            this.stopZygoteLocked();
            return;
        }
    }
}

