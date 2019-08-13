/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

@SystemApi
public final class ImsSuppServiceNotification
implements Parcelable {
    public static final Parcelable.Creator<ImsSuppServiceNotification> CREATOR = new Parcelable.Creator<ImsSuppServiceNotification>(){

        @Override
        public ImsSuppServiceNotification createFromParcel(Parcel parcel) {
            return new ImsSuppServiceNotification(parcel);
        }

        public ImsSuppServiceNotification[] newArray(int n) {
            return new ImsSuppServiceNotification[n];
        }
    };
    private static final String TAG = "ImsSuppServiceNotification";
    public final int code;
    public final String[] history;
    public final int index;
    public final int notificationType;
    public final String number;
    public final int type;

    public ImsSuppServiceNotification(int n, int n2, int n3, int n4, String string2, String[] arrstring) {
        this.notificationType = n;
        this.code = n2;
        this.index = n3;
        this.type = n4;
        this.number = string2;
        this.history = arrstring;
    }

    public ImsSuppServiceNotification(Parcel parcel) {
        this.notificationType = parcel.readInt();
        this.code = parcel.readInt();
        this.index = parcel.readInt();
        this.type = parcel.readInt();
        this.number = parcel.readString();
        this.history = parcel.createStringArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ notificationType=");
        stringBuilder.append(this.notificationType);
        stringBuilder.append(", code=");
        stringBuilder.append(this.code);
        stringBuilder.append(", index=");
        stringBuilder.append(this.index);
        stringBuilder.append(", type=");
        stringBuilder.append(this.type);
        stringBuilder.append(", number=");
        stringBuilder.append(this.number);
        stringBuilder.append(", history=");
        stringBuilder.append(Arrays.toString(this.history));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.notificationType);
        parcel.writeInt(this.code);
        parcel.writeInt(this.index);
        parcel.writeInt(this.type);
        parcel.writeString(this.number);
        parcel.writeStringArray(this.history);
    }

}

