/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.security.keymaster.KeymasterArgument;
import android.security.keymaster.KeymasterDefs;

class KeymasterBlobArgument
extends KeymasterArgument {
    @UnsupportedAppUsage
    public final byte[] blob;

    @UnsupportedAppUsage
    public KeymasterBlobArgument(int n, Parcel parcel) {
        super(n);
        this.blob = parcel.createByteArray();
    }

    @UnsupportedAppUsage
    public KeymasterBlobArgument(int n, byte[] object) {
        super(n);
        int n2 = KeymasterDefs.getTagType(n);
        if (n2 != Integer.MIN_VALUE && n2 != -1879048192) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Bad blob tag ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        this.blob = object;
    }

    @Override
    public void writeValue(Parcel parcel) {
        parcel.writeByteArray(this.blob);
    }
}

