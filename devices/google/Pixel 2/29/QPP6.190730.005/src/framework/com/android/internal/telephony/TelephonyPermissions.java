/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$TelephonyPermissions
 *  com.android.internal.telephony.-$$Lambda$TelephonyPermissions$LxEEC4irBSbjD1lSC4EeVLgFY9I
 */
package com.android.internal.telephony;

import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.util.StatsLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony._$$Lambda$TelephonyPermissions$LxEEC4irBSbjD1lSC4EeVLgFY9I;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class TelephonyPermissions {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "TelephonyPermissions";
    private static final String PROPERTY_DEVICE_IDENTIFIER_ACCESS_RESTRICTIONS_DISABLED = "device_identifier_access_restrictions_disabled";
    private static final Supplier<ITelephony> TELEPHONY_SUPPLIER = _$$Lambda$TelephonyPermissions$LxEEC4irBSbjD1lSC4EeVLgFY9I.INSTANCE;
    private static final Map<String, Set<String>> sReportedDeviceIDPackages = new HashMap<String, Set<String>>();

    private TelephonyPermissions() {
    }

    public static boolean checkCallingOrSelfReadDeviceIdentifiers(Context context, int n, String string2, String string3) {
        return TelephonyPermissions.checkReadDeviceIdentifiers(context, TELEPHONY_SUPPLIER, n, Binder.getCallingPid(), Binder.getCallingUid(), string2, string3);
    }

    public static boolean checkCallingOrSelfReadDeviceIdentifiers(Context context, String string2, String string3) {
        return TelephonyPermissions.checkCallingOrSelfReadDeviceIdentifiers(context, -1, string2, string3);
    }

    public static boolean checkCallingOrSelfReadPhoneNumber(Context context, int n, String string2, String string3) {
        return TelephonyPermissions.checkReadPhoneNumber(context, TELEPHONY_SUPPLIER, n, Binder.getCallingPid(), Binder.getCallingUid(), string2, string3);
    }

    public static boolean checkCallingOrSelfReadPhoneState(Context context, int n, String string2, String string3) {
        return TelephonyPermissions.checkReadPhoneState(context, n, Binder.getCallingPid(), Binder.getCallingUid(), string2, string3);
    }

    public static boolean checkCallingOrSelfReadPhoneStateNoThrow(Context context, int n, String string2, String string3) {
        try {
            boolean bl = TelephonyPermissions.checkCallingOrSelfReadPhoneState(context, n, string2, string3);
            return bl;
        }
        catch (SecurityException securityException) {
            return false;
        }
    }

    public static boolean checkCallingOrSelfReadSubscriberIdentifiers(Context context, int n, String string2, String string3) {
        return TelephonyPermissions.checkReadDeviceIdentifiers(context, TELEPHONY_SUPPLIER, n, Binder.getCallingPid(), Binder.getCallingUid(), string2, string3);
    }

    private static boolean checkCarrierPrivilegeForAnySubId(Context arrn, Supplier<ITelephony> supplier, int n) {
        if ((arrn = ((SubscriptionManager)arrn.getSystemService("telephony_subscription_service")).getActiveSubscriptionIdList()) != null) {
            int n2 = arrn.length;
            for (int i = 0; i < n2; ++i) {
                if (TelephonyPermissions.getCarrierPrivilegeStatus(supplier, arrn[i], n) != 1) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean checkCarrierPrivilegeForSubId(int n) {
        return SubscriptionManager.isValidSubscriptionId(n) && TelephonyPermissions.getCarrierPrivilegeStatus(TELEPHONY_SUPPLIER, n, Binder.getCallingUid()) == 1;
    }

    public static boolean checkReadCallLog(Context context, int n, int n2, int n3, String string2) {
        return TelephonyPermissions.checkReadCallLog(context, TELEPHONY_SUPPLIER, n, n2, n3, string2);
    }

    @VisibleForTesting
    public static boolean checkReadCallLog(Context context, Supplier<ITelephony> supplier, int n, int n2, int n3, String string2) {
        n2 = context.checkPermission("android.permission.READ_CALL_LOG", n2, n3);
        boolean bl = true;
        if (n2 != 0) {
            if (SubscriptionManager.isValidSubscriptionId(n)) {
                TelephonyPermissions.enforceCarrierPrivilege(supplier, n, n3, "readCallLog");
                return true;
            }
            return false;
        }
        if (((AppOpsManager)context.getSystemService("appops")).noteOp(6, n3, string2) != 0) {
            bl = false;
        }
        return bl;
    }

    @VisibleForTesting
    public static boolean checkReadDeviceIdentifiers(Context context, Supplier<ITelephony> object, int n, int n2, int n3, String string2, String string3) {
        block7 : {
            int n4 = UserHandle.getAppId(n3);
            if (n4 == 1000 || n4 == 0) break block7;
            if (context.checkPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", n2, n3) == 0) {
                return true;
            }
            if (TelephonyPermissions.checkCarrierPrivilegeForAnySubId(context, object, n3)) {
                return true;
            }
            if (string2 != null) {
                long l;
                block6 : {
                    l = Binder.clearCallingIdentity();
                    object = (AppOpsManager)context.getSystemService("appops");
                    try {
                        n4 = ((AppOpsManager)object).noteOpNoThrow("android:read_device_identifiers", n3, string2);
                        if (n4 != 0) break block6;
                    }
                    catch (Throwable throwable) {
                        Binder.restoreCallingIdentity(l);
                        throw throwable;
                    }
                    Binder.restoreCallingIdentity(l);
                    return true;
                }
                Binder.restoreCallingIdentity(l);
                object = (DevicePolicyManager)context.getSystemService("device_policy");
                if (object != null && ((DevicePolicyManager)object).checkDeviceIdentifierAccess(string2, n2, n3)) {
                    return true;
                }
            }
            return TelephonyPermissions.reportAccessDeniedToReadIdentifiers(context, n, n2, n3, string2, string3);
        }
        return true;
    }

    @VisibleForTesting
    public static boolean checkReadPhoneNumber(Context context, Supplier<ITelephony> supplier, int n, int n2, int n3, String string2, String string3) {
        AppOpsManager appOpsManager = (AppOpsManager)context.getSystemService("appops");
        if (appOpsManager.noteOp(15, n3, string2) == 0) {
            return true;
        }
        try {
            boolean bl = TelephonyPermissions.checkReadPhoneState(context, supplier, n, n2, n3, string2, string3);
            return bl;
        }
        catch (SecurityException securityException) {
            block11 : {
                boolean bl = false;
                boolean bl2 = false;
                try {
                    context.enforcePermission("android.permission.READ_SMS", n2, n3, string3);
                    n = AppOpsManager.permissionToOpCode("android.permission.READ_SMS");
                    if (n == -1) break block11;
                }
                catch (SecurityException securityException2) {
                    block12 : {
                        try {
                            context.enforcePermission("android.permission.READ_PHONE_NUMBERS", n2, n3, string3);
                            n = AppOpsManager.permissionToOpCode("android.permission.READ_PHONE_NUMBERS");
                            if (n == -1) break block12;
                        }
                        catch (SecurityException securityException3) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(string3);
                            stringBuilder.append(": Neither user ");
                            stringBuilder.append(n3);
                            stringBuilder.append(" nor current process has ");
                            stringBuilder.append("android.permission.READ_PHONE_STATE");
                            stringBuilder.append(", ");
                            stringBuilder.append("android.permission.READ_SMS");
                            stringBuilder.append(", or ");
                            stringBuilder.append("android.permission.READ_PHONE_NUMBERS");
                            throw new SecurityException(stringBuilder.toString());
                        }
                        n = appOpsManager.noteOp(n, n3, string2);
                        bl2 = bl;
                        if (n == 0) {
                            bl2 = true;
                        }
                        return bl2;
                    }
                    return true;
                }
                n = appOpsManager.noteOp(n, n3, string2);
                if (n == 0) {
                    bl2 = true;
                }
                return bl2;
            }
            return true;
        }
    }

    public static boolean checkReadPhoneState(Context context, int n, int n2, int n3, String string2, String string3) {
        return TelephonyPermissions.checkReadPhoneState(context, TELEPHONY_SUPPLIER, n, n2, n3, string2, string3);
    }

    @VisibleForTesting
    public static boolean checkReadPhoneState(Context context, Supplier<ITelephony> supplier, int n, int n2, int n3, String string2, String string3) {
        boolean bl = true;
        try {
            context.enforcePermission("android.permission.READ_PRIVILEGED_PHONE_STATE", n2, n3, string3);
            return true;
        }
        catch (SecurityException securityException) {
            try {
                context.enforcePermission("android.permission.READ_PHONE_STATE", n2, n3, string3);
            }
            catch (SecurityException securityException2) {
                if (SubscriptionManager.isValidSubscriptionId(n)) {
                    TelephonyPermissions.enforceCarrierPrivilege(supplier, n, n3, string3);
                    return true;
                }
                throw securityException2;
            }
            if (((AppOpsManager)context.getSystemService("appops")).noteOp(51, n3, string2) != 0) {
                bl = false;
            }
            return bl;
        }
    }

    public static boolean checkReadPhoneStateOnAnyActiveSub(Context context, int n, int n2, String string2, String string3) {
        return TelephonyPermissions.checkReadPhoneStateOnAnyActiveSub(context, TELEPHONY_SUPPLIER, n, n2, string2, string3);
    }

    @VisibleForTesting
    public static boolean checkReadPhoneStateOnAnyActiveSub(Context context, Supplier<ITelephony> supplier, int n, int n2, String string2, String string3) {
        boolean bl = true;
        try {
            context.enforcePermission("android.permission.READ_PRIVILEGED_PHONE_STATE", n, n2, string3);
            return true;
        }
        catch (SecurityException securityException) {
            try {
                context.enforcePermission("android.permission.READ_PHONE_STATE", n, n2, string3);
            }
            catch (SecurityException securityException2) {
                return TelephonyPermissions.checkCarrierPrivilegeForAnySubId(context, supplier, n2);
            }
            if (((AppOpsManager)context.getSystemService("appops")).noteOp(51, n2, string2) != 0) {
                bl = false;
            }
            return bl;
        }
    }

    public static void enforceCallingOrSelfCarrierPrivilege(int n, String string2) {
        TelephonyPermissions.enforceCarrierPrivilege(n, Binder.getCallingUid(), string2);
    }

    public static void enforceCallingOrSelfModifyPermissionOrCarrierPrivilege(Context context, int n, String string2) {
        if (context.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0) {
            return;
        }
        TelephonyPermissions.enforceCallingOrSelfCarrierPrivilege(n, string2);
    }

    private static void enforceCarrierPrivilege(int n, int n2, String string2) {
        TelephonyPermissions.enforceCarrierPrivilege(TELEPHONY_SUPPLIER, n, n2, string2);
    }

    private static void enforceCarrierPrivilege(Supplier<ITelephony> supplier, int n, int n2, String string2) {
        if (TelephonyPermissions.getCarrierPrivilegeStatus(supplier, n, n2) == 1) {
            return;
        }
        throw new SecurityException(string2);
    }

    public static void enforceShellOnly(int n, String string2) {
        if (n != 2000 && n != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(": Only shell user can call it");
            throw new SecurityException(stringBuilder.toString());
        }
    }

    public static void enforeceCallingOrSelfReadPhoneStatePermissionOrCarrierPrivilege(Context context, int n, String string2) {
        if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
            return;
        }
        TelephonyPermissions.enforceCallingOrSelfCarrierPrivilege(n, string2);
    }

    public static void enforeceCallingOrSelfReadPrivilegedPhoneStatePermissionOrCarrierPrivilege(Context context, int n, String string2) {
        if (context.checkCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE") == 0) {
            return;
        }
        TelephonyPermissions.enforceCallingOrSelfCarrierPrivilege(n, string2);
    }

    private static int getCarrierPrivilegeStatus(Supplier<ITelephony> object, int n, int n2) {
        if ((object = object.get()) != null) {
            try {
                n = object.getCarrierPrivilegeStatusForUid(n, n2);
                return n;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        Rlog.e(LOG_TAG, "Phone process is down, cannot check carrier privileges");
        return 0;
    }

    static /* synthetic */ ITelephony lambda$static$0() {
        return ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
    }

    private static boolean reportAccessDeniedToReadIdentifiers(Context object, int n, int n2, int n3, String string2, String string3) {
        ApplicationInfo applicationInfo;
        Object object2;
        boolean bl;
        boolean bl2;
        boolean bl3;
        block10 : {
            boolean bl4 = false;
            boolean bl5 = false;
            boolean bl6 = false;
            boolean bl7 = false;
            applicationInfo = null;
            bl2 = bl4;
            object2 = ((Context)object).getPackageManager().getApplicationInfoAsUser(string2, 0, UserHandle.getUserId(n3));
            bl = bl5;
            bl3 = bl7;
            if (object2 == null) break block10;
            bl = bl5;
            bl3 = bl7;
            bl2 = bl4;
            applicationInfo = object2;
            if (!((ApplicationInfo)object2).isSystemApp()) break block10;
            bl2 = true;
            bl5 = true;
            applicationInfo = object2;
            try {
                bl4 = ((ApplicationInfo)object2).isPrivilegedApp();
                bl = bl5;
                bl3 = bl7;
                if (!bl4) break block10;
                bl3 = true;
                bl = bl5;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception caught obtaining package info for package ");
                stringBuilder.append(string2);
                Log.e(LOG_TAG, stringBuilder.toString(), nameNotFoundException);
                bl3 = bl6;
            }
        }
        bl2 = bl;
        applicationInfo = object2;
        bl = sReportedDeviceIDPackages.containsKey(string2);
        if (!bl || !sReportedDeviceIDPackages.get(string2).contains(string3)) {
            if (!bl) {
                object2 = new HashSet();
                sReportedDeviceIDPackages.put(string2, (Set<String>)object2);
            } else {
                object2 = sReportedDeviceIDPackages.get(string2);
            }
            object2.add((String)string3);
            StatsLog.write(172, string2, string3, bl2, bl3);
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("reportAccessDeniedToReadIdentifiers:");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(":");
        ((StringBuilder)object2).append(string3);
        ((StringBuilder)object2).append(":isPreinstalled=");
        ((StringBuilder)object2).append(bl2);
        ((StringBuilder)object2).append(":isPrivApp=");
        ((StringBuilder)object2).append(bl3);
        Log.w(LOG_TAG, ((StringBuilder)object2).toString());
        if (applicationInfo != null && applicationInfo.targetSdkVersion < 29) {
            if (((Context)object).checkPermission("android.permission.READ_PHONE_STATE", n2, n3) == 0) {
                return false;
            }
            if (TelephonyPermissions.checkCarrierPrivilegeForSubId(n)) {
                return false;
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string3);
        ((StringBuilder)object).append(": The user ");
        ((StringBuilder)object).append(n3);
        ((StringBuilder)object).append(" does not meet the requirements to access device identifiers.");
        throw new SecurityException(((StringBuilder)object).toString());
    }
}

