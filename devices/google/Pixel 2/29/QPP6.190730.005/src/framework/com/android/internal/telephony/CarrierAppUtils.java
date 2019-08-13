/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.server.SystemConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CarrierAppUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "CarrierAppUtils";

    private CarrierAppUtils() {
    }

    public static void disableCarrierAppsUntilPrivileged(String string2, IPackageManager iPackageManager, ContentResolver contentResolver, int n) {
        synchronized (CarrierAppUtils.class) {
            SystemConfig systemConfig = SystemConfig.getInstance();
            ArraySet<String> arraySet = systemConfig.getDisabledUntilUsedPreinstalledCarrierApps();
            CarrierAppUtils.disableCarrierAppsUntilPrivileged(string2, iPackageManager, null, contentResolver, n, arraySet, systemConfig.getDisabledUntilUsedPreinstalledCarrierAssociatedApps());
            return;
        }
    }

    public static void disableCarrierAppsUntilPrivileged(String string2, IPackageManager iPackageManager, TelephonyManager telephonyManager, ContentResolver contentResolver, int n) {
        synchronized (CarrierAppUtils.class) {
            SystemConfig systemConfig = SystemConfig.getInstance();
            ArraySet<String> arraySet = systemConfig.getDisabledUntilUsedPreinstalledCarrierApps();
            CarrierAppUtils.disableCarrierAppsUntilPrivileged(string2, iPackageManager, telephonyManager, contentResolver, n, arraySet, systemConfig.getDisabledUntilUsedPreinstalledCarrierAssociatedApps());
            return;
        }
    }

    /*
     * Exception decompiling
     */
    @VisibleForTesting
    public static void disableCarrierAppsUntilPrivileged(String var0, IPackageManager var1_8, TelephonyManager var2_9, ContentResolver var3_10, int var4_11, ArraySet<String> var5_12, ArrayMap<String, List<String>> var6_13) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 17[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static ApplicationInfo getApplicationInfoIfSystemApp(IPackageManager object, int n, String string2) {
        block4 : {
            object = object.getApplicationInfo(string2, 536903680, n);
            if (object == null) break block4;
            try {
                boolean bl = ((ApplicationInfo)object).isSystemApp();
                if (bl) {
                    return object;
                }
            }
            catch (RemoteException remoteException) {
                Slog.w(TAG, "Could not reach PackageManager", remoteException);
            }
        }
        return null;
    }

    public static List<ApplicationInfo> getDefaultCarrierAppCandidates(IPackageManager iPackageManager, int n) {
        return CarrierAppUtils.getDefaultCarrierAppCandidatesHelper(iPackageManager, n, SystemConfig.getInstance().getDisabledUntilUsedPreinstalledCarrierApps());
    }

    private static List<ApplicationInfo> getDefaultCarrierAppCandidatesHelper(IPackageManager iPackageManager, int n, ArraySet<String> arraySet) {
        if (arraySet == null) {
            return null;
        }
        int n2 = arraySet.size();
        if (n2 == 0) {
            return null;
        }
        ArrayList<ApplicationInfo> arrayList = new ArrayList<ApplicationInfo>(n2);
        for (int i = 0; i < n2; ++i) {
            Object object = arraySet.valueAt(i);
            if ((object = CarrierAppUtils.getApplicationInfoIfSystemApp(iPackageManager, n, (String)object)) == null) continue;
            arrayList.add((ApplicationInfo)object);
        }
        return arrayList;
    }

    public static List<ApplicationInfo> getDefaultCarrierApps(IPackageManager object, TelephonyManager telephonyManager, int n) {
        if ((object = CarrierAppUtils.getDefaultCarrierAppCandidates((IPackageManager)object, n)) != null && !object.isEmpty()) {
            for (n = object.size() - 1; n >= 0; --n) {
                String string2 = ((ApplicationInfo)object.get((int)n)).packageName;
                boolean bl = telephonyManager.checkCarrierPrivilegesForPackageAnyPhone(string2) == 1;
                if (bl) continue;
                object.remove(n);
            }
            return object;
        }
        return null;
    }

    private static Map<String, List<ApplicationInfo>> getDefaultCarrierAssociatedAppsHelper(IPackageManager iPackageManager, int n, ArrayMap<String, List<String>> arrayMap) {
        int n2 = arrayMap.size();
        ArrayMap<String, List<ApplicationInfo>> arrayMap2 = new ArrayMap<String, List<ApplicationInfo>>(n2);
        for (int i = 0; i < n2; ++i) {
            String string2 = arrayMap.keyAt(i);
            List<String> list = arrayMap.valueAt(i);
            for (int j = 0; j < list.size(); ++j) {
                ArrayList<ApplicationInfo> arrayList;
                ApplicationInfo applicationInfo = CarrierAppUtils.getApplicationInfoIfSystemApp(iPackageManager, n, list.get(j));
                if (applicationInfo == null || applicationInfo.isUpdatedSystemApp()) continue;
                ArrayList<ApplicationInfo> arrayList2 = arrayList = (ArrayList<ApplicationInfo>)arrayMap2.get(string2);
                if (arrayList == null) {
                    arrayList2 = new ArrayList<ApplicationInfo>();
                    arrayMap2.put(string2, arrayList2);
                }
                arrayList2.add(applicationInfo);
            }
        }
        return arrayMap2;
    }
}

