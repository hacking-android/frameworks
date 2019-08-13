/*
 * Decompiled with CFR 0.145.
 */
package android.apex;

import android.os.Parcel;
import android.os.Parcelable;

public class ApexInfo
implements Parcelable {
    public static final Parcelable.Creator<ApexInfo> CREATOR = new Parcelable.Creator<ApexInfo>(){

        @Override
        public ApexInfo createFromParcel(Parcel parcel) {
            ApexInfo apexInfo = new ApexInfo();
            apexInfo.readFromParcel(parcel);
            return apexInfo;
        }

        public ApexInfo[] newArray(int n) {
            return new ApexInfo[n];
        }
    };
    public boolean isActive;
    public boolean isFactory;
    public String packageName;
    public String packagePath;
    public long versionCode;
    public String versionName;

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
            this.packageName = parcel.readString();
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
        this.packagePath = parcel.readString();
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        this.versionCode = parcel.readLong();
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        this.versionName = parcel.readString();
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.isFactory = bl2;
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.isActive = bl2;
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
        parcel.writeString(this.packageName);
        parcel.writeString(this.packagePath);
        parcel.writeLong(this.versionCode);
        parcel.writeString(this.versionName);
        parcel.writeInt((int)this.isFactory);
        parcel.writeInt((int)this.isActive);
        int n2 = parcel.dataPosition();
        parcel.setDataPosition(n);
        parcel.writeInt(n2 - n);
        parcel.setDataPosition(n2);
    }

}

