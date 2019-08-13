/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class ObbInfo
implements Parcelable {
    public static final Parcelable.Creator<ObbInfo> CREATOR = new Parcelable.Creator<ObbInfo>(){

        @Override
        public ObbInfo createFromParcel(Parcel parcel) {
            return new ObbInfo(parcel);
        }

        public ObbInfo[] newArray(int n) {
            return new ObbInfo[n];
        }
    };
    public static final int OBB_OVERLAY = 1;
    public String filename;
    public int flags;
    public String packageName;
    @UnsupportedAppUsage
    public byte[] salt;
    public int version;

    ObbInfo() {
    }

    private ObbInfo(Parcel parcel) {
        this.filename = parcel.readString();
        this.packageName = parcel.readString();
        this.version = parcel.readInt();
        this.flags = parcel.readInt();
        this.salt = parcel.createByteArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ObbInfo{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" packageName=");
        stringBuilder.append(this.packageName);
        stringBuilder.append(",version=");
        stringBuilder.append(this.version);
        stringBuilder.append(",flags=");
        stringBuilder.append(this.flags);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.filename);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.version);
        parcel.writeInt(this.flags);
        parcel.writeByteArray(this.salt);
    }

}

