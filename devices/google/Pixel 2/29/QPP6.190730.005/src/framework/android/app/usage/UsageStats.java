/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.SparseIntArray;
import java.util.Set;

public final class UsageStats
implements Parcelable {
    public static final Parcelable.Creator<UsageStats> CREATOR = new Parcelable.Creator<UsageStats>(){

        private void readBundleToEventMap(Bundle bundle, ArrayMap<String, Integer> arrayMap) {
            if (bundle != null) {
                for (String string2 : bundle.keySet()) {
                    arrayMap.put(string2, bundle.getInt(string2));
                }
            }
        }

        private void readSparseIntArray(Parcel parcel, SparseIntArray sparseIntArray) {
            int n = parcel.readInt();
            for (int i = 0; i < n; ++i) {
                sparseIntArray.put(parcel.readInt(), parcel.readInt());
            }
        }

        @Override
        public UsageStats createFromParcel(Parcel parcel) {
            UsageStats usageStats = new UsageStats();
            usageStats.mPackageName = parcel.readString();
            usageStats.mBeginTimeStamp = parcel.readLong();
            usageStats.mEndTimeStamp = parcel.readLong();
            usageStats.mLastTimeUsed = parcel.readLong();
            usageStats.mLastTimeVisible = parcel.readLong();
            usageStats.mLastTimeForegroundServiceUsed = parcel.readLong();
            usageStats.mTotalTimeInForeground = parcel.readLong();
            usageStats.mTotalTimeVisible = parcel.readLong();
            usageStats.mTotalTimeForegroundServiceUsed = parcel.readLong();
            usageStats.mLaunchCount = parcel.readInt();
            usageStats.mAppLaunchCount = parcel.readInt();
            usageStats.mLastEvent = parcel.readInt();
            Bundle bundle = parcel.readBundle();
            if (bundle != null) {
                usageStats.mChooserCounts = new ArrayMap();
                for (String string2 : bundle.keySet()) {
                    Bundle bundle2;
                    if (!usageStats.mChooserCounts.containsKey(string2)) {
                        ArrayMap arrayMap = new ArrayMap();
                        usageStats.mChooserCounts.put(string2, arrayMap);
                    }
                    if ((bundle2 = bundle.getBundle(string2)) == null) continue;
                    for (String string3 : bundle2.keySet()) {
                        int n = bundle2.getInt(string3);
                        if (n <= 0) continue;
                        usageStats.mChooserCounts.get(string2).put(string3, n);
                    }
                }
            }
            this.readSparseIntArray(parcel, usageStats.mActivities);
            this.readBundleToEventMap(parcel.readBundle(), usageStats.mForegroundServices);
            return usageStats;
        }

        public UsageStats[] newArray(int n) {
            return new UsageStats[n];
        }
    };
    public SparseIntArray mActivities = new SparseIntArray();
    public int mAppLaunchCount;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public long mBeginTimeStamp;
    public ArrayMap<String, ArrayMap<String, Integer>> mChooserCounts = new ArrayMap();
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public long mEndTimeStamp;
    public ArrayMap<String, Integer> mForegroundServices = new ArrayMap();
    @Deprecated
    @UnsupportedAppUsage
    public int mLastEvent;
    public long mLastTimeForegroundServiceUsed;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public long mLastTimeUsed;
    public long mLastTimeVisible;
    @UnsupportedAppUsage
    public int mLaunchCount;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public String mPackageName;
    public long mTotalTimeForegroundServiceUsed;
    @UnsupportedAppUsage
    public long mTotalTimeInForeground;
    public long mTotalTimeVisible;

    public UsageStats() {
    }

    public UsageStats(UsageStats usageStats) {
        this.mPackageName = usageStats.mPackageName;
        this.mBeginTimeStamp = usageStats.mBeginTimeStamp;
        this.mEndTimeStamp = usageStats.mEndTimeStamp;
        this.mLastTimeUsed = usageStats.mLastTimeUsed;
        this.mLastTimeVisible = usageStats.mLastTimeVisible;
        this.mLastTimeForegroundServiceUsed = usageStats.mLastTimeForegroundServiceUsed;
        this.mTotalTimeInForeground = usageStats.mTotalTimeInForeground;
        this.mTotalTimeVisible = usageStats.mTotalTimeVisible;
        this.mTotalTimeForegroundServiceUsed = usageStats.mTotalTimeForegroundServiceUsed;
        this.mLaunchCount = usageStats.mLaunchCount;
        this.mAppLaunchCount = usageStats.mAppLaunchCount;
        this.mLastEvent = usageStats.mLastEvent;
        this.mActivities = usageStats.mActivities;
        this.mForegroundServices = usageStats.mForegroundServices;
        this.mChooserCounts = usageStats.mChooserCounts;
    }

    private boolean anyForegroundServiceStarted() {
        return this.mForegroundServices.isEmpty() ^ true;
    }

    private Bundle eventMapToBundle(ArrayMap<String, Integer> arrayMap) {
        Bundle bundle = new Bundle();
        int n = arrayMap.size();
        for (int i = 0; i < n; ++i) {
            bundle.putInt(arrayMap.keyAt(i), arrayMap.valueAt(i));
        }
        return bundle;
    }

    private boolean hasForegroundActivity() {
        int n = this.mActivities.size();
        for (int i = 0; i < n; ++i) {
            if (this.mActivities.valueAt(i) != 1) continue;
            return true;
        }
        return false;
    }

    private boolean hasVisibleActivity() {
        int n = this.mActivities.size();
        for (int i = 0; i < n; ++i) {
            int n2 = this.mActivities.valueAt(i);
            if (n2 != 1 && n2 != 2) {
                continue;
            }
            return true;
        }
        return false;
    }

    private void incrementServiceTimeUsed(long l) {
        long l2 = this.mLastTimeForegroundServiceUsed;
        if (l > l2) {
            this.mTotalTimeForegroundServiceUsed += l - l2;
            this.mLastTimeForegroundServiceUsed = l;
        }
    }

    private void incrementTimeUsed(long l) {
        long l2 = this.mLastTimeUsed;
        if (l > l2) {
            this.mTotalTimeInForeground += l - l2;
            this.mLastTimeUsed = l;
        }
    }

    private void incrementTimeVisible(long l) {
        long l2 = this.mLastTimeVisible;
        if (l > l2) {
            this.mTotalTimeVisible += l - l2;
            this.mLastTimeVisible = l;
        }
    }

    private void mergeEventMap(ArrayMap<String, Integer> arrayMap, ArrayMap<String, Integer> arrayMap2) {
        int n = arrayMap2.size();
        for (int i = 0; i < n; ++i) {
            String string2 = arrayMap2.keyAt(i);
            Integer n2 = arrayMap2.valueAt(i);
            if (arrayMap.containsKey(string2)) {
                arrayMap.put(string2, Math.max(arrayMap.get(string2), n2));
                continue;
            }
            arrayMap.put(string2, n2);
        }
    }

    private void mergeEventMap(SparseIntArray sparseIntArray, SparseIntArray sparseIntArray2) {
        int n = sparseIntArray2.size();
        for (int i = 0; i < n; ++i) {
            int n2 = sparseIntArray2.keyAt(i);
            int n3 = sparseIntArray2.valueAt(i);
            int n4 = sparseIntArray.indexOfKey(n2);
            if (n4 >= 0) {
                sparseIntArray.put(n2, Math.max(sparseIntArray.valueAt(n4), n3));
                continue;
            }
            sparseIntArray.put(n2, n3);
        }
    }

    private void updateActivity(String string2, long l, int n, int n2) {
        if (n != 1 && n != 2 && n != 23 && n != 24) {
            return;
        }
        int n3 = this.mActivities.indexOfKey(n2);
        if (n3 >= 0) {
            if ((n3 = this.mActivities.valueAt(n3)) != 1) {
                if (n3 == 2) {
                    this.incrementTimeVisible(l);
                }
            } else {
                this.incrementTimeUsed(l);
                this.incrementTimeVisible(l);
            }
        }
        if (n != 1) {
            if (n != 2) {
                if (n == 23 || n == 24) {
                    this.mActivities.delete(n2);
                }
            } else {
                if (!this.hasVisibleActivity()) {
                    this.mLastTimeVisible = l;
                }
                this.mActivities.put(n2, n);
            }
        } else {
            if (!this.hasVisibleActivity()) {
                this.mLastTimeUsed = l;
                this.mLastTimeVisible = l;
            } else if (!this.hasForegroundActivity()) {
                this.mLastTimeUsed = l;
            }
            this.mActivities.put(n2, n);
        }
    }

    private void updateForegroundService(String string2, long l, int n) {
        int n2;
        if (n != 20 && n != 19) {
            return;
        }
        Integer n3 = this.mForegroundServices.get(string2);
        if (n3 != null && ((n2 = n3.intValue()) == 19 || n2 == 21)) {
            this.incrementServiceTimeUsed(l);
        }
        if (n != 19) {
            if (n == 20) {
                this.mForegroundServices.remove(string2);
            }
        } else {
            if (!this.anyForegroundServiceStarted()) {
                this.mLastTimeForegroundServiceUsed = l;
            }
            this.mForegroundServices.put(string2, n);
        }
    }

    private void writeSparseIntArray(Parcel parcel, SparseIntArray sparseIntArray) {
        int n = sparseIntArray.size();
        parcel.writeInt(n);
        for (int i = 0; i < n; ++i) {
            parcel.writeInt(sparseIntArray.keyAt(i));
            parcel.writeInt(sparseIntArray.valueAt(i));
        }
    }

    public void add(UsageStats usageStats) {
        if (this.mPackageName.equals(usageStats.mPackageName)) {
            if (usageStats.mBeginTimeStamp > this.mBeginTimeStamp) {
                this.mergeEventMap(this.mActivities, usageStats.mActivities);
                this.mergeEventMap(this.mForegroundServices, usageStats.mForegroundServices);
                this.mLastTimeUsed = Math.max(this.mLastTimeUsed, usageStats.mLastTimeUsed);
                this.mLastTimeVisible = Math.max(this.mLastTimeVisible, usageStats.mLastTimeVisible);
                this.mLastTimeForegroundServiceUsed = Math.max(this.mLastTimeForegroundServiceUsed, usageStats.mLastTimeForegroundServiceUsed);
            }
            this.mBeginTimeStamp = Math.min(this.mBeginTimeStamp, usageStats.mBeginTimeStamp);
            this.mEndTimeStamp = Math.max(this.mEndTimeStamp, usageStats.mEndTimeStamp);
            this.mTotalTimeInForeground += usageStats.mTotalTimeInForeground;
            this.mTotalTimeVisible += usageStats.mTotalTimeVisible;
            this.mTotalTimeForegroundServiceUsed += usageStats.mTotalTimeForegroundServiceUsed;
            this.mLaunchCount += usageStats.mLaunchCount;
            this.mAppLaunchCount += usageStats.mAppLaunchCount;
            if (this.mChooserCounts == null) {
                this.mChooserCounts = usageStats.mChooserCounts;
            } else {
                ArrayMap<String, ArrayMap<String, Integer>> arrayMap = usageStats.mChooserCounts;
                if (arrayMap != null) {
                    int n = arrayMap.size();
                    for (int i = 0; i < n; ++i) {
                        String string2 = usageStats.mChooserCounts.keyAt(i);
                        ArrayMap<String, Integer> arrayMap2 = usageStats.mChooserCounts.valueAt(i);
                        if (this.mChooserCounts.containsKey(string2) && this.mChooserCounts.get(string2) != null) {
                            int n2 = arrayMap2.size();
                            for (int j = 0; j < n2; ++j) {
                                arrayMap = arrayMap2.keyAt(j);
                                int n3 = arrayMap2.valueAt(j);
                                int n4 = this.mChooserCounts.get(string2).getOrDefault(arrayMap, 0);
                                this.mChooserCounts.get(string2).put((String)((Object)arrayMap), n4 + n3);
                            }
                            continue;
                        }
                        this.mChooserCounts.put(string2, arrayMap2);
                    }
                }
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't merge UsageStats for package '");
        stringBuilder.append(this.mPackageName);
        stringBuilder.append("' with UsageStats for package '");
        stringBuilder.append(usageStats.mPackageName);
        stringBuilder.append("'.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public int getAppLaunchCount() {
        return this.mAppLaunchCount;
    }

    public long getFirstTimeStamp() {
        return this.mBeginTimeStamp;
    }

    public long getLastTimeForegroundServiceUsed() {
        return this.mLastTimeForegroundServiceUsed;
    }

    public long getLastTimeStamp() {
        return this.mEndTimeStamp;
    }

    public long getLastTimeUsed() {
        return this.mLastTimeUsed;
    }

    public long getLastTimeVisible() {
        return this.mLastTimeVisible;
    }

    public UsageStats getObfuscatedForInstantApp() {
        UsageStats usageStats = new UsageStats(this);
        usageStats.mPackageName = "android.instant_app";
        return usageStats;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public long getTotalTimeForegroundServiceUsed() {
        return this.mTotalTimeForegroundServiceUsed;
    }

    public long getTotalTimeInForeground() {
        return this.mTotalTimeInForeground;
    }

    public long getTotalTimeVisible() {
        return this.mTotalTimeVisible;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void update(String var1_1, long var2_2, int var4_3, int var5_4) {
        if (var4_3 == 1 || var4_3 == 2) ** GOTO lbl-1000
        if (var4_3 == 3) ** GOTO lbl26
        switch (var4_3) {
            default: {
                break;
            }
            case 25: 
            case 26: {
                if (this.hasForegroundActivity()) {
                    this.incrementTimeUsed(var2_2);
                }
                if (this.hasVisibleActivity()) {
                    this.incrementTimeVisible(var2_2);
                }
                if (!this.anyForegroundServiceStarted()) break;
                this.incrementServiceTimeUsed(var2_2);
                break;
            }
            case 22: {
                if (!this.anyForegroundServiceStarted()) break;
                this.incrementServiceTimeUsed(var2_2);
                break;
            }
            case 21: {
                this.mLastTimeForegroundServiceUsed = var2_2;
                this.mForegroundServices.put(var1_1, var4_3);
                break;
            }
            case 19: 
            case 20: {
                this.updateForegroundService(var1_1, var2_2, var4_3);
                break;
            }
lbl26: // 1 sources:
            if (this.hasForegroundActivity()) {
                this.incrementTimeUsed(var2_2);
            }
            if (!this.hasVisibleActivity()) break;
            this.incrementTimeVisible(var2_2);
            break;
            case 23: 
            case 24: lbl-1000: // 2 sources:
            {
                this.updateActivity(var1_1, var2_2, var4_3, var5_4);
            }
        }
        this.mEndTimeStamp = var2_2;
        if (var4_3 != 1) return;
        ++this.mLaunchCount;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mBeginTimeStamp);
        parcel.writeLong(this.mEndTimeStamp);
        parcel.writeLong(this.mLastTimeUsed);
        parcel.writeLong(this.mLastTimeVisible);
        parcel.writeLong(this.mLastTimeForegroundServiceUsed);
        parcel.writeLong(this.mTotalTimeInForeground);
        parcel.writeLong(this.mTotalTimeVisible);
        parcel.writeLong(this.mTotalTimeForegroundServiceUsed);
        parcel.writeInt(this.mLaunchCount);
        parcel.writeInt(this.mAppLaunchCount);
        parcel.writeInt(this.mLastEvent);
        Bundle bundle = new Bundle();
        Object object = this.mChooserCounts;
        if (object != null) {
            int n2 = ((ArrayMap)object).size();
            for (n = 0; n < n2; ++n) {
                String string2 = this.mChooserCounts.keyAt(n);
                ArrayMap<String, Integer> arrayMap = this.mChooserCounts.valueAt(n);
                object = new Bundle();
                int n3 = arrayMap.size();
                for (int i = 0; i < n3; ++i) {
                    ((BaseBundle)object).putInt(arrayMap.keyAt(i), arrayMap.valueAt(i));
                }
                bundle.putBundle(string2, (Bundle)object);
            }
        }
        parcel.writeBundle(bundle);
        this.writeSparseIntArray(parcel, this.mActivities);
        parcel.writeBundle(this.eventMapToBundle(this.mForegroundServices));
    }

}

