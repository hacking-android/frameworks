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

public final class CoolingDevice
implements Parcelable {
    public static final Parcelable.Creator<CoolingDevice> CREATOR = new Parcelable.Creator<CoolingDevice>(){

        @Override
        public CoolingDevice createFromParcel(Parcel parcel) {
            return new CoolingDevice(parcel.readLong(), parcel.readInt(), parcel.readString());
        }

        public CoolingDevice[] newArray(int n) {
            return new CoolingDevice[n];
        }
    };
    public static final int TYPE_BATTERY = 1;
    public static final int TYPE_COMPONENT = 6;
    public static final int TYPE_CPU = 2;
    public static final int TYPE_FAN = 0;
    public static final int TYPE_GPU = 3;
    public static final int TYPE_MODEM = 4;
    public static final int TYPE_NPU = 5;
    private final String mName;
    private final int mType;
    private final long mValue;

    public CoolingDevice(long l, int n, String string2) {
        Preconditions.checkArgument(CoolingDevice.isValidType(n), "Invalid Type");
        this.mValue = l;
        this.mType = n;
        this.mName = Preconditions.checkStringNotEmpty(string2);
    }

    public static boolean isValidType(int n) {
        boolean bl = n >= 0 && n <= 6;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof CoolingDevice;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (CoolingDevice)object;
        bl = bl2;
        if (((CoolingDevice)object).mValue == this.mValue) {
            bl = bl2;
            if (((CoolingDevice)object).mType == this.mType) {
                bl = bl2;
                if (((CoolingDevice)object).mName.equals(this.mName)) {
                    bl = true;
                }
            }
        }
        return bl;
    }

    public String getName() {
        return this.mName;
    }

    public int getType() {
        return this.mType;
    }

    public long getValue() {
        return this.mValue;
    }

    public int hashCode() {
        return (this.mName.hashCode() * 31 + Long.hashCode(this.mValue)) * 31 + this.mType;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CoolingDevice{mValue=");
        stringBuilder.append(this.mValue);
        stringBuilder.append(", mType=");
        stringBuilder.append(this.mType);
        stringBuilder.append(", mName=");
        stringBuilder.append(this.mName);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mValue);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mName);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Type {
    }

}

