/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Checksum;

public class CheckedInputStream
extends FilterInputStream {
    private Checksum cksum;

    public CheckedInputStream(InputStream inputStream, Checksum checksum) {
        super(inputStream);
        this.cksum = checksum;
    }

    public Checksum getChecksum() {
        return this.cksum;
    }

    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (n != -1) {
            this.cksum.update(n);
        }
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if ((n2 = this.in.read(arrby, n, n2)) != -1) {
            this.cksum.update(arrby, n, n2);
        }
        return n2;
    }

    @Override
    public long skip(long l) throws IOException {
        long l2;
        long l3;
        byte[] arrby = new byte[512];
        for (l3 = 0L; l3 < l; l3 += l2) {
            l2 = l - l3;
            int n = l2 < (long)arrby.length ? (int)l2 : arrby.length;
            l2 = this.read(arrby, 0, n);
            if (l2 != -1L) continue;
            return l3;
        }
        return l3;
    }
}

