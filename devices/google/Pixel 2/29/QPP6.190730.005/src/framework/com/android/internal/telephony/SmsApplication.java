/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.app.AppOpsManager;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;
import android.telephony.Rlog;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.content.PackageMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.telephony._$$Lambda$SmsApplication$gDx3W_UsTeTFaBSPU_Y_LFPZ9dE;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public final class SmsApplication {
    private static final String BLUETOOTH_PACKAGE_NAME = "com.android.bluetooth";
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_MULTIUSER = false;
    private static final int[] DEFAULT_APP_EXCLUSIVE_APPOPS = new int[]{14, 15, 16, 19, 20, 57};
    static final String LOG_TAG = "SmsApplication";
    private static final String MMS_SERVICE_PACKAGE_NAME = "com.android.mms.service";
    private static final String PHONE_PACKAGE_NAME = "com.android.phone";
    private static final String SCHEME_MMS = "mms";
    private static final String SCHEME_MMSTO = "mmsto";
    private static final String SCHEME_SMS = "sms";
    private static final String SCHEME_SMSTO = "smsto";
    private static final String TELEPHONY_PROVIDER_PACKAGE_NAME = "com.android.providers.telephony";
    private static SmsPackageMonitor sSmsPackageMonitor = null;

    private static void assignExclusiveSmsPermissionsToSystemApp(Context object, PackageManager object2, AppOpsManager appOpsManager, String string2) {
        if (((PackageManager)object2).checkSignatures(((Context)object).getPackageName(), string2) != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" does not have system signature");
            Rlog.e(LOG_TAG, ((StringBuilder)object).toString());
            return;
        }
        try {
            object = ((PackageManager)object2).getPackageInfo(string2, 0);
            if (appOpsManager.checkOp(15, object.applicationInfo.uid, string2) != 0) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append(" does not have OP_WRITE_SMS:  (fixing)");
                Rlog.w(LOG_TAG, ((StringBuilder)object2).toString());
                SmsApplication.setExclusiveAppops(string2, appOpsManager, object.applicationInfo.uid, 0);
            }
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Package not found: ");
            stringBuilder.append(string2);
            Rlog.e(LOG_TAG, stringBuilder.toString());
        }
    }

    private static void broadcastSmsAppChange(Context context, UserHandle userHandle, SmsApplicationData object, SmsApplicationData smsApplicationData) {
        if (object != null && ((SmsApplicationData)object).mSmsAppChangedReceiverClass != null) {
            Intent intent = new Intent("android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED");
            intent.setComponent(new ComponentName(((SmsApplicationData)object).mPackageName, ((SmsApplicationData)object).mSmsAppChangedReceiverClass));
            intent.putExtra("android.provider.extra.IS_DEFAULT_SMS_APP", false);
            context.sendBroadcastAsUser(intent, userHandle);
        }
        if (smsApplicationData != null && smsApplicationData.mSmsAppChangedReceiverClass != null) {
            object = new Intent("android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED");
            ((Intent)object).setComponent(new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mSmsAppChangedReceiverClass));
            ((Intent)object).putExtra("android.provider.extra.IS_DEFAULT_SMS_APP", true);
            context.sendBroadcastAsUser((Intent)object, userHandle);
        }
        context.sendBroadcastAsUser(new Intent("android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL"), userHandle, "android.permission.MONITOR_DEFAULT_SMS_PACKAGE");
        if (smsApplicationData != null) {
            MetricsLogger.action(context, 266, smsApplicationData.mPackageName);
        }
    }

    public static void broadcastSmsAppChange(Context context, UserHandle userHandle, String string2, String string3) {
        Collection<SmsApplicationData> collection = SmsApplication.getApplicationCollection(context);
        SmsApplication.broadcastSmsAppChange(context, userHandle, SmsApplication.getApplicationForPackage(collection, string2), SmsApplication.getApplicationForPackage(collection, string3));
    }

    private static void configurePreferredActivity(PackageManager packageManager, ComponentName componentName, int n) {
        SmsApplication.replacePreferredActivity(packageManager, componentName, n, SCHEME_SMS);
        SmsApplication.replacePreferredActivity(packageManager, componentName, n, SCHEME_SMSTO);
        SmsApplication.replacePreferredActivity(packageManager, componentName, n, SCHEME_MMS);
        SmsApplication.replacePreferredActivity(packageManager, componentName, n, SCHEME_MMSTO);
    }

    private static void defaultSmsAppChanged(Context arrn) {
        PackageManager packageManager = arrn.getPackageManager();
        AppOpsManager appOpsManager = arrn.getSystemService(AppOpsManager.class);
        SmsApplication.assignExclusiveSmsPermissionsToSystemApp((Context)arrn, packageManager, appOpsManager, PHONE_PACKAGE_NAME);
        SmsApplication.assignExclusiveSmsPermissionsToSystemApp((Context)arrn, packageManager, appOpsManager, BLUETOOTH_PACKAGE_NAME);
        SmsApplication.assignExclusiveSmsPermissionsToSystemApp((Context)arrn, packageManager, appOpsManager, MMS_SERVICE_PACKAGE_NAME);
        SmsApplication.assignExclusiveSmsPermissionsToSystemApp((Context)arrn, packageManager, appOpsManager, TELEPHONY_PROVIDER_PACKAGE_NAME);
        arrn = DEFAULT_APP_EXCLUSIVE_APPOPS;
        int n = arrn.length;
        for (int i = 0; i < n; ++i) {
            appOpsManager.setUidMode(arrn[i], 1001, 0);
        }
    }

    private static SmsApplicationData getApplication(Context context, boolean bl, int n) {
        Object object;
        block8 : {
            Object object2;
            block10 : {
                Object object3;
                block9 : {
                    object3 = (TelephonyManager)context.getSystemService("phone");
                    object2 = (RoleManager)context.getSystemService("role");
                    if (!(((TelephonyManager)object3).isSmsCapable() || object2 != null && ((RoleManager)object2).isRoleAvailable("android.app.role.SMS"))) {
                        return null;
                    }
                    object = SmsApplication.getApplicationCollectionInternal(context, n);
                    object2 = SmsApplication.getDefaultSmsPackage(context, n);
                    object3 = null;
                    if (object2 != null) {
                        object3 = SmsApplication.getApplicationForPackage(object, (String)object2);
                    }
                    object = object3;
                    if (object3 == null) break block8;
                    if (bl) break block9;
                    object2 = object3;
                    if (((SmsApplicationData)object3).mUid != Process.myUid()) break block10;
                }
                object2 = object3;
                if (!SmsApplication.tryFixExclusiveSmsAppops(context, (SmsApplicationData)object3, bl)) {
                    object2 = null;
                }
            }
            object = object2;
            if (object2 != null) {
                object = object2;
                if (bl) {
                    SmsApplication.defaultSmsAppChanged(context);
                    object = object2;
                }
            }
        }
        return object;
    }

    public static Collection<SmsApplicationData> getApplicationCollection(Context context) {
        return SmsApplication.getApplicationCollectionAsUser(context, SmsApplication.getIncomingUserId(context));
    }

    public static Collection<SmsApplicationData> getApplicationCollectionAsUser(Context object, int n) {
        long l = Binder.clearCallingIdentity();
        try {
            object = SmsApplication.getApplicationCollectionInternal((Context)object, n);
            return object;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    private static Collection<SmsApplicationData> getApplicationCollectionInternal(Context iterator, int n) {
        Iterator<ResolveInfo> iterator2;
        Object object;
        Object object2 = ((Context)((Object)iterator)).getPackageManager();
        iterator = ((PackageManager)object2).queryBroadcastReceiversAsUser(new Intent("android.provider.Telephony.SMS_DELIVER"), 786432, n);
        HashMap<Iterator<ResolveInfo>, SmsApplicationData> hashMap = new HashMap<Iterator<ResolveInfo>, SmsApplicationData>();
        Object object3 = iterator.iterator();
        while (object3.hasNext()) {
            ActivityInfo activityInfo = ((ResolveInfo)object3.next()).activityInfo;
            if (activityInfo == null || !"android.permission.BROADCAST_SMS".equals(activityInfo.permission) || hashMap.containsKey(iterator2 = activityInfo.packageName)) continue;
            object = new SmsApplicationData((String)((Object)iterator2), activityInfo.applicationInfo.uid);
            ((SmsApplicationData)object).mSmsReceiverClass = activityInfo.name;
            hashMap.put(iterator2, (SmsApplicationData)object);
        }
        object3 = new Intent("android.provider.Telephony.WAP_PUSH_DELIVER");
        ((Intent)object3).setDataAndType(null, "application/vnd.wap.mms-message");
        iterator2 = ((PackageManager)object2).queryBroadcastReceiversAsUser((Intent)object3, 786432, n).iterator();
        while (iterator2.hasNext()) {
            object3 = ((ResolveInfo)iterator2.next()).activityInfo;
            if (object3 == null || !"android.permission.BROADCAST_WAP_PUSH".equals(((ActivityInfo)object3).permission) || (object = (SmsApplicationData)hashMap.get(((ActivityInfo)object3).packageName)) == null) continue;
            ((SmsApplicationData)object).mMmsReceiverClass = ((ActivityInfo)object3).name;
        }
        iterator2 = ((PackageManager)object2).queryIntentServicesAsUser(new Intent("android.intent.action.RESPOND_VIA_MESSAGE", Uri.fromParts(SCHEME_SMSTO, "", null)), 786432, n).iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next().serviceInfo;
            if (object == null || !"android.permission.SEND_RESPOND_VIA_MESSAGE".equals(((ServiceInfo)object).permission) || (object3 = (SmsApplicationData)hashMap.get(((ServiceInfo)object).packageName)) == null) continue;
            ((SmsApplicationData)object3).mRespondViaMessageClass = ((ServiceInfo)object).name;
        }
        iterator2 = ((PackageManager)object2).queryIntentActivitiesAsUser(new Intent("android.intent.action.SENDTO", Uri.fromParts(SCHEME_SMSTO, "", null)), 786432, n).iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next().activityInfo;
            if (object == null || (object3 = (SmsApplicationData)hashMap.get(((ActivityInfo)object).packageName)) == null) continue;
            ((SmsApplicationData)object3).mSendToClass = ((ActivityInfo)object).name;
        }
        object3 = new Intent("android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED");
        iterator2 = ((PackageManager)object2).queryBroadcastReceiversAsUser((Intent)object3, 786432, n).iterator();
        while (iterator2.hasNext()) {
            object3 = iterator2.next().activityInfo;
            if (object3 == null || (object = (SmsApplicationData)hashMap.get(((ActivityInfo)object3).packageName)) == null) continue;
            ((SmsApplicationData)object).mSmsAppChangedReceiverClass = ((ActivityInfo)object3).name;
        }
        object3 = new Intent("android.provider.action.EXTERNAL_PROVIDER_CHANGE");
        iterator2 = ((PackageManager)object2).queryBroadcastReceiversAsUser((Intent)object3, 786432, n).iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next().activityInfo;
            if (object == null || (object3 = (SmsApplicationData)hashMap.get(((ActivityInfo)object).packageName)) == null) continue;
            ((SmsApplicationData)object3).mProviderChangedReceiverClass = ((ActivityInfo)object).name;
        }
        object3 = new Intent("android.provider.Telephony.SIM_FULL");
        object2 = ((PackageManager)object2).queryBroadcastReceiversAsUser((Intent)object3, 786432, n).iterator();
        while (object2.hasNext()) {
            object = ((ResolveInfo)object2.next()).activityInfo;
            if (object == null || (object3 = (SmsApplicationData)hashMap.get(((ActivityInfo)object).packageName)) == null) continue;
            ((SmsApplicationData)object3).mSimFullReceiverClass = ((ActivityInfo)object).name;
        }
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            object2 = ((ResolveInfo)iterator.next()).activityInfo;
            if (object2 == null || (object = (SmsApplicationData)hashMap.get(object3 = ((ActivityInfo)object2).packageName)) == null || ((SmsApplicationData)object).isComplete()) continue;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Package ");
            ((StringBuilder)object2).append((String)object3);
            ((StringBuilder)object2).append(" lacks required manifest declarations to be a default sms app: ");
            ((StringBuilder)object2).append(object);
            Log.w(LOG_TAG, ((StringBuilder)object2).toString());
            hashMap.remove(object3);
        }
        return hashMap.values();
    }

    public static SmsApplicationData getApplicationForPackage(Collection<SmsApplicationData> object, String string2) {
        if (string2 == null) {
            return null;
        }
        Iterator<SmsApplicationData> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (!((SmsApplicationData)object).mPackageName.contentEquals(string2)) continue;
            return object;
        }
        return null;
    }

    public static ComponentName getDefaultExternalTelephonyProviderChangedApplication(Context object, boolean bl) {
        long l;
        block4 : {
            int n = SmsApplication.getIncomingUserId((Context)object);
            l = Binder.clearCallingIdentity();
            Object var5_5 = null;
            SmsApplicationData smsApplicationData = SmsApplication.getApplication((Context)object, bl, n);
            object = var5_5;
            if (smsApplicationData == null) break block4;
            object = var5_5;
            if (smsApplicationData.mProviderChangedReceiverClass == null) break block4;
            object = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mProviderChangedReceiverClass);
        }
        return object;
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    public static ComponentName getDefaultMmsApplication(Context object, boolean bl) {
        long l;
        block4 : {
            int n = SmsApplication.getIncomingUserId((Context)object);
            l = Binder.clearCallingIdentity();
            Object var5_5 = null;
            SmsApplicationData smsApplicationData = SmsApplication.getApplication((Context)object, bl, n);
            object = var5_5;
            if (smsApplicationData == null) break block4;
            object = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mMmsReceiverClass);
        }
        return object;
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    public static ComponentName getDefaultRespondViaMessageApplication(Context object, boolean bl) {
        long l;
        block4 : {
            int n = SmsApplication.getIncomingUserId((Context)object);
            l = Binder.clearCallingIdentity();
            Object var5_5 = null;
            SmsApplicationData smsApplicationData = SmsApplication.getApplication((Context)object, bl, n);
            object = var5_5;
            if (smsApplicationData == null) break block4;
            object = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mRespondViaMessageClass);
        }
        return object;
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    public static ComponentName getDefaultSendToApplication(Context object, boolean bl) {
        long l;
        block4 : {
            int n = SmsApplication.getIncomingUserId((Context)object);
            l = Binder.clearCallingIdentity();
            Object var5_5 = null;
            SmsApplicationData smsApplicationData = SmsApplication.getApplication((Context)object, bl, n);
            object = var5_5;
            if (smsApplicationData == null) break block4;
            object = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mSendToClass);
        }
        return object;
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    public static ComponentName getDefaultSimFullApplication(Context object, boolean bl) {
        long l;
        block4 : {
            int n = SmsApplication.getIncomingUserId((Context)object);
            l = Binder.clearCallingIdentity();
            Object var5_5 = null;
            SmsApplicationData smsApplicationData = SmsApplication.getApplication((Context)object, bl, n);
            object = var5_5;
            if (smsApplicationData == null) break block4;
            object = var5_5;
            if (smsApplicationData.mSimFullReceiverClass == null) break block4;
            object = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mSimFullReceiverClass);
        }
        return object;
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    public static ComponentName getDefaultSmsApplication(Context context, boolean bl) {
        return SmsApplication.getDefaultSmsApplicationAsUser(context, bl, SmsApplication.getIncomingUserId(context));
    }

    public static ComponentName getDefaultSmsApplicationAsUser(Context object, boolean bl, int n) {
        long l;
        block4 : {
            l = Binder.clearCallingIdentity();
            Object var5_5 = null;
            SmsApplicationData smsApplicationData = SmsApplication.getApplication((Context)object, bl, n);
            object = var5_5;
            if (smsApplicationData == null) break block4;
            object = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mSmsReceiverClass);
        }
        return object;
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    private static String getDefaultSmsApplicationPackageName(Context object) {
        if ((object = SmsApplication.getDefaultSmsApplication((Context)object, false)) != null) {
            return ((ComponentName)object).getPackageName();
        }
        return null;
    }

    private static String getDefaultSmsPackage(Context context, int n) {
        return context.getSystemService(RoleManager.class).getDefaultSmsPackage(n);
    }

    private static int getIncomingUserId(Context context) {
        int n = context.getUserId();
        int n2 = Binder.getCallingUid();
        if (UserHandle.getAppId(n2) < 10000) {
            return n;
        }
        return UserHandle.getUserId(n2);
    }

    public static SmsApplicationData getSmsApplicationData(String string2, Context context) {
        return SmsApplication.getApplicationForPackage(SmsApplication.getApplicationCollection(context), string2);
    }

    public static void initSmsPackageMonitor(Context context) {
        sSmsPackageMonitor = new SmsPackageMonitor(context);
        sSmsPackageMonitor.register(context, context.getMainLooper(), UserHandle.ALL, false);
    }

    public static boolean isDefaultSmsApplication(Context object, String string2) {
        if (string2 == null) {
            return false;
        }
        return (object = SmsApplication.getDefaultSmsApplicationPackageName((Context)object)) != null && ((String)object).equals(string2) || BLUETOOTH_PACKAGE_NAME.equals(string2);
    }

    static /* synthetic */ void lambda$setDefaultApplicationInternal$0(CompletableFuture completableFuture, Boolean bl) {
        if (bl.booleanValue()) {
            completableFuture.complete(null);
        } else {
            completableFuture.completeExceptionally(new RuntimeException());
        }
    }

    private static void replacePreferredActivity(PackageManager packageManager, ComponentName componentName, int n, String string2) {
        Parcelable parcelable;
        List<ResolveInfo> list = packageManager.queryIntentActivitiesAsUser(new Intent("android.intent.action.SENDTO", Uri.fromParts(string2, "", null)), 65600, n);
        int n2 = list.size();
        ComponentName[] arrcomponentName = new ComponentName[n2];
        for (int i = 0; i < n2; ++i) {
            parcelable = list.get(i);
            arrcomponentName[i] = new ComponentName(parcelable.activityInfo.packageName, parcelable.activityInfo.name);
        }
        parcelable = new IntentFilter();
        ((IntentFilter)parcelable).addAction("android.intent.action.SENDTO");
        ((IntentFilter)parcelable).addCategory("android.intent.category.DEFAULT");
        ((IntentFilter)parcelable).addDataScheme(string2);
        packageManager.replacePreferredActivityAsUser((IntentFilter)parcelable, 2129920, arrcomponentName, componentName, n);
    }

    public static void setDefaultApplication(String string2, Context context) {
        SmsApplication.setDefaultApplicationAsUser(string2, context, SmsApplication.getIncomingUserId(context));
    }

    public static void setDefaultApplicationAsUser(String string2, Context context, int n) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        RoleManager roleManager = (RoleManager)context.getSystemService("role");
        if (!(telephonyManager.isSmsCapable() || roleManager != null && roleManager.isRoleAvailable("android.app.role.SMS"))) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            SmsApplication.setDefaultApplicationInternal(string2, context, n);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    private static void setDefaultApplicationInternal(String object, Context object2, int n) {
        UserHandle.of(n);
        Object object3 = SmsApplication.getDefaultSmsPackage((Context)object2, n);
        if (object != null && object3 != null && ((String)object).equals(object3)) {
            return;
        }
        PackageManager packageManager = ((Context)object2).getPackageManager();
        Collection<SmsApplicationData> collection = SmsApplication.getApplicationCollectionInternal((Context)object2, n);
        Object object4 = object3 != null ? SmsApplication.getApplicationForPackage(collection, (String)object3) : null;
        if ((object = SmsApplication.getApplicationForPackage(collection, (String)object)) != null) {
            object4 = (AppOpsManager)((Context)object2).getSystemService("appops");
            if (object3 != null) {
                try {
                    SmsApplication.setExclusiveAppops((String)object3, (AppOpsManager)object4, packageManager.getPackageInfoAsUser((String)object3, (int)0, (int)n).applicationInfo.uid, 3);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append("Old SMS package not found: ");
                    ((StringBuilder)object4).append((String)object3);
                    Rlog.w(LOG_TAG, ((StringBuilder)object4).toString());
                }
            }
            object3 = new CompletableFuture();
            object4 = new _$$Lambda$SmsApplication$gDx3W_UsTeTFaBSPU_Y_LFPZ9dE((CompletableFuture)object3);
            ((Context)object2).getSystemService(RoleManager.class).addRoleHolderAsUser("android.app.role.SMS", ((SmsApplicationData)object).mPackageName, 0, UserHandle.of(n), AsyncTask.THREAD_POOL_EXECUTOR, (Consumer<Boolean>)object4);
            try {
                ((CompletableFuture)object3).get(5L, TimeUnit.SECONDS);
            }
            catch (InterruptedException | ExecutionException | TimeoutException exception) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Exception while adding sms role holder ");
                ((StringBuilder)object2).append(object);
                Log.e(LOG_TAG, ((StringBuilder)object2).toString(), exception);
                return;
            }
            SmsApplication.defaultSmsAppChanged((Context)object2);
        }
    }

    private static void setExclusiveAppops(String arrn, AppOpsManager appOpsManager, int n, int n2) {
        arrn = DEFAULT_APP_EXCLUSIVE_APPOPS;
        int n3 = arrn.length;
        for (int i = 0; i < n3; ++i) {
            appOpsManager.setUidMode(arrn[i], n, n2);
        }
    }

    public static boolean shouldWriteMessageForPackage(String string2, Context context) {
        if (SmsManager.getDefault().getAutoPersisting()) {
            return true;
        }
        return SmsApplication.isDefaultSmsApplication(context, string2) ^ true;
    }

    private static boolean tryFixExclusiveSmsAppops(Context object, SmsApplicationData smsApplicationData, boolean bl) {
        AppOpsManager appOpsManager = ((Context)object).getSystemService(AppOpsManager.class);
        for (int n : DEFAULT_APP_EXCLUSIVE_APPOPS) {
            if (appOpsManager.checkOp(n, smsApplicationData.mUid, smsApplicationData.mPackageName) == 0) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(smsApplicationData.mPackageName);
            stringBuilder.append(" lost ");
            stringBuilder.append(AppOpsManager.modeToName(n));
            stringBuilder.append(": ");
            object = bl ? " (fixing)" : " (no permission to fix)";
            stringBuilder.append((String)object);
            Rlog.e(LOG_TAG, stringBuilder.toString());
            if (bl) {
                appOpsManager.setUidMode(n, smsApplicationData.mUid, 0);
                continue;
            }
            return false;
        }
        return true;
    }

    public static class SmsApplicationData {
        private String mApplicationName;
        private String mMmsReceiverClass;
        public String mPackageName;
        private String mProviderChangedReceiverClass;
        private String mRespondViaMessageClass;
        private String mSendToClass;
        private String mSimFullReceiverClass;
        private String mSmsAppChangedReceiverClass;
        private String mSmsReceiverClass;
        private int mUid;

        public SmsApplicationData(String string2, int n) {
            this.mPackageName = string2;
            this.mUid = n;
        }

        public String getApplicationName(Context object) {
            if (this.mApplicationName == null) {
                PackageManager packageManager = ((Context)object).getPackageManager();
                object = null;
                try {
                    Object object2 = packageManager.getApplicationInfoAsUser(this.mPackageName, 0, UserHandle.getUserId(this.mUid));
                    if (object2 != null) {
                        if ((object2 = packageManager.getApplicationLabel((ApplicationInfo)object2)) != null) {
                            object = object2.toString();
                        }
                        this.mApplicationName = object;
                    }
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    return null;
                }
            }
            return this.mApplicationName;
        }

        public boolean isComplete() {
            boolean bl = this.mSmsReceiverClass != null && this.mMmsReceiverClass != null && this.mRespondViaMessageClass != null && this.mSendToClass != null;
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" mPackageName: ");
            stringBuilder.append(this.mPackageName);
            stringBuilder.append(" mSmsReceiverClass: ");
            stringBuilder.append(this.mSmsReceiverClass);
            stringBuilder.append(" mMmsReceiverClass: ");
            stringBuilder.append(this.mMmsReceiverClass);
            stringBuilder.append(" mRespondViaMessageClass: ");
            stringBuilder.append(this.mRespondViaMessageClass);
            stringBuilder.append(" mSendToClass: ");
            stringBuilder.append(this.mSendToClass);
            stringBuilder.append(" mSmsAppChangedClass: ");
            stringBuilder.append(this.mSmsAppChangedReceiverClass);
            stringBuilder.append(" mProviderChangedReceiverClass: ");
            stringBuilder.append(this.mProviderChangedReceiverClass);
            stringBuilder.append(" mSimFullReceiverClass: ");
            stringBuilder.append(this.mSimFullReceiverClass);
            stringBuilder.append(" mUid: ");
            stringBuilder.append(this.mUid);
            return stringBuilder.toString();
        }
    }

    private static final class SmsPackageMonitor
    extends PackageMonitor {
        final Context mContext;

        public SmsPackageMonitor(Context context) {
            this.mContext = context;
        }

        private void onPackageChanged() {
            PackageManager packageManager = this.mContext.getPackageManager();
            Context context = this.mContext;
            int n = this.getSendingUserId();
            Object object = context;
            if (n != 0) {
                try {
                    object = this.mContext;
                    String string2 = this.mContext.getPackageName();
                    UserHandle userHandle = new UserHandle(n);
                    object = ((Context)object).createPackageContextAsUser(string2, 0, userHandle);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    object = context;
                }
            }
            if ((object = SmsApplication.getDefaultSendToApplication((Context)object, true)) != null) {
                SmsApplication.configurePreferredActivity(packageManager, (ComponentName)object, n);
            }
        }

        @Override
        public void onPackageAppeared(String string2, int n) {
            this.onPackageChanged();
        }

        @Override
        public void onPackageDisappeared(String string2, int n) {
            this.onPackageChanged();
        }

        @Override
        public void onPackageModified(String string2) {
            this.onPackageChanged();
        }
    }

}

