/*
 * Decompiled with CFR 0.145.
 */
package android.app.job;

import android.annotation.UnsupportedAppUsage;
import android.content.ClipData;
import android.content.ComponentName;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.TimeUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class JobInfo
implements Parcelable {
    public static final int BACKOFF_POLICY_EXPONENTIAL = 1;
    public static final int BACKOFF_POLICY_LINEAR = 0;
    public static final int CONSTRAINT_FLAG_BATTERY_NOT_LOW = 2;
    public static final int CONSTRAINT_FLAG_CHARGING = 1;
    public static final int CONSTRAINT_FLAG_DEVICE_IDLE = 4;
    public static final int CONSTRAINT_FLAG_STORAGE_NOT_LOW = 8;
    public static final Parcelable.Creator<JobInfo> CREATOR;
    public static final int DEFAULT_BACKOFF_POLICY = 1;
    public static final long DEFAULT_INITIAL_BACKOFF_MILLIS = 30000L;
    public static final int FLAG_EXEMPT_FROM_APP_STANDBY = 8;
    public static final int FLAG_IMPORTANT_WHILE_FOREGROUND = 2;
    public static final int FLAG_PREFETCH = 4;
    @UnsupportedAppUsage
    public static final int FLAG_WILL_BE_FOREGROUND = 1;
    public static final long MAX_BACKOFF_DELAY_MILLIS = 18000000L;
    public static final long MIN_BACKOFF_MILLIS = 10000L;
    private static final long MIN_FLEX_MILLIS = 300000L;
    private static final long MIN_PERIOD_MILLIS = 900000L;
    public static final int NETWORK_BYTES_UNKNOWN = -1;
    public static final int NETWORK_TYPE_ANY = 1;
    public static final int NETWORK_TYPE_CELLULAR = 4;
    @Deprecated
    public static final int NETWORK_TYPE_METERED = 4;
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_NOT_ROAMING = 3;
    public static final int NETWORK_TYPE_UNMETERED = 2;
    public static final int PRIORITY_ADJ_ALWAYS_RUNNING = -80;
    public static final int PRIORITY_ADJ_OFTEN_RUNNING = -40;
    public static final int PRIORITY_BOUND_FOREGROUND_SERVICE = 30;
    public static final int PRIORITY_DEFAULT = 0;
    @UnsupportedAppUsage
    public static final int PRIORITY_FOREGROUND_APP = 30;
    @UnsupportedAppUsage
    public static final int PRIORITY_FOREGROUND_SERVICE = 35;
    public static final int PRIORITY_SYNC_EXPEDITED = 10;
    public static final int PRIORITY_SYNC_INITIALIZATION = 20;
    public static final int PRIORITY_TOP_APP = 40;
    private static String TAG;
    private final int backoffPolicy;
    private final ClipData clipData;
    private final int clipGrantFlags;
    private final int constraintFlags;
    private final PersistableBundle extras;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final int flags;
    private final long flexMillis;
    private final boolean hasEarlyConstraint;
    private final boolean hasLateConstraint;
    private final long initialBackoffMillis;
    private final long intervalMillis;
    private final boolean isPeriodic;
    private final boolean isPersisted;
    @UnsupportedAppUsage
    private final int jobId;
    private final long maxExecutionDelayMillis;
    private final long minLatencyMillis;
    private final long networkDownloadBytes;
    private final NetworkRequest networkRequest;
    private final long networkUploadBytes;
    private final int priority;
    @UnsupportedAppUsage
    private final ComponentName service;
    private final Bundle transientExtras;
    private final long triggerContentMaxDelay;
    private final long triggerContentUpdateDelay;
    private final TriggerContentUri[] triggerContentUris;

    static {
        TAG = "JobInfo";
        CREATOR = new Parcelable.Creator<JobInfo>(){

            @Override
            public JobInfo createFromParcel(Parcel parcel) {
                return new JobInfo(parcel);
            }

            public JobInfo[] newArray(int n) {
                return new JobInfo[n];
            }
        };
    }

    private JobInfo(Builder builder) {
        this.jobId = builder.mJobId;
        this.extras = builder.mExtras.deepCopy();
        this.transientExtras = builder.mTransientExtras.deepCopy();
        this.clipData = builder.mClipData;
        this.clipGrantFlags = builder.mClipGrantFlags;
        this.service = builder.mJobService;
        this.constraintFlags = builder.mConstraintFlags;
        TriggerContentUri[] arrtriggerContentUri = builder.mTriggerContentUris != null ? builder.mTriggerContentUris.toArray(new TriggerContentUri[builder.mTriggerContentUris.size()]) : null;
        this.triggerContentUris = arrtriggerContentUri;
        this.triggerContentUpdateDelay = builder.mTriggerContentUpdateDelay;
        this.triggerContentMaxDelay = builder.mTriggerContentMaxDelay;
        this.networkRequest = builder.mNetworkRequest;
        this.networkDownloadBytes = builder.mNetworkDownloadBytes;
        this.networkUploadBytes = builder.mNetworkUploadBytes;
        this.minLatencyMillis = builder.mMinLatencyMillis;
        this.maxExecutionDelayMillis = builder.mMaxExecutionDelayMillis;
        this.isPeriodic = builder.mIsPeriodic;
        this.isPersisted = builder.mIsPersisted;
        this.intervalMillis = builder.mIntervalMillis;
        this.flexMillis = builder.mFlexMillis;
        this.initialBackoffMillis = builder.mInitialBackoffMillis;
        this.backoffPolicy = builder.mBackoffPolicy;
        this.hasEarlyConstraint = builder.mHasEarlyConstraint;
        this.hasLateConstraint = builder.mHasLateConstraint;
        this.priority = builder.mPriority;
        this.flags = builder.mFlags;
    }

    private JobInfo(Parcel parcel) {
        this.jobId = parcel.readInt();
        this.extras = parcel.readPersistableBundle();
        this.transientExtras = parcel.readBundle();
        if (parcel.readInt() != 0) {
            this.clipData = ClipData.CREATOR.createFromParcel(parcel);
            this.clipGrantFlags = parcel.readInt();
        } else {
            this.clipData = null;
            this.clipGrantFlags = 0;
        }
        this.service = (ComponentName)parcel.readParcelable(null);
        this.constraintFlags = parcel.readInt();
        this.triggerContentUris = parcel.createTypedArray(TriggerContentUri.CREATOR);
        this.triggerContentUpdateDelay = parcel.readLong();
        this.triggerContentMaxDelay = parcel.readLong();
        this.networkRequest = parcel.readInt() != 0 ? NetworkRequest.CREATOR.createFromParcel(parcel) : null;
        this.networkDownloadBytes = parcel.readLong();
        this.networkUploadBytes = parcel.readLong();
        this.minLatencyMillis = parcel.readLong();
        this.maxExecutionDelayMillis = parcel.readLong();
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n == 1;
        this.isPeriodic = bl2;
        bl2 = parcel.readInt() == 1;
        this.isPersisted = bl2;
        this.intervalMillis = parcel.readLong();
        this.flexMillis = parcel.readLong();
        this.initialBackoffMillis = parcel.readLong();
        this.backoffPolicy = parcel.readInt();
        bl2 = parcel.readInt() == 1;
        this.hasEarlyConstraint = bl2;
        bl2 = parcel.readInt() == 1 ? bl : false;
        this.hasLateConstraint = bl2;
        this.priority = parcel.readInt();
        this.flags = parcel.readInt();
    }

    public static final long getMinBackoffMillis() {
        return 10000L;
    }

    public static final long getMinFlexMillis() {
        return 300000L;
    }

    public static final long getMinPeriodMillis() {
        return 900000L;
    }

    public static String getPriorityString(int n) {
        if (n != 0) {
            if (n != 10) {
                if (n != 20) {
                    if (n != 30) {
                        if (n != 35) {
                            if (n != 40) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(n);
                                stringBuilder.append(" [UNKNOWN]");
                                return stringBuilder.toString();
                            }
                            return "40 [TOP_APP]";
                        }
                        return "35 [FGS_APP]";
                    }
                    return "30 [BFGS_APP]";
                }
                return "20 [SYNC_INITIALIZATION]";
            }
            return "10 [SYNC_EXPEDITED]";
        }
        return "0 [DEFAULT]";
    }

    private static boolean kindofEqualsBundle(BaseBundle baseBundle, BaseBundle baseBundle2) {
        boolean bl = baseBundle == baseBundle2 || baseBundle != null && baseBundle.kindofEquals(baseBundle2);
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof JobInfo)) {
            return false;
        }
        object = (JobInfo)object;
        if (this.jobId != ((JobInfo)object).jobId) {
            return false;
        }
        if (!JobInfo.kindofEqualsBundle(this.extras, ((JobInfo)object).extras)) {
            return false;
        }
        if (!JobInfo.kindofEqualsBundle(this.transientExtras, ((JobInfo)object).transientExtras)) {
            return false;
        }
        if (this.clipData != ((JobInfo)object).clipData) {
            return false;
        }
        if (this.clipGrantFlags != ((JobInfo)object).clipGrantFlags) {
            return false;
        }
        if (!Objects.equals(this.service, ((JobInfo)object).service)) {
            return false;
        }
        if (this.constraintFlags != ((JobInfo)object).constraintFlags) {
            return false;
        }
        if (!Arrays.equals(this.triggerContentUris, ((JobInfo)object).triggerContentUris)) {
            return false;
        }
        if (this.triggerContentUpdateDelay != ((JobInfo)object).triggerContentUpdateDelay) {
            return false;
        }
        if (this.triggerContentMaxDelay != ((JobInfo)object).triggerContentMaxDelay) {
            return false;
        }
        if (this.hasEarlyConstraint != ((JobInfo)object).hasEarlyConstraint) {
            return false;
        }
        if (this.hasLateConstraint != ((JobInfo)object).hasLateConstraint) {
            return false;
        }
        if (!Objects.equals(this.networkRequest, ((JobInfo)object).networkRequest)) {
            return false;
        }
        if (this.networkDownloadBytes != ((JobInfo)object).networkDownloadBytes) {
            return false;
        }
        if (this.networkUploadBytes != ((JobInfo)object).networkUploadBytes) {
            return false;
        }
        if (this.minLatencyMillis != ((JobInfo)object).minLatencyMillis) {
            return false;
        }
        if (this.maxExecutionDelayMillis != ((JobInfo)object).maxExecutionDelayMillis) {
            return false;
        }
        if (this.isPeriodic != ((JobInfo)object).isPeriodic) {
            return false;
        }
        if (this.isPersisted != ((JobInfo)object).isPersisted) {
            return false;
        }
        if (this.intervalMillis != ((JobInfo)object).intervalMillis) {
            return false;
        }
        if (this.flexMillis != ((JobInfo)object).flexMillis) {
            return false;
        }
        if (this.initialBackoffMillis != ((JobInfo)object).initialBackoffMillis) {
            return false;
        }
        if (this.backoffPolicy != ((JobInfo)object).backoffPolicy) {
            return false;
        }
        if (this.priority != ((JobInfo)object).priority) {
            return false;
        }
        return this.flags == ((JobInfo)object).flags;
    }

    public int getBackoffPolicy() {
        return this.backoffPolicy;
    }

    public ClipData getClipData() {
        return this.clipData;
    }

    public int getClipGrantFlags() {
        return this.clipGrantFlags;
    }

    public int getConstraintFlags() {
        return this.constraintFlags;
    }

    @Deprecated
    public long getEstimatedNetworkBytes() {
        if (this.networkDownloadBytes == -1L && this.networkUploadBytes == -1L) {
            return -1L;
        }
        long l = this.networkDownloadBytes;
        if (l == -1L) {
            return this.networkUploadBytes;
        }
        long l2 = this.networkUploadBytes;
        if (l2 == -1L) {
            return l;
        }
        return l + l2;
    }

    public long getEstimatedNetworkDownloadBytes() {
        return this.networkDownloadBytes;
    }

    public long getEstimatedNetworkUploadBytes() {
        return this.networkUploadBytes;
    }

    public PersistableBundle getExtras() {
        return this.extras;
    }

    public int getFlags() {
        return this.flags;
    }

    public long getFlexMillis() {
        return this.flexMillis;
    }

    public int getId() {
        return this.jobId;
    }

    public long getInitialBackoffMillis() {
        return this.initialBackoffMillis;
    }

    public long getIntervalMillis() {
        return this.intervalMillis;
    }

    public long getMaxExecutionDelayMillis() {
        return this.maxExecutionDelayMillis;
    }

    public long getMinLatencyMillis() {
        return this.minLatencyMillis;
    }

    @Deprecated
    public int getNetworkType() {
        NetworkRequest networkRequest = this.networkRequest;
        if (networkRequest == null) {
            return 0;
        }
        if (networkRequest.networkCapabilities.hasCapability(11)) {
            return 2;
        }
        if (this.networkRequest.networkCapabilities.hasCapability(18)) {
            return 3;
        }
        if (this.networkRequest.networkCapabilities.hasTransport(0)) {
            return 4;
        }
        return 1;
    }

    public int getPriority() {
        return this.priority;
    }

    public NetworkRequest getRequiredNetwork() {
        return this.networkRequest;
    }

    public ComponentName getService() {
        return this.service;
    }

    public Bundle getTransientExtras() {
        return this.transientExtras;
    }

    public long getTriggerContentMaxDelay() {
        return this.triggerContentMaxDelay;
    }

    public long getTriggerContentUpdateDelay() {
        return this.triggerContentUpdateDelay;
    }

    public TriggerContentUri[] getTriggerContentUris() {
        return this.triggerContentUris;
    }

    public boolean hasEarlyConstraint() {
        return this.hasEarlyConstraint;
    }

    public boolean hasLateConstraint() {
        return this.hasLateConstraint;
    }

    public int hashCode() {
        int n = this.jobId;
        Parcelable parcelable = this.extras;
        int n2 = n;
        if (parcelable != null) {
            n2 = n * 31 + parcelable.hashCode();
        }
        parcelable = this.transientExtras;
        n = n2;
        if (parcelable != null) {
            n = n2 * 31 + parcelable.hashCode();
        }
        parcelable = this.clipData;
        n2 = n;
        if (parcelable != null) {
            n2 = n * 31 + parcelable.hashCode();
        }
        n = n2 * 31 + this.clipGrantFlags;
        parcelable = this.service;
        n2 = n;
        if (parcelable != null) {
            n2 = n * 31 + ((ComponentName)parcelable).hashCode();
        }
        n = n2 * 31 + this.constraintFlags;
        parcelable = this.triggerContentUris;
        n2 = n;
        if (parcelable != null) {
            n2 = n * 31 + Arrays.hashCode((Object[])parcelable);
        }
        n = (((n2 * 31 + Long.hashCode(this.triggerContentUpdateDelay)) * 31 + Long.hashCode(this.triggerContentMaxDelay)) * 31 + Boolean.hashCode(this.hasEarlyConstraint)) * 31 + Boolean.hashCode(this.hasLateConstraint);
        parcelable = this.networkRequest;
        n2 = n;
        if (parcelable != null) {
            n2 = n * 31 + ((NetworkRequest)parcelable).hashCode();
        }
        return (((((((((((n2 * 31 + Long.hashCode(this.networkDownloadBytes)) * 31 + Long.hashCode(this.networkUploadBytes)) * 31 + Long.hashCode(this.minLatencyMillis)) * 31 + Long.hashCode(this.maxExecutionDelayMillis)) * 31 + Boolean.hashCode(this.isPeriodic)) * 31 + Boolean.hashCode(this.isPersisted)) * 31 + Long.hashCode(this.intervalMillis)) * 31 + Long.hashCode(this.flexMillis)) * 31 + Long.hashCode(this.initialBackoffMillis)) * 31 + this.backoffPolicy) * 31 + this.priority) * 31 + this.flags;
    }

    public boolean isExemptedFromAppStandby() {
        boolean bl = (this.flags & 8) != 0 && !this.isPeriodic();
        return bl;
    }

    public boolean isImportantWhileForeground() {
        boolean bl = (this.flags & 2) != 0;
        return bl;
    }

    public boolean isPeriodic() {
        return this.isPeriodic;
    }

    public boolean isPersisted() {
        return this.isPersisted;
    }

    public boolean isPrefetch() {
        boolean bl = (this.flags & 4) != 0;
        return bl;
    }

    public boolean isRequireBatteryNotLow() {
        boolean bl = (this.constraintFlags & 2) != 0;
        return bl;
    }

    public boolean isRequireCharging() {
        int n = this.constraintFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isRequireDeviceIdle() {
        boolean bl = (this.constraintFlags & 4) != 0;
        return bl;
    }

    public boolean isRequireStorageNotLow() {
        boolean bl = (this.constraintFlags & 8) != 0;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(job:");
        stringBuilder.append(this.jobId);
        stringBuilder.append("/");
        stringBuilder.append(this.service.flattenToShortString());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.jobId);
        parcel.writePersistableBundle(this.extras);
        parcel.writeBundle(this.transientExtras);
        if (this.clipData != null) {
            parcel.writeInt(1);
            this.clipData.writeToParcel(parcel, n);
            parcel.writeInt(this.clipGrantFlags);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeParcelable(this.service, n);
        parcel.writeInt(this.constraintFlags);
        parcel.writeTypedArray((Parcelable[])this.triggerContentUris, n);
        parcel.writeLong(this.triggerContentUpdateDelay);
        parcel.writeLong(this.triggerContentMaxDelay);
        if (this.networkRequest != null) {
            parcel.writeInt(1);
            this.networkRequest.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.networkDownloadBytes);
        parcel.writeLong(this.networkUploadBytes);
        parcel.writeLong(this.minLatencyMillis);
        parcel.writeLong(this.maxExecutionDelayMillis);
        parcel.writeInt((int)this.isPeriodic);
        parcel.writeInt((int)this.isPersisted);
        parcel.writeLong(this.intervalMillis);
        parcel.writeLong(this.flexMillis);
        parcel.writeLong(this.initialBackoffMillis);
        parcel.writeInt(this.backoffPolicy);
        parcel.writeInt((int)this.hasEarlyConstraint);
        parcel.writeInt((int)this.hasLateConstraint);
        parcel.writeInt(this.priority);
        parcel.writeInt(this.flags);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BackoffPolicy {
    }

    public static final class Builder {
        private int mBackoffPolicy = 1;
        private boolean mBackoffPolicySet = false;
        private ClipData mClipData;
        private int mClipGrantFlags;
        private int mConstraintFlags;
        private PersistableBundle mExtras = PersistableBundle.EMPTY;
        private int mFlags;
        private long mFlexMillis;
        private boolean mHasEarlyConstraint;
        private boolean mHasLateConstraint;
        private long mInitialBackoffMillis = 30000L;
        private long mIntervalMillis;
        private boolean mIsPeriodic;
        private boolean mIsPersisted;
        private final int mJobId;
        private final ComponentName mJobService;
        private long mMaxExecutionDelayMillis;
        private long mMinLatencyMillis;
        private long mNetworkDownloadBytes = -1L;
        private NetworkRequest mNetworkRequest;
        private long mNetworkUploadBytes = -1L;
        private int mPriority = 0;
        private Bundle mTransientExtras = Bundle.EMPTY;
        private long mTriggerContentMaxDelay = -1L;
        private long mTriggerContentUpdateDelay = -1L;
        private ArrayList<TriggerContentUri> mTriggerContentUris;

        public Builder(int n, ComponentName componentName) {
            this.mJobService = componentName;
            this.mJobId = n;
        }

        public Builder addTriggerContentUri(TriggerContentUri triggerContentUri) {
            if (this.mTriggerContentUris == null) {
                this.mTriggerContentUris = new ArrayList();
            }
            this.mTriggerContentUris.add(triggerContentUri);
            return this;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public JobInfo build() {
            NetworkRequest networkRequest;
            if ((this.mNetworkDownloadBytes > 0L || this.mNetworkUploadBytes > 0L) && this.mNetworkRequest == null) throw new IllegalArgumentException("Can't provide estimated network usage without requiring a network");
            if (this.mIsPersisted && (networkRequest = this.mNetworkRequest) != null && networkRequest.networkCapabilities.getNetworkSpecifier() != null) {
                throw new IllegalArgumentException("Network specifiers aren't supported for persistent jobs");
            }
            if (this.mIsPeriodic) {
                if (this.mMaxExecutionDelayMillis != 0L) throw new IllegalArgumentException("Can't call setOverrideDeadline() on a periodic job.");
                if (this.mMinLatencyMillis != 0L) throw new IllegalArgumentException("Can't call setMinimumLatency() on a periodic job");
                if (this.mTriggerContentUris != null) {
                    throw new IllegalArgumentException("Can't call addTriggerContentUri() on a periodic job");
                }
            }
            if (this.mIsPersisted) {
                if (this.mTriggerContentUris != null) throw new IllegalArgumentException("Can't call addTriggerContentUri() on a persisted job");
                if (!this.mTransientExtras.isEmpty()) throw new IllegalArgumentException("Can't call setTransientExtras() on a persisted job");
                if (this.mClipData != null) {
                    throw new IllegalArgumentException("Can't call setClipData() on a persisted job");
                }
            }
            if ((this.mFlags & 2) != 0 && this.mHasEarlyConstraint) {
                throw new IllegalArgumentException("An important while foreground job cannot have a time delay");
            }
            if (!this.mBackoffPolicySet || (this.mConstraintFlags & 4) == 0) return new JobInfo(this);
            throw new IllegalArgumentException("An idle mode job will not respect any back-off policy, so calling setBackoffCriteria with setRequiresDeviceIdle is an error.");
        }

        public Builder setBackoffCriteria(long l, int n) {
            long l2 = JobInfo.getMinBackoffMillis();
            long l3 = l;
            if (l < l2) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Requested backoff ");
                stringBuilder.append(TimeUtils.formatDuration(l));
                stringBuilder.append(" for job ");
                stringBuilder.append(this.mJobId);
                stringBuilder.append(" is too small; raising to ");
                stringBuilder.append(TimeUtils.formatDuration(l2));
                Log.w(string2, stringBuilder.toString());
                l3 = l2;
            }
            this.mBackoffPolicySet = true;
            this.mInitialBackoffMillis = l3;
            this.mBackoffPolicy = n;
            return this;
        }

        public Builder setClipData(ClipData clipData, int n) {
            this.mClipData = clipData;
            this.mClipGrantFlags = n;
            return this;
        }

        @Deprecated
        public Builder setEstimatedNetworkBytes(long l) {
            return this.setEstimatedNetworkBytes(l, -1L);
        }

        public Builder setEstimatedNetworkBytes(long l, long l2) {
            this.mNetworkDownloadBytes = l;
            this.mNetworkUploadBytes = l2;
            return this;
        }

        public Builder setExtras(PersistableBundle persistableBundle) {
            this.mExtras = persistableBundle;
            return this;
        }

        @UnsupportedAppUsage
        public Builder setFlags(int n) {
            this.mFlags = n;
            return this;
        }

        public Builder setImportantWhileForeground(boolean bl) {
            this.mFlags = bl ? (this.mFlags |= 2) : (this.mFlags &= -3);
            return this;
        }

        @Deprecated
        public Builder setIsPrefetch(boolean bl) {
            return this.setPrefetch(bl);
        }

        public Builder setMinimumLatency(long l) {
            this.mMinLatencyMillis = l;
            this.mHasEarlyConstraint = true;
            return this;
        }

        public Builder setOverrideDeadline(long l) {
            this.mMaxExecutionDelayMillis = l;
            this.mHasLateConstraint = true;
            return this;
        }

        public Builder setPeriodic(long l) {
            return this.setPeriodic(l, l);
        }

        public Builder setPeriodic(long l, long l2) {
            CharSequence charSequence;
            CharSequence charSequence2;
            long l3 = JobInfo.getMinPeriodMillis();
            long l4 = l;
            if (l < l3) {
                charSequence = TAG;
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("Requested interval ");
                ((StringBuilder)charSequence2).append(TimeUtils.formatDuration(l));
                ((StringBuilder)charSequence2).append(" for job ");
                ((StringBuilder)charSequence2).append(this.mJobId);
                ((StringBuilder)charSequence2).append(" is too small; raising to ");
                ((StringBuilder)charSequence2).append(TimeUtils.formatDuration(l3));
                Log.w((String)charSequence, ((StringBuilder)charSequence2).toString());
                l4 = l3;
            }
            l3 = Math.max(5L * l4 / 100L, JobInfo.getMinFlexMillis());
            l = l2;
            if (l2 < l3) {
                charSequence2 = TAG;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Requested flex ");
                ((StringBuilder)charSequence).append(TimeUtils.formatDuration(l2));
                ((StringBuilder)charSequence).append(" for job ");
                ((StringBuilder)charSequence).append(this.mJobId);
                ((StringBuilder)charSequence).append(" is too small; raising to ");
                ((StringBuilder)charSequence).append(TimeUtils.formatDuration(l3));
                Log.w((String)charSequence2, ((StringBuilder)charSequence).toString());
                l = l3;
            }
            this.mIsPeriodic = true;
            this.mIntervalMillis = l4;
            this.mFlexMillis = l;
            this.mHasLateConstraint = true;
            this.mHasEarlyConstraint = true;
            return this;
        }

        public Builder setPersisted(boolean bl) {
            this.mIsPersisted = bl;
            return this;
        }

        public Builder setPrefetch(boolean bl) {
            this.mFlags = bl ? (this.mFlags |= 4) : (this.mFlags &= -5);
            return this;
        }

        @UnsupportedAppUsage
        public Builder setPriority(int n) {
            this.mPriority = n;
            return this;
        }

        public Builder setRequiredNetwork(NetworkRequest networkRequest) {
            this.mNetworkRequest = networkRequest;
            return this;
        }

        public Builder setRequiredNetworkType(int n) {
            if (n == 0) {
                return this.setRequiredNetwork(null);
            }
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(12);
            builder.addCapability(16);
            builder.removeCapability(15);
            if (n != 1) {
                if (n == 2) {
                    builder.addCapability(11);
                } else if (n == 3) {
                    builder.addCapability(18);
                } else if (n == 4) {
                    builder.addTransportType(0);
                }
            }
            return this.setRequiredNetwork(builder.build());
        }

        public Builder setRequiresBatteryNotLow(boolean bl) {
            int n = this.mConstraintFlags;
            int n2 = bl ? 2 : 0;
            this.mConstraintFlags = n & -3 | n2;
            return this;
        }

        public Builder setRequiresCharging(boolean bl) {
            this.mConstraintFlags = this.mConstraintFlags & -2 | bl;
            return this;
        }

        public Builder setRequiresDeviceIdle(boolean bl) {
            int n = this.mConstraintFlags;
            int n2 = bl ? 4 : 0;
            this.mConstraintFlags = n & -5 | n2;
            return this;
        }

        public Builder setRequiresStorageNotLow(boolean bl) {
            int n = this.mConstraintFlags;
            int n2 = bl ? 8 : 0;
            this.mConstraintFlags = n & -9 | n2;
            return this;
        }

        public Builder setTransientExtras(Bundle bundle) {
            this.mTransientExtras = bundle;
            return this;
        }

        public Builder setTriggerContentMaxDelay(long l) {
            this.mTriggerContentMaxDelay = l;
            return this;
        }

        public Builder setTriggerContentUpdateDelay(long l) {
            this.mTriggerContentUpdateDelay = l;
            return this;
        }

        public String summarize() {
            Object object = this.mJobService;
            object = object != null ? ((ComponentName)object).flattenToShortString() : "null";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("JobInfo.Builder{job:");
            stringBuilder.append(this.mJobId);
            stringBuilder.append("/");
            stringBuilder.append((String)object);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NetworkType {
    }

    public static final class TriggerContentUri
    implements Parcelable {
        public static final Parcelable.Creator<TriggerContentUri> CREATOR = new Parcelable.Creator<TriggerContentUri>(){

            @Override
            public TriggerContentUri createFromParcel(Parcel parcel) {
                return new TriggerContentUri(parcel);
            }

            public TriggerContentUri[] newArray(int n) {
                return new TriggerContentUri[n];
            }
        };
        public static final int FLAG_NOTIFY_FOR_DESCENDANTS = 1;
        private final int mFlags;
        private final Uri mUri;

        public TriggerContentUri(Uri uri, int n) {
            this.mUri = uri;
            this.mFlags = n;
        }

        private TriggerContentUri(Parcel parcel) {
            this.mUri = Uri.CREATOR.createFromParcel(parcel);
            this.mFlags = parcel.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof TriggerContentUri;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (TriggerContentUri)object;
            bl = bl2;
            if (Objects.equals(((TriggerContentUri)object).mUri, this.mUri)) {
                bl = bl2;
                if (((TriggerContentUri)object).mFlags == this.mFlags) {
                    bl = true;
                }
            }
            return bl;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public int hashCode() {
            Uri uri = this.mUri;
            int n = uri == null ? 0 : uri.hashCode();
            return n ^ this.mFlags;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            this.mUri.writeToParcel(parcel, n);
            parcel.writeInt(this.mFlags);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Flags {
        }

    }

}

