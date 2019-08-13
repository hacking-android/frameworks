/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.security.keymaster.KeymasterArgument;
import android.security.keymaster.KeymasterDefs;

class KeymasterBooleanArgument
extends KeymasterArgument {
    public final boolean value;

    public KeymasterBooleanArgument(int n) {
        super(n);
        this.value = true;
        if (KeymasterDefs.getTagType(n) == 1879048192) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad bool tag ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public KeymasterBooleanArgument(int n, Parcel parcel) {
        super(n);
        this.value = true;
    }

    @Override
    public void writeValue(Parcel parcel) {
    }
}

