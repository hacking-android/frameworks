/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.widget.Toast;
import java.util.Iterator;
import java.util.List;

public final class LocationAccessPolicy {
    private static final boolean DBG = false;
    public static final int MAX_SDK_FOR_ANY_ENFORCEMENT = 10000;
    private static final String TAG = "LocationAccessPolicy";

    private static LocationPermissionResult appOpsModeToPermissionResult(int n) {
        if (n != 0) {
            if (n != 2) {
                return LocationPermissionResult.DENIED_SOFT;
            }
            return LocationPermissionResult.DENIED_HARD;
        }
        return LocationPermissionResult.ALLOWED;
    }

    private static LocationPermissionResult checkAppLocationPermissionHelper(Context object, LocationPermissionQuery locationPermissionQuery, String charSequence) {
        String string2 = "android.permission.ACCESS_FINE_LOCATION".equals(charSequence) ? "fine" : "coarse";
        if (LocationAccessPolicy.checkManifestPermission((Context)object, locationPermissionQuery.callingPid, locationPermissionQuery.callingUid, (String)charSequence)) {
            int n = ((Context)object).getSystemService(AppOpsManager.class).noteOpNoThrow(AppOpsManager.permissionToOpCode((String)charSequence), locationPermissionQuery.callingUid, locationPermissionQuery.callingPackage);
            if (n == 0) {
                return LocationPermissionResult.ALLOWED;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(locationPermissionQuery.callingPackage);
            ((StringBuilder)object).append(" is aware of ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" but the app-ops permission is specifically denied.");
            Log.i(TAG, ((StringBuilder)object).toString());
            return LocationAccessPolicy.appOpsModeToPermissionResult(n);
        }
        int n = "android.permission.ACCESS_FINE_LOCATION".equals(charSequence) ? locationPermissionQuery.minSdkVersionForFine : locationPermissionQuery.minSdkVersionForCoarse;
        if (n > 10000) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Allowing ");
            ((StringBuilder)charSequence).append(locationPermissionQuery.callingPackage);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" because we're not enforcing API ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" yet. Please fix this app because it will break in the future. Called from ");
            ((StringBuilder)charSequence).append(locationPermissionQuery.method);
            LocationAccessPolicy.logError((Context)object, locationPermissionQuery, ((StringBuilder)charSequence).toString());
            return null;
        }
        if (!LocationAccessPolicy.isAppAtLeastSdkVersion((Context)object, locationPermissionQuery.callingPackage, n)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Allowing ");
            ((StringBuilder)charSequence).append(locationPermissionQuery.callingPackage);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" because it doesn't target API ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" yet. Please fix this app. Called from ");
            ((StringBuilder)charSequence).append(locationPermissionQuery.method);
            LocationAccessPolicy.logError((Context)object, locationPermissionQuery, ((StringBuilder)charSequence).toString());
            return null;
        }
        return LocationPermissionResult.DENIED_HARD;
    }

    private static boolean checkInteractAcrossUsersFull(Context context, int n, int n2) {
        return LocationAccessPolicy.checkManifestPermission(context, n, n2, "android.permission.INTERACT_ACROSS_USERS_FULL");
    }

    public static LocationPermissionResult checkLocationPermission(Context object, LocationPermissionQuery locationPermissionQuery) {
        if (locationPermissionQuery.callingUid != 1001 && locationPermissionQuery.callingUid != 1000 && locationPermissionQuery.callingUid != 0) {
            LocationPermissionResult locationPermissionResult;
            if (!LocationAccessPolicy.checkSystemLocationAccess(object, locationPermissionQuery.callingUid, locationPermissionQuery.callingPid)) {
                return LocationPermissionResult.DENIED_SOFT;
            }
            if (locationPermissionQuery.minSdkVersionForFine < Integer.MAX_VALUE && (locationPermissionResult = LocationAccessPolicy.checkAppLocationPermissionHelper(object, locationPermissionQuery, "android.permission.ACCESS_FINE_LOCATION")) != null) {
                return locationPermissionResult;
            }
            if (locationPermissionQuery.minSdkVersionForCoarse < Integer.MAX_VALUE && (object = LocationAccessPolicy.checkAppLocationPermissionHelper(object, locationPermissionQuery, "android.permission.ACCESS_COARSE_LOCATION")) != null) {
                return object;
            }
            return LocationPermissionResult.ALLOWED;
        }
        return LocationPermissionResult.ALLOWED;
    }

    private static boolean checkManifestPermission(Context context, int n, int n2, String string2) {
        boolean bl = context.checkPermission(string2, n, n2) == 0;
        return bl;
    }

    private static boolean checkSystemLocationAccess(Context context, int n, int n2) {
        boolean bl = LocationAccessPolicy.isLocationModeEnabled(context, UserHandle.getUserId(n));
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (LocationAccessPolicy.isCurrentProfile(context, n) || LocationAccessPolicy.checkInteractAcrossUsersFull(context, n, n2)) {
            bl2 = true;
        }
        return bl2;
    }

    private static boolean isAppAtLeastSdkVersion(Context context, String string2, int n) {
        try {
            int n2 = context.getPackageManager().getApplicationInfo((String)string2, (int)0).targetSdkVersion;
            if (n2 >= n) {
                return true;
            }
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            // empty catch block
        }
        return false;
    }

    private static boolean isCurrentProfile(Context object, int n) {
        long l;
        int n2;
        block4 : {
            l = Binder.clearCallingIdentity();
            n2 = ActivityManager.getCurrentUser();
            n = UserHandle.getUserId(n);
            if (n != n2) break block4;
            Binder.restoreCallingIdentity(l);
            return true;
        }
        try {
            object = ((Context)object).getSystemService(UserManager.class).getProfiles(n2).iterator();
            while (object.hasNext()) {
                n2 = ((UserInfo)object.next()).id;
                if (n2 != n) continue;
            }
        }
        catch (Throwable throwable) {
            Binder.restoreCallingIdentity(l);
            throw throwable;
        }
        {
            Binder.restoreCallingIdentity(l);
            return true;
        }
        Binder.restoreCallingIdentity(l);
        return false;
    }

    private static boolean isLocationModeEnabled(Context object, int n) {
        if ((object = ((Context)object).getSystemService(LocationManager.class)) == null) {
            Log.w(TAG, "Couldn't get location manager, denying location access");
            return false;
        }
        return ((LocationManager)object).isLocationEnabledForUser(UserHandle.of(n));
    }

    private static void logError(Context context, LocationPermissionQuery locationPermissionQuery, String string2) {
        if (locationPermissionQuery.logAsInfo) {
            Log.i(TAG, string2);
            return;
        }
        Log.e(TAG, string2);
        try {
            if (Build.IS_DEBUGGABLE) {
                Toast.makeText(context, string2, 0).show();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static class LocationPermissionQuery {
        public final String callingPackage;
        public final int callingPid;
        public final int callingUid;
        public final boolean logAsInfo;
        public final String method;
        public final int minSdkVersionForCoarse;
        public final int minSdkVersionForFine;

        private LocationPermissionQuery(String string2, int n, int n2, int n3, int n4, boolean bl, String string3) {
            this.callingPackage = string2;
            this.callingUid = n;
            this.callingPid = n2;
            this.minSdkVersionForCoarse = n3;
            this.minSdkVersionForFine = n4;
            this.logAsInfo = bl;
            this.method = string3;
        }

        public static class Builder {
            private String mCallingPackage;
            private int mCallingPid;
            private int mCallingUid;
            private boolean mLogAsInfo = false;
            private String mMethod;
            private int mMinSdkVersionForCoarse = Integer.MAX_VALUE;
            private int mMinSdkVersionForFine = Integer.MAX_VALUE;

            public LocationPermissionQuery build() {
                return new LocationPermissionQuery(this.mCallingPackage, this.mCallingUid, this.mCallingPid, this.mMinSdkVersionForCoarse, this.mMinSdkVersionForFine, this.mLogAsInfo, this.mMethod);
            }

            public Builder setCallingPackage(String string2) {
                this.mCallingPackage = string2;
                return this;
            }

            public Builder setCallingPid(int n) {
                this.mCallingPid = n;
                return this;
            }

            public Builder setCallingUid(int n) {
                this.mCallingUid = n;
                return this;
            }

            public Builder setLogAsInfo(boolean bl) {
                this.mLogAsInfo = bl;
                return this;
            }

            public Builder setMethod(String string2) {
                this.mMethod = string2;
                return this;
            }

            public Builder setMinSdkVersionForCoarse(int n) {
                this.mMinSdkVersionForCoarse = n;
                return this;
            }

            public Builder setMinSdkVersionForFine(int n) {
                this.mMinSdkVersionForFine = n;
                return this;
            }
        }

    }

    public static enum LocationPermissionResult {
        ALLOWED,
        DENIED_SOFT,
        DENIED_HARD;
        
    }

}

