/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.accounts.Account;
import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class SyncInfo
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<SyncInfo> CREATOR;
    private static final Account REDACTED_ACCOUNT;
    public final Account account;
    public final String authority;
    @UnsupportedAppUsage
    public final int authorityId;
    public final long startTime;

    static {
        REDACTED_ACCOUNT = new Account("*****", "*****");
        CREATOR = new Parcelable.Creator<SyncInfo>(){

            @Override
            public SyncInfo createFromParcel(Parcel parcel) {
                return new SyncInfo(parcel);
            }

            public SyncInfo[] newArray(int n) {
                return new SyncInfo[n];
            }
        };
    }

    @UnsupportedAppUsage
    public SyncInfo(int n, Account account, String string2, long l) {
        this.authorityId = n;
        this.account = account;
        this.authority = string2;
        this.startTime = l;
    }

    public SyncInfo(SyncInfo syncInfo) {
        this.authorityId = syncInfo.authorityId;
        this.account = new Account(syncInfo.account.name, syncInfo.account.type);
        this.authority = syncInfo.authority;
        this.startTime = syncInfo.startTime;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    SyncInfo(Parcel parcel) {
        this.authorityId = parcel.readInt();
        this.account = (Account)parcel.readParcelable(Account.class.getClassLoader());
        this.authority = parcel.readString();
        this.startTime = parcel.readLong();
    }

    public static SyncInfo createAccountRedacted(int n, String string2, long l) {
        return new SyncInfo(n, REDACTED_ACCOUNT, string2, l);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.authorityId);
        parcel.writeParcelable(this.account, n);
        parcel.writeString(this.authority);
        parcel.writeLong(this.startTime);
    }

}

