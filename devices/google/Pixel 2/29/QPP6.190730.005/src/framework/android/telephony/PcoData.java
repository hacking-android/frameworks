/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;

public class PcoData
implements Parcelable {
    public static final Parcelable.Creator<PcoData> CREATOR = new Parcelable.Creator(){

        public PcoData createFromParcel(Parcel parcel) {
            return new PcoData(parcel);
        }

        public PcoData[] newArray(int n) {
            return new PcoData[n];
        }
    };
    public final String bearerProto;
    public final int cid;
    public final byte[] contents;
    public final int pcoId;

    public PcoData(int n, String string2, int n2, byte[] arrby) {
        this.cid = n;
        this.bearerProto = string2;
        this.pcoId = n2;
        this.contents = arrby;
    }

    public PcoData(Parcel parcel) {
        this.cid = parcel.readInt();
        this.bearerProto = parcel.readString();
        this.pcoId = parcel.readInt();
        this.contents = parcel.createByteArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PcoData(");
        stringBuilder.append(this.cid);
        stringBuilder.append(", ");
        stringBuilder.append(this.bearerProto);
        stringBuilder.append(", ");
        stringBuilder.append(this.pcoId);
        stringBuilder.append(", contents[");
        stringBuilder.append(this.contents.length);
        stringBuilder.append("])");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.cid);
        parcel.writeString(this.bearerProto);
        parcel.writeInt(this.pcoId);
        parcel.writeByteArray(this.contents);
    }

}

