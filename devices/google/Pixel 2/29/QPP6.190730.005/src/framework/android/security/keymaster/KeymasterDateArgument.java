/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.security.keymaster.KeymasterArgument;
import android.security.keymaster.KeymasterDefs;
import java.io.Serializable;
import java.util.Date;

class KeymasterDateArgument
extends KeymasterArgument {
    public final Date date;

    @UnsupportedAppUsage
    public KeymasterDateArgument(int n, Parcel parcel) {
        super(n);
        this.date = new Date(parcel.readLong());
    }

    public KeymasterDateArgument(int n, Date serializable) {
        super(n);
        if (KeymasterDefs.getTagType(n) == 1610612736) {
            this.date = serializable;
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Bad date tag ");
        ((StringBuilder)serializable).append(n);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    @Override
    public void writeValue(Parcel parcel) {
        parcel.writeLong(this.date.getTime());
    }
}

