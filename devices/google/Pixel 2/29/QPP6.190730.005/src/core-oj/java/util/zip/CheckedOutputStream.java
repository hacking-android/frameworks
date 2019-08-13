/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Checksum;

public class CheckedOutputStream
extends FilterOutputStream {
    private Checksum cksum;

    public CheckedOutputStream(OutputStream outputStream, Checksum checksum) {
        super(outputStream);
        this.cksum = checksum;
    }

    public Checksum getChecksum() {
        return this.cksum;
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
        this.cksum.update(n);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.out.write(arrby, n, n2);
        this.cksum.update(arrby, n, n2);
    }
}

