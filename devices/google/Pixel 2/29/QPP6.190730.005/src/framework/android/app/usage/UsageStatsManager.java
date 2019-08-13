/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.usage.AppStandbyInfo;
import android.app.usage.ConfigurationStats;
import android.app.usage.EventStats;
import android.app.usage.IUsageStatsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public final class UsageStatsManager {
    @SystemApi
    public static final String EXTRA_OBSERVER_ID = "android.app.usage.extra.OBSERVER_ID";
    @SystemApi
    public static final String EXTRA_TIME_LIMIT = "android.app.usage.extra.TIME_LIMIT";
    @SystemApi
    public static final String EXTRA_TIME_USED = "android.app.usage.extra.TIME_USED";
    public static final int INTERVAL_BEST = 4;
    public static final int INTERVAL_COUNT = 4;
    public static final int INTERVAL_DAILY = 0;
    public static final int INTERVAL_MONTHLY = 2;
    public static final int INTERVAL_WEEKLY = 1;
    public static final int INTERVAL_YEARLY = 3;
    public static final int REASON_MAIN_DEFAULT = 256;
    public static final int REASON_MAIN_FORCED = 1024;
    public static final int REASON_MAIN_MASK = 65280;
    public static final int REASON_MAIN_PREDICTED = 1280;
    public static final int REASON_MAIN_TIMEOUT = 512;
    public static final int REASON_MAIN_USAGE = 768;
    public static final int REASON_SUB_MASK = 255;
    public static final int REASON_SUB_PREDICTED_RESTORED = 1;
    public static final int REASON_SUB_USAGE_ACTIVE_TIMEOUT = 7;
    public static final int REASON_SUB_USAGE_EXEMPTED_SYNC_SCHEDULED_DOZE = 12;
    public static final int REASON_SUB_USAGE_EXEMPTED_SYNC_SCHEDULED_NON_DOZE = 11;
    public static final int REASON_SUB_USAGE_EXEMPTED_SYNC_START = 13;
    public static final int REASON_SUB_USAGE_FOREGROUND_SERVICE_START = 15;
    public static final int REASON_SUB_USAGE_MOVE_TO_BACKGROUND = 5;
    public static final int REASON_SUB_USAGE_MOVE_TO_FOREGROUND = 4;
    public static final int REASON_SUB_USAGE_NOTIFICATION_SEEN = 2;
    public static final int REASON_SUB_USAGE_SLICE_PINNED = 9;
    public static final int REASON_SUB_USAGE_SLICE_PINNED_PRIV = 10;
    public static final int REASON_SUB_USAGE_SYNC_ADAPTER = 8;
    public static final int REASON_SUB_USAGE_SYSTEM_INTERACTION = 1;
    public static final int REASON_SUB_USAGE_SYSTEM_UPDATE = 6;
    public static final int REASON_SUB_USAGE_UNEXEMPTED_SYNC_SCHEDULED = 14;
    public static final int REASON_SUB_USAGE_USER_INTERACTION = 3;
    public static final int STANDBY_BUCKET_ACTIVE = 10;
    @SystemApi
    public static final int STANDBY_BUCKET_EXEMPTED = 5;
    public static final int STANDBY_BUCKET_FREQUENT = 30;
    @SystemApi
    public static final int STANDBY_BUCKET_NEVER = 50;
    public static final int STANDBY_BUCKET_RARE = 40;
    public static final int STANDBY_BUCKET_WORKING_SET = 20;
    @SystemApi
    public static final int USAGE_SOURCE_CURRENT_ACTIVITY = 2;
    @SystemApi
    public static final int USAGE_SOURCE_TASK_ROOT_ACTIVITY = 1;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static final UsageEvents sEmptyResults = new UsageEvents();
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final Context mContext;
    @UnsupportedAppUsage
    private final IUsageStatsManager mService;

    public UsageStatsManager(Context context, IUsageStatsManager iUsageStatsManager) {
        this.mContext = context;
        this.mService = iUsageStatsManager;
    }

    public static String reasonToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 65280 & n;
        if (n2 != 256) {
            if (n2 != 512) {
                if (n2 != 768) {
                    if (n2 != 1024) {
                        if (n2 == 1280) {
                            stringBuilder.append("p");
                            if ((n & 255) == 1) {
                                stringBuilder.append("-r");
                            }
                        }
                    } else {
                        stringBuilder.append("f");
                    }
                } else {
                    stringBuilder.append("u");
                    switch (n & 255) {
                        default: {
                            break;
                        }
                        case 15: {
                            stringBuilder.append("-fss");
                            break;
                        }
                        case 14: {
                            stringBuilder.append("-uss");
                            break;
                        }
                        case 13: {
                            stringBuilder.append("-es");
                            break;
                        }
                        case 12: {
                            stringBuilder.append("-ed");
                            break;
                        }
                        case 11: {
                            stringBuilder.append("-en");
                            break;
                        }
                        case 10: {
                            stringBuilder.append("-lv");
                            break;
                        }
                        case 9: {
                            stringBuilder.append("-lp");
                            break;
                        }
                        case 8: {
                            stringBuilder.append("-sa");
                            break;
                        }
                        case 7: {
                            stringBuilder.append("-at");
                            break;
                        }
                        case 6: {
                            stringBuilder.append("-su");
                            break;
                        }
                        case 5: {
                            stringBuilder.append("-mb");
                            break;
                        }
                        case 4: {
                            stringBuilder.append("-mf");
                            break;
                        }
                        case 3: {
                            stringBuilder.append("-ui");
                            break;
                        }
                        case 2: {
                            stringBuilder.append("-ns");
                            break;
                        }
                        case 1: {
                            stringBuilder.append("-si");
                            break;
                        }
                    }
                }
            } else {
                stringBuilder.append("t");
            }
        } else {
            stringBuilder.append("d");
        }
        return stringBuilder.toString();
    }

    public static String usageSourceToString(int n) {
        if (n != 1) {
            if (n != 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("UNKNOWN(");
                stringBuilder.append(n);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
            return "CURRENT_ACTIVITY";
        }
        return "TASK_ROOT_ACTIVITY";
    }

    public void forceUsageSourceSettingRead() {
        try {
            this.mService.forceUsageSourceSettingRead();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getAppStandbyBucket() {
        try {
            int n = this.mService.getAppStandbyBucket(this.mContext.getOpPackageName(), this.mContext.getOpPackageName(), this.mContext.getUserId());
            return n;
        }
        catch (RemoteException remoteException) {
            return 10;
        }
    }

    @SystemApi
    public int getAppStandbyBucket(String string2) {
        try {
            int n = this.mService.getAppStandbyBucket(string2, this.mContext.getOpPackageName(), this.mContext.getUserId());
            return n;
        }
        catch (RemoteException remoteException) {
            return 10;
        }
    }

    @SystemApi
    public Map<String, Integer> getAppStandbyBuckets() {
        List list;
        ArrayMap<String, Integer> arrayMap;
        int n;
        try {
            list = this.mService.getAppStandbyBuckets(this.mContext.getOpPackageName(), this.mContext.getUserId()).getList();
            arrayMap = new ArrayMap<String, Integer>();
            n = list.size();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        for (int i = 0; i < n; ++i) {
            AppStandbyInfo appStandbyInfo = (AppStandbyInfo)list.get(i);
            arrayMap.put(appStandbyInfo.mPackageName, appStandbyInfo.mStandbyBucket);
            continue;
        }
        return arrayMap;
    }

    @SystemApi
    public int getUsageSource() {
        try {
            int n = this.mService.getUsageSource();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isAppInactive(String string2) {
        try {
            boolean bl = this.mService.isAppInactive(string2, this.mContext.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public void onCarrierPrivilegedAppsChanged() {
        try {
            this.mService.onCarrierPrivilegedAppsChanged();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Map<String, UsageStats> queryAndAggregateUsageStats(long l, long l2) {
        List<UsageStats> list = this.queryUsageStats(4, l, l2);
        if (list.isEmpty()) {
            return Collections.emptyMap();
        }
        ArrayMap<String, UsageStats> arrayMap = new ArrayMap<String, UsageStats>();
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            UsageStats usageStats = list.get(i);
            UsageStats usageStats2 = arrayMap.get(usageStats.getPackageName());
            if (usageStats2 == null) {
                arrayMap.put(usageStats.mPackageName, usageStats);
                continue;
            }
            usageStats2.add(usageStats);
        }
        return arrayMap;
    }

    public List<ConfigurationStats> queryConfigurations(int n, long l, long l2) {
        block3 : {
            Object object = this.mService.queryConfigurationStats(n, l, l2, this.mContext.getOpPackageName());
            if (object == null) break block3;
            try {
                object = ((ParceledListSlice)object).getList();
                return object;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return Collections.emptyList();
    }

    public List<EventStats> queryEventStats(int n, long l, long l2) {
        block3 : {
            Object object = this.mService.queryEventStats(n, l, l2, this.mContext.getOpPackageName());
            if (object == null) break block3;
            try {
                object = ((ParceledListSlice)object).getList();
                return object;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return Collections.emptyList();
    }

    public UsageEvents queryEvents(long l, long l2) {
        try {
            UsageEvents usageEvents = this.mService.queryEvents(l, l2, this.mContext.getOpPackageName());
            if (usageEvents != null) {
                return usageEvents;
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        return sEmptyResults;
    }

    public UsageEvents queryEventsForSelf(long l, long l2) {
        try {
            UsageEvents usageEvents = this.mService.queryEventsForPackage(l, l2, this.mContext.getOpPackageName());
            if (usageEvents != null) {
                return usageEvents;
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        return sEmptyResults;
    }

    public List<UsageStats> queryUsageStats(int n, long l, long l2) {
        block3 : {
            Object object = this.mService.queryUsageStats(n, l, l2, this.mContext.getOpPackageName());
            if (object == null) break block3;
            try {
                object = ((ParceledListSlice)object).getList();
                return object;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return Collections.emptyList();
    }

    @SystemApi
    public void registerAppUsageLimitObserver(int n, String[] arrstring, Duration duration, Duration duration2, PendingIntent pendingIntent) {
        try {
            this.mService.registerAppUsageLimitObserver(n, arrstring, duration.toMillis(), duration2.toMillis(), pendingIntent, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void registerAppUsageObserver(int n, String[] arrstring, long l, TimeUnit timeUnit, PendingIntent pendingIntent) {
        try {
            this.mService.registerAppUsageObserver(n, arrstring, timeUnit.toMillis(l), pendingIntent, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void registerUsageSessionObserver(int n, String[] arrstring, Duration duration, Duration duration2, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        try {
            this.mService.registerUsageSessionObserver(n, arrstring, duration.toMillis(), duration2.toMillis(), pendingIntent, pendingIntent2, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportChooserSelection(String string2, int n, String string3, String[] arrstring, String string4) {
        try {
            this.mService.reportChooserSelection(string2, n, string3, arrstring, string4);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @SystemApi
    public void reportUsageStart(Activity activity, String string2) {
        try {
            this.mService.reportUsageStart(activity.getActivityToken(), string2, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void reportUsageStart(Activity activity, String string2, long l) {
        try {
            this.mService.reportPastUsageStart(activity.getActivityToken(), string2, l, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void reportUsageStop(Activity activity, String string2) {
        try {
            this.mService.reportUsageStop(activity.getActivityToken(), string2, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setAppInactive(String string2, boolean bl) {
        try {
            this.mService.setAppInactive(string2, bl, this.mContext.getUserId());
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @SystemApi
    public void setAppStandbyBucket(String string2, int n) {
        try {
            this.mService.setAppStandbyBucket(string2, n, this.mContext.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setAppStandbyBuckets(Map<String, Integer> parceledListSlice2) {
        if (parceledListSlice2 == null) {
            return;
        }
        ArrayList<AppStandbyInfo> arrayList = new ArrayList<AppStandbyInfo>(parceledListSlice2.size());
        for (Map.Entry entry : parceledListSlice2.entrySet()) {
            arrayList.add(new AppStandbyInfo((String)entry.getKey(), (Integer)entry.getValue()));
        }
        ParceledListSlice parceledListSlice = new ParceledListSlice(arrayList);
        try {
            this.mService.setAppStandbyBuckets(parceledListSlice, this.mContext.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterAppUsageLimitObserver(int n) {
        try {
            this.mService.unregisterAppUsageLimitObserver(n, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterAppUsageObserver(int n) {
        try {
            this.mService.unregisterAppUsageObserver(n, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterUsageSessionObserver(int n) {
        try {
            this.mService.unregisterUsageSessionObserver(n, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void whitelistAppTemporarily(String string2, long l, UserHandle userHandle) {
        try {
            this.mService.whitelistAppTemporarily(string2, l, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StandbyBuckets {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UsageSource {
    }

}

