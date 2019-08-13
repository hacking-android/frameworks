/*
 * Decompiled with CFR 0.145.
 */
package android.net.apf;

import android.annotation.SystemApi;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class ApfCapabilities
implements Parcelable {
    public static final Parcelable.Creator<ApfCapabilities> CREATOR = new Parcelable.Creator<ApfCapabilities>(){

        @Override
        public ApfCapabilities createFromParcel(Parcel parcel) {
            return new ApfCapabilities(parcel);
        }

        public ApfCapabilities[] newArray(int n) {
            return new ApfCapabilities[n];
        }
    };
    public final int apfPacketFormat;
    public final int apfVersionSupported;
    public final int maximumApfProgramSize;

    public ApfCapabilities(int n, int n2, int n3) {
        this.apfVersionSupported = n;
        this.maximumApfProgramSize = n2;
        this.apfPacketFormat = n3;
    }

    private ApfCapabilities(Parcel parcel) {
        this.apfVersionSupported = parcel.readInt();
        this.maximumApfProgramSize = parcel.readInt();
        this.apfPacketFormat = parcel.readInt();
    }

    public static boolean getApfDrop8023Frames() {
        return Resources.getSystem().getBoolean(17891361);
    }

    public static int[] getApfEtherTypeBlackList() {
        return Resources.getSystem().getIntArray(17235984);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof ApfCapabilities;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (ApfCapabilities)object;
        bl = bl2;
        if (this.apfVersionSupported == ((ApfCapabilities)object).apfVersionSupported) {
            bl = bl2;
            if (this.maximumApfProgramSize == ((ApfCapabilities)object).maximumApfProgramSize) {
                bl = bl2;
                if (this.apfPacketFormat == ((ApfCapabilities)object).apfPacketFormat) {
                    bl = true;
                }
            }
        }
        return bl;
    }

    public boolean hasDataAccess() {
        boolean bl = this.apfVersionSupported >= 4;
        return bl;
    }

    public String toString() {
        return String.format("%s{version: %d, maxSize: %d, format: %d}", this.getClass().getSimpleName(), this.apfVersionSupported, this.maximumApfProgramSize, this.apfPacketFormat);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.apfVersionSupported);
        parcel.writeInt(this.maximumApfProgramSize);
        parcel.writeInt(this.apfPacketFormat);
    }

}

