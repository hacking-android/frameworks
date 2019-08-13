/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class DigestInputStream
extends FilterInputStream {
    protected MessageDigest digest;
    private boolean on = true;

    public DigestInputStream(InputStream inputStream, MessageDigest messageDigest) {
        super(inputStream);
        this.setMessageDigest(messageDigest);
    }

    public MessageDigest getMessageDigest() {
        return this.digest;
    }

    public void on(boolean bl) {
        this.on = bl;
    }

    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (this.on && n != -1) {
            this.digest.update((byte)n);
        }
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        n2 = this.in.read(arrby, n, n2);
        if (this.on && n2 != -1) {
            this.digest.update(arrby, n, n2);
        }
        return n2;
    }

    public void setMessageDigest(MessageDigest messageDigest) {
        this.digest = messageDigest;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Digest Input Stream] ");
        stringBuilder.append(this.digest.toString());
        return stringBuilder.toString();
    }
}

