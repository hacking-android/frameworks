/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class SyncAdapterType
implements Parcelable {
    public static final Parcelable.Creator<SyncAdapterType> CREATOR = new Parcelable.Creator<SyncAdapterType>(){

        @Override
        public SyncAdapterType createFromParcel(Parcel parcel) {
            return new SyncAdapterType(parcel);
        }

        public SyncAdapterType[] newArray(int n) {
            return new SyncAdapterType[n];
        }
    };
    public final String accountType;
    @UnsupportedAppUsage
    private final boolean allowParallelSyncs;
    public final String authority;
    @UnsupportedAppUsage
    private final boolean isAlwaysSyncable;
    public final boolean isKey;
    private final String packageName;
    @UnsupportedAppUsage
    private final String settingsActivity;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final boolean supportsUploading;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final boolean userVisible;

    public SyncAdapterType(Parcel parcel) {
        String string2 = parcel.readString();
        String string3 = parcel.readString();
        boolean bl = parcel.readInt() != 0;
        boolean bl2 = parcel.readInt() != 0;
        boolean bl3 = parcel.readInt() != 0;
        boolean bl4 = parcel.readInt() != 0;
        this(string2, string3, bl, bl2, bl3, bl4, parcel.readString(), parcel.readString());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private SyncAdapterType(String charSequence, String charSequence2) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (!TextUtils.isEmpty(charSequence2)) {
                this.authority = charSequence;
                this.accountType = charSequence2;
                this.userVisible = true;
                this.supportsUploading = true;
                this.isAlwaysSyncable = false;
                this.allowParallelSyncs = false;
                this.settingsActivity = null;
                this.isKey = true;
                this.packageName = null;
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("the accountType must not be empty: ");
            ((StringBuilder)charSequence).append((String)charSequence2);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("the authority must not be empty: ");
        ((StringBuilder)charSequence2).append((String)charSequence);
        throw new IllegalArgumentException(((StringBuilder)charSequence2).toString());
    }

    public SyncAdapterType(String charSequence, String charSequence2, boolean bl, boolean bl2) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (!TextUtils.isEmpty(charSequence2)) {
                this.authority = charSequence;
                this.accountType = charSequence2;
                this.userVisible = bl;
                this.supportsUploading = bl2;
                this.isAlwaysSyncable = false;
                this.allowParallelSyncs = false;
                this.settingsActivity = null;
                this.isKey = false;
                this.packageName = null;
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("the accountType must not be empty: ");
            ((StringBuilder)charSequence).append((String)charSequence2);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("the authority must not be empty: ");
        ((StringBuilder)charSequence2).append((String)charSequence);
        throw new IllegalArgumentException(((StringBuilder)charSequence2).toString());
    }

    public SyncAdapterType(String charSequence, String charSequence2, boolean bl, boolean bl2, boolean bl3, boolean bl4, String string2, String string3) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (!TextUtils.isEmpty(charSequence2)) {
                this.authority = charSequence;
                this.accountType = charSequence2;
                this.userVisible = bl;
                this.supportsUploading = bl2;
                this.isAlwaysSyncable = bl3;
                this.allowParallelSyncs = bl4;
                this.settingsActivity = string2;
                this.isKey = false;
                this.packageName = string3;
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("the accountType must not be empty: ");
            ((StringBuilder)charSequence).append((String)charSequence2);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("the authority must not be empty: ");
        ((StringBuilder)charSequence2).append((String)charSequence);
        throw new IllegalArgumentException(((StringBuilder)charSequence2).toString());
    }

    public static SyncAdapterType newKey(String string2, String string3) {
        return new SyncAdapterType(string2, string3);
    }

    public boolean allowParallelSyncs() {
        if (!this.isKey) {
            return this.allowParallelSyncs;
        }
        throw new IllegalStateException("this method is not allowed to be called when this is a key");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SyncAdapterType)) {
            return false;
        }
        object = (SyncAdapterType)object;
        if (!this.authority.equals(((SyncAdapterType)object).authority) || !this.accountType.equals(((SyncAdapterType)object).accountType)) {
            bl = false;
        }
        return bl;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getSettingsActivity() {
        if (!this.isKey) {
            return this.settingsActivity;
        }
        throw new IllegalStateException("this method is not allowed to be called when this is a key");
    }

    public int hashCode() {
        return (17 * 31 + this.authority.hashCode()) * 31 + this.accountType.hashCode();
    }

    public boolean isAlwaysSyncable() {
        if (!this.isKey) {
            return this.isAlwaysSyncable;
        }
        throw new IllegalStateException("this method is not allowed to be called when this is a key");
    }

    public boolean isUserVisible() {
        if (!this.isKey) {
            return this.userVisible;
        }
        throw new IllegalStateException("this method is not allowed to be called when this is a key");
    }

    public boolean supportsUploading() {
        if (!this.isKey) {
            return this.supportsUploading;
        }
        throw new IllegalStateException("this method is not allowed to be called when this is a key");
    }

    public String toString() {
        if (this.isKey) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SyncAdapterType Key {name=");
            stringBuilder.append(this.authority);
            stringBuilder.append(", type=");
            stringBuilder.append(this.accountType);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SyncAdapterType {name=");
        stringBuilder.append(this.authority);
        stringBuilder.append(", type=");
        stringBuilder.append(this.accountType);
        stringBuilder.append(", userVisible=");
        stringBuilder.append(this.userVisible);
        stringBuilder.append(", supportsUploading=");
        stringBuilder.append(this.supportsUploading);
        stringBuilder.append(", isAlwaysSyncable=");
        stringBuilder.append(this.isAlwaysSyncable);
        stringBuilder.append(", allowParallelSyncs=");
        stringBuilder.append(this.allowParallelSyncs);
        stringBuilder.append(", settingsActivity=");
        stringBuilder.append(this.settingsActivity);
        stringBuilder.append(", packageName=");
        stringBuilder.append(this.packageName);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (!this.isKey) {
            parcel.writeString(this.authority);
            parcel.writeString(this.accountType);
            parcel.writeInt((int)this.userVisible);
            parcel.writeInt((int)this.supportsUploading);
            parcel.writeInt((int)this.isAlwaysSyncable);
            parcel.writeInt((int)this.allowParallelSyncs);
            parcel.writeString(this.settingsActivity);
            parcel.writeString(this.packageName);
            return;
        }
        throw new IllegalStateException("keys aren't parcelable");
    }

}

