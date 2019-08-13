/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Parcel;
import java.util.HashMap;

public class PooledStringWriter {
    private int mNext;
    private final Parcel mOut;
    private final HashMap<String, Integer> mPool;
    private int mStart;

    public PooledStringWriter(Parcel parcel) {
        this.mOut = parcel;
        this.mPool = new HashMap();
        this.mStart = parcel.dataPosition();
        parcel.writeInt(0);
    }

    public void finish() {
        int n = this.mOut.dataPosition();
        this.mOut.setDataPosition(this.mStart);
        this.mOut.writeInt(this.mNext);
        this.mOut.setDataPosition(n);
    }

    public int getStringCount() {
        return this.mPool.size();
    }

    public void writeString(String string2) {
        Integer n = this.mPool.get(string2);
        if (n != null) {
            this.mOut.writeInt(n);
        } else {
            this.mPool.put(string2, this.mNext);
            this.mOut.writeInt(-(this.mNext + 1));
            this.mOut.writeString(string2);
            ++this.mNext;
        }
    }
}

