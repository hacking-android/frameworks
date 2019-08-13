/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.LocalSocketAddress;
import android.os.Build;
import android.os.ChildZygoteProcess;
import android.os.Process;
import android.os.ZygoteProcess;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;

public class WebViewZygote {
    private static final String LOGTAG = "WebViewZygote";
    private static final Object sLock = new Object();
    @GuardedBy(value={"sLock"})
    private static boolean sMultiprocessEnabled = false;
    @GuardedBy(value={"sLock"})
    private static PackageInfo sPackage;
    @GuardedBy(value={"sLock"})
    private static ChildZygoteProcess sZygote;

    @GuardedBy(value={"sLock"})
    private static void connectToZygoteIfNeededLocked() {
        if (sZygote != null) {
            return;
        }
        Object object = sPackage;
        if (object == null) {
            Log.e(LOGTAG, "Cannot connect to zygote, no package specified");
            return;
        }
        try {
            object = object.applicationInfo.primaryCpuAbi;
            sZygote = Process.ZYGOTE_PROCESS.startChildZygote("com.android.internal.os.WebViewZygoteInit", "webview_zygote", 1053, 1053, null, 0, "webview_zygote", (String)object, TextUtils.join((CharSequence)",", Build.SUPPORTED_ABIS), null, 99000, Integer.MAX_VALUE);
            ZygoteProcess.waitForConnectionToZygote(sZygote.getPrimarySocketAddress());
            sZygote.preloadApp(WebViewZygote.sPackage.applicationInfo, (String)object);
        }
        catch (Exception exception) {
            Log.e(LOGTAG, "Error connecting to webview zygote", exception);
            WebViewZygote.stopZygoteLocked();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getPackageName() {
        Object object = sLock;
        synchronized (object) {
            return WebViewZygote.sPackage.packageName;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ZygoteProcess getProcess() {
        Object object = sLock;
        synchronized (object) {
            if (sZygote != null) {
                return sZygote;
            }
            WebViewZygote.connectToZygoteIfNeededLocked();
            return sZygote;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isMultiprocessEnabled() {
        Object object = sLock;
        synchronized (object) {
            if (!sMultiprocessEnabled) return false;
            if (sPackage == null) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void onWebViewProviderChanged(PackageInfo packageInfo) {
        Object object = sLock;
        synchronized (object) {
            sPackage = packageInfo;
            if (!sMultiprocessEnabled) {
                return;
            }
            WebViewZygote.stopZygoteLocked();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setMultiprocessEnabled(boolean bl) {
        Object object = sLock;
        synchronized (object) {
            sMultiprocessEnabled = bl;
            if (!bl) {
                WebViewZygote.stopZygoteLocked();
            }
            return;
        }
    }

    @GuardedBy(value={"sLock"})
    private static void stopZygoteLocked() {
        ChildZygoteProcess childZygoteProcess = sZygote;
        if (childZygoteProcess != null) {
            childZygoteProcess.close();
            Process.killProcess(sZygote.getPid());
            sZygote = null;
        }
    }
}

