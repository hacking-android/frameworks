/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
public final class EuiccNotification
implements Parcelable {
    public static final int ALL_EVENTS = 15;
    public static final Parcelable.Creator<EuiccNotification> CREATOR = new Parcelable.Creator<EuiccNotification>(){

        @Override
        public EuiccNotification createFromParcel(Parcel parcel) {
            return new EuiccNotification(parcel);
        }

        public EuiccNotification[] newArray(int n) {
            return new EuiccNotification[n];
        }
    };
    public static final int EVENT_DELETE = 8;
    public static final int EVENT_DISABLE = 4;
    public static final int EVENT_ENABLE = 2;
    public static final int EVENT_INSTALL = 1;
    private final byte[] mData;
    private final int mEvent;
    private final int mSeq;
    private final String mTargetAddr;

    public EuiccNotification(int n, String string2, int n2, byte[] arrby) {
        this.mSeq = n;
        this.mTargetAddr = string2;
        this.mEvent = n2;
        this.mData = arrby;
    }

    private EuiccNotification(Parcel parcel) {
        this.mSeq = parcel.readInt();
        this.mTargetAddr = parcel.readString();
        this.mEvent = parcel.readInt();
        this.mData = parcel.createByteArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (EuiccNotification)object;
            if (this.mSeq != ((EuiccNotification)object).mSeq || !Objects.equals(this.mTargetAddr, ((EuiccNotification)object).mTargetAddr) || this.mEvent != ((EuiccNotification)object).mEvent || !Arrays.equals(this.mData, ((EuiccNotification)object).mData)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public byte[] getData() {
        return this.mData;
    }

    public int getEvent() {
        return this.mEvent;
    }

    public int getSeq() {
        return this.mSeq;
    }

    public String getTargetAddr() {
        return this.mTargetAddr;
    }

    public int hashCode() {
        return (((1 * 31 + this.mSeq) * 31 + Objects.hashCode(this.mTargetAddr)) * 31 + this.mEvent) * 31 + Arrays.hashCode(this.mData);
    }

    public String toString() {
        CharSequence charSequence;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EuiccNotification (seq=");
        stringBuilder.append(this.mSeq);
        stringBuilder.append(", targetAddr=");
        stringBuilder.append(this.mTargetAddr);
        stringBuilder.append(", event=");
        stringBuilder.append(this.mEvent);
        stringBuilder.append(", data=");
        if (this.mData == null) {
            charSequence = "null";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("byte[");
            ((StringBuilder)charSequence).append(this.mData.length);
            ((StringBuilder)charSequence).append("]");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSeq);
        parcel.writeString(this.mTargetAddr);
        parcel.writeInt(this.mEvent);
        parcel.writeByteArray(this.mData);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Event {
    }

}

