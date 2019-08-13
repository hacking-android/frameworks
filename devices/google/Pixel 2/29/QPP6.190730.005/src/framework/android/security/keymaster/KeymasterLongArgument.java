/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.security.keymaster.KeymasterArgument;
import android.security.keymaster.KeymasterDefs;

class KeymasterLongArgument
extends KeymasterArgument {
    @UnsupportedAppUsage
    public final long value;

    @UnsupportedAppUsage
    public KeymasterLongArgument(int n, long l) {
        super(n);
        int n2 = KeymasterDefs.getTagType(n);
        if (n2 != -1610612736 && n2 != 1342177280) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad long tag ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.value = l;
    }

    @UnsupportedAppUsage
    public KeymasterLongArgument(int n, Parcel parcel) {
        super(n);
        this.value = parcel.readLong();
    }

    @Override
    public void writeValue(Parcel parcel) {
        parcel.writeLong(this.value);
    }
}

