/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.app.AppGlobals;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ParceledListSlice;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.annotations.VisibleForTesting;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class AppIdToPackageMap {
    private final Map<Integer, String> mAppIdToPackageMap;

    @VisibleForTesting
    public AppIdToPackageMap(Map<Integer, String> map) {
        this.mAppIdToPackageMap = map;
    }

    public static AppIdToPackageMap getSnapshot() {
        HashMap<Integer, String> hashMap;
        Object object;
        try {
            object = AppGlobals.getPackageManager().getInstalledPackages(794624, 0).getList();
            hashMap = new HashMap<Integer, String>();
            object = object.iterator();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        while (object.hasNext()) {
            PackageInfo packageInfo = (PackageInfo)object.next();
            int n = packageInfo.applicationInfo.uid;
            if (packageInfo.sharedUserId != null && hashMap.containsKey(n)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("shared:");
                stringBuilder.append(packageInfo.sharedUserId);
                hashMap.put(n, stringBuilder.toString());
                continue;
            }
            hashMap.put(n, packageInfo.packageName);
        }
        return new AppIdToPackageMap(hashMap);
    }

    public String mapAppId(int n) {
        String string2;
        block0 : {
            string2 = this.mAppIdToPackageMap.get(n);
            if (string2 != null) break block0;
            string2 = String.valueOf(n);
        }
        return string2;
    }

    public String mapUid(int n) {
        int n2 = UserHandle.getAppId(n);
        String string2 = this.mAppIdToPackageMap.get(n2);
        String string3 = UserHandle.formatUid(n);
        if (string2 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append('/');
            stringBuilder.append(string3);
            string3 = stringBuilder.toString();
        }
        return string3;
    }
}

