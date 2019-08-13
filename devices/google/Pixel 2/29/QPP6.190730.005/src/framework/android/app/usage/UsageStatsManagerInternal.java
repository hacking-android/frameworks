/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.app.usage.UsageStats;
import android.content.ComponentName;
import android.content.res.Configuration;
import android.os.UserHandle;
import java.util.List;
import java.util.Set;

public abstract class UsageStatsManagerInternal {
    public abstract void addAppIdleStateChangeListener(AppIdleStateChangeListener var1);

    public abstract void applyRestoredPayload(int var1, String var2, byte[] var3);

    public abstract int getAppStandbyBucket(String var1, int var2, long var3);

    public abstract AppUsageLimitData getAppUsageLimit(String var1, UserHandle var2);

    public abstract byte[] getBackupPayload(int var1, String var2);

    public abstract int[] getIdleUidsForUser(int var1);

    public abstract long getTimeSinceLastJobRun(String var1, int var2);

    public abstract boolean isAppIdle(String var1, int var2, int var3);

    public abstract boolean isAppIdleParoleOn();

    public abstract void onActiveAdminAdded(String var1, int var2);

    public abstract void onAdminDataAvailable();

    public abstract void prepareForPossibleShutdown();

    public abstract void prepareShutdown();

    public abstract List<UsageStats> queryUsageStatsForUser(int var1, int var2, long var3, long var5, boolean var7);

    public abstract void removeAppIdleStateChangeListener(AppIdleStateChangeListener var1);

    public abstract void reportAppJobState(String var1, int var2, int var3, long var4);

    public abstract void reportConfigurationChange(Configuration var1, int var2);

    public abstract void reportContentProviderUsage(String var1, String var2, int var3);

    public abstract void reportEvent(ComponentName var1, int var2, int var3, int var4, ComponentName var5);

    public abstract void reportEvent(String var1, int var2, int var3);

    public abstract void reportExemptedSyncStart(String var1, int var2);

    public abstract void reportInterruptiveNotification(String var1, String var2, int var3);

    public abstract void reportShortcutUsage(String var1, String var2, int var3);

    public abstract void reportSyncScheduled(String var1, int var2, boolean var3);

    public abstract void setActiveAdminApps(Set<String> var1, int var2);

    public abstract void setLastJobRunTime(String var1, int var2, long var3);

    public static abstract class AppIdleStateChangeListener {
        public abstract void onAppIdleStateChanged(String var1, int var2, boolean var3, int var4, int var5);

        public abstract void onParoleStateChanged(boolean var1);

        public void onUserInteractionStarted(String string2, int n) {
        }
    }

    public static class AppUsageLimitData {
        private final long mTotalUsageLimit;
        private final long mUsageRemaining;

        public AppUsageLimitData(long l, long l2) {
            this.mTotalUsageLimit = l;
            this.mUsageRemaining = l2;
        }

        public long getTotalUsageLimit() {
            return this.mTotalUsageLimit;
        }

        public long getUsageRemaining() {
            return this.mUsageRemaining;
        }
    }

}

