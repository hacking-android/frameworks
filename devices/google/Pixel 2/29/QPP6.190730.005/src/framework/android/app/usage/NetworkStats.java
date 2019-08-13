/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.app.usage;

import android.content.Context;
import android.net.INetworkStatsService;
import android.net.INetworkStatsSession;
import android.net.NetworkStats;
import android.net.NetworkStatsHistory;
import android.net.NetworkTemplate;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.IntArray;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class NetworkStats
implements AutoCloseable {
    private static final String TAG = "NetworkStats";
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final long mEndTimeStamp;
    private int mEnumerationIndex = 0;
    private NetworkStatsHistory mHistory = null;
    private NetworkStatsHistory.Entry mRecycledHistoryEntry = null;
    private NetworkStats.Entry mRecycledSummaryEntry = null;
    private INetworkStatsSession mSession;
    private final long mStartTimeStamp;
    private int mState = -1;
    private android.net.NetworkStats mSummary = null;
    private int mTag = 0;
    private NetworkTemplate mTemplate;
    private int mUidOrUidIndex;
    private int[] mUids;

    NetworkStats(Context context, NetworkTemplate networkTemplate, int n, long l, long l2, INetworkStatsService iNetworkStatsService) throws RemoteException, SecurityException {
        this.mSession = iNetworkStatsService.openSessionForUsageStats(n, context.getOpPackageName());
        this.mCloseGuard.open("close");
        this.mTemplate = networkTemplate;
        this.mStartTimeStamp = l;
        this.mEndTimeStamp = l2;
    }

    private void fillBucketFromSummaryEntry(Bucket bucket) {
        bucket.mUid = Bucket.convertUid(this.mRecycledSummaryEntry.uid);
        bucket.mTag = Bucket.convertTag(this.mRecycledSummaryEntry.tag);
        bucket.mState = Bucket.convertState(this.mRecycledSummaryEntry.set);
        bucket.mDefaultNetworkStatus = Bucket.convertDefaultNetworkStatus(this.mRecycledSummaryEntry.defaultNetwork);
        bucket.mMetered = Bucket.convertMetered(this.mRecycledSummaryEntry.metered);
        bucket.mRoaming = Bucket.convertRoaming(this.mRecycledSummaryEntry.roaming);
        bucket.mBeginTimeStamp = this.mStartTimeStamp;
        bucket.mEndTimeStamp = this.mEndTimeStamp;
        bucket.mRxBytes = this.mRecycledSummaryEntry.rxBytes;
        bucket.mRxPackets = this.mRecycledSummaryEntry.rxPackets;
        bucket.mTxBytes = this.mRecycledSummaryEntry.txBytes;
        bucket.mTxPackets = this.mRecycledSummaryEntry.txPackets;
    }

    private boolean getNextHistoryBucket(Bucket bucket) {
        NetworkStatsHistory networkStatsHistory;
        if (bucket != null && (networkStatsHistory = this.mHistory) != null) {
            if (this.mEnumerationIndex < networkStatsHistory.size()) {
                networkStatsHistory = this.mHistory;
                int n = this.mEnumerationIndex;
                this.mEnumerationIndex = n + 1;
                this.mRecycledHistoryEntry = networkStatsHistory.getValues(n, this.mRecycledHistoryEntry);
                bucket.mUid = Bucket.convertUid(this.getUid());
                bucket.mTag = Bucket.convertTag(this.mTag);
                bucket.mState = this.mState;
                bucket.mDefaultNetworkStatus = -1;
                bucket.mMetered = -1;
                bucket.mRoaming = -1;
                bucket.mBeginTimeStamp = this.mRecycledHistoryEntry.bucketStart;
                bucket.mEndTimeStamp = this.mRecycledHistoryEntry.bucketStart + this.mRecycledHistoryEntry.bucketDuration;
                bucket.mRxBytes = this.mRecycledHistoryEntry.rxBytes;
                bucket.mRxPackets = this.mRecycledHistoryEntry.rxPackets;
                bucket.mTxBytes = this.mRecycledHistoryEntry.txBytes;
                bucket.mTxPackets = this.mRecycledHistoryEntry.txPackets;
                return true;
            }
            if (this.hasNextUid()) {
                this.stepHistory();
                return this.getNextHistoryBucket(bucket);
            }
        }
        return false;
    }

    private boolean getNextSummaryBucket(Bucket bucket) {
        if (bucket != null && this.mEnumerationIndex < this.mSummary.size()) {
            android.net.NetworkStats networkStats = this.mSummary;
            int n = this.mEnumerationIndex;
            this.mEnumerationIndex = n + 1;
            this.mRecycledSummaryEntry = networkStats.getValues(n, this.mRecycledSummaryEntry);
            this.fillBucketFromSummaryEntry(bucket);
            return true;
        }
        return false;
    }

    private int getUid() {
        if (this.isUidEnumeration()) {
            Object object;
            int n = this.mUidOrUidIndex;
            if (n >= 0 && n < ((int[])(object = this.mUids)).length) {
                return (int)object[n];
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Index=");
            ((StringBuilder)object).append(this.mUidOrUidIndex);
            ((StringBuilder)object).append(" mUids.length=");
            ((StringBuilder)object).append(this.mUids.length);
            throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
        }
        return this.mUidOrUidIndex;
    }

    private boolean hasNextUid() {
        boolean bl = this.isUidEnumeration();
        boolean bl2 = true;
        if (!bl || this.mUidOrUidIndex + 1 >= this.mUids.length) {
            bl2 = false;
        }
        return bl2;
    }

    private boolean isUidEnumeration() {
        boolean bl = this.mUids != null;
        return bl;
    }

    private void setSingleUidTagState(int n, int n2, int n3) {
        this.mUidOrUidIndex = n;
        this.mTag = n2;
        this.mState = n3;
    }

    private void stepHistory() {
        if (this.hasNextUid()) {
            this.stepUid();
            this.mHistory = null;
            try {
                this.mHistory = this.mSession.getHistoryIntervalForUid(this.mTemplate, this.getUid(), -1, 0, -1, this.mStartTimeStamp, this.mEndTimeStamp);
            }
            catch (RemoteException remoteException) {
                Log.w(TAG, remoteException);
            }
            this.mEnumerationIndex = 0;
        }
    }

    private void stepUid() {
        if (this.mUids != null) {
            ++this.mUidOrUidIndex;
        }
    }

    @Override
    public void close() {
        INetworkStatsSession iNetworkStatsSession = this.mSession;
        if (iNetworkStatsSession != null) {
            try {
                iNetworkStatsSession.close();
            }
            catch (RemoteException remoteException) {
                Log.w(TAG, remoteException);
            }
        }
        this.mSession = null;
        iNetworkStatsSession = this.mCloseGuard;
        if (iNetworkStatsSession != null) {
            iNetworkStatsSession.close();
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    Bucket getDeviceSummaryForNetwork() throws RemoteException {
        this.mSummary = this.mSession.getDeviceSummaryForNetwork(this.mTemplate, this.mStartTimeStamp, this.mEndTimeStamp);
        this.mEnumerationIndex = this.mSummary.size();
        return this.getSummaryAggregate();
    }

    public boolean getNextBucket(Bucket bucket) {
        if (this.mSummary != null) {
            return this.getNextSummaryBucket(bucket);
        }
        return this.getNextHistoryBucket(bucket);
    }

    Bucket getSummaryAggregate() {
        if (this.mSummary == null) {
            return null;
        }
        Bucket bucket = new Bucket();
        if (this.mRecycledSummaryEntry == null) {
            this.mRecycledSummaryEntry = new NetworkStats.Entry();
        }
        this.mSummary.getTotal(this.mRecycledSummaryEntry);
        this.fillBucketFromSummaryEntry(bucket);
        return bucket;
    }

    public boolean hasNextBucket() {
        Parcelable parcelable = this.mSummary;
        boolean bl = true;
        boolean bl2 = true;
        if (parcelable != null) {
            if (this.mEnumerationIndex >= ((android.net.NetworkStats)parcelable).size()) {
                bl2 = false;
            }
            return bl2;
        }
        parcelable = this.mHistory;
        if (parcelable != null) {
            bl2 = this.mEnumerationIndex >= ((NetworkStatsHistory)parcelable).size() && !this.hasNextUid() ? false : bl;
            return bl2;
        }
        return false;
    }

    void startHistoryEnumeration(int n, int n2, int n3) {
        this.mHistory = null;
        try {
            this.mHistory = this.mSession.getHistoryIntervalForUid(this.mTemplate, n, Bucket.convertSet(n3), n2, -1, this.mStartTimeStamp, this.mEndTimeStamp);
            this.setSingleUidTagState(n, n2, n3);
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, remoteException);
        }
        this.mEnumerationIndex = 0;
    }

    void startSummaryEnumeration() throws RemoteException {
        this.mSummary = this.mSession.getSummaryForAllUid(this.mTemplate, this.mStartTimeStamp, this.mEndTimeStamp, false);
        this.mEnumerationIndex = 0;
    }

    void startUserUidEnumeration() throws RemoteException {
        int[] arrn = this.mSession.getRelevantUids();
        IntArray intArray = new IntArray(arrn.length);
        for (int n : arrn) {
            NetworkStatsHistory networkStatsHistory = this.mSession.getHistoryIntervalForUid(this.mTemplate, n, -1, 0, -1, this.mStartTimeStamp, this.mEndTimeStamp);
            if (networkStatsHistory == null) continue;
            try {
                if (networkStatsHistory.size() <= 0) continue;
                intArray.add(n);
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error while getting history of uid ");
                stringBuilder.append(n);
                Log.w(TAG, stringBuilder.toString(), remoteException);
            }
        }
        this.mUids = intArray.toArray();
        this.mUidOrUidIndex = -1;
        this.stepHistory();
    }

    public static class Bucket {
        public static final int DEFAULT_NETWORK_ALL = -1;
        public static final int DEFAULT_NETWORK_NO = 1;
        public static final int DEFAULT_NETWORK_YES = 2;
        public static final int METERED_ALL = -1;
        public static final int METERED_NO = 1;
        public static final int METERED_YES = 2;
        public static final int ROAMING_ALL = -1;
        public static final int ROAMING_NO = 1;
        public static final int ROAMING_YES = 2;
        public static final int STATE_ALL = -1;
        public static final int STATE_DEFAULT = 1;
        public static final int STATE_FOREGROUND = 2;
        public static final int TAG_NONE = 0;
        public static final int UID_ALL = -1;
        public static final int UID_REMOVED = -4;
        public static final int UID_TETHERING = -5;
        private long mBeginTimeStamp;
        private int mDefaultNetworkStatus;
        private long mEndTimeStamp;
        private int mMetered;
        private int mRoaming;
        private long mRxBytes;
        private long mRxPackets;
        private int mState;
        private int mTag;
        private long mTxBytes;
        private long mTxPackets;
        private int mUid;

        private static int convertDefaultNetworkStatus(int n) {
            if (n != -1) {
                if (n != 0) {
                    if (n != 1) {
                        return 0;
                    }
                    return 2;
                }
                return 1;
            }
            return -1;
        }

        private static int convertMetered(int n) {
            if (n != -1) {
                if (n != 0) {
                    if (n != 1) {
                        return 0;
                    }
                    return 2;
                }
                return 1;
            }
            return -1;
        }

        private static int convertRoaming(int n) {
            if (n != -1) {
                if (n != 0) {
                    if (n != 1) {
                        return 0;
                    }
                    return 2;
                }
                return 1;
            }
            return -1;
        }

        private static int convertSet(int n) {
            if (n != -1) {
                if (n != 1) {
                    return n == 2;
                }
                return 0;
            }
            return -1;
        }

        private static int convertState(int n) {
            if (n != -1) {
                if (n != 0) {
                    if (n != 1) {
                        return 0;
                    }
                    return 2;
                }
                return 1;
            }
            return -1;
        }

        private static int convertTag(int n) {
            if (n != 0) {
                return n;
            }
            return 0;
        }

        private static int convertUid(int n) {
            if (n != -5) {
                if (n != -4) {
                    return n;
                }
                return -4;
            }
            return -5;
        }

        public int getDefaultNetworkStatus() {
            return this.mDefaultNetworkStatus;
        }

        public long getEndTimeStamp() {
            return this.mEndTimeStamp;
        }

        public int getMetered() {
            return this.mMetered;
        }

        public int getRoaming() {
            return this.mRoaming;
        }

        public long getRxBytes() {
            return this.mRxBytes;
        }

        public long getRxPackets() {
            return this.mRxPackets;
        }

        public long getStartTimeStamp() {
            return this.mBeginTimeStamp;
        }

        public int getState() {
            return this.mState;
        }

        public int getTag() {
            return this.mTag;
        }

        public long getTxBytes() {
            return this.mTxBytes;
        }

        public long getTxPackets() {
            return this.mTxPackets;
        }

        public int getUid() {
            return this.mUid;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface DefaultNetworkStatus {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Metered {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Roaming {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface State {
        }

    }

}

