/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.security.keymaster.KeymasterArgument;
import android.security.keymaster.KeymasterDefs;

class KeymasterIntArgument
extends KeymasterArgument {
    @UnsupportedAppUsage
    public final int value;

    @UnsupportedAppUsage
    public KeymasterIntArgument(int n, int n2) {
        super(n);
        int n3 = KeymasterDefs.getTagType(n);
        if (n3 != 268435456 && n3 != 536870912 && n3 != 805306368 && n3 != 1073741824) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad int tag ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.value = n2;
    }

    @UnsupportedAppUsage
    public KeymasterIntArgument(int n, Parcel parcel) {
        super(n);
        this.value = parcel.readInt();
    }

    @Override
    public void writeValue(Parcel parcel) {
        parcel.writeInt(this.value);
    }
}

