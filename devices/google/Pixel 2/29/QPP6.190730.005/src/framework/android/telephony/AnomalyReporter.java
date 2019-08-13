/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.telephony.Rlog;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class AnomalyReporter {
    private static final String TAG = "AnomalyReporter";
    private static Context sContext = null;
    private static String sDebugPackageName;
    private static Map<UUID, Integer> sEvents;

    static {
        sEvents = new ConcurrentHashMap<UUID, Integer>();
        sDebugPackageName = null;
    }

    private AnomalyReporter() {
    }

    public static void dump(FileDescriptor object, PrintWriter printWriter, String[] object2) {
        if (sContext == null) {
            return;
        }
        printWriter = new IndentingPrintWriter(printWriter, "  ");
        sContext.enforceCallingOrSelfPermission("android.permission.DUMP", "Requires DUMP");
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Initialized=");
        object = sContext != null ? "Yes" : "No";
        ((StringBuilder)object2).append((String)object);
        printWriter.println(((StringBuilder)object2).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("Debug Package=");
        ((StringBuilder)object).append(sDebugPackageName);
        printWriter.println(((StringBuilder)object).toString());
        printWriter.println("Anomaly Counts:");
        ((IndentingPrintWriter)printWriter).increaseIndent();
        for (UUID uUID : sEvents.keySet()) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(uUID);
            ((StringBuilder)object2).append(": ");
            ((StringBuilder)object2).append(sEvents.get(uUID));
            printWriter.println(((StringBuilder)object2).toString());
        }
        ((IndentingPrintWriter)printWriter).decreaseIndent();
        printWriter.flush();
    }

    public static void initialize(Context list2) {
        if (list2 != null) {
            ((Context)((Object)list2)).enforceCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE", "This app does not have privileges to send debug events");
            sContext = list2;
            Object object = sContext.getPackageManager();
            if (object == null) {
                return;
            }
            List<ResolveInfo> list = ((PackageManager)object).queryBroadcastReceivers(new Intent("android.telephony.action.ANOMALY_REPORTED"), 1835008);
            if (list != null && !list.isEmpty()) {
                if (list.size() > 1) {
                    Rlog.e(TAG, "Multiple Anomaly Receivers installed.");
                }
                for (ResolveInfo resolveInfo : list) {
                    if (resolveInfo.activityInfo != null && ((PackageManager)object).checkPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", resolveInfo.activityInfo.packageName) == 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Found a valid package ");
                        ((StringBuilder)object).append(resolveInfo.activityInfo.packageName);
                        Rlog.d(TAG, ((StringBuilder)object).toString());
                        sDebugPackageName = resolveInfo.activityInfo.packageName;
                        break;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Found package without proper permissions or no activity");
                    stringBuilder.append(resolveInfo.activityInfo.packageName);
                    Rlog.w(TAG, stringBuilder.toString());
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("AnomalyReporter needs a non-null context.");
    }

    public static void reportAnomaly(UUID uUID, String charSequence) {
        if (sContext == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("AnomalyReporter not yet initialized, dropping event=");
            ((StringBuilder)charSequence).append(uUID);
            Rlog.w(TAG, ((StringBuilder)charSequence).toString());
            return;
        }
        int n = sEvents.containsKey(uUID) ? sEvents.get(uUID) + 1 : 1;
        Object object = n;
        sEvents.put(uUID, (Integer)object);
        if ((Integer)object > 1) {
            return;
        }
        if (sDebugPackageName == null) {
            return;
        }
        object = new Intent("android.telephony.action.ANOMALY_REPORTED");
        ((Intent)object).putExtra("android.telephony.extra.ANOMALY_ID", new ParcelUuid(uUID));
        if (charSequence != null) {
            ((Intent)object).putExtra("android.telephony.extra.ANOMALY_DESCRIPTION", (String)charSequence);
        }
        ((Intent)object).setPackage(sDebugPackageName);
        sContext.sendBroadcast((Intent)object, "android.permission.READ_PRIVILEGED_PHONE_STATE");
    }
}

