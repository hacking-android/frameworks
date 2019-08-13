/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class SyncStatusInfo
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<SyncStatusInfo> CREATOR = new Parcelable.Creator<SyncStatusInfo>(){

        @Override
        public SyncStatusInfo createFromParcel(Parcel parcel) {
            return new SyncStatusInfo(parcel);
        }

        public SyncStatusInfo[] newArray(int n) {
            return new SyncStatusInfo[n];
        }
    };
    private static final int MAX_EVENT_COUNT = 10;
    private static final int SOURCE_COUNT = 6;
    private static final String TAG = "Sync";
    static final int VERSION = 6;
    @UnsupportedAppUsage
    public final int authorityId;
    @UnsupportedAppUsage
    public long initialFailureTime;
    @UnsupportedAppUsage
    public boolean initialize;
    @UnsupportedAppUsage
    public String lastFailureMesg;
    @UnsupportedAppUsage
    public int lastFailureSource;
    @UnsupportedAppUsage
    public long lastFailureTime;
    @UnsupportedAppUsage
    public int lastSuccessSource;
    @UnsupportedAppUsage
    public long lastSuccessTime;
    public long lastTodayResetTime;
    private final ArrayList<Long> mLastEventTimes = new ArrayList();
    private final ArrayList<String> mLastEvents = new ArrayList();
    @UnsupportedAppUsage
    public boolean pending;
    public final long[] perSourceLastFailureTimes = new long[6];
    public final long[] perSourceLastSuccessTimes = new long[6];
    @UnsupportedAppUsage
    private ArrayList<Long> periodicSyncTimes;
    public final Stats todayStats = new Stats();
    public final Stats totalStats = new Stats();
    public final Stats yesterdayStats = new Stats();

    @UnsupportedAppUsage
    public SyncStatusInfo(int n) {
        this.authorityId = n;
    }

    public SyncStatusInfo(SyncStatusInfo syncStatusInfo) {
        this.authorityId = syncStatusInfo.authorityId;
        syncStatusInfo.totalStats.copyTo(this.totalStats);
        syncStatusInfo.todayStats.copyTo(this.todayStats);
        syncStatusInfo.yesterdayStats.copyTo(this.yesterdayStats);
        this.lastTodayResetTime = syncStatusInfo.lastTodayResetTime;
        this.lastSuccessTime = syncStatusInfo.lastSuccessTime;
        this.lastSuccessSource = syncStatusInfo.lastSuccessSource;
        this.lastFailureTime = syncStatusInfo.lastFailureTime;
        this.lastFailureSource = syncStatusInfo.lastFailureSource;
        this.lastFailureMesg = syncStatusInfo.lastFailureMesg;
        this.initialFailureTime = syncStatusInfo.initialFailureTime;
        this.pending = syncStatusInfo.pending;
        this.initialize = syncStatusInfo.initialize;
        ArrayList<Long> arrayList = syncStatusInfo.periodicSyncTimes;
        if (arrayList != null) {
            this.periodicSyncTimes = new ArrayList<Long>(arrayList);
        }
        this.mLastEventTimes.addAll(syncStatusInfo.mLastEventTimes);
        this.mLastEvents.addAll(syncStatusInfo.mLastEvents);
        SyncStatusInfo.copy(this.perSourceLastSuccessTimes, syncStatusInfo.perSourceLastSuccessTimes);
        SyncStatusInfo.copy(this.perSourceLastFailureTimes, syncStatusInfo.perSourceLastFailureTimes);
    }

    @UnsupportedAppUsage
    public SyncStatusInfo(Parcel parcel) {
        Object object;
        int n = parcel.readInt();
        if (n != 6 && n != 1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown version: ");
            ((StringBuilder)object).append(n);
            Log.w("SyncStatusInfo", ((StringBuilder)object).toString());
        }
        this.authorityId = parcel.readInt();
        this.totalStats.totalElapsedTime = parcel.readLong();
        this.totalStats.numSyncs = parcel.readInt();
        this.totalStats.numSourcePoll = parcel.readInt();
        this.totalStats.numSourceOther = parcel.readInt();
        this.totalStats.numSourceLocal = parcel.readInt();
        this.totalStats.numSourceUser = parcel.readInt();
        this.lastSuccessTime = parcel.readLong();
        this.lastSuccessSource = parcel.readInt();
        this.lastFailureTime = parcel.readLong();
        this.lastFailureSource = parcel.readInt();
        this.lastFailureMesg = parcel.readString();
        this.initialFailureTime = parcel.readLong();
        boolean bl = parcel.readInt() != 0;
        this.pending = bl;
        bl = parcel.readInt() != 0;
        this.initialize = bl;
        if (n == 1) {
            this.periodicSyncTimes = null;
        } else {
            int n2;
            int n3 = parcel.readInt();
            if (n3 < 0) {
                this.periodicSyncTimes = null;
            } else {
                this.periodicSyncTimes = new ArrayList();
                for (n2 = 0; n2 < n3; ++n2) {
                    this.periodicSyncTimes.add(parcel.readLong());
                }
            }
            if (n >= 3) {
                this.mLastEventTimes.clear();
                this.mLastEvents.clear();
                n3 = parcel.readInt();
                for (n2 = 0; n2 < n3; ++n2) {
                    this.mLastEventTimes.add(parcel.readLong());
                    this.mLastEvents.add(parcel.readString());
                }
            }
        }
        if (n < 4) {
            object = this.totalStats;
            ((Stats)object).numSourcePeriodic = ((Stats)object).numSyncs - this.totalStats.numSourceLocal - this.totalStats.numSourcePoll - this.totalStats.numSourceOther - this.totalStats.numSourceUser;
            if (this.totalStats.numSourcePeriodic < 0) {
                this.totalStats.numSourcePeriodic = 0;
            }
        } else {
            this.totalStats.numSourcePeriodic = parcel.readInt();
        }
        if (n >= 5) {
            this.totalStats.numSourceFeed = parcel.readInt();
            this.totalStats.numFailures = parcel.readInt();
            this.totalStats.numCancels = parcel.readInt();
            this.lastTodayResetTime = parcel.readLong();
            this.todayStats.readFromParcel(parcel);
            this.yesterdayStats.readFromParcel(parcel);
        }
        if (n >= 6) {
            parcel.readLongArray(this.perSourceLastSuccessTimes);
            parcel.readLongArray(this.perSourceLastFailureTimes);
        }
    }

    private static boolean areSameDates(long l, long l2) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(l);
        gregorianCalendar2.setTimeInMillis(l2);
        boolean bl = true;
        if (gregorianCalendar.get(1) != gregorianCalendar2.get(1) || gregorianCalendar.get(6) != gregorianCalendar2.get(6)) {
            bl = false;
        }
        return bl;
    }

    private static void copy(long[] arrl, long[] arrl2) {
        System.arraycopy(arrl2, 0, arrl, 0, arrl.length);
    }

    @UnsupportedAppUsage
    private void ensurePeriodicSyncTimeSize(int n) {
        if (this.periodicSyncTimes == null) {
            this.periodicSyncTimes = new ArrayList(0);
        }
        int n2 = n + 1;
        if (this.periodicSyncTimes.size() < n2) {
            for (n = this.periodicSyncTimes.size(); n < n2; ++n) {
                this.periodicSyncTimes.add(0L);
            }
        }
    }

    public void addEvent(String string2) {
        if (this.mLastEventTimes.size() >= 10) {
            this.mLastEventTimes.remove(9);
            this.mLastEvents.remove(9);
        }
        this.mLastEventTimes.add(0, System.currentTimeMillis());
        this.mLastEvents.add(0, string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getEvent(int n) {
        return this.mLastEvents.get(n);
    }

    public int getEventCount() {
        return this.mLastEventTimes.size();
    }

    public long getEventTime(int n) {
        return this.mLastEventTimes.get(n);
    }

    @UnsupportedAppUsage
    public int getLastFailureMesgAsInt(int n) {
        int n2 = ContentResolver.syncErrorStringToInt(this.lastFailureMesg);
        if (n2 > 0) {
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown lastFailureMesg:");
        stringBuilder.append(this.lastFailureMesg);
        Log.d(TAG, stringBuilder.toString());
        return n;
    }

    @UnsupportedAppUsage
    public long getPeriodicSyncTime(int n) {
        ArrayList<Long> arrayList = this.periodicSyncTimes;
        if (arrayList != null && n < arrayList.size()) {
            return this.periodicSyncTimes.get(n);
        }
        return 0L;
    }

    public void maybeResetTodayStats(boolean bl, boolean bl2) {
        long l = System.currentTimeMillis();
        if (!bl2) {
            if (SyncStatusInfo.areSameDates(l, this.lastTodayResetTime)) {
                return;
            }
            if (l < this.lastTodayResetTime && !bl) {
                return;
            }
        }
        this.lastTodayResetTime = l;
        this.todayStats.copyTo(this.yesterdayStats);
        this.todayStats.clear();
    }

    @UnsupportedAppUsage
    public void removePeriodicSyncTime(int n) {
        ArrayList<Long> arrayList = this.periodicSyncTimes;
        if (arrayList != null && n < arrayList.size()) {
            this.periodicSyncTimes.remove(n);
        }
    }

    public void setLastFailure(int n, long l, String arrl) {
        this.lastFailureTime = l;
        this.lastFailureSource = n;
        this.lastFailureMesg = arrl;
        if (this.initialFailureTime == 0L) {
            this.initialFailureTime = l;
        }
        if (n >= 0 && n < (arrl = this.perSourceLastFailureTimes).length) {
            arrl[n] = l;
        }
    }

    public void setLastSuccess(int n, long l) {
        long[] arrl;
        this.lastSuccessTime = l;
        this.lastSuccessSource = n;
        this.lastFailureTime = 0L;
        this.lastFailureSource = -1;
        this.lastFailureMesg = null;
        this.initialFailureTime = 0L;
        if (n >= 0 && n < (arrl = this.perSourceLastSuccessTimes).length) {
            arrl[n] = l;
        }
    }

    @UnsupportedAppUsage
    public void setPeriodicSyncTime(int n, long l) {
        this.ensurePeriodicSyncTimeSize(n);
        this.periodicSyncTimes.set(n, l);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(6);
        parcel.writeInt(this.authorityId);
        parcel.writeLong(this.totalStats.totalElapsedTime);
        parcel.writeInt(this.totalStats.numSyncs);
        parcel.writeInt(this.totalStats.numSourcePoll);
        parcel.writeInt(this.totalStats.numSourceOther);
        parcel.writeInt(this.totalStats.numSourceLocal);
        parcel.writeInt(this.totalStats.numSourceUser);
        parcel.writeLong(this.lastSuccessTime);
        parcel.writeInt(this.lastSuccessSource);
        parcel.writeLong(this.lastFailureTime);
        parcel.writeInt(this.lastFailureSource);
        parcel.writeString(this.lastFailureMesg);
        parcel.writeLong(this.initialFailureTime);
        parcel.writeInt((int)this.pending);
        parcel.writeInt((int)this.initialize);
        Object object = this.periodicSyncTimes;
        if (object != null) {
            parcel.writeInt(((ArrayList)object).size());
            object = this.periodicSyncTimes.iterator();
            while (object.hasNext()) {
                parcel.writeLong((Long)object.next());
            }
        } else {
            parcel.writeInt(-1);
        }
        parcel.writeInt(this.mLastEventTimes.size());
        for (n = 0; n < this.mLastEventTimes.size(); ++n) {
            parcel.writeLong(this.mLastEventTimes.get(n));
            parcel.writeString(this.mLastEvents.get(n));
        }
        parcel.writeInt(this.totalStats.numSourcePeriodic);
        parcel.writeInt(this.totalStats.numSourceFeed);
        parcel.writeInt(this.totalStats.numFailures);
        parcel.writeInt(this.totalStats.numCancels);
        parcel.writeLong(this.lastTodayResetTime);
        this.todayStats.writeToParcel(parcel);
        this.yesterdayStats.writeToParcel(parcel);
        parcel.writeLongArray(this.perSourceLastSuccessTimes);
        parcel.writeLongArray(this.perSourceLastFailureTimes);
    }

    public static class Stats {
        public int numCancels;
        public int numFailures;
        public int numSourceFeed;
        public int numSourceLocal;
        public int numSourceOther;
        public int numSourcePeriodic;
        public int numSourcePoll;
        public int numSourceUser;
        public int numSyncs;
        public long totalElapsedTime;

        public void clear() {
            this.totalElapsedTime = 0L;
            this.numSyncs = 0;
            this.numSourcePoll = 0;
            this.numSourceOther = 0;
            this.numSourceLocal = 0;
            this.numSourceUser = 0;
            this.numSourcePeriodic = 0;
            this.numSourceFeed = 0;
            this.numFailures = 0;
            this.numCancels = 0;
        }

        public void copyTo(Stats stats) {
            stats.totalElapsedTime = this.totalElapsedTime;
            stats.numSyncs = this.numSyncs;
            stats.numSourcePoll = this.numSourcePoll;
            stats.numSourceOther = this.numSourceOther;
            stats.numSourceLocal = this.numSourceLocal;
            stats.numSourceUser = this.numSourceUser;
            stats.numSourcePeriodic = this.numSourcePeriodic;
            stats.numSourceFeed = this.numSourceFeed;
            stats.numFailures = this.numFailures;
            stats.numCancels = this.numCancels;
        }

        public void readFromParcel(Parcel parcel) {
            this.totalElapsedTime = parcel.readLong();
            this.numSyncs = parcel.readInt();
            this.numSourcePoll = parcel.readInt();
            this.numSourceOther = parcel.readInt();
            this.numSourceLocal = parcel.readInt();
            this.numSourceUser = parcel.readInt();
            this.numSourcePeriodic = parcel.readInt();
            this.numSourceFeed = parcel.readInt();
            this.numFailures = parcel.readInt();
            this.numCancels = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeLong(this.totalElapsedTime);
            parcel.writeInt(this.numSyncs);
            parcel.writeInt(this.numSourcePoll);
            parcel.writeInt(this.numSourceOther);
            parcel.writeInt(this.numSourceLocal);
            parcel.writeInt(this.numSourceUser);
            parcel.writeInt(this.numSourcePeriodic);
            parcel.writeInt(this.numSourceFeed);
            parcel.writeInt(this.numFailures);
            parcel.writeInt(this.numCancels);
        }
    }

}

