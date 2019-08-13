/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.proto.ProtoOutputStream;

public class FeatureInfo
implements Parcelable {
    public static final Parcelable.Creator<FeatureInfo> CREATOR = new Parcelable.Creator<FeatureInfo>(){

        @Override
        public FeatureInfo createFromParcel(Parcel parcel) {
            return new FeatureInfo(parcel);
        }

        public FeatureInfo[] newArray(int n) {
            return new FeatureInfo[n];
        }
    };
    public static final int FLAG_REQUIRED = 1;
    public static final int GL_ES_VERSION_UNDEFINED = 0;
    public int flags;
    public String name;
    public int reqGlEsVersion;
    public int version;

    public FeatureInfo() {
    }

    public FeatureInfo(FeatureInfo featureInfo) {
        this.name = featureInfo.name;
        this.version = featureInfo.version;
        this.reqGlEsVersion = featureInfo.reqGlEsVersion;
        this.flags = featureInfo.flags;
    }

    private FeatureInfo(Parcel parcel) {
        this.name = parcel.readString();
        this.version = parcel.readInt();
        this.reqGlEsVersion = parcel.readInt();
        this.flags = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getGlEsVersion() {
        int n = this.reqGlEsVersion;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf((-65536 & n) >> 16));
        stringBuilder.append(".");
        stringBuilder.append(String.valueOf(n & 65535));
        return stringBuilder.toString();
    }

    public String toString() {
        if (this.name != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FeatureInfo{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" ");
            stringBuilder.append(this.name);
            stringBuilder.append(" v=");
            stringBuilder.append(this.version);
            stringBuilder.append(" fl=0x");
            stringBuilder.append(Integer.toHexString(this.flags));
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FeatureInfo{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" glEsVers=");
        stringBuilder.append(this.getGlEsVersion());
        stringBuilder.append(" fl=0x");
        stringBuilder.append(Integer.toHexString(this.flags));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.name);
        parcel.writeInt(this.version);
        parcel.writeInt(this.reqGlEsVersion);
        parcel.writeInt(this.flags);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        String string2 = this.name;
        if (string2 != null) {
            protoOutputStream.write(1138166333441L, string2);
        }
        protoOutputStream.write(1120986464258L, this.version);
        protoOutputStream.write(1138166333443L, this.getGlEsVersion());
        protoOutputStream.write(1120986464260L, this.flags);
        protoOutputStream.end(l);
    }

}

