/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Parcel;

public class PooledStringReader {
    private final Parcel mIn;
    private final String[] mPool;

    public PooledStringReader(Parcel parcel) {
        this.mIn = parcel;
        this.mPool = new String[parcel.readInt()];
    }

    public int getStringCount() {
        return this.mPool.length;
    }

    public String readString() {
        String string2;
        int n = this.mIn.readInt();
        if (n >= 0) {
            return this.mPool[n];
        }
        n = -n;
        this.mPool[n - 1] = string2 = this.mIn.readString();
        return string2;
    }
}

