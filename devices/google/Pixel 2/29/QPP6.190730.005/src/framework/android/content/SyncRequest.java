/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.accounts.Account;
import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class SyncRequest
implements Parcelable {
    public static final Parcelable.Creator<SyncRequest> CREATOR = new Parcelable.Creator<SyncRequest>(){

        @Override
        public SyncRequest createFromParcel(Parcel parcel) {
            return new SyncRequest(parcel);
        }

        public SyncRequest[] newArray(int n) {
            return new SyncRequest[n];
        }
    };
    private static final String TAG = "SyncRequest";
    @UnsupportedAppUsage
    private final Account mAccountToSync;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final String mAuthority;
    private final boolean mDisallowMetered;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final Bundle mExtras;
    private final boolean mIsAuthority;
    private final boolean mIsExpedited;
    @UnsupportedAppUsage
    private final boolean mIsPeriodic;
    private final long mSyncFlexTimeSecs;
    @UnsupportedAppUsage
    private final long mSyncRunTimeSecs;

    protected SyncRequest(Builder builder) {
        this.mSyncFlexTimeSecs = builder.mSyncFlexTimeSecs;
        this.mSyncRunTimeSecs = builder.mSyncRunTimeSecs;
        this.mAccountToSync = builder.mAccount;
        this.mAuthority = builder.mAuthority;
        int n = builder.mSyncType;
        boolean bl = false;
        boolean bl2 = n == 1;
        this.mIsPeriodic = bl2;
        bl2 = bl;
        if (builder.mSyncTarget == 2) {
            bl2 = true;
        }
        this.mIsAuthority = bl2;
        this.mIsExpedited = builder.mExpedited;
        this.mExtras = new Bundle(builder.mCustomExtras);
        this.mExtras.putAll(builder.mSyncConfigExtras);
        this.mDisallowMetered = builder.mDisallowMetered;
    }

    private SyncRequest(Parcel parcel) {
        Bundle bundle = parcel.readBundle();
        boolean bl = true;
        this.mExtras = Bundle.setDefusable(bundle, true);
        this.mSyncFlexTimeSecs = parcel.readLong();
        this.mSyncRunTimeSecs = parcel.readLong();
        boolean bl2 = parcel.readInt() != 0;
        this.mIsPeriodic = bl2;
        bl2 = parcel.readInt() != 0;
        this.mDisallowMetered = bl2;
        bl2 = parcel.readInt() != 0;
        this.mIsAuthority = bl2;
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.mIsExpedited = bl2;
        this.mAccountToSync = (Account)parcel.readParcelable(null);
        this.mAuthority = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Account getAccount() {
        return this.mAccountToSync;
    }

    public Bundle getBundle() {
        return this.mExtras;
    }

    public String getProvider() {
        return this.mAuthority;
    }

    public long getSyncFlexTime() {
        return this.mSyncFlexTimeSecs;
    }

    public long getSyncRunTime() {
        return this.mSyncRunTimeSecs;
    }

    public boolean isExpedited() {
        return this.mIsExpedited;
    }

    public boolean isPeriodic() {
        return this.mIsPeriodic;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.mExtras);
        parcel.writeLong(this.mSyncFlexTimeSecs);
        parcel.writeLong(this.mSyncRunTimeSecs);
        parcel.writeInt((int)this.mIsPeriodic);
        parcel.writeInt((int)this.mDisallowMetered);
        parcel.writeInt((int)this.mIsAuthority);
        parcel.writeInt((int)this.mIsExpedited);
        parcel.writeParcelable(this.mAccountToSync, n);
        parcel.writeString(this.mAuthority);
    }

    public static class Builder {
        private static final int SYNC_TARGET_ADAPTER = 2;
        private static final int SYNC_TARGET_UNKNOWN = 0;
        private static final int SYNC_TYPE_ONCE = 2;
        private static final int SYNC_TYPE_PERIODIC = 1;
        private static final int SYNC_TYPE_UNKNOWN = 0;
        private Account mAccount;
        private String mAuthority;
        private Bundle mCustomExtras;
        private boolean mDisallowMetered;
        private boolean mExpedited;
        private boolean mIgnoreBackoff;
        private boolean mIgnoreSettings;
        private boolean mIsManual;
        private boolean mNoRetry;
        private boolean mRequiresCharging;
        private Bundle mSyncConfigExtras;
        private long mSyncFlexTimeSecs;
        private long mSyncRunTimeSecs;
        private int mSyncTarget = 0;
        private int mSyncType = 0;

        private void setupInterval(long l, long l2) {
            if (l2 <= l) {
                this.mSyncRunTimeSecs = l;
                this.mSyncFlexTimeSecs = l2;
                return;
            }
            throw new IllegalArgumentException("Specified run time for the sync must be after the specified flex time.");
        }

        public SyncRequest build() {
            ContentResolver.validateSyncExtrasBundle(this.mCustomExtras);
            if (this.mCustomExtras == null) {
                this.mCustomExtras = new Bundle();
            }
            this.mSyncConfigExtras = new Bundle();
            if (this.mIgnoreBackoff) {
                this.mSyncConfigExtras.putBoolean("ignore_backoff", true);
            }
            if (this.mDisallowMetered) {
                this.mSyncConfigExtras.putBoolean("allow_metered", true);
            }
            if (this.mRequiresCharging) {
                this.mSyncConfigExtras.putBoolean("require_charging", true);
            }
            if (this.mIgnoreSettings) {
                this.mSyncConfigExtras.putBoolean("ignore_settings", true);
            }
            if (this.mNoRetry) {
                this.mSyncConfigExtras.putBoolean("do_not_retry", true);
            }
            if (this.mExpedited) {
                this.mSyncConfigExtras.putBoolean("expedited", true);
            }
            if (this.mIsManual) {
                this.mSyncConfigExtras.putBoolean("ignore_backoff", true);
                this.mSyncConfigExtras.putBoolean("ignore_settings", true);
            }
            if (this.mSyncType == 1 && (ContentResolver.invalidPeriodicExtras(this.mCustomExtras) || ContentResolver.invalidPeriodicExtras(this.mSyncConfigExtras))) {
                throw new IllegalArgumentException("Illegal extras were set");
            }
            if (this.mSyncTarget != 0) {
                return new SyncRequest(this);
            }
            throw new IllegalArgumentException("Must specify an adapter with setSyncAdapter(Account, String");
        }

        public Builder setDisallowMetered(boolean bl) {
            if (this.mIgnoreSettings && bl) {
                throw new IllegalArgumentException("setDisallowMetered(true) after having specified that settings are ignored.");
            }
            this.mDisallowMetered = bl;
            return this;
        }

        public Builder setExpedited(boolean bl) {
            this.mExpedited = bl;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mCustomExtras = bundle;
            return this;
        }

        public Builder setIgnoreBackoff(boolean bl) {
            this.mIgnoreBackoff = bl;
            return this;
        }

        public Builder setIgnoreSettings(boolean bl) {
            if (this.mDisallowMetered && bl) {
                throw new IllegalArgumentException("setIgnoreSettings(true) after having specified sync settings with this builder.");
            }
            this.mIgnoreSettings = bl;
            return this;
        }

        public Builder setManual(boolean bl) {
            this.mIsManual = bl;
            return this;
        }

        public Builder setNoRetry(boolean bl) {
            this.mNoRetry = bl;
            return this;
        }

        public Builder setRequiresCharging(boolean bl) {
            this.mRequiresCharging = bl;
            return this;
        }

        public Builder setSyncAdapter(Account account, String string2) {
            if (this.mSyncTarget == 0) {
                if (string2 != null && string2.length() == 0) {
                    throw new IllegalArgumentException("Authority must be non-empty");
                }
                this.mSyncTarget = 2;
                this.mAccount = account;
                this.mAuthority = string2;
                return this;
            }
            throw new IllegalArgumentException("Sync target has already been defined.");
        }

        public Builder syncOnce() {
            if (this.mSyncType == 0) {
                this.mSyncType = 2;
                this.setupInterval(0L, 0L);
                return this;
            }
            throw new IllegalArgumentException("Sync type has already been defined.");
        }

        public Builder syncPeriodic(long l, long l2) {
            if (this.mSyncType == 0) {
                this.mSyncType = 1;
                this.setupInterval(l, l2);
                return this;
            }
            throw new IllegalArgumentException("Sync type has already been defined.");
        }
    }

}

