/*
 * Decompiled with CFR 0.145.
 */
package android.apex;

import android.os.Parcel;
import android.os.Parcelable;

public class ApexSessionInfo
implements Parcelable {
    public static final Parcelable.Creator<ApexSessionInfo> CREATOR = new Parcelable.Creator<ApexSessionInfo>(){

        @Override
        public ApexSessionInfo createFromParcel(Parcel parcel) {
            ApexSessionInfo apexSessionInfo = new ApexSessionInfo();
            apexSessionInfo.readFromParcel(parcel);
            return apexSessionInfo;
        }

        public ApexSessionInfo[] newArray(int n) {
            return new ApexSessionInfo[n];
        }
    };
    public boolean isActivated;
    public boolean isActivationFailed;
    public boolean isRollbackFailed;
    public boolean isRollbackInProgress;
    public boolean isRolledBack;
    public boolean isStaged;
    public boolean isSuccess;
    public boolean isUnknown;
    public boolean isVerified;
    public int sessionId;

    @Override
    public int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        int n;
        int n2 = parcel.dataPosition();
        int n3 = parcel.readInt();
        if (n3 < 0) {
            return;
        }
        try {
            this.sessionId = parcel.readInt();
            n = parcel.dataPosition();
        }
        catch (Throwable throwable) {
            parcel.setDataPosition(n2 + n3);
            throw throwable;
        }
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.isUnknown = bl2;
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        bl2 = parcel.readInt() != 0;
        this.isVerified = bl2;
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        bl2 = parcel.readInt() != 0;
        this.isStaged = bl2;
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        bl2 = parcel.readInt() != 0;
        this.isActivated = bl2;
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        bl2 = parcel.readInt() != 0;
        this.isRollbackInProgress = bl2;
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        bl2 = parcel.readInt() != 0;
        this.isActivationFailed = bl2;
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        bl2 = parcel.readInt() != 0;
        this.isSuccess = bl2;
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        bl2 = parcel.readInt() != 0;
        this.isRolledBack = bl2;
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.isRollbackFailed = bl2;
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        parcel.setDataPosition(n2 + n3);
    }

    @Override
    public final void writeToParcel(Parcel parcel, int n) {
        n = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sessionId);
        parcel.writeInt((int)this.isUnknown);
        parcel.writeInt((int)this.isVerified);
        parcel.writeInt((int)this.isStaged);
        parcel.writeInt((int)this.isActivated);
        parcel.writeInt((int)this.isRollbackInProgress);
        parcel.writeInt((int)this.isActivationFailed);
        parcel.writeInt((int)this.isSuccess);
        parcel.writeInt((int)this.isRolledBack);
        parcel.writeInt((int)this.isRollbackFailed);
        int n2 = parcel.dataPosition();
        parcel.setDataPosition(n);
        parcel.writeInt(n2 - n);
        parcel.setDataPosition(n2);
    }

}

