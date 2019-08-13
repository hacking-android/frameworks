/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import android.security.keymaster.KeymasterBlobArgument;
import android.security.keymaster.KeymasterBooleanArgument;
import android.security.keymaster.KeymasterDateArgument;
import android.security.keymaster.KeymasterDefs;
import android.security.keymaster.KeymasterIntArgument;
import android.security.keymaster.KeymasterLongArgument;

abstract class KeymasterArgument
implements Parcelable {
    public static final Parcelable.Creator<KeymasterArgument> CREATOR = new Parcelable.Creator<KeymasterArgument>(){

        @Override
        public KeymasterArgument createFromParcel(Parcel object) {
            int n = ((Parcel)object).dataPosition();
            int n2 = ((Parcel)object).readInt();
            int n3 = KeymasterDefs.getTagType(n2);
            if (n3 != Integer.MIN_VALUE && n3 != -1879048192) {
                if (n3 != -1610612736) {
                    if (n3 != 268435456 && n3 != 536870912 && n3 != 805306368 && n3 != 1073741824) {
                        if (n3 != 1342177280) {
                            if (n3 != 1610612736) {
                                if (n3 == 1879048192) {
                                    return new KeymasterBooleanArgument(n2, (Parcel)object);
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Bad tag: ");
                                ((StringBuilder)object).append(n2);
                                ((StringBuilder)object).append(" at ");
                                ((StringBuilder)object).append(n);
                                throw new ParcelFormatException(((StringBuilder)object).toString());
                            }
                            return new KeymasterDateArgument(n2, (Parcel)object);
                        }
                    } else {
                        return new KeymasterIntArgument(n2, (Parcel)object);
                    }
                }
                return new KeymasterLongArgument(n2, (Parcel)object);
            }
            return new KeymasterBlobArgument(n2, (Parcel)object);
        }

        public KeymasterArgument[] newArray(int n) {
            return new KeymasterArgument[n];
        }
    };
    public final int tag;

    protected KeymasterArgument(int n) {
        this.tag = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.tag);
        this.writeValue(parcel);
    }

    public abstract void writeValue(Parcel var1);

}

