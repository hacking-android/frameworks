/*
 * Decompiled with CFR 0.145.
 */
package android.gsi;

import android.os.Parcel;
import android.os.Parcelable;

public class GsiInstallParams
implements Parcelable {
    public static final Parcelable.Creator<GsiInstallParams> CREATOR = new Parcelable.Creator<GsiInstallParams>(){

        @Override
        public GsiInstallParams createFromParcel(Parcel parcel) {
            GsiInstallParams gsiInstallParams = new GsiInstallParams();
            gsiInstallParams.readFromParcel(parcel);
            return gsiInstallParams;
        }

        public GsiInstallParams[] newArray(int n) {
            return new GsiInstallParams[n];
        }
    };
    public long gsiSize;
    public String installDir;
    public long userdataSize;
    public boolean wipeUserdata;

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
            this.installDir = parcel.readString();
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
        this.gsiSize = parcel.readLong();
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        this.userdataSize = parcel.readLong();
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        boolean bl = parcel.readInt() != 0;
        this.wipeUserdata = bl;
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
        parcel.writeString(this.installDir);
        parcel.writeLong(this.gsiSize);
        parcel.writeLong(this.userdataSize);
        parcel.writeInt((int)this.wipeUserdata);
        int n2 = parcel.dataPosition();
        parcel.setDataPosition(n);
        parcel.writeInt(n2 - n);
        parcel.setDataPosition(n2);
    }

}

