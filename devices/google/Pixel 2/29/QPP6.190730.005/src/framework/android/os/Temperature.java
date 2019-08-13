/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Temperature
implements Parcelable {
    public static final Parcelable.Creator<Temperature> CREATOR = new Parcelable.Creator<Temperature>(){

        @Override
        public Temperature createFromParcel(Parcel parcel) {
            return new Temperature(parcel.readFloat(), parcel.readInt(), parcel.readString(), parcel.readInt());
        }

        public Temperature[] newArray(int n) {
            return new Temperature[n];
        }
    };
    public static final int THROTTLING_CRITICAL = 4;
    public static final int THROTTLING_EMERGENCY = 5;
    public static final int THROTTLING_LIGHT = 1;
    public static final int THROTTLING_MODERATE = 2;
    public static final int THROTTLING_NONE = 0;
    public static final int THROTTLING_SEVERE = 3;
    public static final int THROTTLING_SHUTDOWN = 6;
    public static final int TYPE_BATTERY = 2;
    public static final int TYPE_BCL_CURRENT = 7;
    public static final int TYPE_BCL_PERCENTAGE = 8;
    public static final int TYPE_BCL_VOLTAGE = 6;
    public static final int TYPE_CPU = 0;
    public static final int TYPE_GPU = 1;
    public static final int TYPE_NPU = 9;
    public static final int TYPE_POWER_AMPLIFIER = 5;
    public static final int TYPE_SKIN = 3;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_USB_PORT = 4;
    private final String mName;
    private final int mStatus;
    private final int mType;
    private final float mValue;

    public Temperature(float f, int n, String string2, int n2) {
        Preconditions.checkArgument(Temperature.isValidType(n), "Invalid Type");
        Preconditions.checkArgument(Temperature.isValidStatus(n2), "Invalid Status");
        this.mValue = f;
        this.mType = n;
        this.mName = Preconditions.checkStringNotEmpty(string2);
        this.mStatus = n2;
    }

    public static boolean isValidStatus(int n) {
        boolean bl = n >= 0 && n <= 6;
        return bl;
    }

    public static boolean isValidType(int n) {
        boolean bl = n >= -1 && n <= 9;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof Temperature;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (Temperature)object;
            if (((Temperature)object).mValue != this.mValue || ((Temperature)object).mType != this.mType || !((Temperature)object).mName.equals(this.mName) || ((Temperature)object).mStatus != this.mStatus) break block1;
            bl = true;
        }
        return bl;
    }

    public String getName() {
        return this.mName;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getType() {
        return this.mType;
    }

    public float getValue() {
        return this.mValue;
    }

    public int hashCode() {
        return ((this.mName.hashCode() * 31 + Float.hashCode(this.mValue)) * 31 + this.mType) * 31 + this.mStatus;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Temperature{mValue=");
        stringBuilder.append(this.mValue);
        stringBuilder.append(", mType=");
        stringBuilder.append(this.mType);
        stringBuilder.append(", mName=");
        stringBuilder.append(this.mName);
        stringBuilder.append(", mStatus=");
        stringBuilder.append(this.mStatus);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.mValue);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mStatus);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ThrottlingStatus {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Type {
    }

}

