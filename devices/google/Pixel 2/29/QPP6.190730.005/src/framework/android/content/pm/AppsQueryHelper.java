/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.app.AppGlobals;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;

public class AppsQueryHelper {
    public static int GET_APPS_WITH_INTERACT_ACROSS_USERS_PERM;
    public static int GET_IMES;
    public static int GET_NON_LAUNCHABLE_APPS;
    public static int GET_REQUIRED_FOR_SYSTEM_USER;
    private List<ApplicationInfo> mAllApps;
    private final IPackageManager mPackageManager;

    static {
        GET_NON_LAUNCHABLE_APPS = 1;
        GET_APPS_WITH_INTERACT_ACROSS_USERS_PERM = 2;
        GET_IMES = 4;
        GET_REQUIRED_FOR_SYSTEM_USER = 8;
    }

    public AppsQueryHelper() {
        this(AppGlobals.getPackageManager());
    }

    public AppsQueryHelper(IPackageManager iPackageManager) {
        this.mPackageManager = iPackageManager;
    }

    @VisibleForTesting
    protected List<ApplicationInfo> getAllApps(int n) {
        try {
            List list = this.mPackageManager.getInstalledApplications(8704, n).getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @VisibleForTesting
    protected List<PackageInfo> getPackagesHoldingPermission(String object, int n) {
        try {
            object = this.mPackageManager.getPackagesHoldingPermissions(new String[]{object}, 0, n).getList();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<String> queryApps(int n, boolean bl, UserHandle object) {
        Object object2;
        Object object3;
        int n2 = GET_NON_LAUNCHABLE_APPS;
        int n3 = 0;
        int n4 = (n & n2) > 0 ? 1 : 0;
        int n5 = (n & GET_APPS_WITH_INTERACT_ACROSS_USERS_PERM) > 0 ? 1 : 0;
        n2 = (n & GET_IMES) > 0 ? 1 : 0;
        if ((n & GET_REQUIRED_FOR_SYSTEM_USER) > 0) {
            n3 = 1;
        }
        if (this.mAllApps == null) {
            this.mAllApps = this.getAllApps(((UserHandle)object).getIdentifier());
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        if (n == 0) {
            n3 = this.mAllApps.size();
            for (n = 0; n < n3; ++n) {
                object = this.mAllApps.get(n);
                if (bl && !((ApplicationInfo)object).isSystemApp()) continue;
                arrayList.add(((ApplicationInfo)object).packageName);
            }
            return arrayList;
        }
        if (n4 != 0) {
            object3 = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER");
            object2 = this.queryIntentActivitiesAsUser((Intent)object3, ((UserHandle)object).getIdentifier());
            object3 = new ArraySet();
            n4 = object2.size();
            for (n = 0; n < n4; ++n) {
                ((ArraySet)object3).add(((ResolveInfo)object2.get((int)n)).activityInfo.packageName);
            }
            n4 = this.mAllApps.size();
            for (n = 0; n < n4; ++n) {
                object2 = this.mAllApps.get(n);
                if (bl && !((ApplicationInfo)object2).isSystemApp() || ((ArraySet)object3).contains(object2 = ((ApplicationInfo)object2).packageName)) continue;
                arrayList.add((String)object2);
            }
        }
        if (n5 != 0) {
            object2 = this.getPackagesHoldingPermission("android.permission.INTERACT_ACROSS_USERS", ((UserHandle)object).getIdentifier());
            n5 = object2.size();
            for (n = 0; n < n5; ++n) {
                object3 = (PackageInfo)object2.get(n);
                if (bl && !((PackageInfo)object3).applicationInfo.isSystemApp() || arrayList.contains(((PackageInfo)object3).packageName)) continue;
                arrayList.add(((PackageInfo)object3).packageName);
            }
        }
        if (n2 != 0) {
            object = this.queryIntentServicesAsUser(new Intent("android.view.InputMethod"), ((UserHandle)object).getIdentifier());
            n2 = object.size();
            for (n = 0; n < n2; ++n) {
                object3 = ((ResolveInfo)object.get((int)n)).serviceInfo;
                if (bl && !((ServiceInfo)object3).applicationInfo.isSystemApp() || arrayList.contains(((ServiceInfo)object3).packageName)) continue;
                arrayList.add(((ServiceInfo)object3).packageName);
            }
        }
        if (n3 != 0) {
            n3 = this.mAllApps.size();
            for (n = 0; n < n3; ++n) {
                object = this.mAllApps.get(n);
                if (bl && !((ApplicationInfo)object).isSystemApp() || !((ApplicationInfo)object).isRequiredForSystemUser()) continue;
                arrayList.add(((ApplicationInfo)object).packageName);
            }
        }
        return arrayList;
    }

    @VisibleForTesting
    protected List<ResolveInfo> queryIntentActivitiesAsUser(Intent object, int n) {
        try {
            object = this.mPackageManager.queryIntentActivities((Intent)object, null, 795136, n).getList();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @VisibleForTesting
    protected List<ResolveInfo> queryIntentServicesAsUser(Intent object, int n) {
        try {
            object = this.mPackageManager.queryIntentServices((Intent)object, null, 819328, n).getList();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

