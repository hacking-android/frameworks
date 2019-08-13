/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.ArrayUtils
 */
package android.content.pm;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import libcore.util.ArrayUtils;

public class LimitedLengthInputStream
extends FilterInputStream {
    private final long mEnd;
    private long mOffset;

    public LimitedLengthInputStream(InputStream inputStream, long l, long l2) throws IOException {
        super(inputStream);
        if (inputStream != null) {
            if (l >= 0L) {
                if (l2 >= 0L) {
                    if (l2 <= Long.MAX_VALUE - l) {
                        this.mEnd = l + l2;
                        this.skip(l);
                        this.mOffset = l;
                        return;
                    }
                    throw new IOException("offset + length > Long.MAX_VALUE");
                }
                throw new IOException("length < 0");
            }
            throw new IOException("offset < 0");
        }
        throw new IOException("in == null");
    }

    @Override
    public int read() throws IOException {
        synchronized (this) {
            block4 : {
                long l = this.mOffset++;
                long l2 = this.mEnd;
                if (l < l2) break block4;
                return -1;
            }
            int n = super.read();
            return n;
        }
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] object, int n, int n2) throws IOException {
        if (this.mOffset >= this.mEnd) {
            return -1;
        }
        ArrayUtils.throwsIfOutOfBounds((int)((byte[])object).length, (int)n, (int)n2);
        long l = this.mOffset;
        if (l <= Long.MAX_VALUE - (long)n2) {
            long l2 = n2;
            long l3 = this.mEnd;
            if (l2 + l > l3) {
                n2 = (int)(l3 - l);
            }
            n = super.read((byte[])object, n, n2);
            this.mOffset += (long)n;
            return n;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("offset out of bounds: ");
        ((StringBuilder)object).append(this.mOffset);
        ((StringBuilder)object).append(" + ");
        ((StringBuilder)object).append(n2);
        throw new IOException(((StringBuilder)object).toString());
    }
}

